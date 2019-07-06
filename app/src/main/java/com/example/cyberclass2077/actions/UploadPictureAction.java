package com.example.cyberclass2077.actions;

import android.graphics.Bitmap;

public class UploadPictureAction extends Action<Bitmap> {
    public static final String ACTION_SEND_PICTURE_PORTRAIT="send_picture_portrait";
    public static final String ACTION_SEND_PICTURE_DYNAMIC="send_picture_dynamic";
    public UploadPictureAction(String type,Bitmap bimap){super(type,bimap);}
}
