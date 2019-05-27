package org.opencv;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView ivSrc, ivDst;
    private Button btnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivSrc = findViewById(R.id.iv_src);
        ivDst = findViewById(R.id.iv_dst);
        btnSub = findViewById(R.id.btn_sub);
        OpenCVUtil.init(MainActivity.this);
        ivSrc.post(() -> {
            Bitmap bitmap = getBitmapByAssets(MainActivity.this, "test2.jpg");
            ivSrc.setImageBitmap(bitmap);
        });
        btnSub.setOnClickListener(v -> {
            Bitmap src = getBitmapByAssets(MainActivity.this, "test2.jpg");
            Bitmap dst = OpenCVUtil.bitmap2Gray(src);
            ivDst.setImageBitmap(dst);
        });
    }


    public static Bitmap getBitmapByAssets(Context mContext, String assetsPath) {
        Bitmap bitmap = null;
        try {
            AssetManager assetManager = mContext.getAssets();
            InputStream is = assetManager.open(assetsPath);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
