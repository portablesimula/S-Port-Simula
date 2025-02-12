Module knwn("RTS");
 begin sysinsert rt,sysr;

       -----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---           P O R T A B L E     S I M U L A           ---
       ---            R U N T I M E     S Y S T E M            ---
       ---                                                     ---
       ---             K n o w n   R o u t i n e s             ---
       ---             V i s i b l e   t o   F E C             ---
       ---                                                     ---
       -----------------------------------------------------------


Visible define

 ENO_ITR_MIN = 0 ,
 ENO_ITR_MAX = 16 ,
 ENO_ITR_0=17,   -- Trap caused by interrupt or exception:
                 --  (The following exception messages must
                 --     be the first elements of the repetition).
 ENO_ITR_1=0,    -- Unspecified error condition.
 ENO_ITR_2=1,    -- Invalid floating point operation.
 ENO_ITR_3=2,    -- Floating point division by zero.
 ENO_ITR_4=3,    -- Floating point overflow.
 ENO_ITR_5=4,    -- Floating point underflow.
 ENO_ITR_6=5,    -- Inexact result (floating point operation).
 ENO_ITR_7=6,    -- Integer overflow.
 ENO_ITR_8=7,    -- Integer division by zero.
 ENO_ITR_9=8,    -- Illegal address trap.
 ENO_ITR_10=9,   -- Illegal instruction trap.
 ENO_ITR_11=10,  -- Breakpoint trap.
 ENO_ITR_12=11,  -- User interrupt - NOT YET IMPLEMENTED.
 ENO_ITR_13=12,  -- Cpu time limit overflow.
 ENO_ITR_14=13,  -- Continuation is impossible.
 ENO_ITR_15=14,  -- Start of statement exception - NOT YET IMPLEMENTED.
 ENO_ITR_16=15,  -- Index out of range exception
 ENO_ITR_17=16,  -- None-dot exception

 ENO_ITN_1=18,   -- Internal error in Simula Run-Time System.
 ENO_ITN_2=19,   -- Internal error in environment
 ENO_ITN_3=20,   -- Internal error, wrong code generation
 ENO_EXC_1=21,   -- Breakpoint trap, but no observation tool available.
 ENO_INI_1=22,   -- Not enough primary storage for predefined instances.
 ENO_QUA_1=23,   -- Qualification check fails.
 ENO_NON_1=24,   -- Attempt to remotely access attribute through none
                 --   (x=none in x.y).
 ENO_SYS_1=25,   -- User error or none-zero status on return
                 --   from environment routine call.
 ENO_SYS_2=26,   -- Exponentiation: Result is undefined.
 ENO_SYS_3=27,   -- Impossible to satisfy the request,
                 --   maybe because it is illegal.
 ENO_SYS_4=28,   -- Actual parameter value is out of range.
 ENO_SYS_5=29,   -- The service function is not implemented.
 ENO_SYS_6=30,   -- Illegal action.
 ENO_DSM_1=31,   -- Storage request cannot be met, not enough primary storage.
 ENO_DSM_2=32,   -- Less than "minfree" storage after compaction
 ENO_GOTO_1=33,  -- Illegal goto destination.
 ENO_DET_1=34,   -- x.Detach: x is not on the operating chain.
 ENO_RES_1=35,   -- Resume(x): x is none.
 ENO_RES_2=36,   -- Resume(x): x is not local to sub-block or prefixed block.
 ENO_RES_3=37,   -- Resume(x): x is not in detached state.
 ENO_RES_4=38,   -- Class Process is not local to prefixed block,
                 --   illegal implicit Resume.
 ENO_RES_5=39,   -- Implicit Resume(x):
                 --   Process object x is not in detached state.
 ENO_ATT_1=40,   -- Call(x): x is none.
 ENO_ATT_2=41,   -- Call(x): x is not in detached state.
 ENO_PRO_1=42,   -- Incorrect number of parameters in
                 --   call on formal or virtual procedure.
 ENO_VIR_1=43,   -- Virtual attribute has no match.
 ENO_ARR_1=44,   -- Lower/upperbound(a,i): illegal value of i.
 ENO_ARR_2=45,   -- Incorrect number of array indicies.
 ENO_ARR_3=46,   -- Array index value is less than value of lower bound.
 ENO_ARR_4=47,   -- Array index value is greater than value of upper bound.

 ENO_TXT_1=48,   -- Blanks(n):  n is negative or too large.
 ENO_TXT_2=49,   -- Text value assignment x := y:
                 --   x.Length < y.Length, maybe x == notext
 ENO_TXT_3=50,   -- Text value assignment x := y:  x.Constant = True.
 ENO_TXT_4=51,   -- Sub(i,n):  i is less than 1.
 ENO_TXT_5=52,   -- Sub(i,n):  n is negative.
 ENO_TXT_6=53,   -- t.Sub(i,n): i + n > t.Length + 1, maybe t == notext.
 ENO_TXT_7=54,   -- t.Get...:  t == notext.
 ENO_TXT_8=55,   -- t.Get...:  Non-numeric item.
 ENO_TXT_9=56,   -- t.Get...:  Numeric item is out of range.
 ENO_TXT_10=57,  -- t.Get...:  Numeric item is not complete.
 ENO_TXT_11=58,  -- t.Getchar:  t.More = False, maybe t == notext.
 ENO_TXT_12=59,  -- t.Put...:  t == notext.
 ENO_TXT_13=60,  -- t.Put...:  t.Constant = True.
 ENO_TXT_14=61,  -- t.Put...(r,n):  Fraction size specification n is negative.
 ENO_TXT_15=62,  -- t.Putchar:  t.More = False, maybe t == notext.

 ENO_FNP_MIN = 64 ,
 ENO_FNP_MAX = 70 ,
 ENO_FNP_0=63,   -- Parameter called by name:
 ENO_FNP_1=64,   -- Assignment to formal: Actual is no variable, cannot assign.
 ENO_FNP_2=65,   -- Parameter transmission: Actual is no variable.
 ENO_FNP_3=66,   -- The types of the actual and the formal parameter
                 --   are different.
 ENO_FNP_4=67,   -- The qualifications of the actual and the formal parameter
                 --   are different.
 ENO_FNP_5=68,   -- Assignment to formal:  object is not subordinate to actual.
 ENO_FNP_6=69,   -- Occurrence of formal:
                 --   actual object is not subordinate to formal.
 ENO_FNP_7=70,   -- Occurrence of formal:
                 --   actual procedure is not subordinate to formal.

 ENO_FPT_MIN = 72 ,
 ENO_FPT_MAX = 79 ,
 ENO_FPT_0=71,   -- Parameter transmission to formal or virtual procedure:
 ENO_FPT_1=72,   -- Actual object is not subordinate to formal parameter.
 ENO_FPT_2=73,   -- Actual procedure parameter is not
                 --   subordinate to formal parameter.
 ENO_FPT_3=74,   -- Actual and formal parameter are of different kinds.
 ENO_FPT_4=75,   -- Actual and formal parameter are of different types.
 ENO_FPT_5=76,   -- Actual and formal parameter are of incompatible types.
 ENO_FPT_6=77,   -- Transplantation:
                 --   actual and formal qualification are dynamically different.
 ENO_FPT_7=78,   -- Qualification of actual and formal reference array
                 --   do not coincide.
 ENO_FPT_8=79,   -- Types of actual and formal procedure
                 --   are neither coincident nor subordinate.

 ENO_FIL_1=80,   -- file.Open:  The file is open already.
 ENO_FIL_2=81,   -- file.Open:  file.FILENAME == notext.
 ENO_FIL_3=82,   -- file.OPEN = false.
 ENO_FIL_4=83,   -- file.ENDFILE == true.
 ENO_FIL_5=84,   -- file.Inimage:  file.image == notext.
 ENO_FIL_6=85,   -- file.Inimage:  file.image.Constant = true.
 ENO_FIL_7=86,   -- Directfile.Inimage:  End of file was encountered.
 ENO_FIL_8=87,   -- file.In...:  Attempt to read through end of file.
 ENO_FIL_9=88,   -- file.In...:  Non-numeric item.
 ENO_FIL_10=89,  -- file.In...:  Numeric item is out of range.
 ENO_FIL_11=80,  -- file.In...:  Numeric item is not complete.
 ENO_FIL_12=81,  -- file.Intext(n):  n is negative or too large.
 ENO_FIL_13=82,  -- file.Outimage:  file.image == notext.
 ENO_FIL_14=93,  -- file.Out...(...,w):  w < 0.
 ENO_FIL_15=94,  -- file.Out...(...,w):  w > file.image.Length
 ENO_FIL_16=95,  -- file.Out...:  file.image.Constant = true.
 ENO_FIL_17=96,  -- file.Out...(...,n,...):
                 --   Fraction size specification n is negative.
 ENO_FIL_18=97,  -- file.Outtext(t):  t.Length > file.image.Length
 ENO_FIL_19=98,  -- Printfile.Spacing(n):  n < 0  or  n > Linesperpage.
 ENO_FIL_20=99,  -- Printfile.Eject(n):  n <= 0.
 ENO_FIL_21=100, -- File.Close:  The file is closed already.
                 --   (Not by this program execution.)
 ENO_FIL_22=101, -- Illegal file operation, not compatible with this file.
 ENO_FIL_23=102, -- The external record format
                 --   is not compatible with this directfile.
 ENO_FIL_24=103, -- File.Open:  Illegal file name.
 ENO_FIL_25=104, -- file.Out...:  Output image too long.
 ENO_FIL_26=105, -- file.In...:  Input image too long.
 ENO_FIL_27=106, -- file.Out...:  The file is full.
                 --   (End of file may have been encountered.)
 ENO_FIL_28=107, -- Directfile:  Location out of range.
 ENO_FIL_29=108, -- I/O error, e.g. hardware fault.
                 --   (The error is not caused by this execution.)
 ENO_FIL_30=109, -- No write access to the file.
 ENO_FIL_31=110, -- File.Open:  Too many files open simultaneously.
 ENO_FIL_32=111, -- No read access to the file.
 ENO_FIL_33=112, -- End of file has been encountered already.
 ENO_FIL_34=113, -- Installation dependent file error. See the value of STATUS.
 ENO_SML_1=114,  -- Simulation:  (Re)Activate empties SQS.
 ENO_SML_2=115,  -- Simulation:  Cancel,Passivate or Wait empties SQS
 ENO_PRC_1=116,  -- Process.Evtime:  The process is idle.
 ENO_DRW_1=117,  -- Random drawing:
                 --   Actual array parameter is not one-dimensional.
 ENO_DRW_2=118,  -- Histd(a,u):  An element of the array a is negative.
 ENO_DRW_3=119,  -- Linear(a,b,u):
                 --   The number of elements in a and b are different.
 ENO_DRW_4=120,  -- Linear(a,b,u):
                 --   The array a does not satisfy the stated assumptions.
 ENO_DRW_5=121,  -- Negexp(a,u) :  a <= 0.
 ENO_DRW_6=122,  -- Randint(a,b,u) or Uniform(a,b,u) :   b < a.
 ENO_DRW_7=123,  -- Erlang(a,b,u):  a <= 0  or  b <= 0.
 ENO_DRW_8=124,  -- Normal(a,b,u):  b <= 0.
 ENO_DRW_9=125,  -- Erlang/Negexp/Normal/Poisson: paramater U <= 0
 ENO_STD_1=126,  -- Histo(a,b,c,d):
                 --   Array parameter is not one-dimensional.
 ENO_STD_2=127,  -- Histo(a,b,c,d):
                 --   number of elements in a <= number of elements in b
 ENO_STD_3=128,  -- Standard mathematical function call:
                 --   Parameter value is out of range.
 ENO_SWT_1=129,  -- Switch designator:  Index value is out of range.
 ENO_EXT_1=130,  -- Call on external non-SIMULA procedure:
                 --   actual label is not local.
 ENO_LTEN =131,  -- Lowten(c): c is illegal as lowten character
 ENO_DCMRK=132,  -- Decimalmark(c): c is neither '.' nor ','
 ENO_RUTIL=133   -- RTS_Utility: index out of range
