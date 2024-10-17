/*
 * (CC) This work is licensed under a Creative Commons
 * Attribution 4.0 International License.
 *
 * You find a copy of the License on the following
 * page: https://creativecommons.org/licenses/by/4.0/
 */
package simuletta.utilities;

import static simuletta.compiler.Global.*;
import static simuletta.utilities.Util.*;
import static simuletta.compiler.common.S_Instructions.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Vector;

import simuletta.compiler.declaration.Declaration;
import simuletta.compiler.declaration.VariableDeclaration;
import simuletta.compiler.declaration.scope.Record;
import simuletta.compiler.expression.value.AttributeValue;
import simuletta.compiler.expression.value.StructuredConst;
import simuletta.compiler.parsing.Parser;

/**
 * 
 * @author Øystein Myhre Andersen
 *
 */
public class OLD_Type implements Externalizable {
	Token key;  // KeyWord or ref(classIdentifier)
	public int getKeyWord() { return(key.getKeyWord()); }
	public Object getValue() { return(key.getValue()); }

	public static final OLD_Type Integer = new OLD_Type(new Token(KeyWord.INTEGER));
	public static final OLD_Type Range = new OLD_Type(new Token(KeyWord.RANGE));
	public static final OLD_Type Range(int lower,int upper) { return (new OLD_Type(KeyWord.RANGE,lower,upper)); }
	public static final OLD_Type Real = new OLD_Type(new Token(KeyWord.REAL));
	public static final OLD_Type LongReal = new OLD_Type(new Token(KeyWord.LONG));
	public static final OLD_Type Boolean = new OLD_Type(new Token(KeyWord.BOOLEAN));
	public static final OLD_Type Character = new OLD_Type(new Token(KeyWord.CHARACTER));
//	public static final Type String = new Type(new Token(KeyWord.STRING));
	public static final OLD_Type Ref = new OLD_Type(new Token(KeyWord.REF));
	public static final OLD_Type Ref(String qual) { return (new OLD_Type(KeyWord.REF,(qual==null)?null:qual.toUpperCase())); }
	public static final OLD_Type Label = new OLD_Type(new Token(KeyWord.LABEL));
	public static final OLD_Type Destination = new OLD_Type(new Token(KeyWord.LABEL));
	public static final OLD_Type Entry = new OLD_Type(new Token(KeyWord.ENTRY));
	public static final OLD_Type Entry(String qual) { return (new OLD_Type(KeyWord.ENTRY,(qual==null)?null:qual.toUpperCase())); }
	public static final OLD_Type Field = new OLD_Type(new Token(KeyWord.FIELD));
	public static final OLD_Type Field(OLD_Type qual) { return (new OLD_Type(KeyWord.FIELD,qual)); }
	public static final OLD_Type Infix = new OLD_Type(new Token(KeyWord.INFIX));
	public static final OLD_Type Infix(String qual,int repCount) { return (new OLD_Type(KeyWord.INFIX,(qual==null)?null:qual.toUpperCase(),repCount)); }
	public static final OLD_Type Size = new OLD_Type(new Token(KeyWord.SIZE));
	public static final OLD_Type Name = new OLD_Type(new Token(KeyWord.NAME));
	public static final OLD_Type Name(OLD_Type qual) { return (new OLD_Type(KeyWord.NAME,qual)); }
	
	public static final OLD_Type StringType=Infix("string",1);


