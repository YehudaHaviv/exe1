import java.util.Comparator;

public class EatComparetorQ2 implements Comparator<Piece> {
	
	private Player player;
	 
	 public EatComparetorQ2(Player player) {
	        this.player = player;
	    }
	
	 @Override
	 public int compare(Piece p1, Piece p2) {
	        
		 int numberEat = Integer.compare(p1.getaet(), p2.getaet());
	        
		 if (numberEat == 0) {
			 int numpiece = Integer.compare(p2.getnumpiec(), p1.getnumpiec());
			 
			 
			 if (numpiece == 0) {
				 boolean isP1 = p1.getOwner()==this.player;
				 
				 if(isP1) {return -1;}
				 else {return 1;}
			 }
			 
			 return numpiece;
		 }
		 return numberEat;
	 }
	 
}
