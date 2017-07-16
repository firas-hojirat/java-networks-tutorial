package tcp.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCP_Client 
{    
    private static Socket socket;
    private static BufferedReader reader;
    private static BufferedReader fromUser;
    private static PrintWriter writer;
    private static InetAddress ip;
    private static final int PORT = 5354;
    private static String line;
    
    public static void main(String[] args) 
    {    
        try
        {
            ip = InetAddress.getByName("localhost");
            // If the specified ip is null it is equivalent to specifying an address of the loopback interface.
            // throws ConnectException if the ServerSocket is not created/closed. 
            socket = new Socket(ip, PORT);
                   
            while(true) 
            {                
                fromUser = new BufferedReader(new InputStreamReader(System.in));
                line = fromUser.readLine();
                
                // throws IOException if THIS socket is closed/THIS socket is not connected.
                OutputStream os = socket.getOutputStream();
                InputStream is = socket.getInputStream();
                
                // If no byte is available because the end of the stream has been reached, the value -1 is returned.
        		// This method blocks until input data is available, the end of the stream is detected, or an exception is thrown.
        		// is.read();
        		
                writer = new PrintWriter(os);
                writer.println(line);
                writer.flush(); // writes the buffer's contents to the stream.
                        		
                reader = new BufferedReader(new InputStreamReader(is));
                line = reader.readLine();
                System.out.println("Server:"+line);
            }
            // Closing the returned OutputStream will close the associated socket.
            // Closing the returned InputStream will close the associated socket. 
        } 
        
        catch(Exception ex)
        {
            ex.printStackTrace();
        } 
        
        finally
        {
            
            try
            {	// Closing this socket will also close the socket's InputStream and OutputStream. 
                if(socket != null)
                    socket.close();
                if(fromUser != null)
                    fromUser.close();
                
            }
            
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
