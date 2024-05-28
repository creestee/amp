package drivus.exception;

public class AppointmentStatusNotFoundException extends RuntimeException {
    public AppointmentStatusNotFoundException(String message) {
        super(message);
    }
}