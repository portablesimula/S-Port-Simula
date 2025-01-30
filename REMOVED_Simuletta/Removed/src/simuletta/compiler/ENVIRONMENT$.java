/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package simuletta.compiler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import simuletta.compiler.ENVIRONMENT$.ENTRY;
import simuletta.compiler.ENVIRONMENT$.Field;
import simuletta.compiler.ENVIRONMENT$.Name;
import simuletta.runtime.LABQNT$;
import simuletta.runtime.RT;
import simuletta.runtime.RT.Option;
import simuletta.utilities.Util;
//import simulettaTestBatch.area;
//import simulettaTestBatch.entity;
//import simulettaTestBatch.instance;
//import simulettaTestBatch.string;
//import simulettaTestBatch.txtqnt;

/**
 * The Class ENVIRONMENT.
 * <p>
 * The purpose of the environmental class is to encapsulate all constants,
 * procedures and classes which are accessible to all source modules. It
 * contains procedures for mathematical functions, text generation, random
 * drawing, etc.
 * <p>
 * 
 * @author Øystein Myhre Andersen
 *
 */
// public final class ENVIRONMENT$ {
public class ENVIRONMENT$ {
	static long START_TIME = System.currentTimeMillis();
	static char CURRENTLOWTEN = '&';
	static char CURRENTDECIMALMARK = '.';
	public final double maxlongreal = Double.MAX_VALUE;
	public final double minlongreal = -maxlongreal;// Double.MIN_VALUE;
	public final float maxreal = Float.MAX_VALUE;
	public final float minreal = -maxreal;// Float.MIN_VALUE;
	public final int maxrank = 256; // Character.???;
	public final int maxint = Integer.MAX_VALUE;
	public final int minint = Integer.MIN_VALUE;

	// Constructor
	public ENVIRONMENT$() {}

	public final class ENTRY {}
	public final class UNKNOWN {}
	public static boolean TRUE$=true;
	
    public class Name<T> {}
	
    public class Field<T> {}
    public static Field<?> NOFIELD$=null;
    public static Field<?> FIELD$(Class<?> class1, String... attributeIdentifier) { return null; }

    public static void CALL$(String string, ENTRY test2, Object... args) {}
    public static ENTRY ENTRY$(String routineIdentifier) { return(null); }
    public static int SIZE$(String recordIdentifier, int repCount) { return(0); }
    
    public static Object REF$(Object obj) {	return null; }

    public static Name<?> NAME$(Object obj) { return null; }
    public static Name<Character> NAME$(char[] utbuff) { return null; }
   
    public static Name<?> NAME$(int value) { return null; }
    public static Name<?> NAME$(long value) { return null; }
    public static Name<?> NAME$(float value) { return null; }
    public static Name<?> NAME$(double value) { return null; }
    public static Name<?> NAME$(char value) { return null; }
    public static Name<?> NAME$(boolean value) { return null; }


    public static void PUTVAR$(Object obj, Object value) {}
	public static void PUTVAR$(Name<?> chrADR, Object value) {}
	public static void PUTVAR$(Name<Integer> name, int value) {}
	public static void PUTVAR$(Name<Long> name, long value) {}
	public static void PUTVAR$(Name<Float> name, float value) {}
	public static void PUTVAR$(Name<Double> name, double value) {}
	public static void PUTVAR$(Name<Character> name, char c) {}
	public static void PUTVAR$(Name<Boolean> name, boolean c) {}

    public static Object GETVAR$(Name<?> xpnt) { return null; }
    public static Object GETVAR$(Object obj) { return null; }
    
    public static boolean EQ$(Object lhs, Object rhs) {	return(lhs.equals(rhs)); }
    public static boolean GE$(Object lhs, Object rhs) {	return false; }
    public static boolean GT$(Object lhs, Object rhs) {	return false; }
    public static boolean LE$(Object lhs, Object rhs) {	return false; }
    public static boolean LT$(Object lhs, Object rhs) {	return false; }
    public static boolean NE$(Object lhs, Object rhs) {	return(!(lhs.equals(rhs))); }

