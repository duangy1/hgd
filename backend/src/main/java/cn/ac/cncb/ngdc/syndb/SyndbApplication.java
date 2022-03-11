package cn.ac.cncb.ngdc.syndb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@MapperScan("cn.ac.cncb.ngdc.syndb.mapper")
@CrossOrigin(origins = {"http://localhost:9405","http:192.168.164.15:9405"})
@ServletComponentScan
@SpringBootApplication
public class SyndbApplication {
    public static void main(String[] args) {
        SpringApplication.run(SyndbApplication.class, args);
    }
}
