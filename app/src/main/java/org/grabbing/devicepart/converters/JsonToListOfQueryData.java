package org.grabbing.devicepart.converters;

import com.google.gson.Gson;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.jsonforconversion.IncomingQueries;

import java.util.ArrayList;
import java.util.List;

public class JsonToListOfQueryData {
    private String json;

    public void setJson(String json) {this.json = json;}

    public void convert(List<QueryData> data) {
        Gson gson = new Gson();

        try {
            IncomingQueries incomingQueries = gson.fromJson(json, IncomingQueries.class);

            for (int i = 0; i < incomingQueries.getQuantity(); i++){
                QueryData queryData = new QueryData(incomingQueries.getUrl().get(String.valueOf(i)), incomingQueries.getId().get(String.valueOf(i)));
                queryData.setParameters(incomingQueries.getParameters().get(String.valueOf(i)));
                queryData.setQueryHeaders(incomingQueries.getHeaders().get(String.valueOf(i)));
                queryData.setQueryBody(incomingQueries.getBody().get(String.valueOf(i)));
                data.add(queryData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
