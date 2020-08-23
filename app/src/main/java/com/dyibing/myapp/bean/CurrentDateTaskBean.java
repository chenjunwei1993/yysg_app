package com.dyibing.myapp.bean;

import java.util.List;

public class CurrentDateTaskBean {

    /**
     * yesterdayTask : {"userId":"YY123456","currentDate":"7月7日","currentDateWeek":"tuesday","taskList":[]}
     * todayTask : {"userId":null,"currentDate":"7月8日","currentDateWeek":"wednesday","taskList":[{"logTaskId":6,"userId":"YY123456","taskName":"测试任务名称","taskFinishCount":1,"taskRepeartCount":20,"taskFinishStatus":"FINISHING","taskReward":null}]}
     */

    private TaskBean yesterdayTask;
    private TaskBean todayTask;

    public TaskBean getYesterdayTask() {
        return yesterdayTask;
    }

    public void setYesterdayTask(TaskBean yesterdayTask) {
        this.yesterdayTask = yesterdayTask;
    }

    public TaskBean getTodayTask() {
        return todayTask;
    }

    public void setTodayTask(TaskBean todayTask) {
        this.todayTask = todayTask;
    }

    public static class TaskBean {
        /**
         * userId : null
         * currentDate : 7月8日
         * currentDateWeek : wednesday
         * taskList : [{"logTaskId":6,"userId":"YY123456","taskName":"测试任务名称","taskFinishCount":1,"taskRepeartCount":20,"taskFinishStatus":"FINISHING","taskReward":null}]
         */

        private Object userId;
        private String currentDate;
        private String currentDateWeek;
        private List<TaskListBean> taskList;

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public String getCurrentDate() {
            return currentDate;
        }

        public void setCurrentDate(String currentDate) {
            this.currentDate = currentDate;
        }

        public String getCurrentDateWeek() {
            return currentDateWeek;
        }

        public void setCurrentDateWeek(String currentDateWeek) {
            this.currentDateWeek = currentDateWeek;
        }

        public List<TaskListBean> getTaskList() {
            return taskList;
        }

        public void setTaskList(List<TaskListBean> taskList) {
            this.taskList = taskList;
        }

        public static class TaskListBean {
            /**
             * logTaskId : 6
             * userId : YY123456
             * taskName : 测试任务名称
             * taskFinishCount : 1
             * taskRepeartCount : 20
             * taskFinishStatus : FINISHING
             * taskReward : null
             */

            private int logTaskId;
            private String userId;
            private String taskName;
            private int taskFinishCount;
            private int taskRepeartCount;
            private String taskFinishStatus;
            private String taskReward;

            public int getLogTaskId() {
                return logTaskId;
            }

            public void setLogTaskId(int logTaskId) {
                this.logTaskId = logTaskId;
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

            public int getTaskFinishCount() {
                return taskFinishCount;
            }

            public void setTaskFinishCount(int taskFinishCount) {
                this.taskFinishCount = taskFinishCount;
            }

            public int getTaskRepeartCount() {
                return taskRepeartCount;
            }

            public void setTaskRepeartCount(int taskRepeartCount) {
                this.taskRepeartCount = taskRepeartCount;
            }

            public String getTaskFinishStatus() {
                return taskFinishStatus;
            }

            public void setTaskFinishStatus(String taskFinishStatus) {
                this.taskFinishStatus = taskFinishStatus;
            }

            public String getTaskReward() {
                return taskReward;
            }

            public void setTaskReward(String  taskReward) {
                this.taskReward = taskReward;
            }
        }
    }
}
