package manager;

import java.io.File;
import java.io.IOException;

public class Manager {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileTaskManager(String fileName) {
        File file = new File(fileName);

        return FileBackedTasksManager.loadFromFile(file);
    }
}
