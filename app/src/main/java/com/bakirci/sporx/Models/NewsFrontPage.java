package com.bakirci.sporx.Models;

/**
 * Created by Mehmet on 23-3-2016.
 */
public class NewsFrontPage implements INews {
    private int id;
    private String title;
    private String headline;
    private String imageUrl;
    private int comment_size;

    public NewsFrontPage(int id) {
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

    public int getComment_size() {
        return comment_size;
    }

    public void setComment_size(int comment_size) {
        this.comment_size = comment_size;
    }

}
