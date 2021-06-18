package datatypes;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ConfigReaderTest {
	
	private ConfigReader configReader;
	
	@Test
	public void shouldReturnEmptyMap_WhenFilePathIsEmpty() throws JsonParseException, JsonMappingException, IOException {
		configReader = new ConfigReader(new File(""));
		assertThat(configReader.read()).isEmpty();
	}
	
	@Test
	public void shouldReturnMapByReadingAllConfigFiles_WhenFilePathIsSpecified() throws JsonParseException, JsonMappingException, IOException{
		configReader = new ConfigReader(new File("src/main/resources/config/azure.json"));
		configReader.read();
	}
	
}
