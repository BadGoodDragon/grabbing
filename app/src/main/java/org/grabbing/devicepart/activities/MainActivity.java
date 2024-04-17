package org.grabbing.devicepart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.grabbing.devicepart.Executor;
import org.grabbing.devicepart.R;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.IntegerLive;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.QueryDataListLive;
import org.grabbing.devicepart.livedata.StringLive;
import org.grabbing.devicepart.managers.UIManager;

public class MainActivity extends AppCompatActivity implements ActivitiesActions {

    private Button logIn;
    private Button register;
    private Button registerFace;
    private Button management;
    private Button start;

    private TextView username;
    private TextView name;
    private TextView status;

    private int code = -1;
    private String faceName = "not authorized";


    private boolean hasToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        logIn = findViewById(R.id.main_button_log_in);
        register = findViewById(R.id.main_button_register);
        registerFace = findViewById(R.id.main_button_register_face);
        management = findViewById(R.id.main_button_management);
        start = findViewById(R.id.main_button_start_stop);

        username = findViewById(R.id.main_text_username);
        name = findViewById(R.id.main_text_name);
        status = findViewById(R.id.main_text_status);

        UIManager.setCurrentActivity(this);
        UIManager.setMainActivity(this);

        Executor executor = new Executor(this.getApplicationContext());

        initExecutor(executor);

        StaticStorage.setExecutor(executor);

        if (LongTermStorage.hasSavedToken(this.getApplicationContext())) {
            executor.setToken(LongTermStorage.getToken(this.getApplicationContext()));
            Log.i("token", LongTermStorage.getToken(this.getApplicationContext()));
            executor.check();
        } else {
            username.setText("not authorized");
            name.setText("not authorized");
        }



        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountLogInActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountRegisterActivity.class);
                startActivity(intent);
            }
        });
        registerFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FaceRegisterActivity.class);
                startActivity(intent);
            }
        });
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FaceManagementActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initExecutor(Executor executor) {
        QueryDataListLive listLive = new QueryDataListLive();
        StringLive tokenLive = new StringLive();
        ListOfUsersLive usersLive = new ListOfUsersLive();
        BooleanLive booleanLive = new BooleanLive();
        IntegerLive integerLive = new IntegerLive();
        StringLive stringLive = new StringLive();
        listLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean v) {
                if (v) {
                    executor.resumeRunningThread();
                }
            }
        });
        tokenLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean v) {
                if (v) {
                    executor.resumeRunningThread();
                }
            }
        });
        usersLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean v) {
                if (v) {
                    executor.resumeRunningThread();
                }
            }
        });
        booleanLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean v) {
                if (v) {
                    executor.resumeRunningThread();
                }
            }
        });
        integerLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean v) {
                if (v) {
                    executor.resumeRunningThread();
                }
            }
        });
        stringLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean v) {
                if (v) {
                    executor.resumeRunningThread();
                }
            }
        });

        executor.setUsersLive(usersLive);
        executor.setListLive(listLive);
        executor.setTokenLive(tokenLive);
        executor.setBooleanLive(booleanLive);
        executor.setIntegerLive(integerLive);
        executor.setStringLive(stringLive);


        executor.init(LongTermStorage.accountQuery,
                LongTermStorage.queryReceiptManagerQuery,
                LongTermStorage.sendingResultManagerQuery,
                LongTermStorage.faceManagerQuery,
                LongTermStorage.checkManagerQuery);

        executor.start();

    }

    @Override
    public void finishNow() {}

    @Override
    public void setError(String error) {
        Toast.makeText(this.getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    public void setFaceName(String name) {
        runOnUiThread(new Thread() {
            @Override
            public void run() {
                MainActivity.this.name.setText(name);
            }
        });
    }

    public void setAuthStatus(int n) {
        if (n == 0) {
            runOnUiThread(new Thread() {
                @Override
                public void run() {
                    username.setText("not authorized");
                    name.setText("not authorized");
                }
            });
        } else if (n == 1) {
            runOnUiThread(new Thread() {
                @Override
                public void run() {
                    username.setText(LongTermStorage.getUsername(MainActivity.this.getApplicationContext()));
                    name.setText("not authorized");
                }
            });
        } else if (n == 3) {
            runOnUiThread(new Thread() {
                @Override
                public void run() {
                    username.setText(LongTermStorage.getUsername(MainActivity.this.getApplicationContext()));
                    name.setText("loading...");
                }
            });
            StaticStorage.getExecutor().getFaceName();
        }
    }

    public void setToken(String token) {
        LongTermStorage.saveToken(token, this.getApplicationContext());
    }

    public void update() {
        StaticStorage.getExecutor().check();
    }

}