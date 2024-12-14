package benchmark;

import org.openjdk.jmh.annotations.*;
import utils.Solution2071;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Solution2071Benchmark {

    private Solution2071 solution2071;

    private int[] tasks;
    private int[] workers;

    public int general(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks);
        Arrays.sort(workers);

        int max = 0, left = 1, right = Math.min(tasks.length, workers.length);
        while (left <= right) {
            int mid = (left + right) / 2;
            if (solution2071.check(tasks, workers, pills, strength, mid)) {
                max = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return max;
    }

    public int refactor(int[] tasks, int[] workers, int pills, int strength) {
        Arrays.sort(tasks);
        Arrays.sort(workers);

        int max = 0, left = 1, right = Math.min(tasks.length, workers.length);
        while (left <= right) {
            int mid = (left + right) / 2;
            if (solution2071.checkRefactor(tasks, workers, pills, strength, mid)) {
                max = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return max;
    }

    @Setup
    public void setup() throws Exception {
        solution2071 = new Solution2071();
        tasks = Arrays.stream(new String(Files.readAllBytes(Paths.get("D:/Desktop/tasks.txt")), StandardCharsets.UTF_8).split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();


        workers = Arrays.stream(new String(Files.readAllBytes(Paths.get("D:/Desktop/workers.txt")), StandardCharsets.UTF_8).split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @Benchmark
    public void generalBenchmark() {
        general(
                new int[]{3, 2, 1},
                new int[]{0, 3, 3},
                1, 1
        );
        general(
                new int[]{5, 4},
                new int[]{0, 0, 0},
                1, 5
        );
        general(
                new int[]{10, 15, 30},
                new int[]{0, 10, 10, 10, 10},
                3, 10
        );

        general(
                new int[]{5, 9, 8, 5, 9},
                new int[]{1, 6, 4, 2, 6},
                1, 5
        );

        general(tasks, workers, 13673, 390295280);
    }

    @Benchmark
    public void refactorBenchmark() {
        refactor(
                new int[]{3, 2, 1},
                new int[]{0, 3, 3},
                1, 1
        );
        refactor(
                new int[]{5, 4},
                new int[]{0, 0, 0},
                1, 5
        );
        refactor(
                new int[]{10, 15, 30},
                new int[]{0, 10, 10, 10, 10},
                3, 10
        );

        refactor(
                new int[]{5, 9, 8, 5, 9},
                new int[]{1, 6, 4, 2, 6},
                1, 5
        );

        refactor(tasks, workers, 13673, 390295280);
    }
}
