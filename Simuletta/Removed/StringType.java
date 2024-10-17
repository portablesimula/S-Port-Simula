package simuletta.compiler.expression;

import static simuletta.compiler.common.S_Instructions.*;

import java.util.Vector;

import simuletta.compiler.declaration.Declaration;
import simuletta.compiler.declaration.VariableDeclaration;
import simuletta.compiler.expression.value.AttributeValue;
import simuletta.compiler.expression.value.GeneralAddress;
import simuletta.compiler.expression.value.SimpleValue;
import simuletta.compiler.expression.value.StructuredConst;
import simuletta.compiler.expression.value.Value;
import simuletta.utilities.Type;

/**
 * Strings.
 * 
 * It is adopted as a convention the interface module should contain the
 * record definition:
 * 
 * 		Record string; info "TYPE";
 * 		begin name(character) chradr; integer nchr end;
 * 
 * @author Øystein Myhre Andersen
 *
 */
public class StringType {
	
//	private static int index_of_string;            // index of STRING
//	private static IdentSymbol chradrBox, nchrBox; // attr of STRING
//	public static void initStringIdentifiers() {
//		//! *****  init STRING identifiers  **** ;
//	    index_of_string=DEFIDENTSYMB("string");
//	    chradrBox=(IdentSymbol)symtab[DEFIDENTSYMB("chradr")];
//	    nchrBox=(IdentSymbol)symtab[DEFIDENTSYMB("nchr")];
//	}

	public static boolean checkStringType(Type type) {
		if(type.isInfixType()) {
			String qual=type.getRefIdent();
//			int idx=GETSYMB(qual);
//			if(idx==index_of_string) return(true);
			if(qual.equals("string")) return(true);
		}
		return(false);
	}

	
	//%title ******   P A R S E R :   new_string   ******
//	public static StructuredConst new_string(Vector<Declaration> declset, String t, Symbol cbox) {
//		// ******  NOTE: DEFIDENT must NEVER be called here!!!!  ****** ;
//		Expression v; String t1;
//	      // ***  const character <id>=(C1,C2, ... Cn)  ***;
//		Quant q=new Quant(Type.Character);
//	    q.symbol=cbox;
//	    q.count=t.length(); q.read_only=true; q.global=false;
//	    // global=false because this quant is only used to define the
//	    // actual string value, never ref'd directly
//	    q.initval=new Vector<Expression>(); q.initval.add(new SimpleValue(S_TEXT,t));
//	    declset.add(q);
//	    StructuredConst structuredConst=new StructuredConst();
//	    structuredConst.ident=index_of_string; structuredConst.attributeValues=new Vector<AttributeValue>();
//	    v=new GeneralAddress(cbox);
//	    structuredConst.attributeValues.add(new AttributeValue(chradrBox,v));
//	    t1=""+t.length(); v=new SimpleValue(S_C_INT,t1);
//	    structuredConst.attributeValues.add(new AttributeValue(nchrBox,v));
//	    return(structuredConst);
//	}

	public static StructuredConst new_string(Vector<Declaration> declset, String t, String identifier) {
		// ******  NOTE: DEFIDENT must NEVER be called here!!!!  ****** ;
		Expression v; String t1;
	      // ***  const character <id>=(C1,C2, ... Cn)  ***;
		VariableDeclaration q=new VariableDeclaration(false,Type.Character,identifier);
	    q.count=t.length(); q.read_only=true; q.global=false;
	    // global=false because this quant is only used to define the
	    // actual string value, never ref'd directly
	    q.initval=new Vector<Value>(); q.initval.add(new SimpleValue(S_TEXT,t));
	    declset.add(q);
	    StructuredConst structuredConst=new StructuredConst();
	    structuredConst.ident="string"; structuredConst.attributeValues=new Vector<AttributeValue>();
	    v=new GeneralAddress(identifier);
	    structuredConst.attributeValues.add(new AttributeValue("chradr",v));
	    t1=""+t.length(); v=new SimpleValue(S_C_INT,t1);
	    structuredConst.attributeValues.add(new AttributeValue("nchr",v));
	    return(structuredConst);
	}

	
	
}
