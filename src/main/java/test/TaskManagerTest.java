package test;

import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    @Test
    public void shouldManagerAddNewTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addTask(task1);
        final Task testTask = taskManager.getTaskFromId(task1.getId());

        assertNotNull(testTask, "Task not back");
        assertEquals(testTask, task1);

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Tasks not back");
        assertEquals(1, tasks.size(), "Wrong task quantity");
        assertEquals(task1, tasks.get(0));
    }

    @Test
    public void shouldManagerAddNextNewTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );


        taskManager.addTask(task1);
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );
        taskManager.addTask(task2);

        final Task testTask1 = taskManager.getTaskFromId(task1.getId());
        final Task testTask2 = taskManager.getTaskFromId(task2.getId());

        assertNotNull(testTask1, "Task not back");
        assertNotNull(testTask2, "Task not back");
        assertEquals(testTask1, task1);
        assertEquals(testTask2, task2);

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Tasks not back");
        assertEquals(2, tasks.size(), "Wrong task quantity");
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));

    }

    @Test
    public void shouldManagerGetListTaskWhenNullTaskAdd() {
        final List<Task> tasks = taskManager.getTasks();

        assertEquals(0, tasks.size(), "Wrong task quantity");
    }

    @Test
    public void shouldManagerGetListTaskWhenOneTaskAdd() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addTask(task1);

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Tasks not back");
        assertEquals(1, tasks.size(), "Wrong task quantity");
        assertEquals(task1, tasks.get(0));
    }

    @Test
    public void shouldManagerGetListTaskWhenTwoTaskAdd() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Tasks not back");
        assertEquals(2, tasks.size(), "Wrong task quantity");
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));

    }

    @Test
    public void shouldManagerRemoveAllTaskWhenAddOneTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addTask(task1);
        taskManager.clearAllTAsks();

        final Task testTask = taskManager.getTaskFromId(0);

        assertNull(testTask, "Task not null");

        final List<Task> tasks = taskManager.getTasks();

        assertEquals(0, tasks.size(), "All tasks not delete");

    }

    @Test
    public void shouldManagerRemoveAllTaskWhenAddTwoTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.clearAllTAsks();

        final Task testTask1 = taskManager.getTaskFromId(0);
        final Task testTask2 = taskManager.getTaskFromId(1);

        assertNull(testTask1, "Task not null");
        assertNull(testTask2, "Task not null");

        final List<Task> tasks = taskManager.getTasks();

        assertEquals(0, tasks.size(), "All tasks not delete");

    }

    @Test
    public void shouldManagerReturnNullWhenTaskGetTaskFromIdIfNullTaskAdd() {
        final Task testTask = taskManager.getTaskFromId(0);

        assertNull(testTask, "Not return null");
    }

    @Test
    public void shouldManagerReturnTaskWhenTaskGetTaskFromIdIfOneTaskAdd() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);

        taskManager.addTask(task1);

        final Task testTask = taskManager.getTaskFromId(0);

        assertNotNull(testTask, "Task not back");
        assertEquals(testTask, taskManager.getTaskFromId(0), "Wrong task quantity");
    }

    @Test
    public void shouldManagerReturnTaskWhenTaskGetTaskFromIdIfTwoTaskAdd() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        final Task testTask1 = taskManager.getTaskFromId(0);
        final Task testTask2 = taskManager.getTaskFromId(1);

        assertNotNull(testTask1, "Task not back");
        assertNotNull(testTask2, "Task not back");
        assertEquals(testTask1, taskManager.getTaskFromId(0), "Wrong task quantity");
        assertEquals(testTask2, taskManager.getTaskFromId(1), "Wrong task quantity");
    }

    @Test
    public void shouldNotManagerUpdateTaskWhenTaskIdNotSame() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);

        taskManager.addTask(task1);

        Task task2 = new Task("Task2",
                1,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );

        taskManager.updateTask(task2);

        final Task testTask = taskManager.getTaskFromId(0);

        assertNotNull(testTask, "Task not back");
        assertNotEquals(testTask, task2, "Tasks is same");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size(), "All tasks not delete");
    }

    @Test
    public void shouldManagerUpdateTaskWhenTaskIdIsSame() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);

        taskManager.addTask(task1);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );

        taskManager.updateTask(task2);

        final Task testTask = taskManager.getTaskFromId(0);

        assertEquals(testTask, task2, "Tasks is not same");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(1, tasks.size(), "All tasks not delete");
    }

    @Test
    public void shouldManagerUpdateTaskWhenTaskIdIsSameIfUpdateTwoTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Task task3 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T07:25",
                30
        );
        Task task4 = new Task("Task2",
                1,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T11:25",
                30
        );

        taskManager.updateTask(task3);
        taskManager.updateTask(task4);

        final Task testTask1 = taskManager.getTaskFromId(0);
        final Task testTask2 = taskManager.getTaskFromId(1);

        assertNotNull(testTask1);
        assertNotNull(testTask2);
        assertEquals(testTask1, task3, "Tasks is not same");
        assertEquals(testTask2, task4, "Tasks is not same");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size(), "All tasks not delete");
    }

    @Test
    public void shouldManagerDeleteTaskWhenAddOneTask() {
        Task task1 = new Task("Task1", 0, "собрать коробки", Status.NEW);

        taskManager.addTask(task1);
        taskManager.deleteTask(0);

        final Task testTask = taskManager.getTaskFromId(0);

        assertNull(testTask, " Task not null");

        final List<Task> tasks = taskManager.getTasks();

        assertEquals(0, tasks.size(), "Tasks.size not null");
    }

    @Test
    public void shouldManagerDeleteTaskWhenAddTwoTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.deleteTask(0);

        final Task testTask1 = taskManager.getTaskFromId(0);
        final Task testTask2 = taskManager.getTaskFromId(1);

        assertNull(testTask1, " Task not null");
        assertEquals(testTask2, task2, "Tasks not same");
        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size(), "Tasks.size not one");
    }

    @Test
    public void shouldManagerAddOneEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addEpic(epic1);

        final Epic testEpic = taskManager.getEpicFromId(0);

        assertNotNull(testEpic, "Epic not null");
        assertEquals(testEpic, epic1, "Epic is same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(1, epics.size(), "Epics size not one");
        assertEquals(epic1, epics.get(0), "Epic not same");
    }

    @Test
    public void shouldManagerAddTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);

        assertNotNull(testEpic1, "Epic not null");
        assertNotNull(testEpic2, "Epic not null");
        assertEquals(testEpic1, epic1, "Epic is same");
        assertEquals(testEpic2, epic2, "Epic is same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(2, epics.size(), "Epics size not one");
        assertEquals(epic1, epics.get(0), "Epic not same");
        assertEquals(epic2, epics.get(1), "Epic not same");
    }

    @Test
    public void shouldManagerAddOneEpicWithOneSubtask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask = taskManager.getSubTaskId(1);

        assertNotNull(testEpic, "Epic not null");
        assertNotNull(testSubtask, "Epic not null");
        assertEquals(testEpic, epic1, "Epic not same");
        assertEquals(testSubtask, subTask1, "Epic not same");
        assertEquals(testEpic.getId(), testSubtask.getEpicId(), "Id is not same");
        assertEquals(testEpic.getStatus(), Status.DONE, "Status is not same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(1, epics.size(), "Epics size not one");
    }

    @Test
    public void shouldManagerAddTwoEpicWithTwoSubtask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(2);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNotNull(testEpic1, "Epic not null");
        assertNotNull(testEpic2, "Epic not null");
        assertNotNull(testSubtask1, "Subtask not null");
        assertNotNull(testSubtask2, "Subtask not null");
        assertEquals(testEpic1, epic1, "Epic not same");
        assertEquals(testEpic2, epic2, "Epic not same");
        assertEquals(testSubtask1, subTask1, "Subtask not same");
        assertEquals(testSubtask2, subTask2, "Subtask not same");
        assertEquals(testEpic1.getId(), testSubtask1.getEpicId(), "Id is not same");
        assertEquals(testEpic2.getId(), testSubtask2.getEpicId(), "Id is not same");
        assertEquals(testEpic1.getStatus(), Status.DONE, "Status is not same");
        assertEquals(testEpic2.getStatus(), Status.IN_PROGRESS, "Status is not same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(2, epics.size(), "Epics size not two");
    }

    @Test
    public void shouldManagerListEpicsSizeAreNullIfEpicNotAdd() {
        final List<Epic> epics = taskManager.getEpics();

        assertEquals(0, epics.size(), "Epics size not two");

    }

    @Test
    public void shouldManagerReturnOneEpicIfOneEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        taskManager.addEpic(epic1);

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(epic1, epics.get(0));
        assertEquals(1, epics.size(), "Epics size not two");
    }

    @Test
    public void shouldManagerReturnTwoEpicIfTwoEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(epic1, epics.get(0), "Epic is not same");
        assertEquals(epic2, epics.get(1), "Epic is not same");
        assertNotNull(epics, "Epics not return");
        assertEquals(2, epics.size(), "Epics size not two");
    }

    @Test
    public void shouldManagerRemoveAllEpicIfOneEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        taskManager.addEpic(epic1);
        taskManager.clearAllEpics();

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        assertNull(testEpic1, "Epic not null");

        final List<Epic> epics = taskManager.getEpics();

        assertEquals(0, epics.size(), "Epics size not null");
    }

    @Test
    public void shouldManagerRemoveAllEpicIfTwoEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.clearAllEpics();

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);

        assertNull(testEpic1, "Epic not null");
        assertNull(testEpic2, "Epic not null");

        final List<Epic> epics = taskManager.getEpics();

        assertEquals(0, epics.size(), "Epics size not null");
    }

    @Test
    public void shouldNotManagerGetEpicFromIdIfEpicNotAdd() {
        final Epic testEpic1 = taskManager.getEpicFromId(0);

        assertNull(testEpic1, "Epic not null");
    }

    @Test
    public void shouldManagerGetEpicFromIdIfOneEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addEpic(epic1);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        assertNotNull(testEpic1, "Epic null");
        assertEquals(testEpic1, epic1, "Epic is same");
    }

    @Test
    public void shouldManagerGetEpicFromIdIfTwoEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        assertNotNull(testEpic1, "Epic null");
        assertEquals(testEpic1, epic1, "Epic is same");
        assertNotEquals(testEpic1, epic2, "Epic is not same");
    }

    @Test
    public void shouldManagerDeleteEpicFromIdIfOneEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addEpic(epic1);
        taskManager.deleteEpic(0);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        assertNull(testEpic1, "Epic not null");

        final List<Epic> epics = taskManager.getEpics();

        assertEquals(0, epics.size(), "Epics size not null");
    }

    @Test
    public void shouldManagerDeleteEpicFromIdIfTwoEpicAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.deleteEpic(0);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);

        assertNull(testEpic1, "Epic not null");
        assertNotNull(testEpic2, "Epic null");

        final List<Epic> epics = taskManager.getEpics();

        assertEquals(1, epics.size(), "Epics size not null");
    }

    @Test
    public void shouldManagerDeleteEpicFromIdAndDeleteEpicSubTaskIfTwoEpicAddOneSubTaskAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask1);
        taskManager.deleteEpic(1);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);
        final SubTask testSubtask1 = taskManager.getSubTaskId(2);

        assertNotNull(testEpic1, "Epic is null");
        assertNull(testEpic2, "Epic not null");
        assertNull(testSubtask1, "Epic not null");

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTask();

        assertEquals(1, epics.size(), "Epics size not one");
        assertEquals(0, subTasks.size(), "SubTasks size not zero");
    }

    @Test
    public void shouldManagerDeleteEpicFromIdAndDeleteEpicSubTaskIfTwoEpicAddTwoSubTaskAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.IN_PROGRESS,
                1
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.deleteEpic(0);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);
        final SubTask testSubtask1 = taskManager.getSubTaskId(2);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNull(testEpic1, "Epic not null");
        assertNull(testSubtask1, "SubTask not null");
        assertNotNull(testEpic2, "Epic is null");
        assertNotNull(testSubtask2, "SubTask is null");

        final List<Epic> epics = taskManager.getEpics();
        final List<SubTask> subTasks = taskManager.getSubTask();

        assertEquals(1, epics.size(), "Epics size not one");
        assertEquals(1, subTasks.size(), "SubTasks size not one");
    }

    @Test
    public void shouldManagerUpdateEpicIfAddOneEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addEpic(epic1);

        Epic epic2 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.DONE,
                "2022-12-21T09:25",
                30
        );

        taskManager.updateEpic(epic2);

        final Epic testEpic = taskManager.getEpicFromId(0);

        assertNotNull(testEpic, "Epic not null");
        assertNotEquals(testEpic, epic1, "Epic is not same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not null");
        assertEquals(1, epics.size(), "Epics size is one");
    }

    @Test
    public void shouldManagerUpdateEpicIfAddTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Epic epic3 = new Epic(
                "Epic1",
                0, "Организовать переезд",
                Status.DONE,
                "2022-12-21T10:25",
                30);

        taskManager.updateEpic(epic3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);

        assertNotNull(testEpic1, "Epic not null");
        assertNotNull(testEpic2, "Epic not null");
        assertNotEquals(testEpic1, epic1, "Epic is not same");
        assertEquals(testEpic2, epic2, "Epic is same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(2, epics.size(), "Epics size not two");
    }

    //
    @Test
    public void shouldManagerUpdateEpicIfAddOneEpicAndOneSubtask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        Epic epic3 = new Epic("Epic3", 0, "Организовать переезд", Status.DONE);

        taskManager.updateEpic(epic3);

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask = taskManager.getSubTaskId(1);

        assertNotNull(testEpic, "Epic not null");
        assertNotNull(testSubtask, "Subtask not null");
        assertNotEquals(testEpic, epic1, "Epic is same");
        assertEquals(testSubtask, subTask1, "Epic not same");
        assertEquals(testEpic.getId(), testSubtask.getEpicId(), "Id is not same");
        assertEquals(testEpic.getStatus(), Status.DONE, "Status is not same");

        final List<Epic> epics = taskManager.getEpics();

        assertNotNull(epics, "Epics not return");
        assertEquals(1, epics.size(), "Epics size not one");

    }

    @Test
    public void shouldManagerUpdateEpicIfAddTwoEpicAndTwoSubtask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic(
                "Учеба",
                0,
                "Выполнить задание",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T13:25",
                30,
                1
        );

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        Epic epic3 = new Epic("Epic3", 0, "Организовать переезд", Status.DONE);

        taskManager.updateEpic(epic3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(1);
        final SubTask testSubtask1 = taskManager.getSubTaskId(2);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNotNull(testEpic1, "Epic is null");
        assertNotNull(testEpic2, "Epic is null");
        assertNotNull(testSubtask1, "Subtask is null");
        assertNotNull(testSubtask2, "Subtask is null");
        assertNotEquals(testEpic1, epic1, "Epic is same");
        assertEquals(testEpic2, epic2, "Epic not same");
        assertEquals(testSubtask1, subTask1, "Subtask not same");
        assertEquals(testSubtask2, subTask2, "Subtask not same");
        assertEquals(testEpic1.getId(), testSubtask1.getEpicId(), "Id is not same");
        assertEquals(testEpic2.getId(), testSubtask2.getEpicId(), "Id is not same");
        assertEquals(testEpic1.getStatus(), Status.DONE, "Status is not same");
        assertEquals(testEpic2.getStatus(), Status.DONE, "Status is not same");

        final List<Epic> epics = taskManager.getEpics();

        assertEquals(2, epics.size(), "Epics size not two");
    }

    @Test
    public void shouldEpicStatusBeNewIfSubTaskStatusNew() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "subTask2",
                0,
                "Упаковать вещи",
                Status.NEW,
                "2022-12-21T09:25",
                30, 0
        );
        SubTask subTask3 = new SubTask(
                "subTask3",
                0,
                "Забронировать машину",
                Status.NEW,
                "2022-12-21T10:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        Assertions.assertEquals(testEpic1.getStatus(), Status.NEW);
    }

    @Test
    public void shouldEpicStatusBeDoneIfSubTaskStatusDone() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "subTask1",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "subTask2",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30, 0
        );
        SubTask subTask3 = new SubTask(
                "subTask3",
                0,
                "Забронировать машину",
                Status.DONE,
                "2022-12-21T10:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        Assertions.assertEquals(testEpic1.getStatus(), Status.DONE);
    }

    @Test
    public void shouldEpicStatusBeInProgressIfSubTaskStatusNewAndDone() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "subTask2",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30, 0
        );
        SubTask subTask3 = new SubTask(
                "subTask3",
                0,
                "Забронировать машину",
                Status.NEW,
                "2022-12-21T10:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        Assertions.assertEquals(testEpic1.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldEpicStatusBeInProgressIfSubTaskStatusInProgress() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "subTask1",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "subTask2",
                0,
                "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30, 0
        );
        SubTask subTask3 = new SubTask(
                "subTask3",
                0,
                "Забронировать машину",
                Status.IN_PROGRESS,
                "2022-12-21T10:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);

        Assertions.assertEquals(testEpic1.getStatus(), Status.IN_PROGRESS);
    }

    @Test
    public void shouldNotManagerAddSubTaskWithoutEpic() {
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                0);

        taskManager.addSubtask(subTask1);

        final SubTask testSubtask = taskManager.getSubTaskId(0);

        assertNull(testSubtask, "Task not back");

        final List<SubTask> tasks = taskManager.getSubTask();

        assertEquals(0, tasks.size(), "Wrong task quantity");
    }

    @Test
    public void shouldManagerAddSubTaskWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "subTask1",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T08:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask = taskManager.getSubTaskId(1);

        assertNotNull(testEpic, "Task not back");
        assertNotNull(testSubtask, "Task not back");
        assertEquals(testSubtask.getEpicId(), testEpic.getId(), "Task id not same");
        assertEquals(testSubtask, subTask1, "Tasks not same");

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(1, subTask.size(), "SubTask size not one");
    }

    @Test
    public void shouldManagerAddTwoSubTaskWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "subTask1",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "subTask2",
                0,
                "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(2);

        assertNotNull(testEpic, "Task not back");
        assertNotNull(testSubtask1, "Task not back");
        assertNotNull(testSubtask2, "Task not back");
        assertEquals(testSubtask1.getEpicId(), testEpic.getId(), "Task id not same");
        assertEquals(testSubtask2.getEpicId(), testEpic.getId(), "Task id not same");
        assertEquals(testSubtask1, subTask1, "Tasks not same");
        assertEquals(testSubtask2, subTask2, "Tasks not same");

        final List<SubTask> subTask = taskManager.getSubTask();

        assertNotNull(subTask, "Task not back");
        assertEquals(2, subTask.size(), "SubTask size not two");
    }

    @Test
    public void shouldManagerAddTwoSubTaskWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(2);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNotNull(testEpic1, "Task not back");
        assertNotNull(testEpic2, "Task not back");
        assertNotNull(testSubtask1, "Task not back");
        assertNotNull(testSubtask2, "Task not back");
        assertEquals(testSubtask1.getEpicId(), testEpic1.getId(), "Task id not same");
        assertEquals(testSubtask2.getEpicId(), testEpic2.getId(), "Task id not same");
        assertEquals(testSubtask1, subTask1, "Tasks not same");
        assertEquals(testSubtask2, subTask2, "Tasks not same");

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(2, subTask.size(), "SubTask size not two");
    }

    @Test
    public void shouldNotManagerGetSubTaskListWithoutEpic() {
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                0);

        taskManager.addSubtask(subTask1);

        final List<SubTask> tasks = taskManager.getSubTask();

        assertEquals(0, tasks.size(), "Wrong task quantity");
    }

    @Test
    public void shouldManagerGetSubTaskListWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final List<SubTask> subTask = taskManager.getSubTask();
        assertNotNull(subTask, "SubTask not null");
        assertEquals(subTask.get(0), subTask1, "Tasks not same");
        assertEquals(subTask.get(0).getEpicId(), epic1.getId(), "Id not same");
        assertEquals(1, subTask.size(), "SubTask size not one");
    }

    @Test
    public void shouldManagerGetTwoSubTaskListWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0);
        SubTask subTask2 = new SubTask(
                "subTask2",
                0, "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T07:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        final List<SubTask> subTask = taskManager.getSubTask();
        assertNotNull(subTask, "SubTask not null");
        assertEquals(subTask.get(0), subTask1, "Tasks not same");
        assertEquals(subTask.get(1), subTask2, "Tasks not same");
        assertEquals(subTask.get(0).getEpicId(), epic1.getId(), "Id not same");
        assertEquals(subTask.get(1).getEpicId(), epic1.getId(), "Id not same");
        assertEquals(2, subTask.size(), "SubTask size not two");
    }

    @Test
    public void shouldManagerGetTwoSubTaskListWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T07:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final List<SubTask> subTask = taskManager.getSubTask();
        assertNotNull(subTask, "SubTask not null");
        assertEquals(subTask.get(0), subTask1, "Tasks not same");
        assertEquals(subTask.get(1), subTask2, "Tasks not same");
        assertEquals(subTask.get(0).getEpicId(), epic1.getId(), "Id not same");
        assertEquals(subTask.get(1).getEpicId(), epic2.getId(), "Id not same");
        assertEquals(2, subTask.size(), "SubTask size not two");
    }

    @Test
    public void shouldManagerClearAllSubTaskWhenOneSubTaskAddWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.clearAllSubTasks();

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask = taskManager.getSubTaskId(1);

        assertNotNull(testEpic, "Task not back");
        assertNull(testSubtask, "Task not null");
        assertEquals(0, testEpic.getSubTaskIds().size(), "Ids size not null");
        assertEquals(testEpic.getStatus(), Status.NEW);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(0, subTask.size(), "SubTask size not null");
    }

    @Test
    public void shouldManagerClearAllSubTaskWhenTwoSubTaskAddWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0);
        SubTask subTask2 = new SubTask(
                "subTask2",
                0, "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.clearAllSubTasks();

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(2);

        assertNotNull(testEpic, "Task not back");
        assertNull(testSubtask1, "Task not null");
        assertNull(testSubtask2, "Task not null");
        assertEquals(0, testEpic.getSubTaskIds().size(), "Ids size not null");
        assertEquals(testEpic.getStatus(), Status.NEW);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(0, subTask.size(), "SubTask size not null");
    }

    @Test
    public void shouldManagerClearAllSubTaskWhenTwoSubTaskAddWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);
        taskManager.clearAllSubTasks();

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(2);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNotNull(testEpic1, "Task not back");
        assertNotNull(testEpic2, "Task not back");
        assertNull(testSubtask1, "Task not null");
        assertNull(testSubtask2, "Task not null");
        assertEquals(0, testEpic1.getSubTaskIds().size(), "Ids size not null");
        assertEquals(0, testEpic2.getSubTaskIds().size(), "Ids size not null");
        assertEquals(testEpic1.getStatus(), Status.NEW);
        assertEquals(testEpic2.getStatus(), Status.NEW);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(0, subTask.size(), "SubTask size not null");
    }

    @Test
    public void shouldManagerDeleteSubTaskWhenOneSubTaskAddWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.deleteSubTask(1);

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask = taskManager.getSubTaskId(1);

        assertNotNull(testEpic, "Task not back");
        assertNull(testSubtask, "Task not null");
        assertEquals(0, testEpic.getSubTaskIds().size(), "Ids size not null");
        assertEquals(testEpic.getStatus(), Status.NEW);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(0, subTask.size(), "SubTask size not null");
    }

    @Test
    public void shouldManagerDeleteSubTaskWhenTwoSubTaskAddWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0);
        SubTask subTask2 = new SubTask(
                "subTask2",
                0, "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.deleteSubTask(1);

        final Epic testEpic = taskManager.getEpicFromId(0);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(2);

        assertNotNull(testEpic, "Task not back");
        assertNull(testSubtask1, "Task not null");
        assertNotNull(testSubtask2, "Task not back");
        assertEquals(testSubtask2.getEpicId(), testEpic.getId(), "id not same");
        assertEquals(1, testEpic.getSubTaskIds().size(), "Ids size not null");
        assertEquals(testEpic.getStatus(), Status.IN_PROGRESS);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(1, subTask.size(), "SubTask size not null");
    }

    @Test
    public void shouldManagerDeleteSubTaskWhenTwoSubTaskAddWithTWoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                1
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);
        taskManager.deleteSubTask(3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(2);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNotNull(testEpic1, "Task not back");
        assertNotNull(testEpic2, "Task not back");
        assertNotNull(testSubtask1, "Task not back");
        assertNull(testSubtask2, "Task not null");
        assertEquals(testSubtask1.getEpicId(), testEpic1.getId(), "id not same");
        assertEquals(1, testEpic1.getSubTaskIds().size(), "Ids size not null");
        assertEquals(0, testEpic2.getSubTaskIds().size(), "Ids size not null");
        assertEquals(testEpic1.getStatus(), Status.DONE);
        assertEquals(testEpic2.getStatus(), Status.NEW);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(1, subTask.size(), "SubTask size not null");
    }

    @Test
    public void shouldManagerReturnSubTaskListWhenSubTaskNotAdd() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);

        taskManager.addEpic(epic1);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(0, subTask.size(), "subTask size not null");
    }

    @Test
    public void shouldManagerReturnSubTaskListWhenOneSubTaskAddWithOneEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final List<SubTask> subTask = taskManager.getSubTask();
        assertEquals(subTask.get(0), subTask1, "Subtask not same");
        assertEquals(1, subTask.size(), "subTask size not null");
    }

    @Test
    public void shouldManagerReturnSubTaskListWhenTwoSubTaskAddWithOneEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "subTask2",
                0, "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(subTask.get(0), subTask1, "Subtask not same");
        assertEquals(subTask.get(1), subTask2, "Subtask not same");
        assertEquals(2, subTask.size(), "subTask size not null");
    }

    @Test
    public void shouldManagerReturnSubTaskListWhenTwoSubTaskAddWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T08:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(subTask.get(0), subTask1, "Subtask not same");
        assertEquals(subTask.get(1), subTask2, "Subtask not same");
        assertEquals(2, subTask.size(), "subTask size not null");
    }

    @Test
    public void shouldManagerUpdateSubTaskWhenOneSubTaskAddWithOneEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        SubTask subTask2 = new SubTask("subTask1",
                1, "Собрать вещи",
                Status.DONE,
                0);

        taskManager.updateSubTask(subTask2);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final SubTask testSubtask = taskManager.getSubTaskId(1);

        assertNotNull(testEpic1, "Task not back");
        assertNotNull(testSubtask, "Task not back");
        assertNotEquals(testSubtask, subTask1, "Task is same");
        assertEquals(testSubtask, subTask2, "Task not same");
        assertEquals(testSubtask.getEpicId(), testEpic1.getId(), "ID not same");
        assertEquals(testEpic1.getStatus(), Status.DONE);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(1, subTask.size(), "subTask size not null");
    }

    @Test
    public void shouldManagerUpdateSubTaskWhenTwoSubTaskAddWithOneEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.DONE,
                "2022-12-21T09:25",
                30,
                0);
        SubTask subTask2 = new SubTask(
                "subTask2",
                0, "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        SubTask subTask3 = new SubTask("subTask1",
                2, "Собрать вещи",
                Status.DONE,
                0);

        taskManager.updateSubTask(subTask3);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(2);

        assertNotNull(testEpic1, "Task not back");
        assertNotNull(testSubtask1, "Task not back");
        assertNotNull(testSubtask2, "Task not back");
        assertEquals(testSubtask1, subTask1, "Task not same");
        assertNotEquals(testSubtask2, subTask2, "Task is same");
        assertEquals(testSubtask2, subTask3, "Task not same");
        assertEquals(testSubtask2.getEpicId(), testEpic1.getId(), "ID not same");
        assertEquals(testEpic1.getStatus(), Status.DONE);

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(2, subTask.size(), "subTask size not two");
    }

    @Test
    public void shouldManagerUpdateSubTaskWhenTwoSubTaskAddWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T09:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.NEW,
                "2022-12-21T11:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        SubTask subTask3 = new SubTask(
                "Подготовка к переезду",
                1, "Собрать вещи",
                Status.DONE,
                "2022-12-21T07:25",
                30,
                0
        );
        SubTask subTask4 = new SubTask(
                "Подготовка к переезду",
                3,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T12:25",
                30,
                2
        );

        taskManager.updateSubTask(subTask3);
        taskManager.updateSubTask(subTask4);

        final Epic testEpic1 = taskManager.getEpicFromId(0);
        final Epic testEpic2 = taskManager.getEpicFromId(2);
        final SubTask testSubtask1 = taskManager.getSubTaskId(1);
        final SubTask testSubtask2 = taskManager.getSubTaskId(3);

        assertNotNull(testEpic1, "Task not back");
        assertNotNull(testEpic2, "Task not back");
        assertNotNull(testSubtask1, "Task not back");
        assertNotNull(testSubtask2, "Task not back");
        assertNotEquals(testSubtask1, subTask1, "Task is same");
        assertNotEquals(testSubtask2, subTask2, "Task is same");
        assertEquals(testSubtask1, subTask3, "Task not same");
        assertEquals(testSubtask2, subTask4, "Task not same");
        assertEquals(testSubtask1.getEpicId(), testEpic1.getId(), "ID not same");
        assertEquals(testSubtask2.getEpicId(), testEpic2.getId(), "ID not same");

        final List<SubTask> subTask = taskManager.getSubTask();

        assertEquals(2, subTask.size(), "subTask size not two");
    }

    @Test
    public void shouldManagerReturnEpicSubTasksListWhenOneSubTaskAddWithEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask("subTask1",
                0, "Собрать вещи",
                Status.NEW,
                "2022-12-21T11:25",
                30,
                0);

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final List<SubTask> subTask = taskManager.epicSubTaskList(0);
        assertNotNull(subTask, "SubTask not null");
        assertEquals(subTask.get(0), subTask1, "Tasks not same");
        assertEquals(subTask.get(0).getEpicId(), epic1.getId(), "Id not same");
        assertEquals(1, subTask.size(), "SubTask size not one");
    }

    @Test
    public void shouldManagerReturnTwoEpicSubTasksListWhenTwoSubTaskAddWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T11:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final List<SubTask> subTasks1 = taskManager.epicSubTaskList(0);
        final List<SubTask> subTasks2 = taskManager.epicSubTaskList(2);

        assertNotNull(subTasks1, "SubTask not null");
        assertNotNull(subTasks2, "SubTask not null");
        assertNotEquals(subTasks1, subTasks2, "Lists ate same");
        assertEquals(subTasks1.get(0), subTask1, "Task not same");
        assertEquals(subTasks2.get(0), subTask2, "Task not same");
        assertEquals(1, subTasks1.size(), "SubTask size not one");
        assertEquals(1, subTasks2.size(), "SubTask size not one");
    }

    @Test
    public void shouldManagerReturnTwoEpicSubTasksListWhenThreeSubTaskAddWithTwoEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T11:25",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.DONE,
                "2022-12-21T12:25",
                30,
                2
        );
        SubTask subTask3 = new SubTask(
                "Подготовка к переезду",
                0,
                "Упаковать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T13:25",
                30,
                2
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        final List<SubTask> subTasks1 = taskManager.epicSubTaskList(0);
        final List<SubTask> subTasks2 = taskManager.epicSubTaskList(2);

        assertNotNull(subTasks1, "SubTask not null");
        assertNotNull(subTasks2, "SubTask not null");
        assertNotEquals(subTasks1, subTasks2, "Lists ate same");
        assertEquals(subTasks1.get(0), subTask1, "Task not same");
        assertEquals(subTasks2.get(0), subTask2, "Task not same");
        assertEquals(subTasks2.get(1), subTask3, "Task not same");
        assertEquals(1, subTasks1.size(), "SubTask size not one");
        assertEquals(2, subTasks2.size(), "SubTask size not one");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksHasSameStartTimeWithAddTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addTask(task1);

        final Task timeTask = taskManager.getTaskFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertEquals(timeTask.getStartTime(), task2.getStartTime(), "Time not same");
        assertEquals(timeTask.getStartTime(), epic1.getStartTime(), "Time not same");
        assertEquals(timeTask.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeTask, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(0, testEpics.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksHasSameStartTimeWithAddEpic() {
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );

        taskManager.addEpic(epic1);

        final Epic timeEpic = taskManager.getEpicFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), epic2.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeEpic, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeEpic, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksHasSameStartTimeWithAddEpicWithSubTask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask = taskManager.getSubTaskId(1);
        final List<Epic> timeEpics = taskManager.getEpics();
        final List<SubTask> timeSubTasks = taskManager.getSubTask();

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                3
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(2);
        final Epic testEpic = taskManager.getEpicFromId(3);
        final SubTask testSunTask = taskManager.getSubTaskId(4);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask, "Task is null");
        assertEquals(timeSubtask.getStartTime(), timeEpic.getStartTime(), "Time not same");
        assertEquals(timeSubtask.getStartTime(), task2.getStartTime(), "Time not same");
        assertEquals(timeSubtask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertEquals(timeSubtask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubtask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeSubtask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeEpic, "Task not same");
        assertEquals(2, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksHasSameStartTimeWithAddTasksAndEpicWithSubTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:25",
                30,
                1
        );

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task timeTask = taskManager.getTaskFromId(0);
        final Epic timeEpic = taskManager.getEpicFromId(1);
        final SubTask timeSubTask = taskManager.getSubTaskId(2);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:25",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:25",
                30,
                4
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(3);
        final Epic testEpic = taskManager.getEpicFromId(4);
        final SubTask testSunTask = taskManager.getSubTaskId(5);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeTask, "Task is null");
        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubTask, "Task is null");
        assertEquals(timeTask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertEquals(timeTask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), timeSubTask.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubTask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeSubTask, "Task not same");
        assertEquals(prioritizedTask.get(2), timeEpic, "Task not same");
        assertEquals(3, prioritizedTask.size(), "Size not 3  ");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddTaskAndStartIsBeforeEndAddTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addTask(task1);

        final Task timeTask = taskManager.getTaskFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:31",
                30
        );
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:40",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:59",
                30,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotEquals(timeTask.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), epic1.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeTask, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(0, testEpics.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddEpicAndStartIsBeforeEndAddEpic() {
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addEpic(epic1);

        final Epic timeEpic = taskManager.getEpicFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:31",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:43",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:59",
                30,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeEpic, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeEpic, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddEpicWithSubTaskAndStartIsBeforeEndAddEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:30",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask = taskManager.getSubTaskId(1);
        final List<Epic> timeEpics = taskManager.getEpics();
        final List<SubTask> timeSubTasks = taskManager.getSubTask();

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:31",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:42",
                30
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:59",
                30,
                3
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(2);
        final Epic testEpic = taskManager.getEpicFromId(3);
        final SubTask testSunTask = taskManager.getSubTaskId(4);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask, "Task is null");
        assertEquals(timeSubtask.getStartTime(), timeEpic.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubtask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeSubtask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeEpic, "Task not same");
        assertEquals(2, prioritizedTask.size(), "Size not 1");
    }


    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddTaskEpicWithSubTaskAndStartIsBeforeEndAddTaskEpic() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:30",
                30,
                1
        );

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task timeTask = taskManager.getTaskFromId(0);
        final Epic timeEpic = taskManager.getEpicFromId(1);
        final SubTask timeSubTask = taskManager.getSubTaskId(2);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:31",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:31",
                30
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:59",
                30,
                4
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(3);
        final Epic testEpic = taskManager.getEpicFromId(4);
        final SubTask testSunTask = taskManager.getSubTaskId(5);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeTask, "Task is null");
        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubTask, "Task is null");
        assertNotEquals(timeTask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), timeSubTask.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubTask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeSubTask, "Task not same");
        assertEquals(prioritizedTask.get(2), timeEpic, "Task not same");
        assertEquals(3, prioritizedTask.size(), "Size not 3 ");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksEndIsAfterAddTaskAndEndIsBeforeEndAddTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addTask(task1);

        final Task timeTask = taskManager.getTaskFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:00",
                31
        );
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:29",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:20",
                30,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotEquals(timeTask.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), epic1.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeTask, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(0, testEpics.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksEndIsAfterAddEpicAndEndIsBeforeEndAddEpic() {
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addEpic(epic1);

        final Epic timeEpic = taskManager.getEpicFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:00",
                31
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:29",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:20",
                30,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeEpic, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeEpic, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksEndIsAfterAddEpicWithSubTaskAndEndIsBeforeEndAddEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:30",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask = taskManager.getSubTaskId(1);
        final List<Epic> timeEpics = taskManager.getEpics();
        final List<SubTask> timeSubTasks = taskManager.getSubTask();

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:00",
                31
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:29",
                30
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:20",
                30,
                3
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(2);
        final Epic testEpic = taskManager.getEpicFromId(3);
        final SubTask testSunTask = taskManager.getSubTaskId(4);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask, "Task is null");
        assertEquals(timeSubtask.getStartTime(), timeEpic.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubtask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeSubtask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeEpic, "Task not same");
        assertEquals(2, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksEndIsAfterAddTaskEpicWithSubTaskAndEndIsBeforeEndAddTaskEpic() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:30",
                30,
                1
        );

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task timeTask = taskManager.getTaskFromId(0);
        final Epic timeEpic = taskManager.getEpicFromId(1);
        final SubTask timeSubTask = taskManager.getSubTaskId(2);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:01",
                30
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:29",
                30
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:20",
                30,
                4
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(3);
        final Epic testEpic = taskManager.getEpicFromId(4);
        final SubTask testSunTask = taskManager.getSubTaskId(5);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeTask, "Task is null");
        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubTask, "Task is null");
        assertNotEquals(timeTask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), timeSubTask.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubTask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeSubTask, "Task not same");
        assertEquals(prioritizedTask.get(2), timeEpic, "Task not same");
        assertEquals(3, prioritizedTask.size(), "Size not 3 ");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddTaskAndEndIsBeforeEndAddTask() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addTask(task1);

        final Task timeTask = taskManager.getTaskFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:31",
                28
        );
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:35",
                10
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:45",
                5,
                1
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotEquals(timeTask.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), epic1.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeTask, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(0, testEpics.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddEpicAndEndIsBeforeEndAddEpic() {
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addEpic(epic1);

        final Epic timeEpic = taskManager.getEpicFromId(0);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:31",
                28
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:37",
                8
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:43",
                7,
                2
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task testTask = taskManager.getTaskFromId(1);
        final Epic testEpic = taskManager.getEpicFromId(2);
        final SubTask testSunTask = taskManager.getSubTaskId(3);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), subTask1.getStartTime(), "Time not same");
        assertNotNull(timeEpic, "Task is null");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(0, testSubTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeEpic, "Task not same");
        assertEquals(1, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddEpicWithSubTaskAndEndIsBeforeEndAddEpic() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:30",
                30,
                0
        );

        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask = taskManager.getSubTaskId(1);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:31",
                28
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:43",
                10
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:35",
                4,
                3
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(2);
        final Epic testEpic = taskManager.getEpicFromId(3);
        final SubTask testSunTask = taskManager.getSubTaskId(4);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask, "Task is null");
        assertEquals(timeSubtask.getStartTime(), timeEpic.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), task2.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeSubtask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubtask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(0, testTasks.size(), "Size not 0");
        assertEquals(prioritizedTask.get(0), timeSubtask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeEpic, "Task not same");
        assertEquals(2, prioritizedTask.size(), "Size not 1");
    }

    @Test
    public void shouldNotManagerAddTasksIfTasksStartIsAfterAddTaskEpicWithSubTaskAndEndIsBeforeEndAddTaskEpic() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:30",
                30,
                1
        );

        taskManager.addTask(task1);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);

        final Task timeTask = taskManager.getTaskFromId(0);
        final Epic timeEpic = taskManager.getEpicFromId(1);
        final SubTask timeSubTask = taskManager.getSubTaskId(2);

        Task task2 = new Task("Task2",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T10:31",
                28
        );
        Epic epic2 = new Epic(
                "Epic2",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T09:31",
                28
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:45",
                5,
                4
        );

        taskManager.addTask(task2);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subTask2);

        final Task testTask = taskManager.getTaskFromId(3);
        final Epic testEpic = taskManager.getEpicFromId(4);
        final SubTask testSunTask = taskManager.getSubTaskId(5);
        final List<Task> testTasks = taskManager.getTasks();
        final List<Epic> testEpics = taskManager.getEpics();
        final List<SubTask> testSubTasks = taskManager.getSubTask();
        final List<Task> prioritizedTask = taskManager.getPrioritizedTasks();

        assertNotNull(timeTask, "Task is null");
        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubTask, "Task is null");
        assertNotEquals(timeTask.getStartTime(), epic2.getStartTime(), "Time not same");
        assertNotEquals(timeTask.getStartTime(), subTask2.getStartTime(), "Time not same");
        assertEquals(timeEpic.getStartTime(), timeSubTask.getStartTime(), "Time not same");
        assertNotEquals(timeEpic.getStartTime(), task2.getStartTime(), "Time not same");
        assertNull(testTask, "Task not null");
        assertNull(testEpic, "Task not null");
        assertNull(testSunTask, "Task not null");
        assertEquals(testEpics.get(0), timeEpic, "Task not same");
        assertEquals(1, testEpics.size(), "Size not 1");
        assertEquals(testSubTasks.get(0), timeSubTask, "Task not same");
        assertEquals(1, testSubTasks.size(), "Size not 1");
        assertEquals(testTasks.get(0), timeTask, "Task not same");
        assertEquals(1, testTasks.size(), "Size not 1");
        assertEquals(prioritizedTask.get(0), timeTask, "Task not same");
        assertEquals(prioritizedTask.get(1), timeSubTask, "Task not same");
        assertEquals(prioritizedTask.get(2), timeEpic, "Task not same");
        assertEquals(3, prioritizedTask.size(), "Size not 3 ");
    }

    @Test
    public void shouldManagerChangeEpicStartTimeDurationEndTimeIfAddWithOneSubTask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:30",
                30,
                0
        );

        taskManager.addEpic(epic1);

        assertNotEquals(epic1.getStartTime(), subTask1.getStartTime(), "Time is same");
        assertNotEquals(epic1.getDuration(), subTask1.getDuration(), "Time is same");
        assertNotEquals(epic1.getEndTime(), subTask1.getEndTime(), "Time is same");

        taskManager.addSubtask(subTask1);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask = taskManager.getSubTaskId(1);

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask, "Task is null");
        assertEquals(timeEpic.getStartTime(), timeSubtask.getStartTime(), "Time not same");
        assertEquals(timeEpic.getDuration(), timeSubtask.getDuration(), "Time not same");
        assertEquals(timeEpic.getEndTime(), timeSubtask.getEndTime(), "Time not same");
    }

    @Test
    public void shouldManagerChangeEpicStartTimeDurationEndTimeIfAddWithTwoSubTask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:00",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:30",
                45,
                0
        );

        taskManager.addEpic(epic1);

        assertNotEquals(epic1.getStartTime(), subTask1.getStartTime(), "Time is same");
        assertNotEquals(epic1.getDuration(), subTask1.getDuration(), "Time is same");
        assertNotEquals(epic1.getEndTime(), subTask1.getEndTime(), "Time is same");
        assertNotEquals(epic1.getStartTime(), subTask2.getStartTime(), "Time is same");
        assertNotEquals(epic1.getDuration(), subTask2.getDuration(), "Time is same");
        assertNotEquals(epic1.getEndTime(), subTask2.getEndTime(), "Time is same");

        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask1 = taskManager.getSubTaskId(1);
        final SubTask timeSubtask2 = taskManager.getSubTaskId(2);
        final int epicDuration = timeSubtask1.getDuration() + timeSubtask2.getDuration();

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask1, "Task is null");
        assertNotNull(timeSubtask2, "Task is null");
        assertNotNull(epicDuration, "Duration is null");
        assertEquals(timeEpic.getStartTime(), timeSubtask1.getStartTime(), "Time not same");
        assertEquals(timeEpic.getDuration(), epicDuration, "Time not same");
        assertEquals(timeEpic.getEndTime(), timeSubtask2.getEndTime(), "Time not same");
    }

    @Test
    public void shouldManagerChangeEpicStartTimeDurationEndTimeIfAddWithThreeSubTask() {
        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T09:00",
                30,
                0
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T10:30",
                45,
                0
        );
        SubTask subTask3 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T12:13",
                25,
                0
        );

        taskManager.addEpic(epic1);

        assertNotEquals(epic1.getStartTime(), subTask1.getStartTime(), "Time is same");
        assertNotEquals(epic1.getDuration(), subTask1.getDuration(), "Time is same");
        assertNotEquals(epic1.getEndTime(), subTask1.getEndTime(), "Time is same");
        assertNotEquals(epic1.getStartTime(), subTask2.getStartTime(), "Time is same");
        assertNotEquals(epic1.getDuration(), subTask2.getDuration(), "Time is same");
        assertNotEquals(epic1.getEndTime(), subTask2.getEndTime(), "Time is same");
        assertNotEquals(epic1.getStartTime(), subTask3.getStartTime(), "Time is same");
        assertNotEquals(epic1.getDuration(), subTask3.getDuration(), "Time is same");
        assertNotEquals(epic1.getEndTime(), subTask3.getEndTime(), "Time is same");

        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);
        taskManager.addSubtask(subTask3);

        final Epic timeEpic = taskManager.getEpicFromId(0);
        final SubTask timeSubtask1 = taskManager.getSubTaskId(1);
        final SubTask timeSubtask2 = taskManager.getSubTaskId(2);
        final SubTask timeSubtask3 = taskManager.getSubTaskId(3);
        final int epicDuration = timeSubtask1.getDuration() + timeSubtask2.getDuration() + timeSubtask3.getDuration();

        assertNotNull(timeEpic, "Task is null");
        assertNotNull(timeSubtask1, "Task is null");
        assertNotNull(timeSubtask2, "Task is null");
        assertNotNull(timeSubtask3, "Task is null");
        assertNotNull(epicDuration, "Duration is null");
        assertEquals(timeEpic.getStartTime(), timeSubtask1.getStartTime(), "Time not same");
        assertEquals(timeEpic.getDuration(), epicDuration, "Time not same");
        assertEquals(timeEpic.getEndTime(), timeSubtask3.getEndTime(), "Time not same");
    }

    @Test
    public void shouldManagerReturnListPrioritizeTasksIfOneTaskAdd() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        taskManager.addTask(task1);

        final Task testTask = taskManager.getTaskFromId(0);
        final List<Task> prioritizeTask = taskManager.getPrioritizedTasks();

        assertNotNull(testTask, "Task is null");
        assertEquals(testTask, task1, "Task not same");
        assertEquals(prioritizeTask.get(0), testTask, "task not same");
        assertEquals(1, prioritizeTask.size(), "Size not 1");
    }

    @Test
    public void shouldManagerReturnListPrioritizeTasksIfTwoTaskAndNullTimeTaskAdd() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        Task task2 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:30",
                30
        );

        Task task3 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        final Task testTask1 = taskManager.getTaskFromId(0);
        final Task testTask2 = taskManager.getTaskFromId(1);
        final Task testTaskZero = taskManager.getTaskFromId(2);
        final List<Task> prioritizeTask = taskManager.getPrioritizedTasks();

        assertNotNull(testTask1, "Task is null");
        assertNotNull(testTask2, "Task is null");
        assertNotNull(testTaskZero, "Task is null");
        assertEquals(testTask1, task1, "Task not same");
        assertEquals(testTask2, task2, "Task not same");
        assertEquals(testTaskZero, task3, "Task not same");
        assertEquals(prioritizeTask.get(0), testTask2, "task not same");
        assertEquals(prioritizeTask.get(1), testTask1, "task not same");
        assertEquals(prioritizeTask.get(2), testTaskZero, "task not same");
        assertEquals(3, prioritizeTask.size(), "Size not 3");
    }

    @Test
    public void shouldManagerReturnListPrioritizeTasksIfTwoTaskAndNullTimeTaskAddAndEpicWithSubTasks() {
        Task task1 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T09:30",
                30
        );

        Task task2 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW,
                "2022-12-21T08:30",
                30
        );

        Task task3 = new Task("Task1",
                0,
                "собрать коробки",
                Status.NEW
        );
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW,
                "2022-12-21T15:00",
                30
        );
        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T12:00",
                30,
                3
        );
        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0, "Собрать вещи",
                Status.IN_PROGRESS,
                "2022-12-21T13:30",
                45,
                3
        );

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subTask1);
        taskManager.addSubtask(subTask2);

        final Task testTask1 = taskManager.getTaskFromId(0);
        final Task testTask2 = taskManager.getTaskFromId(1);
        final Task testTaskZero = taskManager.getTaskFromId(2);
        final Epic testEpic = taskManager.getEpicFromId(3);
        final SubTask testSabTask1 = taskManager.getSubTaskId(4);
        final SubTask testSabTask2 = taskManager.getSubTaskId(5);
        final List<Task> prioritizeTask = taskManager.getPrioritizedTasks();

        assertNotNull(testTask1, "Task is null");
        assertNotNull(testTask2, "Task is null");
        assertNotNull(testTaskZero, "Task is null");
        assertNotNull(testEpic, "Task is null");
        assertNotNull(testSabTask1, "Task is null");
        assertNotNull(testSabTask2, "Task is null");
        assertEquals(testTask1, task1, "Task not same");
        assertEquals(testTask2, task2, "Task not same");
        assertEquals(testTaskZero, task3, "Task not same");
        assertEquals(testEpic, epic1, "Task not same");
        assertEquals(testSabTask1, subTask1, "Task not same");
        assertEquals(testSabTask2, subTask2, "Task not same");
        assertEquals(prioritizeTask.get(0), testTask2, "task not same");
        assertEquals(prioritizeTask.get(1), testTask1, "task not same");
        assertEquals(prioritizeTask.get(2), testSabTask1, "task not same");
        assertEquals(prioritizeTask.get(3), testEpic, "task not same");
        assertEquals(prioritizeTask.get(4), testSabTask2, "task not same");
        assertEquals(prioritizeTask.get(5), testTaskZero, "task not same");
        assertEquals(6, prioritizeTask.size(), "Size not 6");
    }
}