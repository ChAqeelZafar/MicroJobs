package com.AZDeveloper.microjobs.models;

public class Task {
    private String taskId, taskTitle, taskDiscription, taskTillDate;
    private int totalRequired, completedTask, pendingTask, coinsPerTask;

    public Task(String taskId, String taskTitle, String taskDiscription, String taskTillDate, int totalRequired, int completedTask, int pendingTask, int coinsPerTask) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDiscription = taskDiscription;
        this.taskTillDate = taskTillDate;
        this.totalRequired = totalRequired;
        this.completedTask = completedTask;
        this.pendingTask = pendingTask;
        this.coinsPerTask = coinsPerTask;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDiscription() {
        return taskDiscription;
    }

    public void setTaskDiscription(String taskDiscription) {
        this.taskDiscription = taskDiscription;
    }

    public String getTaskTillDate() {
        return taskTillDate;
    }

    public void setTaskTillDate(String taskTillDate) {
        this.taskTillDate = taskTillDate;
    }

    public int getTotalRequired() {
        return totalRequired;
    }

    public void setTotalRequired(int totalRequired) {
        this.totalRequired = totalRequired;
    }

    public int getCompletedTask() {
        return completedTask;
    }

    public void setCompletedTask(int completedTask) {
        this.completedTask = completedTask;
    }

    public int getPendingTask() {
        return pendingTask;
    }

    public void setPendingTask(int pendingTask) {
        this.pendingTask = pendingTask;
    }

    public int getCoinsPerTask() {
        return coinsPerTask;
    }

    public void setCoinsPerTask(int coinsPerTask) {
        this.coinsPerTask = coinsPerTask;
    }
}
