package com.zl.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class OpenCVUtil {
    private static boolean OPENCV_INIT = false;

    public static void init(Context mContext) {
        BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(mContext) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS:
                        OPENCV_INIT = true;
                        break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, mContext, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    /**
     * 使用openCV生成灰度图片
     * @param src 原图
     * @return 灰度图
     */
    public static Bitmap bitmap2Gray(Bitmap src) {
        if (!OPENCV_INIT) return src;
        Mat mat_src = new Mat(src.getWidth(), src.getHeight(), CvType.CV_8UC4);
        Utils.bitmapToMat(src, mat_src);
        Mat mat_gray = new Mat(src.getWidth(), src.getHeight(), CvType.CV_8UC1);
        Imgproc.cvtColor(mat_src, mat_gray, Imgproc.COLOR_BGRA2GRAY, 1);
        Bitmap dst = Bitmap.createBitmap(mat_gray.cols(), mat_gray.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat_gray, dst);
        return dst;
    }
}
