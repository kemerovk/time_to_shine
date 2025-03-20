package me.project.time_to_shine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class TimeToShineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeToShineApplication.class, args);
        new Scanner(System.in).nextLine();
    }

}
