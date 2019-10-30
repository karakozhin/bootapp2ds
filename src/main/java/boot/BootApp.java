package boot;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients
public class BootApp {
    //172.16.9.90:8099/bootapp2ds/excel?periodKindListId=791,792,793,794,795,796,797,798,799,800,801,802&teCode=11&statusCode=RECOUNTED,REPORTED
    public static void main(String[] args) {
        SpringApplication.run(BootApp.class, args);
    }
}
