package tasks;

import java.util.Objects;

public class Task {
    protected TaskType getType;
    protected String name;
    protected int id;
    protected String specification;
    protected Status status;

    public Task(String name, int Id, String specification, Status status) {
        this.name = name;
        this.id = Id;
        this.specification = specification;
        this.status = status;
        getType = TaskType.TASK;
    }

    public Integer getEpicId() {
        return null;
    }

    public TaskType getGetType() {
        return getType;
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
        return id == task.id && Objects.equals(name, task.name)
                && Objects.equals(specification, task.specification)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, specification, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", taskId=" + id +
                ", specification='" + specification + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
