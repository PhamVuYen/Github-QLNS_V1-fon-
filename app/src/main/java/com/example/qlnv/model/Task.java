package com.example.qlnv.model;

import java.util.Date;

public class Task {
    private String user_id, task_id, task_name, task_status;
    private Date createDay, deadline;
    public Task() {
    }

    public Task(String user_id, String task_id, String task_name, String task_status, Date createDay, Date deadline) {
        this.user_id = user_id;
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_status = task_status;
        this.createDay = createDay;
        this.deadline = deadline;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
