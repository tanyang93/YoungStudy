package com.young.study.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import com.young.study.Bmob.Person;
import com.young.study.R;
import com.young.study.ui.activity.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    private EditText edt_user_name;
    private EditText edt_user_pwd;
    private EditText edt_user_nick;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public View getLoadingView() {
        return null;
    }

    @Override
    public boolean getFirstStart() {
        return false;
    }

    @Override
    public void initViewAndEvents() {
        edt_user_name = (EditText) findViewById(R.id.edt_user_name);
        edt_user_pwd = (EditText) findViewById(R.id.edt_user_pwd);
        edt_user_nick = (EditText) findViewById(R.id.edt_user_nick);
        btn_register = (Button) findViewById(R.id.btn_register_id);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edt_user_name.getText().toString();
                String pwd = edt_user_pwd.getText().toString();
                final String nick = edt_user_nick.getText().toString();
                Person person = new Person();
                person.setUsername(name);
                person.setPassword(pwd);
                person.setUser_nick(nick);
                person.signUp(new SaveListener<Person>() {
                    @Override
                    public void done(final Person person, BmobException e) {
                        Log.d(TAG,person.getUsername()+","+person.getUser_nick());
                        if (e == null) {

                        }else {
                            Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
