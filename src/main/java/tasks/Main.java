package tasks;

import manager.*;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Manager.getFileTaskManager("test.csv");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTask());
        System.out.println("список приоритетных задач" + manager.getPrioritizedTasks());
//
//        Task task1 = new Task(
//                "task1",
//                0, "убраться",
//                Status.NEW,
//                "2022-12-21T09:25",
//                "PT30S"
//        );
//        Epic epic1 = new Epic(
//                "Epic1",
//                0,
//                "Организовать переезд",
//                Status.NEW,
//                "2022-12-21T11:21",
//                "PT60S"
//        );

//        SubTask subTask1 = new SubTask(
//                "Подготовка к переезду",
//                0,
//                "Собрать вещи",
//                Status.NEW,
//                "2022-12-21T12:21",
//                "PT30S",
//                1
//        );

//        SubTask subTask2 = new SubTask(
//                "Подготовка к переезду",
//                0,
//                "Собрать вещи",
//                Status.NEW,
//                1
//        );
        // Task task7 = new Task("Task7", 0, "собрать коробки", Status.NEW);

//        SubTask subTask3 = new SubTask(
//                "Подготовка к переезду",
//                0,
//                "Собрать вещи",
//                Status.NEW,
//                "2022-12-21T13:25",
//                "PT40S",
//                1
//        );
//        Task task2 = new Task(
//                "task2",
//                0, "убраться",
//                Status.NEW,
//                "2022-12-21T09:25",
//                "PT30S"
//        );
//        Task task8 = new Task("Task8", 0, "3", Status.DONE);
//        Task task3 = new Task(
//                "task3",
//                0, "убраться",
//                Status.NEW,
//                "2022-12-21T17:11",
//                "PT30S"
//        );
//
//        manager.addTask(task1);
//        manager.addEpic(epic1);
//        manager.addSubtask(subTask1);
//       // manager.addSubtask(subTask2);
//       // manager.addTask(task8);
//        manager.addSubtask(subTask3);
//        manager.addTask(task2);
//        manager.addTask(task3);
//       // manager.addTask(task7);
//        System.out.println(manager.getTasks());
//        System.out.println(manager.getEpics());
//        System.out.println(manager.getSubTask());
//
//        System.out.println("список приоритетных задач" + manager.getPrioritizedTasks());
//        System.out.println(manager.getTasks());
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
