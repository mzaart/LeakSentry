package com.mzaart.leaksentry.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.ImageView;

import com.mzaart.leaksentry.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    // ====== gets scaled bitmap dimensions is imageView =====
    // return int[2]: 0: scaled width, 1: scaled height
    // source: http://stackoverflow.com/a/13318469 (edited)
    public static int[] getScaledDimensions(ImageView imgV, Bitmap src) {
        int[] dimen = new int[2];

        int actualHeight;
        int actualWidth;

        int imageViewHeight = imgV.getHeight();
        int imageViewWidth = imgV.getWidth();

        int bitmapHeight = src.getHeight();
        int bitmapWidth = src.getWidth();

        System.out.println("Bitmap Width: " + bitmapWidth + "\n" +
                "ImageView Width: " + imageViewWidth + "\n" +
                "Bitmap Height: " + bitmapHeight + "\n" +
                "ImageView Height: " + imageViewHeight);

        if (imageViewHeight * bitmapWidth <= imageViewWidth * bitmapHeight) {
            actualWidth = (int) ((float) imageViewHeight / bitmapHeight *  bitmapWidth);
            actualHeight = imageViewHeight;
        } else {
            actualHeight = (int) ((float) imageViewWidth / bitmapWidth * bitmapHeight);
            actualWidth = imageViewWidth;
        }

        dimen[0] = actualWidth;
        dimen[1] = actualHeight;

        return dimen;
    }

    // ===== Saves file to disk =====
    // returns the image file created
    public static File saveBitmapToDisk(File dir, Bitmap src, String name)
    {
        dir.mkdirs();
        File image = new File(dir, name + ".png");

        try {
            FileOutputStream fos = new FileOutputStream(image);

            src.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();

            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== saves bitmap to external storage ====
    //Source: http://stackoverflow.com/questions/11983654/android-how-to-add-an-image-to-an-album/24657409#24657409
    public static void saveImageToGallery(Context context, String imgName, Bitmap bm) {
        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + '/' + context.getString(R.string.app_name)); //Creates app specific folder
        path.mkdirs();

        File imageFile = new File(path, imgName+".png"); // Imagename.png
        // delete image if it already exists
        if(imageFile.exists())
            imageFile.delete();

        try {
            FileOutputStream out = new FileOutputStream(imageFile);

            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();

                // Tell the media scanner about the new file so that it is
                // immediately available to the user.
                MediaScannerConnection.scanFile(context,new String[] { imageFile.getAbsolutePath() }, null, null);
            } catch(Exception e) {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ===== draws text on top of image ======
    public static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text, int tColor, float tSize, Typeface font, int posX, int posY) {
        // prepare canvas
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().scaledDensity;

        Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are immutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(tColor);
        // text size in pixels
        paint.setTextSize((int) (tSize * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        // text fonts
        paint.setTypeface(font);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth();// - (int) (16 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                text, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(posX, posY);//(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    // ===== fills an image with solid color =====
    public static Bitmap setImageAsColor(int color, int width, int height) {
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        image.eraseColor(color);

        return image;
    }

    // ===== fills an image with a gradient =====
    public static Bitmap setImageAsGradient(int color1, int color2, int width, int height) {
        // create bitmap
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        // create gradient
        LinearGradient grad = new LinearGradient(0, 0, width, height, color1, color2, Shader.TileMode.CLAMP);

        // draw gradient on top of bitmap
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);
        p.setShader(grad);
        c.drawRect(0, 0, width, height, p);

        return b;
    }

    // ===== fills an image with radial gradient =====
    public Bitmap makeRadGrad() {
        RadialGradient gradient = new RadialGradient(200, 200, 200, 0xFFFFFFFF,
                0xFF000000, Shader.TileMode.CLAMP);
        Paint p = new Paint();
        p.setDither(true);
        p.setShader(gradient);

        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        c.drawCircle(200, 200, 200, p);

        return bitmap;
    }

    // ===== rotates an image =====
    public static Bitmap rotateImage(Activity activity, Uri uri, Bitmap src) {
        if (getOrientation(activity, uri) == 90) {
            int angle = 90;

            Matrix matrix = new Matrix();
            matrix.postRotate(angle);

            return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        }

        return src;
    }

    public static int getOrientation(Context context, Uri photoUri) {

        Cursor cursor = context.getContentResolver().query(photoUri, new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor == null || cursor.getCount() != 1) {
            return 90;  //Assuming it was taken portrait
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    // ===============================================
    // ======= Handling Bitmaps Efficiently ==========
    // ===============================================
    // more info: https://developer.android.com/training/displaying-bitmaps/index.html

    // ===== Calculates the inSampleSize =====
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // ===== load bitmap from resource =====
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    // ===== load bitmap from inputStream =====
    public static Bitmap decodeSampledBitmapFromInputStream(Context context, Uri imgUri, int reqWidth, int reqHeight) {
        InputStream imgStream = null;
        try {
            imgStream = context.getContentResolver().openInputStream(imgUri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imgStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(imgStream, null, options);
    }

    // load bitmap from cache
    public static Bitmap decodeSampledBitmapFromCache(Context context, String name, int reqWidth, int reqHeight) {
        FileInputStream imgStream = null;

        try {
            imgStream = new FileInputStream(new File(context.getCacheDir(), name));
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imgStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        try {
            imgStream = new FileInputStream(new File(context.getCacheDir(), name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(imgStream, null, options);
    }

    // cache bitmap
    public static void cacheBitmap(Bitmap b) {
        // .....
    }
}
