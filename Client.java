import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class Client {

	public static void main(String[] args) {
		try {
			String ip = "127.0.0.1";

			Socket socket = new Socket(ip, 8888);

			DataInputStream inputStream= new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream= new DataOutputStream(socket.getOutputStream());
			
			
			outputStream.writeUTF("440");
			Thread.sleep(5000);

			outputStream.writeUTF("660");
			Thread.sleep(5000);
			
			outputStream.writeUTF("1200");
			outputStream.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
