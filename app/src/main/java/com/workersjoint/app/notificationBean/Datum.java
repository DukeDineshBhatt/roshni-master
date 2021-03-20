package com.workersjoint.app.notificationBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("type_id")
    @Expose
    private String type_id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("posted")
    @Expose
    private String posted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public String getPosted() {
        return posted;
    }

    public String getSalary() {
        return salary;
    }

    public String getTitle() {
        return title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPosted(String posted) {
        this.posted = posted;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