    public static Object ADD$(Object p, int offset) { return null; }
    public static Object ADD$(Object p, Field<?> offset) { return null; }
    
    public static int SUB$(Object p, Object q) { return 0; }

	// ************************************************************
	// *** lOCAL JUMP/LABEL - Meant for Byte-Code Engineering
	// ************************************************************
	public static LABQNT$ LABEL$(final int labelIndex,final String ident) {
		// Local LABEL - Needs ByteCode Engineering.
		return(null);
	}
//	public static void LABEL$(final LABQNT$ q) {
//		//if (RT.Option.GOTO_TRACING)	RT.TRACE("RTObject$.GOTO: " + q);
//		throw (q);
//	}

	public static void JUMPTABLE$(final int labelIndex) {
		// Local GOTO - Needs ByteCode Engineering.
		if (RT.Option.GOTO_TRACING)
			RT.TRACE("RTObject$.JUMPTABLE$: labelIndex=" + labelIndex);
//		RT.printSimulaStackTrace(0);
		if (labelIndex == 0) return;
		String msg = "Local GOTO LABEL#" + labelIndex + " Needs ByteCode Engineering.";
		RT.println("NOTE: " + msg);
		throw new RuntimeException(msg);
	}
	
//	// ************************************************************
//	// *** FRAMEWORK for NonLocal Label-Parameters in Java Coding
//	// ************************************************************
//	public final class LABQNT$ extends RuntimeException {
//		static final long serialVersionUID = 42L;
////		public final RTObject$ SL$; // Static link, i.e. the block in which the label is defined.
//		public final boolean isGlobal;  // Labels may be local to a Routine or Global in a Module
//		public final int index; // I.e. ordinal number of the Label within its Scope(staticLink).
//		public final String identifier; // To improve error and trace messages.
//
//		// Constructor
////		public LABQNT$(final RTObject$ SL$,final int index,final String identifier) {
//		public LABQNT$(final boolean isGlobal,final int index,final String identifier) {
////			this.SL$ = SL$;
//			this.isGlobal=isGlobal;
//			this.index = index;
//			this.identifier = identifier;
//		}
//
//		public String toString() {
////			return ("LABQNT$(" + SL$ + ", LABEL#" + index + ", identifier=" + identifier + ')');
//			return ("LABQNT$("+(isGlobal?"GLOBAL":"LOCAL")+", LABEL#" + index + ", identifier=" + identifier + ')');
//		}
//	}

	// ************************************************************
	// *** GOTO$ -- To avoid Java-error: "Unreachable code" after GOTO
	// ************************************************************
	public static void GOTO$(final LABQNT$ q) {
		//if (RT.Option.GOTO_TRACING)	RT.TRACE("RTObject$.GOTO: " + q);
		throw (q);
	}

	// ************************************************************
	// *** TRACING: TRACE_GOTO
	// ************************************************************
	public static void TRACE_GOTO(final String msg,final LABQNT$ q) {
		RT.TRACE(msg + " GOTO " + q);
	}

	// ************************************************************
	// *** object IS classIdentifier
	// ************************************************************
	public boolean IS$(final Object obj,final Class<?> cls) {
		return((obj == null)?false:(obj.getClass() == cls));
	}

	
	// *****************************************
	// *** Simuletta Test Environment Interface
	// *****************************************
	public static void PRINT$(String str) {
		System.out.print(str);
	}
	public static void TRACE$(int lno,String str) {
		StackTraceElement[] stackTrace=Thread.currentThread().getStackTrace(); 
//		String methodName=stackTrace[3].getMethodName();
		String callChain=""; String dot="";
		int n=stackTrace.length-1;
		for(int i=3; i<n; i++) {
			callChain=stackTrace[i].getMethodName()+dot+callChain;
			dot=".";
		}
//		System.out.println(methodName+"("+lno+") "+str);
		System.out.println(callChain+"("+lno+") "+str);
	}

