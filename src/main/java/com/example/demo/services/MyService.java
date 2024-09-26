package com.example.demo.services;

import com.example.demo.dto.RequestDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MyService {

    public List<int[]> search(RequestDto requestDto) {
        int startX = requestDto.getStartX();
        int startY = requestDto.getStartY();
        int endX = requestDto.getEndX();
        int endY = requestDto.getEndY();
        boolean[][] obstacles = requestDto.getObstacles();

        int numberOfRows = obstacles.length;
        int numberOfColumns = obstacles[0].length;

        // Check if the source or destination cell is blocked by obstacles
        if (obstacles[startX][startY] || obstacles[endX][endY]) {
            return new ArrayList<>();
        }

        // Create a queue to store the cells to explore
        Queue<int[]> q = new LinkedList<>();

        // Create a 2D array to store the parent of each cell
        int[][] parent = new int[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            Arrays.fill(parent[i], -1);
        }

        // Add the source cell to the queue and mark its parent as -1
        q.add(new int[]{startX, startY});
        parent[startX][startY] = -1;

        // Define two arrays to represent the four directions of movement
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        // Create a 2D array to mark visited cells
        boolean[][] visited = new boolean[numberOfRows][numberOfColumns];
        visited[startX][startY] = true;

        // Loop until the queue is empty
        while (!q.isEmpty()) {
            // Get the front cell from the queue and remove it
            int[] p = q.poll();
            int x = p[0];
            int y = p[1];

            // Loop through the four directions of movement
            for (int i = 0; i < 4; i++) {
                // Calculate the coordinates of the neighboring cell
                int xx = x + dx[i];
                int yy = y + dy[i];

                // Check if the neighboring cell is inside the grid and not visited
                if (xx >= 0 && xx < numberOfRows && yy >= 0 && yy < numberOfColumns && !visited[xx][yy]) {
                    // Check if the neighboring cell is free (not an obstacle)
                    if (!obstacles[xx][yy]) {
                        // Mark the neighboring cell as visited
                        visited[xx][yy] = true;
                        q.add(new int[]{xx, yy});
                        parent[xx][yy] = x * numberOfColumns + y; // Store the parent
                    }
                }
            }
        }

        // Reconstruct the path from the destination to the source
        List<int[]> path = new ArrayList<>();
        int x = endX, y = endY;
        if (parent[x][y] == -1) {
            return path; // Return empty path if no path exists
        }
        while (x != -1 && y != -1) {
            path.add(new int[]{x, y});
            int temp = parent[x][y];
            x = temp / numberOfColumns; // Get the parent coordinates
            y = temp % numberOfColumns;
        }
        Collections.reverse(path); // Reverse the path to get from source to destination
        return path;
    }
}
