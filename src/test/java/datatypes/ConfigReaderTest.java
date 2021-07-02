package datatypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ConfigReaderTest {
	
	private ConfigReader configReader;
	
	@Test
	public void shouldReturnEmptyMap_WhenFilePathIsEmpty() throws JsonParseException, JsonMappingException, IOException {
		configReader = new ConfigReader(new File(""), new FallbackBuilder());
		assertThat(configReader.read()).isEmpty();
	}
	
	@Test
	public void shouldReturnMapByReadingAllConfigFiles_WhenFilePathIsSpecified() throws JsonParseException, JsonMappingException, IOException{
		configReader = new ConfigReader(new File("src/main/resources/config/azure.json"), new FallbackBuilder());
		Map<String, Map<String, String>> responseMap = configReader.read();
		assertNotNull(responseMap);
	}
	
	@Test
	public void shouldReturnEmptyMap_WhenDirectoryDoesNotHaveAnyFiles() throws Exception {
		configReader = new ConfigReader(new File("src/main/resources/config/fileThatDoesNotExist.json"), new FallbackBuilder());
		Map<String, Map<String, String>> responseMap = configReader.read();
		assertEquals(responseMap, new HashMap<String, Map<String, String>>());
		
	}


	
}
