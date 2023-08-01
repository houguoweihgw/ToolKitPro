package com.hgw.toolkitpro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.adapter.ToolAdapter;
import com.hgw.toolkitpro.model.ToolItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.gridView);
        List<ToolItem> toolList = getToolList(); // 用于获取工具列表数据
        ToolAdapter adapter = new ToolAdapter(this, toolList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    // 获取工具列表数据的示例方法
    private List<ToolItem> getToolList() {
        List<ToolItem> toolList = new ArrayList<>();
        toolList.add(new ToolItem(R.drawable.contacts, "联系人"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        toolList.add(new ToolItem(R.drawable.todo, "待开发"));
        // 添加更多工具项数据
        return toolList;
    }
}