;
%title E r r o r    H a n d l i  n g

 Visible known("ERRNON") ERRNON;
 begin call PEXERR(errorX)(ENO_NON_1,none) end;

 Visible known("ERRQUA") ERRQUA;
 begin call PEXERR(errorX)(ENO_QUA_1,none) end;

 Visible known("ERRSWT") ERRSWT;
 begin call PEXERR(errorX)(ENO_SWT_1,none) end;

 Visible known("ERROR") ERROR; import range(0:MAX_ENO) eno;
 begin call PEXERR(errorX)(eno,none) end;
%title ***  T e x t    H a n d l i n g   (do NOT set STATUS)  ***

Visible known("TXTREL") TXTREL; -- text value relations
import infix(txtqnt) left,right; integer code;
export boolean rel;
begin integer i;            --  Loop index.
      integer dif;          --  Difference between lengths.
      integer lng;          --  Length of common parts.
      lng:=right.lp-right.sp; dif:=lng-(left.lp-left.sp);
      if dif <> 0
      then if code = 2 then rel:=false; goto EXX1;
           elsif code = 5 then rel:=true; goto EXX2 endif;
           if dif > 0 then lng:=left.lp - left.sp endif;
      endif;
      i:=0; repeat while i < lng
      do if right.ent.cha(right.sp + i) <> left.ent.cha(left.sp + i)
         then dif:=(right.ent.cha(right.sp+i) qua integer)
                  - (left.ent.cha(left.sp+i) qua integer);
              goto EXX3;
         endif;
         i:=i + 1;
      endrepeat;
