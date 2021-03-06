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

public interface HTMLTokenTypes {
    public static final int EOF = 1;
    public static final int NULL_TREE_LOOKAHEAD = 3;
    public static final int PCDATA = 4;
    public static final int DOCTYPE = 5;
    public static final int OHTML = 6;
    public static final int CHTML = 7;
    public static final int OHEAD = 8;
    public static final int CHEAD = 9;
    public static final int ISINDEX = 10;
    public static final int BASE = 11;
    public static final int META = 12;
    public static final int LINK = 13;
    public static final int OTITLE = 14;
    public static final int CTITLE = 15;
    public static final int OSCRIPT = 16;
    public static final int CSCRIPT = 17;
    public static final int OSTYLE = 18;
    public static final int CSTYLE = 19;
    public static final int OBODY = 20;
    public static final int CBODY = 21;
    public static final int ADDRESS = 22;
    public static final int HR = 23;
    public static final int IMG = 24;
    public static final int BFONT = 25;
    public static final int BR = 26;
    public static final int OH1 = 27;
    public static final int CH1 = 28;
    public static final int OH2 = 29;
    public static final int CH2 = 30;
    public static final int OH3 = 31;
    public static final int CH3 = 32;
    public static final int OH4 = 33;
    public static final int CH4 = 34;
    public static final int OH5 = 35;
    public static final int CH5 = 36;
    public static final int OH6 = 37;
    public static final int CH6 = 38;
    public static final int OADDRESS = 39;
    public static final int CADDRESS = 40;
    public static final int OPARA = 41;
    public static final int CPARA = 42;
    public static final int OULIST = 43;
    public static final int CULIST = 44;
    public static final int OOLIST = 45;
    public static final int COLIST = 46;
    public static final int ODLIST = 47;
    public static final int CDLIST = 48;
    public static final int OLITEM = 49;
    public static final int CLITEM = 50;
    public static final int ODTERM = 51;
    public static final int CDTERM = 52;
    public static final int ODDEF = 53;
    public static final int ODIR = 54;
    public static final int CDIR = 55;
    public static final int OMENU = 56;
    public static final int CMENU = 57;
    public static final int OPRE = 58;
    public static final int CPRE = 59;
    public static final int ODIV = 60;
    public static final int CDIV = 61;
    public static final int OCENTER = 62;
    public static final int CCENTER = 63;
    public static final int OBQUOTE = 64;
    public static final int CBQUOTE = 65;
    public static final int OFORM = 66;
    public static final int CFORM = 67;
    public static final int OTABLE = 68;
    public static final int CTABLE = 69;
    public static final int OCAP = 70;
    public static final int CCAP = 71;
    public static final int O_TR = 72;
    public static final int C_TR = 73;
    public static final int O_TH_OR_TD = 74;
    public static final int C_TH_OR_TD = 75;
    public static final int OTTYPE = 76;
    public static final int CTTYPE = 77;
    public static final int OITALIC = 78;
    public static final int CITALIC = 79;
    public static final int OBOLD = 80;
    public static final int CBOLD = 81;
    public static final int OUNDER = 82;
    public static final int CUNDER = 83;
    public static final int OSTRIKE = 84;
    public static final int CSTRIKE = 85;
    public static final int OBIG = 86;
    public static final int CBIG = 87;
    public static final int OSMALL = 88;
    public static final int CSMALL = 89;
    public static final int OSUB = 90;
    public static final int CSUB = 91;
    public static final int OSUP = 92;
    public static final int CSUP = 93;
    public static final int OEM = 94;
    public static final int CEM = 95;
    public static final int OSTRONG = 96;
    public static final int CSTRONG = 97;
    public static final int ODEF = 98;
    public static final int CDEF = 99;
    public static final int OCODE = 100;
    public static final int CCODE = 101;
    public static final int OSAMP = 102;
    public static final int CSAMP = 103;
    public static final int OKBD = 104;
    public static final int CKBD = 105;
    public static final int OVAR = 106;
    public static final int CVAR = 107;
    public static final int OCITE = 108;
    public static final int CCITE = 109;
    public static final int INPUT = 110;
    public static final int OSELECT = 111;
    public static final int CSELECT = 112;
    public static final int SELOPT = 113;
    public static final int OTAREA = 114;
    public static final int CTAREA = 115;
    public static final int OANCHOR = 116;
    public static final int CANCHOR = 117;
    public static final int OAPPLET = 118;
    public static final int APARAM = 119;
    public static final int CAPPLET = 120;
    public static final int OFONT = 121;
    public static final int CFONT = 122;
    public static final int OMAP = 123;
    public static final int AREA = 124;
    public static final int CMAP = 125;
    public static final int CDDEF = 126;
    public static final int CDIR_OR_CDIV = 127;
    public static final int OSTRIKE_OR_OSTRONG = 128;
    public static final int CST_LEFT_FACTORED = 129;
    public static final int CSUB_OR_CSUP = 130;
    public static final int ODFN = 131;
    public static final int CDFN = 132;
    public static final int APPLET = 133;
    public static final int APARM = 134;
    public static final int CFORM_OR_CFONT = 135;
    public static final int BFONT_OR_BASE = 136;
    public static final int COMMENT_DATA = 137;
    public static final int COMMENT = 138;
    public static final int WS = 139;
    public static final int ATTR = 140;
    public static final int WORD = 141;
    public static final int STRING = 142;
    public static final int WSCHARS = 143;
    public static final int SPECIAL = 144;
    public static final int HEXNUM = 145;
    public static final int INT = 146;
    public static final int HEXINT = 147;
    public static final int DIGIT = 148;
    public static final int HEXDIGIT = 149;
    public static final int LCLETTER = 150;
    public static final int UNDEFINED_TOKEN = 151;
}
