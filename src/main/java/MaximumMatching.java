

public class MaximumMatching {

    /**
     * 获取二分图的最大匹配数
     *
     * @param graph 邻接矩阵表示的二分图，graph[u][v] == 1 表示从左边集合的顶点 u 到右边集合的顶点 v 存在一条边
     * @return 最大匹配数
     */
    public static int getMaximumMatching(int[][] graph) {
        int n = graph.length; // 左集合的顶点数
        int m = graph[0].length; // 右集合的顶点数

        // 匹配对：match[v] 表示右集合顶点 v 当前匹配的左集合顶点
        int[] match = new int[m];
        for (int i = 0; i < m; i++) {
            match[i] = -1; // 初始化为 -1，表示未匹配
        }

        int maxMatching = 0; // 最大匹配数
        for (int u = 0; u < n; u++) {
            // 用于标记右集合顶点是否在当前 DFS 中访问过
            boolean[] visited = new boolean[m];
            if (dfs(graph, u, visited, match)) {
                maxMatching++;
            }
        }

        return maxMatching;
    }

    /**
     * 深度优先搜索，尝试为左集合顶点 u 寻找增广路径
     *
     * @param graph   邻接矩阵
     * @param u       左集合的当前顶点
     * @param visited 标记右集合顶点是否已访问
     * @param match   匹配对
     * @return 是否找到增广路径
     */
    private static boolean dfs(int[][] graph, int u, boolean[] visited, int[] match) {
        for (int v = 0; v < graph[u].length; v++) {
            if (graph[u][v] == 1 && !visited[v]) {
                visited[v] = true; // 标记 v 为已访问
                // 如果 v 未匹配，或者 v 的当前匹配点可以重新匹配
                if (match[v] == -1 || dfs(graph, match[v], visited, match)) {
                    match[v] = u; // 将 v 匹配给 u
                    return true;
                }
            }
        }
        return false;
    }
}
