import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class Client {

	public static final int DO = 220;
	public static final int FA = 294;
	public static final int LA = 349;
	
	public static void main(String[] args) {
		try {
			String ip = "192.168.0.2";

			Socket socket = new Socket(ip, 8888);

			DataInputStream inputStream= new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream= new DataOutputStream(socket.getOutputStream());
			
			
			outputStream.writeUTF(Integer.toString(DO));
			Thread.sleep(5000);

			outputStream.writeUTF(Integer.toString(FA));
			Thread.sleep(5000);
			
			outputStream.writeUTF(Integer.toString(LA));
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
