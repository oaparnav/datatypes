package datatypes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigReader {

	private File filePath;
	
	private FallbackBuilder fallbackBuilder;
	
	public ConfigReader(File filePath, FallbackBuilder fallbackBuilder) {
		this.filePath = filePath;
		this.fallbackBuilder = fallbackBuilder;
	}


	public Map<String, Map<String, String>>read() throws IOException, JsonParseException, JsonMappingException {	
			ObjectMapper objectMapper = new ObjectMapper();
			if(filePath.exists()) {
				Map<String, Map<String, String>> configReaderMap = objectMapper
						.readValue(filePath, new TypeReference<Map<String, Map<String, String>>>(){});
				this.fallbackBuilder.build(configReaderMap);
				return configReaderMap;
			}
			return new HashMap<>();
			
	}

	
}
