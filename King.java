import java.util.ArrayList;

public class King extends ConcretePiece {

    private static final String Type = "♔";

    public King(Player p) {
        super(p, Type, new Position(5, 5));
    }
    
}

