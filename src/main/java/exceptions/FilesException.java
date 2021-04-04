package exceptions;

public class FilesException extends Exception {
    public FilesException() {
    }

    public FilesException(String message) {
        super(message);
    }

    public FilesException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilesException(Throwable cause) {
        super(cause);
    }
}
