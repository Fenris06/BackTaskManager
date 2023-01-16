package test;

import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager;
    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void shouldHistoryManagerReturnHistoryIfTaskNotAdd() {
        final List<Task> history = historyManager.getHistory();

        assertEquals(0, history.size(), "History size not one");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryIfOneTaskAdd() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);

        historyManager.add(task1);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history.get(0), "Task is null");
        assertEquals(history.get(0), task1, "Task not same");
        assertEquals(1, history.size(), "History size not one");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryIfTwoTaskAddAndEpicAdd() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history.get(0), "Task is null");
        assertNotNull(history.get(1), "Task is null");
        assertNotNull(history.get(2), "Task is null");
        assertEquals(history.get(0), task1, "Task not same");
        assertEquals(history.get(1), task2, "Task not same");
        assertEquals(history.get(2), epic1, "Task not same");
        assertEquals(3, history.size(), "History size not one");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryIfTwoTaskAddAndEpicAddWithSubTask() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                3, "Собрать вещи",
                Status.DONE,
                2
        );

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        historyManager.add(subTask1);

        final List<Task> history = historyManager.getHistory();

        assertNotNull(history.get(0), "Task is null");
        assertNotNull(history.get(1), "Task is null");
        assertNotNull(history.get(2), "Task is null");
        assertEquals(history.get(0), task1, "Task not same");
        assertEquals(history.get(1), task2, "Task not same");
        assertEquals(history.get(2), epic1, "Task not same");
        assertEquals(history.get(3), subTask1, "Task not same");
        assertEquals(4, history.size(), "History size not one");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryWithAddDoubleTaskLast() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                3, "Собрать вещи",
                Status.DONE,
                2
        );

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        historyManager.add(subTask1);

        final List<Task> history = historyManager.getHistory();

        Task task3 = new Task("Task2", 1, "собрать коробки", Status.DONE);

        historyManager.add(task3);

        final List<Task> doubleHistory = historyManager.getHistory();

        assertNotNull(doubleHistory.get(0), "Task is null");
        assertNotNull(doubleHistory.get(1), "Task is null");
        assertNotNull(doubleHistory.get(2), "Task is null");
        assertNotNull(doubleHistory.get(3), "Task is null");
        assertNotNull(history, "Task is null");
        assertNotNull(doubleHistory, "Task is null");
        assertNotEquals(doubleHistory, history, "Task is same");
        assertEquals(doubleHistory.size(), history.size(), "Task not same");
        assertEquals(doubleHistory.get(0), task1, "Task not same");
        assertEquals(doubleHistory.get(1), epic1, "Task not same");
        assertEquals(doubleHistory.get(2), subTask1, "Task not same");
        assertEquals(doubleHistory.get(3), task2, "Task not same");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryWithAddTwoDoubleTaskLast() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                3, "Собрать вещи",
                Status.DONE,
                2
        );

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        historyManager.add(subTask1);

        final List<Task> history = historyManager.getHistory();

        Task task3 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic2 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);

        historyManager.add(task3);
        historyManager.add(epic2);

        final List<Task> doubleHistory = historyManager.getHistory();

        assertNotNull(doubleHistory.get(0), "Task is null");
        assertNotNull(doubleHistory.get(1), "Task is null");
        assertNotNull(doubleHistory.get(2), "Task is null");
        assertNotNull(doubleHistory.get(3), "Task is null");
        assertNotNull(history, "Task is null");
        assertNotNull(doubleHistory, "Task is null");
        assertNotEquals(doubleHistory, history, "Task is same");
        assertEquals(doubleHistory.size(), history.size(), "Task not same");
        assertEquals(doubleHistory.get(0), task1, "Task not same");
        assertEquals(doubleHistory.get(1), subTask1, "Task not same");
        assertEquals(doubleHistory.get(2), task2, "Task not same");
        assertEquals(doubleHistory.get(3), epic1, "Task not same");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryWhenFirstTaskDeleteAndAddNewTaskAdd() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                3, "Собрать вещи",
                Status.DONE,
                2
        );

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        historyManager.add(subTask1);

        final List<Task> history = historyManager.getHistory();

        historyManager.remove(0);

        Task task3 = new Task("Task3", 4, "собрать коробки", Status.IN_PROGRESS);

        historyManager.add(task3);

        final List<Task> removeHistory = historyManager.getHistory();

        assertNotNull(removeHistory.get(0), "Task is null");
        assertNotNull(removeHistory.get(1), "Task is null");
        assertNotNull(removeHistory.get(2), "Task is null");
        assertNotNull(removeHistory.get(3), "Task is null");
        assertNotNull(history, "Task is null");
        assertNotNull(removeHistory, "Task is null");
        assertNotEquals(removeHistory, history, "Task is same");
        assertEquals(removeHistory.get(0), task2, "Task not same");
        assertEquals(removeHistory.get(1), epic1, "Task not same");
        assertEquals(removeHistory.get(2), subTask1, "Task not same");
        assertEquals(removeHistory.get(3), task3, "Task not same");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryWhenAverageTaskDeleteAndAddNewTaskAdd() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                3, "Собрать вещи",
                Status.DONE,
                2
        );

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        historyManager.add(subTask1);

        final List<Task> history = historyManager.getHistory();

        historyManager.remove(2);

        Task task3 = new Task("Task3", 4, "собрать коробки", Status.IN_PROGRESS);

        historyManager.add(task3);

        final List<Task> removeHistory = historyManager.getHistory();

        assertNotNull(removeHistory.get(0), "Task is null");
        assertNotNull(removeHistory.get(1), "Task is null");
        assertNotNull(removeHistory.get(2), "Task is null");
        assertNotNull(removeHistory.get(3), "Task is null");
        assertNotNull(history, "Task is null");
        assertNotNull(removeHistory, "Task is null");
        assertNotEquals(removeHistory, history, "Task is same");
        assertEquals(removeHistory.get(0), task1, "Task not same");
        assertEquals(removeHistory.get(1), task2, "Task not same");
        assertEquals(removeHistory.get(2), subTask1, "Task not same");
        assertEquals(removeHistory.get(3), task3, "Task not same");
    }

    @Test
    public void shouldHistoryManagerReturnHistoryWhenLastTaskDeleteAndAddNewTaskAdd() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);
        Task task2 = new Task("Task2", 1, "собрать коробки", Status.DONE);
        Epic epic1 = new Epic("Epic1", 2, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                3, "Собрать вещи",
                Status.DONE,
                2
        );

        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(epic1);
        historyManager.add(subTask1);

        final List<Task> history = historyManager.getHistory();

        historyManager.remove(3);

        Task task3 = new Task("Task3", 4, "собрать коробки", Status.IN_PROGRESS);

        historyManager.add(task3);

        final List<Task> removeHistory = historyManager.getHistory();

        assertNotNull(removeHistory.get(0), "Task is null");
        assertNotNull(removeHistory.get(1), "Task is null");
        assertNotNull(removeHistory.get(2), "Task is null");
        assertNotNull(removeHistory.get(3), "Task is null");
        assertNotNull(history, "Task is null");
        assertNotNull(removeHistory, "Task is null");
        assertNotEquals(removeHistory, history, "Task is same");
        assertEquals(removeHistory.get(0), task1, "Task not same");
        assertEquals(removeHistory.get(1), task2, "Task not same");
        assertEquals(removeHistory.get(2), epic1, "Task not same");
        assertEquals(removeHistory.get(3), task3, "Task not same");
    }
}

