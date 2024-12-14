package utils;

import java.util.Arrays;
import java.util.List;

/**
 * 通用二分图寻找最大匹配算法-匈牙利算法
 * <p>
 * 包含两种实现：
 * 1. 基于邻接矩阵和DFS查找增广路径的方式，时间复杂度约为O(n*m*m)，稠密图时接近O(V*E)。更适用于稠密图
 * 2. 基于邻接表和DFS查找增广路径的方式，时间复杂度约为O(V*E)。适合所有情况
 *
 * @see <a href="https://leetcode.cn/problems/maximum-matching-of-players-with-trainers/description/">2410. 运动员和训练师的最大匹配数 - 力扣（LeetCode）</a>
 * @see <a href="https://leetcode.cn/problems/maximum-number-of-tasks-you-can-assign/description/">2071. 你可以安排的最多任务数目 - 力扣（LeetCode）</a>
 */
public class MaximumMatching {

    /**
     * 方法1：使用邻接矩阵实现的最大匹配
     *
     * @param graph 邻接矩阵表示的二分图，graph[u][v] == 1 表示有边
     * @return 最大匹配数
     */
    public static int getMaximumMatchingMatrix(int[][] graph) {
        int n = graph.length;        // 左集合顶点数
        int m = graph[0].length;     // 右集合顶点数

        int[] match = new int[m];    // 右集合匹配到的左集合顶点
        Arrays.fill(match, -1);

        int maxMatching = 0;
        for (int u = 0; u < n; u++) {
            boolean[] visited = new boolean[m];
            if (dfsMatrix(graph, u, visited, match)) {
                maxMatching++;
            }
        }
        return maxMatching;
    }

    /**
     * 邻接矩阵版本的DFS增广路径
     *
     * @param graph   二分图的邻接矩阵
     * @param u       左集合顶点
     * @param visited 记录增广路径中，右集合顶点是否已被访问过
     * @param match   右集合顶点匹配到的左集合顶点，未匹配到为-1
     * @return true表示找到了新的增广路径
     */
    private static boolean dfsMatrix(int[][] graph, int u, boolean[] visited, int[] match) {
        for (int v = 0; v < graph[u].length; v++) {
            if (graph[u][v] == 1 && !visited[v]) {
                visited[v] = true;
                if (match[v] == -1 || dfsMatrix(graph, match[v], visited, match)) {
                    match[v] = u;
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 方法2：使用邻接表实现的最大匹配
     *
     * @param graph 邻接表表示的二分图，graph[u] 是左集合顶点 u 的邻接右集合顶点列表
     * @param m     右集合顶点数量
     * @return 最大匹配数
     */
    public static int getMaximumMatchingList(List<Integer>[] graph, int m) {
        int n = graph.length;        // 左集合顶点数

        int[] match = new int[m];    // 右集合匹配到的左集合顶点
        Arrays.fill(match, -1);

        int maxMatching = 0;
        for (int u = 0; u < n; u++) {
            boolean[] visited = new boolean[m];
            if (dfsList(graph, u, visited, match)) {
                maxMatching++;
            }
        }
        return maxMatching;
    }

    /**
     * 邻接表版本的DFS增广路径
     *
     * @param graph   二分图的邻接表
     * @param u       左集合顶点
     * @param visited 记录增广路径中，右集合顶点是否已被访问过
     * @param match   右集合顶点匹配到的左集合顶点，未匹配到为-1
     * @return true表示找到了新的增广路径
     */
    private static boolean dfsList(List<Integer>[] graph, int u, boolean[] visited, int[] match) {
        for (int v : graph[u]) {
            if (!visited[v]) {
                visited[v] = true;
                if (match[v] == -1 || dfsList(graph, match[v], visited, match)) {
                    match[v] = u;
                    return true;
                }
            }
        }
        return false;
    }
}
