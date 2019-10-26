package boot;


import boot.service.AppService;
import boot.service.Export;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class BootApp {
    public static void main(String[] args) {
        SpringApplication.run(BootApp.class, args);
        Export export = new Export();
        export.getExcel();
    }
}
