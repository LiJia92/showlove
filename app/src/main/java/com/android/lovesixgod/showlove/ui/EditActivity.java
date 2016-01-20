package com.android.lovesixgod.showlove.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.lovesixgod.showlove.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Lijia on 2016-01-19.
 */
public class EditActivity extends Activity implements View.OnClickListener {

//    private EditText editZone;

    private ImageView imageView;
    private final static float MAX_WIDTH = 480f;
    private final static float MAX_HEIGHT = 800f;
    private final static float IS_LONG_PICTURE = 5; // 长度是高度的5倍或以上，则认定为长图
    private static int displayWidth;
    private static int displayHeight;
    private static int targetWidth;
    private static int targetHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FloatingActionButton addPic = (FloatingActionButton) findViewById(R.id.add_pic);
//        editZone = (EditText) findViewById(R.id.edit_zone);
        imageView = (ImageView) findViewById(R.id.image);
        addPic.setOnClickListener(this);
        getDisplayMetrics();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            Uri pictureUri = data.getData();

            String picturePath = getPicturePath(pictureUri);

            calculateTargetSize(picturePath);

            // Picasso压缩并加载图片到ImageView
            Picasso.with(this).load(pictureUri).resize(targetWidth, targetHeight).into(imageView);
        }
    }

    /**
     * 根据返回的Uri获取真实路径
     *
     * @param uri
     * @return
     */
    private String getPicturePath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        String picturePath;
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        } else {
            picturePath = uri.getPath();
        }
        return picturePath;
    }

    /**
     * 计算压缩尺寸
     *
     * @param path
     */
    private void calculateTargetSize(String path) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int width = options.outWidth; // 原图宽度
        int height = options.outHeight; //原图高度
        if(height / width > IS_LONG_PICTURE){
            // TODO: 2016-01-20 加载长图 
//            targetWidth = (int) (displayWidth * 0.1);
//            targetHeight = height * targetWidth / width;
//
//            // 设置ImageView尺寸
//            layoutParams.width = targetWidth;
//            layoutParams.height = (int) (displayWidth * 0.8 / targetWidth * targetHeight);
        } else {
            float xSize = width / MAX_WIDTH;
            float ySize = height / MAX_HEIGHT;
            float realSize = Math.max(xSize, ySize);
            targetWidth = (int) (width / realSize); // 目标宽度
            targetHeight = (int) (height / realSize); // 目标高度

            // 设置ImageView尺寸
            layoutParams.width = (int) (displayWidth * 0.8);
            layoutParams.height = (int) (displayWidth * 0.8 / targetWidth * targetHeight);

        }
        imageView.setLayoutParams(layoutParams);
    }

    /**
     * 获取屏幕高宽
     */
    private void getDisplayMetrics() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        displayWidth = metric.widthPixels;     // 屏幕宽度（像素）
        displayHeight = metric.heightPixels;   // 屏幕高度（像素）
    }
}
