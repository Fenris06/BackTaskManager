package test;
import client.KVTaskClient;
import manager.FileBackedTasksManager;
import manager.HttpTaskManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.*;
import server.KVServer;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {
    KVServer server;

    @BeforeEach
    public void beforeEachHttpManager() throws IOException, URISyntaxException, InterruptedException {
        server = new KVServer();
        server.start();
        taskManager = new HttpTaskManager("http://localhost:8078");
    }

    @AfterEach
    public void afterEachHttpManager() {
        server.stop();
    }

    @Test
    public void shouldHttpTAskManagerReturnTasksIfTaskNotAdd() throws URISyntaxException, IOException, InterruptedException {
        final List<Task> saveTasks = taskManager.getTasks();
        final List<Epic> saveEpics = taskManager.getEpics();
        final List<SubTask> saveSubTasks = taskManager.getSubTask();
        final List<Task> saveHistory = taskManager.getHistory();

        HttpTaskManager loadTaskManager = new HttpTaskManager("http://localhost:8078");
        loadTaskManager.load();
        final List<Task> loadTasks = loadTaskManager.getTasks();
        final List<Epic> loadEpics = loadTaskManager.getEpics();
        final List<SubTask> loadSubTasks = loadTaskManager.getSubTask();
        final List<Task> loadHistory = loadTaskManager.getHistory();

        assertEquals(loadTasks, saveTasks, "Tasks not same");
        assertEquals(0, loadTasks.size(), "Task size not null");
        assertEquals(loadEpics, saveEpics, "Tasks not same");
        assertEquals(0, loadEpics.size(), "Task size not null");
        assertEquals(loadSubTasks, saveSubTasks, "Tasks not same");
        assertEquals(0, loadSubTasks.size(), "Task size not null");
        assertEquals(loadHistory, saveHistory, "Tasks not same");
        assertEquals(0, loadHistory.size(), "Task size not null");
    }

    @Test
    public void shouldHttpTaskManagerReturnTasksIfTaskAddWithoutHistory() throws
            URISyntaxException,
            IOException,
            InterruptedException {
        Task task1 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:25",
                30);
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);

        final List<Task> saveTasks = taskManager.getTasks();
        final List<Epic> saveEpics = taskManager.getEpics();
        final List<Task> saveHistory = taskManager.getHistory();

        HttpTaskManager loadTaskManager = new HttpTaskManager("http://localhost:8078");
        loadTaskManager.load();

        final List<Task> loadHistory = loadTaskManager.getHistory();

        assertEquals(loadHistory, saveHistory, "Tasks not same");
        assertEquals(0, loadHistory.size(), "Task size not null");

        final Task testTask = loadTaskManager.getTaskFromId(0);
        final Epic testEpic = loadTaskManager.getEpicFromId(1);
        final List<Task> loadTasks = loadTaskManager.getTasks();
        final List<Epic> loadEpics = loadTaskManager.getEpics();

        assertNotNull(testTask, "Task is not return");
        assertNotNull(testEpic, "Task is not return");
        assertNotNull(loadTasks, "Task is not return");
        assertNotNull(loadEpics, "Task is not return");
        assertEquals(testTask, task1, "Tasks not same");
        assertEquals(testEpic, epic1, "Tasks not same");
        assertEquals(loadTasks, saveTasks, "Tasks not same");
        assertEquals(loadTasks.get(0), task1, "Tasks not same");
        assertEquals(1, loadTasks.size(), "Task size not one");
        assertEquals(loadEpics, saveEpics, "Tasks not same");
        assertEquals(loadEpics.get(0), epic1, "Tasks not same");
        assertEquals(1, loadEpics.size(), "Task size not one");
    }

}
