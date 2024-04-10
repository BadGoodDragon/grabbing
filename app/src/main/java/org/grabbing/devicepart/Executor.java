package org.grabbing.devicepart;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Observer;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.QueryDataListLive;
import org.grabbing.devicepart.livedata.TokenLive;
import org.grabbing.devicepart.wrappers.QuickCompletion;
import org.grabbing.devicepart.managers.AccountManager;
import org.grabbing.devicepart.managers.FaceManager;
import org.grabbing.devicepart.managers.QueryExecutionManager;
import org.grabbing.devicepart.managers.QueryReceiptManager;
import org.grabbing.devicepart.managers.SendingResultManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Executor extends Thread {
    private final Context context;

    private AccountManager accountManager;
    private FaceManager faceManager;
    private QueryExecutionManager queryExecutionManager;
    private QueryReceiptManager queryReceiptManager;
    private SendingResultManager sendingResultManager;
    private QueryDataListLive listLive;
    private TokenLive tokenLive;
    private ListOfUsersLive usersLive;

    public Executor(Context context) {
        this.context = context;
        accountManager = new AccountManager(context);
        faceManager = new FaceManager(context);
        queryExecutionManager = new QueryExecutionManager(context);
        queryReceiptManager = new QueryReceiptManager(context);
        sendingResultManager = new SendingResultManager(context);
        tasks = new LinkedList<>();
    }


    public void setToken(String token) {
        accountManager.setToken(token);
    }

    public void init(QueryData faceManagerQuery, QueryData queryReceiptManagerQuery, QueryData sendingResultManagerQuery) {
        faceManager.setQuery(faceManagerQuery);
        queryReceiptManager.setQuery(queryReceiptManagerQuery);
        sendingResultManager.setQuery(sendingResultManagerQuery);
    }


    public class Task {
        private String username;
        private String password;
        private QueryData accountManagerQuery;
        private QuickCompletion quickCompletion;
        private int data;

        public Task(String username, String password, QueryData accountManagerQuery) {
            this.username = username;
            this.password = password;
            this.accountManagerQuery = accountManagerQuery;
            data = 0;
        }
        public Task(QuickCompletion quickCompletion) {
            this.quickCompletion = quickCompletion;
            data = 1;
        }
        public Task(int data) {
            this.data = data;
        }

        public String getUsername() {return username;}
        public String getPassword() {return password;}
        public QueryData getAccountManagerQuery() {return accountManagerQuery;}
        public QuickCompletion getQuickCompletion() {return quickCompletion;}
        public int getData() {return data;}
    }

    private Queue<Task> tasks;

    public void setTask(String username, String password, QueryData accountManagerQuery) {
        tasks.add(new Task(username, password, accountManagerQuery));
    }
    public void setTask(QuickCompletion quickCompletion) {
        tasks.add(new Task(quickCompletion));
    }
    public void setTask(int id) {
        tasks.add(new Task(id));
    }

    public void setListLive(QueryDataListLive listLive) {this.listLive = listLive;}
    public void setTokenLive(TokenLive tokenLive) {
        this.tokenLive = tokenLive;
    }
    public void setUsersLive(ListOfUsersLive usersLive) {this.usersLive = usersLive;}

    @Override
    public void run() {
        for (;;) {
            if (!tasks.isEmpty()) {
                Task task = tasks.poll();
                if (task.data == 0) {
                    authorize(task.getUsername(), task.getPassword(), task.getAccountManagerQuery());
                } else if (task.data == 1) {
                    executeQueries(task.getQuickCompletion());
                } else if (task.data == 2) {
                    getListOfLinkedUsers();
                }
            }
        }
    }

    public boolean authorize(String username, String password, QueryData accountManagerQuery) {
        tokenLive.clearAll();

        accountManager.setQuery(accountManagerQuery);
        accountManager.setToken(tokenLive);
        accountManager.generateToken(username, password);

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i("main test t", tokenLive.getToken());

        if (!tokenLive.getToken().isEmpty()) {
            accountManager.authorizeQuery(faceManager.getQuery());
            accountManager.authorizeQuery(queryReceiptManager.getQuery());
            accountManager.authorizeQuery(sendingResultManager.getQuery());

            return true;
        } else {
            return false;
        }
    }
    public boolean executeQueries(QuickCompletion quickCompletion) {
        listLive.clearAll();

        try {

            for (;!quickCompletion.isStop();) {
                listLive.clearAll();

                Log.i("Executor.executeQueries * get", "get");

                queryReceiptManager.setData(listLive);
                queryReceiptManager.run();

                synchronized (this) {
                    wait();
                }

                Log.i("Executor.executeQueries * run", "run");

                queryExecutionManager.setData(listLive.get());
                listLive.clearAll();
                queryExecutionManager.setOutputData(listLive);
                queryExecutionManager.run();

                synchronized (this) {
                    wait();
                }

                Log.i("Executor.executeQueries * set", "set");

                sendingResultManager.setData(listLive.get());
                sendingResultManager.run();

            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean getListOfLinkedUsers() {
        usersLive.clearAll();

        try {
            faceManager.setLinkedUsers(usersLive);
            faceManager.getListOfLinkedUsers();

            synchronized (this) {
                wait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    public Context getContext() {return context;}
    public AccountManager getAccountManager() {return accountManager;}
    public FaceManager getFaceManager() {return faceManager;}
    public QueryExecutionManager getQueryExecutionManager() {return queryExecutionManager;}
    public QueryReceiptManager getQueryReceiptManager() {return queryReceiptManager;}
    public SendingResultManager getSendingResultManager() {return sendingResultManager;}

    public void setAccountManager(AccountManager accountManager) {this.accountManager = accountManager;}
    public void setFaceManager(FaceManager faceManager) {this.faceManager = faceManager;}
    public void setQueryExecutionManager(QueryExecutionManager queryExecutionManager) {this.queryExecutionManager = queryExecutionManager;}
    public void setQueryReceiptManager(QueryReceiptManager queryReceiptManager) {this.queryReceiptManager = queryReceiptManager;}
    public void setSendingResultManager(SendingResultManager sendingResultManager) {this.sendingResultManager = sendingResultManager;}
}
