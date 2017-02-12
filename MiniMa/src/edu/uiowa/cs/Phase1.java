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
            // (Case 100) Recieved : blt rs, rt, labelx
            // Translate to:
            /*
             * slt $at, rs, rt
             * bne $at, $zero, labelx
             * (and mal_to_tal returns 2)
             */
            case 100://blt r1, r2, label 
            //slt $1, r1, r2 
            Instruction slt1 = new Instruction(8,1,instr.rs,instr.rt,
                             0,0,0,instr.label_id,0);
            //bne $1, $0, label
            // bne rs rt imm
            Instruction bne1 = new Instruction(6,0,1,0,0,0,0,0,
                              instr.branch_label);
            tals.add(slt1);
            tals.add(bne1);
            break;
            
            // (Case 101) Recieved: bge rs, rt, labelx
            // Translate to:
            /*
             * slt $at, rs, rt    (rd rs rt)
             * beq $at, $zer0, labelx (rs rt imm)
             * (and mal_to_tal return 2)
             */
            case 101: //bge r1, r2, label
            //slt $1, r1, r2
            Instruction slt2 = new Instruction(8,1,instr.rs,instr.rt,
                             0,0,0,instr.label_id,0);

            //bne $1, $0, label
            Instruction beq2 = new Instruction(5,0,1,0,0,0,0,0,
                              instr.branch_label);
            tals.add(slt2);
            tals.add(beq2);
            break;
            
            // (Case 1) Recieved: addiu rt, rs, Immediate (when immediate is too large)
            // Translate to:
            /*
             * lui $at, Upper 16-bit immediate (rt, imm)
             * ori $at, $at, lower 16-bit immediate (rt rs imm)
             * addu rt, rs, $at   (rd rs rt)
             */
            case 1: //addui r1, r2, imm
            //if the immediate is longer than 16 bits
            // then we need to translate
            int imm = instr.immediate;
            if((imm&0xFFFF)!=imm){
                //first load the value into a register
                int low = imm&0x0000FFFF;
                int high = imm&0xFFFF0000;
                high = high>>16;
                //lui $1, high
                Instruction lui = new Instruction(9,0,0,1,high,0,0,
                                  instr.label_id,0);
                //ori $1, $0, low
                Instruction ori = new Instruction(10,0,1,1,low,0,0,
                                  0, 0);
                //addu r1, r2, $1
                Instruction addu = new Instruction(2,instr.rt,instr.rs,1,0,0,0,
                                   0,0);
                tals.add(lui);
                tals.add(ori);
                tals.add(addu);
                break;
            }
            tals.add(instr);
            break;
            
            // (Case 10) Recieved: ori rt, rs, Immediate (when immediate is too large)
            // Translate to:
            /*
             * lui $at, Upper 16-bit immediate
             * ori $at, $at, lower 16-bit immediate
             * addu rt, rs, $at
             */
            case 10: //ori $rt, $rs, imm  (might be immediate according to instructions)
            int imm2 = instr.immediate;
            if((imm2&0xFFFF)!=imm2){
                //first load the value into a register
                int low2 = imm2&0xFFFF;
                int high2 = imm2&0xFFFF0000;
                high2 = high2>>16;
                //lui $1, high
                Instruction lui2 = new Instruction(9,0,0,1,high2,0,0,
                                  instr.label_id,0);
                //ori $1, $0, low
                Instruction ori2 = new Instruction(10,0,1,1,low2,0,0,
                                  0, 0);
                //addu r1, r2, $1
                Instruction addu2 = new Instruction(2,instr.rt,instr.rs,1,0,0,0,
                                   0,0);
                tals.add(lui2);
                tals.add(ori2);
                tals.add(addu2);
                break;
            }
            tals.add(instr);
            break;
            //we don't need to translate, so fall through to default
            default:
            //instruction in tal
            tals.add(instr);
        }
        
    }
        return tals;
    }
}
