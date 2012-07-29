/* Copyright 2012 Ben Manes. All Rights Reserved. */
package com.github.benmanes.lockfreequeue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * This benchmark evaluates single-threaded performance.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public class QueueBenchmark extends SimpleBenchmark {
  private ConcurrentSingleConsumerQueue<Integer> linkedSingleConsumerQueue;
  private ConcurrentSingleConsumerQueue<Integer> arraySingleConsumerQueue;
  private Queue<Integer> concurrentLinkedQueue;
  private MPSCQueue<Integer> mpscQueue;
  private AtomicInteger length;

  @Override
  protected void setUp() {
    arraySingleConsumerQueue = new ConcurrentSingleConsumerQueue<Integer>(16);
    linkedSingleConsumerQueue = new ConcurrentSingleConsumerQueue<Integer>(1);
    concurrentLinkedQueue = new ConcurrentLinkedQueue<Integer>();
    mpscQueue = new MPSCQueue<Integer>();
    length = new AtomicInteger();

    for (int i = 0; i < linkedSingleConsumerQueue.estimatedCapacity(); i++) {
      linkedSingleConsumerQueue.add(i);
    }
  }

  public int timeConcurrentSingleConsumerQueue_array(final int reps) {
    int dummy = 0;
    while (dummy < reps) {
      arraySingleConsumerQueue.offer(dummy);
      arraySingleConsumerQueue.poll();
      dummy++;
    }
    return dummy;
  }

  public int timeConcurrentSingleConsumerQueue_linked(final int reps) {
    int dummy = 0;
    while (dummy < reps) {
      linkedSingleConsumerQueue.offer(dummy);
      linkedSingleConsumerQueue.poll();
      dummy++;
    }
    return dummy;
  }

  public int timeConcurrentLinkedQueue(final int reps) {
    int dummy = 0;
    while (dummy < reps) {
      concurrentLinkedQueue.offer(dummy);
      length.incrementAndGet();

      concurrentLinkedQueue.poll();
      length.decrementAndGet();
      dummy++;
    }
    return dummy;
  }

  public int timeMultiProducerSingleConsumer(final int reps) {
    int dummy = 0;
    while (dummy < reps) {
      mpscQueue.offer(dummy);
      length.incrementAndGet();

      mpscQueue.poll();
      length.decrementAndGet();
      dummy++;
    }
    return dummy;
  }

  public static void main(String[] args) {
    Runner.main(QueueBenchmark.class, args);
  }
}
