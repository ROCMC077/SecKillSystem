package com.lai.seckillsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lai.seckillsystem.pojo")
public class SecKillSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecKillSystemApplication.class, args);
	}

}
