package org.grabbing.devicepart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.grabbing.devicepart.R;
import org.grabbing.devicepart.activities.fragments.AccountLogInFragment;
import org.grabbing.devicepart.activities.fragments.AccountRegisterFragment;
import org.grabbing.devicepart.activities.fragments.FaceManagementFragment;
import org.grabbing.devicepart.activities.fragments.FaceRegisterFragment;
import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.dto.QueryInput;
import org.grabbing.devicepart.dto.ResponseOutput;
import org.grabbing.devicepart.livedata.TypeLive;
import org.grabbing.devicepart.managers.CheckManager;
import org.grabbing.devicepart.managers.FaceManager;
import org.grabbing.devicepart.managers.QueryExecutionManager;
import org.grabbing.devicepart.managers.QueryReceiptManager;
import org.grabbing.devicepart.managers.SendingResultManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final static String IN_PROCESS_KEY = "IN_PROCESS_KEY";

    private boolean inProcess;
    private int authorizationStatus;
    private String faceName;

    private Thread thread;
    private Thread executeThread;

    private Button logIn;
    private Button register;
    private Button registerFace;
    private Button management;
    private Button start;
    private Button myQueries;

    private TextView username;
    private TextView name;
    private TextView status;

    private TypeLive<Integer> integerLive;
    private TypeLive<String> stringLive;


    FaceManagementFragment faceManagementFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (savedInstanceState != null) {
            inProcess = savedInstanceState.getBoolean(IN_PROCESS_KEY, false);
        } else {
            inProcess = false;
        }


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


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountLogInFragment accountLogInFragment = new AccountLogInFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, accountLogInFragment);
                transaction.commit();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountRegisterFragment accountRegisterFragment = new AccountRegisterFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, accountRegisterFragment);
                transaction.commit();
            }
        });
        registerFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FaceRegisterFragment faceRegisterFragment = new FaceRegisterFragment();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, faceRegisterFragment);
                transaction.commit();
            }
        });

        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceManagementFragment = new FaceManagementFragment();

                Updater.setFaceManagementFragment(faceManagementFragment);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, faceManagementFragment);
                transaction.commit();
            }
        });

        thread = new Thread("Main Background Thread");
        executeThread = new Thread("Exe Thread");

        if (inProcess) {
            start.setBackgroundResource(R.drawable.running_button_background);
            start.setText("Stop");
        } else {
            start.setBackgroundResource(R.drawable.button_background);
            start.setText("Start");
        }


        TypeLive<List<QueryInput>> listQueryInputLive = new TypeLive<>(new ArrayList<>());
        listQueryInputLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    synchronized (executeThread) {
                        executeThread.notify();
                    }
                }
            }
        });

        TypeLive<List<QueryData>> listQueryDataLive = new TypeLive<>(new ArrayList<>());
        listQueryDataLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    synchronized (executeThread) {
                        executeThread.notify();
                    }
                }
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inProcess) {
                    inProcess = false;
                    start.setBackgroundResource(R.drawable.button_background);
                    start.setText("Start");
                } else {
                    Log.i("step", "1");
                    inProcess = true;
                    executeThread = new Thread(() -> executeQueries(listQueryInputLive,
                                                            listQueryDataLive));
                    executeThread.start();
                    start.setBackgroundResource(R.drawable.running_button_background);
                    start.setText("Stop");
                }
            }
        });
        /*myQueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, myQueriesFragment);
                transaction.commit();
            }
        });*/

        authorizationStatus = 0;

        integerLive = new TypeLive<>(0);
        integerLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    synchronized (thread) {
                        thread.notify();
                    }
                }
            }
        });

        stringLive = new TypeLive<>("");
        stringLive.getStatusLive().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    synchronized (thread) {
                        thread.notify();
                    }
                }
            }
        });

        Updater.setMainActivity(this);
        if (!Updater.isOnRun()) {
            Updater updater = new Updater();
            updater.start();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IN_PROCESS_KEY, inProcess);
    }

    private void checkAuthorizationStatus(TypeLive<Integer> typeLive) {
        CheckManager checkManager = new CheckManager(getApplicationContext(), LongTermStorage.getToken(getApplicationContext()));
        checkManager.check(typeLive);

        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        authorizationStatus = typeLive.getData();

        visualizeAuthorizationStatusCallThread(authorizationStatus);
    }

    private void getFaceName(TypeLive<String> typeLive) {
        FaceManager faceManager = new FaceManager(getApplicationContext(), LongTermStorage.getToken(getApplicationContext()));
        faceManager.getCurrentName(typeLive);
        synchronized (thread) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String currentFaceName = typeLive.getData();
        faceName = currentFaceName;

        name.setText(faceName);
    }

    private void executeQueries(TypeLive<List<QueryInput>> listQueryInputLive,
                                TypeLive<List<QueryData>> listQueryDataLive) {
        Log.i("step", "2");
        QueryReceiptManager queryReceiptManager =
                new QueryReceiptManager(getApplicationContext(), LongTermStorage.getToken(getApplicationContext()));
        QueryExecutionManager queryExecutionManager =
                new QueryExecutionManager(getApplicationContext());
        SendingResultManager sendingResultManager =
                new SendingResultManager(getApplicationContext(), LongTermStorage.getToken(getApplicationContext()));
        Log.i("step", "3");

        for (;inProcess;) {
            Log.i("executeQueries", "check");
            Log.i("authorizationStatus", String.valueOf(authorizationStatus));

            if (authorizationStatus != 3) {
                Log.e("executeQueries", "authorizationStatus != 3");

                inProcess = false;
                start.setBackgroundResource(R.drawable.button_background);
                start.setText("Error");

                return;
            }
            Log.i("step", "4");
            listQueryInputLive.clearAll();
            listQueryDataLive.clearAll();

            Log.i("executeQueries", "get");
            queryReceiptManager.run(listQueryInputLive);
            Log.i("step", "5");
            synchronized (executeThread) {
                try {
                    executeThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.i("step", "6");
            if (listQueryInputLive.getData().isEmpty()) {
                Log.i("executeQueries", "continue");
                continue;
            }
            Log.i("step", "7");

            List<QueryData> inputData = new ArrayList<>();
            for (QueryInput queryInput : listQueryInputLive.getData()) {
                QueryData queryData = new QueryData(queryInput.getUrl(), queryInput.getId());
                queryData.setParameters(queryInput.getParameters());
                queryData.setQueryHeaders(queryInput.getQueryHeaders());
                queryData.setQueryBody(queryInput.getQueryBody());
                inputData.add(queryData);
            }
            Log.i("step", "8");

            Log.i("executeQueries", "run");

            queryExecutionManager.setData(inputData);
            queryExecutionManager.setOutputData(listQueryDataLive);
            queryExecutionManager.run();

            synchronized (executeThread) {
                try {
                    executeThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<ResponseOutput> outputData = new ArrayList<>();
            for (QueryData queryData : listQueryDataLive.getData()) {
                outputData
                        .add(
                                new ResponseOutput(
                                        queryData.getId(),
                                        queryData.getStatusCode(),
                                        queryData.getResponseHeaders(),
                                        queryData.getResponseBody(),
                                        queryData.isError()
                                )
                        );
            }

            Log.i("executeQueries", "set");
            sendingResultManager.run(outputData);
        }
        Log.i("step", "4");

    }

    public void updateCallThread() {
        integerLive.clearAll();
        Log.i("token", LongTermStorage.getToken(getApplicationContext()));
        thread = new Thread(() -> checkAuthorizationStatus(integerLive));
        thread.start();
    }

    private void visualizeAuthorizationStatusCallThread(int authorizationStatus) {
        Log.i("authorizationStatus", String.valueOf(authorizationStatus));
        if (authorizationStatus == 1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    username.setText(LongTermStorage.getUsername(MainActivity.this));
                    name.setText("Not authorized");
                    MainActivity.this.status.setText("Not ready to work");
                }
            });
        } else if (authorizationStatus == 3) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    username.setText(LongTermStorage.getUsername(MainActivity.this));
                    MainActivity.this.status.setText("Ready to work");
                }
            });
            stringLive.clearAll();
            getFaceName(stringLive);

        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    username.setText("Not authorized");
                    name.setText("Not authorized");
                    MainActivity.this.status.setText("Not ready to work");
                }
            });

        }
    }
}

