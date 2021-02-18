package com.example.demo;

import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ScannerFile {
    private String scanner="";

    ScannerFile(){
        scanner= new String();
    }


   public void  setScanner(String s){
   scanner=s;
   }

    public String getScanner(){
        return scanner;
    }

}
