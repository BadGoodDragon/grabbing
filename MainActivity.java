package org.grabbing.devicepart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.grabbing.devicepart.data.http.HttpGet;
import org.grabbing.devicepart.domain.QueryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testInitButton();
    }

    private void testInitButton() {
        Button qeBtn = findViewById(R.id.run);
        TextView qe = findViewById(R.id.qe_text);

        /*HttpQueryManager httpQueryManager = new HttpQueryManager(this.getApplicationContext(), 4);

        List<QueryAndResponseStorageHttp> queryAndResponseStorageHttpList = new ArrayList<>();
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(0, "http://5.35.85.112:8080/"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(1, "http://5.35.85.112:8080/"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(2, "http://5.35.85.112:8080/"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(3, "http://5.35.85.112:8080/"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(4, "http://5.35.85.112:8080/stop"));

        httpQueryManager.setData(queryAndResponseStorageHttpList);
        httpQueryManager.init();

        qeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpQueryManager.launch();
                httpQueryManager.complete();
                List<QueryAndResponseStorageHttp> data = httpQueryManager.getData();

                qe.setText(data.get(4).getResponse());
            }
        });*/

        /*HttpGet httpGet = new HttpGet(this.getApplicationContext());

        List<QueryAndResponseStorageHttp> queryAndResponseStorageHttpList = new ArrayList<>();
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(0, "http://5.35.85.112:8080/a"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(1, "http://5.35.85.112:8080/b"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(2, "http://5.35.85.112:8080/c"));
        queryAndResponseStorageHttpList.add(new QueryAndResponseStorageHttp(3, "http://5.35.85.112:8080/d"));

        httpGet.setData(queryAndResponseStorageHttpList);

        qeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpGet.run();
                qe.setText(httpGet.getData().get(3).getResponse());
            }
        });*/
        List<QueryData> data = new ArrayList<>();
        data.add(new QueryData("https://ya.ru/", 0));
        Map<String, String> params = new HashMap<String, String>();
        params.put("User-Agent", "PostmanRuntime/7.37.0");
        data.get(0).setQueryHeaders(params);

        HttpGet httpGet = new HttpGet(this.getApplicationContext());

        httpGet.setData(data);

        qeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpGet.run();
                qe.setText(""+(httpGet.getData().get(0).getResponseHeaders() == null));
                if(httpGet.getData().get(0).getResponseHeaders() != null) {
                    qe.setText(httpGet.getData().get(0).getResponseHeaders().toString());
                }
            }
        });

    }
}