	// *****************************************
	// *** Simuletta Environment Interface
	// *****************************************
	class string {} // TEMP !
	class txtqnt {} // TEMP !
	class area {} // TEMP !
	class entity {} // TEMP !
	class instance {} // TEMP !
	// From SYSR
    public static void PRINTO$(int i, string img, int j) {}
    public static int INIMAG$(int i, string img) {return 0;}
    public static void OUTIMA$(int filekey, string img) {}
    public static int INBYTE$(int filekey) {return 0;}
    public static void OUTBYT$(int filekey, int byte$) {}
    public static float RLOGAR$(float arg) {return 0;}
    public static double LOGARI$(double arg) {return 0;}
    public static float RSINUS$(float f) {return 0;}
    public static double SINUSR$(double d) {return 0;}
    public static float RSQROO$(float f) {return 0;}
    public static double SQROOT$(double d) {return 0;}
    public static float RARTAN$(float f) {return 0;}
    public static double ARCTAN$(double d) {return 0;}
    public static int REXPON$(float arg) {return 0;}
    public static int EXPONE$(double arg) {return 0;}
    // From UTIL
    public static int GTLNO$(LABQNT$ adr) {return 0;}
    public static LABQNT$ GTOUTM$() {return null;}
    public static void PUTINT$(string wfield, int i) {}
    public static void PTREAL$(string wfield, float r, int i) {}
    public static void PLREAL$(string wfield, double r, int i) {}
    public static void PUTFIX$(string wfield, float r, int i) {}
    public static void PTLFIX$(string wfield, double r, int i) {}
    public static void PTAADR$(string wfield, Field<?> field) {}
    public static void PTOADR$(string wfield, Object val) {}
    public static void PTPADR$(string wfield, LABQNT$ val) {}
    public static void PTRADR$(string wfield, ENTRY entry) {}
    public static void PTSIZE$(string wfield, int val) {}
    public static void CMOVE$(string str, string wfield) {}
    public static int GTLNID$(LABQNT$ lsc, string fld) {return 0;}
    public static void DMPOBJ$(int i, Object object, int lng) {}
    public static string TXT_TO_STR$(txtqnt nam) {return null;}
    // From STRG
    public static int GINTIN$(int i) {return 0;}
    public static int SIZEIN$(int i, int j) {return 0;}
    public static void TERMIN$(int i, string string) {}
    public static area DWAREA$(int poolsize, int i) {return null;}
    public static void ZEROAREA$(Object object, Object object2) {}
    public static double CPUTIM$() {return 0;}
    public static void init_pointer$(Object object) {}
    public static entity get_pointer$() {return null;}
    public static void set_pointer$(Object object) {}
    public static void MOVEIN$(Object object, Object object2, int lng) {}
    // From CENT
    public static void CBLNK$(string str) {}
    public static int GETINT$(string item) {return 0;}
    public static double GTREAL$(string item) {return 0;}
    public static int GTFRAC$(string item) {return 0;}
    public static void PTFRAC$(string item, int val, int n) {}
	public static void LOWTEN$(char c) {}
    // From FIL
    public static int OPFILE$(string nam, int type, string nostring, int i) {return 0;}
    public static void CLFILE$(int i, string nostring) {}
    public static void LOCATE$(int i, int loc) {}
    public static void NEWPAG$(int i) {}
    public static int GETLPP$(int i) {return 0;}
    // From LIBR
    public static string STRBUF$(int i) {return null;}
    public static int DATTIM$(Object strbuf) {return 0;}
    public static void CMOVE$(Object strbuf, string txt_TO_STR) {}
    public static int GTEXIN$(int i, Object strbuf) {return 0;}
    public static void GVIINF$(int i, int inf) {}
    public static void GIVINF$(int i, string txt_TO_STR) {}
    public static instance conv_ref$(Name<?> name) {return null;}
	public static Field<?> conv_field$(Name<?> name) {return null;}
    public static int GDSNAM$(int key, string to) {return 0;}
    public static float DRAWRP$(Name<?> name) {return 0;}
    // From MNTR
    public static LABQNT$ GTPADR$(string modid, int lno) {return null;}
    public static void BRKPNT$(LABQNT$ paddr, boolean b) {}
    public static void INITIA$(ENTRY entry) {}
//	public static void LOWTEN$(char c) {}
    public static int GDSPEC$(int code, string strbuf) {return 0;}



	
	
