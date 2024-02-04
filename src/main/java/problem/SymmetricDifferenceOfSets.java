package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class SymmetricDifferenceOfSets {
    public static  void main(String[] args) throws IOException {
        SymmetricDifferenceOfSetsSolve solve = new SymmetricDifferenceOfSetsSolve();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[] setA = new int[n];
        int[] setB = new int[m];

        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < n; i++) {
            setA[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(bf.readLine());

        for (int i = 0; i < m; i++) {
            setB[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve.solve(setA, setB, n, m));
    }
}

class SymmetricDifferenceOfSetsSolve {
    public int solve(int[] setA, int[] setB, int n, int m) {
        Set<Integer> hashSetA = new HashSet<>();
        Set<Integer> hashSetB = new HashSet<>();
        Set<Integer> diffSetAFromB;
        Set<Integer> diffSetBFromA;

        for (int i : setA) {
            hashSetA.add(i);
        }

        for (int i : setB) {
            hashSetB.add(i);
        }

        diffSetAFromB = new HashSet<>(hashSetA);
        diffSetBFromA = new HashSet<>(hashSetB);

        for (Integer integer : hashSetA) {
            diffSetBFromA.remove(integer);
        }

        for (Integer integer : hashSetB) {
            diffSetAFromB.remove(integer);
        }

        return diffSetBFromA.size() + diffSetAFromB.size();
    }
}
