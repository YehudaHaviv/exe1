import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic implements PlayableLogic{

	private final int BOARD_SIZE = 11;
	private Position[][] gameBoard = new Position[11][11];
	private ArrayList<Piece> Pieces = new ArrayList<Piece> ();
	private Player playerOne = new ConcretePlayer(false, 0);
	private Player playerTwo = new ConcretePlayer(true, 0);
	private boolean isPlayerTwoTurn = !true;
	private ArrayList<Piece> moves = new ArrayList<Piece> ();
	private ArrayList<Piece[]> aetPiece = new ArrayList<Piece[]> ();
	private int time = 0;
	private boolean doneGame;
	
	public GameLogic() {
		reset();
	}
	
	@Override
	public boolean move(Position a, Position b) {
		// TODO Auto-generated method stub
		if(getPieceAtPosition(a)!=null&&getPieceAtPosition(b)==null&&
			((getPieceAtPosition(a).getOwner()==playerTwo&&isPlayerTwoTurn)||
			 (getPieceAtPosition(a).getOwner()==playerOne&&!isPlayerTwoTurn))) {
			if(getPieceAtPosition(a).move(a, b, Pieces))
			{
				for(int i=0; i<Pieces.size(); i++) {
					if(Pieces.get(i).getPlace().isEqual(a))
					{
						//add step on to position
						gameBoard[b.getRow()][b.getCol()].addToolStep();
						//for the undo
						Piece temp = new Pawn(Pieces.get(i).getOwner(), Pieces.get(i).getPlace());
						temp.setTurn(new ArrayList<Integer>(Pieces.get(i).getTurn()));
						temp.setMoving(new ArrayList<Position>(Pieces.get(i).getMove()));
						temp.setSteps(Pieces.get(i).getNumberSteps());
						temp.setAet(Pieces.get(i).getaet());
						moves.add(temp);
						//ad to sum steps
						int num = Math.abs((a.getRow()-b.getRow())+(a.getCol()-b.getCol())); 
						//update pieces status
						Pieces.get(i).addToMonving(gameBoard[b.getRow()][b.getCol()]);
						Pieces.get(i).addSteps(num);
						Pieces.get(i).addToTurn(time);
						time= time+1;
						Pieces.get(i).setPlace(gameBoard[b.getRow()][b.getCol()]);
						//who turn:
						isPlayerTwoTurn = !isPlayerTwoTurn;
						//check for eat
						if(Pieces.get(i).getType()!="♔") {didEatPawn(Pieces.get(i));}
						//if game is done
						if(helpCheckdoesGameFinish()) {this.doneGame = true;}
						//value
						return true;
					}
				}
			}
		}
		//value
		return false;
	}
	
	public void didEatPawn(Piece a) {
		aetPiece.add(new Piece[8]);
		Position ap = new Position(a.getPlace());
		for(int i=0; i<Pieces.size(); i++) {
			if(Pieces.get(i).getOwner()!=a.getOwner()&&Pieces.get(i).getType()!="♔") {
				Position bp = new Position(Pieces.get(i).getPlace());
				if(bp.getCol()==ap.getCol()&&
				  ((bp.getRow()==10&&ap.getRow()==9)||
				  (bp.getRow()==0&&ap.getRow()==1))) {
					aetPiece.get(aetPiece.size()-1)[0] = Pieces.get(i);
					a.addAet();
					removePiece(Pieces.get(i));
				}
				if(bp.getRow()==ap.getRow()&&
				  ((bp.getCol()==10&&ap.getCol()==9)||
			   	  (bp.getCol()==0&&ap.getCol()==1))) {
					aetPiece.get(aetPiece.size()-1)[1] = Pieces.get(i);
					a.addAet();
					removePiece(Pieces.get(i));
					}
				if(bp.getRow()==ap.getRow()&&ap.getRow()==10&&
					((bp.getCol()==9&&ap.getCol()==8)||(bp.getCol()==1&&ap.getCol()==2)))
				{
					aetPiece.get(aetPiece.size()-1)[2] = Pieces.get(i);	
					a.addAet();
					removePiece(Pieces.get(i));
				}
				if(bp.getRow()==ap.getRow()&&ap.getRow()==0&&
						((bp.getCol()==9&&ap.getCol()==8)||(bp.getCol()==1&&ap.getCol()==2)))
					{
						aetPiece.get(aetPiece.size()-1)[3] = Pieces.get(i);
						a.addAet();
						removePiece(Pieces.get(i));
					}
				if(bp.getCol()==ap.getCol()&&ap.getCol()==0&&
						((bp.getRow()==9&&ap.getRow()==8)||(bp.getRow()==1&&ap.getRow()==2)))
					{
						aetPiece.get(aetPiece.size()-1)[4] = Pieces.get(i);
						a.addAet();
						removePiece(Pieces.get(i));
					}
				if(bp.getCol()==ap.getCol()&&ap.getCol()==10&&
						((bp.getRow()==9&&ap.getRow()==8)||(bp.getRow()==1&&ap.getRow()==2)))
					{
						aetPiece.get(aetPiece.size()-1)[5] = Pieces.get(i);
						a.addAet();
						removePiece(Pieces.get(i));
					}
				for(int j=0; j<Pieces.size(); j++) {
					if(Pieces.get(j).getOwner()==a.getOwner()&&Pieces.get(j).getType()!="♔"&&Pieces.get(j)!=a) {
						Position cp = new Position(Pieces.get(j).getPlace());
						if((cp.getCol()==ap.getCol()&&bp.getCol()==ap.getCol())&&
							((cp.getRow()==ap.getRow()-2&&bp.getRow()==ap.getRow()-1)||
							(cp.getRow()==ap.getRow()+2&&bp.getRow()==ap.getRow()+1))) {
							aetPiece.get(aetPiece.size()-1)[6] = Pieces.get(i);
							a.addAet();
							removePiece(Pieces.get(i));
						}
						if((cp.getRow()==ap.getRow()&&bp.getRow()==ap.getRow())&&
						  ((cp.getCol()==ap.getCol()-2&&bp.getCol()==ap.getCol()-1)||
						  (cp.getCol()==ap.getCol()+2&&bp.getCol()==ap.getCol()+1))) {
								aetPiece.get(aetPiece.size()-1)[7] = Pieces.get(i);
								a.addAet();
								removePiece(Pieces.get(i));
							}
					}
				}
			}
		}
	}
	
	public void removePiece(Piece p) {
		for(int i=0; i<Pieces.size(); i++) {
			if(Pieces.get(i)==p) {
				Pieces.remove(i);
			}
		}
	}

	@Override
	public Piece getPieceAtPosition(Position position) {
		// TODO Auto-generated method stub
		Piece ans = null;
		for(int i=0; i<Pieces.size(); i++) {
			if(Pieces.get(i).getPlace().isEqual(position))
			{ans = Pieces.get(i);}
		}
		return ans;
	}

	@Override
	public Player getFirstPlayer() {
		// TODO Auto-generated method stub
		return this.playerOne;
	}

	@Override
	public Player getSecondPlayer() {
		// TODO Auto-generated method stub
		return this.playerTwo;
	}
	
	@Override
	public boolean isGameFinished() {
		// TODO Auto-generated method stub
		return this.doneGame;
	}
	
	public boolean helpCheckdoesGameFinish() {
		Piece king = new King(playerTwo);
		int checkLess2 = 0;
		for(int i=0; i<Pieces.size(); i++)
		{
			if(Pieces.get(i).getOwner()==playerOne) {
				checkLess2++;
			}
			if(Pieces.get(i).getType().equalsIgnoreCase("♔"))
			{
				king.setPlace(Pieces.get(i).getPlace());
			}
		}
		if(checkLess2<3) {
			playerOne.addAwin();
			endgameprint(playerTwo);
			return true;
			}
		Position p = king.getPlace();
		if(p.isCorner()) {
			playerOne.addAwin();
			endgameprint(playerTwo);
			return true;
			}
		if(p.getCol()==10) {
			boolean L = false;
			boolean U = false;
			boolean D = false;
			for(int i=0; i<Pieces.size(); i++)
			{
				if(Pieces.get(i).getOwner()==this.playerOne) {
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()-1)
							{L = true;}
					if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
						Pieces.get(i).getPlace().getRow()==p.getRow()-1)
							{D = true;}
					if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
						Pieces.get(i).getPlace().getRow()==p.getRow()+1)
							{U = true;}
				}
			}
			if(L&&U&&D) {
				playerTwo.addAwin();
				endgameprint(playerOne);
				return true;
				}
		}
		if(p.getCol()==0) {
			boolean R = false;
			boolean U = false;
			boolean D = false;
			for(int i=0; i<Pieces.size(); i++)
			{
				if(Pieces.get(i).getOwner()==this.playerOne) {
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()+1)
							{R = true;}
					if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
						Pieces.get(i).getPlace().getRow()==p.getRow()-1)
							{D = true;}
					if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
						Pieces.get(i).getPlace().getRow()==p.getRow()+1)
							{U = true;}
				}
			}
			if(R&&U&&D) {
				playerTwo.addAwin();
				endgameprint(playerOne);
				return true;
				}
		}
		if(p.getRow()==10) {
			boolean R = false;
			boolean U = false;
			boolean L = false;
			for(int i=0; i<Pieces.size(); i++)
			{
				if(Pieces.get(i).getOwner()==this.playerOne) {
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()+1)
							{R = true;}
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()-1)
							{L = true;}
					if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
						Pieces.get(i).getPlace().getRow()==p.getRow()+1)
							{U = true;}
				}
			}
			if(R&&U&&L) {
				playerTwo.addAwin();
				endgameprint(playerOne);
				return true;
				}
		}
		if(p.getRow()==0) {
			boolean R = false;
			boolean D = false;
			boolean L = false;
			for(int i=0; i<Pieces.size(); i++)
			{
				if(Pieces.get(i).getOwner()==this.playerOne) {
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()+1)
							{R = true;}
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()-1)
							{L = true;}
					if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
						Pieces.get(i).getPlace().getRow()==p.getRow()-1)
							{D = true;}
				}
			}
			if(R&&D&&L) {
				playerTwo.addAwin();
				endgameprint(playerOne);
				return true;
				}
		}
		if(p.getCol()!=0&&p.getCol()!=10&&p.getRow()!=0&&p.getRow()!=10) {
		boolean U = false;
		boolean R = false;
		boolean D = false;
		boolean L = false;
			for(int i=0; i<Pieces.size(); i++)
			{
				if(Pieces.get(i).getOwner()==this.playerOne) {
					if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
						Pieces.get(i).getPlace().getCol()==p.getCol()+1)
								{R = true;}
						if(Pieces.get(i).getPlace().getRow()==p.getRow()&&
							Pieces.get(i).getPlace().getCol()==p.getCol()-1)
								{L = true;}
						if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
							Pieces.get(i).getPlace().getRow()==p.getRow()-1)
								{D = true;}
						if(Pieces.get(i).getPlace().getCol()==p.getCol()&&
							Pieces.get(i).getPlace().getRow()==p.getRow()+1)
								{U = true;}
						if(U&&L&&R&&D) {
							playerTwo.addAwin();
							endgameprint(playerOne);
							return true;
							}
				}
			}
		}
		if(isPlayerturnandBlock()) {return true;}
		
		return false;
	}

	@Override
	public boolean isSecondPlayerTurn() {
		// TODO Auto-generated method stub
		return !this.isPlayerTwoTurn;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		this.doneGame = false;
		time = 0;
		moves = new ArrayList<Piece> ();
		aetPiece = new ArrayList<Piece[]> ();
		Pieces = new ArrayList<Piece> ();
		isPlayerTwoTurn = false;
		playerOne.setWin(playerOne.getWins());
		playerTwo.setWin(playerTwo.getWins());
		for(int i=0; i<BOARD_SIZE; i++) {
			for(int j=0; j<BOARD_SIZE; j++) {
				this.gameBoard[i][j] = new Position(i,j);
				if(((i==0||i==10)&&j>2&&j<8)||
				   ((j==0||j==10)&&i>2&&i<8)||
				   ((j==1||j==9)&&i==5)||
				   ((i==1||i==9)&&j==5)){
						this.gameBoard[i][j].addToolStep();
						Piece temp = new Pawn(playerOne, this.gameBoard[i][j]);
						temp.addToMonving(this.gameBoard[i][j]);
						Pieces.add(temp);
				   }
				else {
					if(((j==3||j==4||j==6||j==7)&&i==5)||
					   ((i==4||i==6)&&j<7&&j>3)||
					   ((i==3||i==7)&&j==5)) {
						this.gameBoard[i][j].addToolStep();
						Piece temp = new Pawn(playerTwo, this.gameBoard[i][j]);
						temp.addToMonving(this.gameBoard[i][j]);
						Pieces.add(temp);
					}
					if(i==5&&j==5) {
						this.gameBoard[i][j].addToolStep();
						Piece temp = new King(playerTwo);
						temp.addToMonving(this.gameBoard[i][j]);
						Pieces.add(temp);
					}
				}
			}
		}
	}

	@Override
	public void undoLastMove() {
		// TODO Auto-generated method stub
		if(moves.size()>0) {
			time--;
			isPlayerTwoTurn = !isPlayerTwoTurn;
			for(int i=0; i<Pieces.size(); i++) {
				ArrayList<Integer> a = Pieces.get(i).getTurn();
				for(int j=0; j<a.size(); j++) {
					if(a.get(j)==moves.size()-1) {
						Pieces.get(i).getPlace().lessToolStep();
						Pieces.remove(i);
						Pieces.add(moves.getLast());
						for(int c=0; c<8; c++) {
							if(aetPiece.getLast()[c]!=null) {
								Pieces.add(aetPiece.getLast()[c]);
							}
						}
					}
				}
			}
			moves.removeLast();
			aetPiece.removeLast();
		}
		
	}

	@Override
	public int getBoardSize() {
		// TODO Auto-generated method stub
		return this.BOARD_SIZE;
	}
	
	public boolean isPlayerturnandBlock() {
		boolean p2Move = false;
		boolean p1Move = false;
		ArrayList<Boolean> p1move = new ArrayList<Boolean> ();
		ArrayList<Boolean> p2move = new ArrayList<Boolean> ();
		for(int i=0; i<Pieces.size(); i++)
		{
			boolean up = true;
			boolean down = true;
			boolean r = true;
			boolean l = true;
			if(Pieces.get(i).getPlace().getCol()==10) {
				r = false;
				for(int j=0; j<Pieces.size(); j++) {
					if(Pieces.get(j).getPlace().getCol()==10&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()+1) {
						up = false;
					}
					if(Pieces.get(j).getPlace().getCol()==10&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()-1) {
						down = false;
					}
					if(Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()-1) {
						l = false;
					}
				}
				if(Pieces.get(i).getOwner()==playerOne) {
					if(!up&&!down&&!r&&!l) {p1move.add(false);}
					else {p1move.add(true);}
				}
				else {
					if(!up&&!down&&!r&&!l) {p2move.add(false);}
					else {p2move.add(true);}
				}
			}
			if(Pieces.get(i).getPlace().getCol()==0) {
				l = false;
				for(int j=0; j<Pieces.size(); j++) {
					if(Pieces.get(j).getPlace().getCol()==0&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()+1) {
						up = false;
					}
					if(Pieces.get(j).getPlace().getCol()==0&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()-1) {
						down = false;
					}
					if(Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()+1) {
						r = false;
					}
				}
				if(Pieces.get(i).getOwner()==playerOne) {
					if(!up&&!down&&!r&&!l) {p1move.add(false);}
					else {p1move.add(true);}
				}
				else {
					if(!up&&!down&&!r&&!l) {p2move.add(false);}
					else {p2move.add(true);}
			}
			}
			if(Pieces.get(i).getPlace().getRow()==10) {
				up = false;
				for(int j=0; j<Pieces.size(); j++) {
					if(Pieces.get(j).getPlace().getRow()==10&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()+1) {
						r = false;
					}
					if(Pieces.get(j).getPlace().getRow()==10&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()-1) {
						l = false;
					}
					if(Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()-1) {
						down = false;
					}
				}
				if(Pieces.get(i).getOwner()==playerOne) {
					if(!up&&!down&&!r&&!l) {p1move.add(false);}
					else {p1move.add(true);}
				}
				else {
					if(!up&&!down&&!r&&!l) {p2move.add(false);}
					else {p2move.add(true);}
			}
			}
			if(Pieces.get(i).getPlace().getRow()==0) {
				down = false;
				for(int j=0; j<Pieces.size(); j++) {
					if(Pieces.get(j).getPlace().getRow()==0&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()+1) {
						r = false;
					}
					if(Pieces.get(j).getPlace().getRow()==0&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()-1) {
						l = false;
					}
					if(Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()+1) {
						up = false;
					}
				}
				if(Pieces.get(i).getOwner()==playerOne) {
					if(!up&&!down&&!r&&!l) {p1move.add(false);}
					else {p1move.add(true);}
				}
				else {
					if(!up&&!down&&!r&&!l) {p2move.add(false);}
					else {p2move.add(true);}
			}
			}
			if(Pieces.get(i).getPlace().getRow()!=0&&Pieces.get(i).getPlace().getRow()!=10&&Pieces.get(i).getPlace().getCol()!=0&&Pieces.get(i).getPlace().getCol()!=10) {
				for(int j=0; j<Pieces.size(); j++) {
					if(Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()+1) {
						r = false;
					}
					if(Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()&&Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()-1) {
						l = false;
					}
					if(Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()+1) {
						up = false;
					}
					if(Pieces.get(j).getPlace().getCol()==Pieces.get(i).getPlace().getCol()&&Pieces.get(j).getPlace().getRow()==Pieces.get(i).getPlace().getRow()-1) {
						down = false;
					}
				}
				if(Pieces.get(i).getOwner()==playerOne) {
					if(!up&&!down&&!r&&!l) {p1move.add(false);}
					else {p1move.add(true);}
				}
				else {
					if(!up&&!down&&!r&&!l) {p2move.add(false);}
					else {p2move.add(true);}
			}
			}
		}
		for(int i=0; i<p1move.size(); i++) {
			if(p1move.get(i)==true) {p1Move = true;}
		}
		for(int i=0; i<p2move.size(); i++) {
			if(p2move.get(i)==true) {p2Move = true;}
		}
		if(!p1Move&&!isPlayerTwoTurn)
			{
			playerOne.addAwin();
			endgameprint(playerTwo);
			return true;
			}
		if(!p2Move&&isPlayerTwoTurn) 
			{
			playerTwo.addAwin();
			endgameprint(playerTwo);
			return true;
			}
		return false;
	}
	
	public String getothertype(Piece p) {
		String ans = "";
		if(p.getType()=="♔") {
			ans = "K7";
			}
		else {
			if(p.getMove().getFirst().getCol()==0) {
				ans = "A"+(p.getMove().getFirst().getRow()-2);
			}
			if(p.getMove().getFirst().getCol()==10) {
				ans = "A"+(20+(p.getMove().getFirst().getRow()-3));
			}
			if(p.getMove().getFirst().getRow()==0&&p.getMove().getFirst().getCol()<6) {
				ans = "A"+((p.getMove().getFirst().getCol()*2)+1);
			}
			if(p.getMove().getFirst().getRow()==0&&p.getMove().getFirst().getCol()==6) {
				ans = "A"+(15);
			}
			if(p.getMove().getFirst().getRow()==0&&p.getMove().getFirst().getCol()==7) {
				ans = "A"+(17);
			}
			if(p.getMove().getFirst().getRow()==1&&p.getMove().getFirst().getCol()==5) {
				ans = "A"+(12);
			}
			if(p.getMove().getFirst().getRow()==5&&p.getMove().getFirst().getCol()==1) {
				ans = "A"+(6);
			}
			if(p.getMove().getFirst().getRow()==5&&p.getMove().getFirst().getCol()==9) {
				ans = "A"+(19);
			}
			if(p.getMove().getFirst().getRow()==9&&p.getMove().getFirst().getCol()==5) {
				ans = "A"+(13);
			}
			if(p.getMove().getFirst().getRow()==10) {
				if(p.getMove().getFirst().getCol()==3) ans = "A"+(8);
				if(p.getMove().getFirst().getCol()==4) ans = "A"+(10);
				if(p.getMove().getFirst().getCol()==5) ans = "A"+(14);
				if(p.getMove().getFirst().getCol()==6) ans = "A"+(16);
				if(p.getMove().getFirst().getCol()==7) ans = "A"+(18);
			}
			if(p.getMove().getFirst().getRow()==5) {
				if(p.getMove().getFirst().getCol()==3) ans = "D"+(1);
				if(p.getMove().getFirst().getCol()==4) ans = "D"+(3);
				if(p.getMove().getFirst().getCol()==6) ans = "D"+(11);
				if(p.getMove().getFirst().getCol()==7) ans = "D"+(13);
			}
			if(p.getMove().getFirst().getRow()==4) {
				if(p.getMove().getFirst().getCol()==4) ans = "D"+(2);
				if(p.getMove().getFirst().getCol()==5) ans = "D"+(6);
				if(p.getMove().getFirst().getCol()==6) ans = "D"+(10);
			}
			if(p.getMove().getFirst().getRow()==6) {
				if(p.getMove().getFirst().getCol()==4) ans = "D"+(4);
				if(p.getMove().getFirst().getCol()==5) ans = "D"+(8);
				if(p.getMove().getFirst().getCol()==6) ans = "D"+(12);
			}
			if(p.getMove().getFirst().getRow()==3&&p.getMove().getFirst().getCol()==5) {
				ans = "D"+(5);
			}
			if(p.getMove().getFirst().getRow()==7&&p.getMove().getFirst().getCol()==5) {
				ans = "D"+(9);
			}
		}
		return ans;
	}
	
	public void endgameprint(Player p) {
		printQ1(p);
		printQ2(p);
		printQ3(p);
		printPosition();
		System.out.println();
	}
	
	public void printQ1(Player win) {
		ArrayList<Piece> winer = new ArrayList<Piece> ();
		ArrayList<Piece> loser = new ArrayList<Piece> ();
		for(int i=0; i<Pieces.size(); i++) {
			if(Pieces.get(i).getOwner()==win) {
				winer.add(Pieces.get(i));
			}
			else {
				loser.add(Pieces.get(i));
			}
		}
		PieceComparatorQ1 comparator = new PieceComparatorQ1(win);
		Collections.sort(winer, comparator);
		Collections.sort(loser, comparator);
		for(int i=0; i<winer.size(); i++) {
			if(winer.get(i).getNumberSteps()>0) {
				String toprint = "[";
				for(int j=0; j<winer.get(i).getMove().size(); j++) {
					if(j==winer.get(i).getMove().size()-1) {
						toprint = toprint + winer.get(i).getMove().get(j).printplace() + "]";
					}
					else {
						toprint = toprint + winer.get(i).getMove().get(j).printplace()+ ", ";
					}
				}
				System.out.println(getothertype(winer.get(i))+": "+toprint);
			}
		}
		for(int i=0; i<loser.size(); i++) {
			if(loser.get(i).getNumberSteps()>0) {
				String toprint = "[";
				for(int j=0; j<loser.get(i).getMove().size(); j++) {
					if(j==loser.get(i).getMove().size()-1) {
						toprint = toprint + loser.get(i).getMove().get(j).printplace() + "]";
					}
					else {
						toprint = toprint + loser.get(i).getMove().get(j).printplace()+ ", ";
					}
				}
				System.out.println(getothertype(loser.get(i))+": "+toprint);
			}
		}
		for(int i=0; i<75; i++) {
			 System.out.print("*"); 
		 }
		System.out.println();
	}
	
	public void printQ2(Player winer) {
		EatComparetorQ2 comparator = new EatComparetorQ2(winer);
		Collections.sort(Pieces, comparator);
		
		for(int i=Pieces.size()-1; i>=0; i--) {
			if(Pieces.get(i).getaet()>0)
			 {
				System.out.println(getothertype(Pieces.get(i))+": "+Pieces.get(i).getaet()+" kills");
			 }
		}
		for(int i=0; i<75; i++) {
				System.out.print("*"); 
			 }
		System.out.println();
	}
	
	public void printQ3(Player winer) {
		PieceComparatorQ3 comparator = new PieceComparatorQ3(winer);
		Collections.sort(Pieces, comparator);
		
		for(int i=Pieces.size()-1; i>=0; i--) {
			if(Pieces.get(i).getNumberSteps()>0) {
				 System.out.println(getothertype(Pieces.get(i))+": "+Pieces.get(i).getNumberSteps()+" squares");
			}
		}
		for(int i=0; i<75; i++) {
			 System.out.print("*"); 
		 }
		System.out.println();
	}
	
	public void printPosition() {
		ArrayList<Position> p = new ArrayList<Position> ();
		for(int i=0; i<gameBoard.length; i++) {
			for(int j=0; j<gameBoard[0].length; j++) {
				p.add(gameBoard[i][j]);
			}
		}
		 Collections.sort(p, new PositoinCompertorQ4());
		 for(int i=p.size()-1; i>=0; i--) {
			 if(p.get(i).getNumbersOfToolsStep()>1) {
				 System.out.println("("+p.get(i).getRow()+", "+p.get(i).getCol()+")"+p.get(i).getNumbersOfToolsStep()+" pieces");
			 }
		 }
		 for(int i=0; i<75; i++) {
			 System.out.print("*"); 
		 }
	}

}


