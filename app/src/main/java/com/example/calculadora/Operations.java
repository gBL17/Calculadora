package com.example.calculadora;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import com.example.calculadora.Operators;

import java.util.Date;

public class Operations extends RealmObject {
    @PrimaryKey private Integer id;
    private Double valueA;
    private Double valueB;
    private String op;
    private Double result;
    private Date date;

    public Operations(Integer id, Double valueA, Double valueB, String operator, Double result,Date date) {
        this.id = id;
        this.valueA = valueA;
        this.valueB = valueB;
        this.op = operator;
        this.result = result;
        this.date = date;
    }

    public Operations() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Number getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Number getValueA() {
        return valueA;
    }

    public void setValueA(Double valueA) {
        this.valueA = valueA;
    }

    public Number getValueB() {
        return valueB;
    }

    public void setValueB(Double valueB) {
        this.valueB = valueB;
    }

    public String getOp() {return op;}

    public void setOp(String operator) {
        this.op = operator;
    }

    public Number getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
