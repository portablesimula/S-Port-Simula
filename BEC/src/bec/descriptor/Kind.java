package bec.descriptor;

public class Kind {

//	---------     O b j e c t   K i n d   C o d e s      ---------
//
//	 Define K_Qfrm1=1,K_Qfrm2=2,K_Qfrm2b=3,K_Qfrm3=4;
//	 Define K_Qfrm4=5,K_Qfrm4b=6,K_Qfrm4c=7,K_Qfrm5=8,K_Qfrm6=9;
//	 --- Descriptors ---
	public static final int K_RecordDescr=10,K_TypeRecord=11,K_Attribute=12;
	public static final int K_Parameter=13,K_Export=14,K_LocalVar=15;
	public static final int K_GlobalVar=16,K_ExternVar=17;
	public static final int K_ProfileDescr=18,K_IntRoutine=19,K_ExtRoutine=20;
	public static final int K_IntLabel=21,K_ExtLabel=22,K_SwitchDescr=23;
//	 --- Stack Items ---
	public static final int K_ProfileItem=24,K_Address=25,K_Temp=26,K_Coonst=27;
	public static final int K_Result=28;
//	 --- Arrays etc. ---
//	public static final int K_RefBlock=29,K_WordBlock=30,K_AddrBlock=31;
//	 --- Others ---
	public static final int K_BSEG=32;
	public static final int K_Exit=33;
	public static final int K_Retur=34;
//
	public static final int K_Max=35;  // Max value of object kind codes  + 1


//	---------     P r o f i l e    K i n d    C o d e s      ---------

	public static final int P_ROUTINE=0      ; // Normal local routine profile
	public static final int P_VISIBLE=1      ; // Normal visible routine profile
	public static final int P_INTERFACE=2    ; // Interface profile
	public static final int P_SYSTEM=3       ; // System routine (not inline)
	public static final int P_KNOWN=4        ; // Known routine (not inline)
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

}
