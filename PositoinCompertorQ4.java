import java.util.Comparator;

public class PositoinCompertorQ4 implements Comparator<Position> {
	

	
	 @Override
	 public int compare(Position p1, Position p2) {
	        
		 int numberComparison = Integer.compare(p1.getNumbersOfToolsStep(), p2.getNumbersOfToolsStep());
	        
		 if (numberComparison == 0) {
			 int xComparison = Integer.compare(p2.getCol(), p1.getCol());
			 
			 
			 if (xComparison == 0) {
	             return Integer.compare(p2.getRow(), p1.getRow());
	         }
			 
			 return xComparison;
		 }
		 return numberComparison;
	 }
	 
}
