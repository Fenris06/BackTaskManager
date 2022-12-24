package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task implements Comparable<Task> {
    protected TaskType type;
    protected String name;
    protected int id;
    protected String specification;
    protected Status status;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
    public static final DateTimeFormatter DURATION = DateTimeFormatter.ofPattern("mm");
    protected LocalDateTime startTime;
    protected Duration duration;
    protected LocalDateTime endTime;

    public Task(String name, int Id, String specification, Status status) {
        this.name = name;
        this.id = Id;
        this.specification = specification;
        this.status = status;
        type = TaskType.TASK;
    }

    public Task(String name, int Id, String specification, Status status, String localDateTime, String duration) {
        this.name = name;
        this.id = Id;
        this.specification = specification;
        this.status = status;
        this.startTime = LocalDateTime.parse(localDateTime);
        this.duration =  Duration.parse(duration);
        type = TaskType.TASK;
        endTime = startTime.plus(this.duration);
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public TaskType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && type == task.type
                && Objects.equals(name, task.name)
                && Objects.equals(specification, task.specification)
                && status == task.status
                && Objects.equals(startTime, task.startTime)
                && Objects.equals(duration, task.duration)
                && Objects.equals(endTime, task.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, id, specification, status, startTime, duration, endTime);
    }

    @Override
    public String toString() {
        return "Task{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", specification='" + specification + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public int compareTo(Task o) {
        if (this.getStartTime() == null && o.getStartTime() == null) {
            return 0;
        }
        if (o.getStartTime() == null) {
            return -1;
        }
        if(this.getStartTime() == null) {
            return 1;
        }
        return this.getStartTime().compareTo(o.getStartTime());
    }
}
