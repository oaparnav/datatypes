package datatypes;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentGeneratorTest {
	
	private EnvironmentGenerator environmentGenerator;
	
	@Mock
	private ConfigProcessor configProcessor;
	
	@Before
	public void setup() {
		environmentGenerator = new EnvironmentGenerator(configProcessor);
	}
	
	@Test
	public void shouldReturnEmptyString_WhenNoDataIsAvailable() throws Exception {

		when(configProcessor.process(any())).thenReturn(Map.of());
		assertEquals(environmentGenerator.generate("default"), "");
	}
 
	@Test
	public void shouldReturnProperties_ForDefaultEnvironment() throws Exception {

		when(configProcessor.process(any())).thenReturn(Map.of("azure_timeout","1000"));
		assertEquals(environmentGenerator.generate("default"), "export AZURE_TIMEOUT=1000");
	}
	
	@Test
	public void shouldReturnMultipeEnvProperties_ForDefaultEnvironment() {
		when(configProcessor.process(any())).thenReturn(Map.of("azure_baseUrl","http://azure.microsoft.com", "azure_timeout","1000"));
		
		List<String> asList = Arrays.asList(environmentGenerator.generate("default").split("\n"));
		Set<String> responseProperties = new HashSet<>(asList);
		Set<String> expectedProperties = Set.of("export AZURE_BASE_URL=http://azure.microsoft.com", "export AZURE_TIMEOUT=1000");
		assertThat(responseProperties).isEqualTo(expectedProperties);
	}
	
				 
	@Test
	public void shouldReturnMultipleEnvProperties_ForDevEnvironment() {
		when(configProcessor.process(any())).thenReturn(Map.of("azure_clientId", "azureDevId", "scac_baseUrl",
				"http://dev.scac.ford.com", "scac_timeout", "30000"));

		List<String> asList = Arrays.asList(environmentGenerator.generate("dev").split("\n"));
		Set<String> responseProperties = new HashSet<>(asList);
		Set<String> expectedProperties = Set.of("export AZURE_CLIENT_ID=azureDevId",
				"export SCAC_BASE_URL=http://dev.scac.ford.com", "export SCAC_TIMEOUT=30000");
		assertThat(responseProperties).isEqualTo(expectedProperties);
	}
}
