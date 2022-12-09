package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskIds;

    public Epic(String name, int Id, String specification, Status status) {
        super(name, Id, specification, status);
        super.getType = TaskType.EPIC;
        subTaskIds = new ArrayList<>();

    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public Integer getEpicId() {
        return null;
    }

    public void addSubtaskId(int subTaskId) {
        subTaskIds.add(subTaskId);
    }

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void removeSubTaskId(int subTasksId) {
        subTaskIds.remove(subTasksId);
    }

    public void clearSubTaskId() {
        subTaskIds.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskIds=" + subTaskIds +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", specification='" + specification + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
