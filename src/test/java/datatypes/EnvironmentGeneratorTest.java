package datatypes;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
		when(configProcessor.process(any())).thenReturn("");
		assertEquals(environmentGenerator.generate("default"), "");
	}
 
	@Test
	public void shouldReturnProperties_ForDefaultEnvironment() throws Exception {
		when(configProcessor.process(any())).thenReturn("export AZURE_TIMEOUT=1000");
		assertEquals(environmentGenerator.generate("default"), "export AZURE_TIMEOUT=1000");
	}
	
}
