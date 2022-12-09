package tasks;


public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, int Id, String specification, Status status, int epicId) {
        super(name, Id, specification, status);
        super.getType = TaskType.SUBTASK;
        this.epicId = epicId;

    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", specification='" + specification + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
