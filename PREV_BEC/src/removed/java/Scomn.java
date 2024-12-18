package removed.java;

public class Scomn {
//	 Global SCOMN("iAPX");
//	 begin
//	       -----------------------------------------------------------------
//	       ---  COPYRIGHT 1988 by                                        ---
//	       ---  Simula a.s.                                              ---
//	       ---  Oslo, Norway                                             ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---              P O R T A B L E     S I M U L A              ---
//	       ---                                                           ---
//	       ---                   F O R    I B M    P C                   ---
//	       ---                                                           ---
//	       ---                                                           ---
//	       ---           S   -   C   O   M   P   I   L   E   R           ---
//	       ---                                                           ---
//	       ---            G l o b a l   D e f i n i t i o n s            ---
//	       ---                                                           ---
//	       ---  Selection Switches:                                      ---
//	       ---                                                           ---
//	       ---     A - Includes Assembly Output                          ---
//	       ---     C - Includes Consistency Checks                       ---
//	       ---     D - Includes Tracing Dumps                            ---
//	       ---     S - Includes System Generation                        ---
//	       ---     E - Extended mode -- 32-bit 386                       ---
//	       ---     F - Optionally Produce COFF output                    ---
//	       ---     V - New version. (+V:New, -V:Previous)                ---
//	       -----------------------------------------------------------------
//
//	%visible
//
//	Define MxpSymb=64;  -- I.e. 16384 Symbols
//	Define MxpSegm=16;  -- I.e.  4096 Segments Names
//	Define MxpPubl=32;  -- I.e.  8192 Public Definitions
//	Define MxpExtr=32;  -- I.e.  8192 External References
//	Define MxpModl=8;   -- I.e.  2048 Module Names
//	Define MxpTag=64;   -- I.e. 16384 Tags
//	Define MxpFix=64;   -- I.e. 16384 Fixups
//	Define MxpSdest=32; -- I.e.  8192 SDEST per S-Code Switch
//	Define MxpXtag=64;  -- I.e. 16384 Tags in Attribute file
//	Define MxpXtyp=1;   -- I.e.   256 Types in Attribute file
//	Define MxpXsmb=64;  -- I.e. 16384 Symbols in Attribute file
//
//	 Define MaxPnt=63        -- Max number of 'pointers' per SAVE-Object
//	 Define MaxHash=255;     -- Max no.of Hash keys-1  DO NOT CHANGE IT !!!
//	 Define MaxPar=63        -- Max number of routine-parameters
//	 Define MaxParbyte=200   -- Max byte size of routine-call-stack
//
//	 Define MinTag=32        -- First user tag
//	 Define MaxKey=255       -- Max number of file keys
//	 Define MaxType=63       -- Max number of data types
//	 Define MaxByte=255      -- Max value of 8-bit byte  (2**8-1)
//	 Define MaxWord=65535    -- Max value of 16-bit word (2**16-1)
//	 Define MaxSint=32767    -- Max value of SINT        (2**15-1)
//	 Define MaxSdest=8192    -- Max number of dest-in-switch
//	 Define MaxString=32000  -- Max number of chars in a string
//	 Define MaxLine=32000    -- Max number of source lines
//	%+D Define MaxPool=63    -- Max number of pools in trace version
//
//	 Define BufLng=1024;     -- Max body size of :OBJ file records

