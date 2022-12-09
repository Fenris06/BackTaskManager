package manager;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void addTask(Task task);

    ArrayList<Task> getTasks();

    void clearAllTAsks();

    Task getTaskFromId(Integer id);

    void updateTask(Task task);

    void deleteTask(int id);

    void addEpic(Epic epic);

    ArrayList<Epic> getEpics();

    void clearAllEpics();

    Epic getEpicFromId(int id);

    void deleteEpic(int id);

    void updateEpic(Epic epic);

    void addSubtask(SubTask subTask);

    ArrayList<SubTask> getSubTask();

    void clearAllSubTasks();

    void deleteSubTask(Integer id);

    SubTask getSubTaskId(int id);

    void updateSubTask(SubTask subTask);

    ArrayList<SubTask> epicSubTaskList(int id);

    List<Task> getHistory();
}


