package com.bphillips91.imagesearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by brendan on 10/5/16.
 */

public class Photo {
    private String farmID;
    private String serverID;
    private String secret;
  //  private String userID;
    private String photoID;
    private String title;

    public Photo(String farmID, String serverID, String photoID, String secret, String title) {
        this.farmID = farmID;
        this.photoID = photoID;
        this.serverID = serverID;
        this.secret = secret;
        this.title = title;
    }

    public String getPhotoID() {
        return photoID;
    }

    public String getFarmID() {
        return farmID;
    }

    public void setFarmID(String farmID) {
        this.farmID = farmID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
