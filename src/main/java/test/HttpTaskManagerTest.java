package test;

import client.KVTaskClient;
import manager.HttpTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer server;
    KVTaskClient client;
    HttpTaskManager taskManager;

    @BeforeEach
    public void beforeEachHttpManager() throws IOException, URISyntaxException, InterruptedException {
        taskManager = new HttpTaskManager("http://localhost:8078");
        server = new KVServer();
        server.start();



    }

    @AfterEach
    public void afterEachHttpManager() {
        server.stop();
    }

    @Test
    public void dfasdfsdvf() {
        Task task1 = new Task(
                "task1",
                0, "убраться",
                Status.NEW
        );
        taskManager.addTask(task1);
    }

}
