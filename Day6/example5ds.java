package org.apache.flink;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
public class example5ds
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket listener = new ServerSocket(9090);
		try{
				Socket socket = listener.accept();
				System.out.println("Got new connection: " + socket.toString());
				
				BufferedReader br = new BufferedReader(new FileReader("/home/flink/flink-day3/src/main/resources/example3.txt"));
				
				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					String line;
					int count = 0;
					while ((line = br.readLine()) != null){
						count++;
						
						out.println(line);
						if (count >= 10){
							count = 0;
							Thread.sleep(1000);
						}
						else
							Thread.sleep(50);
					}
					
				} finally{
					socket.close();
				}
			
		} catch(Exception e ){
			e.printStackTrace();
		} finally{
			
			listener.close();
		}
	}
}

