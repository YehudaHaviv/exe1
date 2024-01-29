import java.util.Comparator;

public class PieceComparatorQ1 implements Comparator<Piece> {

 private Player player;
	 
	 public PieceComparatorQ1(Player player) {
	        this.player = player;
	    }
	
	@Override 
	public int compare(Piece p1, Piece p2) {
		int stepsComparison = Integer.compare(p1.getMove().size(), p2.getMove().size());
		
		 if (stepsComparison == 0) {
			 int pieceNumberComparison = Integer.compare(p1.getnumpiec(), p2.getnumpiec());
			 
			 if (pieceNumberComparison == 0) {
				 boolean isP1 = p1.getOwner()==this.player;
				 
				 if(isP1) {return -1;}
				 else {return 1;}
			 }
			 
			 return pieceNumberComparison;
		 }
		 
		 return stepsComparison;
	}
}
