package com.example.administrator.myapplication.news;

import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class RespNewsCate {

    /**
     * data : [{"id":1,"name":"美文"},{"id":2,"name":"情感"},{"id":3,"name":"健康"},{"id":4,"name":"育儿"},{"id":5,"name":"美食"},{"id":6,"name":"居家"},{"id":7,"name":"时尚"},{"id":8,"name":"旅行"},{"id":9,"name":"暴笑"},{"id":10,"name":"娱乐"},{"id":11,"name":"萌宠"},{"id":12,"name":"风水"},{"id":13,"name":"饮食养生"},{"id":14,"name":"减肥"},{"id":15,"name":"人文"},{"id":16,"name":"历史"},{"id":17,"name":"新闻"},{"id":18,"name":"科技"},{"id":19,"name":"体育"},{"id":20,"name":"命里"},{"id":21,"name":"星座"},{"id":22,"name":"生肖"},{"id":23,"name":"小窍门"},{"id":24,"name":"推荐"}]
     * code : 1
     * info : 查询成功
     */

    private int code;
    private String info;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 美文
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
