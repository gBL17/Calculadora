package com.example.calculadora;

public enum Operators {
    SUM ("+"),
    SUBTRACTION ("-"),
    MULTIPLY("x"),
    DIVISION("÷");

    private String operator;
    Operators (String operator){
        this.operator = operator;
    }
    public String getOperator() {
        return operator;
    }
}
