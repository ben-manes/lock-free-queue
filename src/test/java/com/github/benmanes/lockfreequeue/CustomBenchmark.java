/* Copyright 2012 Ben Manes. All Rights Reserved. */
package com.github.benmanes.lockfreequeue;

import java.util.Arrays;

/**
 * This benchmark evaluates single-threaded performance.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public class CustomBenchmark {
  private static final int ITERATIONS = 100000000;

  private QueueBenchmark benchmark;

  void setUp() {
    benchmark = new QueueBenchmark();
    benchmark.setUp();
  }

  void runCSCQ_array() {
    final long start = System.nanoTime();
    benchmark.timeConcurrentSingleConsumerQueue_array(ITERATIONS);
    final long time = System.nanoTime() - start;

    System.out.printf("%s: %,d ns/op\n", "ConcurrentSingleConsumerQueue_array", time / ITERATIONS);
  }

  void runCSCQ_linked() {
    final long start = System.nanoTime();
    benchmark.timeConcurrentSingleConsumerQueue_linked(ITERATIONS);
    final long time = System.nanoTime() - start;

    System.out.printf("%s: %,d ns/op\n", "ConcurrentSingleConsumerQueue_linked", time / ITERATIONS);
  }

  void runCLQ() {
    final long start = System.nanoTime();
    benchmark.timeConcurrentLinkedQueue(ITERATIONS);
    final long time = System.nanoTime() - start;

    System.out.printf("%s: %,d ns/op\n", "ConcurrentLinkedQueue", time / ITERATIONS);
  }

  void runMPSCQ() {
    final long start = System.nanoTime();
    benchmark.timeMultiProducerSingleConsumer(ITERATIONS);
    final long time = System.nanoTime() - start;

    System.out.printf("%s: %,d ns/op\n", "MPSCQueue", time / ITERATIONS);
  }

  public static void main(String[] args) {
    boolean setUp = Arrays.asList(args).contains("setUp");
    CustomBenchmark benchmark = new CustomBenchmark();
    benchmark.setUp();

    for (int i = 0; i < 3; i++) {
      System.out.printf("--- %d ---\n", i + 1);

      if (setUp) {
        benchmark.setUp();
      }
      benchmark.runCSCQ_array();

      if (setUp) {
        benchmark.setUp();
      }
      benchmark.runCSCQ_linked();

      if (setUp) {
        benchmark.setUp();
      }
      benchmark.runCLQ();

      if (setUp) {
        benchmark.setUp();
      }
      benchmark.runMPSCQ();
    }
  }
}

