package com.emerson.pricecontrol.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDTO {
    private List<String> messages;

    public ResponseDTO(List<String> messages) {
        this.messages = messages;
    }

    public ResponseDTO(String message) {
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
