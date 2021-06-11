package datatypes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConfigProcessorTest {
	
	private ConfigProcessor configProcessor;
	
	@Mock
	private ConfigReader configReader;

	@Test
	public void shouldReturnEmptyMap_WhenNoArgument() {
		configProcessor = new ConfigProcessor(configReader);
		assertThat(configProcessor.process("").isEmpty());
	}
	
	@Test
	public void shouldReturnPropertiesForDefaultEnvironment() {
		when(configReader.read()).thenReturn(Map.of("default", Map.of("azure_timeout", "1000")));
		configProcessor = new ConfigProcessor(configReader);
		assertThat(configProcessor.process("default")).isEqualTo(Map.of("azure_timeout", "1000"));
	}
}
