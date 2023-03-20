package config;

import client.MyClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "api.client")
public class ApiClientConfig {
    private String accessKey;
    private String secretKey;


    @Bean
    public MyClient getClient(){
        return new MyClient(accessKey,secretKey);
    }
}
