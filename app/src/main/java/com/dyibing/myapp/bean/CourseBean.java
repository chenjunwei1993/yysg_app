package com.dyibing.myapp.bean;

public class CourseBean {


    /**
     * userId : YY123456
     * currentDate : 6月18日
     * currentDateWeek : thursday
     * currentCourseContent : {"night":"语文411，英语4","am":"语文,数学","pm":"看漫画,学钢琴"}
     * beforeDateWeek : wednesday
     * afterDateWeek : friday
     */

    private String userId;
    private String currentDate;
    private String currentDateWeek;
    private String currentCourseContent;
    private String beforeDateWeek;
    private String afterDateWeek;
    /**
     * yesterdayDate : 2020-07-15 00:00:00
     * todayDate : 2020-07-16 00:00:00
     * tomorrowDate : 2020-07-17 00:00:00
     */

    private String yesterdayDate;
    private String todayDate;
    private String tomorrowDate;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getCurrentCourseContent() {
        return currentCourseContent;
    }

    public void setCurrentCourseContent(String currentCourseContent) {
        this.currentCourseContent = currentCourseContent;
    }

    public String getBeforeDateWeek() {
        return beforeDateWeek;
    }

    public void setBeforeDateWeek(String beforeDateWeek) {
        this.beforeDateWeek = beforeDateWeek;
    }

    public String getAfterDateWeek() {
        return afterDateWeek;
    }

    public void setAfterDateWeek(String afterDateWeek) {
        this.afterDateWeek = afterDateWeek;
    }

    public String getYesterdayDate() {
        return yesterdayDate;
    }

    public void setYesterdayDate(String yesterdayDate) {
        this.yesterdayDate = yesterdayDate;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getTomorrowDate() {
        return tomorrowDate;
    }

    public void setTomorrowDate(String tomorrowDate) {
        this.tomorrowDate = tomorrowDate;
    }

    public class Class_{

        /**
         * night : 语文411，英语4
         * am : 语文,数学
         * pm : 看漫画,学钢琴
         */

        private String night;
        private String am;
        private String pm;

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        public String getAm() {
            return am;
        }

        public void setAm(String am) {
            this.am = am;
        }

        public String getPm() {
            return pm;
        }

        public void setPm(String pm) {
            this.pm = pm;
        }
    }
}
