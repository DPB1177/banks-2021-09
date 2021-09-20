package com.banks.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "link")
@Data
public class AppProperties {
      private String mono;
      private String privat;
      private String minfin;
      private String key_minfin;
}
