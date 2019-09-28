import java.util.Random;

public class Dice {
	private Random rand;
	private int n;

	// Method that generates random number 1-6
	public int roll() {
		rand = new Random();
		n = rand.nextInt(6) + 1;
		return n;
	}

	// Method that takes a number 1-6 and returns corresponding dice picture
	public String pic() {
		switch(this.n) {
		case 1: 
			return "resources\\dice-six-faces-one.png";
		case 2: 
			return "resources\\dice-six-faces-two.png";
		case 3: 
			return "resources\\dice-six-faces-three.png";
		case 4: 
			return "resources\\dice-six-faces-four.png";
		case 5: 
			return "resources\\dice-six-faces-five.png";
		case 6: 
			return "resources\\dice-six-faces-six.png";
		}
		return "";
	}
}
