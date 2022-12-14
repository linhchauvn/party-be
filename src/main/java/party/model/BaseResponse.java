package party.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseResponse {
    @JsonIgnore
    private int httpStatus;
    private String message;

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
