package com.example.painttext;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static boolean SAVE_PICTURE=false;
    private Spinner sp_color, sp_stroke;
    private ImageView lv;
    private Paint paint, mEraser;
    private Bitmap srcBitmap, copyBitmap,picture,copyPicture;
    private Canvas canvas,newCanvas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ImageView) findViewById(R.id.lv_paint);
        sp_color = (Spinner) findViewById(R.id.sp_color);
        sp_stroke = (Spinner) findViewById(R.id.sp_strokeWidth);
        Button save = (Button) findViewById(R.id.save);
        Button rePaint = (Button) findViewById(R.id.rePaint);
        Button chosePicture = (Button) findViewById(R.id.picture);

        //设置画布资源
        initBitmap();
        //开始作画逻辑
        startPaint();

        //点击下拉窗口，选择画笔颜色
        sp_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String[] convertView = getResources().getStringArray(R.array.color);
                switch (position) {
                    case 0:
                        paint.setColor(Color.RED);
                        break;
                    case 1:
                        paint.setColor(Color.BLUE);
                        break;
                    case 2:
                        paint.setColor(Color.GREEN);
                        break;
                    case 3:
                        paint.setColor(Color.YELLOW);
                        break;
                    case 4:
                        paint.setColor(Color.WHITE);
                        break;
                    case 5:
                        paint.setColor(Color.BLACK);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //点击下拉窗口 ，选择画笔粗细
        sp_stroke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] stroke = getResources().getStringArray(R.array.strokeWidth);
                switch (position) {
                    case 0:
                        paint.setStrokeWidth(1);
                        break;
                    case 1:
                        paint.setStrokeWidth(2);
                        break;
                    case 2:
                        paint.setStrokeWidth(3);
                        break;
                    case 3:
                        paint.setStrokeWidth(4);
                        break;
                    case 4:
                        paint.setStrokeWidth(5);
                        break;
                    case 5:
                        paint.setStrokeWidth(6);
                        break;
                    case 6:
                        paint.setStrokeWidth(7);
                        break;
                    case 7:
                        paint.setStrokeWidth(8);
                        break;
                    case 8:
                        paint.setStrokeWidth(9);
                        break;
                    case 9:
                        paint.setStrokeWidth(10);
                        break;
                    case 10:
                        paint.setStrokeWidth(11);
                        break;
                    case 11:
                        paint.setStrokeWidth(12);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //点击按钮保存图片
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        //点击按钮重新画画
        rePaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SAVE_PICTURE==false){
                    // 创建一个画布
                    canvas = new Canvas(copyBitmap);
                    // 开始作画
                    canvas.drawBitmap(srcBitmap, new Matrix(), paint);
                    // canvas.drawLine(20, 30, 50, 80, paint);
                    lv.setImageBitmap(copyBitmap);
                }else {
                    // 创建一个画布
                    canvas = new Canvas(copyPicture);
                    // 开始作画
                    canvas.drawBitmap(picture, new Matrix(), paint);
                    // canvas.drawLine(20, 30, 50, 80, paint);
                    lv.setImageBitmap(copyPicture);
                }

            }
        });

        //点击按钮选择图片资源
        chosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮进入系统图库
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

    }

    /**
     * 加载画布资源
     */
    private void initBitmap() {
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pg);

        // [2]获取原图的副本 相当于是一个空白 的白纸
        copyBitmap = Bitmap.createBitmap(srcBitmap.getWidth(),
                srcBitmap.getHeight(), srcBitmap.getConfig());
        // 创建画笔
        paint = new Paint();
        // 创建一个画布
        canvas = new Canvas(copyBitmap);
        // 这是画布
        canvas.drawBitmap(srcBitmap, new Matrix(), paint);
        // canvas.drawLine(20, 30, 50, 80, paint);
        lv.setImageBitmap(copyBitmap);
    }


    /**
     * 保存图片
     */
    public void saveImage() {
        try {
            //定义文件的保存路径
            String path = "/mnt/sdcard/Download/image/";
            String fileName = SystemClock.uptimeMillis() + ".jpeg";
            File file = new File(path, fileName);
            //输出
            FileOutputStream fos = new FileOutputStream(file);
            //设置保存文件的格式
            if (SAVE_PICTURE==false){
                copyBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }else {
                copyPicture.compress(Bitmap.CompressFormat.JPEG,100,fos);
            }

            if (fos != null) {
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_LONG).show();
            }
            //将图片插入到图库当中
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
            getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //当activity接收其他activity或者应用返回的数据时调用
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            //获取挑选的结果
            Uri selectImage = data.getData();
            //获取图片数据
            String[] filePathColum = new String[]{MediaStore.Images.Media.DATA};

            //查询文件的路径和数据
            Cursor cursor = getContentResolver().query(selectImage, filePathColum, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColum[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            SAVE_PICTURE=true;

            //在选择的图片上作画
             picture = BitmapFactory.decodeFile(picturePath);
             copyPicture = Bitmap.createBitmap(picture.getWidth(),
                    picture.getHeight(), picture.getConfig());
            paint = new Paint();
            canvas = new Canvas(copyPicture);
            canvas.drawBitmap(picture, new Matrix(), paint);
            lv.setImageBitmap(picture);
            startPaint();

        }
    }

    public void startPaint() {
        lv.setOnTouchListener(new View.OnTouchListener() {
            int startX = 0;
            int startY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY();
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        if (SAVE_PICTURE==false){
                            lv.setImageBitmap(copyBitmap);
                        }else {
                            lv.setImageBitmap(copyPicture);
                        }
                        startX = stopX;
                        startY = stopY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }
}
