
--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
--%PASS 2 OUTPUT=1 -- S-Code Output Trace  --- SKAL INN IGJEN
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace

Module sysr("RTS");
 begin sysinsert rt;

       -----------------------------------------------------------
       ---  COPYRIGHT 1989 by                                  ---
       ---  Simula a.s.                                        ---
       ---  Oslo, Norway                                       ---
       ---                                                     ---
       ---        P O R T A B L E     S I M U L A              ---
       ---         R U N T I M E     S Y S T E M               ---
       ---                                                     ---
       ---  S y s t e m   a n d   K n o w n   R o u t i n e s  ---
       ---         N o t   v i s i b l e   t o   F E C         ---
       ---                                                     ---
       -----------------------------------------------------------


 Visible known("SYSPRI") SYSPRI; import infix(string) img;
 begin
       -----------------------------
--     PRINTO(bio.sysout.key,img,1);
       PRINTO(0,img,1);
       -----------------------------
 end;

 Visible known("SYSPRO") SYSPRO;
 import infix(string) msg,img; export integer filled;
 begin
       -----------------------------
       BREAKO(bio.sysout.key,msg);
       if status=0 then filled:=fINIMA(bio.sysin.key,img) endif;
       -----------------------------
 end;

 Visible known("CBLNK") C_BLNK; import infix(string) str;
 begin repeat while str.nchr > 0
       do var(str.chradr):=' ';      -- Fill in a blank character value.
          str.chradr:=name(var(str.chradr)(1));   -- Increment one char.
          str.nchr:=str.nchr - 1;
       endrepeat;
 end;

 Visible known("CMOVE") C_MOVE; import infix(string) src,dst;
 begin integer i,n,rst; i:= -1; n:=src.nchr; rst:=dst.nchr-n;
       if rst < 0 then n:=dst.nchr; rst:=0 endif;
       repeat i:=i+1 while i < n     --- Move characters
       do var(dst.chradr)(i):=var(src.chradr)(i) endrepeat;
--	SETOPT(2,1); -- Option.RT_InstrStep=val;
       repeat while rst > 0          --- Blank fill
       do var(dst.chradr)(n):=' '; n:=n+1; rst:=rst-1 endrepeat;
--	SETOPT(2,0); -- Option.RT_InstrStep=val;
 end;

% Visible known("TXTREP") TXTREP;
% import infix(string) str1,str2; range(1:6) code; export boolean rel;
% begin ---  code,rel == (1,<) (2,=) (3,<=) (4,>) (5,<>) (6,>=)
%       range(0:MAX_TXT) length, i; short integer diff, ldiff;
%       length:=str2.nchr; ldiff:=length - str1.nchr; rel:=false;
%       if ldiff <> 0
%       then if code=2 then            goto E1 endif;
%            if code=5 then rel:=true; goto E2 endif;
%            if ldiff>0 then length:=str1.nchr endif
%       endif;
%       i:=0;
%       repeat while i < length do
%              diff:=   (var(str1.chradr)(i) qua integer)
%                     - (var(str2.chradr)(i) qua integer);
%              if diff <> 0 then goto SETRES endif;
%              i:= i + 1;
%       endrepeat;
%       diff:=ldiff;
% SETRES:
%       case 1:6 (code)
%       when 1: rel := 0 <  diff
%       when 2: rel := 0  = diff
%       when 3: rel := 0 <= diff
%       when 4: rel := 0 >  diff
%       when 5: rel := 0 <> diff
%       when 6: rel := 0 >= diff
%       otherwise status:=27 endcase;
% E1:E2:
% end;

 Visible known("STRIPP") STRIPP;
 import infix(string) str;
 export range(0:MAX_TXT) filled;          -- note: not integer export
 begin boolean cont; name(character) chaadr;
       filled := str.nchr;
       if filled > 0
       then chaadr:=name(var(str.chradr)(filled-1));
            repeat while var(chaadr) = ' '
                do filled:=filled-1;
                   if filled=0 then goto E endif;
                   chaadr:=name(var(chaadr)(-1)); --  Point to previous char.
            endrepeat
       endif;
 E:end;

 Visible known("BINTXT") BINTXT;
 import range(0:MAX_KEY) key; infix(string) str;
 export range(0:MAX_TXT) filled;
 begin character c;
       filled:=0;
       repeat while filled < str.nchr do
              c:=fINBYT(key) qua character;
              if status>0 then goto E endif;
              var(str.chradr)(filled):=c; filled:=filled+1;
       endrepeat;
