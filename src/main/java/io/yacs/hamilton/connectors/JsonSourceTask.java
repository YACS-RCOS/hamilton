package io.yacs.hamilton.connectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class JsonSourceTask extends SourceTask {
    String topic;
    String endpoint;
    Long pollInterval;

    @Override
    public String version() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void start(Map<String, String> props) {
        this.topic = props.get(JsonSourceConnector.TOPIC_CONFIG);
        this.endpoint = props.get(JsonSourceConnector.ENDPOINT_CONFIG);
        this.pollInterval = Long.valueOf(props.get(JsonSourceConnector.POLL_INTERVAL_CONFIG));
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        ArrayList<SourceRecord> records = new ArrayList<>();
        JSONArray sourceData = getDataSince(null);
        if (sourceData == null) {
            return null;
        }
        while (records.isEmpty()) {
            if (sourceData.length() > 0) {
                sourceData.forEach(item -> {
                    JSONObject record = (JSONObject) item;
                    records.add(new SourceRecord(null, null, topic, Schema.STRING_SCHEMA, record.toString()));
                });
            } else {
                Thread.sleep(this.pollInterval.longValue() * 1000L);
            }
        }
        return records;
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    private JSONArray getDataSince(String timestamp) {
        JSONArray data = null;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(this.endpoint)
                    .header("accept", "application/json")
                    .queryString("since", timestamp)
                    .asJson();
            data = jsonResponse.getBody()
                    .getObject()
                    .getJSONArray("data");
        } catch (UnirestException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }
}
