/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is Forte for Java, Community Edition. The Initial
 * Developer of the Original Code is Sun Microsystems, Inc. Portions
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.editor;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
* Support class for chain of MarkBlocks
*
* @author Miloslav Metelka
* @version 1.00
*/

public class MarkBlockChain {

    /** Chain of all blocks */
    protected MarkBlock chain;

    /** Current block to make checks faster */
    protected MarkBlock currentBlock;

    /** Document for this block */
    protected BaseDocument doc;

    /** Construct chain using regular base marks */
    public MarkBlockChain(BaseDocument doc) {
        this.doc = doc;
    }

    public final MarkBlock getChain() {
        return chain;
    }

    /** Tests whether the position range is partly or fully inside
    * some mark block from the chain.
    * @param startPos starting position of tested area
    * @param endPos ending position of tested area for removal or same
    *   as startPos when insert is made
    * @return relation of currentBlock to the given block
    */
    public int compareBlock(int startPos, int endPos) {
        if (currentBlock == null) {
            currentBlock = chain;
            if (currentBlock == null) {
                return MarkBlock.INVALID;
            }
        }

        int rel; // relation of block to particular mark block
        boolean afterPrev = false; // blk is after previous block
        boolean beforeNext = false; // blk is before next block
        boolean cont = false; // blk continued currentBlock in previous match
        MarkBlock contBlk = null;
        int contRel = 0;
        while (true) {
            rel = currentBlock.compare(startPos, endPos);
            if ((rel & MarkBlock.OVERLAP) != 0) {
                return rel;
            }

            if ((rel & MarkBlock.AFTER) != 0) { // after this mark block
                if (beforeNext) {
                    if (!cont || (rel & MarkBlock.CONTINUE) != 0) {
                        return rel;
                    } else { // continues with contBlk and this relation is pure after
                        currentBlock = contBlk;
                        return contRel;
                    }
                } else { // going from begining of chain
                    if (currentBlock.next != null) {
                        afterPrev = true;
                        cont = ((rel & MarkBlock.CONTINUE) != 0);
                        if (cont) {
                            contRel = rel;
                            contBlk = currentBlock;
                        }
                        currentBlock = currentBlock.next;
                    } else { // end of chain
                        return rel;
                    }
                }
            } else { // before this mark block
                if (afterPrev) {
                    if (!cont || (rel & MarkBlock.EXTEND) != 0) {
                        return rel;
                    } else {
                        currentBlock = contBlk;
                        return contRel;
                    }
                } else { // going from end of chain
                    if (currentBlock.prev != null) {
                        beforeNext = true;
                        cont = ((rel & MarkBlock.CONTINUE) != 0);
                        if (cont) {
                            contRel = rel;
                            contBlk = currentBlock;
                        }
                        currentBlock = currentBlock.prev;
                    } else { // begining of chain
                        return rel;
                    }
                }
            }
        }
    }

    public void removeEmptyBlocks() {
        try {
            MarkBlock blk = chain;
            while (blk != null) {
                if (blk.startMark.getOffset() == blk.endMark.getOffset()) { // empty block
                    blk = checkedRemove(blk); // remove current block and get the next one
                } else {
                    blk = blk.next;
                }
            }
        } catch (InvalidMarkException e) {
            if (Boolean.getBoolean("netbeans.debug.exceptions")) { // NOI18N
                e.printStackTrace();
            }
        }
    }

    protected MarkBlock createBlock(int startPos, int endPos)
    throws BadLocationException {
        return new MarkBlock(doc, createBlockStartMark(), createBlockEndMark(),
                             startPos, endPos);
    }

    protected Mark createBlockStartMark() {
        return new Mark();
    }

    protected Mark createBlockEndMark() {
        return new Mark();
    }


    /** Add non-empty block to the chain of blocks
    * @param concat whether concatenate adjacent blocks
    */
    public void addBlock(int startPos, int endPos, boolean concat) {
        if (startPos == endPos) {
            return;
        }
        try {
            int rel = compareBlock(startPos, endPos) & MarkBlock.IGNORE_EMPTY;
            if ((rel & MarkBlock.BEFORE) != 0) { // before currentBlock or continue_begin
                if (concat && rel == MarkBlock.CONTINUE_BEGIN) { // concatenate
                    doc.op.moveMark(currentBlock.startMark, startPos);
                } else { // insert new block at begining
                    boolean first = (currentBlock == chain);
                    MarkBlock blk = currentBlock.insertChain(createBlock(startPos, endPos));
                    if (first) {
                        chain = blk;
                    }
                }
            } else if ((rel & MarkBlock.AFTER) != 0) { // after currentBlock or continue_end
                if (concat && rel == MarkBlock.CONTINUE_END) {
                    doc.op.moveMark(currentBlock.endMark, endPos);
                } else { // add new block to the chain
                    currentBlock.addChain(createBlock(startPos, endPos));
                }
            } else { // overlap or invalid relation
                if (currentBlock == null) { // no current block
                    chain = createBlock(startPos, endPos);
                } else { // overlap
                    // the block is partly hit - extend it by positions
                    currentBlock.extendStart(startPos);
                    currentBlock.extendEnd(endPos);
                    // remove the blocks covered by startPos to endPos
                    MarkBlock blk = chain;
                    while (blk != null) {
                        if (blk != currentBlock) { // except self
                            if (currentBlock.extend(blk, concat)) { // if they overlapped
                                MarkBlock tempCurBlk = currentBlock;
                                blk = checkedRemove(blk); // will clear currentBlock
                                currentBlock = tempCurBlk;
                            } else { // didn't overlap, go to next
                                blk = blk.next;
                            }
                        } else {
                            blk = blk.next;
                        }
                    }
                }
            }
        } catch (InvalidMarkException e) {
            if (Boolean.getBoolean("netbeans.debug.exceptions")) { // NOI18N
                e.printStackTrace();
            }
        } catch (BadLocationException e) {
            if (Boolean.getBoolean("netbeans.debug.exceptions")) { // NOI18N
                e.printStackTrace();
            }
        }
    }

