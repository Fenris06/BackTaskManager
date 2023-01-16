package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskIds;

    public Epic(String name, int Id, String specification, Status status) {
        super(name, Id, specification, status);
        super.type = TaskType.EPIC;
        subTaskIds = new ArrayList<>();
    }

    public Epic(String name, int Id, String specification, Status status, String localDateTime, int duration){
        super(name, Id, specification, status, localDateTime, duration);
        super.type = TaskType.EPIC;
        subTaskIds = new ArrayList<>();
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public void addSubtaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTaskId(Integer subTasksId) {
        subTaskIds.remove(subTasksId);
    }

    public void clearSubTaskId() {
        subTaskIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskIds=" + subTaskIds +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", specification='" + specification + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", endTime=" + endTime +
                '}';
    }
}