	// ***********************************************************************************************
	// *** Parsing: Type.parse
	// ***********************************************************************************************
	/**
	 * <pre>
	 * Syntax:
	 * 
	 * 		Type
	 * 		   ::= INTEGER 
	 * 		   ::= SHORT INTEGER 
	 * 		   ::= RANGE ( lower'IntegerNumber  :  upper'IntegerNumber ) 
	 * 		   ::= REAL 
	 * 		   ::= LONG REAL 
	 * 		   ::= SIZE 
	 * 		   ::= BOOLEAN 
	 * 		   ::= CHARACTER 
	 * 		   ::= LABEL 
	 * 		   ::= ENTRY ( <Profile'Identifier>? ) 
	 * 		   ::= INFIX ( Record'identifier  < : fixrep'IntegerNumber >? ) 
	 * 		   ::= REF ( Record'identifier ) 
	 * 		   ::= FIELD ( < qualifying'Type >? ) 
	 * 		   ::= NAME ( < qualifying'Type >? ) 
	 * 		   ::= Record'Identifier
	 * 
	 * </pre>
	 */
	public static OLD_Type parse() {
    	OLD_Type type=null; //Type.Notype;
//    	Util.BREAK("Type.parse: currentToken="+Parser.currentToken);
    	if(Parser.accept(KeyWord.BOOLEAN)) type=OLD_Type.Boolean;
    	else if(Parser.accept(KeyWord.CHARACTER)) type=OLD_Type.Character;
    	else if(Parser.accept(KeyWord.INTEGER)) type=OLD_Type.Integer;
		else if(Parser.accept(KeyWord.SHORT)) { Parser.expect(KeyWord.INTEGER); type=OLD_Type.Integer; }
		else if(Parser.accept(KeyWord.REAL))  type=OLD_Type.Real;
		else if(Parser.accept(KeyWord.LONG))  { Parser.expect(KeyWord.REAL); type=OLD_Type.LongReal; }
		else if(Parser.accept(KeyWord.SIZE))  type=OLD_Type.Size;
		else if(Parser.accept(KeyWord.LABEL)) type=OLD_Type.Label;
		else if(Parser.accept(KeyWord.REF))   type=OLD_Type.Ref(Parser.expectParantesedIdentifier()); 
		else if(Parser.accept(KeyWord.ENTRY)) type=OLD_Type.Entry(Parser.expectParantesedIdentifier()); 
		else if(Parser.accept(KeyWord.NAME))  type=OLD_Type.Name(acceptParantesedType()); 
		else if(Parser.accept(KeyWord.FIELD)) type=OLD_Type.Field(acceptParantesedType()); 
		else if(Parser.accept(KeyWord.INFIX))	{
			Parser.expect(KeyWord.BEGPAR);
			String classIdentifier=Parser.acceptIdentifier();
			int repCount=1;
			if(Parser.accept(KeyWord.COLON)) {
				repCount=Parser.expectIntegerConst().intValue()+1;
			}
			Parser.expect(KeyWord.ENDPAR); 
			type=OLD_Type.Infix(classIdentifier,repCount); 
		}
		else if(Parser.accept(KeyWord.RANGE))	{
			Parser.expect(KeyWord.BEGPAR);
			int lower=Parser.expectIntegerConst().intValue();
			Parser.expect(KeyWord.COLON); 
			int upper=Parser.expectIntegerConst().intValue();
			Parser.expect(KeyWord.ENDPAR); 
			type=OLD_Type.Range(lower,upper); 
		}
//		System.out.println("Type.parse: "+type);
		return(type);  
    }

	private static OLD_Type acceptParantesedType() {
		Parser.expect(KeyWord.BEGPAR);
		OLD_Type type=Parser.acceptType();
		Parser.expect(KeyWord.ENDPAR); 
		return(type);
	}
	
	// ***********************************************************************************************
	// *** Constructors
	// ***********************************************************************************************
	public OLD_Type() {} // Externalization
	  
	public OLD_Type(Token key) {
		this.key=key;
		if(key==null) Util.FATAL_ERROR("New Type - key == null");
	}

	public OLD_Type(String qual) {
		if(qual==null) Util.FATAL_ERROR("Type1");
		if(qual==null) qual="Object"; // Error recovery
		this.key=new Token(KeyWord.REF,qual);
	}

	private OLD_Type(int key,OLD_Type qual) {
//		if(qual==null) qual="Object"; // Error recovery
		this.key=new Token(key,qual);
	}

	private OLD_Type(int key,String qual) {
//		if(qual==null) Util.FATAL_ERROR("Type2");
//		if(qual==null) qual="Object"; // Error recovery
		this.key=new Token(key,qual);
	}

	private OLD_Type(int key,int lower,int upper) {
//		if(qual==null) qual="Object"; // Error recovery
		this.key=new Token("",key,lower,upper);
	}

