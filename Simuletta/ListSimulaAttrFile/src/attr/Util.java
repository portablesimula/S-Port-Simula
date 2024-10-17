package attr;

public class Util {
	public static final int TESTING=1;//4;//1;
	public static int indent=0;
	
	public static final int layoutindex=2;
	public static ByteInputStream inpt;
	public static int key;

    public static final int bufsize= 2048;
    public static final int bufmax = bufsize - 25;
    //*** bufsize-bufmax must allow for max number of bytes to be
    //    output from a quantity descriptor without strings ;
    
    public static final int zero=0;
    public static final int one=1;
    public static final int NUL=0;

    public static final int longSwap = 255; // very long string (>bufsize);
    public static final int bufSwap  = 254; // read next buffer;
    public static final int longText = 253; // long string (>= lowkey);
    public static final int mainKey  = 252; // separator between main items;
    public static final int begList  = 251; // start quantity list;
    public static final int endlist  = 250; // end   quantity list;
    public static final int protMark = 249; // quantity is protected;
    public static final int hidMark  = 248; // hidden pack;
    public static final int nestMark = 247; // for/conn nesting;
    public static final int xMark    = 246;
    public static final int yMark    = 245;
    public static final int specMark = 244; // const or const lb array;
    public static final int overMark = 243; // overloading mark ***;
    public static final int dimMark  = 242; // dimension (array/switch);
    public static final int forcMark = 241; // quantity must be created;
    public static final int thisMark = 240; // thisused,hasCode,inrTag ***;
    public static final int lowkey=240;
    public static final int hikey=255;

    public static String edMaybeKey(int k) {
    	if(k < lowkey || key > hikey) return(""+k);
    	else return(edKey(k));
    }

    public static String edKey(int k) {
        if(k==longSwap) return("longSwap["+k+']'); // very long string (>bufsize);
        if(k==bufSwap)  return("bufSwap["+k+']');  // read next buffer;
        if(k==longText) return("longText["+k+']'); // long string (>= lowkey);
        if(k==mainKey)  return("mainKey["+k+']');  // separator between main items;
        if(k==begList)  return("begList["+k+']');  // start quantity list;
        if(k==endlist)  return("endlist["+k+']');  // end   quantity list;
        if(k==protMark) return("protMark["+k+']'); // quantity is protected;
        if(k==hidMark)  return("hidMark["+k+']');  // hidden pack;
        if(k==nestMark) return("nestMark["+k+']'); // for/conn nesting;
        if(k==xMark)    return("xMark["+k+']');
        if(k==yMark)    return("yMark["+k+']');
        if(k==specMark) return("specMark["+k+']'); // const or const lb array;
        if(k==overMark) return("overMark["+k+']'); // overloading mark ***;
        if(k==dimMark)  return("dimMark["+k+']');  // dimension (array/switch);
        if(k==forcMark) return("forcMark["+k+']'); // quantity must be created;
        if(k==thisMark) return("thisMark["+k+']'); // thisused,hasCode,inrTag ***;
        return("Unknown key: "+k);
    }
    
    public static String edType(int t) {
    	switch(t) {
    	case 1: return("boolean");
    	case 2: return("character");
    	case 3: return("short");
    	case 4: return("integer");
    	case 5: return("real");
    	case 6: return("long");
    	case 7: return("ref");
    	case 8: return("text");
    	case 9: return("_pointer");
    	case 10: return("value");
    	case 11: return("label");
    	case 12: return("procedure");
    	case 13: return("array");
    	case 14: return("no type");
    	default: return(""+t);
    	}
    }


//%title ***   c a t e g    c o d e s   ***

    public static final int C_unspec=  0;  // corresponds to RTS 'm_ref';
    public static final int C_value =  1;  // corresponds to RTS 'm_value';
    public static final int C_name  =  2;  // corresponds to RTS 'm_name';
    public static final int C_local =  3;  // corresponds to RTS 'm_local';
    public static final int C_extnal=  4;  // corresponds to RTS 'm_extr';
    public static final int C_unknwn=  5;
    public static final int C_virt  =  6;
    public static final int C_block =  7;

    public static final int C_max   =  7;  // NB *** must be < 8;

