package com.dyibing.myapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WelfareBean {


    /**
     * welfareRankList : {"data":[{"id":19,"userId":"YY123456","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":20,"haveForestCoin":150,"likesCount":null},{"id":20,"userId":"YY123468","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":21,"userId":"YY123461","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":22,"userId":"YY123457","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":23,"userId":"YY123458","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":24,"userId":"YY123465","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":25,"userId":"YY123466","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":26,"userId":"YY123462","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":27,"userId":"YY123463","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":28,"userId":"YY123459","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null}],"pageNo":1,"pageSize":10,"hasNext":true}
     * currentWelfare : {"publicWelfareName":"test_publicWelfareName","forestCoin":20,"haveForestCoin":12,"donationForestCoin":12}
     */

    private WelfareRankListBean welfareRankList;
    private CurrentWelfareBean currentWelfare;

    public WelfareRankListBean getWelfareRankList() {
        return welfareRankList;
    }

    public void setWelfareRankList(WelfareRankListBean welfareRankList) {
        this.welfareRankList = welfareRankList;
    }

    public CurrentWelfareBean getCurrentWelfare() {
        return currentWelfare;
    }

    public void setCurrentWelfare(CurrentWelfareBean currentWelfare) {
        this.currentWelfare = currentWelfare;
    }

    public static class WelfareRankListBean {
        /**
         * data : [{"id":19,"userId":"YY123456","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":20,"haveForestCoin":150,"likesCount":null},{"id":20,"userId":"YY123468","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":21,"userId":"YY123461","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":22,"userId":"YY123457","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":23,"userId":"YY123458","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":24,"userId":"YY123465","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":25,"userId":"YY123466","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":26,"userId":"YY123462","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":27,"userId":"YY123463","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null},{"id":28,"userId":"YY123459","nickName":"nickName","publicWelfareId":8,"publicWelfareName":"公益名称修改名称888","publicWelfareUrl":"http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png","donationForestCoinCount":10,"haveForestCoin":150,"likesCount":null}]
         * pageNo : 1
         * pageSize : 10
         * hasNext : true
         */

        private int pageNo;
        private int pageSize;
        private boolean hasNext;
        private List<DataBean> data;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 19
             * userId : YY123456
             * nickName : nickName
             * publicWelfareId : 8
             * publicWelfareName : 公益名称修改名称888
             * publicWelfareUrl : http://q8o2kwhfh.bkt.clouddn.com//chunfen6.png
             * donationForestCoinCount : 20
             * haveForestCoin : 150
             * likesCount : null
             */

            private int id;
            private String userId;
            private String nickName;
            private int publicWelfareId;
            private String publicWelfareName;
            private String publicWelfareUrl;
            private int donationForestCoinCount;
            private int haveForestCoin;
            private int likesCount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getPublicWelfareId() {
                return publicWelfareId;
            }

            public void setPublicWelfareId(int publicWelfareId) {
                this.publicWelfareId = publicWelfareId;
            }

            public String getPublicWelfareName() {
                return publicWelfareName;
            }

            public void setPublicWelfareName(String publicWelfareName) {
                this.publicWelfareName = publicWelfareName;
            }

            public String getPublicWelfareUrl() {
                return publicWelfareUrl;
            }

            public void setPublicWelfareUrl(String publicWelfareUrl) {
                this.publicWelfareUrl = publicWelfareUrl;
            }

            public int getDonationForestCoinCount() {
                return donationForestCoinCount;
            }

            public void setDonationForestCoinCount(int donationForestCoinCount) {
                this.donationForestCoinCount = donationForestCoinCount;
            }

            public int getHaveForestCoin() {
                return haveForestCoin;
            }

            public void setHaveForestCoin(int haveForestCoin) {
                this.haveForestCoin = haveForestCoin;
            }

            public int getLikesCount() {
                return likesCount;
            }

            public void setLikesCount(int likesCount) {
                this.likesCount = likesCount;
            }
        }
    }

    public static class CurrentWelfareBean {
        /**
         * publicWelfareName : test_publicWelfareName
         * forestCoin : 20
         * haveForestCoin : 12
         * donationForestCoin : 12
         */

        private int publicWelfareId;
        private String publicWelfareName;
        private int forestCoin;
        private int haveForestCoin;
        private int donationForestCoin;
        private String publicWelfareContent;//公益内容
        private String publicWelfareUrl;//公益图片地址
        private String relatedArticles;//关联文章信息

        public String getRelatedArticles() {
            return relatedArticles;
        }

        public void setRelatedArticles(String relatedArticles) {
            this.relatedArticles = relatedArticles;
        }

        public String getPublicWelfareContent() {
            return publicWelfareContent;
        }

        public void setPublicWelfareContent(String publicWelfareContent) {
            this.publicWelfareContent = publicWelfareContent;
        }

        public String getPublicWelfareUrl() {
            return publicWelfareUrl;
        }

        public void setPublicWelfareUrl(String publicWelfareUrl) {
            this.publicWelfareUrl = publicWelfareUrl;
        }

        /**
         * publicWelfareId : 45
         * haveForestCoin : null
         * donationForestCoin : null
         */



        public String getPublicWelfareName() {
            return publicWelfareName;
        }

        public void setPublicWelfareName(String publicWelfareName) {
            this.publicWelfareName = publicWelfareName;
        }

        public int getForestCoin() {
            return forestCoin;
        }

        public void setForestCoin(int forestCoin) {
            this.forestCoin = forestCoin;
        }

        public int getHaveForestCoin() {
            return haveForestCoin;
        }

        public void setHaveForestCoin(int haveForestCoin) {
            this.haveForestCoin = haveForestCoin;
        }

        public int getDonationForestCoin() {
            return donationForestCoin;
        }

        public void setDonationForestCoin(int donationForestCoin) {
            this.donationForestCoin = donationForestCoin;
        }

        public int getPublicWelfareId() {
            return publicWelfareId;
        }

        public void setPublicWelfareId(int publicWelfareId) {
            this.publicWelfareId = publicWelfareId;
        }
    }
}
