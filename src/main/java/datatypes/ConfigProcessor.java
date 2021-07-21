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

		if (environmentProperties != null && !environmentProperties.isEmpty()) {

			Map<String, String> defaultConfiguration = environmentProperties.get("default");
			Map<String, String> prodConfiguration = new FallbackMap<>(environmentProperties.get("prod"),
					defaultConfiguration);
			Map<String, String> devConfiguration = new FallbackMap<>(environmentProperties.get("dev"),
					prodConfiguration);
			Map<String, String> localConfiguration = new FallbackMap<>(environmentProperties.get("local"),
					devConfiguration);

			Map<String, Map<String, String>> configurationsMap = Map.of("local", localConfiguration, "dev",
					devConfiguration, "prod", prodConfiguration, "default", defaultConfiguration);

			Map<String, String> properties = configurationsMap.get(env);
			if (properties != null) {
				return properties;
			}
		}
		return new HashMap<>();
	}

}
