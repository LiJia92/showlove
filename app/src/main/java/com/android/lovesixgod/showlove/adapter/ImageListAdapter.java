package com.android.lovesixgod.showlove.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.lovesixgod.showlove.R;
import com.android.lovesixgod.showlove.ui.EditActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lijia on 2016-01-20.
 */
public class ImageListAdapter extends BaseAdapter {

    private List<Uri> uris;
    private Context context;

    public ImageListAdapter(List<Uri> uris, Context context) {
        this.uris = uris;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (uris != null) {
            return uris.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
            image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(image);
        } else {
            image = (ImageView) convertView.getTag();
        }

        // 获取图片真实路径
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uris.get(position), filePathColumn, null, null, null);
        String picturePath;
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
        } else {
            picturePath = uris.get(position).getPath();
        }

        // 根据原图计算目标尺寸
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
        ViewGroup.LayoutParams layoutParams = image.getLayoutParams();
        layoutParams.width = (int) (EditActivity.displayWidth * widthScale);
        layoutParams.height = (int) (EditActivity.displayWidth * widthScale / width * height);
        image.setLayoutParams(layoutParams);

        // Picasso加载图片
        Picasso.with(context).load(uris.get(position)).fit().into(image);
        return convertView;
    }

}
