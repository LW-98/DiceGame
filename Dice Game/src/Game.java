
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application{
	

	private static Game game;
	private static Dice d1,d2,d3;
	private static Player player1, player2;
	private static int maxScore;

	private Scene scene1, scene2, winScene, startScene, instructionScene;
	private Label playerOneLabel, playerTwoLabel, winLabel, targetScore, instructionLabel;

	public static void main(String[] args) {
		
		launch(args);
	}

	public Game(int n) {
		maxScore = n;
		d1 = new Dice();
		d2 = new Dice();
		d3 = new Dice();
	}

	public Game() {}

	public void setMaxScore(int n) { maxScore = n; }

	public static int getMaxScore() { return maxScore; }

	@Override
	public void start(Stage window) throws Exception {
		window.setTitle("Dice-Throwing Game");
		window.getIcons().add(new Image(this.getClass().getResourceAsStream("resources\\dice-six-faces-one.png")));
		setUserAgentStylesheet(STYLESHEET_CASPIAN);

		// Creating players
		player1 = new Player("");
		player2 = new Player("");

		// Images/gif
		ImageView imageView1 = new ImageView();
		imageView1.setFitHeight(100);
		imageView1.setFitWidth(100);
		ImageView imageView2 = new ImageView();
		imageView2.setFitHeight(100);
		imageView2.setFitWidth(100);
		ImageView imageView3 = new ImageView();
		imageView3.setFitHeight(100);
		imageView3.setFitWidth(100);
		ImageView imageView4 = new ImageView();
		imageView3.setFitHeight(100);
		imageView3.setFitWidth(100);

		// HBox for images and gif
		HBox dice = new HBox();
		HBox dice2 = new HBox();
		
		// VBox for win scene
		VBox win= new VBox();

		// Labels
		Label playerOneNameLabel = new Label("Enter Player 1 name: ");
		playerOneNameLabel.setTextFill(Color.WHITE);
		playerOneNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		Label playerTwoNameLabel = new Label("Enter Player 2 name: ");
		playerTwoNameLabel.setTextFill(Color.WHITE);
		playerTwoNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		Label maxScoreLabel = new Label("Enter Max Score: ");
		maxScoreLabel.setTextFill(Color.WHITE);
		maxScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		Label lastRollLabel = new Label();
		lastRollLabel.setTextFill(Color.WHITE);
		lastRollLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
		Label playerOneScoreLabel = new Label(""+player1.getScore());
		playerOneScoreLabel.setTextFill(Color.WHITE);
		playerOneScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		Label playerTwoScoreLabel = new Label(""+player2.getScore());
		playerTwoScoreLabel.setTextFill(Color.WHITE);
		playerTwoScoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		Label errorLabel = new Label(""+player2.getScore());
		errorLabel.setTextFill(Color.RED);
		errorLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

		// Text fields
		TextField maxScore = new TextField();
		TextField playerOneName = new TextField();
		TextField playerTwoName = new TextField();

		// Start Scene
		GridPane gpane = new GridPane();
		Button startButton = new Button("Start");
		Button restartButton = new Button("Restart");
		Button howToPlay = new Button("How to Play");
		gpane.setHgap(5);
		gpane.setVgap(5);
		gpane.add(playerOneNameLabel, 0, 0);
		gpane.add(playerOneName, 1, 0,2,1);
		gpane.add(playerTwoNameLabel, 0, 1);
		gpane.add(playerTwoName, 1, 1,2,1);
		gpane.add(maxScoreLabel, 0, 2);
		gpane.add(maxScore, 1, 2,2,1);
		gpane.add(startButton, 1, 3);
		gpane.add(howToPlay, 2, 3);
		gpane.setAlignment(Pos.CENTER);
		startScene = new Scene(gpane,550,300);
		startScene.setFill(Color.BLACK);

		// Player 1 Scene
		GridPane gpane1 = new GridPane();
		Button b1 = new Button("Roll for " + player1.getName());
		gpane1.setHgap(5);
		gpane1.setVgap(5);
		scene1 = new Scene(gpane1,550,300);
		scene1.setFill(Color.BLACK);

		// Player 2 Scene
		GridPane gpane2 = new GridPane();
		Button b2 = new Button("Roll for " + player2.getName());
		gpane2.setHgap(5);
		gpane2.setVgap(5);
		scene2 = new Scene(gpane2,550,300);
		scene2.setFill(Color.BLACK);

		// Win Scene
		BorderPane bpane = new BorderPane();
		winScene = new Scene(bpane,550,300);
		winScene.setFill(Color.BLACK);
		winLabel = new Label();
		winLabel.setTextFill(Color.WHITE);
		winLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

		// Instruction Scene
		GridPane gpane4 = new GridPane();
		Button back = new Button("Go Back");
		gpane4.setHgap(5);
		gpane4.setVgap(3);
		instructionLabel = new Label(" Rules: \n" 
				+ " 1. Player 1 presses the button to roll 3 dice\n"
				+ " 2. Player 1's roll is displayed on screen, when ready, player 2 presses roll\n"
				+ " 3. This is repeated until a player reaches a pre-defined score and wins\n\n"
				+ " Scoring: \n"
				+ " If all 3 dice are different, 1 point is scored\n"
				+ " If 2 dice are the same, the sum of those dice is scored\n"
				+ " If all 3 dice are the same, 18 points are scored");
		instructionLabel.setTextFill(Color.WHITE);
		instructionLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		gpane4.add(instructionLabel, 0, 2,10,1);
//		try {
			imageView4.setImage(new Image(this.getClass().getResourceAsStream("resources\\dice.gif")));
			imageView4.setFitHeight(90);
			imageView4.setFitWidth(120);
			dice2.getChildren().add(imageView4);
			gpane4.add(dice2, 7, 0,4,1);
//		} catch (FileNotFoundException e1) {}
		instructionScene = new Scene(gpane4,550,300);
		instructionScene.setFill(Color.BLACK);

		// Start Button
		startButton.setOnAction(e -> {
			targetScore = new Label("");
			player1.setName(playerOneName.getText());
			b1.setText("Roll for " + player1.getName());
			player2.setName(playerTwoName.getText());
			b2.setText("Roll for " + player2.getName());
			restartButton.setText("Restart");
			playerTwoLabel = new Label(player2.getName());
			playerOneLabel = new Label(player1.getName());
			playerOneLabel.setUnderline(true);
			playerTwoLabel.setUnderline(true);
			playerOneLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			playerTwoLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
			playerOneLabel.setTextFill(Color.WHITE);
			playerTwoLabel.setTextFill(Color.WHITE);
			gpane1.getChildren().removeAll(playerOneLabel,b1,playerTwoLabel,dice,lastRollLabel,restartButton,targetScore,playerTwoScoreLabel,playerOneScoreLabel);
			gpane1.add(playerOneLabel,0,1);
			gpane1.add(playerOneScoreLabel,0,2);
			gpane1.add(b1,3,5);
			gpane1.add(playerTwoLabel,5,1);
			gpane1.add(playerTwoScoreLabel,5,2);
			gpane1.add(restartButton, 4, 5);
			gpane1.setAlignment(Pos.CENTER);
			window.setScene(scene1);
			if (!isInt(maxScore, maxScore.getText())){
				errorLabel.setText("Error: Max score must be a positive integer");
				gpane.getChildren().remove(errorLabel);
				gpane.add(errorLabel, 0, 4,4,1);
				window.setScene(startScene);
			}else {
				game = new Game(Integer.parseInt(maxScore.getText()));
				targetScore = new Label("Target Score: " + Game.getMaxScore());
				targetScore.setTextFill(Color.WHITE);
				targetScore.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
				gpane1.getChildren().remove(targetScore);
				gpane1.add(targetScore,0,0,6,1);
				GridPane.setHalignment(targetScore, HPos.CENTER);
			}
			if (playerOneName.getText().length()==0 || playerTwoName.getText().length()==0) {
				errorLabel.setText("Error: Names must not be empty");
				gpane.getChildren().remove(errorLabel);
				gpane.add(errorLabel, 0, 4,4,1);
				window.setScene(startScene);
			}
		});

		// Button for Player 1 roll
		b1.setOnAction(e -> {
			if (player1.score(d1.roll(), d2.roll(), d3.roll())) {
				winLabel.setText("Congratulations "+player1.getName()+"\n you win!");
				winLabel.setTextAlignment(TextAlignment.CENTER);
				restartButton.setText("New Game");
				win.setAlignment(Pos.CENTER);
				BorderPane.setAlignment(win, Pos.CENTER);
				win.getChildren().addAll(winLabel,restartButton);
				bpane.setCenter(win);
				win.setSpacing(10);
				window.setScene(winScene);
			} else {
//				try {
					imageView1.setImage(new Image(this.getClass().getResourceAsStream(d1.pic())));
					imageView2.setImage(new Image(this.getClass().getResourceAsStream(d2.pic())));
					imageView3.setImage(new Image(this.getClass().getResourceAsStream(d3.pic())));
					dice.getChildren().removeAll(imageView1,imageView2,imageView3);
					dice.getChildren().addAll(imageView1,imageView2,imageView3);
//				} catch (FileNotFoundException e1) {}
				gpane2.getChildren().removeAll(playerOneLabel,b2,playerTwoLabel,dice,lastRollLabel,restartButton,targetScore,playerTwoScoreLabel,playerOneScoreLabel);
				gpane2.add(targetScore,0,0,6,1);
				playerOneNameLabel.setUnderline(true);
				playerTwoNameLabel.setUnderline(true);
				gpane2.add(playerOneLabel,0,1);
				gpane2.add(b2,3,5);
				gpane2.add(restartButton, 4, 5);
				gpane2.add(playerTwoLabel,5,1);
				gpane2.add(playerTwoScoreLabel,5,2);
				GridPane.setHalignment(playerTwoScoreLabel, HPos.CENTER);
				GridPane.setHalignment(playerOneScoreLabel, HPos.CENTER);
				gpane2.add(playerOneScoreLabel,0,2);
				gpane2.add(dice,1,4,4,1);
				lastRollLabel.setText(player1.getName()+"'s last roll: ");
				gpane2.add(lastRollLabel, 1, 3);
				gpane2.setAlignment(Pos.CENTER);
				playerOneScoreLabel.setText(""+player1.getScore());
				window.setScene(scene2);
			}
		});

		// Button for Player 2 roll
		b2.setOnAction(e -> {
			if (player2.score(d1.roll(), d2.roll(), d3.roll())) {
				winLabel.setText("Congratulations "+player2.getName()+"\n you win!");
				winLabel.setTextAlignment(TextAlignment.CENTER);
				restartButton.setText("New Game");
				win.setAlignment(Pos.CENTER);
				BorderPane.setAlignment(win, Pos.CENTER);
				win.getChildren().addAll(winLabel,restartButton);
				bpane.setCenter(win);
				win.setSpacing(10);
				window.setScene(winScene);
			} else {
//				try {
					imageView1.setImage(new Image(this.getClass().getResourceAsStream(d1.pic())));
					imageView2.setImage(new Image(this.getClass().getResourceAsStream(d2.pic())));
					imageView3.setImage(new Image(this.getClass().getResourceAsStream(d3.pic())));
					dice.getChildren().removeAll(imageView1,imageView2,imageView3);
					dice.getChildren().addAll(imageView1,imageView2,imageView3);
//				} catch (FileNotFoundException e1) {}
				gpane1.getChildren().removeAll(playerOneLabel,b1,playerTwoLabel,dice,lastRollLabel,restartButton,targetScore,playerTwoScoreLabel,playerOneScoreLabel);
				gpane1.add(targetScore,0,0,6,1);
				playerOneLabel.setUnderline(true);
				playerTwoLabel.setUnderline(true);
				gpane1.add(playerOneLabel,0,1);
				gpane1.add(playerOneScoreLabel,0,2);
				GridPane.setHalignment(playerOneScoreLabel, HPos.CENTER);
				gpane1.add(b1,3,5);
				gpane1.add(restartButton, 4, 5);
				gpane1.add(playerTwoLabel,5,1);
				gpane1.add(playerTwoScoreLabel,5,2);
				GridPane.setHalignment(playerTwoScoreLabel, HPos.CENTER);
				gpane1.add(dice,1,4,4,1);
				lastRollLabel.setText(player2.getName()+"'s last roll: ");
				gpane1.add(lastRollLabel, 1, 3);
				gpane1.setAlignment(Pos.CENTER);
				playerTwoScoreLabel.setText(""+player2.getScore());
				window.setScene(scene1);
			}
		});

		// Button for Instructions
		howToPlay.setOnAction(e -> { 
			gpane4.getChildren().remove(back);
			gpane4.add(back, 5, 5,10,1);
			window.setScene(instructionScene); 
		});

		// Button for Go Back
		back.setOnAction(e -> {
			window.setScene(startScene);
		});

		// Button for Restart/New Game
		restartButton.setOnAction(e -> {
			window.close();
			Platform.runLater(() -> {
				try {
					new Game().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
		});
		window.setScene(startScene);
		window.show();
	}

	// Method to check if input is a positive integer
	private boolean isInt(TextField x, String y) {
		try {
			int z = Integer.parseInt(x.getText());
			if (z>0) return true;
			else return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
