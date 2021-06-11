package datatypes;

import java.util.Map;
import java.util.TreeMap;

public class EnvironmentGenerator {

	private ConfigProcessor configProcessor;

	public EnvironmentGenerator(ConfigProcessor configProcessor) {
		this.configProcessor = configProcessor;
	}

	public String generate(String environment) {
		StringBuilder envBuilder = new StringBuilder();
		
		Map<String, String> processMap = new TreeMap<>();
		
		processMap = configProcessor.process(environment);

		processMap.entrySet().stream().forEach(entry -> {
			envBuilder.append("export").append(" ").append(entry.getKey().toUpperCase()).append("=")
					.append(entry.getValue()).append("\n");
		});
		
		return envBuilder.toString().trim();

	}


}
