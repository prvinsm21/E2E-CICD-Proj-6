package com.macko.sentra;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class SentraApplication {
	
	
	@GetMapping("/")
    public String home() {
        return "index.html";
    }
	
	
	public static void main(String[] args) {
		SpringApplication.run(SentraApplication.class, args);
	}

}
