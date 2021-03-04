package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.Scanner;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RestController

public class StandrtIO implements Commands.DefaultIO {
    Scanner scanner=new Scanner(System.in);
    PrintWriter p = new PrintWriter(System.out);
    String concat = new String();
//    @PostMapping("/ret/text")
    @Override
    public String readText() {
        return scanner.nextLine();

    }

    @Override
    public void write(String text) {
        concat+=text;
        writeToClient(concat);
//        p.println(text);
//        System.out.println(text);
    }

//    @PostMapping("/ret/val")
    @Override
    public float readVal() {
        return scanner.nextFloat();
    }

    @Override
    public void write(float val) {
        p.println(val);
        System.out.println(val);
    }



    @Override
    public void close() {

    }

    public void setInput(@PathVariable("text") String text){
        concat=text;
    }

    public String writeToClient( String text) {
//        p.println(text);
//        setInput(new Scanner(System));
//        System.out.println("concat "+concat);
//        text=concat;
//        System.out.println(text);
//        System.out.println(concat);
        return text;
    }
}
