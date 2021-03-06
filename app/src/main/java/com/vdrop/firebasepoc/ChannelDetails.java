package com.vdrop.firebasepoc;

import com.google.gson.annotations.SerializedName;

public class ChannelDetails {

    @SerializedName("bannerImage")
    private String bannerImage;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChannelDetails() {

    }

    public ChannelDetails(String bannerImage, String description, String name) {
        this.bannerImage = bannerImage;
        this.description = description;
        this.name = name;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