	public final static int TAG_BOOL  = 1;
	public final static int TAG_CHAR  = 2;
	public final static int	TAG_INT   = 3;
	public final static int TAG_SINT  = 4;
	public final static int TAG_REAL  = 5;
	public final static int TAG_LREAL = 6;
	public final static int TAG_AADDR = 7;
	public final static int TAG_OADDR = 8;
	public final static int TAG_GADDR = 9;
	public final static int TAG_PADDR = 10;
	public final static int TAG_RADDR = 11;
	public final static int TAG_SIZE  = 12;
//
//	 Define T_VOID=0,T_WRD4=1,T_WRD2=2,T_BYT2=3,T_BYT1=4;
//	 Define T_REAL=5,T_LREAL=6,T_TREAL=7,T_BOOL=8,T_CHAR=9;
//	 Define T_SIZE=10,T_OADDR=11,T_AADDR=12,T_GADDR=13,T_PADDR=14,T_RADDR=15
//	 Define T_NPADR=16,T_NOINF=17;
//	 Define T_max=17;  -- Max value of predefined type
//
//	 Define FMF_REAL  = 0; -- 00 0B
//	 Define FMF_LREAL = 4; -- 10 0B
//	 Define FMF_INT   = 2; -- 01 0B
//	 Define FMF_SINT  = 6; -- 11 0B
//	 Define FMF_TEMP  = 3; -- 01 1B  -- Special Case
//
//	 Define O_SSEG=1,O_IDATA=2,O_LDATA=3;
//	 Define O_AEXT=4,O_ASEG=5,O_AFIX=6;
//	 Define O_LINE=7,O_TRC=8,O_END=9;
//	 Define O_max=9;
//
//	%-E Define F_POINTER=3,F_BASE=2,F_OFFSET=1,F_HIBYTE=4,F_LOBYTE=0;
//	 Define aDGRP=0,aDATA=1,aCODE=2,aLINE=3;
//
//	 Define iAPX86=1,iAPX186=2,iAPX286=3,iAPX386=4;
//	 Define NoNPX=0,iAPX87=1,iAPX287=2,iAPX387=3,WTLx167=4;
//
//	 Define oMSDOS     = 0; -- DOS    without numeric coprocessor
//	 Define oMSDOS87   = 1; -- DOS       with 8087 or higher
//	 Define oMSOS2     = 2; -- OS/2      with 8087 or higher
//	 Define oXENIX286  = 3; -- XENIX/286 with 80287 (or emulator) or higher
//	 Define oXENIX386  = 4; -- XENIX/386 with 80287 (or emulator) or higher
//	 Define oXENIX386W = 5; -- XENIX/386 with Weitek 1167 or higher
//	 Define oUNIX386   = 6; -- UNIX/386  with 80287 (or emulator) or higher
//	 Define oUNIX386W  = 7; -- UNIX/386  with Weitek 1167 or higher
//	 Define oSINTRAN   = 8; -- SINTRAN/ND500
//
//	 Define cNONE=0;        -- No C-Binding defined
//	 Define cMS=1;          -- Simula used together with MicroSoft C
//	 Define cTURBO=2;       -- Simula used together with TURBO C
//
//	 Define xGOTO=0,xCALL=1;
//
//	 ------ Symbol Class Codes ------
//	 Define sSYMB=0,sEXTR=1,sPUBL=2,sSEGM=3,sMODL=4;
//	 Define sMAX=4;
//	%page
//
//	%-E ------ SBI-register packing in Operand ------
//	%-E
//	%-E Define oES=128;    -- 80H=1000 0000  ES:
//	%-E Define oCS=144;    -- 90H=1001 0000  CS:
//	%-E Define oSS=160;    -- A0H=1010 0000  SS:
//	%-E Define oDS=176;    -- B0H=1011 0000  DS:
//	%-E Define oSREG=240;  -- F0H=1111 0000
//	%-E Define biBXSI=0,rmBXSI=8;     -- 08H=0000 1000  [BX]+[SI]
//	%-E Define biBXDI=1,rmBXDI=9;     -- 09H=0000 1001  [BX]+[DI]
//	%-E Define biBPSI=2,rmBPSI=10;    -- 0AH=0000 1010  [BP]+[SI]
//	%-E Define biBPDI=3,rmBPDI=11;    -- 0BH=0000 1011  [BP]+[DI]
//	%-E Define biSI=4,rmSI=12;        -- 0CH=0000 1100  [SI]
//	%-E Define biDI=5,rmDI=13;        -- 0DH=0000 1101  [DI]
//	%-E Define biBP=6,rmBP=14;        -- 0EH=0000 1110  [BP]
//	%-E Define biBX=7,rmBX=15;        -- 0FH=0000 1111  [BX]
//	%-E Define biBIREG=7,rmBIREG=15;  -- 0FH=0000 1111
//
//	%+E ------ SIB-register packing in Operand ------
//	%+E --
//	%+E --     ireg=iESP                 ==>  no index register
//	%+E --     breg=ireg & breg <> bESP  ==>  no base register
//	%+E --     It is impossible to specify same base and index register.
//	%+E
//	%+E Define iEAX=0, bEAX=0;        --   0=00 000 000   0=00 000 000
//	%+E Define iECX=8, bECX=1;        --   8=00 001 000   1=00 000 001
//	%+E Define iEDX=16,bEDX=2;        --  16=00 010 000   2=00 000 010
//	%+E Define iEBX=24,bEBX=3;        --  24=00 011 000   3=00 000 011
//	%+E Define iESP=32,bESP=4;        --  32=00 100 000   4=00 000 100
//	%+E Define iEBP=40,bEBP=5;        --  40=00 101 000   5=00 000 101
//	%+E Define iESI=48,bESI=6;        --  48=00 110 000   6=00 000 110
//	%+E Define iEDI=56,bEDI=7;        --  56=00 111 000   7=00 000 111
//	%+E Define ssMASK=192;            -- 192=11 000 000
//	%+E Define IndxREG=56,BaseREG=7;  --  56=00 111 000   7=00 000 111
//	%+E Define NoIBREG=228;           -- 228=11 100 100   IS RULED OUT.
//	%+E Define NoIREG=32;             --  32=00 100 000   IS RULED OUT.
//	%page
//	%page
//
//	---------     O b j e c t   K i n d   C o d e s      ---------
//
//	 Define K_Qfrm1=1,K_Qfrm2=2,K_Qfrm2b=3,K_Qfrm3=4;
//	 Define K_Qfrm4=5,K_Qfrm4b=6,K_Qfrm4c=7,K_Qfrm5=8,K_Qfrm6=9;
//	 --- Descriptors ---
//	 Define K_RecordDescr=10,K_TypeRecord=11,K_Attribute=12;
//	 Define K_Import=13,K_Export=14,K_LocalVar=15;
//	 Define K_GlobalVar=16,K_ExternVar=17;
//	 Define K_ProfileDescr=18,K_IntRoutine=19,K_ExtRoutine=20;
//	 Define K_IntLabel=21,K_ExtLabel=22,K_SwitchDescr=23;
//	 --- Stack Items ---
//	 Define K_ProfileItem=24,K_Address=25,K_Temp=26,K_Coonst=27;
//	 Define K_Result=28;
//	 --- Arrays etc. ---
//	 Define K_RefBlock=29,K_WordBlock=30,K_AddrBlock=31;
//	 --- Others ---
//	 Define K_BSEG=32;
//
//	 Define K_Max=33;  -- Max value of object kind codes  + 1
//	%page
//
//	---------     P r o f i l e    K i n d    C o d e s      ---------
//
//	 Define P_ROUTINE=0      -- Normal local routine profile
//	 Define P_VISIBLE=1      -- Normal visible routine profile
//	 Define P_INTERFACE=2    -- Interface profile
//	 Define P_SYSTEM=3       -- System routine (not inline)
//	 Define P_KNOWN=4        -- Known routine (not inline)
//	 Define P_OS2=5          -- MS-OS2 routine (partially inline)
//	 Define P_XNX=6          -- UNIX/XENIX C-lib routine (partially inline)
//	 Define P_KNL=7          -- UNIX/XENIX Kernel routine (partially inline)
//	 Define P_EXTERNAL=8     -- External <unknown> routine
//	 Define P_SIMULETTA=9    -- External SIMULETTA routine
//	 Define P_ASM=10         -- External ASSEMBLY routine
//	 Define P_C=11           -- External C routine
//	 Define P_FTN=12         -- External FORTRAN routine
//	 Define P_PASCAL=13      -- External PASCAL routine
//	 --     14 .. 19         -- Reserved
//
//	 Define P_GTOUTM=20      -- Sysroutine("GTOUTM")
//	 Define P_MOVEIN=21      -- Sysroutine("MOVEIN")
//
//	 Define P_RSQROO=22      -- Sysroutine ("RSQROO")
//	 Define P_SQROOT=23      -- Sysroutine("SQROOT")
//	 Define P_RLOGAR=24      -- Sysroutine ("RLOGAR")
//	 Define P_LOGARI=25      -- Sysroutine("LOGARI")
//	 Define P_REXPON=26      -- Sysroutine ("REXPON")
//	 Define P_EXPONE=27      -- Sysroutine("EXPONE")
//	 Define P_RSINUS=28      -- Sysroutine("RSINUS")
//	 Define P_SINUSR=29      -- Sysroutine("SINUSR")
//	 Define P_RARTAN=30      -- Sysroutine("RARTAN")
//	 Define P_ARCTAN=31      -- Sysroutine("ARCTAN")
//
//	 Define P_RLOG10=32      -- Known("RLOG10")
//	 Define P_DLOG10=33      -- Known("DLOG10")
//	 Define P_RCOSIN=34      -- Known("RCOSIN")
//	 Define P_COSINU=35      -- Known("COSINU")
//	 Define P_RTANGN=36      -- Known("RTANGN")
//	 Define P_TANGEN=37      -- Known("TANGEN")
//	 Define P_RARCOS=38      -- Known("RARCOS")
//	 Define P_ARCCOS=39      -- Known("ARCCOS")
//	 Define P_RARSIN=40      -- Known("RARSIN")
//	 Define P_ARCSIN=41      -- Known("ARCSIN")
//
//	 Define P_ERRNON=42      -- Known("ERRNON")
//	 Define P_ERRQUA=43      -- Known("ERRQUA")
//	 Define P_ERRSWT=44      -- Known("ERRSWT")
//	 Define P_ERROR=45       -- Known("ERROR")
//
//	 Define P_CBLNK=46       -- Known("CBLNK")
//	 Define P_CMOVE=47       -- Known("CMOVE")
//	 Define P_STRIP=48       -- Known("STRIP")
//	 Define P_TXTREL=49      -- Known("TXTREL")
//	 Define P_TRFREL=50      -- Known("TRFREL")
//
//	 Define P_AR1IND=51      -- Known("AR1IND")
//	 Define P_AR2IND=52      -- Known("AR2IND")
//	 Define P_ARGIND=53      -- Known("ARGIND")
//
//	 Define P_IABS=54        -- Known("IABS")
//	 Define P_RABS=55        -- Known("RABS")
//	 Define P_DABS=56        -- Known("DABS")
//	 Define P_RSIGN=57       -- Known("RSIGN")
//	 Define P_DSIGN=58       -- Known("DSIGN")
//	 Define P_MODULO=59      -- Known("MODULO")
//	 Define P_RENTI=60       -- Known("RENTI")
//	 Define P_DENTI=61       -- Known("DENTI")
//	 Define P_DIGIT=62       -- Known("DIGIT")
//	 Define P_LETTER=63      -- Known("LETTER")
//
//	 Define P_RIPOWR=64      -- Known("RIPOWR")
//	 Define P_RRPOWR=65      -- Known("RRPOWR")
//	 Define P_RDPOWR=66      -- Known("RDPOWR")
//	 Define P_DIPOWR=67      -- Known("DIPOWR")
//	 Define P_DRPOWR=68      -- Known("DRPOWR")
//	 Define P_DDPOWR=69      -- Known("DDPOWR")
//
//	 Define P_DOstatic int S_CREF=70    -- Sysroutine("M?CREF")
//	 Define P_DOstatic int S_OPEN=71    -- Sysroutine("M?OPEN")
//	 Define P_DOstatic int S_CLOSE=72   -- Sysroutine("M?CLOSE")
//	 Define P_DOstatic int S_READ=73    -- Sysroutine("M?READ")
//	 Define P_DOstatic int S_WRITE=74   -- Sysroutine("M?WRITE")
//	 Define P_DOstatic int S_DELF=75    -- Sysroutine("M?DELF")
//	 Define P_DOstatic int S_FPTR=76    -- Sysroutine("M?FPTR")
//	 Define P_DOstatic int S_CDIR=77    -- Sysroutine("M?CDIR")
//	 Define P_DOstatic int S_ALOC=78    -- Sysroutine("M?ALOC")
//	 Define P_DOstatic int S_TERM=79    -- Sysroutine("M?TERM")
//	 Define P_DOstatic int S_TIME=80    -- Sysroutine("M?TIME")
//	 Define P_DOstatic int S_DATE=81    -- Sysroutine("M?DATE")
//	 Define P_DOstatic int S_VERS=82    -- Sysroutine("M?VERS")
//	 Define P_DOstatic int S_EXEC=83    -- Sysroutine("M?EXEC")
//	 Define P_DOstatic int S_IOCTL=84   -- Sysroutine("M?IOCTL")
//	 Define P_DOstatic int S_LOCK=85    -- Sysroutine("M?LOCK")
//	 Define P_DOstatic int S_GDRV=86    -- Sysroutine("M?GDRV")
//	 Define P_DOstatic int S_GDIR=87    -- Sysroutine("M?GDIR")
//
//	 Define P_APX_SCMPEQ=88  -- Sysroutine("S?SCMPEQ")
//	 Define P_APX_SMOVEI=89  -- Sysroutine("S?MOVEI")
//	 Define P_APX_SMOVED=90  -- Sysroutine("S?MOVED")
//	 Define P_APX_SSKIP=91   -- Sysroutine("S?SKIP")
//	 Define P_APX_STRIP=92   -- Sysroutine("S?TRIP")
//	 Define P_APX_SFINDI=93  -- Sysroutine("S?FINDI")
//	 Define P_APX_SFINDD=94  -- Sysroutine("S?FINDD")
//	 Define P_APX_SFILL=95   -- Sysroutine("S?FILL")
//
//	 Define P_APX_BOBY=96    -- Sysroutine("S?BOBY")
//	 Define P_APX_BYBO=97    -- Sysroutine("S?BYBO")
//	 Define P_APX_SZ2W=98    -- Sysroutine("S?SZ2W")
//	 Define P_APX_W2SZ=99    -- Sysroutine("S?W2SZ")
//	 Define P_APX_RF2N=100   -- Sysroutine("S?RF2N")
//	 Define P_APX_N2RF=101   -- Sysroutine("S?N2RF")
//	 Define P_APX_BNOT=102   -- Sysroutine("S?BNOT")
//	 Define P_APX_BAND=103   -- Sysroutine("S?BAND")
//	 Define P_APX_BOR=104    -- Sysroutine("S?BOR")
//	 Define P_APX_BXOR=105   -- Sysroutine("S?BXOR")
//	 Define P_APX_WNOT=106   -- Sysroutine("S?WNOT")
//	 Define P_APX_WAND=107   -- Sysroutine("S?WAND")
//	 Define P_APX_WOR=108    -- Sysroutine("S?WOR")
//	 Define P_APX_WXOR=109   -- Sysroutine("S?WXOR")
//	 Define P_APX_BSHL=110   -- Sysroutine("S?BSHL")
//	 Define P_APX_WSHL=111   -- Sysroutine("S?WSHL")
//	 Define P_APX_BSHR=112   -- Sysroutine("S?BSHR")
//	 Define P_APX_WSHR=113   -- Sysroutine("S?WSHR")
//
//	 Define P_DOstatic int S_SDMODE=114 -- Sysroutine("M?SVDM")
//	 Define P_DOstatic int S_UPDPOS=115 -- Sysroutine("M?UPOS") 
//	 Define P_DOstatic int S_CURSOR=116 -- Sysroutine("M?CURS") 
//	 Define P_DOstatic int S_SDPAGE=117 -- Sysroutine("M?SDPG") 
//	 Define P_DOstatic int S_SROLUP=118 -- Sysroutine("M?SRUP") 
//	 Define P_DOstatic int S_SROLDW=119 -- Sysroutine("M?SRDW") 
//	 Define P_DOstatic int S_GETCEL=120 -- Sysroutine("M?GETC") 
//	 Define P_DOstatic int S_PUTCHR=121 -- Sysroutine("M?PUTC") 
//	 Define P_DOstatic int S_GDMODE=122 -- Sysroutine("M?GVDM") 
//	 Define P_DOstatic int S_SETPAL=123 -- Sysroutine("M?SPAL") 
//
//	 Define P_DOstatic int S_RDCHK=124  -- Sysroutine("M?RCHK") 
//	 Define P_DOstatic int S_KEYIN=125  -- Sysroutine("M?KEYI") 
//
//	 Define P_max=125
//	%title ***  Routine Statistics  ***
//	------  SBASE  ------
//	%+D Define R_DefType=1,R_DICREF=2,R_DICSMB=3,R_DefSymb=4;
//	%+D Define R_DefSegm=5,R_PutSegx=6,R_GetSegx=7,R_DefExtr=8;
//	%+D Define R_PutExtern=9,R_DefPubl=10,R_PutPublic=11,R_NewPubl=12;
//	%+D Define R_DefModl=13,R_PutModule=14,R_GetDefaultSreg=15;
//	%+D Define R_OverrideSreg=16,R_MakeRegmap=17,R_SamePart=18;
//	%+D Define R_RegDies=19,R_RegxAvailable=20,R_IntoDisplay=21,R_GetRec=22;
//	%+D Define R_GetAtr=23,R_GetPrf=24,R_GetRut=25,R_GetLab=26;
//	%+D Define R_NEWOBX=27,R_NEWOBJ=28,R_DELETE=29;
//	------  COASM  ------
//	%+D Define R_iCodeSize=30,R_ShrtJMP=31,R_EmitSOP=32;
//	%+D Define R_EmitCall=33,R_EmitAddr=34,R_EncodeEA=35;
//	%+D Define R_ModifySP=36,R_LoadCnst=37,R_EmitJMP=38,R_QtoI=39;
//	------  MASSAGE  ------
//	%+D Define R_Massage=40,R_PrevQinstr=41;
//	%+D Define R_AppendQinstr=42,R_InsertQinstr=43,R_DeleteQinstr=44;
//	%+D Define R_DeleteQPosibJ=45,R_MoveFdest=46,R_QinstrEqual=47;
//	%+D Define R_OprEqual=48,R_ForwJMP=49,R_DefFDEST=50,R_DefBDEST=51;
//	%+D Define R_RegsWrittenDies=52,R_RegLastused=53,R_RegLastWrite=54;
//	%+D Define R_RegOneshot=55,R_StackModification=56,R_FindPush2=57;
//	%+D Define R_FindPush=58,R_StackEqual=59,R_RegsReadUnmodified=60;
//	%+D Define R_RegUnused=61,R_OperandregsModified=62,R_MemoryUse=63;
//	%+D Define R_MemoryUnused=64,R_MemoryLastused=65,R_mOPR=66;
//	%+D Define R_mPUSHR=67,R_mPUSHM=68,R_mPOPK=69,R_mPOPR=70;
//	%+D Define R_mPOPR2=71,R_mPOPM=72,R_mLOADC=73,R_mLOADSC=74,R_mLOAD=75;
//	%+D Define R_mLDS=76,R_mLES=77,R_mLOADA=78;
//	%+D Define R_mMOV=79,R_mSTORE=80,R_mMONADR=81,R_QinstrBefore=82;
//	%+D Define R_TryReverse=83,R_mDYADR=84,R_mDYADC=85,R_mTRIADR=86;
//	%+D Define R_mTRIADM=87,R_mFDYAD=89,R_mFDYADrev=90;
//	%+D Define R_mCondition=91,R_mJMP=92,R_mFDEST=93;
//	------  CODER  ------
//	%+D Define R_Push=94,R_Precede=95,R_Pop=96,R_TakeTOS=97,R_TakeRef=98;
//	%+D Define R_CopyBSEG=99,R_AssertObjStacked=100;
//	%+D Define R_AssertAtrStacked=101,R_PresaveOprRegs=102,R_GetTosAddr=103;
//	%+D Define R_GetSosAddr=104,R_GetTosValueIn86R3=105;
//	%+D Define R_GetTosValueIn86=106,R_GetTosAdjustedIn86=107;
//	%+D Define R_GetTosAsBYT1=108,R_GetTosAsBYT2=109,R_GetTosAsBYT4=110;
//	%+D Define R_GQfetch=111,R_GQdup=112,R_DupIn86=113,R_GQpop=114;
//	%+D Define R_GetOprAddr=115,R_ArithType=116;
//	------  PARSE  ------
//	%+D Define R_SpecLab=117,R_DefLab=118,R_Viisible=119;
//	%+D Define R_ProgramElement=120,R_Instruction=121,R_CallInstruction=122;
//	%+D Define R_WordsOnStack=123,R_MoveOnStack=124,R_CallDefault=125;
//	%+D Define R_PopExport=126,R_PushExport=127,R_PutPar=128,R_ParType=129;
//	%+D Define R_ConvRepWRD2=130,R_ConvRepWRD4=131,R_IfConstruction=132;
//	%+D Define R_SkipifConstruction=133,R_ProtectConstruction=134;
//	%+D Define R_Max=135;
//
//	%title ******   R e g i s t e r    U s a g e   ******
//	------------  For  MS-DOS   OS/2   and   XENIX   ------------
//
//	%-E Visible Define qAL=0,qCL=1,qDL=2,qBL=3,qAH=4,qCH=5,qDH=6,qBH=7,qAX=8,
//	%-E                qCX=9,qDX=10,qBX=11,qSP=12,qBP=13,qSI=14,qDI=15,
//	%-E                qES=16,qCS=17,qSS=18,qDS=19,qF=20,qM=21,
//	%-E                nregs=22;
//
//	%-E Visible Define qw_B=0, qw_W=8;  -- Instruksjons-"bredde"
//
//	%-E ---                                  MFDE DSBS BBDD CCAA
//	%-E ---                                    SS IIPP HLHL HLHL
//
//	%-E Visible Define uAL=1,     -- 0001H = 0000 0000 0000 0001B
//	%-E                uAH=2,     -- 0002H = 0000 0000 0000 0010B
//	%-E                uAX=3,     -- 0003H = 0000 0000 0000 0011B
//	%-E                uCL=4,     -- 0004H = 0000 0000 0000 0100B
//	%-E                uCH=8,     -- 0008H = 0000 0000 0000 1000B
//	%-E                uCX=12,    -- 000CH = 0000 0000 0000 1100B
//	%-E                uDL=16,    -- 0010H = 0000 0000 0001 0000B
//	%-E                uDH=32,    -- 0020H = 0000 0000 0010 0000B
//	%-E                uDX=48,    -- 0030H = 0000 0000 0011 0000B
//	%-E                uBL=64,    -- 0040H = 0000 0000 0100 0000B
//	%-E                uBH=128,   -- 0080H = 0000 0000 1000 0000B
//	%-E                uBX=192,   -- 00C0H = 0000 0000 1100 0000B
//	%-E                uSP=256,   -- 0100H = 0000 0001 0000 0000B
//	%-E                uBP=512,   -- 0200H = 0000 0010 0000 0000B
//	%-E                uSI=1024,  -- 0400H = 0000 0100 0000 0000B
//	%-E                uDI=2048,  -- 0800H = 0000 1000 0000 0000B
//	%-E                uES=4096,  -- 1000H = 0001 0000 0000 0000B
//	%-E                uDS=8192,  -- 2000H = 0010 0000 0000 0000B
//	%-E                uF =16384, -- 4000H = 0100 0000 0000 0000B
//	%-E                uM =32768, -- 8000H = 1000 0000 0000 0000B
//	%-E             uSPBPM=33536, -- 8300H = 1000 0011 0000 0000B
//	%-E               uALL=65535, -- FFFFH = 1111 1111 1111 1111B
//	%-E          uALLbutBP=65023; -- FDFFH = 1111 1101 1111 1111B
//
//	%-E Visible Const Range(0:MaxWord) uMask(nregs) = (
//	%-E uAL,uCL,uDL,uBL,uAH,uCH,uDH,uBH,uAX,uCX,uDX,uBX,
//	%-E uSP,uBP,uSI,uDI,uES,0  ,0  ,uDS,uF,uM )
//	---                      CS  SS
//
//	%-E Visible Const Range(0:nregs) WordReg(nregs) = (
//	%-E qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,
//	%-E qSP,qBP,qSI,qDI,qES,qCS,qSS,qDS,qF,qM )
//
//	%-E Visible Const Range(0:nregs) WholeReg(nregs) = (
//	%-E qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,
//	%-E qSP,qBP,qSI,qDI,qES,qCS,qSS,qDS,qF,qM )
//
//	%-E Visible Const Range(0:2) RegSize(nregs) =
//	%-E  ( 1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,0,0 )
//
//	%-E Visible Const Range(0:1) wBIT(nregs) =
//	%-E  ( 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0 )
//
//	%-E Visible Const Boolean RegParts(nregs) =
//	%-E  ( false,false,false,false,false,false,false,false,
//	%-E    true, true, true, true, false,false,false,false,
//	%-E    false,false,false,false,false,false )
//
//	%title ******   R e g i s t e r    U s a g e   ******
//	------------   For  R e a l    U N I X    3 8 6   ------------
//	%+E Visible Define
//	%+E     qAL=0,qCL=1,qDL=2,qBL=3,qAH=4,qCH=5,qDH=6,qBH=7,
//	%+E     qAX=8,qCX=9,qDX=10,qBX=11, --  qSP=12,qBP=13,qSI=14,qDI=15,
//	%+E     qEAX=16,qECX=17,qEDX=18,qEBX=19,qESP=20,qEBP=21,qESI=22,qEDI=23,
//	%+E     qF=24,qM=25,
//	%+E     nregs=26;
//
//	%+E Visible Define qw_B=0,qw_W=8,qw_D=16;  -- Instruksjons-"bredde"
//
//	%+E Visible Define uAL=1,     -- 0001H = 0000 0000 0000 0001B
//	%+E                uAH=2,     -- 0002H = 0000 0000 0000 0010B
//	%+E         uAX=3, uEAX=3,    -- 0003H = 0000 0000 0000 0011B
//	%+E                uCL=4,     -- 0004H = 0000 0000 0000 0100B
//	%+E                uCH=8,     -- 0008H = 0000 0000 0000 1000B
//	%+E         uCX=12,uECX=12,   -- 000CH = 0000 0000 0000 1100B
//	%+E                uDL=16,    -- 0010H = 0000 0000 0001 0000B
//	%+E                uDH=32,    -- 0020H = 0000 0000 0010 0000B
//	%+E         uDX=48,uEDX=48,   -- 0030H = 0000 0000 0011 0000B
//	%+E                uBL=64,    -- 0040H = 0000 0000 0100 0000B
//	%+E                uBH=128,   -- 0080H = 0000 0000 1000 0000B
//	%+E        uBX=192,uEBX=192,  -- 00C0H = 0000 0000 1100 0000B
//	%+E        uSP=256,uESP=256,  -- 0100H = 0000 0001 0000 0000B
//	%+E        uBP=512,uEBP=512,  -- 0200H = 0000 0010 0000 0000B
//	%+E       uSI=1024,uESI=1024, -- 0400H = 0000 0100 0000 0000B
//	%+E       uDI=2048,uEDI=2048, -- 0800H = 0000 1000 0000 0000B
//	%+E                uF =16384, -- 4000H = 0100 0000 0000 0000B
//	%+E                uM =32768, -- 8000H = 1000 0000 0000 0000B
//	%+E             uSPBPM=33536, -- 8300H = 1000 0011 0000 0000B
//	%+E               uALL=65535, -- FFFFH = 1111 1111 1111 1111B
//	%+E          uALLbutBP=65023; -- FDFFH = 1111 1101 1111 1111B
//
//	%+E Visible Const Range(0:MaxWord) uMask(nregs) = (
//	%+E uAL,uCL,uDL,uBL,uAH,uCH,uDH,uBH,uEAX,uECX,uEDX,uEBX,
//	%+E uESP,uEBP,uESI,uEDI,uEAX,uECX,uEDX,uEBX,uESP,uEBP,uESI,uEDI,uF,uM )
//
//	%+E Visible Const Range(0:nregs) WordReg(nregs) = (
//	%+E qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,qAX,qCX,qDX,qBX,
//	%+E   0,  0,  0,  0,qAX,qCX,qDX,qBX,  0,  0,  0,  0,qF,qM )
//
//	%+E Visible Const Range(0:nregs) WholeReg(nregs) = (
//	%+E qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,qEAX,qECX,qEDX,qEBX,
//	%+E qESP,qEBP,qESI,qEDI,qEAX,qECX,qEDX,qEBX,qESP,qEBP,qESI,qEDI,qF,qM )
//
//	%+E Visible Const Range(0:2) RegSize(nregs) =
//	%+E  ( 1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,4,4,4,4,4,4,4,4,0,0 )
//
//	%+E Visible Const Range(0:1) wBIT(nregs) =
//	%+E  ( 0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0 )
//
//	%+E Visible Const Boolean RegParts(nregs) =
//	%+E  ( false,false,false,false,false,false,false,false,
//	%+E    true ,true ,true ,true ,false,false,false,false,
//	%+E    true ,true ,true ,true ,false,false,false,false,false,false )
//	%title ***    Q  -  I  N  S  T  R  U  C  T  I  O  N  S    ***
//
//	-- Instruction value classification codes ---
//	    Visible Define cANY=0; -- Don't know
//	    Visible Define cVAL=1; -- Value -- integer, character, boolean, real etc.
//	    Visible Define cOBJ=2; -- Pure Object Address (segment and/or base)
//	    Visible Define cSTP=3; -- Address into Stack (SP etc.)
//	    Visible Define cADR=4; -- Address otherwise (NOT pure object)
//	    Visible Define cMAX=4;
//
//	Visible const range(0:cMAX) cTYPE(18) = -- T_MAX+1
//	 (  cANY,cVAL,cVAL,cVAL,cVAL,cVAL,cVAL, cVAL, cVAL,cVAL,cVAL,cOBJ, cVAL, cADR,
//	 -- VOID,WRD4,WRD2,BYT2,BYT1,REAL,LREAL,TREAL,BOOL,CHAR,SIZE,OADDR,AADDR,GADDR,
//	    cANY, cVAL, cVAL, cANY );
//	 -- PADDR,RADDR,NPADR,NOINF;
//
//	Visible Record Qpkt:Object;
//	begin ref(Qpkt) next,pred;
//	      range(0:MaxWord) read;       -- Reg read mask
//	      range(0:MaxWord) write;      -- Reg write mask
//	      range(0:20) isize;           -- I-code size in bytes
//	      range(0:127) fnc;            -- Operation code
//	      range(0:20) subc;            -- Operation subcode
//	      range(0:nregs) reg;
//	      range(0:MaxType) type;       -- Type of value          --- NEW !!!
//	end;
//
//	------------------------------------------------------------------------
//	-- Format 1.  Generated by Qf1(fnc,subc)   or   Qf1b(fnc,subc,reg)
//	------------------------------------------------------------------------
//	Visible Record Qfrm1:Qpkt; begin end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//	   qWAIT    =  1, --
//	   qEVAL    =  2, --
//	   qTSTOFL  =  3, --  val
//	   qLAHF    =  4, --
//	   qSAHF    =  5, --
//	   qCWD     =  6, -- width
//	   qFDUP    =  7, --  fSD
//	   qIRET    =  8, --
//	   qDOS2    =  9, --
//	   qFLDCK   = 10, -- subc
//	                                                   qFLD1  =1, qFLDL2T=2,
//	                                                   qFLDL2E=3, qFLDPI =4,
//	                                                   qFLDLG2=5, qFLDLN2=6,
//	                                                   qFLDZ  =7,
//	   qFMONAD  = 11, -- subc     fSD
//	                                                   qFNEG=1, qFSQRT=2,
//	                                                   qFABS=3,
//	                                                   qFRND=4, qFREM=5,
//	   qFDYAD   = 12, -- subc     fSD
//	                                                   qFCOM=1, qFADD=2,
//	                                                   qFSUB=3, qFSUBR=4,
//	                                                   qFMUL=5, qFDIV=6,
//	                                                   qFDIVR=7,
//	   qFPUSH   = 13, -- fSD      fmf
//	   qFPOP    = 14, -- fSD      fmf
//	   qPUSHR   = 15, -- reg
//	   qPOPR    = 16; -- reg
//	------------------------------------------------------------------------
//	-- Format 2.  Generated by Qf2(fnc,subc,reg,aux)
//	------------------------------------------------------------------------
//	Visible Record Qfrm2:Qpkt;
//	begin infix(wWORD) aux end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//	   qRSTRB   = 17, --   subc   dir    rep
//	   qRSTRW   = 18, --   subc   dir    rep
//	                                                   qRMOV=1,   -- subc
//	                                                   qRCMP=2,   -- subc
//	                                                   qZERO=3,   -- subc
//	                                                   qRCMPS=4,  -- subc
//	                                                   qRSCAS=5,  -- subc
//	                                                   qRSTOS=6,  -- subc
//	                                                   qCLD=0,    -- dir
//	                                                   qSTD=1,    -- dir
//	                                                   qREP=0,    -- rep
//	                                                   qREPEQ=1,  -- rep
//	                                                   qREPNE=2,  -- rep
//	   qMONADR  = 19, --   subc   reg
//	                                                   qNOT=1, qNEGM=8,
//	                                                   qNEG=2, qNEGF=9,
//	                                                   qINC=3, qINCF=10,
//	                                                   qDEC=4, qDECF=11,
//	                                                   qSHL1=5,qSHL1F=12,
//	                                                   qSHR1=6,qSHR1F=13,
//	                                                   qSAR1=7,qSAR1F=14,
//	   qTRIADR  = 20, --   subc   reg
//	                                                   qWMUL=1,  qWMULF=5,
//	                                                   qWDIV=2,  qWDIVF=6,
//	                                                   qWMOD=3,  qWMODF=7,
//	                                                   qIMUL=9,  qIMULF=13,
//	                                                   qIDIV=10, qIDIVF=14,
//	                                                   qIMOD=11, qIMODF=15,
//	   qCONDEC  = 21, --   subc   reg
//	                                                   q_WLT=1,  q_WLE=2,
//	                                                   q_WEQ=3,  q_WGE=4,
//	                                                   q_WGT=5,  q_WNE=6,
//	                                                   q_ILT=9,  q_ILE=10,
//	                                                   q_IEQ=11, q_IGE=12,
//	                                                   q_IGT=13, q_INE=14,
//	   qMOV     = 22, --          reg    reg2
//	%+E                                                qSEXT=1, qZEXT=2,
//	   qXCHG    = 23, --          reg    reg2
//	   qDYADR   = 24, --   subc   reg    reg2
//	                                                   qAND=1,  qOR=2,
//	                                                   qXOR=3,
//	                                                   qANDM=4, qORM=5,
//	                                                   qXORM=6,
//	                                                   qINCO=7, qDECO=8,
//	                                                   qCMP=9,  qADD=10,
//	                                                   qSUB=11,
//	                                                   qADDM=12,qSUBM=13,
//	                                                   qADC=14, qSBB=15,
//	                                                   qADDF=16,qSUBF=17,
//	                                                   qADCF=18,qSBBF=19,
//	   qSHIFT   = 25, --   subc   reg
//	                                                   qSHL=1,qSHR=2,qSAR=3,
//	   qPOPK    = 26, --                 const
//	   qRET     = 27, --                 const
//	   qINT     = 28, --                 const
//	   qADJST   = 29, --                 const
//	   qENTER   = 30, --                 const
//	   qLEAVE   = 31, --                 const
//	   qLINE    = 32, --   subc          const
//	                                                   qDCL=1,qSTM=2,
//	   qDYADC   = 33; --   subc   reg    const     -- Same subcodes as DYADR
//	------------------------------------------------------------------------
//	-- Format 2.  Generated by Qf2(fnc,subc,reg,aux)
//	-- Format 2b. Generated by Qf2b(fnc,subc,reg,aux,addr)
//	------------------------------------------------------------------------
//	Visible Record Qfrm2b:Qfrm2;
//	begin infix(MemAddr) addr end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//	%-E qLOADSC = 34, --   sreg   reg           addr
//	   qPUSHC   = 35, --          reg   const
//	                  --          reg   fld     addr
//	   qLOADC   = 36; --          reg   const
//	                  --          reg   fld     addr
//	------------------------------------------------------------------------
//	-- Format 3.  Generated by Qf3(fnc,subc,reg,opr)
//	-- Format 3b. Generated by Qf3b(fnc,subc,reg,val)
//	------------------------------------------------------------------------
//	Visible Record Qfrm3:Qpkt;
//	begin variant infix(MemAddr) opr;
//	      variant infix(ValueItem) val;
//	end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//	   qJMPM    = 37, --                        opr
//	%-E qJMPFM  = 38, --                        opr
//	%+E qBOUND  = 39, --                        opr
//	   qPUSHA   = 40, --          reg           opr
//	   qLOADA   = 41, --          reg           opr
//	   qSTORE   = 42, --          reg           opr
//	   qXCHGM   = 43, --          reg           opr
//	   qFLD     = 44, --   fSD    fmf           opr
//	%-E qFLDC   = 45, --   sreg   fmf       lrv/rev/val
//	%+E qFLDC   = 45, --   fSD    fmf       lrv/rev/val
//	   qFST     = 46, --   fSD    fmf           opr
//	   qFSTP    = 47, --   fSD    fmf           opr
//	   qDYADM   = 48, --   subc   reg           opr   -- Same subc as DYADR
//	   qDYADMR  = 49, --   subc   reg           opr   -- Same subc as DYADR
//	   qMONADM  = 50, --   subc  width          opr   -- Same subc as MONADR
//	   qTRIADM  = 51; --   subc  width          opr   -- Same subc as TRIADR
//	------------------------------------------------------------------------
//	-- Format 4.  Generated by Qf4(fnc,subc,reg,aux,opr)
//	-- Format 4b. Generated by Qf4b(fnc,subc,reg,aux,opr,addr)
//	-- Format 4c. Generated by Qf4c(fnc,subc,reg,aux,opr,nrep)
//	------------------------------------------------------------------------
//	Visible Record Qfrm4:Qfrm3;
//	begin infix(wWORD) aux end;
//
//	Visible Record Qfrm4b:Qfrm4;
//	begin infix(MemAddr) addr end;
//
//	Visible Record Qfrm4c:Qfrm4;
//	begin range(0:MaxByte) nrep end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//
//	   qLOAD    = 52, --   subc   reg    ofst   opr nrep -- Same subc as MOV
//	%-E qLDS    = 53, --          reg    ofst   opr nrep
//	%-E qLES    = 54, --          reg    ofst   opr nrep
//
//	   qFDYADM  = 55, --   subc   fmf    fSD    opr   -- Same subc as FDYAD
//	   qPUSHM   = 56, --                const   opr
//	   qPOPM    = 57, --          reg   const   opr
//	   qMOVMC   = 58, --         width  const   opr
//	                  --         width   fld    opr addr
//	   qDYADMC  = 59; --   subc  width  const   opr   -- Same subc as DYADR
//	                  --   subc  width   fld    opr addr
//	------------------------------------------------------------------------
//	-- Format 5.  Generated by Qf5(fnc,subc,addr)
//	------------------------------------------------------------------------
//	Visible Record Qfrm5:Qpkt;
//	begin infix(MemAddr) addr;
//	      range(0:MaxWord) aux;
//	      ref(Qpkt) dst;
//	end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//	   qCALL    = 60, --   subc         pxlng  addr
//	                                                   qEXIT=1, qXNX=2,
//	                                                   qOS2=3,  qC=4,
//	                                                   qKNL=5,
//	   qJMP     = 61; --   subc                addr dst -- Same subc as CONDEC 
//	                                                    -- plus subc=0
//	------------------------------------------------------------------------
//	-- Format 6.  Generated by Qf6(fnc,subc,reg,aux)
//	------------------------------------------------------------------------
//	Visible Record Qfrm6:Qpkt;
//	begin range(0:MaxWord) aux;
//	      ref(Qfrm5) jmp;
//	      variant infix(wWORD) rela;
//	      variant range(0:MaxWord) smbx;
//	end;
//	------------------------------------------------------------------------
//	-- Mnemonic = FNC --   SUBC   REG    AUX   other   subcodes/comments
//	------------------------------------------------------------------------
//	Visible Define
//	   qFDEST   = 62, --                fixno       jmp
//	   qBDEST   = 63, --                labno  rela jmp
//	   qLABEL   = 64, --   subc         fixno  smbx
//	                                                   qBPROC=1,qEPROC=2,
//
//	   qMXX     = 64;
//	%title ***   T h e   E n v i r o n m e n t   I n t e r f a c e   ***
//	Range(0:36) status system "STATUS";
//
//	Record string; info "TYPE";
//	begin name(character) chradr; integer nchr; end;
//
//	---   E r r o r   H a n d l i n g   ---
//
//	global profile Perhandl;
//	import range(0:13) code;
//	       infix(string) msg;
//	       label addr;
//	export label cont end;
//
//	global profile Perror;
//	import infix(string) msg end;
//
//	Entry(Perror) Erroutine;
//	%title ***    G l o b a l   V a r i a b l e s    ***
//
//	    Range(0:MaxKey) scode;      -- File key for S-Code input
//	    Range(0:MaxKey) modoupt;    -- File key for module output file
//	    Range(0:MaxKey) modinpt;    -- File key for module input file
//	    Range(0:MaxKey) scrfile;    -- File key for relocatable scratch file
//	    Range(0:MaxKey) objfile;    -- File key for relocatable object file
//	    Ref(File) sysin;            -- Main input's file-object
//	    Ref(File) sysout;           -- Print output's file-object
//	%+D Ref(File) inptrace;         -- Input Trace's file-object
//	public static StringBuilder inptrace;         // Input Trace's StringBuilder
//	%+D Ref(File) ouptrace;         -- Output Trace's file-object
//	    Ref(File) sysedit;          -- Text editing's file-object
//	    Ref(File) errmsg;           -- Error message editing's file-object
//	    Range(0:10) CPUID;          -- 1=iAPX86,2=iAPX88,3=iAPX186,4=iAPX286
//	                                -- 5=iAPX386,6=iAPX486
//	    Range(0:10) NUMID;          -- 1=iAPX87,2=iAPX287,3=iAPX387
//	    Range(0:10) OSID;           -- 0=DOS,1=OS2,2=XENIX/286,3=XENIX/386
//	                                -- 4=UNIX/386
//	    Range(0:10) CBIND;          -- cNONE=0,cMS=1,cTURBO=2
//	    Range(0:MaxByte) CombAtr;   -- 0:Normal, 1:Combined Sysinsert
//
//	    Boolean TSTOFL;             -- True:Insert test-on-overflow
//	%-E Boolean CHKSTK;             -- True:Produce Call on E.CHKSTK
//
//	    Range(0:MaxType) ntype;     -- Number of data types defined
//	    Range(0:T_Max) T_INT,T_SINT; -- Integer type mapping
//	    Infix(DataType) TTAB(MaxType); -- Type specification table
//
//	%+S Range(0:P_max) PsysKind; --
//	    Range(0:MaxWord) TypeLength; -- Nbytes of last nonstandard InType
//	    Infix(WORD) TagIdent;        -- Tag-Ident from InXtag
//
//	    Range(0:16000) nerr;        -- Number of error messages until now
//	public static int curline;		// Current source line number
//	public static int inputTrace;	// Input trace switch
//	%+D Range(0:10) listq1;         -- Output Q-code 1 listing switch
//	%+D Range(0:10) listq2;         -- Output Q-code 2 listing switch
//	%+D Range(0:10) listsw;         -- Output I-code listing switch
//	%+D Range(0:10) TraceMode;      -- Processing trace switch
//	%+D Range(0:10) ModuleTrace;    -- Module I/O trace switch
//	    Range(0:255) MASSLV;        -- Massage depth level
//	    Range(0:255) MASSDP;        -- Massage max recursion depth
//	%-E Range(0:255) BNKLNK;        -- >0: Prepare Produced code for BANKING
//	    Real InitTime;              -- Initiation CPU-time
//	%+S Boolean envpar;             -- True: Parameters from environment
//	    Boolean errormode;          -- Treating an Error
//	%+A Boolean asmgen;             -- ASM-output generating mode
//	    Boolean InsideRoutine;      -- Inside Routine Body indicator
//
//	    --- Dictionary ---
//	    Infix(Dictionary) DIC;
//	    Infix(WORD) PRFXID;     -- Prefix to entry point symbols
//	    Infix(WORD) PROGID;     -- Ident of program being defined
//	    Infix(WORD) CSEGID;     -- Index of program's current code Segment
//	    Infix(WORD) DSEGID;     -- Index of program's data Segment
//	    Infix(WORD) LSEGID;     -- Index of program's LineTable Segment
//	    Infix(WORD) DumSEG;     -- Index of the dummy Segment
//	    Infix(WORD) DGROUP;     -- Index of the _DATA Segment
//	    Infix(WORD) EnvCSEG;    -- Index of environment's code Segment
//	    Infix(WORD) SCODID;     -- Name of Scode input file
//	    Infix(WORD) ATTRID;     -- Prefix to attribute file
//	    Infix(WORD) RELID;      -- Name of RELocatable object Output file
//	    Infix(WORD) SCRID;      -- Name of rel/asm scratch file
//	%+A Infix(WORD) ASMID;      -- Name of assembly source output file
//	    Infix(WORD) ProgIdent;  -- S-Code PROG String
//	public static String progIdent;  // S-Code PROG String
//	    Infix(WORD) modident;   -- Ident of module being defined
//	    Infix(WORD) modcheck;   -- Check code of module being defined
//	    Infix(WORD) DsegEntry;  -- Data Segment start symbol
//	    ---   Entry Points ---
//	    Infix(MemAddr) MainEntry; -- Main program's entry-point
//	    Infix(MemAddr) LtabEntry; -- Line-no-table's entry-point
//	    Infix(MemAddr) X_OSSTAT;  -- Entry-point of G.OSSTAT
//	    Infix(MemAddr) X_KNLAUX;  -- Entry-point of G.KNLAUX
//	    Infix(MemAddr) X_STATUS;  -- Entry-point of G.STATUS
//	    Infix(MemAddr) X_STMFLG;  -- Entry-point of G.STMFLG
//	%-E Infix(MemAddr) X_IMUL;    -- Entry-point of E.IMUL
//	%-E Infix(MemAddr) X_IDIV;    -- Entry-point of E.IDIV
//	%-E Infix(MemAddr) X_IREM;    -- Entry-point of E.IREM
//	%-E Infix(MemAddr) X_RENEG;   -- Entry-point of E.RENEG
//	%-E Infix(MemAddr) X_READD;   -- Entry-point of E.READD
//	%-E Infix(MemAddr) X_RESUB;   -- Entry-point of E.RESUB
//	%-E Infix(MemAddr) X_REMUL;   -- Entry-point of E.REMUL
//	%-E Infix(MemAddr) X_REDIV;   -- Entry-point of E.REDIV
//	%-E Infix(MemAddr) X_RECMP;   -- Entry-point of E.RECMP
//	%-E Infix(MemAddr) X_LRNEG;   -- Entry-point of E.LRNEG
//	%-E Infix(MemAddr) X_LRADD;   -- Entry-point of E.LRADD
//	%-E Infix(MemAddr) X_LRSUB;   -- Entry-point of E.LRSUB
//	%-E Infix(MemAddr) X_LRMUL;   -- Entry-point of E.LRMUL
//	%-E Infix(MemAddr) X_LRDIV;   -- Entry-point of E.LRDIV
//	%-E Infix(MemAddr) X_LRCMP;   -- Entry-point of E.LRCMP
//	%-E Infix(MemAddr) X_IN2RE;   -- Entry-point of E.IN2RE
//	%-E Infix(MemAddr) X_IN2LR;   -- Entry-point of E.IN2LR
//	%-E Infix(MemAddr) X_RE2IN;   -- Entry-point of E.RE2IN
//	%-E Infix(MemAddr) X_RE2LR;   -- Entry-point of E.RE2LR
//	%-E Infix(MemAddr) X_LR2IN;   -- Entry-point of E.LR2IN
//	%-E Infix(MemAddr) X_LR2RE;   -- Entry-point of E.LR2RE
//
//	%-E Infix(MemAddr) X_GOTO;    -- Entry-point of E.GOTO
//	%-E Infix(MemAddr) X_CALL;    -- Entry-point of E.CALL
//	%+D Infix(MemAddr) X_ECASE;   -- Entry-point of ECASE routine
//	%+S Infix(MemAddr) X_INITO;   -- Entry-point of INITO routine
//	%+S Infix(MemAddr) X_GETO;    -- Entry-point of GETO routine
//	%+S Infix(MemAddr) X_SETO;    -- Entry-point of SETO routine
//	%-E Infix(MemAddr) X_CHKSTK;  -- Entry-point of CHKSTK routine
//	%-E %+SC Infix(MemAddr) X_STKBEG; -- Entry-point of start-of-stack
//	%-E %+SC Infix(MemAddr) X_STKOFL; -- Entry-point of STKOFL routine
//	    Infix(MemAddr) X_SSTAT;   -- Entry-point of XENIX get errno rut.
//
//	    Infix(MemAddr) TMPAREA;   -- Temp area
//	    Infix(MemAddr) TMPQNT;    -- Temp quant area (of RTS)
//	    Infix(MemAddr) X_INITSP;  -- Entry-point of G.INITSP
//
//	    --- Current Stack ---
//	    range(0:8) StackDepth87; -- initial(0)
//	    ref(StackItem) TOS;      -- Top of Compile-time stack
//	    ref(StackItem) BOS;      -- Bot of Compile-time stack
//	    ref(StackItem) SAV;   -- Last Compile-time stack-item for which
//	                          -- the corresponding Runtime-item is saved.
//	                          -- NOTE: SAV =/= none implies TOS =/= none
//
//	    Ref(BSEG) curseg;           -- Current program BSEG
//	    Ref(BSEG) FreeSeg;          -- Free program BSEG list
//	    Range(0:MaxWord) nSubSeg;   -- No.of sub-segments gen. by BSEG
//
//	    Ref() PoolTop;              -- Storage boundary pointer
//	    Ref() PoolNxt;              -- Storage boundary pointer
//	    Ref() PoolBot;              -- Storage boundary pointer
//	    Range(0:MaxByte) npool;     -- No.of data pools allocated
//	    Ref(FreeArea) FreePool;     -- Head of Free Pool list
//	%+D Infix(PoolSpec) PoolTab(MaxPool); -- Storage pool statistics
//	    Ref(Object) FreeObj(K_Max); -- Free Object lists
//	%+D Range(0:MaxWord) ObjCount(K_Max); -- No.of Objects generated
//	%+D Integer CalCount(R_Max);    -- No.of Routine calls
//
//	%+D Range(0:10) BECDEB;      -- Debugging level   (Debugging purposes)
//	%+D Range(0:10) TLIST;       -- Option D - Major Event Trace Level
//	    Range(0:16000) MXXERR;   -- Max number of errors
//	%+S Range(0:10) SYSGEN;      --  System generation
//	                             --      0: User program
//	                             --      1: Generation of Runtime System
//	                             --      2: Generation of S-Compiler
//	                             --      3: Generation of Environment
//	                             --      4: Generation of Library
//
//	    Range(0:MaxWord) SEGLIM; -- Max seg-size befor segment-split
//	    Range(0:16000) QBFLIM;   -- No.of Q-instr in buf before Exhaust Half
//	    Range(0:10) RNGCHK;      -- >0: produce Range --> char range check
//	    Range(0:10) IDXCHK;      -- >0: produce array index check
//	    Range(0:10) LINTAB;      -- 0:No-LineTab, else:Generate LineTab
//	    Range(0:10) DEBMOD;      -- >2:Generate line breaks, else: do not!
//
//	    Range(0:MaxWord) LabelSequ; -- No.of labels  created by 'NewLabno'
//	    Range(0:MaxWord) SymbSequ;  -- No.of symbols created by 'NewPubl'
//
//	%+D Integer SK1LIN;    --  S-Compiler-Trace - Pass 1 starting line
//	%+D Integer SK1TRC;    --  Pass 1 Trace level=SEOMTI (one digit each)
//	%+D Integer SK2LIN;    --  S-Compiler-Trace - Pass 2 starting line
//	%+D Integer SK2TRC;    --  Pass 2 Trace level=SEOMTI (one digit each)
//	                                 --   I = 0..9 Input trace level
//	                                 --   T = 0..9 Trace-mode level
//	                                 --   M = 0..9 Module IO trace level
//	                                 --   O = 0..9 Output trace level
//	                                 --   E = 0..9 Output trace level listq1
//	                                 --   S = 0..9 Output trace level listq2
//
//	    Range(0:MaxWord) nTag;         -- No.of tags defined
//	    Range(0:MaxWord) nFix;         -- No.of Fixups defined
//	    Range(0:MaxWord) nXtag;        -- Size of TAGTAB
//	    Range(0:MaxWord) nXtyp;        -- Size of TYPMAP
//	    Range(0:MaxWord) nXsmb;        -- Size of SMBMAP
//	    Ref(Qpkt) DESTAB(255);         -- Jump Destination table
//	    Ref(Qpkt) DESTAB256;           -- ???? TEMP ????
//	    Ref(Qpkt) FWRTAB(255);         -- Extra Jump Destination table
//	    Ref(Qpkt) FWRTAB256;           -- ???? TEMP ????
//	    ref(qpkt) xFJUMP;              --- see parse/coder (gqrelation)
//	    Ref(RefBlock)  DISPL(MxpTag);  -- Descriptor Display Table
//	%+D Ref(WordBlock) TIDTAB(MxpTag); -- Tag-Identifier table
//	    Ref(FixBlock) FIXTAB(MxpFix);  -- FIXUP table
//	    Ref(WordBlock)TAGTAB(MxpXtag); -- Tag Table (during Module I/O)
//	    Ref(WordBlock)TYPMAP(MxpXtyp); -- Type-mapping (during Module I/O)
//	    Ref(WordBlock)SMBMAP(MxpXsmb); -- Symbol-mapping (during Module I/O)
//
//	    Range(0:MaxByte) NXTYPE; -- Type of First/Next buffer
//	    Infix(WORD) NXTLNG;      -- Length of First/Next buffer
//	    Infix(Scodebuffer) SBUF; -- S-Code Buffer
//	    Infix(iCodebuffer) CBUF; -- Code Buffer
//	    Infix(Relocbuffer) CREL; -- Code Relocations
//	    Infix(iCodebuffer) DBUF; -- Data Buffer
//	    Infix(Relocbuffer) DREL; -- Data Relocations
//
//	%title ***   M e m o r y   A d d r e s s e s   ***
//	Define reladr=1,locadr=2,segadr=3,extadr=4,fixadr=5,knladr=6;
//	Define adrMax=6;
//
//	Record MemAddr; info "TYPE";
//	-- NOTE: All variants of MemAddr are treated as Compact and
//	--       of the same size. So, test for equality between two
//	--       MemAddrs may be performed directly on the type MemAddr.
//	--       E.g.  'opr1' and 'opr2'  are equal iff  'opr1=opr2'.
//	begin
//	      infix(wWORD) rela;       -- Relative byte address
//	%-E   range(0:MaxByte) sbireg; -- <m>1<sreg>3<m>1<bireg>3
//	%-E                            -- m: 1:following field is significant
//	%-E                            --    0:following field=0
//	%-E                            -- sreg:   000:ES, 001:CS, 010:SS, 011:DS
//	%-E                            -- bireg:  0=000:[BX]+[SI]   4=100:[SI]
//	%-E                            --         1=001:[BX]+[DI]   5=101:[DI]
//	%-E                            --         2=010:[BP]+[SI]   6=110:[BP]
//	%-E                            --         3=011:[BP]+[DI]   7=111:[BX]
//	%+E   range(0:MaxByte) sibreg; -- <ss>2<ireg>3<breg>3
//	%+E                            -- ss: Scale Factor 00=1,01=2,10=4,11=8
//	%+E                            -- ireg,breg: 000:[EAX]   001:[ECX]
//	%+E                            --            010:[EDX]   011:[EBX]
//	%+E                            --            100:[ESP]   101:[EBP]
//	%+E                            --            110:[ESI]   111:[EDI]
//	%+E                            -- E.g.   10 110 011=DS:[EBX]+[ESI]*4
//	%+E                            --        11 111 101=SS:[EBP]+[EDI]*8
//	%+E                            -- Note:  11 100 100=228 is ruled out,
//	%+E                            --        meaning no breg or ireg
//	      range(0:adrMax) kind;    -- Variant kind code
//	                                       -- reladr: + rela
//	      variant range(0:MaxWord) loca;   -- locadr: + rela - loca
//	      variant infix(WORD) segmid;      -- segadr: SEG(segid)+rela
//	      variant infix(WORD) smbx;        -- extadr: SYMBOL(smbx)+rela
//	      variant infix(WORD) fix;         -- fixadr: FIXUP(fix)+rela
//	      variant infix(WORD) knlx;        -- knladr: KERNEL(knlx)
//	end;
//
//	Record Fixup; info "TYPE";
//	begin Boolean Matched;
//	%+D   range(0:MaxLine) line;     -- Created at line
//	%+D   range(0:MaxWord) fixno;    -- Assembly label serial number
//	      Infix(WORD) segid;         -- Segment name index
//	      variant Infix(WORD) smbx;        -- Unmatched: External Symbol
//	      variant Infix(wWORD) rela;       -- Relative byte address
//	end;
//
//	Record FixBlock;
//	begin infix(Fixup) elt(255);
//	      infix(Fixup) elt256;   --- TEMP
//	end;
//
//	Record ExtRef; info "TYPE";
//	begin range(0:MaxWord) rela;   -- Relative byte address
//	      Infix(WORD) smbx;        -- External Symbol index
//	end;
//	%title ***   S t r u c t u r e d    T y p e s   ***
//	------   D a t a   T y p e s   ------
//
//	Define tUnsigned=0,tSigned=1,tFloat=2;
//
//	Record DataType; info "TYPE";
//	begin range(0:MaxByte) nbyte;    -- Size of type in bytes
//	      range(0:2) kind;           -- tUnsigned,tSigned,tFloat
//	      infix(WORD) pntmap;        -- 0:no pointers,
//	end;                             -- else: Reladdr of pointers
//
//	------   W O R D  /  D W O R D  /  Q W O R D   ------
//
//	Record WORD; info "TYPE";
//	begin variant range(0:MaxWord) val;
//	      variant range(0:MaxByte) LO,HI;
//	end;
//
//	Record DWORD; info "TYPE";
//	begin variant integer          val;
//	      variant range(0:MaxByte) LO,LOHI,HILO,HI;
//	      variant infix(WORD) LowWord;
//	              infix(WORD) HighWord;
//	end;
//
//	Record wWORD; info "TYPE";
//	begin
//	%-E   variant range(0:MaxWord) val;
//	%+E   variant integer          val;
//	%-E   variant range(0:MaxByte) LO,HI;
//	%+E   variant range(0:MaxByte) LO,LOHI,HILO,HI;
//	      variant infix(WORD) LowWord;
//	%+E           infix(WORD) HighWord;
//	end;
//
//	------   V  a  l  u  e     I  t  e  m   ------
//
//	Record ValueItem; info "TYPE";
//	begin variant range(0:MaxByte) byt(8); -- ?? variant range(0:MaxWord) byt(8);
//	      variant range(0:MaxWord) wrd(4);
//	      variant integer int(2);
//	      variant real rev(2);
//	      variant long real lrv;
//	      variant infix(MemAddr) base;     -- size 6(-E) / 8(+E)
//	              range(0:MaxWord) Ofst;
//	end;
//
//	Record LinePkt;
//	begin ref(LinePkt) next;
//	      range(0:MaxLine) line;
//	      infix(wWORD) rela;
//	end;
//
//	Record File;
//	begin range(0:MaxKey) key;     --- File key
//	      range(0:132) pos;        --- Posision indicatior (0..nchr-1)
//	      range(0:MaxByte) nchr;
//	      character chr(0);        --- File buffer
//	end;
//
//	%title ***  B U F F E R S  ***
//
//	Record Buffer;
//	begin character hed(2);     -- 1:Scode, 2:Code, 3:Reloc
//	      range(0:MaxWord) nxt; -- Next available byte position
//	end;
//
//	define sBufLen = 2048
//	Record ScodeBuffer:Buffer;
//	begin variant character chr(sbuflen);
//	      variant range(0:MaxByte) byt(sbuflen);
//	end;
//
//	Record iCodeBuffer:Buffer;
//	begin
//	%-E   infix(WORD) segx;                -- NOTE: This record is written
//	%-E   infix(WORD) ofst;                --       to give the same layout
//	%+E   range(0:MaxWord) segx;           --       with %+E on 286 and 386
//	%+E   range(0:MaxWord) ofstLO,ofstHI;  -- -----------------------------
//	      variant character chr(1024);
//	      variant range(0:MaxByte) byt(1024);
//	end;
//
//	------  R e l o c a t i o n    P a c k e t s  ------
//
//	-- mrk = <0>1<FieldType>3<RelType>2<Offset>10
//	-- FieldType:
//	    Define fPOINTER=3,  mfPOINTER=12288;  -- 3000H Segm-relative
//	    Define fSEGMENT=2,  mfSEGMENT=8192;   -- 2000H Segm-relative
//	    Define fOFFSET=1,   mfOFFSET=4096;    -- 1000H Segm-relative
//	%+E Define fOFST32=5,   mfOFST32=20480;   -- 5000H Segm-relative
//	    Define fFULLDISP=4, mfFULLDISP=16384; -- 4000H Self-relative
//	    Define fBYTEDISP=0, mfBYTEDISP=0;     -- 0000H Self-relative
//	-- RelType:
//	    Define rSEG=0,mrSEG=0;                -- 0000H Segment base
//	    Define rEXT=1,mrEXT=1024;             -- 0400H External
//	    Define rFIX=2,mrFIX=2048;             -- 0800H Fixup
//
//	Record RelocPkt; info "TYPE";
//	begin range(0:MaxWord) mrk;
//	      variant infix(WORD) segx;
//	      variant infix(WORD) extx;
//	      variant infix(WORD) fix;
//	end;
//
//	Record RelocBuffer:Buffer;
//	begin infix(RelocPkt) elt(255);
//	      infix(RelocPkt) elt256;
//	end;
//
//	%+F Record CoffRelocPkt;
//	%+F begin integer vAddr;
//	%+F       integer SymNdx;
//	%+F       range(0:MaxWord) Type;  -- Relocation Type
//	%+F end;
//
//	%+F     Define R_ABS=0;       -- No Relocation
//	%+F %-E Define R_REL16=1;     -- Self-relative 16-bit Relocation
//	%+F %-E Define R_OFF8=7;      -- Segment relative 8-bit Offset
//	%+F %-E Define R_OFF16=8;     -- Segment relative 16-bit Offset
//	%+F %-E Define R_SEG12=9;     -- Segment relative 16-bit Selector
//	%+FE    Define R_DIR32=6;     -- Segment relative 32-bit Relocation
//	%+FE    Define R_PCRLONG=20;  -- Self-relative 32-bit Relocation
//
//	%+F Record RelocObj;
//	%+F begin ref(RelocObj) next;
//	%+F       infix(CoffRelocPkt) Cpkt;
//	%+F end;
//	%title ***   D i c t i o n a r y    ***
//	Record Dictionary;
//	begin range(0:MaxWord) nSymb;       -- No.of Symbols
//	      range(0:MaxWord) nSegm;       -- No.of Segments
//	      range(0:MaxWord) nPubl;       -- No.of Public Definitions
//	      range(0:MaxWord) nExtr;       -- No.of External References
//	      range(0:MaxWord) nModl;       -- No.of Module Names
//	      infix(WORD) HashKey(255);     -- Hash Key table
//	      infix(WORD) HashKey256;       -- !!!! TEMP
//	      ref(RefBlock)  Symb(MxpSymb); -- Symbol table
//	      ref(WordBlock) Segm(MxpSegm); -- Segment Table
//	      ref(WordBlock) Publ(MxpPubl); -- Public Definition Table
//	      ref(WordBlock) Extr(MxpExtr); -- External Reference Table
//	      ref(WordBlock) Modl(MxpModl); -- Module Definition Table
//	end;
//
//	Record SmbElt;
//	begin range(0:MaxByte) clas;   --- Symbol Class
//	      range(0:MaxByte) nchr;   --- No.of chars in symbol
//	      infix(WORD) link;        --- Next Symbol with same hash-key
//	end;
//
//	Record Symbol:SmbElt;          -- clas=sSYMB
//	begin character chr(0) end;
//
//	Record Extern:SmbElt;          -- clas=sEXTR
//	begin infix(WORD) extx;        -- Extern index
//	      infix(WORD) segid;       -- Segment name
//	      character chr(0);
//	end;
//
//	Record Public:SmbElt;          -- clas=sPUBL
//	begin infix(WORD) pubx;        -- Public index
//	      infix(WORD) segx;        -- Segment index
//	      infix(wWORD) rela;       -- Relative address
//	      character chr(0);
//	end;
//
//	Record ModElt:SmbElt;          -- clas=sMODL
//	begin infix(WORD) modx;        -- Module index
//	      Infix(WORD) LinTab;      -- Entry-Point of Line-no-Table
//	      Infix(WORD) RelElt;      -- REL-file's file-spec
//	      Infix(WORD) AtrElt;      -- AT2-file's file-spec
//	      Infix(WORD) AtrNam;      -- AT2-file's DataSetName i.e. Full name
//	      character chr(0);
//	end;
//
//	Record Segment:SmbElt;         -- clas=sSEGM
//	begin infix(WORD) segx;           -- Segment index
//	      range(0:2) type;            -- 0:Stack, 1:Data, 2:Code
//	      infix(wWord) lng;           -- Segment length in bytes
//	      infix(wWord) rela;
//	      range(0:MaxWord) MindMask;  -- Accumulated Reg mind mask
//	      ref(Qpkt) qfirst,qlast;     -- Q-Code Queue
//	      range(0:MaxWord) qcount;
//	      ref(LinePkt) lfirst,llast;  -- Line-no-Table
//	      range(0:MaxWord) lcount;
//	%+F   integer pAddr;              -- Start addr within raw text in COFF
//	%+F   ref(RelocObj) FstRel,LstRel;
//	%+F   range(0:MaxWord) Sectn;     -- Mapped to COFF Section no.
//	      character chr(0);           -- 
//	end;
//	%title ***   D y n a m i c     O b j e c t s    ***
//
//	Record Object;
//	begin range(0:MaxByte) kind;   --- Object kind code
//	      range(0:MaxType) type; 
//	end;
//
//	Record FreeObject:Object;
//	begin ref(Object) next end;    --- Free list pointer
//
//	Record FreeArea;
//	begin ref(FreeArea) next;      --- Free list pointer
//	      size PoolSize;
//	end;
//
//	%+D Record PoolSpec; info "TYPE";
//	%+D begin ref() PoolTop;
//	%+D       size PoolSize;
//	%+D end;
//
//	Record RefBlock:Object;
//	begin ref() elt(255);
//	      ref() elt256;     --- TEMP
//	end;
//
//	Record WordBlock:Object;
//	begin infix(WORD) elt(255);
//	      infix(WORD) elt256;   --- TEMP
//	end;
//
//
//	Record AddrBlock:Object;
//	begin infix(MemAddr) elt(255);
//	      infix(MemAddr) elt256;   --- TEMP
//	end;
//
//	%page
//	      ---------------------------------------------------
//	      ---       D  e  s  c  r  i  p  t  o  r  s       ---
//	      ---------------------------------------------------
//
//	Record Descriptor:Object;
//	begin end;
//
//	Record RecordDescr:Descriptor;   -- K_RecordDescr                 SIZE = 8 bytes
//	begin range(0:MaxWord) nbyte;    -- Record size information
//	      range(0:MaxWord) nbrep;    -- Size of rep(0) attribute
//	      infix(WORD) pntmap;        -- Only used by TypeRecord
//	end;
//
//	Record TypeRecord:RecordDescr;   -- K_TypeRecord                  SIZE = 8 bytes
//	begin -- infix(WORD) pntmap;     -- 0:no pointers,
//	end;                             -- else: Reladdr of pointers
//
//	Record LocDescr:Descriptor;      -- K_Attribute  Offset = rela    SIZE = 8 bytes
//	                                 -- K_Import  Address = [BP] + rela
//	                                 -- K_Export     Address = [BP] + rela
//	                                 -- K_LocalVar   Address = [BP] - rela
//	begin range(0:MaxWord) rela;
//	      infix(WORD) nextag;        -- NOTE: Only used for Parameters
//	                                 -- NOTE: And only in 'inprofile'
//	      range(0:MaxWord) UNUSED;   -- To 4-byte align record in all cases
//	end;  
//
//	Record IntDescr:Descriptor;      -- K_Globalvar
//	begin infix(MemAddr) adr;        -- K_IntLabel
//	end;                             -- K_IntRoutine   Local Routine
//
//	Record ExtDescr:Descriptor;      -- K_ExternVar                   SIZE = 8 bytes
//	begin range(0:MaxWord) UNUSED;   -- To 4-byte align record in all cases
//	      infix(ExtRef) adr;         -- K_ExtLabel
//	end;                             -- K_ExtRoutine   External Routine
//
//	Record ParamSpec; info "TYPE";
//	begin range(0:MaxType) type;
//	      range(0:MaxByte) count;
//	end;
//
//	Record ProfileDescr:Descriptor;  -- K_ProfileDescr     SIZE = (6+npar*2) align 4
//	begin range(0:MaxByte) npar;     -- No.of parameters
//	      range(0:1) WithExit;
//	      range(0:MaxParByte) nparbyte;
//	      range(0:P_max) Pkind;
//	      infix(ParamSpec) Par(0);   -- Parameter Specifications
//	end;
//
//	Record SwitchDescr:Descriptor;
//	begin range(0:MaxSdest) ndest;   -- No. of Sdest in this switch
//	      range(0:MaxSdest) nleft;   -- No. of Sdest left to be defined
//	      infix(MemAddr) swtab;      -- Start of Sdest-Table
//	      ref(AddrBlock) DESTAB(MxpSdest); -- All SDEST addresses
//	end;
//	%page
//
//	---------------------------------------------------------
//	---       M  o  d  u  l  e     H  e  a  d  e  r       ---
//	---------------------------------------------------------
//
//	Record ModuleHeader;
//	begin range(0:MaxWord) Magic;  -- Magic number
//	      range(0:MaxByte) Layout; -- File Layout number
//	      range(0:MaxByte) Comb;   -- 0:Normal, 1:Combined
//	      Infix(WORD) modid;       -- Module's identification
//	      Infix(WORD) check;       -- Module's check code
//	      range(0:MaxWord) nXtag;  -- No.of external tags
//	      range(0:MaxWord) nType;  -- No.of Types
//	      range(0:MaxWord) nSymb;  -- No.of Symboles
//	      range(0:MaxWord) sDesc;  -- Size of Descriptor area
//	      range(0:MaxWord) sFeca;  -- Size of FEC-Attribute area
//	      range(0:MaxWord) nTmap;  -- 0:No TAGMAP, no.of tags in TAGMAP
//	      integer DescLoc;         -- Fileloc: Descriptor area
//	      integer TypeLoc;         -- Fileloc: Type table
//	      integer TgidLoc;         -- Fileloc: Tagid table
//	      integer SymbLoc;         -- Fileloc: Symbol Table
//	      integer FecaLoc;         -- Fileloc: FEC-Attributes
//	      integer TmapLoc;         -- Fileloc: TAGMAP table
//	end;
//
//	%page
//	---------------------------------------------
//	---------    S  e  g  m  e  n  t    ---------
//	---------------------------------------------
//
//	Record BSEG:Object;
//	begin ref(BSEG) next;
//	      Infix(WORD) segid;       -- Program Segment's name index
//	      range(0:8) StackDepth87; -- initial(0)
//	      ref(StackItem) TOS;      -- Top of Compile-time stack
//	      ref(StackItem) BOS;      -- Bot of Compile-time stack
//	      ref(StackItem) SAV;   -- Last Compile-time stack-item for which
//	                            -- the corresponding Runtime-item is saved.
//	                            -- NOTE: SAV =/= none implies TOS =/= none
//	end;
//
//
//	---------------------------------------------
//	---------   S t a c k   I t e m s   ---------
//	---------------------------------------------
//
//	Record StackItem:Object;
//	begin short integer repdist;
//	      ref(StackItem) suc;
//	      ref(StackItem) pred;
//	end;
//
//	Record ProfileItem:StackItem;
//	begin ref(ProfileDescr) spc;
//	      range(0:MaxPar) nasspar;
//	end;
//
//	Record Address:StackItem;
//	begin infix(MemAddr) Objadr;   -- Object Address
//	      range(0:MaxWord) Offset; -- Attribute Offset
//	      range(0:2) ObjState;     -- NotStacked ! FromConst ! Calculated
//	      range(0:2) AtrState;     -- NotStacked ! FromConst ! Calculated
//	end;
//
//	Visible Define NotStacked=0,FromConst=1,Calculated=2;
//
//	Record Temp:StackItem;            --- Value is pushed on RT-stack
//	begin  end;
//
//	Record Coonst:Temp;               --- Value is also in 'itm'
//	begin infix(ValueItem) itm;
//	end;
//	%title
//	--- Current Location Counter Data ---
//	Visible Ref(Qpkt) qfirst,qlast;   -- initial(none) q-list of CSEG
//	Visible Range(0:16000) qcount;    -- initial(0), No.of Qpkt's in q-list
//	------------------------------------------------------------------------
//
//	Visible Boolean reversed;          -- whether compare exchanged operands
//	Visible Boolean InMassage;         -- True while in massage
//	Visible Boolean DeadCode;          -- inital(false)
//	Visible Boolean Changeable;        -- Changereg in massage
//	Visible Range(0:255) RecDepth;     -- Current Masseur recursion depth left
//	Visible short integer stackMod1, stackMod2; -- see massage
//	Visible boolean deleteOK;          -- see massage'peepExhaust
//
//	Visible Range(0:MaxWord) MindMask; -- Registers 'mind' after last qi
//	Visible Range(0:MaxWord) PreReadMask;  -- Do 'read'  opposite normal
//	%+S Visible Range(0:MaxWord) PreWriteMask; -- Do 'write' opposite normal
//	Visible Range(0:MaxWord) PreMindMask;  -- Do 'mind'  opposite normal
//	Visible Range(0:MaxWord) NotMindMask;  -- Not 'mind' opposite normal
//
//	%-E Visible Const Range(0:nregs) accreg(3)=(0,qAL,qAX);
//	%+E Visible Const Range(0:nregs) accreg(5)=(0,qAL,qAX,0,qEAX);
//	%-E Visible Const Range(0:nregs) extreg(3)=(0,qAH,qDX);
//	%+E Visible Const Range(0:nregs) extreg(5)=(0,qAH,qDX,0,qEDX);
//	%-E Visible Const Range(0:nregs) countreg(3)=(0,qCL,qCX);
//	%+E Visible Const Range(0:nregs) countreg(5)=(0,qCL,qCX,0,qECX);
//	%-E Visible Const Range(0:nregs) datareg(3)=(0,qDL,qDX);
//	%+E Visible Const Range(0:nregs) datareg(5)=(0,qDL,qDX,0,qEDX);
//
//	Visible Const Range(0:14) NotQcond(16) = (
//	-- original:      <       <=      =       >=      >       <>         ---
//	-- test flipped:  >=      >       <>      <       <=      =          ---
//	------- 0       q_WLT   q_WLE   q_WEQ   q_WGE   q_WGT   q_WNE   7    ---
//	        0,      q_WGE,  q_WGT,  q_WNE,  q_WLT,  q_WLE,  q_WEQ,  0,
//	------- 8       q_ILT   q_ILE   q_IEQ   q_IGE   q_IGT   q_INE   15   ---
//	        0,      q_IGE,  q_IGT,  q_INE,  q_ILT,  q_ILE,  q_IEQ,  0);
//
//	Visible Const Range(0:14) RevQcond(16) = (
//	-- original:      <       <=      =       >=      >       <>         ---
//	-- data flipped:  >       >=      =       <=      <       <>         ---
//	------- 0       q_WLT   q_WLE   q_WEQ   q_WGE   q_WGT   q_WNE   7    ---
//	        0,      q_WGT,  q_WGE,  q_WEQ,  q_WLE,  q_WLT,  q_WNE,  0,
//	------- 8       q_ILT   q_ILE   q_IEQ   q_IGE   q_IGT   q_INE   15   ---
//	        0,      q_IGT,  q_IGE,  q_IEQ,  q_ILE,  q_ILT,  q_INE,  0);
//
//
//	end;

}