	// *****************************************
	// *** Basic operations ***
	// *****************************************

	/**
	 * <pre>
	 * integer procedure mod(i,j);   integer i,j;
	 * begin integer res;
	 *    res := i - (i//j)*j;
	 *    mod := if res = 0 then 0
	 *      else if sign(res) <> sign(j) then res+j
	 *      else res
	 * end mod;
	 * </pre>
	 * <p>
	 * The result is the mathematical modulo value of the parameters.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public int mod(final int i,final int j) {
		int res = i % j;
		if (res == 0)
			return (0);
		if (res * j < 0)
			return (res + j);
		return (res);
	}

	/**
	 * <pre>
	 * integer procedure rem(i,j);   integer i,j;
	 *                   rem := i - (i//j)*j;
	 * </pre>
	 * <p>
	 * The result is the remainder of an integer division.
	 * 
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public int rem(final int i,final int j) {
		return (i % j);
	}

	/**
	 * <pre>
	 * <type of e> procedure abs(e); <type> e;
	 *       abs := if e >= 0 then e else -e;
	 * </pre>
	 * 
	 * The result is the absolute value of the parameter.
	 * 
	 * @param e
	 * @return
	 */
	public int abs(final int e) {
		return (Math.abs(e));
	}

	public float abs(final float e) {
		return (Math.abs(e));
	}

	public double abs(final double e) {
		return (Math.abs(e));
	}

	/**
	 * <pre>
	 * integer procedure sign(e);    e;
	 *     sign := if e > 0 then  1
	 *        else if e < 0 then -1 else 0;
	 * </pre>
	 * 
	 * The result is zero if the parameter is zero, one if the parameter is
	 * positive, and minus one otherwise.
	 * 
	 * @param e
	 * @return
	 */
	public int sign(final double e) {
		return ((e > 0) ? (1) : ((e < 0) ? -1 : 0));
	}

	/**
	 * <pre>
	 * integer procedure entier(r); <real-type> r;
	 * begin integer j;
	 *       j := r;             ! implied conversion of "r" to integer ;
	 *       entier:= if j > r   ! implied conversion of "j" to real ;
	 *                then j-1 else j
	 * end entier;
	 * </pre>
	 * 
	 * The result is the integer "floor" of a real type item, the value always being
	 * less than or equal to the parameter. Thus, entier(1.8) returns the value 1,
	 * while entier(-1.8) returns -2.
	 * 
	 * @param d
	 * @return
	 */
	public int entier(final double d) {
		int j = (int) d;
		return ((((float) j) > d) ? (j - 1) : (j));
	}

	/**
	 * Integer Power: b ** x
	 * 
	 * @param b
	 * @param x
	 * @return
	 */
	public int IPOW$(final int b,int x) {
		// RT.println("IPOW("+b+','+x+')');
		if (x == 0) {
			if (b == 0)
				throw new RuntimeException("Exponentiation: " + b + " ** " + x + "  Result is undefined.");
			return (1); // any ** 0 ==> 1
		} else if (x < 0)
			throw new RuntimeException("Exponentiation: " + b + " ** " + x + "  Result is undefined.");
		else if (b == 0)
			return (0); // 0 ** non_zero ==> 0
		int v = b;
		while ((--x) > 0)
			v = v * b;
		return (v);
	}
	
