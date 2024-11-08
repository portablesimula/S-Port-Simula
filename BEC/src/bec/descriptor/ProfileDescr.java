package bec.descriptor;

import java.util.Vector;

//Record ProfileDescr:Descriptor;  -- K_ProfileDescr     SIZE = (6+npar*2) align 4
//begin range(0:MaxByte) npar;     -- No.of parameters
//      range(0:1) WithExit;
//      range(0:MaxParByte) nparbyte;
//      range(0:P_max) Pkind;
//      infix(ParamSpec) Par(0);   -- Parameter Specifications
//end;
public class ProfileDescr extends Descriptor {
	int npar;     // No.of parameters
	boolean withExit;
	int nparbyte;
	int Pkind; // Profile Kind
	Vector<ParamSpec> Par; // Parameter Specifications
	
//	Record ParamSpec; info "TYPE";
//	begin range(0:MaxType) type;
//	      range(0:MaxByte) count;
//	end;
	class ParamSpec {
		int type;
		int count;
	}


}