	private OLD_Type(int key,String qual,int cnt) {
		if(qual==null) Util.FATAL_ERROR("Type3");
		if(qual==null) qual="Object"; // Error recovery
		this.key=new Token("",key,qual,cnt);
	}
	
	private boolean CHECKED=false; // Set true when doChecking is called
	public void doChecking() {
		if (CHECKED) return;
		if(Option.TRACE_CHECKER) Util.TRACE("Checking "+this);
		if(this.isReferenceType()) {
	    	Record rec=this.getQualifyingRecord();
	    	if(rec!=null) rec.doChecking();
		} else if(this.isNameType() || this.isFieldType()) {
			OLD_Type type=qualifyingType();
			if(type!=null) type.doChecking();
		}
		CHECKED=true;
	}
	

	// ***********************************************************************************************
	// *** getQualifyingRecord
	// ***********************************************************************************************
//	public RecordDeclaration getQualifyingRecord() {
//		RecordDeclaration qualifyingRecord=null; // Qual in case of ref(Qual) type; or infix(Qual) type;
//		if(isReferenceType()) {
//			String refIdent=getRefIdent();
//			if(refIdent!=null) {
//				if(!refIdent.equals("LABQNT$") && !refIdent.equals("Object")) {
//					Declaration decl=Global.getCurrentScope().find(refIdent);
//					if(decl instanceof RecordDeclaration) {
//						qualifyingRecord=(RecordDeclaration)decl;
//					} else Util.error("Illegal Type: "+this.toString()+" - "+refIdent+" is not a Record");
//				}
//			}
//		}
//		return (qualifyingRecord);
//	}
	public Record getQualifyingRecord() {
		Record qualifyingRecord=null; // Qual in case of ref(Qual) type; or infix(Qual) type;
		if(isReferenceType()) {
			String refIdent=getRefIdent();
			if(refIdent==null) {
				Util.BREAK("Record.getQualifyingRecord: refIdent="+refIdent);
			}
			if(refIdent!=null) {
				if(Option.TRACE_FIND_MEANING) Util.BREAK("Record.getQualifyingRecord: refIdent="+refIdent);
				Declaration dentry=Declaration.findMeaning(refIdent);
				if(dentry==null) IERR("Missing declaration of "+refIdent);
				if(Option.TRACE_FIND_MEANING) Util.BREAK("Record.getQualifyingRecord: dentry="+dentry);//+", QUAL="+dentry.getClass().getSimpleName());
				if(dentry instanceof VariableDeclaration) {
					VariableDeclaration q=(VariableDeclaration)dentry;
					OLD_Type tt=q.type;
					if(tt.isInfixType()) {
//						Util.BREAK("Record.getQualifyingRecord: infixType tt="+tt);
						Record rec=tt.getQualifyingRecord();
//						Util.BREAK("Record.getQualifyingRecord: infixType rec="+rec);
						return(rec);
					}
				}
//				if(!(dentry instanceof Record)) dentry=get_rec(idx);
//				if(!(dentry instanceof Record)) dentry=Declaration.getRecord(refIdent);
//				qualifyingRecord=(Record)dentry;
				if(dentry instanceof Record rec) qualifyingRecord=rec;
				else IERR("");
			}
		} else if(this.isNameType()) {
			Util.TRACE("Record.getQualifyingRecord: NameType="+this);
//			Util.STOP();
		}
			
		Util.TRACE("Record.getQualifyingRecord: "+this+"  -->  "+qualifyingRecord);
		return (qualifyingRecord);
	}

	// ***********************************************************************************************
	// *** qualifyingType
	// ***********************************************************************************************
	public OLD_Type qualifyingType() {
		OLD_Type qualifyingType=null; // Qual in case of name(Qual) type; or field(Qual) type;
	    if(this.isNameType() || this.isFieldType()) {
	    	if(key.getValue()!=null) {
	    		qualifyingType=(OLD_Type)key.getValue();
	    	}
	    }
		return (qualifyingType);
	}
	