E: end;

 Visible known("BOUTXT") BOUTXT;
 import range(0:MAX_KEY) key; infix(string) str;
 begin range(0:MAX_TXT) i;
       i:=0;
       repeat while i < str.nchr do
              fUTBYT(key,var(str.chradr)(i) qua integer); i:=i+1;
              if status<>0 then goto E endif;
       endrepeat;
E: end;

 Visible known("EXTGC") EXTGC;
 import size request; export boolean extend;
 begin --- decide whether to extend storage or do a garbage collection
       -----------------------------
--       extend:=true
       extend:=false
       -----------------------------
 end;

 Visible known("PRTCHR") PRTCHR;
 import character c; export boolean res;  -- true if char c is printable
 begin
       -----------------------------
       res:= (' ' <= c) and (c <= (126 qua character))
       -----------------------------
 end;

 Visible sysroutine("INITIA") INITIA;
 import entry(PXCHDL) exchdl  end;

 Visible sysroutine("SETOPT") SETOPT;
 import range(0:5) code; integer val  end;
 -- code 0:	Option.RT_Dumps=val;
 -- code 1:	Option.RT_Trace=val;
 -- code 2:	Option.RT_InstrStep=val;
 -- code 3:	Option.RT_InstrList=val;
 -- code 4:	Option.CALL_TRACE==val;
 -- code 5:	Option.STORE_CHECK=val;

 Visible sysroutine("TERMIN") TERMIN;
 import range(0:3) code; infix(string) msg  end;
%title ***   I n f o r m a t i o n    E x c h a n g e   ***

 Visible sysroutine("DMPSEG") DMPSEG;
 import infix(string) segnam; integer start,lng  end;

 Visible sysroutine("DMPENT") DMPENT;
 import ref() rtAddr;  end;

 Visible sysroutine("DMPOOL") DMPOOL; -- Dump POOL_n
 import integer n;  end;

 Visible sysroutine("VERBOSE") VERBOSE;
 export boolean result  end;

 Visible sysroutine("GINTIN") GINTIN;
 import range(0:127) index; export integer result  end;

 Visible sysroutine("GTEXIN") GTEXIN;
 import range(0:127) index; infix(string) item;
 export integer filled  end;

 Visible sysroutine ("SIZEIN") SIZEIN;
 import range(0:127) index; range(0:255) ano;
 export size result  end;

 Visible sysroutine("GVIINF")  GVIINF;
 import range(0:127) index; integer inform  end;

 Visible sysroutine("GIVINF")  GVTINF;
 import range(0:127) index; infix(string) inform  end;

 Visible known("CLOCKT") CLOCKT;
 export long real sec;
 begin
       --------------------------------------
       sec := 0.0&&0;
       --------------------------------------
 end;

 Visible sysroutine("CPUTIM") CPUTIM;
 export long real sec  end;

 Visible sysroutine ("DWAREA")  DWAREA;
 import size lng; range(0:255) ano;
 export ref() pool  end;

 Visible sysroutine("MOVEIN") MOVEIN;
 import ref() from,to; size length  end;

