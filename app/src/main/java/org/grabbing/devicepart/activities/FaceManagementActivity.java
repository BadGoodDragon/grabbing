package org.grabbing.devicepart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