	public String getRefIdent() {
		int keyWord=key.getKeyWord();
		if(keyWord==KeyWord.REF || keyWord==KeyWord.INFIX) {
			if(key.getValue()==null) return(null);
			return(key.getValue().toString());
		}
		return(null); 
	}

	public boolean isIntegerType() {
		return(key.getKeyWord()==KeyWord.INTEGER||key.getKeyWord()==KeyWord.RANGE); }

	public boolean isRealType() {
		return(key.getKeyWord()==KeyWord.REAL||key.getKeyWord()==KeyWord.LONG); }

	public boolean isArithmeticType() {
		return(key.getKeyWord()==KeyWord.INTEGER||key.getKeyWord()==KeyWord.REAL
			 ||key.getKeyWord()==KeyWord.LONG||key.getKeyWord()==KeyWord.RANGE); }

    public OLD_Type arith_type() {
    	int key=getKeyWord();
    	if(key==KeyWord.INTEGER) return(OLD_Type.Integer);
    	if(key==KeyWord.RANGE) return(OLD_Type.Integer);
    	if(key==KeyWord.REAL) return(OLD_Type.Real);
    	if(key==KeyWord.LONG) return(OLD_Type.LongReal);
    	ERROR("Non-arithmetic type: " + this);
    	OLD_Type.tstconv(this,OLD_Type.Integer);
    	return(OLD_Type.Integer);
    }


	public boolean isCharacterType() {
		return(key.getKeyWord()==KeyWord.CHARACTER); }

	public boolean isObjectReferenceType() {
		if(key.getKeyWord()==KeyWord.REF) return(true);
		return(getRefIdent()!=null);
	}
	  
	public boolean isRefType() {
		if(key.getKeyWord()==KeyWord.REF) return(true);
		return(false);
	}
  
	public boolean isReferenceType() {
		if(key.getKeyWord()==KeyWord.REF) return(true);
		if(key.getKeyWord()==KeyWord.INFIX) return(true);
//		if(this.equals(Type.String)) return(true);
//		return(getRefIdent()!=null);
		return(false);
	}
	  
	public boolean isInfixType() {
		if(key.getKeyWord()==KeyWord.INFIX) return(true);
		return(false);
	}
	  
	public boolean isSizeType() {
		if(key.getKeyWord()==KeyWord.SIZE) return(true);
		return(false);
	}
	  
	public boolean isFieldType() {
		if(key.getKeyWord()==KeyWord.FIELD) return(true);
		//return(getRefIdent()!=null);
		return(false);
	}
	  
	public boolean isNameType() {
		if(key.getKeyWord()==KeyWord.NAME) return(true);
		//return(getRefIdent()!=null);
		return(false);
	}
	  
	public boolean isEntryType() {
		if(key.getKeyWord()==KeyWord.ENTRY) return(true);
		//return(getRefIdent()!=null);
		return(false);
	}
  
	public boolean equals(final Object other) {
		Token thisKey=this.key; 
		if(other==null) return(false);
		Token otherKey=((OLD_Type)other).key;  
		return(thisKey.equals(otherKey));
	}
	  
//	public boolean equals(Object other) {
//		if(other !=null && other instanceof Type tp2) {
//			return(key.equals(tp2.key));
//		} else return(false);
//	}
  
  
	// ***********************************************************************************************
	// *** isConvertableTo
	// ***********************************************************************************************
	/**
     * Checks if a type-conversion is legal.
     * <p>
     * The possible return values are:
     * <ul>
     * <li>DirectAssignable - No type-conversion is necessary. E.g. integer to integer
     * <li>ConvertValue - Type-conversion with possible Runtime check is necessary. E.g. real to integer.
     * <li>ConvertRef - Type-conversion with Runtime check is necessary. E.g. ref(A) to ref(B) where B is a subclass of A.
     * <li>Illegal - Conversion is illegal
     */
    public enum ConversionKind { Illegal, DirectAssignable, ConvertValue, ConvertRef }
    public ConversionKind isConvertableTo(final OLD_Type to) {
//    	Util.BREAK("Type.isConvertableTo: this="+this+", to="+to);
	    ConversionKind result;
	    if(to==null) result=ConversionKind.Illegal;
	    else if(to.isNameType()) result=ConversionKind.ConvertRef;
	    else if(to.isEntryType()) result=ConversionKind.ConvertRef;
	    else if(to.isFieldType()) result=ConversionKind.ConvertRef;
	    else if(this.equals(to)) result=ConversionKind.DirectAssignable;
	    else if(this.isArithmeticType() && to.isArithmeticType()) result=ConversionKind.ConvertValue;
//	    else if(this==Type.String && to.isReferenceType()) result=ConversionKind.ConvertValue;
//	    else if(this.isSubReferenceOf(to)) result=ConversionKind.DirectAssignable;  
	    else if(this.isSubReferenceOf(to)) result=ConversionKind.ConvertRef;  
	    else if(to.isSubReferenceOf(this)) result=ConversionKind.ConvertRef; // Needs Runtime-Check
//	    else if(to.isNameType()) result=ConversionKind.ConvertRef;
//	    else if(to.isEntryType()) result=ConversionKind.ConvertRef;
	    else result=ConversionKind.Illegal;
	    return(result); 
    }
  
