package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.AnomalyDetectionHandler;
import com.example.demo.Commands;
import com.example.demo.Server;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import prinTest;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication
@EntityScan("com.example.entities")
@CrossOrigin
@RestController
public class DemoApplication  implements CommandLineRunner{

	public static void check(String outputFile,String expectedOutputFile){
		try {
			int chk[]={31,58,59,70,71,82,83,94,95,106,107};
			BufferedReader st=new BufferedReader(new FileReader(outputFile));
			BufferedReader ex=new BufferedReader(new FileReader(expectedOutputFile));
			int i=1,j=0;
			String lst,lex;
			while((lst=st.readLine())!=null && (lex=ex.readLine())!=null){
				if(i<13 && lst.compareTo(lex)!=0){ // 12
					System.out.println("line "+i+" expected: "+lex+" you got "+lst);
					System.out.println("wrong output (-1)");
				}else
				if(j<11 && i==chk[j]){
					if(lst.compareTo(lex)!=0){ // 88
						System.out.println("line "+i+" expected: "+lex+" you got "+lst);
						System.out.println("wrong output (-8)");
					}
					j++;
				}
				i++;
			}
			if(j<11)
				System.out.println("wrong output size (-"+((11-j)*8)+")");
			st.close();
			ex.close();
		}catch(IOException e) {
			System.out.println("an exception has occured (-100)");
		}
	}


	public static void testClient(int port){
		try {
			Socket theServer=new Socket("localhost", port);
			PrintWriter outToserver=new PrintWriter(theServer.getOutputStream());
			BufferedReader inFromServer=new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			Thread reader=new Thread(()->{
				try {
					PrintWriter out=new PrintWriter(System.out,true);
					String line;
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

	@Autowired
	private StandrtIO sio = new StandrtIO();

	CLI cli = new CLI(sio);
//	@Autowired
//	private CLI cli= new CLI(new StandrtIO(sf.getScanner()));

	@Override
	public void run(String... args) throws Exception {
//		cli.start();

		//		 s = new Scanner(System.in);
		//String s = "hello";
//		p.getTest();
//		PrintWriter pw = new PrintWriter(System.out);
//		StandrtIO sio=new StandrtIO(s);
//		cli=new CLI(sio);
//		cli.start();
//		s.close();
//		sio.close();
	}
}
