
public class Pawn extends ConcretePiece {

    private static final String pawnP2 = "♟";
    private static final String pawnP1 = "♙";
    
        public Pawn(Player player, Position place) {
            super(player, getPawnType(player), place);
        }

        private static String getPawnType(Player player) {
            return player.isPlayerOne() ? pawnP1 : pawnP2;
        }
        
        
        

}


	
	

