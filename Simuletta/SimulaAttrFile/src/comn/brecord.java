package comn;

public class brecord {

	public quantity declquant;
	public quantity fpar;
	public quantity favirt;
//  public idpack  hidlist;   // Contains blno of par-brec
//                             when this is procedure body-brec;
//%+N      public idpack   formals;   //formal parameter list, or ident seen;
//  public stackedb preinsp;   // stacked at INSPECT;

	public int     blno, blev,  rtblev;
    public int dcltag,stmtag,inrtag;
    public int kind;          // - for coding, see below;
//%+K      boolean isGlobal;
    public boolean thisused;        // - also marks prior procedure;
    public boolean localclasses;
    public boolean descriptorpr;    // descriptor for this class produced;
    public boolean hasCode;         // - see below;
    public int inspected;     // <>0: this class is inspected;
    public int     connests;
    public int npar;      // Accumulated in pref.chain;
    public int navirt;
    public int line1;     // not used : line2;


	public String toString() {
		StringBuilder sb=new StringBuilder();
		if(declquant!=null) sb.append(" declquant=("+declquant+')');
		if(fpar!=null) sb.append(" fpar=("+fpar+')');
		return("BRECORD: "+sb);
	}

}
