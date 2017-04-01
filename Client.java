import java.io.*;
import java.net.*;

public class Client
{

	static PrintWriter pr=new PrintWriter(System.out,true);
	private Socket socket=null;
	private InputStream in=null;
	private String ipAddress;
	private int portNo;
	private String fileName="receive.txt";
	private int fileSize=202;
	private int bytesRead;
	private int currentTotal=0;

	Client(String ipAddress,int portNo)
	{
		this.ipAddress=ipAddress;
		this.portNo=portNo;
	}

	void receiveFile()
	{
		try
		{
			pr.println("Looking for Connection");
			socket=new Socket(ipAddress,portNo);
			pr.println("Connection established");
			in=socket.getInputStream();
			byte[] bytearray=new byte[fileSize];

			FileOutputStream fos=new FileOutputStream(fileName);
			BufferedOutputStream bos=new BufferedOutputStream(fos);

			do
			{
				bytesRead=in.read(bytearray,currentTotal,bytearray.length-currentTotal);
				if(bytesRead>=0)
					currentTotal+=bytesRead;
			}
			while(bytesRead>-1);

			bos.write(bytearray,0,currentTotal);
			bos.flush();
			bos.close();
			socket.close();


			String data=new String(bytearray);
			pr.println(data);
			pr.println("Client- Recieved the file");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		pr.println("Enter the ipAddress");
		String ipAddress=br.readLine();
		pr.println("Enter the port no");
		int portNo=Integer.parseInt(br.readLine());
		Client user=new Client(ipAddress,portNo);
		user.receiveFile();
	}
}