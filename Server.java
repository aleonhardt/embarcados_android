import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {


	public static void main(String[] args) {
		Socket socket;
		DataInputStream inputStream;
		ServerSocket server;
		try {
			server = new ServerSocket(8888);

			socket = server.accept();

			inputStream= new DataInputStream(socket.getInputStream());
			DataOutputStream outputStream= new DataOutputStream(socket.getOutputStream());

			while(true)
			{
				try{

					System.out.println(inputStream.readUTF());
				} catch (EOFException e) {
					System.out.println("closing");
					inputStream.close();
					socket.close();
					server.close();
					return;
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	



	}

}