EXX3:
      case 1:6 (code)
      when 1: rel:=0 <  dif
      when 2: rel:=0  = dif
      when 3: rel:=0 <= dif
      when 4: rel:=0 >  dif
      when 5: rel:=0 <> dif
      when 6: rel:=0 >= dif
      otherwise ERROR(ENO_ITN_1);
      endcase;
EXX1:EXX2:end;


 Visible known("TRFREL") TRFREL; --- Text reference relation ---
 import infix(txtqnt) left;
        infix(txtqnt) right;
        boolean code;              -- true: =/=   false: ==
 export boolean rel;
 begin if left.sp <> right.sp then rel:=code;
    elsif left.lp <> right.lp then rel:=code;
    elsif left.ent <> right.ent then rel:=code;
    else rel:=not code endif;
 end;


Visible known("STRIP") STRIP;
import infix(txtqnt) txt; export infix(txtqnt) res;
begin boolean cont; name(character) chaadr;
      res:=txt; cont:=true;
      ---   resl.lp = 0  ===>  res = notext  ===>  res.sp = 0
      if res.lp <> 0 then chaadr:=name(res.ent.cha(res.lp-1)) endif;
      repeat --- res.lp>res.sp ==>  chaadr = name(res.ent.cha(res.lp-1))
            if res.lp <= res.sp then res:=notext; cont:=false;
            elsif var(chaadr) = ' '
            then res.lp:=res.lp - 1;            --  Strip off a blank.
                 chaadr:=name(var(chaadr)(-1)); --  Point to previous ch
            else res.cp:=res.sp; cont:=false endif;
   while cont do endrepeat;
