package utils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @see <a href="https://leetcode.cn/problems/maximum-number-of-tasks-you-can-assign/description/">2071. 你可以安排的最多任务数目 - 力扣（LeetCode）</a>
 */
public class Solution2071 {

    /**
     * <pre>{@code
     * 给你 n 个任务和 m 个工人。每个任务需要一定的力量值才能完成，需要的力量值保存在下标从 0 开始的整数数组 tasks 中，第 i 个任务需要 tasks[i] 的力量才能完成。每个工人的力量值保存在下标从 0 开始的整数数组 workers 中，第 j 个工人的力量值为 workers[j] 。每个工人只能完成 一个 任务，且力量值需要 大于等于 该任务的力量要求值（即 workers[j] >= tasks[i] ）。
     *
     * 除此以外，你还有 pills 个神奇药丸，可以给 一个工人的力量值 增加 strength 。你可以决定给哪些工人使用药丸，但每个工人 最多 只能使用 一片 药丸。
     *
     * 给你下标从 0 开始的整数数组tasks 和 workers 以及两个整数 pills 和 strength ，请你返回 最多 有多少个任务可以被完成。
     *
     *
     *
     * 示例 1：
     *
     * 输入：tasks = [3,2,1], workers = [0,3,3], pills = 1, strength = 1
     * 输出：3
     * 解释：
     * 我们可以按照如下方案安排药丸：
     * - 给 0 号工人药丸。
     * - 0 号工人完成任务 2（0 + 1 >= 1）
     * - 1 号工人完成任务 1（3 >= 2）
     * - 2 号工人完成任务 0（3 >= 3）
     * 示例 2：
     *
     * 输入：tasks = [5,4], workers = [0,0,0], pills = 1, strength = 5
     * 输出：1
     * 解释：
     * 我们可以按照如下方案安排药丸：
     * - 给 0 号工人药丸。
     * - 0 号工人完成任务 0（0 + 5 >= 5）
     * 示例 3：
     *
     * 输入：tasks = [10,15,30], workers = [0,10,10,10,10], pills = 3, strength = 10
     * 输出：2
     * 解释：
     * 我们可以按照如下方案安排药丸：
     * - 给 0 号和 1 号工人药丸。
     * - 0 号工人完成任务 0（0 + 10 >= 10）
     * - 1 号工人完成任务 1（10 + 10 >= 15）
     * 示例 4：
     *
     * 输入：tasks = [5,9,8,5,9], workers = [1,6,4,2,6], pills = 1, strength = 5
     * 输出：3
     * 解释：
     * 我们可以按照如下方案安排药丸：
     * - 给 2 号工人药丸。
     * - 1 号工人完成任务 0（6 >= 5）
     * - 2 号工人完成任务 2（4 + 5 >= 8）
     * - 4 号工人完成任务 3（6 >= 5）
     *
     *
     * 提示：
     *
     * n == tasks.length
     * m == workers.length
     * 1 <= n, m <= 5 * 104
     * 0 <= pills <= m
     * 0 <= tasks[i], workers[j], strength <= 109
     * }</pre>
     */
    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        /**
         * 尽可能多的将任务分配给工人，若工人能力不够，则嗑药，但是药物有限。
         *
         * 假设最大mid个任务可以完成，那么就需要mid个任务和mid个人，这肯定是mid个最容易的任务和mid个最强的人。
         * 这个mid的值，可以通过二分法来获取，left=1，right=Math.min(tasks.length,workers.length)
         *
         * 思路分析：
         * 1. 将任务由难到易排序
         * 2. 取当前最难的任务
         *   - 若能力最强的人可以完成，则由该人完成
         *   - 若能力最强的人不可以完成，则从能力最差的开始，找一个可以嗑药完成的
         */
        Arrays.sort(tasks);
        Arrays.sort(workers);

        int max = 0, left = 1, right = Math.min(tasks.length, workers.length);
        while (left <= right) {
            int mid = (left + right) / 2;
            // 检查是否能完成mid个任务
            if (checkRefactor(tasks, workers, pills, strength, mid)) {
                max = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return max;
    }

    /**
     * 性能略差，最直观的将思路翻译一下，属于暴力解法
     */
    public boolean check(int[] tasks, int[] workers, int pills, int strength, int mid) {
        int p = pills;
        // 任务的区间控制在[0,mid)，人的区间控制在[workers.length-mid,workers.length)
        boolean[] workerMatched = new boolean[mid];
        for (int i = mid - 1; i >= 0; i--) {
            int ans = 0;
            for (int j = 0; j < mid; j++) {
                // 人的区间为[workers.length-mid,workers.length)，若将其保留原顺序，换算为由下标0开始的长度为mid的数组，那么区间就是[0,mid)
                if (!workerMatched[mid - 1 - j] && workers[workers.length - 1 - j] >= tasks[i]) {
                    workerMatched[mid - 1 - j] = true;
                    ++ans;
                    break;
                } else {
                    if (p > 0 && !workerMatched[j] && (workers[workers.length - mid + j] + strength) >= tasks[i]) {
                        workerMatched[j] = true;
                        ++ans;
                        --p;
                        break;
                    }
                }
            }
            if (ans != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean checkRefactor(int[] tasks, int[] workers, int pills, int strength, int mid) {
        // 双for的时间复杂度为O(mid*mid)
        // 针对双for的内层循环，每次都要从头找，因为没有记录哪个最强的人已经安排了、哪个最弱的人也吃药安排了。这边只需要维护两个指针或者双端队列。每次O(1)取最强人或最弱人
        // 时间复杂度可以降低为O(mid)
        int p = pills;
        // 维护一个双端队列，里面只放置可以完成当前任务的人
        Deque<Integer> deq = new ArrayDeque<>();

        for (int i = mid - 1, ptr = 0; i >= 0; i--) {
            // 按人的能力由大到小，将可以完成当前任务的人放入队列。由于能力从大到小，是单调的，所以可以通过一个指针ptr来保证不会放入重复的人
            // 需要注意获取workers时要转换一下
            while (ptr <= mid - 1 && (workers[workers.length - 1 - ptr] + strength) >= tasks[i]) {
                deq.addFirst(workers[workers.length - 1 - ptr]);
                ++ptr;
            }
            // 判断能否完成当前任务，若不能，则最大就无法完成mid个任务了，直接返回false
            if (deq.isEmpty()) {
                return false;
            } else if (deq.peekLast() >= tasks[i]) {
                deq.pollLast();
            } else {
                if (p <= 0) {
                    return false;
                }
                --p;
                deq.pollFirst();
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Solution2071 s = new Solution2071();
        System.out.println(3 == s.maxTaskAssign(
                new int[]{3, 2, 1},
                new int[]{0, 3, 3},
                1, 1
        ));
        System.out.println(1 == s.maxTaskAssign(
                new int[]{5, 4},
                new int[]{0, 0, 0},
                1, 5
        ));
        System.out.println(2 == s.maxTaskAssign(
                new int[]{10, 15, 30},
                new int[]{0, 10, 10, 10, 10},
                3, 10
        ));

        System.out.println(3 == s.maxTaskAssign(
                new int[]{5, 9, 8, 5, 9},
                new int[]{1, 6, 4, 2, 6},
                1, 5
        ));
    }
}
