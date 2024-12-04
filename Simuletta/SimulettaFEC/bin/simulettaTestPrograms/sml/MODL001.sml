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
 begin
--  insert envir0;
  insert ENVIR_RT;
       -----------------------------------------------------------------------
       ---                                                                 ---
       ---            S I M U L E T T A    T E S T B A T C H               ---
       ---                                                                 ---
       ---                                                                 ---
       -----------------------------------------------------------------------
 const infix(string) ERR_MSG(MAX_ENO) = (

 "Unspecified error condition.",
 "Invalid floating point operation." );

character xbuff(5);

Visible routine TX2STR;
import infix (txtqnt) txt; export infix (string) str; begin
	integer tull; boolean cond;
	str:="not observable";
	
--	str.nchr:=txt.lp - txt.sp;
--	str.chradr:=if txt.lp = 0 then noname
--	else name(txt.ent.cha(txt.sp));
	tull := 222 + if cond then 444 else 666;
end;

 routine ED_iID; import ref(inst) ins;
 begin ref(ptpExt) xpp; label act;
       act:=ins.pp qua ref(claptp).stm;
end;
   
   
end;

