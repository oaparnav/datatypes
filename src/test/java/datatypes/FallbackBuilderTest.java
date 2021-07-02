package datatypes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class FallbackBuilderTest {

    private FallbackBuilder fallbackBuilder;

    @Before
    public void setup(){
        this.fallbackBuilder = new FallbackBuilder();
    }

    @Test
    public void shouldCreateAFallbackMapWithInheritance(){
        
        Map<String, Map<String, String>> configurationMap = this.fallbackBuilder.build(
            Map.of(
                "default", Map.of("azure_timeout", "1000"), 
                "prod", Map.of("baseUrl", "https://127.0.0.1"),
                "dev", Map.of("clientId", "abc123"),
                "local", Map.of("extendedPath", "/api/v1")
        ));
        assertThat(configurationMap.get("default")).containsOnly(Map.entry("azure_timeout", "1000"));
        assertThat(configurationMap.get("prod")).containsOnly(
            Map.entry("azure_timeout", "1000"),
            Map.entry("baseUrl", "https://127.0.0.1")
        );
        assertThat(configurationMap.get("dev")).containsOnly(
            Map.entry("azure_timeout", "1000"),
            Map.entry("baseUrl", "https://127.0.0.1"),
            Map.entry("clientId", "abc123")
        );
        assertThat(configurationMap.get("local")).containsOnly(
            Map.entry("azure_timeout", "1000"),
            Map.entry("baseUrl", "https://127.0.0.1"),
            Map.entry("clientId", "abc123"),
            Map.entry("extendedPath", "/api/v1")
        );
    
    }
    
}