	public int IPOW$EXACT(final int b,int x) {
		//RT.println("IPOW$EXACT("+b+','+x+')');
		if (x == 0) {
			if (b == 0)
				throw new RuntimeException("Exponentiation: " + b + " ** " + x + "  Result is undefined.");
			return (1); // any ** 0 ==> 1
		} else if (x < 0)
			throw new RuntimeException("Exponentiation: " + b + " ** " + x + "  Result is undefined.");
		else if (b == 0)
			return (0); // 0 ** non_zero ==> 0
		int v = b;
		while ((--x) > 0) {
//			v = v * b;
			//RT.println("IPOW$EXACT: x="+x+", v="+v);
		    v = Math.multiplyExact(v,b);
		}
		return (v);
	}

	/**
	 * Overloaded versions of 'addepsilon'.
	 * 
	 * <pre>
	 * <type of e> procedure addepsilon(e);   <real-type> e;
	 *     addepsilon := e + ... ; ! see below;
	 * </pre>
	 * <p>
	 * The result type is that of the parameter. The result is the value of the
	 * parameter incremented (addepsilon) or decremented (subepsilon) by the
	 * smallest positive value, such that the result is not equal to the parameter
	 * within the precision of the implementation. Thus, for all positive values of
	 * "eps",
	 * 
	 * <pre>
	 * E - eps &lt;= subepsilon(E) &lt; E &lt; addepsilon(E) &lt;= E + eps
	 * </pre>
	 * 
	 * @param x
	 * @return
	 */
	public float addepsilon(final float x) {
		return (Math.nextUp(x));
	}

	public double addepsilon(final double x) {
		return (Math.nextUp(x));
	}

	/**
	 * Overloaded versions of 'subepsilon'.
	 * 
	 * <pre>
	 * <type of e> procedure subepsilon(e);   <real-type> e;
	 *     subepsilon := e - ... ; ! see below;
	 * </pre>
	 * <p>
	 * The result type is that of the parameter. The result is the value of the
	 * parameter incremented (addepsilon) or decremented (subepsilon) by the
	 * smallest positive value, such that the result is not equal to the parameter
	 * within the precision of the implementation. Thus, for all positive values of
	 * "eps",
	 * 
	 * <pre>
	 * E - eps &lt;= subepsilon(E) &lt; E &lt; addepsilon(E) &lt;= E + eps
	 * </pre>
	 * 
	 * @param x
	 * @return
	 */
	public float subepsilon(final float x) {
		return (Math.nextDown(x));
	}

	public double subepsilon(final double x) {
		return (Math.nextDown(x));
	}

	/**
	 * <pre>
	 * character procedure char(i);  integer i;
	 *    char := ... ;
	 * </pre>
	 * 
	 * The result is the character obtained by converting the parameter according to
	 * the implementation-defined coding of characters. The parameter must be in the
	 * range 0..maxrank.
	 * 
	 * @param i
	 * @return
	 */
	public char Char(final int i) {
		return ((char) i);
	}
	public char char$(final int i) {
		return ((char) i);
	}

	/**
	 * <pre>
	 * character procedure isochar(i);  integer i;
	 *    isochar := ... ;
	 * </pre>
	 * 
	 * The result is the character obtained by converting the parameter according to
	 * the ISO 2022 standard character code. The parameter must be in the range
	 * 0..255.
	 * 
	 * @param n
	 * @return
	 */
	public char isochar(final int n) {
		return ((char) n);
	}

	/**
	 * <pre>
	 * integer procedure rank(c);  character c;
	 *    rank := ... ;
	 * </pre>
	 * 
	 * The result is the integer obtained by converting the parameter according to
	 * the implementation-defined character code.
	 * 
	 * @param c
	 * @return
	 */
	public int rank(final char c) {
		return ((int) c);
	}

