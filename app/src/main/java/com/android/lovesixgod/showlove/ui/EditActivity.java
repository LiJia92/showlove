package com.android.lovesixgod.showlove.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.android.lovesixgod.showlove.R;
import com.android.lovesixgod.showlove.adapter.ImageListAdapter;

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
    private FloatingActionButton addPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

//        getDisplayMetrics();
//
//        addPic = (FloatingActionButton) findViewById(R.id.add_pic);
//        imageList = (ListView) findViewById(R.id.image_list);
//
//        addPic.setOnClickListener(this);
//        MaterialEditText labelEdit = (MaterialEditText) findViewById(R.id.label_edit_text);
//        labelEdit.requestFocus();
        showKeyBoard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.add_pic:
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(intent, 0);
//                break;
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
//        if (resultCode == RESULT_OK && requestCode == 0) {
//            Uri pictureUri = data.getData();
//            uris.add(pictureUri);
//            if (adapter == null) {
//                adapter = new ImageListAdapter(uris, EditActivity.this);
//                imageList.setAdapter(adapter);
//            } else {
//                adapter.notifyDataSetChanged();
//            }
//        }
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
