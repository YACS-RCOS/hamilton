package hamilton;

import java.util.List;
import java.util.Map;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

public class JsonSourceTask extends SourceTask {
	String endpoint;
	String topic;
	
	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start(Map<String, String> props) {
		// TODO Auto-generated method stub
		this.endpoint = props.get(JsonSourceConnector.ENDPOINT_CONFIG);
		
		
	}

	@Override
	public List<SourceRecord> poll() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}