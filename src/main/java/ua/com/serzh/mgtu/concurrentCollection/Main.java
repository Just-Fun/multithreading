package ua.com.serzh.mgtu.concurrentCollection;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Serzh on 12/14/16.
 */
public class Main {
    public static void main(String[] args) {
//       Реализованы без synchronized
        AtomicBoolean aBoolean;
        AtomicInteger atomicInteger;
        AtomicLong atomicLong;

        CopyOnWriteArrayList arrayList; // копирование при вставке в ArrayList
        CopyOnWriteArraySet arraySet; // Set интерфейс над CopyOnWriteArrayList
        ConcurrentHashMap hashMap; // thread safe HashMap
        ConcurrentSkipListMap listMap; // ключи уникальны и отсортированы
        ConcurrentSkipListSet listSet; // set на базе ConcurrentSkipListMap

        ConcurrentLinkedQueue linkedQueue; // thread safe очередь

        BlockingQueue blockingQueue; // очередь с ограничение размера
        ArrayBlockingQueue arrayBlockingQueue;
        LinkedBlockingQueue linkedBlockingQueue;

        BlockingDeque blockingDeque; // двухсторонняя очередь
//        ArrayBlockingDeque arrayBlockingDeque;

    }
}
