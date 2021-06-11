package datatypes;


import java.util.HashMap;
import java.util.Map;

public class ConfigProcessor {

	private ConfigReader configReader;
	
	public ConfigProcessor(ConfigReader configReader) {
		this.configReader = configReader;
	}
	
	public Map<String, String> process(String env) {
	
		Map<String, Map<String, String>> environmentProperties = configReader.read();
		
		Map<String, String> properties = environmentProperties.get(env);
		if(properties != null) {
			return properties;
		}
		return new HashMap<String, String>();
	}

}
