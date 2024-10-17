--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
%PASS 2 OUTPUT=1 -- S-Code Output Trace
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace
begin
--   SYSINSERT envir,modl1;
   SYSINSERT RT,SYSR,KNWN,UTIL;    

	Visible record REC:inst;
	begin integer   i;
    	  integer   j;
    	  variant   integer int;
    	            real    rea;
    	  variant   infix(string) str;
	end;

	Visible routine ALLOC;
	import size length;	export ref(inst) ins;
	begin ins:=bio.nxtAdr; bio.nxtAdr:= bio.nxtAdr + length;
		  ins.sort:= S_SUB;
	end;
       
	ref() pool;
	size poolsize;
	integer sequ;
	
	ref(REC) r1;
	ref(inst) space;
	ref(REC) r2,r3,r4,r5;
	size dist;
	size spsize;
	
	poolsize:=SIZEIN(1,sequ);
	pool:=DWAREA(poolsize,sequ);
	bio.nxtAdr:=pool;
	bio.lstAdr:=pool+poolsize;
	
	
	r1:=ALLOC(size(REC));
	space:=ALLOC(size(inst));
	r2:=ALLOC(size(REC));
	
--	dist:=r2 - r1;
--	spsize:=size(inst);
--	r3:=r2+spsize+spsize+spsize+spsize+spsize+spsize;
--	r4:=r2-spsize;
	
--	r2.str:="ABRACADAB";
 	r2.i:=4444;
--	r2.rea:=3.14;
--	r5:=(r1+size(REC))+size(inst);
	sequ:=r2.i;
--	sequ:=r5.i;
 end;