	// ref(B) is a sub-reference of ref(A) iff B is a subclass of A
	// any ref is a sub-reference of NONE, NONAME, ...
	public boolean isSubReferenceOf(final OLD_Type other) {
		String thisRef=this.getRefIdent(); // May be null for NONE
		String otherRef=other.getRefIdent(); // May be null for NONE
		boolean result=false;
//		Util.BREAK("Type.isSubReferenceOf: thisType="+this+", otherType="+other);
//		Util.BREAK("Type.isSubReferenceOf: thisRef="+thisRef+", otherRef="+otherRef);
		if(otherRef==null) result=false;  // No ref is a super-reference of NONE
		else if(thisRef==null) result=true; // Any ref is a sub-reference of NONE
		else {
			Record thisQual=this.getQualifyingRecord();
			Record otherQual=other.getQualifyingRecord();
			if(thisQual==null) result=false; // Error Recovery
			else result=(thisQual).isSubRecordOf(otherQual);
		}
		return(result); 
	}
  
	
    public static void convert(OLD_Type t1,OLD_Type t2) {
//    	tstconv(t1,t2);
//    	if(checkCompatible(t1,t2)!=null) {
    	if(!t1.equals(t2)) {
    		sCode.outinst(S_CONVERT); sCode.outtype(t2);
    		sCode.outcode();
    	}
    } //*** convert ***;

