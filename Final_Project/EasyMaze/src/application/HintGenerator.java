package application;  

import javafx.scene.control.Alert;  
import java.util.*;  

public class HintGenerator {  
    private static class RecursivePathFinder {  
        private boolean[][] maze;  
        private List<String> path;  
        private boolean[][] visited;  
        private int endX, endY;  
        
        // 方向定义  
        private static final int[][] DIRECTIONS = {  
            {-1, 0},  // 左  
            {1, 0},   // 右  
            {0, -1},  // 上  
            {0, 1}    // 下  
        };  
        
        private static final String[] DIR_NAMES = {  
            "Left", "Right", "Up", "Down"  
        };  

        // 设置最大递归深度，防止栈溢出  
        private static final int MAX_DEPTH = 100;  

        public List<String> findPath(boolean[][] maze, int startX, int startY, int endX, int endY) {  
            this.maze = maze;  
            this.endX = endX;  
            this.endY = endY;  
            this.path = new ArrayList<>();  
            this.visited = new boolean[maze.length][maze[0].length];  
            
            // 打印迷宫布局  
            System.out.println("Maze Layout:");  
            for (boolean[] row : maze) {  
                StringBuilder rowStr = new StringBuilder();  
                for (boolean cell : row) {  
                    rowStr.append(cell ? "■ " : "□ ");  
                }  
                System.out.println(rowStr);  
            }  
            
            // 使用广度优先搜索（BFS）替代深度优先搜索  
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

                // 找到终点  
                if (current.x == endX && current.y == endY) {  
                    return reconstructPath(current);  
                }  

                // 尝试四个方向  
                for (int i = 0; i < DIRECTIONS.length; i++) {  
                    int newX = current.x + DIRECTIONS[i][0];  
                    int newY = current.y + DIRECTIONS[i][1];  

                    // 检查移动是否有效  
                    if (isValidMove(newX, newY) && !visited.containsKey(key(newX, newY))) {  
                        SearchNode next = new SearchNode(newX, newY, current, DIR_NAMES[i]);  
                        queue.offer(next);  
                        visited.put(key(newX, newY), next);  
                    }  
                }  
            }  

            return null; // 没有找到路径  
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

        // 辅助内部类，用于BFS搜索  
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

    // HintGenerator 的主要属性  
    private boolean[][] maze;  
    private int playerX;  
    private int playerY;  
    private int endX;  
    private int endY;  
    private MazeView view;  

    // 构造方法  
    public HintGenerator(boolean[][] maze, int playerX, int playerY, int endX, int endY, MazeView view) {  
        this.maze = maze;  
        this.playerX = playerX;  
        this.playerY = playerY;  
        this.endX = endX;  
        this.endY = endY;  
        this.view = view;  
    }  

    // 生成提示的主方法  
    public void generateHint() {  
        // 调试输出  
        System.out.println("Generating Hint:");  
        System.out.println("Player Position: (" + playerX + ", " + playerY + ")");  
        System.out.println("End Position: (" + endX + ", " + endY + ")");  
        
        // 使用内部递归路径查找  
        RecursivePathFinder pathFinder = new RecursivePathFinder();  
        List<String> recommendedMoves = pathFinder.findPath(  
            maze,   
            playerX,   
            playerY,   
            endX,   
            endY  
        );  

        // 添加推荐路径的调试信息  
        System.out.println("Recommended Moves: " + recommendedMoves);  

        showHintDialog(recommendedMoves);  
    }  

    // 显示提示对话框  
    private void showHintDialog(List<String> moves) {  
        Alert alert = new Alert(Alert.AlertType.INFORMATION);  
        alert.setTitle("Maze Hint");  
        alert.setHeaderText("Recommended Path");  

        // 将可移动方向转换为字符串  
        String moveText = moves.isEmpty()   
            ? "No recommended moves!"   
            : "Next moves: " + moves.get(0) +   
              (moves.size() > 1 ? " (and " + (moves.size() - 1) + " more)" : "");  
        
        alert.setContentText(moveText);  
        
        alert.showAndWait();  
        view.requestFocus();  
    }  
}  