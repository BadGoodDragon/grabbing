package org.grabbing.devicepart;

import android.content.Context;
import android.util.Log;

import org.grabbing.devicepart.data.storage.LongTermStorage;
import org.grabbing.devicepart.data.storage.StaticStorage;
import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.livedata.BooleanLive;
import org.grabbing.devicepart.livedata.IntegerLive;
import org.grabbing.devicepart.livedata.ListOfUsersLive;
import org.grabbing.devicepart.livedata.QueryDataListLive;
import org.grabbing.devicepart.livedata.StringLive;
import org.grabbing.devicepart.managers.CheckManager;
import org.grabbing.devicepart.managers.UIManager;
import org.grabbing.devicepart.wrappers.QuickCompletion;
import org.grabbing.devicepart.managers.AccountManager;
import org.grabbing.devicepart.managers.FaceManager;
import org.grabbing.devicepart.managers.QueryExecutionManager;
import org.grabbing.devicepart.managers.QueryReceiptManager;
import org.grabbing.devicepart.managers.SendingResultManager;

import java.util.LinkedList;
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
    private StringLive tokenLive;
    private ListOfUsersLive usersLive;
    private BooleanLive booleanLive;
    private IntegerLive integerLive;
    private StringLive stringLive;

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

    public void init(QueryData accountManagerQuery, QueryData queryReceiptManagerQuery, QueryData sendingResultManagerQuery, QueryData faceManagerQuery, QueryData checkManagerQuery) {
        accountManager.setQuery(accountManagerQuery);
        faceManager.setQuery(faceManagerQuery);
        queryReceiptManager.setQuery(queryReceiptManagerQuery);
        sendingResultManager.setQuery(sendingResultManagerQuery);
        checkManager.setQuery(checkManagerQuery);
    }

    public void setToken(String token) {
        AccountManager.authorizeQuery(faceManager.getQuery(), token);
        AccountManager.authorizeQuery(queryReceiptManager.getQuery(), token);
        AccountManager.authorizeQuery(sendingResultManager.getQuery(), token);
        AccountManager.authorizeQuery(checkManager.getQuery(), token);
    }


    public void setListLive(QueryDataListLive listLive) {this.listLive = listLive;}
    public void setTokenLive(StringLive tokenLive) {this.tokenLive = tokenLive;}
    public void setUsersLive(ListOfUsersLive usersLive) {this.usersLive = usersLive;}
    public void setBooleanLive(BooleanLive booleanLive) {this.booleanLive = booleanLive;}
    public void setIntegerLive(IntegerLive integerLive) {this.integerLive = integerLive;}
    public void setStringLive(StringLive stringLive) {this.stringLive = stringLive;}

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

    public void authorize(String username, String password) {
        queueUnloadedThreads.add(new Thread(() -> authorizeSync(username, password)));
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
    public void getFaceName() {
        queueUnloadedThreads.add(new Thread(() -> getFaceNameSync()));
    }
    public void check() {
        queueUnloadedThreads.add(new Thread(() -> checkSync()));
    }



    public boolean authorizeSync(String username, String password) {
        tokenLive.clearAll();

        accountManager.setToken(tokenLive);
        accountManager.generateToken(username, password);

        synchronized (runningThread) {
            try {
                runningThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        if (!tokenLive.getData().isEmpty()) {
            accountManager.authorizeQuery(faceManager.getQuery());
            accountManager.authorizeQuery(queryReceiptManager.getQuery());
            accountManager.authorizeQuery(sendingResultManager.getQuery());
            accountManager.authorizeQuery(checkManager.getQuery());

            UIManager.dataTransmission("token", tokenLive.getData());

            LongTermStorage.saveToken(tokenLive.getData(), context);
            UIManager.nextStep();

            return true;
        } else {
            UIManager.setError("authorization error");

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
                    runningThread.wait();
                }

                Log.i("Executor.executeQueries * run", "run");

                queryExecutionManager.setData(listLive.get());
                listLive.clearAll();
                queryExecutionManager.setOutputData(listLive);
                queryExecutionManager.run();

                synchronized (runningThread) {
                    runningThread.wait();
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
                runningThread.wait();
            }
            if (!usersLive.getListOfUsers().isEmpty()) {
                UIManager.dataTransmission("list of linked users", usersLive.getListOfUsers());
            } else {
                UIManager.setError("error");
            }
        } catch (Exception e) {
            UIManager.setError("error");
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
                    UIManager.setError("register error");
                }
            }

            if (booleanLive.getData()) {
                UIManager.nextStep();
            } else {
                UIManager.setError("register error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            UIManager.setError("register error");
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
                runningThread.wait();
            }

            if (booleanLive.getData()) {
                UIManager.nextStep();
            } else {
                UIManager.setError("register error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            UIManager.setError("register error");
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
    public boolean getFaceNameSync(){
        stringLive.clearAll();

        faceManager.setStringLive(stringLive);
        faceManager.getCurrentName();

        synchronized (runningThread) {
            try {
                runningThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!stringLive.getData().isEmpty()) {

            Log.i("TAG", "!isEmpty");

            //UIManager.getMainActivity().setFaceName(stringLive.getData());
            UIManager.dataTransmission("face name", stringLive.getData());

            return true;
        } else {
            UIManager.setError("error");
            return false;
        }
    }
    public boolean checkSync() {
        checkManager.setIntegerLive(integerLive);
        checkManager.check();
        synchronized (runningThread) {
            try {
                runningThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        //UIManager.getMainActivity().setAuthStatus(integerLive.getData());
        UIManager.dataTransmission("authorization status", integerLive.getData());

        return true;
    }
}

