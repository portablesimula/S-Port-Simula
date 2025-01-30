package comn;

public class extbrecord extends brecord {

//	public char status;
//	public quantity fpar;
//	public int kind;
	public String check; // If not null, then checkcode identifying compilation;
	public String modul; // Module identifier (in symtab);

	public extquantity cause; // The meaning depends on status:
//        'S':  If=/=none then an implicit ext dcl is
//              identified with this decl, and its
//              checkcode is given here.
//        'M':  The ext decl it is identified to at a
//              prefix level.
//        'B':  The first treated external declaration
//              that brougt this ext declaration in.
//        'H':  Same as 'B'.  ;
	
	public int exttagnum; // The number of external tags in this module.
                          // Set during expand-externals;
	
	public int ftag; // The first internal tag for this module
							 // in the program bringing in the module;

	public int status; // Can have the following values:
//           'S'(source)   Stems from an explicit external
//                         declaration in the source
//                         program.
//           'H'(head)     Stems only from the external
//                         head of one or more included
//                         modules.
//           'B'(body)     Stems from explicit external
//                         declaration in the body of a
//                         directely or indirectely
//                         included module.
//           'M'(marker)   Marks that an external decl
//                         that is implicitely included
//                         at this prefix level is iden-
//                         tified with an external decl
//                         at an earlier prefix level. ;


	public String toString() {
		StringBuilder sb=new StringBuilder(""+modul);
		return("EXTBRECORD: "+sb);
	}

}
