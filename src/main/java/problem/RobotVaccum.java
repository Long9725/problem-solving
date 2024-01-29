package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RobotVaccum {
    public static void main(String[] args) throws IOException {
        RobotVaccumSolve solve = new RobotVaccumSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] robot = new int[3];
        int[][] map = new int[n][m];

        st = new StringTokenizer(bf.readLine());
        robot[0] = Integer.parseInt(st.nextToken());
        robot[1] = Integer.parseInt(st.nextToken());
        robot[2] = Integer.parseInt(st.nextToken());

        for (int y = 0; y < n; y++) {
            st = new StringTokenizer(bf.readLine());

            for (int x = 0; x < m; x++) {
                map[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solve.solve(map, robot, m, n));
    }


}

class RobotVaccumSolve {
    // 상좌하우
    public int solve(int[][] map, int[] robot, int m, int n) {
        RobotVaccumInfo robotVaccumInfo = new RobotVaccumInfo(robot[1], robot[0], robot[2]);
        boolean[][] visited = new boolean[n][m];

        visited[robotVaccumInfo.getY()][robotVaccumInfo.getX()] = true;

        while (robotVaccumInfo.move(map, visited)) ;

        return robotVaccumInfo.getAnswer();
    }
}

class RobotVaccumInfo {
    private int x;

    private int y;

    private int direction;

    private int answer;

    // 상우하좌
    private int[] directions = {0, 1, 2, 3};

    private int[] dx = {0, 1, 0, -1};

    private int[] dy = {-1, 0, 1, 0};

    public RobotVaccumInfo(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.answer = 1;
    }

    private void updateDirection() {
        this.direction = (this.direction + 3) % 4;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAnswer() {
        return answer;
    }

    /**
     * true면 계속 움직일 수 있고, false면 움직이지 못하므로 종료
     */
    public boolean move(int[][] map, boolean[][] visited) {
        // 3번 체크
        if (existNotClean(map, visited)) {
            cleanIfCanImmediately(map, visited);
            return true;
        }

        // 2번
        int index = (getNextMoveIndex() + 2) % 4;
        int nextX = this.x + dx[index];
        int nextY = this.y + dy[index];
        int n = map.length;
        int m = map[0].length;

        if (isWall(nextX, nextY, m, n)) {
            return false;
        }
        // 2-1 해당
        if (map[nextY][nextX] == 0) {
            // 한 칸 후진
            this.x = nextX;
            this.y = nextY;
            return true;
        }

        return false;
    }

    /**
     * 반시계 방향으로 90도 회전한 뒤 즉시 청소가능하면 청소하고 전진, 아니면 청소하지 않음
     */
    private void cleanIfCanImmediately(int[][] map, boolean[][] visited) {
        int index, nextX, nextY;
        int n = map.length;
        int m = map[0].length;

        // 3-1
        updateDirection();

        index = getNextMoveIndex();

        // 3-2 체크
        if (index != -1) {
            nextX = this.x + dx[index];
            nextY = this.y + dy[index];

            if (isWall(nextX, nextY, m, n)) {
                return;
            }

            // 3-2 해당
            if (!visited[nextY][nextX] && map[nextY][nextX] == 0) {
                // 방문 처리이후 한 칸 전진
                answer++;
                visited[nextY][nextX] = true;
                this.x = nextX;
                this.y = nextY;
            }
        }
    }

    /**
     * 현재 방향의 인덱스 가져오기
     */
    private int getNextMoveIndex() {
        int index = -1;

        for (int i = 0; i < directions.length; i++) {
            if (direction == directions[i]) {
                index = i;
            }
        }

        return index;
    }

    /**
     * 주변 4칸 중 청소 안한 곳이 존재하는지 확인
     */
    private boolean existNotClean(int[][] map, boolean[][] visited) {
        for (int i = 0; i < dx.length; i++) {
            int n = map.length;
            int m = map[0].length;
            int nextX = this.x + dx[i];
            int nextY = this.y + dy[i];

            if (isWall(nextX, nextY, m, n)) {
                continue;
            }

            // 청소 안한 곳이 존재
            if (!visited[nextY][nextX] && map[nextY][nextX] == 0) {
                return true;
            }
        }

        return false;
    }

    private boolean isWall(int x, int y, int m, int n) {
        // 벽
        if (x < 1 || x >= m - 1) {
            return true;
        }

        // 벽
        if (y < 1 || y >= n - 1) {
            return true;
        }

        return false;
    }
}