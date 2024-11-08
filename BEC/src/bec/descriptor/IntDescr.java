package bec.descriptor;

import bec.segment.MemAddr;

//Record IntDescr:Descriptor;      -- K_Globalvar
//begin infix(MemAddr) adr;        -- K_IntLabel
//end;                             -- K_IntRoutine   Local Routine
public class IntDescr extends Descriptor {
	MemAddr adr;
}