	/**
	 * <pre>
	 * integer procedure isorank(c);  character c;
	 *    isorank := ... ;
	 * </pre>
	 * 
	 * The result is the integer obtained by converting the parameter according to
	 * the ISO 2022 standard character code.
	 * 
	 * @param c
	 * @return
	 */
	public int isorank(final char c) {
		return ((int) c);
	}

	/**
	 * <pre>
	 * Boolean procedure digit(c);  character c;
	 *    digit := ... ;
	 * </pre>
	 * 
	 * The result is true if the parameter is a decimal digit.
	 * 
	 * @param c
	 * @return
	 */
	public boolean digit(final char c) {
		return (Character.isDigit(c));
	}

	/**
	 * <pre>
	 * Boolean procedure letter(c);  character c;
	 *    letter := ... ;
	 * </pre>
	 * 
	 * The result is true if the parameter is a letter of the English alphabet ('a'
	 * ... 'z', 'A' ... 'Z').
	 * 
	 * @param c
	 * @return
	 */
	public boolean letter(final char c) {
		return (Character.isLetter(c));
	}

	/**
	 * <pre>
	 * character procedure lowten(c);  character c;
	 *                   if ... ! c is illegal as lowten;
	 *                   then  error("..." ! Lowten error ;)
	 *                   else begin
	 *                      lowten:= CURRENTLOWTEN; CURRENTLOWTEN:= c
	 *                    end lowten;
	 * </pre>
	 * 
	 * Changes the value of the current lowten character to that of the parameter.
	 * The previous value is returned. Illegal parameters are
	 * <p>
	 * digits, plus ("+"), minus ("-"), dot ("."), comma (","), control characters
	 * (i.e. ISO code<32), DEL (ISO code 127), and all characters with ISO code
	 * greater than 127.
	 * 
	 * @param c
	 * @return
	 */
	public char lowten(final char c) {
		if (illegalLowten(c))
			throw new RuntimeException("Illegal LOWTEN Character: " + c + "  Code=" + (int) c);
		char lowten = CURRENTLOWTEN;
		// CURRENTLOWTEN = c;
		CURRENTLOWTEN = Character.toUpperCase(c);
		return (lowten);
	}

