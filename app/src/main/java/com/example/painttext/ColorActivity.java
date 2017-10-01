package com.example.painttext;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ColorActivity extends AppCompatActivity {
     Paint paint;
    private ListView lv_color;
    String objects[]={"红色","黄色","绿色","黑色","白色","蓝色"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        //找到控件
        lv_color= (ListView) findViewById(R.id.lv_color);
        //设置适配器
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.color_item,objects);
        lv_color.setAdapter(adapter);
        paint=new Paint();
        lv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent=new Intent();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String object = objects[position];
              if(parent.equals(object)==object.equals("红色")) {
                  setResult(1,intent);
              }
              finish();
            }
        });

    }
}
