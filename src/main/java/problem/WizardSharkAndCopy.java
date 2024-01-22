package problem;

import util.NumberOfCaseGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class WizardSharkAndCopy {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int[][] fishes = new int[m][3];
        int[] shark = new int[2];
        WizardSharkAndCopySolve solve = new WizardSharkAndCopySolve();

        for (int i = 0; i < fishes.length; i++) {
            st = new StringTokenizer(bf.readLine());

            for (int i1 = 0; i1 < fishes[i].length; i1++) {
                fishes[i][i1] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < shark.length; i++) {
            shark[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve.solve(m, s, fishes, shark));
    }
}

class WizardSharkAndCopySolve {
    public int solve(int m, int s, int[][] fishes, int[] sharkCoordinate) {
        /**
         * (r, c)
         * 물고기 M 마리, 이동방향은 8가지
         * 둘 이상 물고기가 같은 칸 가능, 상어랑 물고기도 같은 칸 가능
         * 마법 시작
         * 물고기가 한칸 이동, 8방향 => 상어 / 물고기 냄새 / 격자 밖 X, 이동 할 수 없으면 이동 X, 이동칸을 찾기 위해 반시계 방향 45도로 계속 돎
         * 상어는 3칸 연속해서 이동, 상하좌우, 제외되는 물고기 수가 가장 많은 곳으로, 동시에 여러 곳이면 사전 순 => 격자 밖 X, 물고기 칸으로 이동하면 해당 칸에 있는 모든 물고기는 격자에서 제외, 물고기 냄새 남
         * 물고기 냄새가 사라짐
         * 복제된 물고기는 위치와 방향 그대로 가짐
         * 물고기수 M, 마법 횟수 S, 물고기 위치 (x,y) & 방향 d, 상어 (x,y)
         *
         * 1. 물고기 이동 => 물고기의 (x, y, d)를 큐에 넣어서 하나씩 이동 => 이동할 때 condition 체크하기. V
         * 2. 상어 이동 => 물고기 수 가장 많이를 어떻게 찾을 것인지? => 이후 숫자로 사전순 / bfs, visited[][]로 처리 V
         * 3. 물고기 냄새 => 좌표를 따로 관리 => 리스트로 관리 => 물고기 냄새 카운트 -1 할 때 마다 O(n) 순회
         * 4. 복제 => 물고기 이동 전의 모습을 관리, 리스트로 관리 => 합칠 땐 2차원 배열?
         * 5. 그럼 처음 모습을 저장하려면 전체 순회를 해야하는건가? 어차피 무조건 4x4니까 ㄱㅊ? 굳이 2차원으로 해야하나??
         * 6. 물고기 냄새는 2차원으로 해야할 거 같고, 물고기 관리 자체는 1차원으로 하는게 좋을거 같은데.
         * */

        Shark shark = new Shark(sharkCoordinate[1], sharkCoordinate[0]);
        List<Fish> fishList = Arrays.stream(fishes).map(fish -> new Fish(fish[1], fish[0], fish[2])).collect(Collectors.toList());
        FishSmell[][] fishSmells = new FishSmell[5][5];

        // 4x4에 물고기 냄새 할당
        // -1이면 냄새 X, 0 이상이면 냄새 O
        for (int i = 0; i < fishSmells.length; i++) {
            for (int j = 0; j < fishSmells.length; j++) {
                fishSmells[i][j] = new FishSmell();
            }
        }

        for (int i = 0; i < s; i++) {
            // 1. 복제 마법 시전
            List<Fish> temp = fishList.stream().map(fish -> new Fish(fish.getX(), fish.getY(), fish.getDirection())).collect(Collectors.toList());
            int[][] numOfFishes = new int[5][5];

            // 2. 물고기 이동
            // 각 물고기 이동 후, 해당 위치에 물고기 개수 1개 추가
            for (Fish fish : fishList) {
                fish.move(fishSmells, shark);

                numOfFishes[fish.getY()][fish.getX()] += 1;
            }


            // 4. 물고기 냄새 삭제
            for (FishSmell[] fishSmell : fishSmells) {
                for (FishSmell smell : fishSmell) {
                    smell.reduceCount();
                }
            }

            // 3. 상어 이동
            int[][] sharkRoutes = shark.move(fishSmells, numOfFishes);

            // 상어가 지나온 길 물고기 전부 지우기
            List<Fish> remainFishList = fishList.stream().filter(fish -> {
                for (int j = 0; j < sharkRoutes.length; j++) {
                    if (fish.getX() == sharkRoutes[j][0] && fish.getY() == sharkRoutes[j][1]) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());

            // 복제
            temp.addAll(remainFishList);
            fishList = new ArrayList<>(temp);
        }

        return fishList.size();
    }
}

abstract class Move {
    private int x;
    private int y;

    public Move(int x, int y) {
        assert (x > 0 && x < 5);
        assert (y > 0 && y < 5);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected void setX(int x) {
        assert (x > 0 && x < 5);
        this.x = x;
    }

    protected void setY(int y) {
        assert (y > 0 && y < 5);
        this.y = y;
    }

    private int MIN_COORDINATE = 1;

    private int MAX_COORDINATE = 4;

    // 사용X, 좌, 좌상, 상, 우상, 우, 우하, 하, 좌하
    // x, y
    int[][] coordinateDirections = {{0, 0}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};

    // 사용 X, 상, 좌, 하, 우
    int[][] coordinateDirectionsForShark = {{0, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

    protected boolean isSafeCoordinate(int x, int y) {
        // 격자 밖 X
        if (x > MAX_COORDINATE || x < MIN_COORDINATE) {
            return false;
        }

        // 격자 밖 X
        if (y > MAX_COORDINATE || y < MIN_COORDINATE) {
            return false;
        }

        return true;
    }
}

class Shark extends Move {
    private List<List<Integer>> routes = generate(new ArrayList<>(Arrays.asList(1, 2, 3, 4)), 3);

    public Shark(int x, int y) {
        super(x, y);
    }

    public int[][] move(FishSmell[][] fishSmells, int[][] numOfFishes) {
        int max = Integer.MIN_VALUE;
        List<List<Integer>> maxRoutes = new ArrayList<>();
        int[][] result = new int[3][2];

        for (List<Integer> route : routes) {
            int sum = 0;
            int tempX = getX();
            int tempY = getY();
            boolean safeCoordinate = true;
            boolean[][] visited = new boolean[5][5];

            for (Integer integer : route) {
                tempX += coordinateDirectionsForShark[integer][0];
                tempY += coordinateDirectionsForShark[integer][1];

                if (!isSafeCoordinate(tempX, tempY)) {
                    safeCoordinate = false;
                    break;
                }

                if(!visited[tempY][tempX]) {
                    sum += numOfFishes[tempY][tempX];
                    visited[tempY][tempX] = true;
                }
            }

            if (safeCoordinate && sum >= max) {
                List<Integer> maxRoute = new ArrayList<>(route);

                max = sum;
                maxRoute.add(sum);
                maxRoutes.add(maxRoute);
            }
        }

        int currentIndex = 0;
        int currentValue = -1;
        int finalMax = max;
        final List<List<Integer>> filteredMaxRoutes = maxRoutes.stream().filter(maxRoute -> maxRoute.get(3) == finalMax).collect(Collectors.toList());

        // max 값보다 작은거 다 삭제
        // 같으면 사전 순으로 고르기
        for (int i = 0; i < filteredMaxRoutes.size(); i++) {
            List<Integer> route = filteredMaxRoutes.get(i);

            if (route.get(3) < max) {
                continue;
            }

            if (currentValue == -1) {
                currentValue = route.get(0) * 100 + route.get(1) * 10 + route.get(2);
                currentIndex = i;
                continue;
            }

            int nextValue = route.get(0) * 100 + route.get(1) * 10 + route.get(2);

            if (nextValue < currentValue) {
                currentValue = nextValue;
                currentIndex = i;
            }
        }

        // 상어 위치 업데이트
        // 상어가 지나온 길 냄새 추가
        for (int i = 0; i < filteredMaxRoutes.get(currentIndex).size() - 1; i++) {
            setX(getX() + coordinateDirectionsForShark[filteredMaxRoutes.get(currentIndex).get(i)][0]);
            setY(getY() + coordinateDirectionsForShark[filteredMaxRoutes.get(currentIndex).get(i)][1]);
            result[i][0] = getX();
            result[i][1] = getY();

            if (numOfFishes[getY()][getX()] > 0) {
                fishSmells[getY()][getX()].generate();
            }
        }

        return result;
    }

    // 상상상 처럼 중복 허용
    public List<List<Integer>> generate(List<Integer> list, int r) {
        assert (list.size() >= r);
        List<List<Integer>> permutations = new ArrayList<>();
        backtrack(permutations, list, new ArrayList<>(), r);
        return permutations;
    }

    private void backtrack(List<List<Integer>> permutations, List<Integer> list, List<Integer> temp, int remain) {
        if (remain == 0) {
            permutations.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            temp.add(list.get(i));
            backtrack(permutations, list, temp, remain - 1);
            temp.remove(temp.size() - 1);
        }
    }

    @Override
    public String toString() {
        return "Shark{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}

class Fish extends Move {

    /**
     * 왼쪽이 1, 상단이 3, 오른쪽이 5, 하단이 7
     */
    private int direction;

    public Fish(int x, int y, int direction) {
        super(x, y);
        assert (direction > 0 && direction < 9);
        this.direction = direction;
    }


    public void setDirection(int direction) {
        assert (direction > 0 && direction < 9);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public void move(FishSmell[][] fishSmells, Shark shark) {
        move(fishSmells, 0, shark);
    }

    private void move(FishSmell[][] fishSmells, int count, Shark shark) {
        if (count >= 8) {
            return;
        }

        int[] coordinateDirection = coordinateDirections[direction];
        int nextX = getX() + coordinateDirection[0];
        int nextY = getY() + coordinateDirection[1];

        if (!isSafeCoordinate(fishSmells, nextX, nextY, shark)) {
            int newDirection = (direction - 1) % 8;

            if (newDirection <= 0) {
                newDirection = 8;
            }

            this.setDirection(newDirection);

            move(fishSmells, count + 1, shark);
            return;
        }

        this.setX(nextX);
        this.setY(nextY);
    }

    protected boolean isSafeCoordinate(FishSmell[][] fishSmells, int nextX, int nextY, Shark shark) {
        // 격자 밖 X

        if (!super.isSafeCoordinate(nextX, nextY)) {
            return false;
        }

        // 상어
        if (shark.getX() == nextX && shark.getY() == nextY) {
            return false;
        }

        // 물고기 냄새
        return !fishSmells[nextY][nextX].isActivated();
    }

    @Override
    public String toString() {
        return "Fish{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", direction=" + direction +
                '}';
    }
}

class FishSmell {
    int count = -1;

    // 2번짜리 냄새
    void generate() {
        count = 1;
    }

    // 냄새를 1씩 줄임
    void reduceCount() {
        if (isActivated()) {
            count -= 1;
        }
    }

    // -1보다 크면 냄새가 있음
    boolean isActivated() {
        return count > -1;
    }

    @Override
    public String toString() {
        return "FishSmell{" +
                "count=" + count +
                '}';
    }
}
