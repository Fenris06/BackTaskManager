package manager;

import converter.CSVTaskConverter;
import exception.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;


    public FileBackedTasksManager(File file) {
        super();
        this.file = file;
    }

    public FileBackedTasksManager() {
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);// Создать через конструктор FileBackedTasksManager
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new ManagerSaveException("can not create file", e);
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            List<String> reader = new ArrayList<>();
            while (fileReader.ready()) {
                reader.add(fileReader.readLine());
            }
            int newGeneratorId = 0;
            List<Integer> historyId = new ArrayList<>();
            for (int i = 1; i < reader.size(); i++) {
                if (reader.get(i).isEmpty()) {
                    continue;
                } else if (i == reader.size() - 1 && !reader.get(reader.size() - 1).isEmpty()) {
                    for (Integer id : CSVTaskConverter.historyFromString(reader.get(i))) {
                        historyId.add(id);
                    }
                } else {
                    Task task = CSVTaskConverter.fromString(reader.get(i));

                    if (task.getType().equals(TaskType.TASK)) {
                        if (task.getId() > newGeneratorId)
                            newGeneratorId = task.getId();
                        fileBackedTasksManager.tasks.put(task.getId(), task);
                    }
                    if (task.getType().equals(TaskType.EPIC)) {
                        if (task.getId() > newGeneratorId)
                            newGeneratorId = task.getId();
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    }
                    if (task.getType().equals(TaskType.SUBTASK)) {
                        if (task.getId() > newGeneratorId)
                            newGeneratorId = task.getId();
                        fileBackedTasksManager.subTasks.put(task.getId(), (SubTask) task);
                    }
                }
                fileBackedTasksManager.generationId = newGeneratorId + 1;
            }
            for (SubTask subTask : fileBackedTasksManager.subTasks.values()) {
                Integer epicSubTaskId = subTask.getEpicId();
                for (Integer epicId : fileBackedTasksManager.epics.keySet()) {
                    Epic epic = fileBackedTasksManager.epics.get(epicId);
                    if (epicSubTaskId.equals(epicId)) {
                        epic.addSubtaskId(subTask.getId());
                    }
                }
            }
            for (Integer id : historyId) {
                if(fileBackedTasksManager.tasks.containsKey(id)) {
                    fileBackedTasksManager.historyManager.add(fileBackedTasksManager.tasks.get(id));
                } else if (fileBackedTasksManager.epics.containsKey(id)) {
                    fileBackedTasksManager.historyManager.add(fileBackedTasksManager.epics.get(id));
                } else if (fileBackedTasksManager.subTasks.containsKey(id)) {
                    fileBackedTasksManager.historyManager.add(fileBackedTasksManager.subTasks.get(id));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileBackedTasksManager;
    }

    // Обернем все в try, пы таясь поймать IOException
    // Прочитать из файла содержимое

    //int generatorId = 0;
    // В цикле проходим все строки
    // Десереализуем таски из строки, например если получили Task task = ...
    // Если task.id > generatorId, то generatorId = task.id
    // Если мы наткнулись на пустую строку, то это - история, тогда парсим ее

    // Привязать сабтаски и эпики
    // Проходимся по сабтаскам и связываем с эпиком

    // дообработать историю
    // Пройти список айдишников из десериализованной истории и подобовлять в историю с помощью
    // уже существующего метода HistoryManager.add()

    // Не забываем привязать новый generatorId

//
//
//        }
//
//


    public void save() {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(CSVTaskConverter.getHeader());
            writer.write(System.lineSeparator());
            // writeTasks(tasks.values(),writer);
            for (Task task : tasks.values()) {
                writer.write(CSVTaskConverter.toString(task));
                writer.write(System.lineSeparator());
            }
            for (Task task : epics.values()) {
                writer.write(CSVTaskConverter.toString(task));
                writer.write(System.lineSeparator());
            }
            for (Task task : subTasks.values()) {
                writer.write(CSVTaskConverter.toString(task));
                writer.write(System.lineSeparator());
            }
            writer.write(System.lineSeparator());
            writer.write(CSVTaskConverter.historyToString(historyManager));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void writeTasks(Iterable<Task> tasks, FileWriter writer) throws IOException {
//        for (Task task : tasks) {
//            writer.write(CSVTaskConverter.toString(task));
//            writer.write(System.lineSeparator());
//        }
//    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public Task getTaskFromId(Integer id) {
        Task taskFromId = super.getTaskFromId(id);
        save();
        return taskFromId;
    }

    @Override
    public void clearAllTAsks() {
        super.clearAllTAsks();
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public Epic getEpicFromId(int id) {
        Epic epic = super.getEpicFromId(id);
        save();
        return epic;
    }

    @Override
    public void clearAllEpics() {
        super.clearAllEpics();
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void addSubtask(SubTask subTask) {
        super.addSubtask(subTask);
        save();
    }

    @Override
    public SubTask getSubTaskId(int id) {
        SubTask subTask = super.getSubTaskId(id);
        save();
        return subTask;
    }

    @Override
    public void clearAllSubTasks() {
        super.clearAllSubTasks();
        save();
    }

    @Override
    public void deleteSubTask(Integer id) {
        super.deleteSubTask(id);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }
}