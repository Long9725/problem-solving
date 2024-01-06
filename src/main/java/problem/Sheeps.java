package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sheeps {
    static int[] dr = {-1, 1, 0, 0};

    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());

        for (int i = 0; i < n; i++) {
            String sizeString = bf.readLine();
            String[] size = sizeString.split(" ");
            int height = Integer.parseInt(size[0]);
            int width = Integer.parseInt(size[1]);
            boolean[][] grid = new boolean[height][width];

            for (int j = 0; j < height; j++) {
                String input = bf.readLine();

                for (int k = 0; k < width; k++) {
                    if (input.charAt(k) == '#') {
                        grid[j][k] = true;
                    }
                }
            }

            int result = solution(grid, height, width);
            System.out.println(result);
        }
    }

    private static int solution(boolean[][] grid, int height, int width) {
        int result = 0;
        boolean[][] visited = new boolean[height][width];
        Queue<Coordinate> queue = new LinkedList<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] && !visited[row][col]) {
                    result++;
                    visited[row][col] = true;
                    queue.add(new Coordinate(row, col));

                    bfs(grid, visited, queue, height, width);
                }
            }
        }

        return result;
    }

    private static void bfs(boolean[][] grid, boolean[][] visited, Queue<Coordinate> queue, int height, int width) {
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            List<Coordinate> filteredCoordinates = IntStream.range(0, dr.length)
                    .mapToObj(i -> new Coordinate(current.row + dr[i], current.col + dc[i]))
                    .filter(coordinate -> check(grid, visited, height, width, coordinate))
                    .peek(coordinate -> visited[coordinate.row][coordinate.col] = true)
                    .collect(Collectors.toList());

            queue.addAll(filteredCoordinates);
        }
    }

    private static boolean check(boolean[][] grid, boolean[][] visited, int height, int width, Coordinate coordinate) {
        boolean overflow = coordinate.row < 0 || coordinate.row >= height || coordinate.col < 0 || coordinate.col >= width;

        if (overflow) {
            return false;
        }
        if (visited[coordinate.row][coordinate.col]) {
            return false;
        }
        return grid[coordinate.row][coordinate.col];
    }

    static class Coordinate {
        int row;
        int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
