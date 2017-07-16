package udp.client;

import java.io.*;
import java.net.*;

public class UDP_Client {
    
    public static void main(String args[]) throws Exception {
        
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData;
        byte[] receiveData = new byte[1024];
        String sentence;
        
        try(BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));){            

            sentence = inFromUser.readLine();
            //Binds this socket to any available Port.
            //if there is no ip specified, it binds the socket to all of the network interfaces. 
            //no need to specify which port/address, since the Server can retrieve the IP and Port number from the received Packet.
            try(DatagramSocket clientSocket = new DatagramSocket();){

                sendData = sentence.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5354);
                clientSocket.send(sendPacket);
                //no need to specify IP and Port when recieving a Packet.
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //the Data received is in the Packet's Buffer.
                clientSocket.receive(receivePacket);

                String modifiedSentence = new String(receivePacket.getData());
                System.out.println("FROM SERVER:" + modifiedSentence);
            }

        }
        
    } 
    
}
