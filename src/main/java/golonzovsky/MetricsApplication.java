package golonzovsky;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@SpringBootApplication
public class MetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsApplication.class, args);
    }
}

/*@Configuration
@EnableMetrics
class SpringConfiguringClass extends MetricsConfigurerAdapter {

    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        // registerReporter allows the MetricsConfigurerAdapter to
        // shut down the reporter when the Spring context is closed
        registerReporter(Slf4jReporter
                .forRegistry(metricRegistry)//.outputTo(Logger)
                .build())
                .start(1, TimeUnit.MINUTES);
    }

}*/

@RestController
class RestEndpoints {

    @GetMapping("one")
    String one() throws InterruptedException {
        Thread.sleep(1000);
        return "one";
    }

    @GetMapping("half")
    String half() throws InterruptedException {
        Thread.sleep(500);
        return "half";
    }

    @GetMapping("instant")
    String instant() throws InterruptedException {
        return "instant";
    }
}
