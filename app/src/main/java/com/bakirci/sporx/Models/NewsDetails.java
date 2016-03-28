package com.bakirci.sporx.Models;

import java.util.Date;

/**
 * Created by Mehmet on 24-3-2016.
 */
public class NewsDetails implements INews {

    private int id;
    private String title;
    private String headline;
    private String imageUrl;
    private Date date;
    private String source;
    private String body;
    private String[] category;

    public NewsDetails(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getHeadline() {
        return headline;
    }

    @Override
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }
}
