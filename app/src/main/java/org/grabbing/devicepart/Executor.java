package org.grabbing.devicepart;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Observer;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.IntegerLive;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.QueryDataListLive;
import org.grabbing.devicepart.livedata.TokenLive;
import org.grabbing.devicepart.managers.CheckManager;
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
    private CheckManager checkManager;

    private QueryDataListLive listLive;
    private TokenLive tokenLive;
    private ListOfUsersLive usersLive;
    private BooleanLive booleanLive;
    private IntegerLive integerLive;

    private Thread runningThread;
    private Queue<Thread> queueUnloadedThreads;

    public Executor(Context context) {
        this.context = context;
        accountManager = new AccountManager(context);
        faceManager = new FaceManager(context);
        queryExecutionManager = new QueryExecutionManager(context);
        queryReceiptManager = new QueryReceiptManager(context);
        sendingResultManager = new SendingResultManager(context);
        checkManager = new CheckManager(context);
        queueUnloadedThreads = new LinkedList<>();
    }

    public void init(QueryData queryReceiptManagerQuery, QueryData sendingResultManagerQuery, QueryData faceManagerQuery, QueryData checkManagerQuery) {
        faceManager.setQuery(faceManagerQuery);
        queryReceiptManager.setQuery(queryReceiptManagerQuery);
        sendingResultManager.setQuery(sendingResultManagerQuery);
        checkManager.setQuery(checkManagerQuery);
    }

    public void setToken(String token) {
        accountManager.setToken(token);
    }


    public void setListLive(QueryDataListLive listLive) {this.listLive = listLive;}
    public void setTokenLive(TokenLive tokenLive) {this.tokenLive = tokenLive;}
    public void setUsersLive(ListOfUsersLive usersLive) {this.usersLive = usersLive;}
    public void setBooleanLive(BooleanLive booleanLive) {this.booleanLive = booleanLive;}
    public void setIntegerLive(IntegerLive integerLive) {this.integerLive = integerLive;}

    public void resumeRunningThread() {
        synchronized (runningThread) {
            runningThread.notify();
        }
    }

    @Override
    public void run() {
        runningThread = new Thread();
        for (;;) {
            if (queueUnloadedThreads.isEmpty() || runningThread.isAlive()) {
                /*Log.i("Tasks", "No tasks");
                Log.i("queue", String.valueOf(queueUnloadedThreads.size()));
                Log.i("empty", String.valueOf(queueUnloadedThreads.isEmpty()));
                Log.i("allive", String.valueOf(runningThread.isAlive()));*/
                continue;
            }
            runningThread = queueUnloadedThreads.poll();

            /*Log.i("Tasks", "run");
            Log.i("queue", String.valueOf(queueUnloadedThreads.size()));*/

            runningThread.start();

        }
    }

    public void authorize(String username, String password, QueryData accountManagerQuery) {
        queueUnloadedThreads.add(new Thread(() -> authorizeSync(username, password, accountManagerQuery)));
    }
    public void executeQueries(QuickCompletion quickCompletion) {
        queueUnloadedThreads.add(new Thread(() -> executeQueriesSync(quickCompletion)));
    }
    public void getListOfLinkedUsers() {
        queueUnloadedThreads.add(new Thread(() -> getListOfLinkedUsersSync()));
    }
    public void registerAccount(String username, String password) {
        queueUnloadedThreads.add(new Thread(() -> registerAccountSync(username, password)));
    }
    public void registerFace(String name) {
        queueUnloadedThreads.add(new Thread(() -> registerFaceSync(name)));
    }
    public void attachFace(String name) {
        queueUnloadedThreads.add(new Thread(() -> attachFaceSync(name)));
    }
    public void detachFace(String name) {
        queueUnloadedThreads.add(new Thread(() -> detachFaceSync(name)));
    }

    public boolean authorizeSync(String username, String password, QueryData accountManagerQuery) {
        Log.i("authorizeSync", "start");

        tokenLive.clearAll();
        Log.i("authorizeSync", "clear");

        accountManager.setQuery(accountManagerQuery);
        accountManager.setToken(tokenLive);
        accountManager.generateToken(username, password);
        Log.i("authorizeSync", "generate");

        synchronized (runningThread) {
            try {
                runningThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("authorizeSync", "wake up");


        Log.i("main test t", tokenLive.getToken());

        Log.i("END", "END");
        if (!tokenLive.getToken().isEmpty()) {
            accountManager.authorizeQuery(faceManager.getQuery());
            accountManager.authorizeQuery(queryReceiptManager.getQuery());
            accountManager.authorizeQuery(sendingResultManager.getQuery());
            accountManager.authorizeQuery(checkManager.getQuery());

            return true;
        } else {
            return false;
        }
    }
    public boolean executeQueriesSync(QuickCompletion quickCompletion) {

        integerLive.clearAll();

        checkManager.setIntegerLive(integerLive);
        checkManager.check();
        synchronized (runningThread) {
            try {
                runningThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (integerLive.getData() != 3) {
            Log.e("STOP", "NON AUTH");
            return false;
        }


        listLive.clearAll();

        try {

            for (;!quickCompletion.isStop();) {
                listLive.clearAll();

                Log.i("Executor.executeQueries * get", "get");

                queryReceiptManager.setData(listLive);
                queryReceiptManager.run();

                synchronized (runningThread) {
                    try {
                        runningThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.i("Executor.executeQueries * run", "run");

                queryExecutionManager.setData(listLive.get());
                listLive.clearAll();
                queryExecutionManager.setOutputData(listLive);
                queryExecutionManager.run();

                synchronized (runningThread) {
                    try {
                        runningThread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
    public boolean getListOfLinkedUsersSync() {
        usersLive.clearAll();

        try {
            faceManager.setLinkedUsers(usersLive);
            faceManager.getListOfLinkedUsers();

            synchronized (runningThread) {
                try {
                    runningThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean registerAccountSync(String username, String password) {
        booleanLive.clearAll();

        try {
            accountManager.setBooleanLive(booleanLive);
            accountManager.register(username, password);

            synchronized (runningThread) {
                try {
                    runningThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean registerFaceSync(String name) {
        booleanLive.clearAll();

        try {
            faceManager.setResponses(booleanLive);
            faceManager.register(name);

            synchronized (runningThread) {
                try {
                    runningThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.i("registerFaceSync", String.valueOf(booleanLive.getData()));
            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean attachFaceSync(String name) {
        booleanLive.clearAll();

        try {
            faceManager.setResponses(booleanLive);
            faceManager.attach(name);

            synchronized (runningThread) {
                try {
                    runningThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean detachFaceSync(String name) {
        booleanLive.clearAll();

        try {
            faceManager.setResponses(booleanLive);
            faceManager.detach(name);

            synchronized (runningThread) {
                try {
                    runningThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

/*public class Executor extends Thread {
    private final Context context;

    private AccountManager accountManager;
    private FaceManager faceManager;
    private QueryExecutionManager queryExecutionManager;
    private QueryReceiptManager queryReceiptManager;
    private SendingResultManager sendingResultManager;

    private QueryDataListLive listLive;
    private TokenLive tokenLive;
    private ListOfUsersLive usersLive;
    private BooleanLive booleanLive;

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
        public Task(String username, String password) {
            this.username = username;
            this.password = password;
            data = 3;
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
    public void setTask(String username, String password) {
        tasks.add(new Task(username, password));
    }
    public void setTask(String name, int id) {

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
                } else if (task.data == 3) {

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

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean registerAccount(String username, String password) {
        booleanLive.clearAll();

        try {
            accountManager.setBooleanLive(booleanLive);
            accountManager.register(username, password);

            synchronized (this) {
                wait();
            }

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean registerFace(String name) {
        booleanLive.clearAll();

        try {
            faceManager.setResponses(booleanLive);
            faceManager.register(name);

            synchronized (this) {
                wait();
            }

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean attachFace(String name) {
        booleanLive.clearAll();

        try {
            faceManager.setResponses(booleanLive);
            faceManager.attach(name);

            synchronized (this) {
                wait();
            }

            //TODO: use data
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean detachFace(String name) {
        booleanLive.clearAll();

        try {
            faceManager.setResponses(booleanLive);
            faceManager.detach(name);

            synchronized (this) {
                wait();
            }

            //TODO: use data
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
}*/