%title ***   F i l e   H a n d l i n g   ***

 Visible sysroutine("OPFILE") OPFILE;
 import  infix(string)      spec;   -- dsetspec;
         range(0:MAX_FIL)   type;   -- dsettype;
         infix(string)      action;
         integer            imglmg; -- img_lng;
 export  range(0:MAX_KEY)   key;    -- filekey;
 -- action encoding: (a digit gives the rank of the character, e.g. 0 is NUL)
 --      action == <0 ! 1 >          -- shared/noshared
 --                <0 ! 1 >          -- append/noappend
 --                <0 ! 1 ! 2 >      -- create/nocreate/anycreate
 --                <0 ! 1 ! 2 >      -- readonly/writeonly/readwrite
 --                <0 ! 1 >          -- purge/nopurge
 --                <0 ! 1 ! 2 ! 3 ! 4 ! 5 >
 --                -- rewind/norewind/next/previous/repeat/release
 --                <<char>>          -- bytesize: rank(char) (!0! default)
 --                <<c1><c2>>        -- move:<rank(c1)*256+rank(c2)>
 --                ( <l><string> )*  -- unknown access modes
 --                0                 -- terminating NUL character
 --
 -- The action string will always be at least 10 chars long, encoded
 -- with the predefined modes in the above given sequence (e.g. char
 -- number 3 will always specify the CREATE mode). If no value is given 
 -- for some mode, RTS will insert the appropriate default character
 -- at the relevant position. These defaults are:
 --
 --      in(byte)file:     "!0!!1!!1!!0!!1!!2!!0!!0!!0!!0!!0!"
 --      out(byte)file:    "!1!!1!!2!!1!!1!!2!!0!!0!!0!!0!!0!"
 --      direct(byte)file: "!1!!1!!1!!2!!1!!5!!0!!0!!0!!0!!0!"
 --
 -- If an unknown (i.e. non-Sport-defined) value are given as parameter
 -- to procedure "setaccess", the first character must be '%' (percent),
 -- otherwise "setaccess" returns FALSE (in all other cases it is TRUE).
 -- Accepted values will be concatenated with the standard string, with 
 -- '%' replaced by a character (l) whose rank gives the length of the
 -- string, excluding the overwritten '%'.
 -- The action string is always terminated by the NUL character ('!0!').
 end;

 Visible sysroutine("CLFILE") CLFILE;
 import range(1:MAX_KEY) key; infix(string) action  end;
 -- see OPFILE for encoding of action string --
%page

 Visible sysroutine("LSTLOC") LSTLOC;
 import range(1:MAX_KEY) key; export integer res  end;

 Visible sysroutine("MAXLOC") MXLOC;
 import range(1:MAX_KEY) key; export integer res  end;

 Visible sysroutine("CHKPNT") CHECKP;
 import range(1:MAX_KEY) key; export boolean res  end;

 Visible sysroutine("LOCKFI") LOCKFI;
 import range(1:MAX_KEY) key; real lim; integer loc1,loc;
 export integer res  end;

 Visible sysroutine("UNLOCK") UPLOCK;
 import range(1:MAX_KEY) key  end;

 Visible sysroutine("INIMAG") fINIMA;
 import range(1:MAX_KEY) key; infix(string) image;
 export integer filled  end;

 Visible sysroutine("OUTIMA")  fUTIMA;
 import range(1:MAX_KEY) key; infix(string) img  end;

 Visible sysroutine("BREAKO") BREAKO;
 import range(1:MAX_KEY) key; infix(string) img  end;

 Visible sysroutine("LOCATE") fLOCAT;
 import range(1:MAX_KEY) key; integer loc  end;

 Visible sysroutine("DELETE") DELETE;
 import range(1:MAX_KEY) key; export boolean result end;

 Visible sysroutine("GDSNAM") GDSNAM;
 import range(0:MAX_BYT) key; infix(string) str;
 export integer filled  end;

 Visible sysroutine("GDSPEC") GDSPEC;
 import range(1:3) code; infix(string) spec;
 export integer filled  end;

 Visible sysroutine("GETLPP") GETLPP;
 import range(1:MAX_KEY) key; export integer lpp end;

 Visible sysroutine("NEWPAG") NEWPAG;
 import range(1:MAX_KEY) key  end;

 Visible sysroutine("PRINTO") PRINTO;
 import range(1:MAX_KEY) key; infix(string) image; integer spc  end;

 Visible sysroutine("STREQL") STREQL;
 import infix(string) str1,str2; export boolean res  end;

 Visible sysroutine("INBYTE") fINBYT;
 import range(1:MAX_KEY) key; export range(0:MAX_BYT) byte  end;

 Visible known("IN2BYT") fIN2BY;
 import range(1:MAX_KEY) key; export range(0:MAX_2BT) val;
 begin val:=fINBYT(key);
       if status = 0 then val:=val * 256 + fINBYT(key) endif;
 end;

 Visible sysroutine("OUTBYT") fUTBYT;
 import range(1:MAX_KEY) key; range(0:MAX_BYT) byte  end;

 Visible known("OUT2BY") fUT2BY;
 import range(1:MAX_KEY) key; range(0:MAX_2BT) val;
 begin range(0:MAX_BYT) byte;
       byte:=val / 256; fUTBYT(key,byte);
       if status = 0 then fUTBYT(key,val-(byte*256)) endif;
 end;

