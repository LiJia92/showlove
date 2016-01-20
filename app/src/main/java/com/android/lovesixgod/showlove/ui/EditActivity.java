package com.android.lovesixgod.showlove.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.View;
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

        getDisplayMetrics();

        addPic = (FloatingActionButton) findViewById(R.id.add_pic);
        imageList = (ListView) findViewById(R.id.image_list);

        addPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_pic:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            Uri pictureUri = data.getData();
            uris.add(pictureUri);
            if (adapter == null) {
                adapter = new ImageListAdapter(uris, EditActivity.this);
                imageList.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
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
