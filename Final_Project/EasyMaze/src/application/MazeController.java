package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;



public class MazeController {
    private MazeGenerator generator;
    private MazeView view;
    private int playerX;
    private int playerY;
    private boolean[][] maze;
    
    private boolean gameActive = false;
    private long startTime;	
    private Timeline timer;
    
    private boolean scoreSaved = false;
   

    public MazeController(MazeGenerator generator, MazeView view) {
        this.generator = generator;
        this.view = view;
        
        view.getStartButton().setOnAction(e -> {
            startNewGame();
            view.requestFocus();
        });
        
        view.getHintButton().setOnAction(e -> {  
            if (gameActive) {  
                // 创建 HintGenerator 实例并生成提示  
                HintGenerator hintGenerator = new HintGenerator(  
                    maze,  // 当前迷宫  
                    playerX,  // 玩家当前X坐标  
                    playerY,  // 玩家当前Y坐标  
                    generator.getEndX(),  // 终点X坐标  
                    generator.getEndY(),  // 终点Y坐标  
                    view  // MazeView 实例  
                );  
                hintGenerator.generateHint();  
            }  
        });  
    }

    public void handleKeyPress(KeyEvent event) {
        if (!gameActive) return;
        
        int newX = playerX;
        int newY = playerY;
        
        // Directions
        switch (event.getCode()) {
            case W:
            case UP:
                newY--;
                break;
            case S:
            case DOWN:
                newY++;
                break;
            case A:
            case LEFT:
                newX--;
                break;
            case D:
            case RIGHT:
                newX++;
                break;
            default:
                return; // Ignore other keys
        }
        
        // Check if the move is valid
        if (isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;
            view.movePlayer(playerX, playerY);
            
            // Check if the player has reached the end point
            if (playerX == generator.getEndX() && playerY == generator.getEndY()) {
                gameActive = false;
                stopTimer();
                long elapsedTime = System.currentTimeMillis() - startTime;
//                showWinMessage();
                showResultScreen(elapsedTime);
            }
        }
        
        // Consume events to prevent other processors from processing
        event.consume();
        
        System.out.println("Button: " + event.getCode() + ", Location: " + playerX + "," + playerY);
    }

    private boolean isValidMove(int x, int y) {
        // Check if it is within the maze range
        if (x < 0 || x >= generator.getWidth() || y < 0 || y >= generator.getHeight()) {
            return false;
        }
        
        // Check if the target location is a wall
        return !maze[y][x];
    }

    // Add game state activation in startNewGame method
    public void startNewGame() {   	
    	scoreSaved = false;
        // Generate a new maze
    	
    	if (timer != null) {
            timer.stop();
        }
    	
        generator.generateMaze();
        maze = generator.getMaze();
        
        // Set the player's position as the starting point
        playerX = generator.getStartX();
        playerY = generator.getStartY();
        
        // Draw a maze
        view.drawMaze(maze, playerX, playerY, generator.getEndX(), generator.getEndY());
        
        // Activate game state
        gameActive = true;
        
        startTime = System.currentTimeMillis();
        view.updateTimer("Time: 0 sec"); 
        startTimer();
        
        view.showHintButton();
        
        System.out.println("A new game has begun! Starting point: " + playerX + "," + playerY + ", End point: " + generator.getEndX() + "," + generator.getEndY());
    }
    
 // Start a Timeline timer to update the MazeView's timer label every second
    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            int seconds = (int) (elapsed / 1000.0);
            view.updateTimer("Time: " + seconds + " sec");
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    
    // Stop the timer
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    
    private void showResultScreen(long elapsedTime) {
        // Display the actual score (including decimals) in the result interface
        double secondsWithFraction = elapsedTime / 1000.0;

        Stage resultStage = new Stage();
        resultStage.initModality(Modality.APPLICATION_MODAL);
        resultStage.setTitle("Results");

        Label timeLabel = new Label("Your Time: " + secondsWithFraction + " sec");
        timeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label rankingLabel = new Label("Current Ranking:");
        TextArea rankingArea = new TextArea(ScoreDatabase.getRanking());
        rankingArea.setEditable(false);
        rankingArea.setPrefHeight(150);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name (optional)");

        Button saveButton = new Button("Save");
        Button skipButton = new Button("Skip");

        // Avoid duplicate saving by judging whether the results have been saved
        saveButton.setOnAction((ActionEvent e) -> {
            if (!scoreSaved) {
                String playerName = nameField.getText().trim();
                if (!playerName.isEmpty()) {
                    ScoreDatabase.saveScore(playerName, secondsWithFraction);
                    rankingArea.setText(ScoreDatabase.getRanking());
                    scoreSaved = true;
                    // After saving, modify the button text and hide Skip
                    saveButton.setText("Close");
                    skipButton.setVisible(false);
                }
            } else {
                resultStage.close();  // If saved, close the result interface
            }
        });

        skipButton.setOnAction((ActionEvent e) -> {
            resultStage.close();
        });

        HBox buttonBox = new HBox(10, saveButton, skipButton);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER);

        VBox vbox = new VBox(10, timeLabel, rankingLabel, rankingArea, nameField, buttonBox);
        vbox.setPadding(new javafx.geometry.Insets(15));
        vbox.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(vbox, 350, 350);
        resultStage.setScene(scene);
        resultStage.showAndWait();
        
        view.hideHintButton(); 
    }

}
