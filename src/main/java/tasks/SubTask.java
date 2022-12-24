package tasks;


public class SubTask extends Task {
    private int epicId;

    public SubTask(String name, int Id, String specification, Status status, int epicId) {
        super(name, Id, specification, status);
        super.type = TaskType.SUBTASK;
        this.epicId = epicId;
    }

    public SubTask(String name, int Id, String specification, Status status, String localDateTime, String duration, int epicId) {
        super(name, Id, specification, status, localDateTime, duration);
        super.type = TaskType.SUBTASK;
        this.epicId = epicId;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
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