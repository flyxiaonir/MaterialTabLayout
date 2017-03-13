package com.example.administrator.myapplication.news;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻（热点）数据解析类
 * Created by Administrator on 2017/3/13.
 */

public class NewsDataParser {
    //    private JSONObject mJsonBean;
    private JSONObject mPageData;

    private List<ItemNewsHot> itemList;//新闻列表数据

    public NewsDataParser(String raw) {
        try {
            JSONObject dataBean = new JSONObject(raw);
            JSONObject layer1 = dataBean.optJSONObject("data");//数据（int code,String info,jsonObject data）
            JSONObject layer2 = layer1.optJSONObject("data");//数据（int total,int per_page,int current_page,int pagecount,jsonArray data）
            JSONArray itemListBean = layer2.optJSONArray("data");//(String title,String description,String detailurl,jsonArray images)
            itemList = new ArrayList<>();
            for (int i = 0; i < itemListBean.length(); i++) {
                JSONObject itemObject = itemListBean.getJSONObject(i);
                ItemNewsHot item = new ItemNewsHot();
                item.setImages(new ArrayList<String>());
                item.setTitle(TextUtils.isEmpty(itemObject.optString("title")) ? "" : itemObject.optString("title"));
                item.setDescription(TextUtils.isEmpty(itemObject.optString("description")) ? "" : itemObject.optString("description"));
                item.setDetailurl(TextUtils.isEmpty(itemObject.optString("detailurl")) ? "" : itemObject.optString("detailurl"));

                //获取item图片列表
                JSONArray imageArray = itemObject.optJSONArray("images");
                if (imageArray.length() > 0) {
                    JSONObject imagesObj = imageArray.optJSONObject(0);
                    if (imagesObj != null) {

                        Log.d("NewsDataParser", "列表中放的是对象");
                        for (int j = 1; j <Integer.MAX_VALUE ; j++) {
                            String tempImage =imagesObj.optString("icon_small"+j);
                            if (TextUtils.isEmpty(tempImage)){
                                break;
                            }else{
                                item.getImages().add(tempImage);
                            }

                        }
                        item.setItemType((item.getImages().size())>1?2:1);//设置布局类型
                    } else {
                        Log.d("NewsDataParser", "列表为数组");
                        for (int j = 0; j <imageArray.length() ; j++) {
                            item.getImages().add(imageArray.optString(i));
                        }
                            item.setItemType((item.getImages().size())>1?2:1);//设置布局类型
                    }
                } else {
                    item.setItemType(0);
                    Log.d("NewsDataParser", "没有图片");
                }


                itemList.add(item);
            }
//            itemListBean.op
            Log.d("lf", "parse to jsonarray");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("NewsDataParser", "新闻热点数据解析失败");
        }
    }

    private class ItemNewsHot {
        private String title;
        private String description;
        private String detailurl;
        private List<String> images;
        private int itemType;//item项类型，供适配选择view布局（0：无图片，1：单张图片 ，2：多张图片）

        @Override
        public String toString() {
            return "ItemNewsHot{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", detailurl='" + detailurl + '\'' +
                    ", images=" + images +
                    ", itemType=" + itemType +
                    '}';
        }

        public int getItemType() {
            return itemType;
        }

        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDetailurl() {
            return detailurl;
        }

        public void setDetailurl(String detailurl) {
            this.detailurl = detailurl;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
