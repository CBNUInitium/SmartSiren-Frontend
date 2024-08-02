package com.example.smartsiren;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name, et_email, et_pwd, et_pwd_chk;
    Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        et_name = findViewById(R.id.et_name);
        et_pwd_chk = findViewById(R.id.et_pwd_chk);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String pwd_chk = et_pwd_chk.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {
                    // Check if any field is empty
                    Toast.makeText(RegisterActivity.this, "이름, 이메일, 비밀번호를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (pwd.length() < 8) {
                    // Check if password length is less than 8 characters
                    Toast.makeText(RegisterActivity.this, "비밀번호를 8자 이상 설정해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    // Check if email format is valid
                    Toast.makeText(RegisterActivity.this, "올바른 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!pwd.equals(pwd_chk)) {
                    // Check if passwords match
                    Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다. 동일한 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_pwd.setText(""); // Clear the password fields
                    et_pwd_chk.setText("");
                } else {
                    // Successful registration
                    Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // 뒤로가기 버튼을 눌렀을 때의 동작
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isValidEmail(String email) {
        // 정규식을 사용하여 이메일 형식을 검증
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
