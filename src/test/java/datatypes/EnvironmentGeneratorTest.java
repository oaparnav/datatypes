package datatypes;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

@RunWith(JUnit4ClassRunner.class)
public class EnvironmentGeneratorTest {
	
	EnvironmentGenerator environmentGenerator;
	
	@Before
	public void setup() {
		environmentGenerator = new EnvironmentGenerator();
	}
	
	@Test
	public void shouldReturnEmptyString_WhenNoDataIsAvailable() throws Exception {
		assertEquals(environmentGenerator.generate("default"), "");
	}
 
	@Test
	public void shouldReturnProperties_ForDefaultEnvironment() throws Exception {
		assertEquals(environmentGenerator.generate("default"), "export AZURE_TIMEOUT=1000");
	}
	
}
