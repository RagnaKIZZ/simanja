package com.hayya5k.simanja.Dashboard.MenuDashboard;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class MenuDashboardModel {

    private String title;
    private Drawable image;
    private String info;
    private String proses;
    private Class aClass;
    private String status;

    public MenuDashboardModel(String title, Drawable image, String info, String proses, Class aClass, String status) {
        this.title = title;
        this.image = image;
        this.info = info;
        this.proses = proses;
        this.aClass = aClass;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public MenuDashboardModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public Drawable getImage() {
        return image;
    }

    public MenuDashboardModel setImage(Drawable image) {
        this.image = image;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public MenuDashboardModel setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getProses() {
        return proses;
    }

    public MenuDashboardModel setProses(String proses) {
        this.proses = proses;
        return this;
    }

    public Class getaClass() {
        return aClass;
    }

    public MenuDashboardModel setaClass(Class aClass) {
        this.aClass = aClass;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MenuDashboardModel setStatus(String status) {
        this.status = status;
        return this;
    }
}
