package application;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//在文件顶部添加这个导入
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class MazeController {
    private MazeGenerator generator;
    private MazeView view;
    private int playerX;
    private int playerY;
    private boolean[][] maze;
    
    private boolean gameActive = false;

    public MazeController(MazeGenerator generator, MazeView view) {
        this.generator = generator;
        this.view = view;
        
        view.getStartButton().setOnAction(e -> {
            startNewGame();
            view.requestFocus();
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
                showWinMessage();
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

    private void showWinMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText("You win！");
        alert.setContentText("You successfully reached the end！");
        alert.showAndWait();
    }

    // Add game state activation in startNewGame method
    public void startNewGame() {
        // Generate a new maze
        generator.generateMaze();
        maze = generator.getMaze();
        
        // Set the player's position as the starting point
        playerX = generator.getStartX();
        playerY = generator.getStartY();
        
        // Draw a maze
        view.drawMaze(maze, playerX, playerY, generator.getEndX(), generator.getEndY());
        
        // Activate game state
        gameActive = true;
        
        System.out.println("A new game has begun! Starting point: " + playerX + "," + playerY + ", End point: " + generator.getEndX() + "," + generator.getEndY());
    }
}
