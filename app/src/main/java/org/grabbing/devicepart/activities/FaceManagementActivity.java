package org.grabbing.devicepart.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

        View view = findViewById(R.id.face_management);
        FaceManagementActivity.SwipeListener swipeListener = new FaceManagementActivity.SwipeListener(this);
        view.setOnTouchListener(swipeListener);


        StaticStorage.getExecutor().getListOfLinkedUsers();
    }

    public class SwipeListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public SwipeListener(Context context) {
            gestureDetector = new GestureDetector(context, new FaceManagementActivity.SwipeListener.GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            // Свайп вниз
                            onSwipeDown();
                        }
                    }
                    result = true;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeDown() {
            StaticStorage.getExecutor().getListOfLinkedUsers();
        }
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
