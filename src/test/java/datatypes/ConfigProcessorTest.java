package datatypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(MockitoJUnitRunner.class)
public class ConfigProcessorTest {
	
	private ConfigProcessor configProcessor;
	
	@Mock
	private ConfigReader configReader;

	@Test
	public void shouldReturnEmptyMap_WhenNoArgument() throws JsonParseException, JsonMappingException, IOException {
		
		when(configReader.read()).thenReturn(Map.of());
		configProcessor = new ConfigProcessor(configReader);
		assertThat(configProcessor.process("").isEmpty());
	}
	
	@Test
	public void shouldReturnPropertiesForDefaultEnvironment() throws JsonParseException, JsonMappingException, IOException {
		when(configReader.read()).thenReturn(Map.of("default", Map.of("azure_timeout", "1000")));
		configProcessor = new ConfigProcessor(configReader);
		assertThat(configProcessor.process("default")).isEqualTo(Map.of("azure_timeout", "1000"));
	}
	
	@Test
	public void shouldReturnMissingPropertiesFromDefaultForProdEnvironment() throws Exception {
		when(configReader.read()).thenReturn(Map.of("default", Map.of("azure_timeout", "1000"), "prod", Map.of()));
		configProcessor = new ConfigProcessor(configReader);
		assertThat(configProcessor.process("prod").toString()).isEqualTo(Map.of("azure_timeout", "1000").toString());
		
	}
	
	@Test
	public void shouldReturnMissingPropertiesFromDefaultForDevEnvironment() throws Exception {
		when(configReader.read()).thenReturn(Map.of("default", Map.of("azure_timeout", "1000"), "prod", Map.of("azure_baseurl","xyz","azure_clientId","1234"),"dev", Map.of("azure_clientId","4321")));
		configProcessor = new ConfigProcessor(configReader);
		
		assertThat(configProcessor.process("dev")).containsOnly(
	            Map.entry("azure_timeout", "1000"),
	            Map.entry("azure_clientId","4321"),
	            Map.entry("azure_baseurl","xyz")
	        );
		
	}
	
	@Test
	public void shouldReturnMissingPropertiesFromDefaultForLocalEnvironment() throws Exception {
		when(configReader.read()).thenReturn(Map.of("local", Map.of("azure_basepath", "http://basepath"), "default", Map.of("azure_timeout", "1000"), "prod", Map.of("azure_baseurl","xyz","azure_clientId","1234"),"dev", Map.of("azure_clientId","4321", "azure_clientSecret", "clientscret")));
		configProcessor = new ConfigProcessor(configReader);
		 assertThat(configProcessor.process("local")).containsOnly(
		            Map.entry("azure_timeout", "1000"),
		            Map.entry("azure_basepath", "http://basepath"),
		            Map.entry("azure_clientId","4321"),
		            Map.entry("azure_baseurl","xyz"),
		            Map.entry("azure_clientSecret", "clientscret")
		        );
		
	}
}
