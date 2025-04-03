package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MazeView extends BorderPane {
    private int width;
    private int height;
    private int cellSize;
    private GridPane mazeGrid;
    private Rectangle playerRect;
    private Button startButton;
    
    public MazeView(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        
        // Set padding
        setPadding(new Insets(10));
        
        // Creating a Maze Grid
        mazeGrid = new GridPane();
        mazeGrid.setAlignment(Pos.CENTER);
        
        // Creating the Player Graphics
        playerRect = new Rectangle(cellSize * 0.8, cellSize * 0.8);
        playerRect.setFill(Color.RED);
        playerRect.setArcWidth(cellSize * 0.5);
        playerRect.setArcHeight(cellSize * 0.5);
        
        // Creating a Start Button
        startButton = new Button("New Game");
        HBox buttonBox = new HBox(startButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        
        // Adding Components to the BorderPane
        setCenter(mazeGrid);
        setBottom(buttonBox);
        
        // Set to focusable
        setFocusTraversable(true);
    }
    
    public void drawMaze(boolean[][] maze, int playerX, int playerY, int endX, int endY) {
        mazeGrid.getChildren().clear();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                
                if (maze[y][x]) {
                    // walls
                    cell.setFill(Color.BLACK);
                } else if (x == endX && y == endY) {
                    // End point
                    cell.setFill(Color.GREEN);
                } else {
                    // Start point
                    cell.setFill(Color.WHITE);
                }
                
                cell.setStroke(Color.GRAY);
                cell.setStrokeWidth(0.5);
                mazeGrid.add(cell, x, y);
            }
        }
        
        // Add player
        playerRect.setFill(Color.RED);
        mazeGrid.add(playerRect, playerX, playerY);
        
        System.out.println("The maze is drawn, the player's position:" + playerX + "," + playerY);
    }

    public void movePlayer(int x, int y) {
        // Remove the player from the current position
        mazeGrid.getChildren().remove(playerRect);
        
        // Add to New Location
        mazeGrid.add(playerRect, x, y);
        
        System.out.println("The player moves to: " + x + "," + y);
    }
    
    public Button getStartButton() {
        return startButton;
    }
}
