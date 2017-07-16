package tcp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Server //Echo Server, returns what it receives.
{    
    private static ServerSocket server;
    private static Socket socket; 
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static final int PORT = 5354;
    
    // TCP is connection based protocol that provides a reliable flow of data between two computers, 
    // that is the data sent from one end of the connection is guaranteed to get to the other end
    // and in the same order that it was sent.(TCP is SLOWER than UDP)
    
    // A SOCKET is one end-point of a two-way communication link between two programs running on the network.
    // An ENDPOINT is a combination of an IP address and a port number. 
    
    // Every TCP connection can be uniquely identified by its two endpoints. 
    // That way you can have multiple connections between your host and the server.
    
    public static void main(String[] args) 
    {    
        try
        {
            String line;
            // if there is no ip specified, it binds the socket to all of the network interfaces.
            server = new ServerSocket(PORT);
           
            // Listens for a connection to be made to this socket and accepts it. 
            // The method blocks until a connection is made        
            socket = server.accept();
            
            // throws IOException if THIS socket is closed/THIS socket is not connected.
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
                
            // If no byte is available because the end of the stream has been reached, the value -1 is returned.
        	// This method blocks until input data is available, the end of the stream is detected, 
        	// or an exception is thrown.
        		
        	// is.read();
            
            while(true)
            {
                reader = new BufferedReader(new InputStreamReader(is));
                writer = new PrintWriter(os);

                line = reader.readLine(); // blocks until there is input data available.             

                writer.println(line); 
                writer.flush(); // writes the buffer's contents to the stream.  
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
                if(server != null)
                    server.close();
            }
            
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }   
    }
}
