package com.intuit.cg.backendtechassessment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.intuit.cg.backendtechassessment.util.AutoWinerThread;

@SpringBootApplication
public class BackendTechAssessmentApplication {
	@Autowired
	private ApplicationContext poApplicationContext = null;
	
	public static void main(String[] args) {
		SpringApplication.run(BackendTechAssessmentApplication.class, args);
	}
	
	@PostConstruct
    public void init(){
		Thread oAutoWinerThread = new AutoWinerThread(poApplicationContext);
		oAutoWinerThread.start();
    }
}
