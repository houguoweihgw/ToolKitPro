package com.hgw.toolkitpro.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.adapter.ContactAdapter;
import com.hgw.toolkitpro.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private static final int REQUEST_READ_WRITE_CONTACTS_PERMISSION = 1;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否授予读取联系人权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            // 读取联系人权限尚未授予，向用户请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                    REQUEST_READ_WRITE_CONTACTS_PERMISSION);
        } else {
            setContentView(R.layout.activity_contact_list);
            // 初始化RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            // 获取所有联系人数据
            contactList = getAllContacts();
            // 创建适配器并设置给RecyclerView
            adapter = new ContactAdapter(contactList);
            recyclerView.setAdapter(adapter);

            FloatingActionButton fabAddContact = findViewById(R.id.fabAddContact);
            fabAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
                    startActivity(intent);
                }
            });

            FloatingActionButton fabCall = findViewById(R.id.fabCall);
            fabCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    startActivity(dialIntent);
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_WRITE_CONTACTS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了读取联系人权限，执行获取联系人数量的操作
                setContentView(R.layout.activity_contact_list);
                // 初始化RecyclerView
                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                // 获取所有联系人数据
                contactList = getAllContacts();
                // 创建适配器并设置给RecyclerView
                adapter = new ContactAdapter(contactList);
                recyclerView.setAdapter(adapter);
            } else {
                // 用户拒绝了读取联系人权限，可以在此处给出解释或其他操作
                Toast.makeText(this, "Read And Write contacts permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 获取所有联系人数据的方法
    private List<Contact> getAllContacts() {
        // 创建存储联系人数据的列表
        List<Contact> contacts = new ArrayList<>();

        // 定义需要查询的联系人字段
        String[] projection = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME, // 姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER // 电话号码
        };

        // 查询联系人数据
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME+" ASC"
        );
        // 遍历Cursor，提取联系人数据
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int columnIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                String id = cursor.getString(columnIdIndex);
                int columnNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(columnNameIndex);
                int columnNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = cursor.getString(columnNumberIndex);
                contacts.add(new Contact(Long.parseLong(id),name, phoneNumber));
            }
            cursor.close();
        }
        return contacts;
    }
}