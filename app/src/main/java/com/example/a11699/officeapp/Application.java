package com.example.a11699.officeapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.example.a11699.officeapp.FacePack.FaceDB;

public class Application extends android.app.Application{
    //初始化加载人脸识别引擎
    private final String TAG=this.getClass().toString();
    public  FaceDB mFaceDB;
    Uri mImage;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         *
         通过Context.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
         通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
         */
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        mImage = null;
    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }
    /**
     * @param path
     * @return
     * 获取到了我们需要的图像数据bmp，把图片取出来
       我们在Application类用函数 decodeImage中实现这段代码
     */
     public static Bitmap decodeImage(String path){
         Bitmap res;
         try {
             ExifInterface exif = new ExifInterface(path);
             int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

             BitmapFactory.Options op = new BitmapFactory.Options();
             op.inSampleSize = 1;
             op.inJustDecodeBounds = false;
             //op.inMutable = true;
             res = BitmapFactory.decodeFile(path, op);
             //rotate and scale.
             Matrix matrix = new Matrix();

             if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                 matrix.postRotate(90);
             } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                 matrix.postRotate(180);
             } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                 matrix.postRotate(270);
             }

             Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
             //打印得check target Image:2592X4608
             Log.d("zjc", "图片的宽度和高度：check target Image:" + temp.getWidth() + "X" + temp.getHeight());
             if (!temp.equals(res)) {
                 res.recycle();
             }
             return temp;
         }catch (Exception e){
             e.printStackTrace();
         }
         return  null;
     }
}
