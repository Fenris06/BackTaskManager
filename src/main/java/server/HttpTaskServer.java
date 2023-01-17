package server;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import converter.CSVTaskConverter;
import manager.Manager;
import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Task;
import tasks.SubTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    private TaskManager taskManager;
    private static final int PORT = 8080;
    private final HttpServer taskServer;
    private static Gson gson;

    public HttpTaskServer(TaskManager manager) throws IOException, URISyntaxException, InterruptedException {
        gson = new Gson(); // пока так
        this.taskManager = manager;
        this.taskServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        taskServer.createContext("/tasks/task", this::tasksHandler);
        taskServer.createContext("/tasks/epic", this::epicHandler);
        taskServer.createContext("/tasks/subtask", this::subTaskHandler);
        taskServer.createContext("/tasks", this::prioritizedAndHistoryHandler);
    }

    private void tasksHandler(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String query = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/task$", path)) {
                        if (taskManager.getTasks() != null) {
                            String response = gson.toJson(taskManager.getTasks());
                            sendText(httpExchange, response);

                        } else {
                            writeServiceResponse(httpExchange, "Task not found", 405);
                        }
                        break;
                    }
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String taskId = query.replaceFirst("id=", "");
                        int id = parseIntId(taskId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getTaskFromId(id) != null) {
                                response = gson.toJson(taskManager.getTaskFromId(id));
                                sendText(httpExchange, response);
                            } else {
                                writeServiceResponse(httpExchange, "Task not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                        break;
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/task$", path)) {
                        InputStream stream = httpExchange.getRequestBody();
                        String stringTask = new String(stream.readAllBytes(), UTF_8);
                        Task task = gson.fromJson(stringTask, Task.class);
                        String response;
                        if (taskManager.getTaskFromId(task.getId()) != null) {
                            taskManager.updateTask(task);
                            response = "Task update";
                            sendText(httpExchange, response);
                            break;
                        } else {
                            taskManager.addTask(task);
                            response = "Task add";
                            sendText(httpExchange, response);
                        }

                    } else {
                        String response = "Id has not add " + requestMethod;
                        writeServiceResponse(httpExchange, response, 405);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/task$", path)) {
                        taskManager.clearAllTAsks();
                        sendText(httpExchange, "All tasks delete");
                        break;
                    }
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String taskId = query.replaceFirst("id=", "");
                        int id = parseIntId(taskId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getTaskFromId(id) != null) {
                                taskManager.deleteTask(id);
                                sendText(httpExchange, "Task delete");
                            } else {
                                writeServiceResponse(httpExchange, "Task not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                    }
                    break;
                }
                default:
                    String response = "This request is not supported " + requestMethod;
                    writeServiceResponse(httpExchange, response, 405);

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private void epicHandler(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String query = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        if (taskManager.getEpics() != null) {
                            String response = gson.toJson(taskManager.getEpics());
                            sendText(httpExchange, response);

                        } else {
                            writeServiceResponse(httpExchange, "Epic not found", 405);
                        }
                        break;
                    }
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String taskId = query.replaceFirst("id=", "");
                        int id = parseIntId(taskId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getEpicFromId(id) != null) {
                                response = gson.toJson(taskManager.getEpicFromId(id));
                                sendText(httpExchange, response);
                            } else {
                                writeServiceResponse(httpExchange, "Epic not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                        break;
                    }

                }
                case "POST": {
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        InputStream stream = httpExchange.getRequestBody();
                        String stringEpic = new String(stream.readAllBytes(), UTF_8);
                        Epic epic = gson.fromJson(stringEpic, Epic.class);
                        String response;
                        if (taskManager.getEpicFromId(epic.getId()) != null) {
                            taskManager.updateEpic(epic);
                            response = "Epic update";
                            sendText(httpExchange, response);
                            break;
                        } else {
                            taskManager.addEpic(epic);
                            response = "Epic add";
                            sendText(httpExchange, response);
                        }
                    } else {
                        String response = "Id has not add " + requestMethod;
                        writeServiceResponse(httpExchange, response, 405);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        taskManager.clearAllEpics();
                        sendText(httpExchange, "All tasks delete");
                        break;
                    }
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String epicId = query.replaceFirst("id=", "");
                        int id = parseIntId(epicId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getEpicFromId(id) != null) {
                                taskManager.deleteEpic(id);
                                sendText(httpExchange, "Epic delete");
                            } else {
                                writeServiceResponse(httpExchange, "Task not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                    }
                    break;
                }
                default:
                    String response = "This request is not supported " + requestMethod;
                    writeServiceResponse(httpExchange, response, 405);

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private void subTaskHandler(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String query = httpExchange.getRequestURI().getQuery();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        if (taskManager.getSubTask() != null) {
                            String response = gson.toJson(taskManager.getSubTask());
                            sendText(httpExchange, response);

                        } else {
                            writeServiceResponse(httpExchange, "SubTask not found", 405);
                        }
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask/epic/$", path)) {
                        String subTaskId = query.replaceFirst("id=", "");
                        int id = parseIntId(subTaskId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getEpicFromId(id) != null) {
                                response = gson.toJson(taskManager.epicSubTaskList(id));
                                sendText(httpExchange, response);
                            } else {
                                writeServiceResponse(httpExchange, "Epic not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                        break;
                    }
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String subTaskId = query.replaceFirst("id=", "");
                        int id = parseIntId(subTaskId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getSubTaskId(id) != null) {
                                response = gson.toJson(taskManager.getSubTaskId(id));
                                sendText(httpExchange, response);
                            } else {
                                writeServiceResponse(httpExchange, "Subtask not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                        break;
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        InputStream stream = httpExchange.getRequestBody();
                        String stringSubTask = new String(stream.readAllBytes(), UTF_8);
                        SubTask subTask = gson.fromJson(stringSubTask, SubTask.class);
                        String response;
                        if (taskManager.getSubTaskId(subTask.getId()) != null) {
                            taskManager.updateSubTask(subTask);
                            response = "Subtask update";
                            sendText(httpExchange, response);
                            break;
                        } else {
                            taskManager.addSubtask(subTask);
                            response = "Subtask add";
                            sendText(httpExchange, response);
                        }
                    } else {
                        String response = "Id has not add " + requestMethod;
                        writeServiceResponse(httpExchange, response, 405);
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        taskManager.clearAllSubTasks();
                        sendText(httpExchange, "All tasks delete");
                        break;
                    }
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String subTaskId = query.replaceFirst("id=", "");
                        int id = parseIntId(subTaskId);
                        String response;
                        if (id != -1) {
                            if (taskManager.getSubTaskId(id) != null) {
                                taskManager.deleteSubTask(id);
                                sendText(httpExchange, "Epic delete");
                            } else {
                                writeServiceResponse(httpExchange, "Subtask not found", 405);
                            }
                        } else {
                            response = "Id has not  " + requestMethod;
                            writeServiceResponse(httpExchange, response, 405);
                        }
                    }
                    break;
                }
                default:
                    String response = "This request is not supported " + requestMethod;
                    writeServiceResponse(httpExchange, response, 405);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private void prioritizedAndHistoryHandler(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks/history$", path)) {
                        if (taskManager.getHistory().isEmpty()) {
                            writeServiceResponse(httpExchange, "Tasks not add", 405);
                        } else {
                            String response = gson.toJson(taskManager.getHistory());
                            sendText(httpExchange, response);
                            break;
                        }
                    } else {
                        if (taskManager.getPrioritizedTasks().isEmpty()) {
                            writeServiceResponse(httpExchange, "Tasks not add", 405);
                        } else {
                            String response = gson.toJson(taskManager.getPrioritizedTasks());
                            sendText(httpExchange, response);
                            break;
                        }
                    }
                }
                default:
                    String response = "This request is not supported " + requestMethod;
                    writeServiceResponse(httpExchange, response, 405);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }
    }

    private int parseIntId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        taskServer.start();
    }

    public void stop() {
        taskServer.stop(0);
        System.out.println("Server stop");
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    private void writeServiceResponse(HttpExchange exchange,
                                      String responseString,
                                      int responseCode) throws IOException {
        if (responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(UTF_8);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
    }


}
