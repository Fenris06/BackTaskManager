package converter;

import manager.HistoryManager;
import tasks.*;

import java.util.ArrayList;
import java.util.List;

public class CSVTaskConverter {

    public static String getHeader() {
        String getHeader = "id,type,name,status,description,epic";
        return getHeader;
    }

    //TaskType.valueOf(task.getName().toUpperCase().substring(0, 4))
    public static String toString(Task task) {
        if(task.getType().equals(TaskType.SUBTASK)){
            SubTask subTask = (SubTask)task;
            return subTask.getId()
                    + ","
                    + subTask.getType()
                    + ","
                    + subTask.getName()
                    + ","
                    + subTask.getStatus()
                    + ","
                    + subTask.getSpecification()
                    + ","
                    + subTask.getEpicId();
        }
        return task.getId()
                + ","
                + task.getType()
                + ","
                + task.getName()
                + ","
                + task.getStatus()
                + ","
                + task.getSpecification()
                + ","
                + null;
    }

    public static Task fromString(String value) {
        String[] taskString = value.split(",");
        TaskType taskType = TaskType.valueOf(taskString[1]);
        switch (taskType) {
            case TASK: {
                return new Task(
                        taskString[2],
                        Integer.parseInt(taskString[0]),
                        taskString[4],
                        Status.valueOf(taskString[3])
                );
            }
            case EPIC: {
                return new Epic(
                        taskString[2],
                        Integer.parseInt(taskString[0]),
                        taskString[4],
                        Status.valueOf(taskString[3])
                );
            }
            case SUBTASK: {
                return new SubTask(
                        taskString[2],
                        Integer.parseInt(taskString[0]),
                        taskString[4],
                        Status.valueOf(taskString[3]),
                        Integer.parseInt(taskString[5])
                );
            }
            default:
                return null;
        }
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> history = new ArrayList<>(manager.getHistory());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
            builder.append(history.get(i).getId());
            if (i != history.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public static List<Integer> historyFromString(String value) {
        String[] historyFromString = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (int i = 0; i < historyFromString.length; i++) {
            history.add(Integer.valueOf(historyFromString[i]));
        }
        return history;
    }


}