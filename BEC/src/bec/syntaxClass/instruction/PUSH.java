package bec.syntaxClass.instruction;

import bec.compileTimeStack.Address;
import bec.compileTimeStack.CTStack;
import bec.segment.ProgramSegment;
import bec.syntaxClass.programElement.Variable;
import bec.util.Global;
import bec.util.Scode;
import bec.util.Util;

public class PUSH extends Instruction {
	int instr;
	int tag;
	
	public PUSH(int instr) {
		this.instr = instr;
		parse();
	}

	/**
	 * stack_instruction ::= push obj:tag | pushv obj:tag
	 * 
	 * End-Condition: Scode'nextByte = First byte after the tag
	 */
	public void parse() {
		tag = Scode.inTag();
	}
	
	@Override
	public void doCode() {
//	      when S_PUSH,S_PUSHV:
//        InTag(%tag%); v:=DISPL(tag.HI).elt(tag.LO);
		System.out.println("PUSH.doCode: tag="+Scode.edTag(tag)+"  "+tag);
//		Variable var = (Variable) Global.Display.get(tag);
		Variable var = (Variable) Global.getMeaning(tag);
		System.out.println("PUSH.doCode: var="+var);
//        case 0:K_Max (v.kind)
//        when K_GlobalVar: d:=v qua IntDescr.adr
//%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//%-E                 if AdrSegid(d)=DGROUP.val then d.sbireg:=oSS endif;
//%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//        when K_ExternVar:
//                 d.kind:=extadr;
//%-E                 d.sbireg:=0;
//%+E                 d.sibreg:=NoIBREG;
//                 d.rela.val:=v qua ExtDescr.adr.rela;
//                 d.smbx:=v qua ExtDescr.adr.smbx;
//%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//%-E                 if AdrSegid(d)=DGROUP.val then d.sbireg:=oSS endif;
//%-E                 --------   TEMP  TEMP  TEMP TEMP  TEMP  -------
//        when K_LocalVar:
//             d.kind:=locadr; d.rela.val:=0;
//             d.loca:=v qua LocDescr.rela;
//%-E             d.sbireg:=bOR(oSS,rmBP);
//%+E             d.sibreg:=bEBP+NoIREG;
//        when K_Parameter:
//             d.kind:=reladr; d.segmid.val:=0;
//             d.rela.val:=v qua LocDescr.rela;
//%-E             d.sbireg:=bOR(oSS,rmBP);
//%+E             d.sibreg:=bEBP+NoIREG;
//        when K_Export:
//             d.kind:=reladr; d.segmid.val:=0;
//             d.rela.val:=v qua LocDescr.rela;
//%-E             d.sbireg:=bOR(oSS,rmBP);
//%+E             d.sibreg:=bEBP+NoIREG;
//        otherwise a:=noadr;
//%+D                  edit(errmsg,v);
//                  IERR("Illegal push of: ")
//        endcase;
		CTStack.push(new Address(var.quant.type.tag,0,var.address));
//        if v.kind=K_Parameter
//        then TOS.repdist:= - wAllign(%TOS.repdist%) endif;
        if(instr == Scode.S_PUSHV) {
        	Util.GQfetch();
        }
		CTStack.dumpStack();
//		Util.IERR("NOT IMPL");
	}

	@Override
	public void printTree(final int indent) {
		sLIST(indent, toString());
	}
	
	public String toString() {
		return Scode.edInstr(instr) + " " + Scode.edTag(tag);
	}
	

}
