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

    private boolean hasToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LongTermStorage.saveToken("token-bbb", this.getApplicationContext());

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

        if (hasToken) {
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


        executor.init(LongTermStorage.queryReceiptManagerQuery,
                LongTermStorage.sendingResultManagerQuery,
                LongTermStorage.faceManagerQuery,
                LongTermStorage.checkManagerQuery);

        executor.start();

        if (LongTermStorage.hasSavedToken(this.getApplicationContext())) {
            executor.setToken(LongTermStorage.getToken(this.getApplicationContext()));
            hasToken = true;
        } else {
            hasToken = false;
        }

    }

    @Override
    public void finishNow() {}

    @Override
    public void setError(String error) {
        Toast.makeText(this.getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    public void setFaceName(String name) {
        this.name.setText(name);
    }

    public void setAuthStatus(int n) {
        if (n == 0) {

        } else if (n == 1) {
            username.setText(LongTermStorage.getUsername(this.getApplicationContext()));
            name.setText("not authorized");
        } else if (n == 3) {
            username.setText(LongTermStorage.getUsername(this.getApplicationContext()));
            StaticStorage.getExecutor().getFaceName();
        }
    }  

    /*private void testInitButtonV2() {
        Button test = findViewById(R.id.test_run);
        Button stop = findViewById(R.id.test_stop);
        TextView textView = findViewById(R.id.test_output);

        Executor executor = new Executor(getApplicationContext());
        executor.start();

        QueryDataListLive listLive = new QueryDataListLive();
        TokenLive tokenLive = new TokenLive();
        ListOfUsersLive usersLive = new ListOfUsersLive();
        BooleanLive booleanLive = new BooleanLive();
        IntegerLive integerLive = new IntegerLive();
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

        executor.setUsersLive(usersLive);
        executor.setListLive(listLive);
        executor.setTokenLive(tokenLive);
        executor.setBooleanLive(booleanLive);
        executor.setIntegerLive(integerLive);

        executor.authorize("login", "password", new QueryData("http://94.103.92.242:8090/au", -1));


        executor.init(new QueryData("http://94.103.92.242:8090/", -3),
                new QueryData("http://94.103.92.242:8090/ret", -4),
                new QueryData("http://94.103.92.242:8090/false", -2),
                new QueryData("http://94.103.92.242:8090/yes", -5));


        QuickCompletion quickCompletion = new QuickCompletion();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //executor.registerFace("name");
                quickCompletion.setStop(false);
                executor.executeQueries(quickCompletion);
                Log.d("MAIN * on click", "onClick: ");
                //executor.setTask(2);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickCompletion.setStop(true);
                //textView.setText(usersLive.getListOfUsers().toString());
            }
        });
    }*/

    /*private void testInitButton() {
        EditText url = findViewById(R.id.e_url);
        EditText login = findViewById(R.id.e_login);
        EditText password = findViewById(R.id.e_password);
        EditText token = findViewById(R.id.e_token);
        EditText name = findViewById(R.id.e_name);
        EditText username = findViewById(R.id.e_username);

        Button getToken = findViewById(R.id.b_get_token);
        Button useToken = findViewById(R.id.b_use_token);
        Button register = findViewById(R.id.b_register);
        Button attach = findViewById(R.id.b_attach);
        Button detach = findViewById(R.id.b_detach);
        Button get = findViewById(R.id.b_get);
        Button run = findViewById(R.id.b_run);
        Button set = findViewById(R.id.b_set);

        TextView console = findViewById(R.id.console);

        AccountManager accountManager = new AccountManager(getApplicationContext());
        FaceManager faceManager = new FaceManager(getApplicationContext());
        QueryExecutionManager queryExecutionManager = new QueryExecutionManager(getApplicationContext());
        QueryReceiptManager queryReceiptManager = new QueryReceiptManager(getApplicationContext());
        SendingResultManager sendingResultManager = new SendingResultManager(getApplicationContext());

        getToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryData queryData = new QueryData(url.getText().toString(), -1);
                accountManager.setQuery(queryData);

                accountManager.generateToken(login.getText().toString(), password.getText().toString());
            }
        });


        useToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //accountManager.setToken(token.getText().toString());
                Log.i("token", accountManager.getToken());
                console.setText(accountManager.getToken());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryData queryData = new QueryData(url.getText().toString(), -1);
                accountManager.authorizeQuery(queryData);
                faceManager.setQuery(queryData);

                faceManager.register(name.getText().toString());
            }
        });
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryData queryData = new QueryData(url.getText().toString(), -1);
                accountManager.authorizeQuery(queryData);
                faceManager.setQuery(queryData);

                faceManager.attach(username.getText().toString());
            }
        });
        detach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryData queryData = new QueryData(url.getText().toString(), -1);
                accountManager.authorizeQuery(queryData);
                faceManager.setQuery(queryData);

                faceManager.detach(username.getText().toString());
            }
        });

        final List<QueryData>[] queryDataList = new List[]{new ArrayList<>()};

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryData queryData = new QueryData(url.getText().toString(), -1);
                accountManager.authorizeQuery(queryData);
                queryReceiptManager.setQuery(queryData);

                queryReceiptManager.run();


            }
        });

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryExecutionManager.setData(queryReceiptManager.getData());
                queryExecutionManager.run();
                queryDataList[0] = queryExecutionManager.getData();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryData queryData = new QueryData(url.getText().toString(), -1);
                accountManager.authorizeQuery(queryData);
                sendingResultManager.setQuery(queryData);

                sendingResultManager.setData(queryDataList[0]);
                sendingResultManager.run();
            }
        });

    }*/
}