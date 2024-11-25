 begin sysinsert rt,sysr


Visible routine REST; export infix(string) s;
begin s.chradr:=@bio.utbuff(bio.utpos); s.nchr:=utlng-bio.utpos; end;

Visible routine ED_STR; import infix(string) str; begin
--	infix(string) xstr;
--	xstr := REST;
	bio.utpos:=bio.utpos+PUTSTR(REST,str);
end;

end