    public static String C_code(int k) {
        if(k ==C_unknwn) return("default");
        if(k ==C_local ) return("local");
        if(k ==C_value ) return("value");
        if(k ==C_name  ) return("name");
        if(k ==C_unspec) return("unspec");
        if(k ==C_virt  ) return("virt");
        if(k ==C_extnal) return("extnal");
        if(k ==C_block ) return("block");
                         return("illegal");
    }

//%title ***   k i n d    c o d e s   ***

     public static final int K_ident = 0;  // corresponds to RTS 'k_smp';
     public static final int K_proc  = 1;  // corresponds to RTS 'k_pro';
     public static final int K_array = 2;  // corresponds to RTS 'k_arr';
     public static final int K_label = 3;  // corresponds to RTS 'k_lab';
     public static final int K_switch= 4;  // corresponds to RTS 'k_swt';
     public static final int K_class = 5;  // corresponds to RTS 'k_cla';
     public static final int K_rep   = 6;  // corresponds to RTS 'k_rep';
     public static final int K_record= 7;  // corresponds to RTS 'k_rec';
     public static final int K_subbl = 8;
     public static final int K_prefbl= 9;
     public static final int K_error = 10;
     public static final int K_labbl = 11;
     public static final int K_unknwn= 12;
     public static final int K_extnal= 13;

     public static final int K_max   = 13;  // NB *** must be < 16 ;

     public static String K_code(int k) {
        if(k == K_error ) return("unknwn");
        if(k == K_ident ) return("ident");
        if(k == K_array ) return("array");
        if(k == K_rep   ) return("infix array");
        if(k == K_proc  ) return("procedure");
        if(k == K_class ) return("class");
        if(k == K_label ) return("label");
        if(k == K_switch) return("switch");
        if(k == K_subbl ) return("subblock");
        if(k == K_prefbl) return("prefblock");
        if(k == K_record) return("record");
        if(k == K_labbl ) return("labbl");
        if(k == K_unknwn) return("unknwn");
        if(k == K_extnal) return("predef");
                          return("illegal");
     }

//%title ***   c l a s s i f i c    c o d e s   ***

     public static final int Clf000 =0;  //Normal userdefined declaration, used when it comes from a usual external declaration.;
     public static final int Clf001 =1;  //External procedure with binding;
     public static final int Clf002 =2;  //parameters to external procedure with binding.

//                        The rest is only used in system attr. files;

     public static final int Clf003 =3;  //Is used when it is of no specific interest.;
     public static final int Clf004 =4;  //Used for system classes.;
//                         --  Procedures implemented by routines:
     public static final int Clf005 =5;  //Used for procedure which is attribute of text
//                        ( accessed as <text expr>.<procedure> ). Should
//                        be called as a routine with an extra first GADDR
//                        parameter (address of text variable).  If the
//                        <text expr> is not a variable, this routine
//                        should not be called. Instead its successor in
//                        the declaration list of 'class _text' should be
//                        called. This is assumed to be classified with U;
     public static final int Clf006 =6;  //Used for procedure which is attribute of text
//                        ( accessed as <text expr>.<procedure> ).  Should
//                        be called as a routine with an extra first text
//                        quantity parameter (as a value).;
     public static final int Clf007 =7;  //Used for procedures local in classes that must
//                        have a reference to the object as an extra first
//                        parameter.;
     public static final int Clf008 =8;  //- as '7', but the routine may lead to garbage
//                        collection.  Thus SAVE-RESTORE must possibly
//                        enclose the call.  Except for 'setacces', these
//                        are all of type text, and the text reference is
//                        delivered on TOS. If the procedure is remotely
//                        accessed, the extra first parameter has to be
//                        kept in TMP.PNT during a possible save.;
     public static final int Clf009 =9;  //Used for class process of SIMULATION.
//                        Enables checking of encloser as simulation block;
     public static final int Clf010 =10;  //Used for procedures that are translated to routines with no special treatment.;

