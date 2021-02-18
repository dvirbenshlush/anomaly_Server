package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.Scanner;
//@Component
@RestController
@RequestMapping("/api1")
public class StandrtIO implements Commands.DefaultIO {
    Scanner scanner=new Scanner(System.in);
    PrintWriter p = new PrintWriter(System.out);
    @GetMapping()
    @Override
    public String readText() {
        return scanner.nextLine();

    }

    @Override
    public void write(String text) {
        p.println(text);
        System.out.println(text);
    }

    @PostMapping()
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
}
