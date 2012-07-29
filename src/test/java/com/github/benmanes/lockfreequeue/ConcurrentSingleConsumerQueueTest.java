/* Copyright 2012 Ben Manes. All Rights Reserved. */
package com.github.benmanes.lockfreequeue;

import java.util.Queue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

/**
 * A unit-test for {@link java.util.Queue} interface. These tests do not assert
 * correct concurrency behavior.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public final class ConcurrentSingleConsumerQueueTest {
  private static final int ESTIMATED_CAPACITY = 16;

  @Test(enabled = false, dataProvider = "allQueues")
  public void clear(Queue<Integer> queue) {
    queue.clear();
    assertThat(queue, is(empty()));
  }

  @Test(dataProvider = "emptyQueue")
  public void size_whenEmpty(Queue<Integer> queue) {
    assertThat(queue.size(), is(0));
  }

  @Test(dataProvider = "warmedQueue")
  public void size_whenPopulated(Queue<Integer> queue) {
    assertThat(queue.size(), is(2 * ESTIMATED_CAPACITY));
  }

  @Test(dataProvider = "emptyQueue")
  public void isEmpty_whenEmpty(Queue<Integer> queue) {
    assertThat(queue.isEmpty(), is(true));
  }

  @Test(dataProvider = "warmedQueue")
  public void isEmpty_whenPopulated(Queue<Integer> queue) {
    assertThat(queue.isEmpty(), is(false));
  }

  @Test(dataProvider = "emptyQueue")
  public void contains_withNull(Queue<Integer> queue) {
    assertThat(queue.contains(null), is(false));
  }

  @Test(dataProvider = "warmedQueue")
  public void contains_whenFound(Queue<Integer> queue) {
    assertThat(queue.contains(1), is(true));
  }

  @Test(dataProvider = "warmedQueue")
  public void contains_whenNotFound(Queue<Integer> queue) {
    assertThat(queue.contains(-1), is(false));
  }

  /* ---------------- Queue providers -------------- */

  @DataProvider(name = "allQueues")
  public Object[][] providesAllQueues() {
    return new Object[][] {{ emptyQueue() }, { warmedQueue() }};
  }

  @DataProvider(name = "emptyQueue")
  public Object[][] providesEmptyQueue() {
    return new Object[][] {{ new ConcurrentSingleConsumerQueue<Integer>(ESTIMATED_CAPACITY) }};
  }

  @DataProvider(name = "warmedQueue")
  public Object[][] providesWarmedQueue() {
    return new Object[][] {{ warmedQueue() }};
  }

  private Queue<Integer> emptyQueue() {
    return new ConcurrentSingleConsumerQueue<Integer>(ESTIMATED_CAPACITY);
  }

  private Queue<Integer> warmedQueue() {
    Queue<Integer> queue = new ConcurrentSingleConsumerQueue<Integer>(ESTIMATED_CAPACITY);
    warmUp(queue, 2 * ESTIMATED_CAPACITY);
    return queue;
  }

  private void warmUp(Queue<Integer> queue, int count) {
    for (int i = 0; i < count; i++) {
      queue.add(i + 1);
    }
  }
}
