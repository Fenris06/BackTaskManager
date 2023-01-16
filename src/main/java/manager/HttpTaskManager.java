package manager;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient client;
    Gson gson = new Gson();

    public HttpTaskManager(String url) throws URISyntaxException, IOException, InterruptedException {
        this.client = new KVTaskClient(url);
    }

    @Override
    public void save() {
        try {
            String tasksJson = gson.toJson(new ArrayList<>(tasks.values()));

            client.put("tasks", tasksJson);

            String epicJson = gson.toJson(new ArrayList<>(epics.values()));

            client.put("epics", epicJson);

            String subTasksJson = gson.toJson(new ArrayList<>(subTasks.values()));

            client.put("subTasks", epicJson);

            String historyJson = gson.toJson(getHistory());

            client.put("history", epicJson);

            String prioritizedTasksJson = gson.toJson(getPrioritizedTasks());

            client.put("prioritizedTasks", prioritizedTasksJson);
        } catch (URISyntaxException | IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void load() throws URISyntaxException, IOException, InterruptedException {
        tasks.putAll(gson.fromJson(client.load("tasks"), new TypeToken<>() {
        }));
        epics.putAll(gson.fromJson(client.load("epics"), new TypeToken<>() {
        }));
        subTasks.putAll(gson.fromJson(client.load("subTasks"), new TypeToken<>() {
        }));
        prioritizedTasks.addAll(gson.fromJson(client.load("prioritizedTasks"), new TypeToken<>() {
        }));
        List<Task> jsonHistory = gson.fromJson(client.load("history"), new TypeToken<>() {});
        for (Task task : jsonHistory) {
            historyManager.add(task);
        }
    }
}

