package project.shop.exception;

public class NoEnoughStockException extends RuntimeException{

    public NoEnoughStockException() {
    }

    public NoEnoughStockException(String message) {
        super(message);
    }

    public NoEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoEnoughStockException(Throwable cause) {
        super(cause);
    }

    public NoEnoughStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
