package edu.uiowa.cs;

import java.util.LinkedList;
import java.util.List;

public class Phase1 {

    /* Translates the MAL instruction to 1-3 TAL instructions
     * and returns the TAL instructions in a list
     *
     * mals: input program as a list of Instruction objects
     *
     * returns a list of TAL instructions (should be same size or longer than input list)
     */
    public static List<Instruction> mal_to_tal(List<Instruction> mals) {
	List<Instruction> tals = new LinkedList<Instruction>();
	for(Instruction instr : mals){
		//determine if instruction is in tal
		int id = instr.instruction_id;
		switch (instr.instruction_id){
			case 100://blt r1, r2, label
			//slt $1, r1, r2 
			Instruction slt1 = new Instruction(9,1,instr.rs,instr.rt,
							 0,0,0,instr.label_id,
							 instr.branch_label);
			tals.add(slt1);
			//beq $1, $0, label
			Instruction beq1 = new Instruction(5,0,1,0,0,0,0,
							  instr.label_id,
							  instr.branch_label);
			tals.add(beq1);
			break;
			case 101: //bge r1, r2, label
			//slt $1, r1, r2
			Instruction slt2 = new Instruction(9,1,instr.rs,instr.rt,
							 0,0,0,instr.label_id,
							 instr.branch_label);
			tals.add(slt2);
			//bne $1, $0, label
			Instruction bne2 = new Instruction(6,0,1,0,0,0,0,
							  instr.label_id,
							  instr.branch_label);
			tals.add(bne2);
			break;
			default:
			//instruction in tal
			tals.add(instr);
		}
		
	}
        return null;
    }
}
