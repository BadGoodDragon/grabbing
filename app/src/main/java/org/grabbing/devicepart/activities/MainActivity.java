package org.grabbing.devicepart.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.grabbing.devicepart.Executor;
import org.grabbing.devicepart.R;
import org.grabbing.devicepart.activities.fragments.AccountLogInFragment;
import org.grabbing.devicepart.activities.fragments.AccountRegisterFragment;
import org.grabbing.devicepart.activities.fragments.FaceManagementFragment;
import org.grabbing.devicepart.activities.fragments.FaceRegisterFragment;
import org.grabbing.devicepart.activities.fragments.MyQueriesFragment;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.IntegerLive;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.QueryDataListLive;
import org.grabbing.devicepart.livedata.StringLive;
import org.grabbing.devicepart.managers.UIManager;
import org.grabbing.devicepart.wrappers.QuickCompletion;

public class MainActivity extends AppCompatActivity {
    private Button logIn;
    private Button register;
    private Button registerFace;
    private Button management;
    private Button start;
    private Button myQueries;

    private TextView username;
    private TextView name;
    private TextView status;

    private static boolean isStart;
    private static QuickCompletion quickCompletion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUIStepFirst();

        Executor executor = new Executor(this);
        initExecutor(executor);
        StaticStorage.setExecutor(executor);

        checkToken();

        isStart = false;

        initUIStepSecond();

        runUpdater();

        StaticStorage.setMainActivity(this);
    }

    private void initUIStepFirst() {
        setContentView(R.layout.main);

        logIn = findViewById(R.id.main_button_log_in);
        register = findViewById(R.id.main_button_register);
        registerFace = findViewById(R.id.main_button_register_face);
        management = findViewById(R.id.main_button_management);
        start = findViewById(R.id.main_button_start_stop);
        myQueries = findViewById(R.id.main_button_my_queries);

        username = findViewById(R.id.main_text_username);
        name = findViewById(R.id.main_text_name);
        status = findViewById(R.id.main_text_status);
    }
    private void initUIStepSecond() {
        AccountLogInFragment accountLogInFragment = new AccountLogInFragment();
        AccountRegisterFragment accountRegisterFragment = new AccountRegisterFragment();
        FaceManagementFragment faceManagementFragment = new FaceManagementFragment();
        FaceRegisterFragment faceRegisterFragment = new FaceRegisterFragment();
        MyQueriesFragment myQueriesFragment = new MyQueriesFragment();

        StaticStorage.setAccountLogInFragment(accountLogInFragment);
        StaticStorage.setAccountRegisterFragment(accountRegisterFragment);
        StaticStorage.setFaceManagementFragment(faceManagementFragment);
        StaticStorage.setFaceRegisterFragment(faceRegisterFragment);
        StaticStorage.setMyQueriesFragment(myQueriesFragment);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, AccountLogInActivity.class);
                startActivity(intent);*/
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, accountLogInFragment);
                transaction.commit();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, AccountRegisterActivity.class);
                startActivity(intent);*/
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, accountRegisterFragment);
                transaction.commit();
            }
        });
        registerFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, FaceRegisterActivity.class);
                startActivity(intent);*/
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, faceRegisterFragment);
                transaction.commit();
            }
        });
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, FaceManagementActivity.class);
                startActivity(intent);*/
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, faceManagementFragment);
                transaction.commit();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startButtonOnClick();
            }
        });

        myQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, myQueriesFragment);
                transaction.commit();
            }
        });
    }

    private void startButtonOnClick() {
        if (!isStart) {
            isStart = true;

            quickCompletion = new QuickCompletion();
            StaticStorage.getExecutor().executeQueries(quickCompletion);
            StaticStorage.getUpdater().setOnRun(false);

            start.setBackgroundResource(R.drawable.running_button_background);
            start.setText("Stop");

        } else {
            isStart = false;

            quickCompletion.setStop(true);
            StaticStorage.getUpdater().setOnRun(true);


            start.setBackgroundResource(R.drawable.button_background);
            start.setText("Start");
        }
    }
    public void setStartButtonOnClickErrorCallThread() {
        isStart = false;
        quickCompletion.setStop(true);
        StaticStorage.getUpdater().setOnRun(true);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                start.setBackgroundResource(R.drawable.button_background);
                start.setText("Start");
                status.setText("Error");
            }
        });
    }

    private void runUpdater() {
        if (StaticStorage.getUpdater() == null) {
            Updater updater = new Updater();
            //updater.setActivitiesActions(this);
            updater.start();
            StaticStorage.setUpdater(updater);
        }
    }

    private void initExecutor(Executor executor) {
        QueryDataListLive listLive = new QueryDataListLive();
        StringLive tokenLive = new StringLive();
        ListOfUsersLive usersLive = new ListOfUsersLive();
        BooleanLive booleanLive = new BooleanLive();
        IntegerLive integerLive = new IntegerLive();
        StringLive stringLive = new StringLive();
        ListOfUsersLive qLive = new ListOfUsersLive();
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
        qLive.getStatusLive().observeForever(new Observer<Boolean>() {
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
        executor.setMyQueryLive(qLive);


        executor.init(LongTermStorage.accountQuery,
                LongTermStorage.queryReceiptManagerQuery,
                LongTermStorage.sendingResultManagerQuery,
                LongTermStorage.faceManagerQuery,
                LongTermStorage.checkManagerQuery,
                LongTermStorage.addQuery);

        executor.start();

    }

    private void checkToken() {
        Executor executor = StaticStorage.getExecutor();

        if (LongTermStorage.hasSavedToken(this)) {
            Log.i("token", LongTermStorage.getToken(this.getApplicationContext()));

            /*username.setText("Loading ...");
            name.setText("Loading ...");
            status.setText("Wait ...");*/

            executor.setToken(LongTermStorage.getToken(this.getApplicationContext()));
            executor.check();
        } else {
            StaticStorage.setHasFace(false);
            username.setText("Not authorized");
            name.setText("Not authorized");
            status.setText("Not ready to work");
        }
    }

    public void setCheckTokenResultCallThread(int status) {
        Log.i("Status", String.valueOf(status));
        if (status == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StaticStorage.setHasFace(false);
                    username.setText("Not authorized");
                    name.setText("Not authorized");
                    MainActivity.this.status.setText("Not ready to work");
                }
            });

            LongTermStorage.deleteToken(this);
            LongTermStorage.deleteUsername(this);
        } else if (status == 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StaticStorage.setHasFace(false);
                    username.setText(LongTermStorage.getUsername(MainActivity.this));
                    name.setText("Not authorized");
                    MainActivity.this.status.setText("Not ready to work");
                }
            });
        } else if (status == 3) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StaticStorage.setHasFace(true);
                    username.setText(LongTermStorage.getUsername(MainActivity.this));
                    //name.setText("Loading ...");
                    MainActivity.this.status.setText("Ready to work");
                }
            });

            Executor executor = StaticStorage.getExecutor();
            executor.getFaceName();
        }
    }
    public void setFaceNameCallThread(String faceName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name.setText(faceName);
            }
        });
    }

    public void updateCallThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                checkToken();
            }
        });
    }
}

