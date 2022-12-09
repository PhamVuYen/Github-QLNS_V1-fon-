package com.example.qlnv.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
@Entity(tableName = "task")
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = false)
    @NotNull
    private String task_id;
    private String user_id, task_name, task_status,manage_id;
    private Date createDay, deadline;
    public Task() {
    }

    public Task(String user_id, @NotNull String task_id, String task_name, String task_status, String manage_id, Date createDay, Date deadline) {
        this.user_id = user_id;
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_status = task_status;
        this.manage_id = manage_id;
        this.createDay = createDay;
        this.deadline = deadline;
    }

    public String getManage_id() {
        return manage_id;
    }

    public void setManage_id(String manage_id) {
        this.manage_id = manage_id;
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
