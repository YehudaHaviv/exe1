
public class Position {
	

	private boolean corner;
	private int row;
	private int col;
	private int sumTools = 0;
	
	public Position(int row, int colume) {
		this.col = colume;
		this.row = row;
		if((row==0||row==10)&&(colume==0||colume==10))
			{this.corner = true;}
		else
			{this.corner = false;}
	}
	
	public Position(Position p) {
		this.col = p.getCol();
		this.row = p.getRow();
		if((row==0||row==10)&&(col==0||col==10))
			{this.corner = true;}
		else
			{this.corner = false;}
	}
	
	public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
	
	public boolean isCorner() {
		return corner;
	}
	
	public boolean isEqual(Position p) {
		return p.getCol()==this.col&&p.getRow()==this.row;
	}
	
	public void addToolStep() {
		this.sumTools++;
	}
	
	public void lessToolStep() {
		this.sumTools--;
	}
	
	public int getNumbersOfToolsStep() {
		return this.sumTools;
	}
	
	public String printplace() {
		return "("+this.row+", "+this.col+")";
	}
}
