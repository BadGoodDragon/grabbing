package org.grabbing.devicepart.data.http;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.grabbing.devicepart.domain.QueryData;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpGet implements HttpQuery {
    private List<QueryData> data;
    private final Context context;

    public HttpGet(Context context) {
        this.data = null;
        this.context = context;
    }

    @Override
    public void setData(List<QueryData> data) {
        this.data = data;
    }

    @Override
    public void run() {
        if (data == null) {
            throw new RuntimeException("data == null");
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        for (int i = 0; i < data.size(); i++) {
            int finalI = i;
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    data.get(finalI).setResponseBody(response);
                    data.get(finalI).setHasResponse(true);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    data.get(finalI).setError(true);
                    error.printStackTrace();
                }
            };

            StringRequest stringRequest = new StringRequest(Request.Method.GET, data.get(i).getUrl(), listener, errorListener) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    data.get(finalI).setStatusCode(response.statusCode);
                    data.get(finalI).setResponseHeaders(response.headers);

                    return super.parseNetworkResponse(response);
                }

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return data.get(finalI).getParameters();
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return data.get(finalI).getQueryBody().getBytes(StandardCharsets.UTF_8);
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return data.get(finalI).getQueryHeaders();
                }
            };
            requestQueue.add(stringRequest);
        }

    }

    @Override
    public List<QueryData> getData() {
        return data;
    }
}
