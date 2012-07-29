/* Copyright 2012 Ben Manes. All Rights Reserved. */
package com.github.benmanes.lockfreequeue;

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

  @Test(dataProvider = "emptyQueue")
  public void size_whenEmpty(ConcurrentSingleConsumerQueue<Integer> queue) {
    assertThat(queue.size(), is(0));
  }

  @Test(dataProvider = "emptyQueue")
  public void isEmpty_whenEmpty(ConcurrentSingleConsumerQueue<Integer> queue) {
    assertThat(queue, is(empty()));
  }

  @DataProvider(name = "emptyQueue")
  public Object[][] providesEmptyQueue() {
    return new Object[][] {{ new ConcurrentSingleConsumerQueue<Integer>(ESTIMATED_CAPACITY) }};
  }
}
