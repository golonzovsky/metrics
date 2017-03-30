package golonzovsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

@SpringBootApplication
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class MetricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetricsApplication.class, args);
    }
}

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
