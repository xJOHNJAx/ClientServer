package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Client client = new Client(executor);
        Server server = new Server(executor);

        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            requests.add(new Request(i));
        }

        client.sendRequests(requests, server);
        List<Response> responses = client.getResponses();

        responses.forEach(response -> System.out.println("Response: " + response.value));

        executor.shutdown();
    }
}
