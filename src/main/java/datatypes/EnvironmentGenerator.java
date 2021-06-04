package datatypes;

public class EnvironmentGenerator {

	private ConfigProcessor configProcessor;

	public EnvironmentGenerator(ConfigProcessor configProcessor) {
		this.configProcessor = configProcessor;
	}

	public String generate(String environment) {
		
		return configProcessor.process(environment);
		
	}


}
