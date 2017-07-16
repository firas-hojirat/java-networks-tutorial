package udp.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// UDP (User Datagram Protocol) is a protocol that sends independent packets of data, called datagrams, 
// from one computer to another with no guarantees about arrival and the order of delivery. 

// UDP is not connection-based like TCP.
// UDP IS FASTER THAN TCP.

public class UDP_Server {

    public static void main(String[] args) throws Exception{ 
                 
        byte[] receiveData = new byte[1024];
        byte[] sendData;
        //The Server Receives the Packets at this port.
        //if there is no ip specified, it binds the socket to all of the network interfaces.
        try(DatagramSocket serverSocket = new DatagramSocket(5354);){

            while(true){ 
                //Receiving a Packet doesn't require knowledge of the Packet's IP and Port. 
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                //the data received is in the Packet's Buffer.
                serverSocket.receive(receivePacket);//Blocks until recipient of the packet.

                String sentence = new String( receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
                //The IP and Port of the Packet's Sender.
                //The Client Doesn't have to specify a Port number to Receive, since
                //a free Port is randomally generated if no Port number is specified in the
                //Client's DatagramSocket Constructor.
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                //Sending a Packet Requires the target's IP and Port.
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            } 

        }
    }
    
}
