package Utils.Error;

import Utils.Position;

public class assemblyError extends Error {

    public assemblyError(Position pos, String msg) {
        super("Assembly Error:" + msg, pos);
    }

}