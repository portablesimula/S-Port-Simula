--% %pass 2 input = 4
--% %pass 2 trace = 4
--% %pass 2 modtrc = 4

--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
%PASS 2 OUTPUT=1 -- S-Code Output Trace
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace

Module MODL01("TST 1.0");
 begin insert envir0;

       -----------------------------------------------------------------------
       ---                                                                 ---
       ---            S I M U L E T T A    T E S T B A T C H               ---
       ---                                                                 ---
       ---                                                                 ---
       -----------------------------------------------------------------------


   
   Visible routine XREST; export infix(string) s; begin
-- 		s.chradr:=@bio.utbuff(bio.utpos); s.nchr:=utlng-bio.utpos;
   end;

Visible routine XED_STR; import infix(string) str; begin
	infix(string) xstr;
	xstr := XREST;
--	bio.utpos:=bio.utpos+PUTSTR(REST,str);
end;
   
   
end;

