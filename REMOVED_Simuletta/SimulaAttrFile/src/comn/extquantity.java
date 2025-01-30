package comn;

public class extquantity extends quantity {
	extbrecord module;
    //     comment: Module is the module to which it is local.  All locals
    //              in separate modules are represented by extquantities,
    //              also the declquant of the extbrecord itself, in which
    //              case module==descr.        ;
	public int clf; // "classific", see sortcodes ;
	public int longindic;
       
    //     LONGINDIC:   The rank of this variable is not zero if this is
    //                  a procedure that is overloaded on the parameter type.
    //                  -- see CHECKER1 for usage --
    //                  It is given after "classific" to attributefile,
    //                  as a negative number in front of "number of tags". ;

    public extquantity(extbrecord module) {
    	this.module=module;
    }


//	public String toString() {
//		StringBuilder sb=new StringBuilder(symb);
//		return("EXTQUANTITY: "+sb);
//	}

}