	private boolean illegalLowten(final char c) {
		if (c <= 32)
			return (true); // SPACE is also Illegal in this implementation
		if (c >= 127)
			return (true);
		switch (c) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case '+':
		case '-':
		case '.':
		case ',':
			return (true);
		}
		return (false);
	}

	/**
	 * <pre>
	 * character procedure decimalmark(c);   character c;
	 *    if c <> '.' and then c <> ','
	 *    then error("..." ! Decimalmark error ;)
	 *    else begin
	 *            decimalmark:= CURRENTDECIMALMARK;
	 *            CURRENTDECIMALMARK:= c
	 * end decimalmark;
	 * </pre>
	 * 
	 * Changes the value of the decimal point character used by the text (de)editing
	 * procedures (cf. 8.7 and 8.8). See the TXT$ methods: getreal, getfrac, putfix,
	 * putreal and putfrac. The previous value is returned. The only legal parameter
	 * values are dot and comma.
	 * 
	 * @param c
	 * @return
	 */
	public char decimalmark(final char c) {
		char decimalmark = 0;
		if (c != '.' && c != ',') {
			throw new RuntimeException("Decimalmark error: "+c);
		} else {
			decimalmark = CURRENTDECIMALMARK;
			CURRENTDECIMALMARK = c;
		}
		return (decimalmark);
	}

	// *****************************************
	// *** Mathematical functions ***
	// *****************************************

	public double sqrt(final double r) {
		return (Math.sqrt(r));
	}

	public double sin(final double r) {
		return (Math.sin(r));
	}

	public double cos(final double r) {
		return (Math.cos(r));
	}

	public double tan(final double r) {
		return (Math.tan(r));
	}

	public double cotan(final double r) {
		return (1.0 / Math.tan(r));
	}

	public double arcsin(final double r) {
		return (Math.asin(r));
	}

	public double arccos(final double r) {
		return (Math.acos(r));
	}

	public double arctan(final double r) {
		return (Math.atan(r));
	}

	public double arctan2(final double y,final double x) {
		return (Math.atan2(y, x));
	}

	public double sinh(final double r) {
		return (Math.sinh(r));
	}

	public double cosh(final double r) {
		return (Math.cosh(r));
	}

	public double tanh(final double r) {
		return (Math.tanh(r));
	}

	public double ln(final double r) {
		return (Math.log(r));
	}

	public double log10(final double r) {
		return (Math.log10(r));
	}

	public double exp(final double r) {
		return (Math.exp(r));
	}

	// *****************************************
	// *** Extremum functions ***
	// *****************************************

	/**
	 * <pre>
	 * <type> procedure max(i1,i2); <type> i1; <type> i2;
	 * </pre>
	 * 
	 * The value is the greater of the two parameter values. Legal parameter types
	 * are text, character, real type and integer type.
	 * <p>
	 * The type of the result conforms to the rules of 3.3.1. in Simula Standard.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double max(final double x,final double y) {
		return (Math.max(x, y));
	}

	public float max(final float x,final float y) {
		return (Math.max(x, y));
	}

	public int max(final int x,final int y) {
		return (Math.max(x, y));
	}

	public char max(final char x,final char y) {
		return ((char) Math.max((int) x, (int) y));
	}

	/**
	 * <pre>
	 * <type> procedure min(i1,i2); <type> i1; <type> i2;
	 * </pre>
	 * 
	 * The value is the lesser of the two parameter values. Legal parameter types
	 * are text, character, real type and integer type.
	 * <p>
	 * The type of the result conforms to the rules of 3.3.1. in Simula Standard.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public double min(final double x,final double y) {
		return (Math.min(x, y));
	}

	public float min(final float x,final float y) {
		return (Math.min(x, y));
	}

	public int min(final int x,final int y) {
		return (Math.min(x, y));
	}

	public char min(final char x,final char y) {
		return ((char) Math.min((int) x, (int) y));
	}

	// *****************************************
	// *** Error control ***
	// *****************************************

	/**
	 * <pre>
	 * procedure error(t);   text t;
	 *    begin ... display text "t" and stop program...
	 *    end error;
	 * </pre>
	 * 
	 * The procedure "error" stops the execution of the program as if a runtime
	 * error has occurred and presents the contents of the text parameter on the
	 * diagnostic channel (normally the controlling terminal).
	 * 
	 * @param msg
	 */
	public void error(final String msg) {
		System.err.println(msg);
	}

	// *****************************************
	// *** Calendar and timing utilities ***
	// *****************************************
	/**
	 * <pre>
	 * text procedure datetime;   datetime :- Copy("...");
	 * </pre>
	 * 
	 * The value is a text frame containing the current date and time in the form
	 * YYYY-MM-DD HH:MM:SS.sss.... The number of decimals in the field for seconds
	 * is implementation-defined.
	 * 
	 * @return
	 */
	public String datetime() {
		DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String datim = LocalDateTime.now().format(form);
		return (datim);
	}

	/**
	 * <pre>
	 * long real procedure cputime;
	 * </pre>
	 * 
	 * The value is the number of processor seconds spent by the calling program.
	 * 
	 * @return
	 */
	public double cputime() {
		double cputime = System.currentTimeMillis() - START_TIME;
		return (cputime / 1000);
	}

	/**
	 * <pre>
	 * long real procedure clocktime;
	 * </pre>
	 * 
	 * @return The value is the number of seconds since midnight.
	 */
	public double clocktime() {
		LocalTime localTime = LocalTime.now();
		int hour = localTime.getHour();
		int minute = localTime.getMinute();
		int second = localTime.getSecond();
		// RT.println("ClockTime: Hour="+hour+", Minute="+minute+",
		// Second="+second);
		double time = ((hour * 60) + minute) * 60 + second;
		return (time);
	}

}
