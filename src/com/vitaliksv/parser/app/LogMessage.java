package com.vitaliksv.parser.app;


public class LogMessage {

    private String time;
    private String type;
    private String text;

    public LogMessage(String time, String type, String text) {
        this.time = time;
        this.type = type;
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
