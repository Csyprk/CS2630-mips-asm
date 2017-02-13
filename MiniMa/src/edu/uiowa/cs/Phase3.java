package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase3 {

    /* Translate each Instruction object into
     * a 32-bit number.
     *
     * tals: list of Instructions to translate
     *
     * returns a list of instructions in their 32-bit binary representation
     *
     *
     */
    /*
     *get opcode/function number
     */
    private int getOpcode(Instruction instr){
	switch(instr.instruction_id){
	case 1://addui
	    return 8;
	case 2://addu
	    return 33;
	case 3://or
	    return 37;
	case 5://beq
	    return 4;
	case 6://bne
	    return 5;
	case 8://slt
	    return 42;
	case 9://lui
	    return 15;
	case 10://ori
	    return 13;
	default://should not be reached on valid inputs
	    return 0;
	}
    }
    private int handleRType(Instruction instr){
	int result = 0;
	int funct = getOpcode(instr);
	//r types have an opcode of zero
	//and instead store their opcode in the function segment
	result |= instr.rs<<21;
	result |= instr.rt<<16;
	result |= instr.rd<<11;
	result |= funct;
	return result;
    }
    private int handleIType(Instruction instr){
	int result = 0;
	int opcode = getOpcode(instr);
	result |= opcode<<26;
	result |= instr.rs<<21;
	result |= instr.rt<<16;
	result |= instr.imm;
	return result;
    }
    public static List<Integer> translate_instructions(List<Instruction> tals) {
	
        return null;
    }
}
