package org.example;

import org.junit.Test;
import org.junit.Assert;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ClientServerIntegrationTest {
    @Test
    public void testClientServerInteraction() throws InterruptedException, ExecutionException {
        ExecutorService clientExecutor = Executors.newFixedThreadPool(5);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(5);
        Client client = new Client(clientExecutor);
        Server server = new Server(serverExecutor);

        List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            requests.add(new Request(i));
        }
        client.sendRequests(requests, server);

        List<Response> responses = client.getResponses();
        Assert.assertEquals("Количество ответов = количеству запросов", requests.size(), responses.size());

        for (int i = 0; i < responses.size(); i++) {
            Assert.assertEquals("Значение ответа должно быть корректным", 100 - requests.get(i).value, responses.get(i).value);
        }

        clientExecutor.shutdown();
        serverExecutor.shutdown();
        if (!clientExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            clientExecutor.shutdownNow();
        }
        if (!serverExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            serverExecutor.shutdownNow();
        }
    }
}