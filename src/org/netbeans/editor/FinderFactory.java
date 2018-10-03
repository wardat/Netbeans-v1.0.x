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

/**
* Various finders are located here.
*
* @author Miloslav Metelka
* @version 1.00
*/

public class FinderFactory {

    /** Abstract finder implementation. The only <CODE>find()</CODE>
    * method must be redefined.
    */
    public static abstract class AbstractFinder implements Finder {

        /** Was the string found? */
        protected boolean found;

        /** Was the string found? */
        public final boolean isFound() {
            return found;
        }

        /** Reset the finder */
        public void reset() {
            found = false;
        }

    }

    /** Return successful match on the first searched char */
    public static class TrueFinder extends AbstractFinder {

        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            found = true;
            return reqPos;
        }

    }

    /** Request non-existent position immediately */
    public static class FalseFinder extends AbstractFinder
        implements StringFinder {

        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            return -1;
        }

        public int getFoundLength() {
            return 0;
        }

    }

    /** Finds end of current line in forward direction */
    static final class EOLFwdFinder extends AbstractFinder {

        /** Finds EOL in forward direction */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            while (offset < offset2) {
                if (buffer[offset] == '\n') {
                    found = true;
                    return bufferStartPos + offset;
                }
                offset++;
            }
            return bufferStartPos + offset;
        }

    }

    /** Finder for going n-lines forward where n is greater or equal 1 and stops
    * on the first position of next line.
    */
    static final class NBOLFwdFinder extends AbstractFinder {

        /** How many lines go forward */
        int fwdLines;

        /** Finds BOL in forward direction */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            while (offset < offset2) {
                if (buffer[offset] == '\n') {
                    fwdLines--;
                    if (fwdLines == 0) {
                        found = true;
                        return bufferStartPos + offset + 1;
                    }
                }
                offset++;
            }
            return bufferStartPos + offset;
        }

    }

    /** Finder for going n-lines forward where n is greater or equal 1
    * and finding and storing the BOL position of the line and then
    * going till EOL is found.
    */
    static final class BEOLLineFwdFinder extends AbstractFinder {

        /** How many lines go forward */
        int fwdLines;

        /** Position of BOL */
        int bolPos;

        public void reset() {
            bolPos = -1;
        }

        /** Finds BOL and EOL in forward direction */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            while (offset < offset2) {
                if (buffer[offset] == '\n') {
                    if (fwdLines-- == 0) {
                        found = true;
                        return bufferStartPos + offset; // store eol
                    }
                    bolPos =  bufferStartPos + offset + 1; // store bol
                }
                offset++;
            }
            return bufferStartPos + offset;
        }

    }

    /** Finder for finding the line, bol and eol from position.
    */
    static final class BEOLPosFwdFinder extends AbstractFinder {

        /** How many lines went forward */
        int line;

        /** Target position */
        int tgtPos;

        /** Begin of line position */
        int bolPos;

        public void reset() {
            line = 0;
            bolPos = -1;
        }

        /** Finds BOL and EOL in forward direction */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            while (offset < offset2) {
                if (buffer[offset] == '\n') {
                    if (tgtPos <= bufferStartPos + offset) {
                        found = true;
                        return bufferStartPos + offset; // store eol
                    }
                    line++;
                    bolPos = bufferStartPos + offset + 1; // store bol
                }
                offset++;
            }
            return bufferStartPos + offset;
        }

    }

    /** Finder for going back to the first new line which helps
    * to find the begining of the line.
    */
    public static final class BOLBwdFinder extends AbstractFinder {

        /** finds BOL on current line */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            while (offset >= offset1) {
                if (buffer[offset] == '\n') {
                    found = true;
                    return bufferStartPos + offset;
                }
                offset--;
            }
            return bufferStartPos + offset;
        }

    }

    /** Finder for getting visual column value for particular position.
    * The starting position for find must be the start of particular
    * line. The limit position should be set to position for which
    * the visual column is requested. This method can be used only
    * in case the font is superfixed i.e. all the characters of all
    * font styles have the same width.
    */
    public static final class PosVisColFwdFinder extends AbstractFinder {

        /** Visual column on line */
        int visCol;

        /** Tab size for particular document scanned */
        int tabSize;

        /** Get visual column that this finder computed */
        public int getVisCol() {
            return visCol;
        }

        public void setTabSize(int tabSize) {
            this.tabSize = tabSize;
        }

        /** Mark that first call will follow */
        public void reset() {
            super.reset();
            visCol = 0;
        }

        /** finds BOL on current line */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {

            int offset = reqPos - bufferStartPos;
            while (offset < offset2) {
                if (buffer[offset] == '\t') {
                    visCol = (visCol + tabSize) / tabSize * tabSize;
                } else {
                    visCol++;
                }
                offset++;
            }
            return bufferStartPos + offset;
        }

    }

    /** Finder for getting position from visual column knowledge.
    * It is kind of reverse finder for <CODE>PosVisColFwdFinder</CODE>.
    * The starting position for find should be the start of particular
    * line. The found position will be that position in document
    * that corresponds to the column position. This method can be used only
    * in case the font is superfixed i.e. all the characters of all
    * font styles have the same width.
    */
    public static final class VisColPosFwdFinder extends AbstractFinder {

        /** Visual column position on line */
        int visCol;

        /** Current visual position as tracked by finder */
        int curVisCol;

        /** Tab size for particular document scanned */
        int tabSize;

        /** Extended UI to get character widths */
        ExtUI extUI;

        /** Set visual column that this finder will try to reach */
        public void setVisCol(int visCol) {
            this.visCol = visCol;
        }

        public void setTabSize(int tabSize) {
            this.tabSize = tabSize;
        }

        /** Mark that first call will follow */
        public void reset() {
            super.reset();
            curVisCol = 0;
        }

        /** finds BOL on current line */
        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {

            int offset = reqPos - bufferStartPos;
            while (offset < offset2) {
                if (curVisCol >= visCol) {
                    found = true;
                    return bufferStartPos + offset;
                }

                switch (buffer[offset]) {
                case '\t':
                    curVisCol = (curVisCol + tabSize) / tabSize * tabSize;
                    break;
                case '\n':
                    found = true;
                    return bufferStartPos + offset;
                default:
                    curVisCol++;
                }
                offset++;
            }
            return bufferStartPos + offset;
        }

    }

    /** Generic forward finder that simplifies the search process. */
    public static abstract class GenericFwdFinder extends AbstractFinder {

        public final int find(int bufferStartPos, char buffer[],
                              int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            int limitOffset = limitPos - bufferStartPos - 1;
            while (offset >= offset1 && offset < offset2) {
                offset += scan(buffer[offset], (offset == limitOffset));
                if (found) {
                    break;
                }
            }
            return bufferStartPos + offset;
        }

        /** This function decides if it found a desired string or not.
        * The function receives currently searched character and flag if it's
        * the last one that is searched or not.
        * @return if the function decides that
        * it found a desired string it sets <CODE>found = true</CODE> and returns
        * how many characters back the searched string begins in forward
        * direction (0 stands for current character).
        * For example if the function looks for word 'yes' and it gets
        * 's' as parameter it sets found = true and returns -2.
        * If the string is not yet found it returns how many characters it should go
        * in forward direction (in this case it would usually be 1).
        * The next searched character will be that one requested.
        */
        protected abstract int scan(char ch, boolean lastChar);

    }

    /** Generic backward finder that simplifies the search process. */
    public static abstract class GenericBwdFinder extends AbstractFinder {

        public final int find(int bufferStartPos, char buffer[],
                              int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            int limitOffset = limitPos - bufferStartPos;
            while (offset >= offset1 && offset < offset2) {
                offset += scan(buffer[offset], (offset == limitOffset));
                if (found) {
                    break;
                }
            }
            return bufferStartPos + offset;
        }

        /** This function decides if it found a desired string or not.
        * The function receives currently searched character and flag if it's
        * the last one that is searched or not.
        * @return if the function decides that
        * it found a desired string it sets <CODE>found = true</CODE> and returns
        * how many characters back the searched string begins in backward
        * direction (0 stands for current character). It is usually 0 as the
        * finder usually decides after the last required character but it's
        * not always the case e.g. for whole-words-only search it can be 1 or so.
        * If the string is not yet found it returns how many characters it should go
        * in backward direction (in this case it would usually be -1).
        * The next searched character will be that one requested.
        */
        protected abstract int scan(char ch, boolean lastChar);

    }

    public static abstract class GenericFinder extends AbstractFinder {

        /** Flag that determines whether the search is in the forward direction */
        protected boolean forward;

        public final int find(int bufferStartPos, char buffer[],
                              int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            int limitOffset = limitPos - bufferStartPos;
            if (forward) {
                limitOffset--; // decrease limit offset for the forward search
            }
            while (offset >= offset1 && offset < offset2) {
                offset += scan(buffer[offset], (offset == limitOffset));
                if (found) {
                    break;
                }
            }
            return bufferStartPos + offset;
        }

        /** The method that gets the actual character and whether
        * that character is the last in the search. It can
        * generally set the found flag to true to signal the successive
        * search or it can return positive number to go forward
        * or negative number to go back.
        */
        protected abstract int scan(char ch, boolean lastChar);
    }

    /** Searches for the specified char in forward direction. */
    public static class CharFwdFinder extends GenericFwdFinder {

        char searchChar;

        public CharFwdFinder(char searchChar) {
            this.searchChar = searchChar;
        }

        protected int scan(char ch, boolean lastChar) {
            if (ch == searchChar) {
                found = true;
                return 0;
            }
            return +1;
        }

    }

    /** Searches for the specified char in backward direction. */
    public static class CharBwdFinder extends GenericBwdFinder {

        char searchChar;

        public CharBwdFinder(char searchChar) {
            this.searchChar = searchChar;
        }

        protected int scan(char ch, boolean lastChar) {
            if (ch == searchChar) {
                found = true;
                return 0;
            }
            return -1;
        }

    }

    /** Searches for anyone of the specified chars in forward direction. */
    public static class CharArrayFwdFinder extends GenericFwdFinder {

        char searchChars[];

        char foundChar;

        public CharArrayFwdFinder(char searchChars[]) {
            this.searchChars = searchChars;
        }

        protected int scan(char ch, boolean lastChar) {
            for (int i = 0; i < searchChars.length; i++) {
                if (ch == searchChars[i]) {
                    foundChar = searchChars[i];
                    found = true;
                    return 0;
                }
            }
            return +1;
        }

        public char getFoundChar() {
            return foundChar;
        }

    }

    public static class AcceptorFwdFinder extends GenericFwdFinder {

        Acceptor a;

        public AcceptorFwdFinder(Acceptor a) {
            this.a = a;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!a.accept(ch)) {
                found = true;
                return 0;
            }
            return +1;
        }

    }

    /** Searches for anyone of the specified chars in backward direction. */
    public static class CharArrayBwdFinder extends GenericBwdFinder {

        char searchChars[];

        char foundChar;

        public CharArrayBwdFinder(char searchChars[]) {
            this.searchChars = searchChars;
        }

        protected int scan(char ch, boolean lastChar) {
            for (int i = 0; i < searchChars.length; i++) {
                if (ch == searchChars[i]) {
                    foundChar = searchChars[i];
                    found = true;
                    return 0;
                }
            }
            return -1;
        }

        public char getFoundChar() {
            return foundChar;
        }

    }

    public static class AcceptorBwdFinder extends GenericBwdFinder {

        Acceptor a;

        public AcceptorBwdFinder(Acceptor a) {
            this.a = a;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!a.accept(ch)) {
                found = true;
                return 0;
            }
            return -1;
        }

    }

    /** Next word forward finder */
    public static class NextWordFwdFinder extends GenericFwdFinder {

        /** Document used to recognize the character types */
        BaseDocument doc;

        /** Currently inside whitespace */
        boolean inWhitespace;

        /** Currently inside identifier */
        boolean inIdentifier;

        /** Currently inside not in word and not in whitespace */
        boolean inPunct;

        /** Whether scanning the first character */
        boolean firstChar;

        /** Whether stop on EOL */
        boolean stopOnEOL;

        /** Stop with successful find on the first white character */
        boolean stopOnWhitespace;

        public NextWordFwdFinder(BaseDocument doc, boolean stopOnEOL, boolean stopOnWhitespace) {
            this.doc = doc;
            this.stopOnEOL = stopOnEOL;
            this.stopOnWhitespace = stopOnWhitespace;
        }

        public void reset() {
            super.reset();
            inWhitespace = false;
            inIdentifier = false;
            inPunct = false;
            firstChar = true;
        }

        protected int scan(char ch, boolean lastChar) {
            if (stopOnEOL) {
                if (ch == '\n') {
                    found = true;
                    return firstChar ? 1 : 0;
                }
                firstChar = false;
            }

            if (doc.isWhitespace(ch)) { // whitespace char found
                if (stopOnWhitespace) {
                    found = true;
                    return 0;
                } else {
                    inWhitespace = true;
                    return 1;
                }
            }

            if (inWhitespace) {
                found = true;
                return 0;
            }
            if (inIdentifier) { // inside word
                if (doc.isIdentifierPart(ch)) { // still in word
                    return 1;
                }
                found = true;
                return 0; // found punct
            }
            if (inPunct) { // inside punctuation
                if (doc.isIdentifierPart(ch)) { // a word starts after punct
                    found = true;
                    return 0;
                }
                return 1; // still in punct
            }

            // just starting - no state assigned yet
            if (doc.isIdentifierPart(ch)) {
                inIdentifier = true;
                return 1;
            } else {
                inPunct = true;
                return 1;
            }
        }

    }

    /** Find start of the word. This finder can be used to go to previous
    * word or to the start of the current word.
    */
    public static class PreviousWordBwdFinder extends GenericBwdFinder {

        BaseDocument doc;

        /** Currently inside identifier */
        boolean inIdentifier;

        /** Currently inside not in word and not in whitespace */
        boolean inPunct;

        /** Stop on EOL */
        boolean stopOnEOL;

        /** Stop with successful find on the first white character */
        boolean stopOnWhitespace;

        boolean firstChar;

        public PreviousWordBwdFinder(BaseDocument doc, boolean stopOnEOL, boolean stopOnWhitespace) {
            this.doc = doc;
            this.stopOnEOL = stopOnEOL;
            this.stopOnWhitespace = stopOnWhitespace;
        }

        public void reset() {
            super.reset();
            inIdentifier = false;
            inPunct = false;
            firstChar = true;
        }

        protected int scan(char ch, boolean lastChar) {
            if (stopOnEOL) {
                if (ch == '\n') {
                    found = true;
                    return firstChar ? 0 : 1;
                }
                firstChar = false;
            }

            if (inIdentifier) { // inside word
                if (doc.isIdentifierPart(ch)) {
                    if (lastChar) {
                        found = true;
                        return 0;
                    }
                    return -1;
                }
                found = true;
                return 1; // found punct or whitespace
            }
            if (inPunct) { // inside punctuation
                if (doc.isIdentifierPart(ch) || doc.isWhitespace(ch) || lastChar) {
                    found = true;
                    return 1;
                }
                return -1; // still in punct
            }
            if (doc.isWhitespace(ch)) {
                if (stopOnWhitespace) {
                    found = true;
                    return 1;
                }
                return -1;
            }
            if (doc.isIdentifierPart(ch)) {
                inIdentifier = true;
                if (lastChar) {
                    found = true;
                    return 0;
                }
                return -1;
            }
            inPunct = true;
            return -1;
        }

    }

    /** Find first non-white character forward */
    public static class WhiteFwdFinder extends GenericFwdFinder {

        BaseDocument doc;

        public WhiteFwdFinder(BaseDocument doc) {
            this.doc = doc;
        }

        protected int scan(char ch, boolean lastChar) {
            if (doc.isWhitespace(ch)) {
                found = true;
                return 0;
            }
            return 1;
        }
    }

    /** Find first non-white character backward */
    public static class WhiteBwdFinder extends GenericBwdFinder {

        BaseDocument doc;

        public WhiteBwdFinder(BaseDocument doc) {
            this.doc = doc;
        }

        protected int scan(char ch, boolean lastChar) {
            if (doc.isWhitespace(ch)) {
                found = true;
                return 0;
            }
            return -1;
        }
    }

    /** Find first non-white character forward */
    public static class NonWhiteFwdFinder extends GenericFwdFinder {

        BaseDocument doc;

        public NonWhiteFwdFinder(BaseDocument doc) {
            this.doc = doc;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!doc.isWhitespace(ch)) {
                found = true;
                return 0;
            }
            return 1;
        }
    }

    /** Find first non-white character backward */
    public static class NonWhiteBwdFinder extends GenericBwdFinder {

        BaseDocument doc;

        public NonWhiteBwdFinder(BaseDocument doc) {
            this.doc = doc;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!doc.isWhitespace(ch)) {
                found = true;
                return 0;
            }
            return -1;
        }
    }

    /** String forward finder */
    public static final class StringFwdFinder extends GenericFwdFinder
        implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        public StringFwdFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            stringInd = 0;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }
            if (ch == chars[stringInd]) {
                stringInd++;
                if (stringInd == chars.length) { // found whole string
                    found = true;
                    return 1 - stringInd; // how many chars back the string starts
                }
                return 1; // successfully matched char, go to next char
            } else {
                if (stringInd == 0) {
                    return 1;
                } else {
                    int back = 1 - stringInd;
                    stringInd = 0;
                    return back;
                }
            }
        }

    }

    /** String backward finder */
    public static class StringBwdFinder extends GenericBwdFinder
        implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        int endInd;

        public StringBwdFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            endInd = chars.length - 1;
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            stringInd = endInd;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }
            if (ch == chars[stringInd]) {
                stringInd--;
                if (stringInd == -1) {
                    found = true;
                    return 0;
                }
                return -1;
            } else {
                if (stringInd == endInd) {
                    return -1;
                } else {
                    int back = chars.length - 2 - stringInd;
                    stringInd = endInd;
                    return back;
                }
            }
        }

    }

    /** String forward finder that finds whole words only.
    * There are some speed optimizations attempted.
    */
    public static final class WholeWordsFwdFinder extends GenericFwdFinder
        implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        BaseDocument doc;

        boolean insideWord;

        boolean firstCharWordPart;

        boolean wordFound;

        public WholeWordsFwdFinder(BaseDocument doc, String s, boolean matchCase) {
            this.doc = doc;
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            firstCharWordPart = doc.isIdentifierPart(chars[0]);
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            insideWord = false;
            wordFound = false;
            stringInd = 0;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }

            // whole word already found but must verify next char
            if (wordFound) {
                if (doc.isIdentifierPart(ch)) { // word continues
                    wordFound = false;
                    insideWord = firstCharWordPart;
                    stringInd = 0;
                    return 1 - chars.length;
                } else {
                    found = true;
                    return -chars.length;
                }
            }

            if (stringInd == 0) { // special case for first char
                if (ch != chars[0] || insideWord) { // first char doesn't match
                    insideWord = doc.isIdentifierPart(ch);
                    return 1;
                } else { // first char matches
                    stringInd = 1; // matched and not inside word
                    if (chars.length == 1) {
                        if (lastChar) {
                            found = true;
                            return 0;
                        } else {
                            wordFound = true;
                            return 1;
                        }
                    }
                    return 1;
                }
            } else { // already matched at least one char
                if (ch == chars[stringInd]) { // matches current char
                    stringInd++;
                    if (stringInd == chars.length) { // found whole string
                        if (lastChar) {
                            found = true;
                            return 1 - chars.length; // how many chars back the string starts
                        } else {
                            wordFound = true;
                            return 1;
                        }
                    }
                    return 1; // successfully matched char, go to next char
                } else { // current char doesn't match, stringInd > 0
                    int back = 1 - stringInd;
                    stringInd = 0;
                    insideWord = firstCharWordPart;
                    return back; // go back to search from the next to first char
                }
            }
        }

    }

    /** String backward finder that finds whole words only.
    * There are some speed optimizations attemted.
    */
    public static final class WholeWordsBwdFinder extends GenericBwdFinder
        implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        boolean insideWord;

        boolean lastCharWordPart;

        boolean wordFound;

        int endInd;

        BaseDocument doc;

        public WholeWordsBwdFinder(BaseDocument doc, String s, boolean matchCase) {
            this.doc = doc;
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            endInd = chars.length - 1;
            doc.isIdentifierPart(chars[endInd]);
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            insideWord = false;
            wordFound = false;
            stringInd = endInd;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }

            // whole word already found but must verify next char
            if (wordFound) {
                if (doc.isIdentifierPart(ch)) { // word continues
                    wordFound = false;
                    insideWord = lastCharWordPart;
                    stringInd = endInd;
                    return endInd;
                } else {
                    found = true;
                    return 1;
                }
            }

            if (stringInd == endInd) { // special case for last char
                if (ch != chars[endInd] || insideWord) { // first char doesn't match
                    insideWord = doc.isIdentifierPart(ch);
                    return -1;
                } else { // first char matches
                    stringInd = endInd - 1; // matched and not inside word
                    if (chars.length == 1) {
                        if (lastChar) {
                            found = true;
                            return 0;
                        } else {
                            wordFound = true;
                            return -1;
                        }
                    }
                    return -1;
                }
            } else { // already matched at least one char
                if (ch == chars[stringInd]) { // matches current char
                    stringInd--;
                    if (stringInd == -1) { // found whole string
                        if (lastChar) {
                            found = true;
                            return 0;
                        } else {
                            wordFound = true;
                            return -1;
                        }
                    }
                    return -1; // successfully matched char, go to next char
                } else { // current char doesn't match, stringInd > 0
                    int back = chars.length - 2 - stringInd;
                    stringInd = endInd;
                    insideWord = lastCharWordPart;
                    return back;
                }
            }
        }
    }

    /** Support for creating blocks finders. */
    public static abstract class AbstractBlocksFinder extends AbstractFinder
        implements BlocksFinder {

        private static int[] EMPTY_INT_ARRAY = new int[0];

        private int[] blocks = EMPTY_INT_ARRAY;

        private int blocksInd;

        private boolean closed;

        public void reset() {
            blocksInd = 0;
            closed = false;
        }

        public int[] getBlocks() {
            if (!closed) { // not closed yet
                closeBlocks();
                closed = true;
            }
            return blocks;
        }

        public void setBlocks(int[] blocks) {
            this.blocks = blocks;
            closed = false;
        }

        protected void addBlock(int blkStartPos, int blkEndPos) {
            if (blocksInd == blocks.length) {
                int[] dbl = new int[blocks.length * 2];
                System.arraycopy(blocks, 0, dbl, 0, blocks.length);
                blocks = dbl;
            }
            blocks[blocksInd++] = blkStartPos;
            blocks[blocksInd++] = blkEndPos;
        }

        /** Insert closing sequence [-1, -1] */
        protected void closeBlocks() {
            addBlock(-1, -1);
        }

        public String debugBlocks() {
            StringBuffer buf = new StringBuffer();
            int ind = 0;
            while (blocks[ind] != -1) {
                buf.append((ind/2 + 1) + ": [" + blocks[ind] + ", " + blocks[ind + 1] + "]\n"); // NOI18N
                ind+= 2;
            }
            return buf.toString();
        }

    }

    public static final class FalseBlocksFinder extends AbstractBlocksFinder {

        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            return -1;
        }

    }

    /** String forward finder that creates position blocks */
    public static final class StringBlocksFinder
        extends AbstractBlocksFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        public StringBlocksFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
        }

        public void reset() {
            super.reset();
            stringInd = 0;
        }

        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            while (offset >= offset1 && offset < offset2) {
                char ch = buffer[offset];

                if (!matchCase) {
                    ch = Character.toLowerCase(ch);
                }
                if (ch == chars[stringInd]) {
                    stringInd++;
                    if (stringInd == chars.length) {
                        int blkEnd = bufferStartPos + offset + 1;
                        addBlock(blkEnd - stringInd, blkEnd);
                        stringInd = 0;
                    }
                    offset++;
                } else {
                    offset += 1 - stringInd;
                    stringInd = 0;
                }

            }
            reqPos = bufferStartPos + offset;
            return reqPos;
        }

    }

    /** String forward finder that finds whole words only
    * and that creates position blocks.
    * There are some speed optimizations attempted.
    */
    public static final class WholeWordsBlocksFinder extends AbstractBlocksFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        boolean insideWord;

        boolean firstCharWordPart;

        boolean wordFound;

        BaseDocument doc;

        public WholeWordsBlocksFinder(BaseDocument doc, String s, boolean matchCase) {
            this.doc = doc;
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            firstCharWordPart = doc.isIdentifierPart(chars[0]);
        }

        public void reset() {
            super.reset();
            insideWord = false;
            wordFound = false;
            stringInd = 0;
        }

        public int find(int bufferStartPos, char buffer[],
                        int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            int limitOffset = limitPos - bufferStartPos - 1;
            while (offset >= offset1 && offset < offset2) {
                char ch = buffer[offset];

                if (!matchCase) {
                    ch = Character.toLowerCase(ch);
                }

                // whole word already found but must verify next char
                if (wordFound) {
                    if (doc.isIdentifierPart(ch)) { // word continues
                        insideWord = firstCharWordPart;
                        offset -= chars.length - 1;
                    } else {
                        int blkEnd = bufferStartPos + offset;
                        addBlock(blkEnd - chars.length, blkEnd);
                        insideWord = false;
                        offset++;
                    }
                    wordFound = false;
                    stringInd = 0;
                    continue;
                }

                if (stringInd == 0) { // special case for first char
                    if (ch != chars[0] || insideWord) { // first char doesn't match
                        insideWord = doc.isIdentifierPart(ch);
                        offset++;
                    } else { // first char matches
                        stringInd = 1; // matched and not inside word
                        if (chars.length == 1) {
                            if (offset == limitOffset) {
                                int blkStart = bufferStartPos + offset;
                                addBlock(blkStart, blkStart + 1);
                            } else {
                                wordFound = true;
                            }
                        }
                        offset++;
                    }
                } else { // already matched at least one char
                    if (ch == chars[stringInd]) { // matches current char
                        stringInd++;
                        if (stringInd == chars.length) { // found whole string
                            if (offset == limitOffset) {
                                int blkEnd = bufferStartPos + 1;
                                addBlock(blkEnd - stringInd, blkEnd);
                            } else {
                                wordFound = true;
                            }
                        }
                        offset++;
                    } else { // current char doesn't match, stringInd > 0
                        offset += 1 - stringInd;
                        stringInd = 0;
                        insideWord = firstCharWordPart;
                    }
                }

            }
            reqPos = bufferStartPos + offset;
            return reqPos;
        }

    }

    /** Finder that looks for some search expression expressed by string.
    * It can be either simple string
    * or some form of regular expression expressed by string.
    */
    public interface StringFinder extends Finder {

        /** Get the length of the found string. This is useful
        * for regular expressions, because the length of the regular
        * expression can be different than the length of the string
        * that matched the expression.
        */
        public int getFoundLength();

    }

    /** Finder that constructs [begin-pos, end-pos] blocks.
    * This is useful for highlight-search draw layer.
    * The block-finders are always forward-search finders.
    */
    public interface BlocksFinder extends Finder {

        /** Set the array into which the finder puts
        * the position blocks. If the length of array is not sufficient
        * the finder extends the array. The last block is set to [-1, -1].
        */
        public void setBlocks(int[] blocks);

        /** Get the array filled with position blocks. It is either
        * original array passed to setBlocks() or the new array
        * if the finder extended the array.
        */
        public int[] getBlocks();

    }


}

