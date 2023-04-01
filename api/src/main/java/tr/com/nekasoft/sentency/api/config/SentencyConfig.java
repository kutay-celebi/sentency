package tr.com.nekasoft.sentency.api.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import java.math.BigDecimal;

@ConfigMapping(prefix = "sentency")
public interface SentencyConfig {

  Review review();

  interface Review {

    @WithDefault("1")
    BigDecimal multiplier();

    @WithDefault("12")
    BigDecimal easy();

    @WithDefault("8")
    BigDecimal medium();

    @WithDefault("4")
    BigDecimal hard();
  }
}
