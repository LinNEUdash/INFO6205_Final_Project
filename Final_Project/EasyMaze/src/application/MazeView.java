package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;  

public class MazeView extends VBox {
    private int width;
    private int height;
    private int cellSize;
    private GridPane mazeGrid;
    private Rectangle playerRect;
    private Button startButton;
    private Label timerLabel;
    private Button hintButton;  
    
    public MazeView(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        
        setPadding(new Insets(10));
        setSpacing(10);
        setAlignment(Pos.CENTER);
        
        // Top Timer
        timerLabel = new Label("Time: 0 sec");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        timerLabel.setAlignment(Pos.CENTER);
        
        // Create a maze area and set a fixed size
        mazeGrid = new GridPane();
        mazeGrid.setAlignment(Pos.CENTER);
        mazeGrid.setPrefSize(width * cellSize, height * cellSize);
        
        // New Game button
        startButton = new Button("New Game");
        startButton.setPrefWidth(120);
        
        //hint button
        hintButton = new Button("Get Hint");  
        hintButton.setPrefWidth(120);
        hintButton.setVisible(false);
        
        HBox buttonBox = new HBox(40); // 设置按钮间距  
        buttonBox.setAlignment(Pos.CENTER_RIGHT); // 靠右对齐  
        buttonBox.setPadding(new Insets(0, 70, 0, 0)); // 右侧内边距  
        HBox.setHgrow(hintButton, Priority.ALWAYS); // 使hint按钮尽可能向右扩展  
        buttonBox.getChildren().addAll(startButton, hintButton); // 添加按钮             
        
        // Add all components in sequence
        getChildren().addAll(timerLabel, mazeGrid, buttonBox); 
    }
    
    public void updateTimer(String timeText) {
        timerLabel.setText(timeText);
    }
    
    public void drawMaze(boolean[][] maze, int playerX, int playerY, int endX, int endY) {
        mazeGrid.getChildren().clear();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                
                if (maze[y][x]) {
                    cell.setFill(Color.BLACK);
                } else if (x == endX && y == endY) {
                    cell.setFill(Color.GREEN);
                } else {
                    cell.setFill(Color.WHITE);
                }
                
                cell.setStroke(Color.GRAY);
                cell.setStrokeWidth(0.5);
                mazeGrid.add(cell, x, y);
            }
        }
        
        // Drawing the player graphics
        playerRect = new Rectangle(cellSize * 0.8, cellSize * 0.8);
        playerRect.setFill(Color.RED);
        playerRect.setArcWidth(cellSize * 0.5);
        playerRect.setArcHeight(cellSize * 0.5);
        mazeGrid.add(playerRect, playerX, playerY);
        
        System.out.println("Maze drawn. Player position: " + playerX + "," + playerY);
    }
    
    public void movePlayer(int x, int y) {
        mazeGrid.getChildren().remove(playerRect);
        mazeGrid.add(playerRect, x, y);
        System.out.println("Player moved to: " + x + "," + y);
    }
    
    public Button getStartButton() {
        return startButton;
    }
    
    public Button getHintButton() {  
        return hintButton;  
    }  
    
   
    public void showHintButton() {  
        hintButton.setVisible(true);  
    }  

    
    public void hideHintButton() {  
        hintButton.setVisible(false);  
    }  
}

