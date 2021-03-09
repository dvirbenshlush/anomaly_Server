package com.example.demo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import  com.example.demo.Commands.Command;
import com.example.demo.Commands.DefaultIO;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RestController
@RequestMapping("api/")
public class CLI {

	Map<String,Command> commands;
	DefaultIO dio;
	Commands c;
	
	public CLI(DefaultIO dio) {
		this.dio=dio;
		c=new Commands(dio);
		commands=new HashMap<>();
//		commands.add((c.new mainCommand()));
		commands.put("upload",c.new uploadCommand());
		commands.put("algorithm",c.new algorithmCommand());
		commands.put("detect",c.new detectCommand());
		commands.put("download",c.new downloadCommand());
		commands.put("display",c.new displayCommand());
		commands.put("analyze",c.new analyzeCommand());
		commands.put("exit",c.new exitCommand());

		// implement
	}

	@CrossOrigin
	@GetMapping("/add3/{str}")
	public String something(@PathVariable String str,@RequestParam(defaultValue = "its not work",name = "parameter",value = "parameter") String parameter) {
		System.out.println(str);
//		System.out.println(parameter);

	return commands.get(str).execute(parameter);
	}

	@CrossOrigin
	@PostMapping("/add2/upload/{str}")
	public String start(@RequestBody MultipartFile file,@PathVariable String str) {

		try {
			str = new String(file.getBytes());
//			System.out.println(str);
			commands.get("download").execute(str);
			if(str.length()>=1)
				return "succ";
			return "empty file";
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(file.isEmpty());
		return "somethig from server";
	}
}