end;
%page

 Visible known("HASH") HASH;
 import infix(txtqnt) txt; export short integer val;
     -- ***** TEXT param, not STRING - SINT export, but must be in 0:255
 begin range(0:255) tmp,shift,ch;
       -----------------------------------------------------------------
       val:=0;
       if txt.ent <> none
       then shift:=1; tmp:=0;
            repeat while txt.sp<txt.lp do
                   -- ******* truncate below - DO NOT OVERFLOW ****** --
                   tmp:=tmp + ((txt.ent.cha(txt.sp) qua integer)*shift);
                   -- *******  i.e. "rem 256" *********************** --
                   txt.sp:=txt.sp+1;  shift:=5-shift;
            endrepeat;
            val:=tmp;
       endif;
       -----------------------------------------------------------------
 end;


 Visible known("ISOCHR") ISOCHR;
 import short integer x; export character c;     -- ISOCHAR
 begin
       -------------------------
       c := x qua character;
       -------------------------
 end;

 Visible known("ISORNK") ISORNK;
 import character c; export short integer x;     -- ISORANK
 begin
       -------------------------
       x := c qua integer;
       -------------------------
 end;

%title ***  D e c l a r e d    A r r a y    I n d e x i n g  ***

 Visible known("ARGIND") ARGIND;
 import ref(arhead) head; integer dim,ind(MAX_DIM);
 begin integer i,negbas,subscript; infix(arrbnd) bnd;
       dim:=dim-1; bnd:=head.bound(dim);
       negbas:=bnd.negbas; subscript:=ind(dim);
       if subscript < bnd.lb then ERROR(ENO_ARR_3) endif;
       if subscript > bnd.ub then ERROR(ENO_ARR_4) endif;
       repeat while dim > 0
       do dim:=dim - 1; bnd:=head.bound(dim); i:=ind(dim);
          if i < bnd.lb then ERROR(ENO_ARR_3) endif;
          if i > bnd.ub then ERROR(ENO_ARR_4) endif;
          subscript:=subscript * bnd.dope  +  i;
       endrepeat;
       ---  The index will be accessed from the compiler produced code.
       tmp.int:=subscript + negbas;
 end;


 Visible known("AR2IND") AR2IND;
 import ref(arent2) arr; integer ind_1,ind_2;
 begin if ind_1 < arr.lb_1 then ERROR(ENO_ARR_3) endif;
       if ind_1 > arr.ub_1 then ERROR(ENO_ARR_4) endif;
       if ind_2 < arr.lb_2 then ERROR(ENO_ARR_3) endif;
       if ind_2 > arr.ub_2 then ERROR(ENO_ARR_4) endif;
       ---  The index will be accessed from the compiler produced code.
       tmp.int:=ind_2 * arr.dope + ind_1 + arr.negbas;
 end;

 Visible known("AR1IND") AR1IND;
 import ref(arent1) arr; integer ind;
 begin if ind < arr.lb then ERROR(ENO_ARR_3) endif;
       if ind > arr.ub then ERROR(ENO_ARR_4) endif;
       ---  The index will be accessed from the compiler produced code.
       tmp.int:=ind - arr.lb;
 end;

