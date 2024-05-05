package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class Client {
    private final ExecutorService executor;
    private final List<Future<Response>> responses;

    public Client(ExecutorService executor) {
        this.executor = executor;
        this.responses = new ArrayList<>();
    }

    public void sendRequests(List<Request> requests, Server server) {
        for (Request request : requests) {
            Future<Response> future = executor.submit(() -> server.processRequest(request));
            responses.add(future);
        }
    }

    public List<Response> getResponses() throws ExecutionException, InterruptedException {
        List<Response> results = new ArrayList<>();
        for (Future<Response> future : responses) {
            results.add(future.get());
        }
        return results;
    }
}