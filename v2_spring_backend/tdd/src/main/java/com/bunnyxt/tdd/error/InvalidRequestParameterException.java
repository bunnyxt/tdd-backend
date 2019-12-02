package com.bunnyxt.tdd.error;

public class InvalidRequestParameterException extends RuntimeException {

    private String parameter;
    private Object value;
    private String prompt;

    public InvalidRequestParameterException() {
        super();
    }

    public InvalidRequestParameterException(String parameter, Object value, String prompt) {
        super();
        this.parameter = parameter;
        this.value = value;
        this.prompt = prompt;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
