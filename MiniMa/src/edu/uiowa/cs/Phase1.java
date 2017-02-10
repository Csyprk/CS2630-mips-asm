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
							 0,0,0,instr.label_id,0);
			//beq $1, $0, label
			Instruction beq1 = new Instruction(5,0,1,0,0,0,0,0,
							  instr.branch_label);
			tals.add(slt1);
			tals.add(beq1);
			break;
			case 101: //bge r1, r2, label
			//slt $1, r1, r2
			Instruction slt2 = new Instruction(9,1,instr.rs,instr.rt,
							 0,0,0,instr.label_id,0);

			//bne $1, $0, label
			Instruction bne2 = new Instruction(6,0,1,0,0,0,0,0,
							  instr.branch_label);
			tals.add(slt2);
			tals.add(bne2);
			break;
			case 1: //addui r1, r2, imm
			//if the immediate is longer than 16 bits
			// then we need to translate
			int imm = instr.immediate;
			if((imm&0xFFFF)!=imm){
				//first load the value into a register
				int low = imm&0xFFFF;
				int high = imm&0xFFFF0000;
				//lui $1, high
				Instruction lui = new Instruction(9,1,0,0,high,0,0,
								  instr.label_id,0);
				//ori $1, $0, low
				Instruction ori = new Instruction(10,1,0,0,low,0,0,
								  0, 0);
				//addu r1, r2, $1
				Instruction addu = new Instruction(2,0,0,0,high,0,0,
								   0,0);
				tals.add(lui);
				tals.add(ori);
				tals.add(addu);
				break;
			}
			//we don't need to translate, so fall through to default
			default:
			//instruction in tal
			tals.add(instr);
		}
		
	}
        return tals;
    }
}
