package edu.uiowa.cs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Phase2 {

    /* Returns a list of copies of the Instructions with the
     * immediate field of the instruction filled in
     * with the address calculated from the branch_label.
     *
     * The instruction should not be changed if it is not a branch instruction.
     *
     * unresolved: list of instructions without resolved addresses
     * first_pc: address where the first instruction will eventually be placed in memory
     */
    
    // Branch instruction codes: 
    //5             beq
    //6               bne
    
    //int label_id;      // 0=no label on this line; nonzero is a unique id (what we need to look at)
    //int branch_label;  // label used by branch or jump instructions (what we need to look for)

    public static List<Instruction> resolve_addresses(List<Instruction> unresolved, int first_pc) {
        List<Instruction> resolved = new LinkedList<Instruction>(unresolved);
        for(int i = 0; i < resolved.size(); i ++)
	    {// This is a branch instruction, so make the appropriate changes, which are universal per event
		if(resolved.get(i).instruction_id == 5 || resolved.get(i).instruction_id == 6)
		    {
			int branchLabel = resolved.get(i).branch_label;
			int j = 0; 
			for(int k = 0; k < resolved.size(); k++)
			    {
				if(resolved.get(i).label_id == branchLabel)
				    {
					j = k;
					break;
				    }
			    }
			int nextDistance = 4 * j;
			int nextAddress = 0;
			if (i < j)
			    nextAddress = (i - j) + 1 ;
			else
			    nextAddress = (j - i) - 1;
            
			resolved.get(i).immediate = nextAddress;
		    }
            
		// This is not a branch instruction, so leave it alone
		else;
	    }
        return resolved;
    }

}
