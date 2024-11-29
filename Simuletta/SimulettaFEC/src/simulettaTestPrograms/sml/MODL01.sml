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

-- Visible sysroutine("GTOUTM") GTOUTM;
 --- psc must always correspond to an address in the user program
 --- GTOUTM is not called from the exception handler
 --- Status must not be changed
-- export label psc  end;


 Visible sysroutine("STREQL") STREQL;
 import infix(string) str1,str2; export boolean res  end;

Visible routine ED_STR; import infix(string) str;
begin integer i,n;
      n:=str.nchr-1;
end;


 Visible sysroutine("PRINTO") PRINTO;
 import integer key; infix(string) image; integer spc  end;

-- Visible known("SYSPRI") SYSPRI; import infix(string) img;
-- begin PRINTO(0,img,1) end;

 Visible sysroutine("TERMIN") TERMIN;
 import range(0:3) code; infix(string) msg  end;

 Visible body(PRF) BDY;
 begin
 end;

   
visible routine TEST_ADD; begin
		boolean cont;
		integer i, j, k, n, m;
		cont:=true;
		i := i + 500;
		j := 66 + j;
		k := i + j;
		n := 440;
		m := n + n;
    end;
   
visible routine TEST_SUB; begin
		boolean cont;
		integer i, j, k, n, m;
		cont:=true;
		i := i - 500;
		j := 66 - j;
		k := i - j;
		n := 440;
		m := n - n;
    end;
   
visible routine TEST_DIV; begin
		boolean cont;
		integer i, j, k, n, m;
		cont:=true;
		i := i / 500;
--		j := 66 / j;
--		k := i / j;
		n := 440;
		m := n / 10;
    end;

   
visible routine TEST_MULT; begin
		boolean cont;
		integer i, j, k, n, m;
		cont:=true;
		i := i * 500;
		j := 66 * j;
		k := i * j;
		n := 44;
		m := n * 10;
    end;
   
visible routine TEST_SWITCH; begin
	integer code,res;
		case 1:3 (code)
			when 1: res := 1001
--			when 2: res := 1002
			when 3: res := 1003
			otherwise res := -999;
      endcase;
   end;
   
visible routine TEST_EXIT; import integer code; infix(string) msg;  begin
			TERMIN(code, msg);
	end;   
end;

