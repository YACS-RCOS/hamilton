package hamilton;

import java.util.List;
import java.util.Map;

import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

public class JsonSourceConnector extends SourceConnector {
	public static final String ENDPOINT_CONFIG = "endpoint";
	public static final String SOURCE_NAME_CONFIG = "source_name";
	
	private String endpoint;
//	private String topic;
	private String sourceName;
	
	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Map<String, String> props) {
		// TODO Auto-generated method stub
		this.endpoint = props.get(ENDPOINT_CONFIG);
		this.sourceName = props.get(SOURCE_NAME_CONFIG);
	}

	@Override
	public Class<? extends Task> taskClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> taskConfigs(int maxTasks) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public ConfigDef config() {
		// TODO Auto-generated method stub
		return null;
	}

}
