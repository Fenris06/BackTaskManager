package tasks;

import manager.*;
import server.HttpTaskServer;
import server.KVServer;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        KVServer server = new KVServer();
        server.start();
        TaskManager manager = Manager.getDefault();
//        HttpTaskServer taskServer = new HttpTaskServer();
//        taskServer.start();
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTask());
        System.out.println("список приоритетных задач" + manager.getPrioritizedTasks());


        Task task1 = new Task(
                "task1",
                0, "убраться",
                Status.NEW,
                "2022-12-21T09:25",
                30
        );
        Epic epic1 = new Epic(
                "Epic1",
                0,
                "Организовать переезд",
                Status.NEW
        );

        SubTask subTask1 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.NEW,
                "2022-12-21T12:21",
                30,
                1
        );

        SubTask subTask2 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.NEW,
                1
        );

        SubTask subTask3 = new SubTask(
                "Подготовка к переезду",
                0,
                "Собрать вещи",
                Status.DONE,
                "2022-12-21T13:25",
                40,
                1
        );
        Task task2 = new Task(
                "task2",
                0, "убраться",
                Status.NEW,
                "2022-12-21T07:25",
                30
        );
        Task task8 = new Task("Task8", 0, "3", Status.DONE);
        Task task3 = new Task(
                "task3",
                0, "убраться",
                Status.NEW,
                "2022-12-21T17:11",
                30
        );

        Task task4 = new Task(
                "task4",
                1, "убраться",
                Status.DONE,
                "2022-12-21T18:00",
                30
        );
        SubTask subTask4 = new SubTask(
                "Subtask4",
                3,
                "Собрать вещи",
                Status.NEW,
                "2022-12-21T14:25",
                40,
                1
        );



        manager.addTask(task1);
        manager.addEpic(epic1);
        manager.addSubtask(subTask1);
        manager.addSubtask(subTask2);

        manager.addTask(task2);

        manager.addTask(task8);
        manager.addSubtask(subTask3);
        manager.addTask(task2);
        manager.addTask(task3);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTask());
        System.out.println("список приоритетных задач" + manager.getPrioritizedTasks());
        manager.clearAllSubTasks();
        System.out.println(manager.getSubTask());
        System.out.println("список приоритетных задач" + manager.getPrioritizedTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTask());
        System.out.println("список приоритетных задач" + manager.getPrioritizedTasks());
        System.out.println(manager.getTasks());

        server.stop();
//       taskServer.stop();


//        System.out.println("История " + manager.getHistory());
//        System.out.println(manager.getEpics());
//        System.out.println(manager.getSubTask());
//
//       Task task = new Task("Task2", 0, "собрать коробки", Status.NEW);
//       manager.addTask(task);
//        System.out.println("Таска после добавления " + manager.getTasks());
//
//        Task task1 = new Task("task1", 0, "убраться", Status.NEW);
//        Task task2 = new Task("уборка", 0, "помыть посуду", Status.NEW);
//        Epic epic1 = new Epic("Epic1", 0, "Организовать переезд", Status.NEW);
//        SubTask subTask1 = new SubTask("Подготовка к переезду", 0, "Собрать вещи", Status.NEW, 2);
//        SubTask subTask2 = new SubTask("Подготовка к переезду", 0, "Упаковать вещи", Status.NEW, 2);
//        SubTask subTask3 = new SubTask("Подготовка к переезду", 0, "Забронировать машину", Status.DONE, 2);
//        Epic epic2 = new Epic("Учеба", 0, "Выполнить задание", Status.NEW);
////
//       manager.addTask(task1);
//
////        manager.getSubTask();
////        manager.getTasks();
//
//      manager.addTask(task2);
//        manager.addEpic(epic1);
//
//
//
//       manager.addSubtask(subTask1);
//        manager.addSubtask(subTask2);
//        manager.addSubtask(subTask3);
//        System.out.println(manager.getEpics());
//        System.out.println(manager.getSubTask());
//        manager.deleteSubTask(3);
//       System.out.println(manager.getSubTask());
//        System.out.println(manager.epicSubTaskList(2));


//        manager.addEpic(epic2);
//        System.out.println(manager.getTasks());
//        System.out.println(manager.getEpics());
//        System.out.println(manager.getSubTask());
////
//        manager.getTaskFromId(0);
//        System.out.println("История задач " + manager.getHistory());
//        manager.getTaskFromId(1);
//       System.out.println("История задач " + manager.getHistory());
//      manager.getTaskFromId(1);
//       System.out.println("История задач вызов второй таски " + manager.getHistory());
//       manager.getEpicFromId(2);
//       manager.getEpicFromId(6);
//        System.out.println("История задач " + manager.getHistory());
//        manager.getEpicFromId(2);
//       System.out.println("История задач " + manager.getHistory());
//                manager.getTaskFromId(0);
//        System.out.println("История задач и эпика" + manager.getHistory());
//        manager.getSubTaskId(3);
//        manager.getSubTaskId(4);
//        manager.getSubTaskId(5);
//        System.out.println("Новые эпики " + manager.getEpics());
//        System.out.println(manager.getHistory());
//        System.out.println("История задач, эпика и сабтасками" + manager.getHistory());


//        manager.getSubTaskId(3);
//        manager.getEpicFromId(6);
//        System.out.println("История задач, эпика и сабтасками и эпиком" + manager.getHistory());
//        manager.deleteTask(1);
//        System.out.println("История задач после удаления таски" + manager.getHistory());
//        manager.deleteEpic(2);
//        System.out.println("История задач после удаления эпика с сабтасками " + manager.getHistory());

    }

}
