package org.grabbing.devicepart.converters;

import com.google.gson.Gson;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.dto.ResponseOutput;

import java.util.ArrayList;
import java.util.List;

public class ListOfQueryDataToJson {
    private List<QueryData> data;
    private String json;

    public void setData(List<QueryData> data) {this.data = data;}

    public void convert() {
        json = new String();

        List<ResponseOutput> responseOutputs = new ArrayList<>();

        /*Map<String, Integer> statusCode = new HashMap<>();
        Map<String, Map<String, String>> responseHeaders = new HashMap<>();
        Map<String, String> responseBody = new HashMap<>();
        Map<String, Boolean> error = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            QueryData queryData = data.get(i);

            statusCode.put(String.valueOf(i), queryData.getStatusCode());
            responseHeaders.put(String.valueOf(i), queryData.getResponseHeaders());
            responseBody.put(String.valueOf(i), queryData.getResponseBody());
            error.put(String.valueOf(i), queryData.isError());
        }

        OutgoingQueries outgoingQueries = new OutgoingQueries(data.size(), statusCode, responseHeaders, responseBody, error);*/

        for (QueryData queryData : data) {
            responseOutputs
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

        Gson gson = new Gson();

        json = gson.toJson(responseOutputs);
    }

    public String getJson() {return json;}
}
