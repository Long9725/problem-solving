package problem;

import java.util.*;
import java.util.stream.*;

public class PGPCCP3 {
    public int solution(int[][] points, int[][] routes) {
        long answer = 0;
        final List<Point> pointList = Arrays.stream(points)
                .map(point -> new Point(point[1], point[0]))
                .collect(Collectors.toList());
        final List<RobotRoutes> routeList = Arrays.stream(routes)
                .map(route -> new RobotRoutes(Arrays.stream(route).mapToObj(value -> pointList.get(value - 1)).collect(Collectors.toList())))
                .collect(Collectors.toList());
        final List<Robot> robots = routeList.stream()
                .map(Robot::new)
                .collect(Collectors.toList());

        while (robots.stream().anyMatch(robot -> !robot.done())) {
            for (final Robot robot : robots) {
                robot.move();
            }
            final List<Robot> isNotDoneRobots = robots.stream()
                    .filter(robot -> !robot.done())
                    .collect(Collectors.toList());

            answer += isNotDoneRobots.size() - isNotDoneRobots.stream()
                    .map(robot -> robot.point)
                    .distinct()
                    .count();
        }

        return (int) answer;
    }
}

class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Robot {
    final Point point;
    final RobotRoutes routes;

    public Robot(Point point, RobotRoutes routes) {
        this.point = point;
        this.routes = routes;
    }

    public Robot(RobotRoutes routes) {
        this.point = routes.getCurrentStartPoint();
        this.routes = routes;
    }

    // 다음 포인트로 이동할 때는 항상 최단 경로로 이동하며 최단 경로가 여러 가지일 경우, r 좌표가 변하는 이동을 c 좌표가 변하는 이동보다 먼저 합니다.
    public void move() {
        if (done()) {
            return;
        }

        final Point endPoint = routes.getCurrentEndPoint();

        if (point.x > endPoint.x) {
            point.x--;
        } else if (point.x < endPoint.x) {
            point.x++;
        } else if (point.y > endPoint.y) {
            point.y--;
        } else if (point.y < endPoint.y) {
            point.y++;
        }

        // 위치가 목표 지점에 도달했을 때만 인덱스 업데이트
        if (point.equals(endPoint)) {
            routes.updateIndex();
        }
    }

    public boolean done() {
        return routes.isDone();
    }

    @Override
    public String toString() {
        return "Robot{" +
                "point=" + point + ", " +
                "done=" + done() +
                '}';
    }
}

class RobotRoutes {
    final List<Point> points;
    int startIndex;

    public RobotRoutes(List<Point> points) {
        this.points = points;
        this.startIndex = 0;
    }

    public Point getCurrentStartPoint() {
        return points.get(startIndex);
    }

    public Point getCurrentEndPoint() {
        return points.get(startIndex + 1);
    }

    public void updateIndex() {
        if (startIndex < points.size() - 1) {
            startIndex++;
        }
    }

    public boolean isDone() {
        return startIndex >= points.size() - 1;
    }

    @Override
    public String toString() {
        return "RobotRoutes{" +
                "points=" + points +
                ", startIndex=" + startIndex +
                '}';
    }
}