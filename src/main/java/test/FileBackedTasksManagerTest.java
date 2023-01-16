package test;

import manager.FileBackedTasksManager;
import org.junit.jupiter.api.Assertions;
import manager.Manager;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    File file;

    @BeforeEach
    public void beforeEachManager() {
        file = new File("test.csv");
        taskManager = new FileBackedTasksManager(file);
    }

    @AfterEach
    public void afterEachManager() {
        file.delete();
    }

    @Test
    public void shouldFileBackTAskManagerReturnTasksIfTaskNotAdd() {
        final List<Task> saveTasks = taskManager.getTasks();
        final List<Epic> saveEpics = taskManager.getEpics();
        final List<SubTask> saveSubTasks = taskManager.getSubTask();
        final List<Task> saveHistory = taskManager.getHistory();

        FileBackedTasksManager loadTaskManager = FileBackedTasksManager.loadFromFile(file);

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
    public void shouldFileBackTaskManagerReturnTasksIfTaskAddWithoutHistory() {
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

        FileBackedTasksManager loadTaskManager = FileBackedTasksManager.loadFromFile(file);

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
        assertEquals(loadTasks.get(0), task1,"Tasks not same");
        assertEquals(1, loadTasks.size(), "Task size not one");
        assertEquals(loadEpics, saveEpics, "Tasks not same");
        assertEquals(loadEpics.get(0), epic1,"Tasks not same");
        assertEquals(1, loadEpics.size(), "Task size not one");
    }

    @Test
    public void shouldFileBackTaskManagerReturnTasksIfTaskAddWithHistory() {
        Task task1 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:25",
                30
        );
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.getTaskFromId(0);
        taskManager.getEpicFromId(1);

        final List<Task> saveTasks = taskManager.getTasks();
        final List<Epic> saveEpics = taskManager.getEpics();
        final List<Task> saveHistory = taskManager.getHistory();

        FileBackedTasksManager loadTaskManager = FileBackedTasksManager.loadFromFile(file);

        final List<Task> loadHistory = loadTaskManager.getHistory();

        assertNotNull(loadHistory, "Task is not return");
        assertEquals(loadHistory, saveHistory, "Tasks not same");
        assertEquals(loadHistory.get(0), task1, "Tasks not same");
        assertEquals(loadHistory.get(1), epic1 , "Tasks not same");
        assertEquals(2, loadHistory.size(), "Task size not two");

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
        assertEquals(loadTasks.get(0), task1,"Tasks not same");
        assertEquals(1, loadTasks.size(), "Task size not one");
        assertEquals(loadEpics, saveEpics, "Tasks not same");
        assertEquals(loadEpics.get(0), epic1,"Tasks not same");
        assertEquals(1, loadEpics.size(), "Task size not one");
    }

    @Test
    public void shouldFileBackTaskManagerReturnTasksIfTaskAddAndEpicAddSubtaskWithHistory() {
        Task task1 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:25",
                30);
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                1
        );

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.getTaskFromId(0);
        taskManager.getEpicFromId(1);
        taskManager.getSubTaskId(2);

        final List<Task> saveTasks = taskManager.getTasks();
        final List<Epic> saveEpics = taskManager.getEpics();
        final List<SubTask> saveSubTasks = taskManager.getSubTask();
        final List<Task> saveHistory = taskManager.getHistory();

        FileBackedTasksManager loadTaskManager = FileBackedTasksManager.loadFromFile(file);

        final List<Task> loadHistory = loadTaskManager.getHistory();

        assertNotNull(loadHistory, "Task is not return");
        assertEquals(loadHistory, saveHistory, "Tasks not same");
        assertEquals(loadHistory.get(0), task1, "Tasks not same");
        assertEquals(loadHistory.get(1), epic1 , "Tasks not same");
        assertEquals(loadHistory.get(2), subTask1, "Tasks not same");
        assertEquals(3, loadHistory.size(), "Task size not two");

        final Task testTask = loadTaskManager.getTaskFromId(0);
        final Epic testEpic = loadTaskManager.getEpicFromId(1);
        final SubTask testSubtask = loadTaskManager.getSubTaskId(2);
        final List<Task> loadTasks = loadTaskManager.getTasks();
        final List<Epic> loadEpics = loadTaskManager.getEpics();
        final List<SubTask> loadSubtask = loadTaskManager.getSubTask();

        assertNotNull(testTask, "Task is not return");
        assertNotNull(testEpic, "Task is not return");
        assertNotNull(testSubtask, "Task is not return");
        assertNotNull(loadTasks, "Task is not return");
        assertNotNull(loadEpics, "Task is not return");
        assertNotNull(loadSubtask, "Task is not return");
        assertEquals(testTask, task1, "Tasks not same");
        assertEquals(testEpic, epic1, "Tasks not same");
        assertEquals(testSubtask, subTask1, "Tasks not same");
        assertEquals(loadTasks, saveTasks, "Tasks not same");
        assertEquals(loadTasks.get(0), task1,"Tasks not same");
        assertEquals(1, loadTasks.size(), "Task size not one");
        assertEquals(loadEpics, saveEpics, "Tasks not same");
        assertEquals(loadEpics.get(0), epic1,"Tasks not same");
        assertEquals(1, loadEpics.size(), "Task size not one");
        assertEquals(loadSubtask, saveSubTasks, "Tasks not same");
        assertEquals(loadSubtask.get(0), subTask1,"Tasks not same");
        assertEquals(1, loadSubtask.size(),"Task size not one");
    }

    @Test
    public void shouldFileBackTaskManagerReturnTasksIfTwoTaskAddAndTwoEpicAddSubtaskWithHistory() {
        Task task1 = new Task(
                "Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:25",
                30);
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30);
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T11:25",
                30,
                2
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T12:25",
                30,
                4
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);
        taskManager.getTaskFromId(0);
        taskManager.getTaskFromId(1);
        taskManager.getEpicFromId(2);
        taskManager.getEpicFromId(4);
        taskManager.getSubTaskId(3);
        taskManager.getSubTaskId(5);

        final List<Task> saveTasks = taskManager.getTasks();
        final List<Epic> saveEpics = taskManager.getEpics();
        final List<SubTask> saveSubTasks = taskManager.getSubTask();
        final List<Task> saveHistory = taskManager.getHistory();

        FileBackedTasksManager loadTaskManager = FileBackedTasksManager.loadFromFile(file);

        final List<Task> loadHistory = loadTaskManager.getHistory();

        assertNotNull(loadHistory, "Task is not return");
        assertEquals(loadHistory, saveHistory, "Tasks not same");
        assertEquals(loadHistory.get(0), task1, "Tasks not same");
        assertEquals(loadHistory.get(1), task2 , "Tasks not same");
        assertEquals(loadHistory.get(2), epic1, "Tasks not same");
        assertEquals(loadHistory.get(3), epic2, "Tasks not same");
        assertEquals(loadHistory.get(4), subTask1, "Tasks not same");
        assertEquals(loadHistory.get(5), subTask2, "Tasks not same");
        assertEquals(6, loadHistory.size(), "Task size not two");

        final Task testTask1 = loadTaskManager.getTaskFromId(0);
        final Task testTask2 = loadTaskManager.getTaskFromId(1);
        final Epic testEpic1 = loadTaskManager.getEpicFromId(2);
        final Epic testEpic2 = loadTaskManager.getEpicFromId(4);
        final SubTask testSubtask1 = loadTaskManager.getSubTaskId(3);
        final SubTask testSubtask2 = loadTaskManager.getSubTaskId(5);
        final List<Task> loadTasks = loadTaskManager.getTasks();
        final List<Epic> loadEpics = loadTaskManager.getEpics();
        final List<SubTask> loadSubtask = loadTaskManager.getSubTask();

        assertNotNull(testTask1, "Task is not return");
        assertNotNull(testTask2, "Task is not return");
        assertNotNull(testEpic1, "Task is not return");
        assertNotNull(testEpic2, "Task is not return");
        assertNotNull(testSubtask1, "Task is not return");
        assertNotNull(testSubtask2, "Task is not return");
        assertEquals(testTask1, task1, "Tasks not same");
        assertEquals(testTask2, task2, "Tasks not same");
        assertEquals(testEpic1, epic1, "Tasks not same");
        assertEquals(testEpic2, epic2, "Tasks not same");
        assertEquals(testSubtask1, subTask1, "Tasks not same");
        assertEquals(testSubtask2, subTask2, "Tasks not same");
        assertEquals(loadTasks, saveTasks, "Tasks not same");
        assertEquals(loadTasks.get(0), task1, "Tasks not same");
        assertEquals(loadTasks.get(1), task2, "Tasks not same");
        assertEquals(2, loadTasks.size(), "Task size not two");
        assertEquals(loadEpics, saveEpics, "Tasks not same");
        assertEquals(loadEpics.get(0), epic1,"Tasks not same");
        assertEquals(loadEpics.get(1), epic2,"Tasks not same");
        assertEquals(2, loadEpics.size(), "Task size not two");
        assertEquals(loadSubtask, saveSubTasks, "Tasks not same");
        assertEquals(loadSubtask.get(0), subTask1,"Tasks not same");
        assertEquals(loadSubtask.get(1), subTask2,"Tasks not same");
        assertEquals(2, loadSubtask.size(), "Task size not two");
    }


}

