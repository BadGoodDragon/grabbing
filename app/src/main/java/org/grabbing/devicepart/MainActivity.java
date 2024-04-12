package org.grabbing.devicepart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.IntegerLive;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.QueryDataListLive;
import org.grabbing.devicepart.livedata.TokenLive;
import org.grabbing.devicepart.wrappers.QuickCompletion;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //testInitButton();
        //testInitButtonV2();
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