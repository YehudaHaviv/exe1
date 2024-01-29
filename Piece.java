import java.util.ArrayList;

/**
 * The Piece interface defines the characteristics of a game piece in a chess-like game.
 * Implementing classes should provide information about the player who owns the piece
 * and return a Unicode character representing the type of the piece (e.g., ♟ for pawn,
 * ♞ for knight, ♜ for rook, etc.).
 */
public interface Piece {

    /**
     * Get the player who owns the piece.
     *
     * @return The player who is the owner of this game piece.
     */
    Player getOwner();

    /**
     * Get a Unicode character representing the type of the game piece.
     *  <a href="https://en.wikipedia.org/wiki/Chess_symbols_in_Unicode">...</a>
     * @return A Unicode character representing the type of this game piece
     *         (e.g., ♟ for pawn, ♞ for knight, ♜ for rook, etc.).
     *
     */
    String getType();
    
    Position getPlace();
    
    boolean move(Position a, Position b, ArrayList<Piece> Pieces);
    
    public void setPlace(Position b);
    
    public ArrayList<Integer> getTurn();
    
    public void setTurn(ArrayList<Integer> turn);
    
    public void addToTurn(int time);
    
    public void setMoving(ArrayList<Position> moves);
    
    public ArrayList<Position> getMove();
    
    public void addSteps(int amount);
    
    public int getNumberSteps();
    
    public void setSteps(int num);
    
    public int getnumpiec();
    
    public int getaet();
    
    public void setAet(int num);
    
    public void addAet();
    
    public void addToMonving(Position p);
}

