package com.android.lovesixgod.showlove.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.lovesixgod.showlove.R;
import com.android.lovesixgod.showlove.adapter.ImageListAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lijia on 2016-01-19.
 */
public class EditActivity extends Activity implements View.OnClickListener {

    public static int displayWidth;
    public static int displayHeight;
    private List<Uri> uris = new ArrayList<>();
    private ImageListAdapter adapter;
    private ListView imageList;
    private FloatingActionButton addPicture;
    private MaterialEditText contentEdit;
    private LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getDisplayMetrics();
//
        addPicture = (FloatingActionButton) findViewById(R.id.add_picture);
//        imageList = (ListView) findViewById(R.id.image_list);
//
        addPicture.setOnClickListener(this);
        contentEdit = (MaterialEditText) findViewById(R.id.content_edit_text);
        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 改变之前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        parentLayout = (LinearLayout) findViewById(R.id.edit_parent);
        showKeyBoard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_picture:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
        }
    }

    /**
     * 打开软键盘
     */
    public void showKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 关闭软键盘
     *
     * @param editText
     */
    private void hideKeyBoard(EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            Uri pictureUri = data.getData();
//            uris.add(pictureUri);
//            if (adapter == null) {
//                adapter = new ImageListAdapter(uris, EditActivity.this);
//                imageList.setAdapter(adapter);
//            } else {
//                adapter.notifyDataSetChanged();
//            }
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pictureUri, filePathColumn, null, null, null);
            String picturePath;
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
            } else {
                picturePath = pictureUri.getPath();
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, options);
            int width = options.outWidth; // 原图宽度
            int height = options.outHeight; //原图高度
            float widthScale;
            if (height / width > 5) { // 高是长的5倍或以上，则认定为长图
                widthScale = 0.12f;
            } else {
                widthScale = 0.8f;
            }
            int targetWidth = (int) (EditActivity.displayWidth * widthScale);
            int targetHeight = (int) (EditActivity.displayWidth * widthScale / width * height);
            Bitmap originalBitmap = BitmapFactory.decodeFile(picturePath);

            Matrix matrix = new Matrix();
            matrix.postScale((float) targetWidth / width, (float) targetHeight / height);
            Bitmap bitmap = Bitmap.createBitmap(originalBitmap, 0, 0, width, height, matrix, false);
            originalBitmap.recycle();
            ImageSpan imageSpan = new ImageSpan(EditActivity.this, bitmap);
            // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
//            String tempUrl = "<img src=\"" + pictureUri.getPath() + "\" />";
            String tempUrl = "<img>";
            SpannableString spannableString = new SpannableString(tempUrl);
            // 用ImageSpan对象替换你指定的字符串
            spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 将选择的图片追加到EditText中光标所在位置
            int index = contentEdit.getSelectionStart(); // 获取光标所在位置
            Editable edit_text = contentEdit.getEditableText();
            if (index < 0 || index >= edit_text.length()) {
                edit_text.append(spannableString);
            } else {
                edit_text.insert(index, spannableString);
            }
        }
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