%title ***  P a r a m e t e r    A r r a y    I n d e x i n g  ***

 Visible known("PAGIND") PAGIND;
 import ref(arhead) head; integer dim,ind(MAX_DIM);
 begin integer i,negbas,subscript; infix(arrbnd) bnd;
       if head.sort <> S_ARHEAD then ERROR(ENO_ARR_2) endif;
       if head.ndim <> dim then ERROR(ENO_ARR_2) endif;
       dim:=dim-1; bnd:=head.bound(dim);
       negbas:=bnd.negbas; subscript:=ind(dim);
       if subscript < bnd.lb then ERROR(ENO_ARR_3) endif;
       if subscript > bnd.ub then ERROR(ENO_ARR_4) endif;
       repeat while dim > 0
       do dim:=dim - 1; bnd:=head.bound(dim); i:=ind(dim);
          if i < bnd.lb then ERROR(ENO_ARR_3) endif;
          if i > bnd.ub then ERROR(ENO_ARR_4) endif;
          subscript:=subscript * bnd.dope  +  i;
       endrepeat;
       ---  The index will be accessed from the compiler produced code.
       tmp.int:=subscript + negbas;
 end;


 Visible known("PA2IND") PA2IND;
 import ref(arent2) arr; integer ind_1,ind_2;
 begin case  0:MAX_SORT (arr.sort)
       when  S_ARENT2,S_ARREF2,S_ARTXT2:          --  It is two-dimensional.
       otherwise ERROR(ENO_ARR_2) endcase;
       if ind_1 < arr.lb_1 then ERROR(ENO_ARR_3) endif;
       if ind_1 > arr.ub_1 then ERROR(ENO_ARR_4) endif;
       if ind_2 < arr.lb_2 then ERROR(ENO_ARR_3) endif;
       if ind_2 > arr.ub_2 then ERROR(ENO_ARR_4) endif;
       ---  The index will be accessed from the compiler produced code.
       tmp.int:=ind_2 * arr.dope + ind_1 + arr.negbas;
 end;


 Visible known("PA1IND") PA1IND;
 import ref(arent1) arr; integer ind;
 begin case  0:MAX_SORT (arr.sort)
       when  S_ARENT1,S_ARREF1,S_ARTXT1:          --  It is one-dimensional.
       otherwise ERROR(ENO_ARR_2) endcase;
       if ind < arr.lb then ERROR(ENO_ARR_3) endif;
       if ind > arr.ub then ERROR(ENO_ARR_4) endif;
       ---  The index will be accessed from the compiler produced code.
       tmp.int:=ind - arr.lb;
 end;

