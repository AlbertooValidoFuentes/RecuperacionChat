package shared;

import java.io.Serializable;
import java.time.LocalTime;

public class Info implements Serializable {
    private String name;
    private String message;
    private LocalTime time;

    public Info(String name, String message, LocalTime time) {
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public Info(String message) {
        this.message = message;
    }

    public Info() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return time + " " + name + ": " + message  ;
    }

  
}
