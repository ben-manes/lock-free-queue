/* Copyright 2012 Ben Manes. All Rights Reserved. */
package com.github.benmanes.lockfreequeue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A linked-list based concurrent queue supporting multiple producers and a single consumer.
 *
 * @author ben.manes@gmail.com (Ben Manes)
 */
public final class MPSCQueue<E> implements Queue<E> {
  Node<E> tail = new Node<E>(null);
  final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(tail);

  @Override
  public boolean offer(E e) {
    Node<E> node = new Node<E>(e);
    head.getAndSet(node).lazySet(node);
    return true;
  }

  @Override
  public E poll() {
    Node<E> next = tail.get();
    if (next == null) {
      return null;
    } else {
      tail = next;
      return next.value;
    }
  }

  static class Node<T> extends AtomicReference<Node<T>> {
    private static final long serialVersionUID = 1L;

    final T value;

    Node(T value) {
      this.value = value;
    }
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean contains(Object o) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Iterator<E> iterator() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Object[] toArray() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean remove(Object o) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void clear() {
    while (poll() != null);
  }

  @Override
  public boolean add(E e) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public E remove() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public E element() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public E peek() {
    // TODO Auto-generated method stub
    return null;
  }
}
