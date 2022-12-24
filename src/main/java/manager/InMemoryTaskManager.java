package manager;

import tasks.Epic;
import tasks.Status;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected int generationId = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected TreeSet<Task> prioritizedTasks = new TreeSet<>();

    protected HistoryManager historyManager = Manager.getDefaultHistory();


    @Override
    public void addTask(Task task) {
        task.setId(generationId++);
        if (isValidationTask(task)) {
            prioritizedTasks.add(task);
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void clearAllTAsks() {
        tasks.clear();
    }

    @Override
    public Task getTaskFromId(Integer id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.get(task.getId()) != null)
            tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTask(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generationId++);
        if (isValidationTask(epic)) {
            prioritizedTasks.add(epic);
            epics.put(epic.getId(), epic);
        }

    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void clearAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicFromId(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void deleteEpic(int id) {
        historyManager.remove(id);
        Epic deletedEpic = epics.remove(id);
        for (Integer subtaskId : deletedEpic.getSubTaskIds()) {
            subTasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.get(epic.getId()) != null)
            epics.put(epic.getId(), epic);
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subTasksId = epic.getSubTaskIds();
        if (subTasksId.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        Status status = null;
        for (int subtasksId : subTasksId) {
            SubTask subTask = subTasks.get(subtasksId);
            if (status == null) {
                status = subTask.getStatus();
                continue;
            }
            if (status.equals(subTask.getStatus())) {
                continue;
            }
            epic.setStatus(Status.IN_PROGRESS);
            return;
        }
        epic.setStatus(status);
    }

    @Override
    public void addSubtask(SubTask subTask) {
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subTask.setId(generationId++);
        if (isValidationTask(subTask)) {
            prioritizedTasks.add(subTask);
            subTasks.put(subTask.getId(), subTask);
            // addPrioritizedTask(subTask);
            // prioritizedTasks.add(subTask);
            epic.addSubtaskId(subTask.getId());
            updateEpicStatus(epic);
            setEpicStartTime(epic);
            setEpicEndTime(epic);
            setEpicDuration(epic);
        }
    }

    @Override
    public ArrayList<SubTask> getSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void clearAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTaskId();
            updateEpicStatus(epic);
            setEpicStartTime(epic);
            setEpicEndTime(epic);
            setEpicDuration(epic);
        }
    }

    @Override
    public void deleteSubTask(Integer id) {
        historyManager.remove(id);
        SubTask deleteSubTask = subTasks.remove(id);
        if (deleteSubTask != null) {
            int deleteEpicId = deleteSubTask.getEpicId();
            Epic deleteEpic = epics.get(deleteEpicId);
            deleteEpic.removeSubTaskId(id);
            updateEpicStatus(deleteEpic);
            setEpicStartTime(deleteEpic);
            setEpicEndTime(deleteEpic);
            setEpicDuration(deleteEpic);
        }
    }

    @Override
    public SubTask getSubTaskId(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.get(subTask.getId()) != null) {
            subTasks.put(subTask.getId(), subTask);
            int epicId = subTask.getEpicId();
            Epic epic = epics.get(epicId);
            updateEpicStatus(epic);
            setEpicStartTime(epic);
            setEpicEndTime(epic);
            setEpicDuration(epic);
        }
    }

    @Override
    public ArrayList<SubTask> epicSubTaskList(int id) {
        Epic epicSubTaskList = epics.get(id);
        ArrayList<SubTask> subTask = new ArrayList<>();
        if (epicSubTaskList != null) {
            for (Integer subTaskId : epicSubTaskList.getSubTaskIds()) {
                subTask.add(subTasks.get(subTaskId));
            }
        }
        return subTask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void setEpicStartTime(Epic epic) {
        List<Integer> epicSubtasks = epic.getSubTaskIds();
        if (epicSubtasks.isEmpty()) {
            return;
        }
        LocalDateTime minSubTask = LocalDateTime.MAX;
        for (Integer id : epicSubtasks) {
            SubTask subTask = subTasks.get(id);
            if (subTask.getStartTime() == null) {
                continue;
            }
            if (subTask.getStartTime().isBefore(minSubTask))
                minSubTask = subTask.getStartTime();
        }
        epic.setStartTime(minSubTask);
    }

    public void setEpicDuration(Epic epic) {
        List<Integer> epicSubtasks = epic.getSubTaskIds();
        if (epicSubtasks.isEmpty()) {
            return;
        }
        Duration sumDurationSubTask = Duration.ofMinutes(0);
        for (Integer id : epicSubtasks) {
            SubTask subTask = subTasks.get(id);
            if (subTask.getStartTime() == null) {
                continue;
            }
            sumDurationSubTask = sumDurationSubTask.plus(subTask.getDuration());
        }
        epic.setDuration(sumDurationSubTask);
    }

    public void setEpicEndTime(Epic epic) {
        List<Integer> epicSubtasks = epic.getSubTaskIds();
        if (epicSubtasks.isEmpty()) {
            return;
        }
        LocalDateTime maxSubTask = LocalDateTime.MIN;
        for (Integer id : epicSubtasks) {
            SubTask subTask = subTasks.get(id);
            if (subTask.getEndTime() == null) {
                continue;
            }
            if (subTask.getEndTime().isAfter(maxSubTask))
                maxSubTask = subTask.getEndTime();
        }
        epic.setEndTime(maxSubTask);
    }

    public boolean isValidationTask(Task task) {
        if (prioritizedTasks.isEmpty()) {
            return true;
        }
        if (task.getStartTime() != null && task.getEndTime() != null) {
            for (Task tasks : prioritizedTasks) {
                if (tasks.getStartTime() == null || tasks.getEndTime() == null) {
                    continue;
                }
                if (task.getStartTime().isBefore(tasks.getEndTime())
                        && task.getEndTime().isAfter(tasks.getStartTime())) {
                    System.out.println("StartTime has used. Change time " + task);
                    return false;
                }
            }
        }

        return true;
    }

    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public String toString() {
        return "InMemoryTaskManager{" +
                "generationId=" + generationId +
                ", tasks=" + tasks +
                ", subTasks=" + subTasks +
                ", epics=" + epics +
                ", prioritizedTasks=" + prioritizedTasks +
                ", historyManager=" + historyManager +
                '}';
    }
}



