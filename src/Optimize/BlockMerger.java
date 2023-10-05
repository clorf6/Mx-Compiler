package Optimize;

import IR.Function;
import IR.Program;
import IR.Block;
import IR.Instruction.*;
import IR.Type.labelType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BlockMerger {
    Program program;
    public BlockMerger(Program program) {
        this.program = program;
        for (var func: program.funcs) {
            merge(func);
        }
    }

    public void merge(Function it) {
        HashMap<labelType, Block> blockMap = new HashMap<>();
        HashSet<labelType> isMerge = new HashSet<>();
        for (var bl : it.block) blockMap.put(bl.name, bl);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (var bl : it.block) {
                if (isMerge.contains(bl.name)) continue;
                var jumpIns = bl.inst.getLast();
                if (jumpIns instanceof br && ((br) jumpIns).iffalse == null) {
                    var toBlock = blockMap.get(((br) jumpIns).iftrue);
                    if (toBlock.pre.size() == 1) {
                        flag = true;
                        bl.inst.remove(jumpIns);
                        bl.inst.addAll(toBlock.inst);
                        blockMap.put(toBlock.name, bl);
                        isMerge.add(toBlock.name);
                    }
                }
            }
        }
        LinkedList<Block> newBlocks = new LinkedList<>();
        for (var bl : it.block) {
            if (isMerge.contains(bl.name)) continue;
            for (var ins : bl.inst) {
                if (!(ins instanceof phi)) break;
                for (int i = 0; i < ((phi) ins).label.size(); i++) {
                    var fr = ((phi) ins).label.get(i);
                    while (blockMap.containsKey(fr) && !fr.equals(blockMap.get(fr).name)) fr = blockMap.get(fr).name;
                    ((phi) ins).label.set(i, fr);
                }
            }
            newBlocks.add(bl);
        }
        it.block = newBlocks;
    }
}