     public static final int Clf011 =11;  //- as '10', but the routine may lead to garbage
//                        collection.  Thus SAVE-RESTORE must possibly
//                        enclose the call.  These are all procedures of
//                        type text, and the text reference is delivered
//                        on TOS.;
     public static final int Clf012 =12;  //Used for procedure in class file which is an
//                        operation on the image. Image of the actual file
//                        should be given as extra first parameter, as a
//                        GADDR (corresponding to '5' above).;
     public static final int Clf013 =13;  //Used for standard procedures which should be
//                        translated to a direct fetch from a RTS variable (max/min functions, simulaid).;
     public static final int Clf014 =14;  //Used for type procedures local in classes that
//                        should be translated to fetching a value
//                        directely from an attribute (certain attributes
//                        of FILE and SIMULATION). Is translated to
//                        code for OADDR of the enclosure followed by a
//                        REMOTEV.;
     public static final int Clf015 =15;  //Used for type procedures local in classes that
//                        should be translated to fetching a value from an
//                        attribute through one level of indirection.  Is
//                        translated to code for OADDR followed by two
//                        REMOTEV's.  Currently used for Simulation'time
//                        only.;
//                        -- Classification of parameters:
     public static final int Clf016 =16;  //Used for parameters to procedures that must be
//                        translated to usual parameters to routines.;
//%                       === Special classification codes ===
//%                       These codes enable the compiler to recognize
//%                       inline coded procedures, etc.
     public static final int Clf017 =17;  //- as '16', but parameter checking suppressed;
     public static final int Clf018 =18;  //Used for parameters to routines class. > 19;
     public static final int Clf019 =19;  //Used for text parameters of loadchar/storechar
//                         - prepare for (S-code) index;
//         ----- Inline coded procedures:
     public static final int Clf020 =20;  //rem;
     public static final int Clf021 =21;  //int abs;
     public static final int Clf022 =22;  //real abs;
     public static final int Clf023 =23;  //lreal abs;
     public static final int Clf024 =24;  //int sign;
     public static final int Clf025 =25;  //real sign;
     public static final int Clf026 =26;  //lreal sign;
     public static final int Clf027 =27;  //char;
     public static final int Clf028 =28;  //isochar;
     public static final int Clf029 =29;  //rank;
     public static final int Clf030 =30;  //isorank;
     public static final int Clf031 =31;  //loadchar;
     public static final int Clf032 =32;  //storechar;
     public static final int Clf033 =33;  //sint min;
     public static final int Clf034 =34;  //int min;
     public static final int Clf035 =35;  //real min;
     public static final int Clf036 =36;  //lreal min;
     public static final int Clf037 =37;  //sint max;
     public static final int Clf038 =38;  //int max;
     public static final int Clf039 =39;  //rela max;
     public static final int Clf040 =40;  //lreal max;
     public static final int Clf041 =41;  //sourceline;
     public static final int Clf042 =42;  //text'more;
     public static final int Clf043 =43;  //imagefile'more;
     public static final int Clf044 =44;  //text'pos;
     public static final int Clf045 =45;  //imagefile'pos;
     public static final int Clf046 =46;  //text'length;
     public static final int Clf047 =47;  //imagefile'length;
     public static final int Clf048 =48;  //text'start;

     public static final int Clfmax =48;

     public static String Cl_code(int k) {
    	 if(k<=Clfmax) return(""+k);
    	 return("illegal");
     }



     public static String edIndent() {
    	 String res="";
    	 for(int i=0;i<indent;i++) res=res+"      ";
    	 return(res);
     }

     public static void ERROR(String msg) {
    	 System.out.println("\n============================================================================");
    	 System.out.println("ERROR: "+msg);
    	 for(int i=0;i<5;i++) {
    		 System.out.println("   NEXT BYTE="+inpt.inbyte());			
    	 }
    	 System.out.println("============================================================================");
    	 EXIT(); // TODO Auto-generated method stub
     }

     public static void expxerr(String key) {
    	 ERROR("Expecting key: "+key);
     }

     public static void NOTIMPL(String msg) {
    	 System.out.println("NOT IMPLEMENTED: "+msg);
    	 EXIT();
     }

     public static void EXIT() {
    	 System.out.println("FORCED EXIT:  -- FORTSETT HER SEINERE");
    	 Thread.dumpStack();
    	 System.exit(-1);
     }

}
