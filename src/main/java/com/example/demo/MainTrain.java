package com.example.demo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class MainTrain {

	public static void testClient(int port){
		try {
			Socket theServer=new Socket("localhost", port);
			PrintWriter outToserver=new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer=new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			Thread reader=new Thread(()->{
				try {
//					PrintWriter out=new PrintWriter(System.out,true);
					String line;
					PrintWriter out = outToserver;
					while(!(line=inFromServer.readLine()).equals("bye")) {
						out.println(line);
					}
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			Thread writer=new Thread(()-> {
				try {

					BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
					String line;
					while(!(line=in.readLine()).equals("6")) {
						outToserver.println(line);
						outToserver.flush();
					}
					outToserver.println(line);
					outToserver.flush();
					in.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			});

			reader.start();
			writer.start();
			try {
				reader.join();
				writer.join();
			} catch (InterruptedException e) {}
			outToserver.close();
			inFromServer.close();
			theServer.close();
		} catch (IOException e) {
			System.out.println("an IO exception has happend (-100)");
		}
	}

	public static void main(String[] args) {
		Random r=new Random();

//		int port=6033;
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

}
