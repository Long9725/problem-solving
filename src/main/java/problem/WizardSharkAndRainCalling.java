package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class WizardSharkAndRainCalling {
    public static void main(String[] args) throws IOException {
        WizardSharkAndRainCallingSolve solve = new WizardSharkAndRainCallingSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] arr = new int[n][n];
        int[][] move = new int[m][2];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(bf.readLine());

            for (int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(bf.readLine());

            move[i][0] = Integer.parseInt(st.nextToken());
            move[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve.solve(arr, move, n, m));
    }
}

class WizardSharkAndRainCallingSolve {
    private Coordinate[] initialCoordinates = {new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(0, -1), new Coordinate(1, -1)};

    private Coordinate[] diagonalCoordinates = {new Coordinate(1, 1), new Coordinate(1, -1), new Coordinate(-1, 1), new Coordinate(-1, -1)};

    public int solve(int[][] arr, int[][] moves, int n, int m) {
        List<WizardSharkAndRainCallingCloud> clouds = new ArrayList<>();
        boolean[][] visited = new boolean[n][n];

        for (int i = 0; i < initialCoordinates.length; i++) {
            clouds.add(new WizardSharkAndRainCallingCloud(new Coordinate(initialCoordinates[i].x, n - 1 + initialCoordinates[i].y)));
        }

        for (int[] move : moves) {
            // 1. 모든 구름이 di 방향으로 si칸 이동한다.
            // 2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물의 양이 1 증가한다.
            for (WizardSharkAndRainCallingCloud cloud : clouds) {
                cloud.move(move[0], move[1], n);
                arr[cloud.coordinate.y][cloud.coordinate.x]++;
                visited[cloud.coordinate.y][cloud.coordinate.x] = true;
            }

            // 4. 2에서 물이 증가한 칸 (r, c)에 물복사버그 마법을 시전한다.
            for (WizardSharkAndRainCallingCloud cloud : clouds) {
                int count = 0;

                // 대각선 좌표들
                for (Coordinate diagonalCoordinate : diagonalCoordinates) {
                    Coordinate next = new Coordinate(cloud.coordinate.x + diagonalCoordinate.x, cloud.coordinate.y + diagonalCoordinate.y);

                    // 대각선 방향으로 거리가 1인 칸에 물이 있는 바구니의 수 세기
                    if (next.isSafeArea(n)) {
                        if (arr[next.y][next.x] > 0) {
                            count++;
                        }
                    }
                }

                // 대각선 방향으로 거리가 1인 칸에 물이 있는 바구니의 수만큼 (r, c)에 있는 바구니의 물이 양이 증가한다.
                arr[cloud.coordinate.y][cloud.coordinate.x] += count;
            }

            // 3. 구름이 모두 사라진다.
            clouds.clear();

            // 5. 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다. 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
            for (int y = 0; y < n; y++) {
                for (int x = 0; x < n; x++) {
                    // 이때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 한다.
                    if (!visited[y][x] && arr[y][x] >= 2) {
                        // 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름이 생기고, 물의 양이 2 줄어든다.
                        clouds.add(new WizardSharkAndRainCallingCloud(new Coordinate(x, y)));
                        arr[y][x] -= 2;
                    }
                }
            }

            // 구름 생성 내역 초기화
            visited = new boolean[n][n];
        }

        return Arrays.stream(arr).map(row -> Arrays.stream(row).reduce(0, Integer::sum)).reduce(0, Integer::sum);
    }
}

class WizardSharkAndRainCallingCloud {
    final Coordinate coordinate;

    private final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};

    private final int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};

    public WizardSharkAndRainCallingCloud(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void move(int direction, int count, int n) {
        int nextX = coordinate.x + dx[direction - 1] * count;
        int nextY = coordinate.y + dy[direction - 1] * count;

        coordinate.move(nextX, nextY, n);
    }

    @Override
    public String toString() {
        return "Cloud{" +
                "coordinate=" + coordinate +
                '}';
    }
}

class Coordinate {
    int x;

    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int nextX, int nextY, int n) {
        nextX = nextX % n;
        nextY = nextY % n;

        if (nextX < 0) {
            x = n + nextX;
        } else {
            x = nextX;
        }


        if (nextY < 0) {
            y = n + nextY;
        } else {
            y = nextY;
        }
    }

    public boolean isSafeArea(int n) {
        if (x < 0 || x >= n) {
            return false;
        }
        if (y < 0 || y >= n) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}