package application;  

import javafx.scene.control.Alert;  
import java.util.*;  

public class HintGenerator {  
    private static class RecursivePathFinder {  
        private boolean[][] maze;  
        private List<String> path;  
        private boolean[][] visited;  
        private int endX, endY;  
        
        // Direction definitions  
        private static final int[][] DIRECTIONS = {  
            {-1, 0},  // Left  
            {1, 0},   // Right  
            {0, -1},  // Up  
            {0, 1}    // Down  
        };  
        
        private static final String[] DIR_NAMES = {  
            "Left", "Right", "Up", "Down"  
        };  

        // Set maximum recursion depth to prevent stack overflow  
        private static final int MAX_DEPTH = 100;  

        public List<String> findPath(boolean[][] maze, int startX, int startY, int endX, int endY) {  
            this.maze = maze;  
            this.endX = endX;  
            this.endY = endY;  
            this.path = new ArrayList<>();  
            this.visited = new boolean[maze.length][maze[0].length];  
            
            // Print maze layout  
            System.out.println("Maze Layout:");  
            for (boolean[] row : maze) {  
                StringBuilder rowStr = new StringBuilder();  
                for (boolean cell : row) {  
                    rowStr.append(cell ? "■ " : "□ ");  
                }  
                System.out.println(rowStr);  
            }  
            
            // Use Breadth-First Search (BFS) instead of Depth-First Search  
            List<String> result = breadthFirstSearch(startX, startY);  
            
            return result != null ? result : Collections.emptyList();  
        }  

        private List<String> breadthFirstSearch(int startX, int startY) {  
            Queue<SearchNode> queue = new LinkedList<>();  
            Map<String, SearchNode> visited = new HashMap<>();  

            SearchNode start = new SearchNode(startX, startY, null, null);  
            queue.offer(start);  
            visited.put(key(startX, startY), start);  

            while (!queue.isEmpty()) {  
                SearchNode current = queue.poll();  

                // Find the endpoint  
                if (current.x == endX && current.y == endY) {  
                    return reconstructPath(current);  
                }  

                // Try four directions  
                for (int i = 0; i < DIRECTIONS.length; i++) {  
                    int newX = current.x + DIRECTIONS[i][0];  
                    int newY = current.y + DIRECTIONS[i][1];  

                    // Check if the move is valid  
                    if (isValidMove(newX, newY) && !visited.containsKey(key(newX, newY))) {  
                        SearchNode next = new SearchNode(newX, newY, current, DIR_NAMES[i]);  
                        queue.offer(next);  
                        visited.put(key(newX, newY), next);  
                    }  
                }  
            }  

            return null; // No path found  
        }  

        private String key(int x, int y) {  
            return x + "," + y;  
        }  

        private List<String> reconstructPath(SearchNode node) {  
            List<String> moves = new ArrayList<>();  
            while (node.parent != null) {  
                moves.add(0, node.move);  
                node = node.parent;  
            }  
            return moves;  
        }  

        private boolean isValidMove(int x, int y) {  
            return x >= 0 && x < maze[0].length &&   
                   y >= 0 && y < maze.length &&   
                   !maze[y][x];  
        }  

        // Auxiliary inner class for BFS search  
        private static class SearchNode {  
            int x, y;  
            SearchNode parent;  
            String move;  

            SearchNode(int x, int y, SearchNode parent, String move) {  
                this.x = x;  
                this.y = y;  
                this.parent = parent;  
                this.move = move;  
            }  
        }  
    }  

    // Main attributes of HintGenerator  
    private boolean[][] maze;  
    private int playerX;  
    private int playerY;  
    private int endX;  
    private int endY;  
    private MazeView view;  

    // Constructor  
    public HintGenerator(boolean[][] maze, int playerX, int playerY, int endX, int endY, MazeView view) {  
        this.maze = maze;  
        this.playerX = playerX;  
        this.playerY = playerY;  
        this.endX = endX;  
        this.endY = endY;  
        this.view = view;  
    }  

    // Main method to generate hint  
    public void generateHint() {  
        // Debug output  
        System.out.println("Generating Hint:");  
        System.out.println("Player Position: (" + playerX + ", " + playerY + ")");  
        System.out.println("End Position: (" + endX + ", " + endY + ")");  
        
        // Use internal recursive path finding  
        RecursivePathFinder pathFinder = new RecursivePathFinder();  
        List<String> recommendedMoves = pathFinder.findPath(  
            maze,   
            playerX,   
            playerY,   
            endX,   
            endY  
        );  

        // Add recommended path debug information  
        System.out.println("Recommended Moves: " + recommendedMoves);  

        showHintDialog(recommendedMoves);  
    }  

    // Show hint dialog  
    private void showHintDialog(List<String> moves) {  
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  
        alert.setTitle("Maze Hint");  
        alert.setHeaderText("Recommended Path");  

        // Convert movable directions to string  
        String moveText = moves.isEmpty()   
            ? "No recommended moves!"   
            : "Next moves: " + moves.get(0) +   
              (moves.size() > 1 ? " (and " + (moves.size() - 1) + " more)" : "");  
        
        alert.setContentText(moveText);  
        
        alert.showAndWait();  
        view.requestFocus();  
    }  
}  