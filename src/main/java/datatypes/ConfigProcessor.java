package datatypes;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ConfigProcessor {

	private ConfigReader configReader;
	
	public ConfigProcessor(ConfigReader configReader) {
		this.configReader = configReader;
	}
	
	public Map<String, String> process(String env) throws JsonParseException, JsonMappingException, IOException {
	
		Map<String, Map<String, String>> environmentProperties = configReader.read();
		
		Map<String, String> localConfiguration = environmentProperties.get("local");
		Map<String, String> devConfiguration = environmentProperties.get("dev");
		Map<String, String> prodConfiguration = environmentProperties.get("prod");
		Map<String, String> defaultConfiguration = environmentProperties.get("default");
		
		FallbackMap<String, String> configurations = new FallbackMap<>(localConfiguration, devConfiguration);
		
		Map<String, String> properties = environmentProperties.get(env);
		if(properties != null) {
			return properties;
		}
		return new HashMap<String, String>();
	}

}
