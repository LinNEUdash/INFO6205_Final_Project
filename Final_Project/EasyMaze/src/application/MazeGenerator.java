package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MazeGenerator {
    private int width;
    private int height;
    private boolean[][] maze; // True means wall, false means road
    private int startX = 0;
    private int startY = 0;
    private int endX;
    private int endY;
    
    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.endX = width - 1;
        this.endY = height - 1;
        this.maze = new boolean[height][width];
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void generateMaze() {
        // Initialize the maze so that all cells are walls
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = true;
            }
        }
        
        // Generate a maze using depth-first search
        Stack<int[]> stack = new Stack<>();
        maze[startY][startX] = false; // Start point
        stack.push(new int[]{startX, startY});
        
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];
            
            List<int[]> neighbors = getUnvisitedNeighbors(x, y);
            
            if (neighbors.isEmpty()) {
                stack.pop();
            } else {
                int[] next = neighbors.get(0);
                int nextX = next[0];
                int nextY = next[1];
                
                // Break through the wall
                maze[nextY][nextX] = false;
                // Break through the middle wall
                maze[(y + nextY) / 2][(x + nextX) / 2] = false;
                
                // Add the next cell to the stack
                stack.push(new int[]{nextX, nextY});
            }
        }
        
        // Make sure the endpoint is a pathway
        maze[endY][endX] = false;
    }

    private List<int[]> getUnvisitedNeighbors(int x, int y) {
        List<int[]> neighbors = new ArrayList<>();
        
        // Check neighbors in four directions (up, right, bottom, left)
        int[][] directions = {{0, -2}, {2, 0}, {0, 2}, {-2, 0}};
        
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            
            if (newX >= 0 && newX < width && newY >= 0 && newY < height && maze[newY][newX]) {
                // If the neighbor is a wall, add it to the list
                neighbors.add(new int[]{newX, newY});
            }
        }
        
        // Randomly shuffle the neighbor list to make the maze more random
        Collections.shuffle(neighbors);
        return neighbors;
    }
    
    public boolean[][] getMaze() {
        return maze;
    }
    
    public int getStartX() {
        return startX;
    }
    
    public int getStartY() {
        return startY;
    }
    
    public int getEndX() {
        return endX;
    }
    
    public int getEndY() {
        return endY;
    }
}
