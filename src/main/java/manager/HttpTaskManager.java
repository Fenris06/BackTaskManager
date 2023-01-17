package manager;

import client.KVTaskClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
        Map<Integer, Task> tempTasks = gson.fromJson(client.load("tasks"), new TypeToken<>() {
        });

        tasks.putAll(Optional.ofNullable(tempTasks).orElse(new HashMap<>()));

        Map<Integer, Epic> tempEpics = gson.fromJson(client.load("epics"), new TypeToken<>() {
        });

        epics.putAll(Optional.ofNullable(tempEpics).orElse(new HashMap<>()));

        Map<Integer, SubTask> tempSubTask = gson.fromJson(client.load("subTasks"), new TypeToken<>() {
        });

        subTasks.putAll(Optional.ofNullable(tempSubTask).orElse(new HashMap<>()));

        Set<Task> prioriTasks = gson.fromJson(client.load("prioritizedTasks"), new TypeToken<>() {
        });

        prioritizedTasks.addAll(Optional.ofNullable(prioriTasks).orElse(new HashSet<>()));

        List<Task> jsonHistory = gson.fromJson(client.load("history"), new TypeToken<>() {});
        List<Task> tempHistory = Optional.ofNullable(jsonHistory).orElse(new ArrayList<>());

        for (Task task : tempHistory) {
            historyManager.add(task);
        }
    }
}