%title  **********  known routines removed in 106 *****************

---  Calls of these standard procedures are expanded as inline S-code

-- Visible known("RMIN") RMIN;
-- import real x,y; export real val;
-- begin val:=if x < y then x else y end;
--
-- Visible known("DMIN") DMIN;
-- import long real x,y; export long real val;
-- begin val:=if x < y then x else y end;
--
-- Visible known("RMAX") RMAX;
-- import real x,y; export real val;
-- begin val:=if x > y then x else y end;
--
-- Visible known("DMAX") DMAX;
-- import long real x,y; export long real val;
-- begin val:=if x > y then x else y end;
--
-- Visible known("IABS") IABS;
-- import integer arg; export integer val;
-- begin val:=if arg > 0 then arg else -arg end;
--
-- Visible known("RABS") RABS;
-- import real arg; export real val;
-- begin val:=if arg > 0.0 then arg else -arg end;
--
-- Visible known("DABS") DABS;
-- import long real arg; export long real val;
-- begin val:=if arg > 0.0&&0 then arg else -arg end;
--
-- Visible known("RSIGN") RSIGN;
-- import real arg; export integer val;
-- begin val:=if arg > 0.0 then 1 else if arg < 0.0 then -1 else 0 end;
--
-- Visible known("DSIGN") DSIGN;
-- import long real arg; export integer val;
-- begin val:=if arg > 0.0&&0 then 1 else if arg < 0.0&&0 then -1 else 0 end;

-- Visible known("REMAIN") REMAIND;
-- import integer x,y; export integer val;
-- begin val:= x - ((x/y) * y) end;
%title

 Visible sysroutine("MODULO") MOD;
 import integer x,y; export integer val  end;

 Visible known("RENTI") RENTI;
 import real arg; export integer   val;
 begin val:=arg qua integer;
       if arg < (val qua real) then val := val - 1 endif;
 end;

 Visible known("DENTI") DENTI;
 import long real arg; export integer val;
 begin val:=arg qua integer;
       if arg < (val qua long real) then val := val - 1 endif;
 end;

 Visible known("DIGIT") DIGIT;
 import character arg; export boolean val;
 begin val:=(arg >= '0') and (arg <= '9') end;

 Visible known("LETTER") LETTER;
 import character arg; export boolean val;
 begin val:=((arg >= 'A') and (arg <= 'Z'))
         or ((arg >= 'a') and (arg <= 'z'));
 end;

 Visible sysroutine("RADDEP") RADDEP;
 import real arg; export real val  end;

 Visible sysroutine("DADDEP") DADDEP;
 import long real arg; export long real val  end;

 Visible sysroutine("RSUBEP") RSUBEP;
 import real arg; export real val  end;

 Visible sysroutine("DSUBEP") DSUBEP;
 import long real arg; export long real val  end;