    public static void tstconv(OLD_Type t1,OLD_Type t2) {
    	if(checkCompatible(t1,t2)==null)
    		ERROR("Missing type conversion: " + t1 + " => " + t2);
    } // *** tstconv ***;

	
	public static OLD_Type checkCompatible(final OLD_Type type1,final OLD_Type type2) {
		Util.TRACE("Type.checkCompatible: type1="+type1+", type2="+type2);
		if(type1==null) {
//			if(type2.isRefType()) return(type2);
//			if(type2.isNameType()) return(type2);
//			if(type2.isSizeType()) return(type2);
//			return(null); // NUL Type 
			return(type2); // NUL Type 
		}
//		if(type2==null) return(null); // NUL Type 
		int key1=type1.key.getKeyWord();
		int key2=type2.key.getKeyWord();
		if(key1==KeyWord.INTEGER)	{
			if(key2==KeyWord.INTEGER) return(OLD_Type.Integer); 
			if(key2==KeyWord.RANGE) return(OLD_Type.Integer); 
		}
		else if(key1==KeyWord.RANGE) {
			if(key2==KeyWord.INTEGER) return(OLD_Type.Integer); 
			if(key2==KeyWord.RANGE) return(OLD_Type.Integer); 
		}
		else if(key1==KeyWord.REAL) {
			if(key2==KeyWord.REAL) return(OLD_Type.Real);
			if(key2==KeyWord.LONG) return(OLD_Type.LongReal);
		}
		else if(key1==KeyWord.LONG) {
			if(key2==KeyWord.REAL) return(OLD_Type.LongReal);
			if(key2==KeyWord.LONG) return(OLD_Type.LongReal);
		}
		else if(key1==KeyWord.CHARACTER) {
			if(key2==KeyWord.CHARACTER) return(OLD_Type.Character);
		}
		else if(type1.equals(type2)) return(type1);

//		if(type1.isInfixType() && type2.isInfixType()) {
//			Util.BREAK("Type.checkCompatible: InfixType:  type1="+type1+", type2="+type2);
//			if(type1.isSubReferenceOf(type2)) return(type2);
//		    if(type2.isSubReferenceOf(type1)) return(type1);
//		    return(type1); // All  ref(record'identifier)  are compatible in Simuletta
//		}

		if(type1.isReferenceType() && type2.isReferenceType()) {
			if(type1.isSubReferenceOf(type2)) return(type2);
		    if(type2.isSubReferenceOf(type1)) return(type1);
		    return(type1); // All  ref(record'identifier)  are compatible in Simuletta
		}

		if(type1.isFieldType() && type2.isFieldType()) {
			OLD_Type qType1=(OLD_Type)type1.key.getValue();
			OLD_Type qType2=(OLD_Type)type2.key.getValue();
			if(qType1==null) return(type2);
			if(qType2==null) return(type1);
			return(new OLD_Type(KeyWord.FIELD,checkCompatible(qType1,qType2)));
		}

		if(type1.isNameType() && type2.isNameType()) {
			OLD_Type qType1=(OLD_Type)type1.key.getValue();
			OLD_Type qType2=(OLD_Type)type2.key.getValue();
			if(qType1==null) return(type2);
			if(qType2==null) return(type1);
			return(new OLD_Type(KeyWord.NAME,checkCompatible(qType1,qType2)));
		}


		if(type1.isEntryType() && type2.isEntryType()) {
			String q1=(String)type1.key.getValue();
			String q2=(String)type2.key.getValue();
			if(q1==null) return(type2);
			if(q2==null) return(type1);
			if(q1.equals(q2)) return(type1);
		}

		return(null);  
	}
	

	public String edDefaultValue() {
		switch(getKeyWord()) {
			case KeyWord.INTEGER:
			case KeyWord.RANGE:		return("0");
			case KeyWord.SIZE:		return("0");
			case KeyWord.CHARACTER: return("0");
			case KeyWord.REAL:		return("0.0f");
			case KeyWord.LONG:		return("0.0d");
			case KeyWord.BOOLEAN:	return("false");
			case KeyWord.INFIX:		return("new "+getValue()+"()");
//			case KeyWord.STRING:	return("null");
			case KeyWord.LABEL:		return("null");
			case KeyWord.REF:		return("null");
			case KeyWord.NAME:		return("null");
			case KeyWord.FIELD:		return("null");
			case KeyWord.ENTRY:		return("null");
		default: 
			Util.FATAL_ERROR("NullValue.edValue: IMPOSIBLE !!! "+this);
			return("null"); // All other cases			
		}
	}

  
	public String toJavaType() {
		if(key==null) return("void");
	    //if(this.equals(Array)) return("array"); // ARRAY Elements 
		if(this.equals(LongReal)) return("double");
		if(this.equals(Real)) return("float");
		if(this.equals(Integer)) return("int");
		if(this.equals(Boolean)) return("boolean");
		if(this.equals(Character)) return("char");
//		if(this.equals(String)) return("TXT$");
//		if(this.equals(String)) return("String");
		if(this.equals(Size)) return("int");
		if(this.equals(Label)) return("LABQNT$");
		if(key.getKeyWord()==KeyWord.ENTRY) return("ENTRY");
		if(key.getKeyWord()==KeyWord.RANGE) return("int");
		if(key.getKeyWord()==KeyWord.REF) return(""+key.getValue());
		if(key.getKeyWord()==KeyWord.INFIX) {
			String value=""+key.getValue();
			//if(value.equalsIgnoreCase("STRING")) value="String";
			return(value);
		}
		if(key.getKeyWord()==KeyWord.NAME) {
//			Object val=key.getValue();
//			if(val==null) return("Name"); // Generic Type Name
//			Type type=(Type)val;
//			String res="Name<"+type.toJavaTypeClass()+">";
			String res="Name<?>";
			return(res);
		}
		if(key.getKeyWord()==KeyWord.FIELD) {
			Object val=key.getValue();
			if(val==null) return("Field"); // Generic Type Field
			OLD_Type type=(OLD_Type)val;
			String res="Field<"+type.toJavaTypeClass()+">";
			return(res);
		}
		return(this.toString());
	}
	 
