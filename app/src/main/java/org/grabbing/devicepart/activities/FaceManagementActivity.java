package org.grabbing.devicepart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.activities.recyclerview.ListOfLinkedUsersAdapter;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.managers.UIManager;

import java.util.List;

public class FaceManagementActivity extends AppCompatActivity implements ActivitiesActions {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_management);

        UIManager.setCurrentActivity(this);
        UIManager.setFaceManagementActivity(this);

        Button back = findViewById(R.id.face_management_button_back);
        recyclerView = findViewById(R.id.face_management_recycler_view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button attach = findViewById(R.id.face_management_button_attach);
        Button detach = findViewById(R.id.face_management_button_detach);

        TextInputLayout name = findViewById(R.id.face_management_text_input_layout_name);

        Button update = findViewById(R.id.face_management_button_update);

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().attachFace(name.getEditText().getText().toString());
            }
        });

        detach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().detachFace(name.getEditText().getText().toString());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticStorage.getExecutor().getListOfLinkedUsers();
            }
        });


        StaticStorage.getExecutor().getListOfLinkedUsers();
    }

    @Override
    public void finishNow() {
        finish();
    }

    @Override
    public void setError(String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FaceManagementActivity.this.getApplicationContext(), error, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setListOfLinkedUsers(List<String> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListOfLinkedUsersAdapter customAdapter = new ListOfLinkedUsersAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(FaceManagementActivity.this.getApplicationContext()));
                recyclerView.setAdapter(customAdapter);
            }
        });
    }
}
