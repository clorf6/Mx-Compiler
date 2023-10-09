package ASM.Entity;

import java.util.HashSet;

public abstract class reg extends Entity {
    public String name;
    public int size;

    public reg(String name, int size) {
        this.name = name;
        this.size = size;
    }

}

