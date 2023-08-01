package com.hgw.toolkitpro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hgw.toolkitpro.adapter.ContactUpdater;
import com.hgw.toolkitpro.R;
import com.hgw.toolkitpro.model.Contact;

public class EditContactActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // 初始化视图
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        // 获取传递的联系人信息
        Intent intent = getIntent();
        contactId = intent.getLongExtra("contact_id", -1);
        String contactName = intent.getStringExtra("contact_name");
        String contactPhoneNumber = intent.getStringExtra("contact_phone");

        // 在EditText中显示联系人信息
        editTextName.setText(contactName);
        editTextPhoneNumber.setText(contactPhoneNumber);

        Button saveBtn = findViewById(R.id.btnSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newContactName = editTextName.getText().toString().trim();
                String newContactPhoneNumber = editTextPhoneNumber.getText().toString().trim();
                Contact updatedContact = new Contact(contactId, newContactName, newContactPhoneNumber);
                boolean isUpdated = updateContact(updatedContact);
                if (isUpdated) {
                    Toast.makeText(EditContactActivity.this, "联系人信息已更新", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditContactActivity.this, "更新联系人信息失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean updateContact(Contact updatedContact) {
        ContentResolver contentResolver = getContentResolver();
        return ContactUpdater.updateContact(contentResolver, updatedContact);
    }
}