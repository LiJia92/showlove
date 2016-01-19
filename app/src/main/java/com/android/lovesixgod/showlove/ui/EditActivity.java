package com.android.lovesixgod.showlove.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.android.lovesixgod.showlove.R;

/**
 * Created by Lijia on 2016-01-19.
 */
public class EditActivity extends Activity implements View.OnClickListener {

//    private EditText editZone;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FloatingActionButton addPic = (FloatingActionButton) findViewById(R.id.add_pic);
//        editZone = (EditText) findViewById(R.id.edit_zone);
        imageView = (ImageView) findViewById(R.id.image);
        addPic.setOnClickListener(this);
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
            Uri originalUri = data.getData();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(originalUri.getPath(), options);
            int height = options.outHeight;
            int width = options.outWidth;
            int inSampleSize = 1;
            int reqHeight = 800;
            int reqWidth = 480;
            if (height > reqHeight || width > reqWidth) {
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            options.inJustDecodeBounds = false;
            options.inSampleSize = inSampleSize;
            Bitmap bitmap = BitmapFactory.decodeFile(originalUri.getPath(), options);
            imageView.setImageBitmap(bitmap);
        }
    }
}
