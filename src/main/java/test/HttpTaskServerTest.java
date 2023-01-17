package test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.HistoryManager;
import manager.HttpTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;
import tasks.Status;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskServerTest {
    private HttpTaskServer server;
    private HttpTaskManager taskManager;
    private KVServer kvServer;
    private Gson gson = new Gson();

    @BeforeEach
    public void beforeEachHttpManager() throws URISyntaxException, IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        taskManager = new HttpTaskManager("http://localhost:8078");
        server = new HttpTaskServer(taskManager);
        server.start();
    }

    @AfterEach
    public void afterEachHttpManager() {
        server.stop();
        kvServer.stop();
    }

    @Test
    public void shouldHttTaskServerGetTask() throws IOException, InterruptedException {
        Task task1 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:25",
                30);

        taskManager.addTask(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(response.body(), taskType);

        assertNotNull(tasks, "Tasks is null");
        assertEquals(1, tasks.size());
        assertEquals(taskManager.getTaskFromId(0), tasks.get(0) );
    }

    @Test
    public void shouldHttTaskServerGetTaskIfTwoTaskAdd() throws IOException, InterruptedException {
        Task task1 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:25",
                30);

        Task task2 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(response.body(), taskType);

        assertNotNull(tasks, "Tasks is null");
        assertEquals(2, tasks.size());
        assertEquals(taskManager.getTaskFromId(0), tasks.get(0) );
        assertEquals(taskManager.getTaskFromId(1), tasks.get(1) );
    }
}
