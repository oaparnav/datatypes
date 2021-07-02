package datatypes;

import java.util.Map;

public class FallbackBuilder {

    public Map<String, Map<String, String>> build(Map<String, Map<String, String>> configReaderMap) {
        
        Map<String, String> defaultConfiguration = configReaderMap.get("default");
		Map<String, String> prodConfiguration = new FallbackMap<>(configReaderMap.get("prod"), defaultConfiguration);
		Map<String, String> devConfiguration = new FallbackMap<>(configReaderMap.get("dev"), prodConfiguration);
		Map<String, String> localConfiguration = new FallbackMap<>(configReaderMap.get("local"), devConfiguration);
		
		return Map.of("local", localConfiguration, 
				"dev", devConfiguration, "prod", prodConfiguration, "default", defaultConfiguration);
    }

}
