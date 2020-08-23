package com.dyibing.myapp.bean;

public class WaitingCashTaskBean {

    /**
     * taskId : 18
     * userId : oz3L25WqbrrwbBDuRTGmV86Ky3KI
     * taskName : 任务3
     * taskRepeartCount : 1
     * taskStartTime : 2020-07-18 00:00:00
     * taskEndTime : 2020-07-18 00:00:00
     * taskReward : 跑步读书
     * taskStatus : AWAITCASH
     */

    private int taskId;
    private String userId;
    private String taskName;
    private int taskRepeartCount;
    private String taskStartTime;
    private String taskEndTime;
    private String taskReward;
    private String taskStatus;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskRepeartCount() {
        return taskRepeartCount;
    }

    public void setTaskRepeartCount(int taskRepeartCount) {
        this.taskRepeartCount = taskRepeartCount;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(String taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskReward() {
        return taskReward;
    }

    public void setTaskReward(String taskReward) {
        this.taskReward = taskReward;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