%title ***   E d i t i n g   a n d   d e - e d i t i n g   ***

 Visible sysroutine("GETINT") GETINT;
 import infix (string) item; export integer res end;

 Visible sysroutine("GTREAL") GTREAL;
 import infix (string) item; export long real res  end;

 Visible sysroutine("GTFRAC") GTFRAC;
 import infix (string) item; export integer res  end;

 Visible sysroutine("PUTSTR") PUTSTR;
 import infix (string) item; infix(string) val;
 export integer lng;
 end;

 Visible sysroutine("PUTINT") PUTINT;
 import infix (string) item; integer val  end;

 Visible sysroutine("PUTINT2") PUTINT2;
    import infix (string) item; integer val
    export integer lng;
 end;

 Visible sysroutine("PUTSIZE") PUTSIZE;
    import infix (string) item; size val
    export integer lng;
 end;
 
 Visible sysroutine("PUTHEX") PUTHEX;
    import infix (string) item; integer val
    export integer lng;
 end;

 Visible sysroutine("PUTFIX") PUTFIX;
 import infix (string) item; real val; integer frac  end;

 Visible sysroutine("PUTFIX2") PUTFIX2;
	import infix (string) item; real val; integer frac;
    export integer lng;
 end;

 Visible sysroutine("PTLFIX") PTLFIX;
 import infix (string) item; long real val; integer frac  end;

 Visible sysroutine("PTLFIX2") PTLFIX2;
	import infix (string) item; long real val; integer frac;
    export integer lng;
 end;

 Visible sysroutine("PTREAL") PTREAL;
 import infix (string) item; real val; integer frac  end;
 
 Visible sysroutine("PTREAL2") PTREAL2;
    import infix (string) item; real val; integer frac; 
    export integer lng;
 end;

 Visible sysroutine("PLREAL") PLREAL;
 import infix (string) item; long real val; integer frac  end;
 
 Visible sysroutine("PLREAL2") PLREAL2;
    import infix (string) item; long real val; integer frac; 
    export integer lng;
 end;

 Visible sysroutine("PTFRAC") PTFRAC;
 import infix (string) item; integer val,n  end;

 Visible sysroutine ("PTSIZE") PTSIZE;
 import infix(string) item; size val  end;

 Visible sysroutine ("PTOADR") PTOADR;
 import infix(string) item; ref() val  end;

 Visible sysroutine ("PTOADR2") PTOADR2;
 import infix(string) item; ref() val;
 export integer lng;
 end;

 Visible sysroutine ("PTAADR") PTAADR;
 import infix(string) item; field() val  end;

 Visible sysroutine ("PTAADR2") PTAADR2;
 import infix(string) item; field() val;
 export integer lng;
 end;

 Visible sysroutine ("PTGADR") PTGADR;
 import infix(string) item; name() val  end;

 Visible sysroutine ("PTGADR2") PTGADR2;
 import infix(string) item; name() val;
 export integer lng;
 end;

 Visible sysroutine ("PTPADR") PTPADR;
 import infix(string) item; label val  end;

 Visible sysroutine ("PTPADR2") PTPADR2;
 import infix(string) item; label val;
 export integer lng;
 end;

 Visible sysroutine ("PTRADR") PTRADR;
 import infix(string) item; entry() val  end;

 Visible sysroutine ("PTRADR2") PTRADR2;
 import infix(string) item; entry() val;
 export integer lng;
 end;

