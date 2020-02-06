package com.opencode.managment.app;


public class Response {
    private String answer;
    private String status;

    public Response(String answer) {
        this.answer = answer;
        status = "GAME";
    }

    public Response() {
    }

    public Response(String answer, String status) {
        this.answer = answer;
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
