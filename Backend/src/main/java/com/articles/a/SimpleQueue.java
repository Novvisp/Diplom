package com.articles.a;

import java.util.LinkedList;

public class SimpleQueue<Long> {
    private LinkedList<Long> queue;

    public SimpleQueue() {
        this.queue = new LinkedList<>();

    }

    public synchronized void enqueue(Long item) {
        queue.add(item);
        notify(); // Уведомляем поток, который ждет элементы в очереди
    }

    public synchronized Long dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Если очередь пуста, поток ждет
        }
        return queue.remove();
    }
    public int size() {
        return queue.size();
    }

}
