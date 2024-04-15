package org.grabbing.devicepart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.managers.UIManager;

public class AccountRegisterActivity extends AppCompatActivity implements ActivitiesActions{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_register);

        UIManager.setCurrentActivity(this);

        Button back = findViewById(R.id.account_register_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button register = findViewById(R.id.account_register_button_register);
        TextInputLayout username = findViewById(R.id.account_register_text_input_layout_username);
        TextInputLayout password = findViewById(R.id.account_register_text_input_layout_password);
    }

    @Override
    public void finishNow() {
        finish();
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this.getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }
}
