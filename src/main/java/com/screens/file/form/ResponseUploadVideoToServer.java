package com.screens.file.form;

import java.io.Serializable;

public class ResponseUploadVideoToServer implements Serializable {
    private String eventId;

    public ResponseUploadVideoToServer() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
