package org.example.streams;

import java.util.stream.LongStream;

public class ParallelizationStreams {

    public static long sum(long value) {
        LongStream l = LongStream.rangeClosed(1, value);
        return l.reduce(0L, Long::sum);
    }

    public static long parallelSum(long value) {
        LongStream l = LongStream.rangeClosed(1, value);
        return l.parallel().reduce(0L, Long::sum);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(sum(1000000000));
        System.out.println("Time take sequential: "+ (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println(parallelSum(1000000000));
        System.out.println("Time take parallel: "+ (System.currentTimeMillis() - start));
    }
}
