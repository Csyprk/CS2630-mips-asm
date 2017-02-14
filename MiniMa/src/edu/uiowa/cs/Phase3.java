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
     * 1                addiu   I-type   (addiu $rt, $rs, imm)
       2                addu    R-type   (addu $rd, $rs, $rt)
       3                or      R-type   (or $rd, $rs, $rt)
       5                beq     I-type   (beq $rs, $rt, imm)
       6                bne     I-type   (bne $rs, $rt, imm)
       8                slt     R-type   (slt $rd, $rs, $rt)
       9                lui     I-type   (lui $rt, imm)
       10               ori     I-type   (ori $rt, $rs, imm)
       
               31   26  | 25  21 | 20   16 | 15   11 | 10   06 | 05    0 |
    // R-type = opcode  | $rs    |   $rt   |    $rd  |   shamt |  funct  |
    // I-type = opcode  | $rs    |   $rt   | $imm                        |
     *get opcode/function number
     */
    private static int getOpcode(Instruction instr){
    switch(instr.instruction_id){
    case 1://addiu
        return 9;
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
    private static String extendLength(int base, int desiredLength)
    {String baseString = Integer.toBinaryString(base);
     // This case means that there's leading 0's or 1's in the conversion, as we checked for greater than 16 bits)
     if(baseString.length() > desiredLength)
        return baseString.substring(baseString.length()-16, baseString.length());
     
     int i = baseString.length();
     for(; i < desiredLength; i++)
     {if (base < 0)
         baseString = "1" + baseString;
      else
         baseString = "0" + baseString;
        }
     return baseString;
    }
    
    // opcode = 6 bits
    // $rs = 5 bits
    // $rt = 5 bits
    // $rd = 5 bits
    // shamt = 5 bits
    // funct = 6 bits
    private static int convertRType(Instruction instr){
        String result = "";
        result = result + extendLength(0,6);
        result = result + extendLength(instr.rs,5);
        result = result + extendLength(instr.rt,5);
        result = result + extendLength(instr.rd,5);
        result = result + extendLength(0,5);
        result = result + extendLength(getOpcode(instr),6);
        return convertRIType(result);
    }
    
    //opcode = 6 bits
    //$rs = 5 bits
    // $rt = 5 bits
    // imm = 16 bits
    private static int convertIType(Instruction instr){
        String result = "";
        result = result + extendLength(getOpcode(instr),6);
        result = result + extendLength(instr.rs,5);
        result = result + extendLength(instr.rt,5);
        result = result + extendLength(instr.immediate,16);
        return convertRIType(result);
    }
    
	// This method was created because converting binary strings to Integer's could be too long using Integer.parseInt
	// As a result, by breaking this into hexString's, we can not only compare answers easier, but also get better results
    private static int convertRIType(String instr){
        String result = "";
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(0,4),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(4,8),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(8,12),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(12,16),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(16,20),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(20,24),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(24,28),2));
        result = result + Integer.toHexString(Integer.parseInt(instr.substring(28,32),2));
        return Integer.parseInt(result,16);
    }
    
	/* (Initial code. I ended up going a different way so if this way works we can delete this
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
    result |= instr.immediate;
    return result;
    }
    */
    
    private static int convertInstruction(Instruction instr){
       switch(instr.instruction_id){
    case 1://addiu
        return convertIType(instr);
    case 2://addu R
        return convertRType(instr);
    case 3://or R
        return convertRType(instr);
    case 5://beq
        return convertIType(instr);
    case 6://bne
        return convertIType(instr);
    case 8://slt R
        return convertRType(instr);
    case 9://lui
        return convertIType(instr);
    case 10://ori
        return convertIType(instr);
    default://should not be reached on valid inputs
        return 0;
    } 
    }
    
    public static List<Integer> translate_instructions(List<Instruction> tals) {
        List<Integer> translated = new LinkedList<Integer>();
        for(Instruction instr : tals)
            {translated.add(Integer.valueOf(convertInstruction(instr)));}
        return translated;
    }
}
