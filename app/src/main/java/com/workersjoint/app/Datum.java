package com.workersjoint.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("cpin")
    @Expose
    private String cpin;

    @SerializedName("cstate")
    @Expose
    private String cstate;

    @SerializedName("cdistrict")
    @Expose
    private String cdistrict;

    @SerializedName("carea")
    @Expose
    private String carea;

    @SerializedName("cstreet")
    @Expose
    private String cstreet;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("profile_id")
    @Expose
    private String profile_id;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCpin() {
        return cpin;
    }

    public void setCpin(String cpin) {
        this.cpin = cpin;
    }

    public String getCstate() {
        return cstate;
    }

    public void setCstate(String cstate) {
        this.cstate = cstate;
    }

    public String getCdistrict() {
        return cdistrict;
    }

    public void setCdistrict(String cdistrict) {
        this.cdistrict = cdistrict;
    }

    public String getCarea() {
        return carea;
    }

    public void setCarea(String carea) {
        this.carea = carea;
    }

    public String getCstreet() {
        return cstreet;
    }

    public void setCstreet(String cstreet) {
        this.cstreet = cstreet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(String profile_id) {
        this.profile_id = profile_id;
    }
}
