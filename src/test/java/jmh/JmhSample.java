package jmh;

import benchmark.Solution2071Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhSample {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(Solution2071Benchmark.class.getSimpleName()) // 指定要执行的基准测试
                .forks(1) // 启动多少个独立的 JVM 进程执行
                .warmupIterations(3) // 预热轮数
                .measurementIterations(5) // 实际测量轮数
                .build();

        new Runner(opt).run();
    }
}
