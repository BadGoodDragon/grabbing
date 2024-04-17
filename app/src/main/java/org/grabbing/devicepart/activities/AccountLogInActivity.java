package org.grabbing.devicepart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.managers.UIManager;

public class AccountLogInActivity extends AppCompatActivity implements ActivitiesActions {

    private TextInputLayout username;
    private TextInputLayout password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_log_in);

        UIManager.setCurrentActivity(this);

        Button back = findViewById(R.id.account_log_in_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button logIn = findViewById(R.id.account_log_in_button_log_in);
        username = findViewById(R.id.account_log_in_text_input_layout_username);
        password = findViewById(R.id.account_log_in_text_input_layout_password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().authorize(username.getEditText().getText().toString(),
                                                            password.getEditText().getText().toString());
                LongTermStorage.saveUsername(username.getEditText().getText().toString(), AccountLogInActivity.this.getApplicationContext());
            }
        });
    }

    @Override
    public void finishNow() {
        UIManager.update();
        finish();
    }

    @Override
    public void setError(String error) {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                username.setError(error);
            }
        });
    }
}
