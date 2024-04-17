package org.grabbing.devicepart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.managers.UIManager;

public class FaceRegisterActivity extends AppCompatActivity implements ActivitiesActions {
    private TextInputLayout name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_register);

        UIManager.setCurrentActivity(this);

        Button back = findViewById(R.id.face_register_button_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button register = findViewById(R.id.face_register_button_register);
        name = findViewById(R.id.face_register_text_input_layout_name);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().registerFace(name.getEditText().getText().toString());
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
                name.setError(error);
            }
        });
    }
}
