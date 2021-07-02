package datatypes;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class EnvironmentGenerator {

	private ConfigProcessor configProcessor;

	public EnvironmentGenerator(ConfigProcessor configProcessor) {
		this.configProcessor = configProcessor;
	}

	public String generate(String environment) throws StreamReadException, JsonMappingException, IOException {
		StringBuilder envBuilder = new StringBuilder();
		
		Map<String, String> processMap = configProcessor.process(environment);

		processMap.entrySet().stream().forEach(entry -> {
			
			String key = String.join("_", entry.getKey().split("(?=\\p{Upper})"));
			envBuilder.append("export").append(" ").append(key.toUpperCase()).append("=")
					.append(entry.getValue()).append("\n");
		});
		
		return envBuilder.toString().trim();

	}


}