%title ***   S t a n d a r d   P r o c e d u r e s   ***

 Visible sysroutine("DRAWRP") DRAWRP; -- cannot change STATUS
 import name(integer) u; export long real val  end;

 Visible sysroutine("DATTIM") DATTIM;
 import infix(string) item; export integer filled  end;

 Visible sysroutine("LOWTEN") LTEN;
 import character c  end;

 Visible sysroutine("DCMARK") DECMRK;
 import character c  end;

 Visible sysroutine ("RSQROO") RSQROO;
 import real arg; export real val  end;

 Visible sysroutine("SQROOT") SQROOT;
 import long real arg; export long real val  end;

 Visible sysroutine ("RLOGAR") RLOGAR;
 import real arg; export real val  end;

 Visible sysroutine("LOGARI") LOGARI;
 import long real arg; export long real val  end;

 Visible sysroutine("RLOG10") RLOG10;
 import real arg; export real val  end;

 Visible sysroutine("DLOG10") DLOG10;
 import long real arg; export long real val  end;

 Visible sysroutine ("REXPON") REXPON;
 import real arg; export real val  end;

 Visible sysroutine("EXPONE") EXPONE;
 import long real arg; export long real val  end;

 Visible sysroutine("RSINUS") RSINUS;
 import real arg; export real val  end;

 Visible sysroutine("SINUSR") SINUSR;
 import long real arg; export long real val  end;

 Visible sysroutine("RCOSIN") RCOSIN;
 import real arg; export real val  end;

 Visible sysroutine("COSINU") COSINU;
 import long real arg; export long real val  end;

 Visible sysroutine("RTANGN") RTANGN;
 import real arg; export real val  end;

 Visible sysroutine("TANGEN") TANGEN;
 import long real arg; export long real val  end;

 Visible sysroutine("RCOTAN") COTANR;
 import real arg; export real val  end;

 Visible sysroutine("COTANG") COTANG;
 import long real arg; export long real val  end;

 Visible sysroutine("RARTAN") RARTAN;
 import real arg; export real val  end;

 Visible sysroutine("ARCTAN") ARCTAN;
 import long real arg; export long real val  end;

 Visible sysroutine("RARCOS") RARCOS;
 import real arg; export real val  end;

 Visible sysroutine("ARCCOS") ARCCOS;
 import long real arg; export long real val  end;

 Visible sysroutine("RARSIN") RARSIN;
 import real arg; export real val  end;

 Visible sysroutine("ARCSIN") ARCSIN;
 import long real arg; export long real val  end;

 Visible sysroutine("RATAN2") ATAN2R;
 import real y,x; export real val  end;

 Visible sysroutine("ATAN2") ATAN2;
 import long real y,x; export long real val  end;

 Visible sysroutine("RSINH") SINHR;
 import real arg; export real val  end;

 Visible sysroutine("SINH") SINH;
 import long real arg; export long real val  end;

 Visible sysroutine("RCOSH") COSHR;
 import real arg; export real val  end;

 Visible sysroutine("COSH") COSH;
 import long real arg; export long real val  end;

 Visible sysroutine("RTANH") TANHR;
 import real arg; export real val  end;

 Visible sysroutine("TANH") TANH;
 import long real arg; export long real val  end;

%title ***   O b s e r v a t i o n   t o o l s   ***
 Visible sysroutine ("BEGDEB") BEGDEB; --- new in 108.2
 --- no status setting possible
 end;

 Visible sysroutine ("ENDDEB") ENDDEB; --- new in 108.2
 --- no status setting possible
 import label ctnue end;

 Visible sysroutine ("BEGTRP") BEGTRP; --- new in 108.2
 --- no status setting possible
 export short integer trapCode end;

 Visible sysroutine ("ENDTRP") ENDTRP; --- new in 108.2
 --- no status setting possible
 end;

 Visible sysroutine ("GTPADR") GTPADR;
 --- Any nonzero status taken to mean: parameters outside source
 import infix(string) modid; integer lno;
 export label padr  end;

 Visible sysroutine("GTOUTM") GTOUTM;
 --- psc must always correspond to an address in the user program
 --- GTOUTM is not called from the exception handler
 --- Status must not be changed
 export label psc  end;

 Visible sysroutine("GTLNID")  GTLNID;
 --- item either contains (truncated, status 24) line identification
 --- (including moduleid.), or it is NOT CHANGED (status 19)
 import label adr; infix(string) item; export integer filled  end;

 Visible sysroutine("GTLNO") GTLNO;
 --- lno is either a source line number, or status 27 set
 import label pad; export integer lno  end;

 Visible sysroutine("BRKPNT") BRKPNT;
 import label adr; boolean sw  end;

 Visible sysroutine("STMNOT") STMNOT;
 import boolean sw  end;

 Visible sysroutine("DMPOBJ")  DMPOBJ;
 import range(1:MAX_KEY) key; ref() obj; size lng  end;


end;
