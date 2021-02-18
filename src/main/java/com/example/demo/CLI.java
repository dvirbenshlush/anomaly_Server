package com.example.demo;

import java.util.ArrayList;

import  com.example.demo.Commands.Command;
import com.example.demo.Commands.DefaultIO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RestController
@RequestMapping("api/")
public class CLI {

	ArrayList<Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio);
		commands=new ArrayList<>();
//		commands.add((c.new mainCommand()));
		commands.add(c.new uploadCommand());
		commands.add(c.new algorithmCommand());
		commands.add(c.new detectCommand());
		commands.add(c.new displayCommand());
		commands.add(c.new analyzeCommand());
		commands.add(c.new exitCommand());

		// implement
	}


	@PostMapping("/add2/{t}")
	public void start(@PathVariable String t) {
		// implement
		System.out.println("Welcome to the Anomaly Detection Server.\n" +
				"Please choose an option:\n");
		System.out.println("option "+t);
		commands.forEach(s->System.out.println(s.description));
		commands.get(Integer.parseInt(t)-1).execute();
	}
}
