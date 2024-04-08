package org.grabbing.devicepart;

import android.content.Context;
import android.util.Log;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.domain.QuickCompletion;
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
        accountManager.authorizeQuery(faceManagerQuery);
        accountManager.authorizeQuery(queryReceiptManagerQuery);
        accountManager.authorizeQuery(sendingResultManagerQuery);

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


    @Override
    public void run() {
        for (;;) {
            if (!tasks.isEmpty()) {
                Task task = tasks.poll();
                if (task.data == 0) {
                    authorize(task.getUsername(), task.getPassword(), task.getAccountManagerQuery());
                } else if (task.data == 1) {
                    executeQueries(task.getQuickCompletion());
                }
            }
        }
    }


    public boolean authorize(String username, String password, QueryData accountManagerQuery) {
        accountManager.setQuery(accountManagerQuery);
        accountManager.generateToken(username, password);
        for (;!accountManager.isHasResponse();) {}
        return accountManager.getToken().isEmpty();
    }

    public boolean executeQueries(QuickCompletion quickCompletion) {
        try {
            for (;!quickCompletion.isStop();) {
                Log.i("Executor.executeQueries * start", "start");
                Log.i("Executor.executeQueries * get", "get");
                queryReceiptManager.run();
                for (; !queryReceiptManager.isHasResponse(); ) {}
                List<QueryData> queryDataList = queryReceiptManager.getData();

                Log.i("Executor.executeQueries * run", "run");
                queryExecutionManager.setData(queryDataList);
                queryExecutionManager.run();
                for (; !queryExecutionManager.isDone(); ) {}

                Log.i("Executor.executeQueries * set", "set");
                sendingResultManager.setData(queryExecutionManager.getData());
                sendingResultManager.run();

                Log.i("Executor.executeQueries * finish", "finish");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public String getToken() {return accountManager.getToken();}

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
