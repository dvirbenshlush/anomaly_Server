package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.AnomalyDetectionHandler;
import com.example.demo.Commands;
import com.example.demo.Server;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import prinTest;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan("com.example.entities")
@CrossOrigin
@RestController

public class DemoApplication  implements CommandLineRunner{


	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
//		Random r=new Random();
//		int port=6000+r.nextInt(1000);
//		Server server=new Server();
//		server.start(port, new AnomalyDetectionHandler());
//		testClient(port);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {}
//		server.stop();
//		check("C:\\Users\\dvir\\IdeaProjects\\Patam1\\test\\output.txt", "C:\\Users\\dvir\\IdeaProjects\\Patam1\\test\\expectedOutput.txt");
		System.out.println("done");
	}
//	@Autowired
//	 prinTest p;

	private StandrtIO sio;
	@Autowired(required = false)
	private Commands c;
	CLI cli = new CLI(sio);
	@Override
	public void run(String... args) throws Exception {

	}
}
