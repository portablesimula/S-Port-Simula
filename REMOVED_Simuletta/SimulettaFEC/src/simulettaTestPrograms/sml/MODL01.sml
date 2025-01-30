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


 Visible sysroutine("PRINTO") PRINTO;
 import integer key; infix(string) image; integer spc  end;

-- Visible known("SYSPRI") SYSPRI; import infix(string) img;
-- begin PRINTO(0,img,1) end;

 Visible sysroutine("PUTSTR") PUTSTR;
 import infix (string) item; infix(string) val;
 export integer lng;
 end;

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
   
Visible Routine TEST_INDEX; begin
	integer i;
	character c,d;
	real r(5);
	character chr(8);
	i := 3;
  	chr(5) := 'X';
  	chr(i) := 'Z';
	c := chr(5);
	d := chr(i);
%       var(dst.chradr)(i):=var(src.chradr)(i);
 end;
 
 Visible Routine TEST0; import integer i, j, k; export infix(string) s; begin
 	integer m,n;
 	i := i + 666;
 	m := j + k;
 	n := 12345;
 end;
 
 Visible Routine TEST1; import integer k; export infix(string) s; begin
 	integer w;
 	infix(string) str;
 	k := 54321;
 	w := 789;
 	s := TEST0(111,222,333);
 end;
   

Visible routine REST; export infix(string) s; begin
	integer i,j,k,l;
	name(character) chradr;
%	chradr:=@TEST_BUFF(5);
%	chradr:= name(bio.utbuff);
%	chradr:=@bio.utbuff(0);
	s.chradr:=@bio.utbuff(bio.utpos+13);
%	s.nchr:=utlng-bio.utpos;
end;
   
% Visible routine ED_STR; import infix(string) str; begin
%	bio.utpos:=bio.utpos+PUTSTR(REST,str);
% end;

   
visible routine TEST_EXIT; import integer code; infix(string) msg;  begin
			TERMIN(code, msg);
	end;   
end;