/*public class MainActivity extends AppCompatActivity implements ActivitiesActions {

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

    private static boolean isStart = false;
    private static QuickCompletion quickCompletion;

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

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStart) {
                    isStart = true;
                    quickCompletion = new QuickCompletion();
                    executor.executeQueries(quickCompletion);
                } else {
                    isStart = false;
                    start.setBackgroundColor(Color.parseColor("#10DF0C"));
                    quickCompletion.setStop(true);
                    status.setText("Start");
                }
            }
        });

        View view = findViewById(R.id.main);
        SwipeListener swipeListener = new SwipeListener(this);
        view.setOnTouchListener(swipeListener);

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
    public void finishNow() {
        throw new RuntimeException("not call finishNow in MainActivity");
    }

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
                    status.setText("Not ready to work");
                }
            });
        } else if (n == 1) {
            runOnUiThread(new Thread() {
                @Override
                public void run() {
                    username.setText(LongTermStorage.getUsername(MainActivity.this.getApplicationContext()));
                    name.setText("not authorized");
                    status.setText("Not ready to work");
                }
            });
        } else if (n == 3) {
            runOnUiThread(new Thread() {
                @Override
                public void run() {
                    username.setText(LongTermStorage.getUsername(MainActivity.this.getApplicationContext()));
                    name.setText("loading...");
                    status.setText("Ready to work");
                }
            });
            StaticStorage.getExecutor().getFaceName();
        }
    }

    public void setToken(String token) {
        LongTermStorage.saveToken(token, this.getApplicationContext());
    }

    public class SwipeListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public SwipeListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
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
            update();
        }
    }

    public void update() {
        StaticStorage.getExecutor().check();
    }

    public void setButtonStatus(boolean v) {
        if (v) {
            start.setBackgroundColor(Color.GRAY);
            start.setText("Stop");
            isStart = true;
        } else {
            status.setText("Error");
            isStart = false;
        }
    }

}*/