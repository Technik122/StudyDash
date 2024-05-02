package de.gruppe1.studydash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@SpringBootApplication

public class StudyDashApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyDashApplication.class, args);
    }
}
