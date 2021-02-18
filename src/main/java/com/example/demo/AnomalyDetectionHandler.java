package com.example.demo;



import com.example.demo.Commands.DefaultIO;
//import com.example.demo.Server.ClientHandler;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;



interface ClientHandler{
	void handleClient(Scanner inFromClient, PrintWriter outToClient);
}

@CrossOrigin
@RestController
@RequestMapping("api/")
public class AnomalyDetectionHandler implements ClientHandler{

//		Scanner scanner;
//		PrintWriter pw;
	public AnomalyDetectionHandler() {
//		scanner=s;
//		pw=pw;
	}

	@Override
	public void handleClient(Scanner inFromClient, PrintWriter outToClient) {
//		StandrtIO sio=new StandrtIO(inFromClient);
//		CLI cli=new CLI(sio);
//		cli.start();
//		sio.close();
//
	}

//	public class SocketIO implements DefaultIO{
//		Scanner in;
//		PrintWriter out;
//
//		public  SocketIO(InputStream inFromClient, OutputStream outToClient){
//			in=new Scanner(inFromClient);
//			out = new PrintWriter(outToClient);
//		}
//
//
//		@GetMapping("/get")
//		@Override
//		public String readText() {
//			return in.nextLine();
//		}
//
//		@RequestMapping(method = RequestMethod.POST,value = "/add")
//		@Override
//		public void write(@RequestBody String text) {
//			out.println(text);
//			out.flush();
//		}
//
////		@RequestMapping(method = RequestMethod.POST,value = "/add2")
//		@GetMapping("/get")
//		@Override
//		public float readVal() {
//				return in.nextFloat();
//		}
//
//		@RequestMapping(method = RequestMethod.POST,value = "/add")
//		@Override
//		public void write(@RequestBody float val) {
//			out.println(val);
//			out.flush();
//		}
//
//		@Override
//		public void close() {
//			in.close();
//			out.close();
//
//		}
//	}


}
