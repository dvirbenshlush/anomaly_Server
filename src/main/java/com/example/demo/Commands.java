package com.example.demo;

import org.springframework.stereotype.Component;
import java.io.*;
import java.util.*;

public class Commands {

	@Component
	public interface DefaultIO{
		public String readText();
		public void write( String text);
		public float readVal();
		public void write( float val);
		public default void uploadFile(String exit, String outputPath) {
			try {
				PrintWriter writer = new PrintWriter(new FileWriter(outputPath));
				String line;
				while (  (line = readText()) != null) {
					if(line.equals(exit))
						break;
					if(line.equals(""))
						continue;
					writer.println(line);
					System.out.println(line);
				}
				writer.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public default String[] downloadFile(String outputFileName) {
			try {
				Scanner scanner=new Scanner(new BufferedReader(new FileReader(outputFileName)));
				String str="";
				while (scanner.hasNext()){
					str+=scanner.nextLine()+"split";
				}
				scanner.close();
				return str.split("split");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return "doesnt work".split(" ");
		}


		void close();

	}

	DefaultIO dio;
	public Commands(DefaultIO dio) {
		this.dio=dio;
	}




	private class SharedState{
		float threshold= (float) 0.9;
		String anomaliesFile = "C:\\Users\\dvir\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\anomaliesFile.txt";
		String trainPath = "C:\\Users\\dvir\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\input.txt";
		String testPath = "C:\\Users\\dvir\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\input2.txt";
		String testPathToServer = "C:\\Users\\dvir\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\input3.txt";
		String resultPath = "C:\\Users\\dvir\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\result.txt";
		SimpleAnomalyDetector sad = new SimpleAnomalyDetector();
		public void initializeSAD(){
			sad = new SimpleAnomalyDetector();
		}
//		public void EnterClick(Command c) {
//			String input=dio.readText();
//			System.out.println(input);
//			while (!input.equals(""))
//			{
//				input=dio.readText();
//			}
//			c.execute("sdd");
//		}
	}

	private  SharedState sharedState=new SharedState();


	// Command abstract class
	public abstract class Command{
		protected String description;

		public Command(String description) {
			this.description=description;
		}

		public abstract String execute(String text);

		public abstract String writeToClient( String text);
	}

	public  class mainCommand extends Command{


		public mainCommand() {
			super("Welcome to the Anomaly Detection Server.\n" +
					"Please choose an option:\n");
		}

		@Override
		public String execute(String text) {
			CLI cli= new CLI(dio);
//			cli.commands.forEach(command -> dio.write( command.description));
			int clientNumber = (int) dio.readVal();
			cli.commands.get(clientNumber).execute("s");
			return "";
		}

		@Override
		public String writeToClient(String text) {
			return "null";
		}
	}


	// Command class for example:
	public class uploadCommand extends Command{

		public uploadCommand() {
			super("1. upload a time series csv file\n");
		}

		@Override
		public String execute(String text) {
//			TimeSeries ts = new TimeSeries(sharedState.trainPath);
//			sharedState.sad.learnNormal(ts);
			return  "i am from server.\n";
		}

		@Override
		public String writeToClient(String text) {
			return text;
		}


	}

	public class algorithmCommand extends Command {

		public algorithmCommand() {
			super("2. algorithm settings\n");
		}

		@Override
		public String execute(String text) {
			sharedState.threshold = (float) Double.parseDouble(text);
			if (sharedState.threshold <= 1 && sharedState.threshold >= 0){
				System.out.println("threshold update");
				return "threshold update";
			}
			else {
				System.out.println("please choose a value between 0 and 1.");
				return "please choose a value between 0 and 1.\n";
			}
		}

		@Override
		public String writeToClient(String text) {
			return null+"";
		}
	}

	public class detectCommand extends Command {

		public detectCommand() {
			super("3. detect anomalies\n");
		}

		@Override
		public String execute(String text) {
			TimeSeries ts = new TimeSeries(sharedState.trainPath);
			sharedState.sad.learnNormal(ts);
//			sharedState.sad.getNormalModel().forEach(t->t.threshold=sharedState.threshold);
			TimeSeries ts2 = new TimeSeries(sharedState.testPathToServer);
			sharedState.sad.detect(ts2);
			dio.write("anomaly detection complete.\n");
			return null+"";

		}

		@Override
		public String writeToClient(String text) {
			return null+"";
		}
	}

	public class displayCommand extends Command {

		public displayCommand() {
			super("4. display results\n");
		}

		@Override
		public String execute(String text) {
			TimeSeries ts = new TimeSeries(sharedState.trainPath);
			TimeSeries ts2 = new TimeSeries(sharedState.testPathToServer);
			sharedState.sad.learnNormal(ts);
			//System.out.println("List<AnomalyReport>" + anomalies);
			String result = "";
			List<AnomalyReport> anomalies =  sharedState.sad.detect(ts2);

			for (AnomalyReport anomalyReport : anomalies) {
				result+=anomalyReport.timeStep+" "+anomalyReport.description+"\n";
			}
			result= result.substring(0,result.length()-1);
			return result;
		}

		@Override
		public String writeToClient(String text) {
			return null+"";
		}
	}

	public class downloadCommand extends Command {

		public downloadCommand() {
			super("5. download results\n");
		}

		@Override
		public String execute(String text) {
			sharedState.initializeSAD();
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(sharedState.testPathToServer));
				writer.write(text);
				writer.close();
				StandrtIO dio = new StandrtIO();
				Scanner in=new Scanner(new FileReader(sharedState.resultPath));
//				if(!in.hasNext()) {
					FileIO fio = new FileIO(sharedState.testPathToServer, sharedState.resultPath);
					TimeSeries ts = new TimeSeries(sharedState.trainPath);
					sharedState.sad.learnNormal(ts);
					TimeSeries ts2 = new TimeSeries(sharedState.testPathToServer);
					sharedState.sad.detect(ts2).forEach(d -> fio.write(d.timeStep + " n " + d.description + "\n"));
					fio.close();
//				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null+"";
		//	sharedState.EnterClick(new mainCommand());
		}

		@Override
		public String writeToClient(String text) {
			return null+"";
		}
	}

	public class analyzeCommand extends Command {

		public analyzeCommand() {
			super("5. upload anomalies and analyze results\n");
		}

		@Override
		public String execute(String text) {

			TimeSeries ts = new TimeSeries(sharedState.trainPath);
			sharedState.sad.learnNormal(ts);
			TimeSeries ts2 = new TimeSeries(sharedState.testPathToServer);
			List<AnomalyReport> an=sharedState.sad.detect(ts2);
			int N = ts.getColumn("A").length-an.size();
			List<List<AnomalyReport>> anomalyArray = new ArrayList<>();
			List<AnomalyReport> newAnomaly = new ArrayList<>();
			int i=0;
			while (i<an.size()-1){
				while (an.get(i).description.equals(an.get(i+1).description) && an.get(i).timeStep+1==an.get(i+1).timeStep){
					newAnomaly.add(an.get(i));
					i++;
					if(i==an.size()-2)
						break;
				}
				newAnomaly.add(an.get(i));
				anomalyArray.add(newAnomaly);
				newAnomaly = new ArrayList<>();
				i++;
			}
			if (!an.get(i).description.equals(an.get(i - 1).description) || an.get(i).timeStep - 1 != an.get(i - 1).timeStep) {
				newAnomaly = new ArrayList<>();
				anomalyArray.add(newAnomaly);
			}
			anomalyArray.get(anomalyArray.size()-1).add(an.get(i));

			List<List<Long>> ranges = new ArrayList<>();
			List<Long> r = new ArrayList<>();
			String[] anomalyMachine = new String[anomalyArray.size()];
			int inx=0;
			for (List<AnomalyReport> a:anomalyArray){
				r.add((a.get(0).timeStep));
				r.add((a.get(a.size()-1).timeStep));
				anomalyMachine[inx++]=r.get(0)+","+r.get(1);
				ranges.add(r);
				r = new ArrayList<>();
			}
			String[] anomalyInputFile = dio.downloadFile(sharedState.anomaliesFile);
			int T = anomalyInputFile.length;
			int TF =0;
			int FP =0;
			int count = 0;
			for (String am:anomalyMachine) {
					for (String s : anomalyInputFile) {

					long firstIn = Long.parseLong(s.split(",")[0]);
					long lastIn = Long.parseLong(s.split(",")[1]);
					long firstOut = Long.parseLong(am.split(",")[0]);
					long lastOut = Long.parseLong(am.split(",")[1]);
					if (isTP(firstIn, lastIn, firstOut, lastOut))
						TF++;
					else
						count++;
				}
				if(count==anomalyInputFile.length-1)
					FP++;
				count=0;
			}
			FP= isFP(anomalyMachine,anomalyInputFile);
			float resTP =  ((int)((TF+0.00000)/T*1000.0))/1000.0f;
			float resFP = ((int)((FP+0.00000)/N*1000.0))/1000.0f;
			System.out.println("True Positive Rate: "+resTP+"\n");
			System.out.println("False Positive Rate: "+resFP+"\n");
			String st1 = "True Positive Rate: "+resTP+"\n";
			String st2 = "False Positive Rate: "+resFP+"\n";
			return st1+st2;
		}

		@Override
		public String writeToClient(String text) {
			return null+"";
		}

		public boolean isTP(long firstFromDetect,long lastFromDetect,long firstFromFile,long lastFromFile){
			for (long i=firstFromDetect;i<=lastFromDetect;i++){
				for (long j = firstFromFile; j <=lastFromFile; j++) {
					if(i==j)
						return  true;
				}
			}
			return false;
		}

		public int isFP(String[] anomalyMachine,String[] anomalyInputFile){
			int counter=anomalyMachine.length;
			int i = 0;
			for (String aif:anomalyInputFile){
				long firstFromFile = Long.parseLong(aif.split(",")[0]);
				long lastFromFile = Long.parseLong(aif.split(",")[1]);
				long firstFromMachine = Long.parseLong(anomalyMachine[i].split(",")[0]);
				long lastFromMachine = Long.parseLong(anomalyMachine[i].split(",")[1]);
				boolean flag=true;
				for (int j = 0; j <anomalyMachine.length ; j++) {
					flag = isTP(Long.parseLong(anomalyMachine[j].split(",")[0]), Long.parseLong(anomalyMachine[j].split(",")[1]), firstFromFile, lastFromFile);
					if(flag)
						counter--;
				}
			}
			return counter;
		}
	}

	public class exitCommand extends Command {

		public exitCommand() {
			super("6. exit\n");
		}

		@Override
		public String execute(String text) {
			StandrtIO dio = new StandrtIO();
			dio.write("bye");
			System.out.println("server closed");
			dio.close();
			return null+"";

		}

		@Override
		public String writeToClient(String text) {
			return null+"";
		}
	}


}