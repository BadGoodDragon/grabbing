package org.grabbing.devicepart.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.grabbing.devicepart.domain.QueryData;
import org.grabbing.devicepart.jsonforconversion.QueryInput;

import java.util.ArrayList;
import java.util.List;

public class JsonToListOfQueryData {
    private String json;

    public void setJson(String json) {this.json = json;}

    public void convert(List<QueryData> data) {
        Gson gson = new Gson();

        try {
            List<QueryInput> queryInputs = gson.fromJson(json, new TypeToken<List<QueryInput>>() {}.getType());

            /*for (int i = 0; i < incomingQueries.getQuantity(); i++){
                QueryData queryData = new QueryData(incomingQueries.getUrl().get(String.valueOf(i)), incomingQueries.getId().get(String.valueOf(i)));
                queryData.setParameters(incomingQueries.getParameters().get(String.valueOf(i)));
                queryData.setQueryHeaders(incomingQueries.getHeaders().get(String.valueOf(i)));
                queryData.setQueryBody(incomingQueries.getBody().get(String.valueOf(i)));
                data.add(queryData);
            }*/

            for (QueryInput queryInput : queryInputs) {
                QueryData queryData = new QueryData(queryInput.getUrl(), queryInput.getId());
                queryData.setParameters(queryInput.getParameters());
                queryData.setQueryHeaders(queryInput.getQueryHeaders());
                queryData.setQueryBody(queryInput.getQueryBody());
                data.add(queryData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
