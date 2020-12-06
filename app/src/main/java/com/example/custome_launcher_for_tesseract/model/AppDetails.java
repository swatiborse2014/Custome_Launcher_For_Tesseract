package com.example.custome_launcher_for_tesseract.model;

import android.graphics.drawable.Drawable;

public class AppDetails {
    public String packageName;
    public String appName;
    public String versionCode;
    public String versionName;
    public String MainActivityName;
    public Drawable icon;

    public AppDetails(String appName) {
        this.appName = appName;
    }

    public AppDetails() {

    }

    public AppDetails(String appName, String packageName) {
        this.appName = appName;
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "AppDetails{" +
                "packageName='" + packageName + '\'' +
                ", appName='" + appName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", MainActivityName='" + MainActivityName + '\'' +
                ", icon=" + icon +
                '}';
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getMainActivityName() {
        return MainActivityName;
    }

    public void setMainActivityName(String mainActivityName) {
        MainActivityName = mainActivityName;
    }
}
