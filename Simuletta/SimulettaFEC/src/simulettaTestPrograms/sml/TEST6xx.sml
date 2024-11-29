--%PASS 1 INPUT=5 -- Input Trace
--%PASS 1 OUTPUT=1 -- Output Trace
--%PASS 1 MODTRC=4 -- Module I/O Trace
--%PASS 1 TRACE=4 -- Trace level
--%PASS 2 INPUT=1 -- Input Trace
%PASS 2 OUTPUT=1 -- S-Code Output Trace
--%PASS 2 MODTRC=1 -- Module I/O Trace
--%PASS 2 TRACE=1 -- Trace level
--%TRACE 2 -- Output Trace

 -- MAIN Program:
 begin
-- 	 insert envir0,modl01,modl02;
 	 insert envir0xx,modl01xx,modl02xx;
--      sysinsert rt,sysr,knwn,util;

    
--    TEST_ADD;
--    TEST_SUB;
--    TEST_DIV;
--    TEST_MULT;
    
--    TEST_SWITCH;
    
--    GET;

--	ERRNON;
	errorX:=entry(EXERR);
	call PEXERR(errorX)(666,none);
	
--	TEST_EXIT(444, "END PROGRAM");
--	TERMIN(0, "END PROGRAM");
end
