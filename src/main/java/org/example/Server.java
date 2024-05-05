package org.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Server {
    private final Lock lock = new ReentrantLock();
    private final Random random = new Random();

    public Server(ExecutorService serverExecutor) {
    }

    public Response processRequest(Request request) {
        lock.lock();
        try {
            Thread.sleep(random.nextInt(2000));
            return new Response(100 - request.value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }
}

