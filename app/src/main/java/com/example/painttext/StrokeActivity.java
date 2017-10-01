package com.example.painttext;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by xxdeng on 2017/6/20.
 */

public class StrokeActivity extends AppCompatActivity{
    private Paint paint;
    private ListView lv_stroke;
    String objects[]={"0","1","2","3","4","5","6"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        //找到控件
        lv_stroke= (ListView) findViewById(R.id.lv_color);
        //设置适配器
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.color_item,objects);
        lv_stroke.setAdapter(adapter);
        paint=new Paint();
        lv_stroke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        paint.setStrokeWidth(3);
                        break;
                    case 1:
                        paint.setStrokeWidth(4);
                        break;
                    case 2:
                        paint.setStrokeWidth(5);
                        break;
                    case 3:
                        paint.setStrokeWidth(6);
                        break;
                    case 4:
                        paint.setStrokeWidth(7);
                        break;
                    case 5:
                        paint.setStrokeWidth(8);
                        break;
                    case 6:
                        paint.setStrokeWidth(9);
                        break;
                }
                finish();
            }
        });

    }
    }
