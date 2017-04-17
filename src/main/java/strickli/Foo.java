// CLASSIFICATION NOTICE: This file is UNCLASSIFIED
package strickli;

import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.Queue;

import static com.google.common.collect.Queues.newArrayDeque;

@Slf4j
public class Foo {

    public static void main( String[] args ) {
        Queue<Integer> q = newArrayDeque();
        q.add(1);
        q.add(2);
        q.add(3);
        q.add(4);
        q.add(5);

        Deque<Integer> d = newArrayDeque();
        for (Integer i: q) {
            log.info("Queue {}", i);
            d.push(i);
        }

        for (Integer i: d) {
            log.info("Deque {}", i);
        }


    }
}