	public String toJavaTypeClass() {
		if(key==null) return("void");
	    //Util.BREAK("Type.toJavaTypeClass: key="+key);
		if(this.equals(LongReal)) return("Double");
		if(this.equals(Real)) return("Float");
		if(this.equals(Integer)) return("Integer");
		if(this.equals(Boolean)) return("Boolean");
		if(this.equals(Character)) return("Character");
//		if(this.equals(String)) return("TXT$");
//		if(this.equals(String)) return("String");
		if(key.getKeyWord()==KeyWord.RANGE) return("Integer");
		if(key.getKeyWord()==KeyWord.REF) return(""+key.getValue());
		if(key.getKeyWord()==KeyWord.INFIX) return(""+key.getValue());
		return(this.toString());
	}
	
	
	public static final int TAG_BOOL=  1, TAG_CHAR=  2, TAG_INT=   3,
		      TAG_SINT=  4, TAG_REAL=  5, TAG_LREAL= 6,
		      TAG_AADDR= 7, TAG_OADDR= 8, TAG_GADDR= 9,
		      TAG_PADDR=10, TAG_RADDR=11, TAG_SIZE= 12;

	public void toSCode() {
		//if(key==null) return("void");
		//if(this.equals(Array)) return("array"); // ARRAY Elements 
		if(this.equals(OLD_Type.LongReal)) sCode.uttag(TAG_LREAL);
		if(this.equals(OLD_Type.Real)) sCode.uttag(TAG_REAL);
		if(this.equals(OLD_Type.Integer)) sCode.uttag(TAG_INT);
		if(this.equals(OLD_Type.Boolean)) sCode.uttag(TAG_BOOL);
		if(this.equals(OLD_Type.Character)) sCode.uttag(TAG_CHAR);
		//			if(this.equals(Type.String)) return("TXT$");
		//			if(this.equals(Type.String)) return("String");
		if(this.equals(OLD_Type.Size)) sCode.uttag(TAG_SIZE);
		if(this.equals(OLD_Type.Label)) sCode.uttag(TAG_PADDR);
		if(this.getKeyWord()==KeyWord.ENTRY) sCode.uttag(TAG_RADDR);
		if(this.getKeyWord()==KeyWord.RANGE) {
			sCode.uttag(TAG_INT);	sCode.outinst(S_RANGE);

			Object inf1=key.getValue();
			int lower=(inf1==null)?0:(int)inf1;
			sCode.outnumber(lower);

			Object inf2=key.getVal2();
			int upper=(inf2==null)?0:(int)inf2;
			sCode.outnumber(upper);
		}
		if(this.getKeyWord()==KeyWord.REF) sCode.uttag(TAG_OADDR);
		if(this.getKeyWord()==KeyWord.INFIX) {

			String qual=this.getRefIdent();
//			Record rec=getRecord(qual);
			Record rec=(Record) Declaration.findMeaning(qual);
			if(!rec.IS_SCODE_EMITTED()) ERROR("Infix record is not written yet: "+rec.identifier);
//					Util.STOP();

//			if(rec.getTag()==null) Tag.deftag(rec);
			sCode.outtag(rec.getTag());
			//System.out.println("Type.toSCode: INFIX qual="+qual+", value="+key.getValue()+", value2="+key.getVal2());
			//Util.BREAK("Type.toSCode: INFIX qual="+qual+", value="+key.getValue()+", value2="+key.getVal2());
			int info1=(int)key.getVal2();
//			if(info1>0) { sCode.outinst(S_FIXREP); sCode.outnumber(info1-1); Util.STOP(); }
			if(info1>1) { sCode.outinst(S_FIXREP); sCode.outnumber(info1-1); }
		}
		if(this.getKeyWord()==KeyWord.NAME) sCode.uttag(TAG_GADDR);
		if(this.getKeyWord()==KeyWord.FIELD) sCode.uttag(TAG_AADDR);
	}
	
