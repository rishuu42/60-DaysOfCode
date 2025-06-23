/**
 * -------------------------------------------------------------------
 * Program Title : K Queues in a Single Array (Naive Fixed Partitioning)
 * Author        : Rishitt Gupta
 * Language      : Java
 * Date          : 24th June 2025
 * Description   :
 * This program implements 'k' independent queues using a single array
 * of size 'n'. It uses a naive approach where the array is divided into 
 * k equal-sized segments, and each queue operates only within its own 
 * fixed partition. This ensures logical separation without dynamic sharing 
 * of space.
 *
 * Key Features:
 * - Enqueue and dequeue operations for k separate queues
 * - Constant time operations using front[] and rear[]
 * - Circular behavior within each queue’s own segment
 * -------------------------------------------------------------------
 */

import java.util.*;

class kQueues {
    int[] arr;     // Shared array to store all queue elements
    int n, k;      // n = total size of array, k = number of queues
    int[] front;   // Front index for each queue
    int[] rear;    // Rear index for each queue

    // Constructor
    kQueues(int n, int k) {
        this.n = n;
        this.k = k;
        arr = new int[n];
        front = new int[k];
        rear = new int[k];
        Arrays.fill(front, -1);  // Initialize all queues as empty
        Arrays.fill(rear, -1); 
    }

    // Check if a specific queue is empty
    static boolean isEmpty(int[] front, int qn) {
        return front[qn] == -1;
    }

    // Check if a specific queue is full (within its segment)
    static boolean isFull(int[] front, int[] rear, int qn, int n, int k) {
        int size = n / k;
        int nextPos = (rear[qn] + 1) % size;
        return (nextPos == front[qn] % size && front[qn] != -1);
    }

    // Enqueue element 'x' into queue number 'qn'
    boolean enqueue(int x, int qn) {
        if (isFull(front, rear, qn, n, k))
            return false; // Queue full

        int size = n / k;
        int base = qn * size;

        // First element in queue
        if (isEmpty(front, qn)) {
            front[qn] = base;
            rear[qn] = base;
        } else {
            // Move rear forward in a circular manner within segment
            rear[qn] = base + (rear[qn] + 1 - base) % size;
        }

        arr[rear[qn]] = x; // Insert element
        return true;
    }

    // Dequeue element from queue number 'qn'
    int dequeue(int qn) {
        if (isEmpty(front, qn))
            return -1; // Queue empty

        int size = n / k;
        int base = qn * size;
        int x = arr[front[qn]];

        // If only one element was present
        if (front[qn] == rear[qn]) {
            front[qn] = -1;
            rear[qn] = -1;
        } else {
            // Move front forward within segment
            front[qn] = base + (front[qn] + 1 - base) % size;
        }

        return x;
    }
}

public class Day23_Q1_KQueue {
    public static void main(String[] args) {
        int n = 10, k = 3; // 10 slots, 3 queues
        kQueues queues = new kQueues(n, k);

        // Enqueue operations
        System.out.print(queues.enqueue(10, 0) + " ");  // Queue 0: 10
        System.out.print(queues.enqueue(20, 1) + " ");  // Queue 1: 20
        System.out.print(queues.enqueue(30, 0) + " ");  // Queue 0: 30
        System.out.print(queues.enqueue(40, 2) + " ");  // Queue 2: 40

        // Dequeue operations
        System.out.print(queues.dequeue(0) + " ");  // Removes 10 f=1 3 6 r = 1 3 6
        System.out.print(queues.dequeue(1) + " ");  // Removes 20 f=1 -1 6 r = 1 -1 6
        System.out.print(queues.dequeue(2) + " ");  // Removes 40 f=1 -1 -1 r = 1 -1 -1
        System.out.print(queues.dequeue(0) + " ");  // Removes 30 f=-1 -1 -1 r = -1 -1 -1
        System.out.print(queues.dequeue(0) + " ");  // Empty → -1
    }
}
