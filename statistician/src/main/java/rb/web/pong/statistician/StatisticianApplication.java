package rb.web.pong.statistician;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class StatisticianApplication {
	public static void main(String[] args) {
		SpringApplication.run(StatisticianApplication.class, args);
	}
}
