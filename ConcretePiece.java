import java.util.ArrayList;

public abstract class ConcretePiece implements Piece {

    private Player Owner;
    private String Type;
    private Position place;
    private ArrayList<Integer> turn = new ArrayList<Integer> ();
    private ArrayList<Position> moving = new ArrayList<Position> ();
    private int numberOfSteps = 0;
    int num = 0;
    int aetpieces = 0;

    public ConcretePiece(Player owner, String type, Position place) {
        this.Owner = owner;
        this.Type = type;
        this.place = place;
        turn = new ArrayList<Integer> ();
    }
    
    public ConcretePiece(ConcretePiece p) {
    	this.Owner = p.getOwner();
    	this.place = p.getPlace();
    	this.Type =p.getType();
    	turn = new ArrayList<Integer> ();
    }
    
   public ConcretePiece(Piece p) {
	   this.Owner = new ConcretePlayer(p.getOwner());
	   this.Type = p.getType();
	   this.place = new Position(p.getPlace());
	   turn = new ArrayList<Integer> ();
   }
   
   public void setTurn(ArrayList<Integer> turn) {
	   this.turn = turn;
   }
   
   public void setMoving(ArrayList<Position> moves) {
	   this.moving = moves;
   }
   
   public void addToTurn(int time) {
	   this.turn.add(time);
   }
   
   public void addToMonving(Position p) {
	   this.moving.add(p);
   }
   
   public int getaet() {
	   return this.aetpieces;
   }
   
   public void setAet(int num) {
	   this.aetpieces = num;
   }
   
   public void addAet() {
	   this.aetpieces++;
   }

    @Override
    public Player getOwner() {
        return Owner;
    }

    @Override
    public String getType() {
        return Type;
    }

    public Position getPlace() {
        return place;
    }
    
    public void setPlace(Position b) {
    	place = b;
    }
    
    
    public ArrayList<Integer> getTurn() {
    	return this.turn;
    }
    
    public ArrayList<Position> getMove() {
    	return this.moving;
    }
    
    public void setSteps(int num) {
    	this.numberOfSteps = num;
    }
    
    public void addSteps(int amount) {
    	this.numberOfSteps+=amount;
    }
    
    public int getNumberSteps() {
    	return this.numberOfSteps;
    }
    
    
    
    
    public boolean move(Position a, Position b, ArrayList<Piece>pieces) {
    		if(b.isCorner()&&this.getType()!="♔") 
    			{return false;}
    	if(a.getCol()==b.getCol()&&b.getRow()==a.getRow())
    	{return false;}
    	if(a.getCol()==b.getCol()) {
    		if(a.getRow()>b.getRow()) {
    			for(int i=a.getRow()-1; i>b.getRow(); i--) {
    				Position temp = new Position(i, a.getCol());
    				boolean problem = false;
    				for(int j=0; j<pieces.size(); j++) {
    					if(pieces.get(j).getPlace().isEqual(temp))
    						{problem = true;}
    				}
    				if(problem) {return false;}
    			}
    			return true;
    		}
    		else {
        		for(int i=a.getRow()+1; i<b.getRow(); i++) {
        			Position temp = new Position(i, a.getCol());
        			boolean problem = false;
        			for(int j=0; j<pieces.size(); j++) {
        				if(pieces.get(j).getPlace().isEqual(temp))
        					{problem = true;}
        			}
        			if(problem) {return false;}
        		}
    		}
    		return true;
    	}
    	if(a.getRow()==b.getRow()) {
    		if(a.getCol()>b.getCol()) {
    			for(int i=a.getCol()-1; i>b.getCol(); i--) {
    				Position temp = new Position(a.getRow(), i);
    				boolean problem = false;
    				for(int j=0; j<pieces.size(); j++) {
    					if(pieces.get(j).getPlace().isEqual(temp))
    						{problem = true;}
    				}
    				if(problem) {return false;}
    			}
    			return true;
    		}
    		else {
        		for(int i=a.getCol()+1; i<b.getCol(); i++) {
        			Position temp = new Position(a.getRow(), i);
        			boolean problem = false;
        			for(int j=0; j<pieces.size(); j++) {
        				if(pieces.get(j).getPlace().isEqual(temp))
        					{problem = true;}
        			}
        			if(problem) {return false;}
        		}
        		return true;
    		}
    	}
    	return false;
    }
    
   
    
    public int getnumpiec() {
		int ans = 0;
		if(getType()=="♔") {
			ans = 7;
			}
		else {
			if(getMove().getFirst().getCol()==0) {
				ans = getMove().getFirst().getRow()-2;
			}
			if(getMove().getFirst().getCol()==10) {
				ans = 20+(getMove().getFirst().getRow()-3);
			}
			if(getMove().getFirst().getRow()==0&&getMove().getFirst().getCol()<6) {
				ans = (getMove().getFirst().getCol()*2)+1;
			}
			if(getMove().getFirst().getRow()==0&&getMove().getFirst().getCol()==6) {
				ans = 15;
			}
			if(getMove().getFirst().getRow()==0&&getMove().getFirst().getCol()==7) {
				ans = 17;
			}
			if(getMove().getFirst().getRow()==1&&getMove().getFirst().getCol()==5) {
				ans = 12;
			}
			if(getMove().getFirst().getRow()==5&&getMove().getFirst().getCol()==1) {
				ans = 6;
			}
			if(getMove().getFirst().getRow()==5&&getMove().getFirst().getCol()==9) {
				ans = 19;
			}
			if(getMove().getFirst().getRow()==9&&getMove().getFirst().getCol()==5) {
				ans = 13;
			}
			if(getMove().getFirst().getRow()==10) {
				if(getMove().getFirst().getCol()==3) ans = 8;
				if(getMove().getFirst().getCol()==4) ans = 10;
				if(getMove().getFirst().getCol()==5) ans = 14;
				if(getMove().getFirst().getCol()==6) ans = 16;
				if(getMove().getFirst().getCol()==7) ans = 18;
			}
			if(getMove().getFirst().getRow()==5) {
				if(getMove().getFirst().getCol()==3) ans = 1;
				if(getMove().getFirst().getCol()==4) ans = 3;
				if(getMove().getFirst().getCol()==6) ans = 11;
				if(getMove().getFirst().getCol()==7) ans = 13;
			}
			if(getMove().getFirst().getRow()==4) {
				if(getMove().getFirst().getCol()==4) ans = 2;
				if(getMove().getFirst().getCol()==5) ans = 6;
				if(getMove().getFirst().getCol()==6) ans = 10;
			}
			if(getMove().getFirst().getRow()==6) {
				if(getMove().getFirst().getCol()==4) ans = 4;
				if(getMove().getFirst().getCol()==5) ans = 8;
				if(getMove().getFirst().getCol()==6) ans = 12;
			}
			if(getMove().getFirst().getRow()==3&&getMove().getFirst().getCol()==5) {
				ans = 5;
			}
			if(getMove().getFirst().getRow()==7&&getMove().getFirst().getCol()==5) {
				ans = 9;
			}
		}
		return ans;
	}
}
