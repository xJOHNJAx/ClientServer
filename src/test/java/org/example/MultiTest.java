package org.example;

import org.junit.Test;
import org.junit.Assert;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class MultiTest {
    @Test
    public void testClientServerMultithreading() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Client client = new Client(executor);
        Server server = new Server(executor);

        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            requests.add(new Request(i));
        }

        client.sendRequests(requests, server);
        List<Response> responses = client.getResponses();

        Assert.assertEquals(requests.size(), responses.size());

        for (int i = 0; i < responses.size(); i++) {
            Assert.assertEquals(100 - i, responses.get(i).value);
        }

        executor.shutdown();
        if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
            executor.shutdownNow();
        }
    }
}