    /** Remove non-empty block from area covered by blocks from chain */
    public void removeBlock(int startPos, int endPos) {
        if (startPos == endPos) {
            return;
        }
        try {
            int rel;
            while (((rel = compareBlock(startPos, endPos)) & MarkBlock.OVERLAP) != 0) {
                if ((rel & MarkBlock.THIS_EMPTY) != 0) { // currentBlock is empty
                    checkedRemove(currentBlock);
                } else {
                    switch (currentBlock.shrink(startPos, endPos)) {
                    case MarkBlock.INNER: // tested block inside currentBlock -> divide
                        int endMarkPos = currentBlock.endMark.getOffset();
                        doc.op.moveMark(currentBlock.endMark, startPos);
                        currentBlock.addChain(createBlock(endPos, endMarkPos));
                        return;
                    case MarkBlock.INSIDE_BEGIN:
                    case MarkBlock.OVERLAP_BEGIN:
                        doc.op.moveMark(currentBlock.startMark, endPos);
                        return;
                    case MarkBlock.INSIDE_END:
                    case MarkBlock.OVERLAP_END:
                        doc.op.moveMark(currentBlock.endMark, startPos);
                        return;
                    default: // EXTEND
                        checkedRemove(currentBlock);
                        break;
                    }
                }
            }
        } catch (BadLocationException e) {
            if (Boolean.getBoolean("netbeans.debug.exceptions")) { // NOI18N
                e.printStackTrace();
            }
        } catch (InvalidMarkException e) {
            if (Boolean.getBoolean("netbeans.debug.exceptions")) { // NOI18N
                e.printStackTrace();
            }
        }
    }

    /** Removes mark block and possibly updates the chain.
    * @return next block after removed one
    */
    protected MarkBlock checkedRemove(MarkBlock blk) {
        boolean first = (blk == chain);
        blk = blk.removeChain();
        if (first) {
            chain = blk;
        }
        currentBlock = null; // make sure current block is cleared
        return blk;
    }

    public int adjustToBlockEnd(int pos) {
        int rel = compareBlock(pos, pos) & MarkBlock.IGNORE_EMPTY;
        if (rel == MarkBlock.INSIDE_BEGIN || rel == MarkBlock.INNER) { // inside blk
            pos = currentBlock.getEndOffset();
        }
        return pos;
    }

    /** Return the position adjusted to the start of the next mark-block.
    */
    public int adjustToNextBlockStart(int pos) {
        // !!! what about empty blocks
        int rel = compareBlock(pos, pos) & MarkBlock.IGNORE_EMPTY;
        if ((rel & MarkBlock.BEFORE) != 0) {
            pos = currentBlock.getStartOffset();
        } else { // after the block or inside
            if (currentBlock != null) {
                MarkBlock nextBlk = currentBlock.getNext();
                if (nextBlk != null) {
                    pos = nextBlk.getStartOffset();
                } else { // no next block
                    pos = -1;
                }
            } else { // no current block
                pos = -1;
            }
        }
        return pos;
    }

    public static class LayerChain extends MarkBlockChain {

        private String layerName;

        public LayerChain(BaseDocument doc, String layerName) {
            super(doc);
            this.layerName = layerName;
        }

        public final String getLayerName() {
            return layerName;
        }

        protected Mark createBlockStartMark() {
            MarkFactory.DrawMark startMark = new MarkFactory.DrawMark(layerName, null);
            startMark.activateLayer = true;
            return startMark;
        }

        protected Mark createBlockEndMark() {
            MarkFactory.DrawMark endMark = new MarkFactory.DrawMark(layerName, null);
            endMark.insertAfter = true;
            return endMark;
        }

    }

    public String toString() {
        return "MarkBlockChain: currentBlock=" + currentBlock + "\nblock chain: " // NOI18N
               + (chain != null ? ("\n" + chain.toStringChain()) : " Empty"); // NOI18N
    }

}

/*
 * Log
 *  12   Gandalf-post-FCS1.10.1.0    3/8/00   Miloslav Metelka 
 *  11   Gandalf   1.10        1/18/00  Miloslav Metelka 
 *  10   Gandalf   1.9         1/13/00  Miloslav Metelka 
 *  9    Gandalf   1.8         1/10/00  Miloslav Metelka 
 *  8    Gandalf   1.7         11/14/99 Miloslav Metelka 
 *  7    Gandalf   1.6         11/8/99  Miloslav Metelka 
 *  6    Gandalf   1.5         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  5    Gandalf   1.4         10/10/99 Miloslav Metelka 
 *  4    Gandalf   1.3         5/13/99  Miloslav Metelka 
 *  3    Gandalf   1.2         5/7/99   Miloslav Metelka line numbering and fixes
 *  2    Gandalf   1.1         3/23/99  Miloslav Metelka 
 *  1    Gandalf   1.0         3/18/99  Miloslav Metelka 
 * $
 */

