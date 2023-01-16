package manager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Manager {
    public static TaskManager getDefault() throws URISyntaxException, IOException, InterruptedException {
        return new HttpTaskManager("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileTaskManager(String fileName) {
        File file = new File(fileName);

        return FileBackedTasksManager.loadFromFile(file);
    }
}