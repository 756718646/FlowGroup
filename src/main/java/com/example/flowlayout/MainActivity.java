package com.example.flowlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FlowGroup flow;

    private String[] arr = new String[]{
            "1短篇小说","2短篇小说         ",
            "坐落在曼谷市中心的泰国伦披尼公园，坐落在曼谷市中心的泰国伦披尼公园坐落在曼谷市中心的泰国伦披尼公园坐落在曼谷市中心的泰国伦披尼公园坐落在曼谷市中心的泰国伦披尼公园是众多游客和市民们非常喜欢的一处休闲好去处，但是公园却渐渐被一种神奇的生物入侵了，泽巨蜥的存在让公园管理方头疼不已。",
            "4我是来搞笑的00000", "5漫画·手绘","读书6545",
            "4我是来搞笑的00000", "5漫画·手绘","读",
            "4我是来搞笑的00000", "5漫画·手绘","读sssssssss书",
            "6短篇小说","7短篇小说","8短篇小说",
            "9我是来搞笑的", "10漫画·手绘","读书",
            "11运动&健身","12社会热点","13自然科学"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.flow = (FlowGroup) findViewById(R.id.flow);
        flow.setAdapter(new FlowAdapter(this,Arrays.asList(arr)));
    }
}
