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
import org.grabbing.devicepart.hooks.Hook;
import org.grabbing.devicepart.hooks.TypeHook;
import org.grabbing.devicepart.livedata.TypeLive;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpPost implements HttpQuery {
    private List<QueryData> data;
    private final Context context;
    private Hook hookForArray;
    private static RequestQueue requestQueue;


    public HttpPost(Context context) {
        this.data = null;
        this.context = context;
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    @Override
    public void setData(List<QueryData> data) {
        this.data = data;
    }
    @Override
    public void setHook(Hook hook) {this.hookForArray = hook;}


    @Override
    public void run() {
        if (data == null) {
            throw new RuntimeException("data == null");
        }

        for (int i = 0; i < data.size(); i++) {
            int finalI = i;
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    data.get(finalI).setResponseBody(response);
                    data.get(finalI).setHasResponse(true);
                    hookForArray.capture(data.get(finalI));
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    data.get(finalI).setError(true);
                    hookForArray.capture(data.get(finalI));
                }
            };

            StringRequest stringRequest = new StringRequest(Request.Method.POST, data.get(i).getUrl(), listener, errorListener) {
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

    @Override
    public void runRightAway(QueryData query, Hook hook) {
        Log.i("HttpQuery.HttpPost.runRightAway * start", query.getUrl());

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                query.setResponseBody(response);
                query.setHasResponse(true);
                Log.i("HttpQuery.HttpPost.runRightAway * response", response);
                hook.capture(query);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                query.setError(true);
                if (error.networkResponse == null) {
                    query.setStatusCode(-1);
                } else {
                    query.setStatusCode(error.networkResponse.statusCode);
                }
                Log.i("HttpQuery.HttpPost.runRightAway * error", "error");
                hook.capture(query);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST, query.getUrl(), listener, errorListener) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                query.setStatusCode(response.statusCode);
                query.setResponseHeaders(response.headers);

                return super.parseNetworkResponse(response);
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return query.getParameters();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return query.getQueryBody().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return query.getQueryHeaders();
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void runRightAway(QueryData query, Hook hook, TypeLive<Integer> statusCode) {
        Log.i("HttpQuery.HttpPost.runRightAway * start", query.getUrl());

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                query.setResponseBody(response);
                query.setHasResponse(true);
                Log.i("HttpQuery.HttpPost.runRightAway * response", response);
                hook.capture(query);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                query.setError(true);
                if (error.networkResponse == null) {
                    statusCode.setData(-1);
                    query.setStatusCode(-1);
                } else {
                    statusCode.setData(error.networkResponse.statusCode);
                    query.setStatusCode(error.networkResponse.statusCode);
                }
                statusCode.setStatus(true);
                Log.i("HttpQuery.HttpPost.runRightAway * error", "error");
                hook.capture(query);
            }
        };


        StringRequest stringRequest = new StringRequest(Request.Method.POST, query.getUrl(), listener, errorListener) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statusCode.setData(response.statusCode);
                statusCode.setStatus(true);
                query.setStatusCode(response.statusCode);
                query.setResponseHeaders(response.headers);

                return super.parseNetworkResponse(response);
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return query.getParameters();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return query.getQueryBody().getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return query.getQueryHeaders();
            }
        };
        requestQueue.add(stringRequest);
    }
}
