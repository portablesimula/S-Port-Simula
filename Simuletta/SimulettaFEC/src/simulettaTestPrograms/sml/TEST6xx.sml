
Module TEST6xx("TEST6xx");
 begin sysinsert rt,sysr,knwn,util,strg,cent


 Visible routine sysNam;
 import range(0:MAX_KEY) key; export infix(txtqnt) res;
 begin size lng;
 
 	lng := size(txtent:10);
 end;

%page

  INACT:  curins:=curins;   goto FILDCL3;

curins:=curins;
curins:=curins;

FILDCL3:  curins qua ref(filent).action:=COPY(txttmp);
          nxtDcl(3);
end;
