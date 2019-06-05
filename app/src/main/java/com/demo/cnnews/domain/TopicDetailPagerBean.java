package com.demo.cnnews.domain;

import java.util.List;

public class TopicDetailPagerBean {

    private int retcode;

    private DataEntity data;

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public DataEntity getData() {
        return data;
    }


    public static class DataEntity{
        private List<TopicEntity> topic;
        private String more;

        public void setTopic(List<TopicEntity> topic) {
            this.topic = topic;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public List<TopicEntity> getTopic() {
            return topic;
        }

        public String getMore() {
            return more;
        }


        //topic实体
        public static class TopicEntity{
            private int id;
            private String title;
            private String url;
            private String listimage;
            private String description;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getUrl() {
                return url;
            }

            public String getListimage() {
                return listimage;
            }

            public String getDescription() {
                return description;
            }


        }


    }



}