/*
 * Log
 *  20   Gandalf   1.19        1/13/00  Miloslav Metelka 
 *  19   Gandalf   1.18        1/7/00   Miloslav Metelka 
 *  18   Gandalf   1.17        1/6/00   Miloslav Metelka 
 *  17   Gandalf   1.16        1/4/00   Miloslav Metelka 
 *  16   Gandalf   1.15        11/8/99  Miloslav Metelka 
 *  15   Gandalf   1.14        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  14   Gandalf   1.13        8/17/99  Miloslav Metelka 
 *  13   Gandalf   1.12        7/21/99  Miloslav Metelka 
 *  12   Gandalf   1.11        6/10/99  Miloslav Metelka 
 *  11   Gandalf   1.10        6/8/99   Miloslav Metelka 
 *  10   Gandalf   1.9         6/1/99   Miloslav Metelka 
 *  9    Gandalf   1.8         5/13/99  Miloslav Metelka 
 *  8    Gandalf   1.7         5/5/99   Miloslav Metelka 
 *  7    Gandalf   1.6         4/23/99  Miloslav Metelka Undo added and internal 
 *       improvements
 *  6    Gandalf   1.5         4/8/99   Miloslav Metelka 
 *  5    Gandalf   1.4         4/1/99   Miloslav Metelka 
 *  4    Gandalf   1.3         3/30/99  Miloslav Metelka 
 *  3    Gandalf   1.2         3/27/99  Miloslav Metelka 
 *  2    Gandalf   1.1         3/18/99  Miloslav Metelka 
 *  1    Gandalf   1.0         2/3/99   Miloslav Metelka 
 * $
 */

