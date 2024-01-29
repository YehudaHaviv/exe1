
class ConcretePlayer implements Player{
	private boolean isAttacker;
	private int wining;
	
	public ConcretePlayer(boolean isAttacker, int num) {
		this.isAttacker = isAttacker;
		this.wining = num;
	}
	
	public ConcretePlayer(Player player) {
		this.isAttacker = player.isPlayerOne();
	}
	
	
	@Override
	public boolean isPlayerOne() {
		// TODO Auto-generated method stub
		return isAttacker;
	}

	@Override
	public int getWins() {
		// TODO Auto-generated method stub
		return wining;
	}
	
	public void addAwin() {
		this.wining++;
	}
	
	public void setWin(int num) {
		wining = num;
	}
}
