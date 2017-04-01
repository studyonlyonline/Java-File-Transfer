import java.io.*;
import java.net.*;

public class Server
{
	PrintWriter pr=new PrintWriter(System.out,true);
	private ServerSocket serverSocket=null;
	
	//file Details
	private String fileName=null;
	private String fileExtension=null;
	private String completeFileName=null;
	private byte[] fileData;
	File fileToTransfer=null;

	//network details
	private Socket socket=null;
	private String ipAddress;
	private int portNo;
	private InputStream in=null;
	private OutputStream out=null;

	void printDetails()
	{
		try
		{
			InetAddress address=InetAddress.getLocalHost();
			ipAddress=address.getHostAddress();

			pr.println("Printing the important details \n");
			pr.println("Ip address: "+ipAddress);
			pr.println("Port No :"+portNo);
		}
		catch(UnknownHostException ex)
		{
			ex.printStackTrace();
		}

	}

	void connection()
	{
		pr.println("Making Connection");
		try
		{
			serverSocket=new ServerSocket(0);
			portNo=serverSocket.getLocalPort();

			//print network related details
			printDetails();

			socket=serverSocket.accept();
			pr.println("Connection Established");

			FileInputStream fin=new FileInputStream(fileToTransfer);
			BufferedInputStream bin=new BufferedInputStream(fin);

			//Output Stream
			out=socket.getOutputStream();
			completeFileName=completeFileName+"$\n";
			byte[] temp=completeFileName.getBytes();
			out.write(temp,0,temp.length);

			//read int the fileData bytearray
			bin.read(fileData,0,(int)fileToTransfer.length());
			out.write(fileData,0,(int)fileToTransfer.length());
			out.flush();
			out.close();
			pr.println("File Transfer complete");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	void setFileData()
	{
		fileName="fileToSend";
		fileExtension=".txt";
		completeFileName=fileName+fileExtension;
		fileToTransfer=new File(completeFileName);
		fileData=new byte[(int)fileToTransfer.length()+500];
	}


	public static void main(String[] args) 
	{
		Server newServer=new Server();
		newServer.setFileData();
		newServer.connection();
	}
}