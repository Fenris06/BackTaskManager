package exception;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {
        System.out.println("Save Error");
    }
    public ManagerSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
