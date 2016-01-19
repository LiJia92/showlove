package com.android.lovesixgod.showlove.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.EditText;

import com.android.lovesixgod.showlove.R;

import java.io.FileNotFoundException;

/**
 * Created by Lijia on 2016-01-19.
 */
public class EditActivity extends Activity implements View.OnClickListener {

    private EditText editZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        FloatingActionButton addPic = (FloatingActionButton) findViewById(R.id.add_pic);
        editZone = (EditText) findViewById(R.id.edit_zone);
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
            ContentResolver resolver = getContentResolver();
            // 获得图片的uri
            Uri originalUri = data.getData();
            try {
                Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
                ImageSpan imageSpan = new ImageSpan(EditActivity.this, originalBitmap);
                // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
                String tempUrl = "<img src=\"" + originalUri.getPath().toString() + "\" />";
                SpannableString spannableString = new SpannableString(tempUrl);
                // 用ImageSpan对象替换你指定的字符串
                spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 将选择的图片追加到EditText中光标所在位置
                int index = editZone.getSelectionStart(); // 获取光标所在位置
                Editable editText = editZone.getEditableText();
                if (index < 0 || index >= editText.length()) {
                    editText.append(spannableString);
                } else {
                    editText.insert(index, spannableString);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
