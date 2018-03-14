package io.yacs.hamilton.connectors;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.data.Schema.Type;
import org.apache.kafka.connect.source.SourceConnector;

public class JsonSourceConnector extends SourceConnector {
	public static final String TOPIC_CONFIG = "topic";
	public static final String ENDPOINT_CONFIG = "endpoint";
	public static final String SOURCE_NAME_CONFIG = "source_name";
	public static final String POLL_INTERVAL_CONFIG = "poll_interval";
	
	private String topic;
	private String endpoint;
	private String sourceName;
	private String pollInterval;
	
	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Map<String, String> props) {
		this.topic = props.get(TOPIC_CONFIG);
		this.endpoint = props.get(ENDPOINT_CONFIG);
		this.sourceName = props.get(SOURCE_NAME_CONFIG);
		this.pollInterval = props.get(POLL_INTERVAL_CONFIG);
	}

	@Override
	public Class<? extends Task> taskClass() {
		return JsonSourceTask.class;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		ArrayList<Map<String, String>> configs = new ArrayList<>(1);
		Map<String, String> config = new HashMap<>();
		config.put(TOPIC_CONFIG, topic);
		config.put(ENDPOINT_CONFIG, endpoint);
		config.put(POLL_INTERVAL_CONFIG, pollInterval);
		configs.add(config);
		return configs;
	}

	@Override
	public void stop() {
	}

	@Override
	public ConfigDef config() {
		return new ConfigDef()
			.define(TOPIC_CONFIG, Type.STRING, Importance.HIGH, "Topic the records should be read into")
			.define(ENDPOINT_CONFIG, Type.STRING, Importance.HIGH, "URI of the source endpoint")
    		.define(SOURCE_NAME_CONFIG, Type.STRING, Importance.HIGH, "Source name (unique)")
			.define(POLL_INTERVAL_CONFIG, Type.INT64, Importance.HIGH, "How often the source should be polled (in seconds)")
		;
	}
}
