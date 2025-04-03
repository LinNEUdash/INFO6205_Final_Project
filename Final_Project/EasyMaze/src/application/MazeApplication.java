package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MazeApplication extends Application {
    
    private static final int MAZE_WIDTH = 20;
    private static final int MAZE_HEIGHT = 15;
    private static final int CELL_SIZE = 30;
    
    @Override
    public void start(Stage primaryStage) {
        // Create MazeGenerator
        MazeGenerator generator = new MazeGenerator(MAZE_WIDTH, MAZE_HEIGHT);
        
        // Create MazeView
        MazeView mazeView = new MazeView(MAZE_WIDTH, MAZE_HEIGHT, CELL_SIZE);
        
        // Create MazeController
        MazeController controller = new MazeController(generator, mazeView);
        
        // Set a new scene
        Scene scene = new Scene(mazeView, MAZE_WIDTH * CELL_SIZE, MAZE_HEIGHT * CELL_SIZE + 50);
        
        // Add keyboard event listener
        scene.setOnKeyPressed(event -> controller.handleKeyPress(event));
        
        // Set stage
        primaryStage.setTitle("EasyMaze");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Start a new game
        controller.startNewGame();
        
        // Request focus to ensure keyboard events can be captured
        mazeView.requestFocus();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

