package simuletta.compiler;

public class PROGINFO$ {
	final String file; // Simuletta Source File - e.g.  program.sim
	final String ident;
	final int[] LINEMAP$;
//	PROGINFO$(String ident,int[] lineMap)
	public PROGINFO$(final String file,final String ident,final int... lineMap)
	{ this.file=file;
	  this.ident=ident;
	  this.LINEMAP$=lineMap;
	}
}