	public void toDefaultSCode() {
		if(this.equals(OLD_Type.LongReal)) { sCode.outinst(S_C_LREAL); sCode.outstring("0"); }
		if(this.equals(OLD_Type.Real))     { sCode.outinst(S_C_REAL); sCode.outstring("0"); }
		if(this.equals(OLD_Type.Integer))  { sCode.outinst(S_C_INT); sCode.outstring("0"); }
		if(this.equals(OLD_Type.Boolean)) { sCode.outinst(S_FALSE); }
		if(this.equals(OLD_Type.Character)) { sCode.outinst(S_C_CHAR); sCode.outbyt((int)(' ')); }
		if(this.equals(OLD_Type.Size)) { sCode.outinst(S_NOSIZE); }
		if(this.equals(OLD_Type.Label)) { sCode.outinst(S_NOWHERE); }
		if(this.getKeyWord()==KeyWord.ENTRY) { sCode.outinst(S_NOBODY); }
		if(this.getKeyWord()==KeyWord.RANGE)  { sCode.outinst(S_C_INT); sCode.outstring("0"); }
		if(this.getKeyWord()==KeyWord.REF) { sCode.outinst(S_ONONE); }
		if(this.getKeyWord()==KeyWord.INFIX) {

			String qual=this.getRefIdent();
//			Record rec=getRecord(qual);
			Record rec=(Record) Declaration.findMeaning(qual);

			sCode.outinst(S_C_RECORD);
			sCode.outtag(rec.getTag());
			sCode.outcode();
			StructuredConst.outstruct(rec,new Vector<AttributeValue>());
			sCode.outinst(S_ENDRECORD);
		}
		if(this.getKeyWord()==KeyWord.NAME) { sCode.outinst(S_GNONE); }
		if(this.getKeyWord()==KeyWord.FIELD) { sCode.outinst(S_ANONE); }
	}
	  
	public String toString() {
		if(key==null) return("null");
		if(key.getKeyWord()==KeyWord.RANGE) return("Range("+key.getValue()+':'+key.getVal2()+')');
		if(key.getKeyWord()==KeyWord.ENTRY) return("Entry("+key.getValue()+')');
		if(key.getKeyWord()==KeyWord.INFIX) return("Infix("+key.getValue()+':'+key.getVal2()+')');
		if(key.getKeyWord()==KeyWord.REF) return("Ref("+key.getValue()+')');
		if(key.getKeyWord()==KeyWord.FIELD) return("Field("+key.getValue()+')');
		if(key.getKeyWord()==KeyWord.NAME) return("Name("+key.getValue()+')');
		if(this.equals(LongReal)) return("LONG REAL"); 
		return(key.toString());
	}
	
	public static OLD_Type inType(ObjectInput inpt) throws IOException, ClassNotFoundException {
		OLD_Type tp=(OLD_Type)inpt.readObject();
		if(tp==null) return(null);
		int key=tp.key.getKeyWord();
		if(key==KeyWord.INTEGER) return(OLD_Type.Integer);
		if(key==KeyWord.REAL) return(OLD_Type.Real);
		if(key==KeyWord.LONG) return(OLD_Type.LongReal);
		if(key==KeyWord.BOOLEAN) return(OLD_Type.Boolean);
		if(key==KeyWord.CHARACTER) return(OLD_Type.Character);
//		if(key==KeyWord.STRING) return(Type.String);
		if(key==KeyWord.LABEL) return(OLD_Type.Label);
		return(tp);

	}
	

	@Override
	public void writeExternal(ObjectOutput oupt) throws IOException {
		oupt.writeObject(key);
	}

	@Override
	public void readExternal(ObjectInput inpt) throws IOException, ClassNotFoundException {
		key=(Token)inpt.readObject();
	}
	
}
