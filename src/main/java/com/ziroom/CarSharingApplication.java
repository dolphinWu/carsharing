package com.ziroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.ziroom.dao")
@EnableCaching
public class CarSharingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarSharingApplication.class, args);
	}
}
