package org.apache.flink;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class example7ds
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
					while ((line = br.readLine()) != null){
						
						out.println(line);
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

