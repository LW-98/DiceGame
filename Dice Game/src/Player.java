
public class Player {
	
	private int score;
	private String name;
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	// Method that takes the value of three numbers and scores them based on the games scoring system
	public boolean score(int n1, int n2, int n3) {
		if ((n1==n2 || n1==n3) && !(n2==n3)) score += (2*n1);
		else if (n2==n3 && !(n1==n2)) score += (2*n2);
		else if (n1==n2 && n2==n3) score += 18;
		else score += 1;
		
		if (score>=Game.getMaxScore()) return true;
		else return false;
	}

}
