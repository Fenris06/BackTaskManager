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
        if (client.load("tasks") != null) {
            Map<Integer, Task> tempTasks = gson.fromJson(client.load("tasks"), new TypeToken<>() {
            });

            tasks.putAll(Optional.ofNullable(tempTasks).orElse(new HashMap<>()));
        }

        if (client.load("epics") != null) {
            Map<Integer, Epic> tempEpics = gson.fromJson(client.load("epics"), new TypeToken<>() {
            });

            epics.putAll(Optional.ofNullable(tempEpics).orElse(new HashMap<>()));
        }

        if (client.load("subTasks") != null) {
            Map<Integer, SubTask> tempSubTask = gson.fromJson(client.load("subTasks"), new TypeToken<>() {
            });

            subTasks.putAll(Optional.ofNullable(tempSubTask).orElse(new HashMap<>()));
        }

        if (client.load("prioritizedTasks") != null) {
            Set<Task> prioriTasks = gson.fromJson(client.load("prioritizedTasks"), new TypeToken<>() {
            });

            prioritizedTasks.addAll(Optional.ofNullable(prioriTasks).orElse(new HashSet<>()));
        }

        if (client.load("history") != null) {
            List<Task> jsonHistory = gson.fromJson(client.load("history"), new TypeToken<>() {
            });
            List<Task> tempHistory = Optional.ofNullable(jsonHistory).orElse(new ArrayList<>());

            for (Task task : tempHistory) {
                historyManager.add(task);
            }
        }
    }
}

