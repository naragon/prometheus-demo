package org.sandragon;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by naragon on 5/22/16.
 *
 */
@Configuration
public class PrometheusMetricsConfiguration {

	static {
        DefaultExports.initialize();
    }

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Bean
    public ServletRegistrationBean registerPrometheusExporterServlet() {
        log.info("Configuring Prometheus Servlet ...");
        return new ServletRegistrationBean(new MetricsServlet(), "/prometheus");
    }
}