%page

 Visible sysroutine("IIPOWR") IIPOWR; --- v:=b**x
 import integer b,x; export integer v  end;

 Visible sysroutine("RIPOWR") RIPOWR; --- v:=b**x
 import real b; integer x; export real v  end;

 Visible sysroutine("RRPOWR") RRPOWR; --- v:=b**x
 import real b,x; export real v  end;

 Visible sysroutine("RDPOWR") RDPOWR; --- v:=b**x
 import real b; long real x; export long real v  end;

 Visible sysroutine("DIPOWR") DIPOWR; --- v:=b**x
 import long real b; integer x; export long real v  end;

 Visible sysroutine("DRPOWR") DRPOWR; --- v:=b**x
 import long real b; real x; export real v  end; -- NOTE: real result

 Visible sysroutine("DDPOWR") DDPOWR; --- v:=b**x
 import long real b,x; export long real v  end;


%title ***  B a s i c   O u t p u t    H a n d l i n g  ***

Visible routine ED_STR;   import infix(string) str;         begin bio.utpos:=bio.utpos+PUTSTR(REST,str); end;
Visible routine ED_CHA;   import character chr;             begin bio.utbuff(bio.utpos):=chr; bio.utpos:=bio.utpos+1; end;
Visible routine ED_INT;   import integer i;                 begin bio.utpos:=bio.utpos+PUTINT2(REST,i); end;
Visible routine ED_TXT;   import infix (txtqnt) txt;        begin ED_STR(TX2STR(txt)); end
Visible routine ED_HEX;   import integer i;                 begin bio.utpos:=bio.utpos+PUTHEX(REST,i); end;
Visible routine ED_SIZE;  import size i;                    begin bio.utpos:=bio.utpos+PUTSIZE(REST,i); end;
Visible routine ED_BOOL;  import boolean b;                 begin if b then ED_STR("true") else ED_STR("false") endif; end;
Visible routine ED_FIX;   import real r; integer frac;      begin bio.utpos:=bio.utpos+PUTFIX2(REST,r,frac); end;
Visible routine ED_LFIX;  import long real r; integer frac; begin bio.utpos:=bio.utpos+PTLFIX2(REST,r,frac); end;
Visible routine ED_REA;   import real r; integer frac;      begin bio.utpos:=bio.utpos+PTREAL2(REST,r,frac); end;
Visible routine ED_LRL;   import long real r; integer frac; begin bio.utpos:=bio.utpos+PLREAL2(REST,r,frac); end;
Visible routine ED_AADDR; import field() val;               begin bio.utpos:=bio.utpos+PTAADR2(REST,val); end;
Visible routine ED_GADDR; import name() val;                begin bio.utpos:=bio.utpos+PTGADR2(REST,val); end;
Visible routine ED_OADDR; import ref() val;                 begin bio.utpos:=bio.utpos+PTOADR2(REST,val); end;
Visible routine ED_PADDR; import label val;                 begin bio.utpos:=bio.utpos+PTPADR2(REST,val); end;
Visible routine ED_RADDR; import entry() val;               begin bio.utpos:=bio.utpos+PTRADR2(REST,val); end;

Visible routine PRT;  import infix(string) t;               begin ED_STR(t); ED_OUT end
Visible routine PRT2; import infix(string) t,t2;            begin ED_STR(t); ED_STR(t2); ED_OUT end;
Visible routine PRT3; import infix(string) t,t2,t3;         begin ED_STR(t); ED_STR(t2); ED_STR(t3); ED_OUT end;

Visible routine ED_OUT;
begin infix(string) im; 
      if bio.utpos > 0 then
           im.chradr:=@bio.utbuff; im.nchr:=bio.utpos;
           SYSPRI(im); bio.utpos:=0;
      endif;
end;

Visible routine REST; export infix(string) s;
begin s.chradr:=@bio.utbuff(bio.utpos); s.nchr:=utlng-bio.utpos; end;



%title ***  U t i l i t y    R o u t i n e s  ***

Visible routine TX2STR;
import infix (txtqnt) txt; export infix (string) str;
begin str.nchr:=txt.lp - txt.sp;
      str.chradr:=if txt.lp = 0 then noname
      else name(txt.ent.cha(txt.sp));
end;

end;
