package problem;

import java.util.*;
import java.util.stream.Collectors;

public class PGPracticeDoAssignment {
    private final Deque<Assignment> remainAssignments = new LinkedList<>();
    private final List<Assignment> doneAssignments = new ArrayList<>();

    /**
     * 과제는 시작하기로 한 시각이 되면 시작합니다.
     * 새로운 과제를 시작할 시각이 되었을 때, 기존에 진행 중이던 과제가 있다면 진행 중이던 과제를 멈추고 새로운 과제를 시작합니다.
     * 진행중이던 과제를 끝냈을 때, 잠시 멈춘 과제가 있다면, 멈춰둔 과제를 이어서 진행합니다.
     * 만약, 과제를 끝낸 시각에 새로 시작해야 되는 과제와 잠시 멈춰둔 과제가 모두 있다면, 새로 시작해야 하는 과제부터 진행합니다.
     * 멈춰둔 과제가 여러 개일 경우, 가장 최근에 멈춘 과제부터 시작합니다.
     * <p>
     * 선점 스케쥴링. 멈춰둔 과제는 queue에 적재.
     * <p>
     * 테스트 2
     * 입력값 〉	[["science", "12:40", "50"], ["music", "12:20", "40"], ["history", "14:00", "30"], ["computer", "12:30", "100"]]
     * 기댓값 〉	["science", "history", "computer", "music"]
     * 실행 결과 〉	실행한 결괏값 ["history","music","computer","music","computer"]이 기댓값 ["science","history","computer","music"]과 다릅니다.
     * 테스트 3
     * 입력값 〉	[["aaa", "12:00", "20"], ["bbb", "12:10", "30"], ["ccc", "12:40", "10"]]
     * 기댓값 〉	["bbb", "ccc", "aaa"]
     * 실행 결과 〉	실행한 결괏값 ["ccc","aaa","aaa"]이 기댓값 ["bbb","ccc","aaa"]과 다릅니다.
     */
    public String[] solution(String[][] plans) {
        final Deque<Assignment> assignments = Arrays.stream(plans)
                .map(plan -> new Assignment(plan[0], plan[1], plan[2]))
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));
        int currentTime = assignments.getFirst().time;
        while (doneAssignments.size() < plans.length) {
            // 멈춰둔 과제만 남았을 경우
            if (assignments.isEmpty()) {
                doneAssignments.addAll(remainAssignments);
                continue;
            }

            final Assignment currentAssignment = assignments.poll();

            // 처리해야할 과제가 하나 남아있을 경우
            if (assignments.isEmpty()) {
                doneAssignments.add(currentAssignment);
                continue;
            }
            // 처리해야할 과제가 두 개 이상 남아있을 경우
            final Assignment nextAssignment = assignments.poll();
            int remainTime = currentAssignment.process(nextAssignment, currentTime);
            currentTime = nextAssignment.time;
            assignments.addFirst(nextAssignment);

            if (currentAssignment.isRemain()) {
                remainAssignments.addFirst(currentAssignment);
                continue;
            }
            doneAssignments.add(currentAssignment);
            while (remainTime > 0 && !remainAssignments.isEmpty()) {
                Assignment remainAssignment = remainAssignments.poll();
                remainTime = remainAssignment.process(remainTime);
                if(remainAssignment.isRemain()) {
                    remainAssignments.addFirst(remainAssignment);
                    continue;
                }
                doneAssignments.add(remainAssignment);
            }
        }

        return doneAssignments.stream()
                .map(assignment -> assignment.name)
                .toArray(String[]::new);
    }
}

class Assignment implements Comparable<Assignment> {
    final String name;
    final int time;
    int playtime;

    public Assignment(String name, int time, int playtime) {
        this.name = name;
        this.time = time;
        this.playtime = playtime;
    }

    public Assignment(String name, String time, String playtime) {
        this(
                name,
                Arrays.stream(time.split(":"))
                        .map(Integer::parseInt)
                        .reduce((left, right) -> left * 60 + right)
                        .get(),
                Integer.parseInt(playtime)
        );
    }

    public int process(
            final Assignment next,
            final int currentTime
    ) {
        if (playtime == 0) {
            return 0;
        }
        final int processTime = next.time - currentTime;
        final int remainTime = Math.max(processTime - playtime, 0);
        playtime = Math.max(playtime - processTime, 0);
        return remainTime;
    }

    public int process(final int remainTime) {
        final int result = Math.max(remainTime - playtime, 0);
        playtime = Math.max(playtime - remainTime, 0);
        return result;
    }

    public boolean isRemain() {
        return playtime > 0;
    }

    @Override
    public int compareTo(Assignment o) {
        return Integer.compare(time, o.time);
    }
}