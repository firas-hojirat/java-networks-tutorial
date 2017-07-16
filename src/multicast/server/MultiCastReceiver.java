package multicast.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by root on 1/23/17.
 */
public class MultiCastReceiver {

    private byte[] sendData;
    private byte[] receiveData = new byte[1024];
    private static final int PORT = 5354;
    private static final String multiCastAddress = "225.4.5.6";


    public MultiCastReceiver() {


    }

    public void receivePacket() {

        try(MulticastSocket socket = new MulticastSocket(PORT)) {

            InetAddress group = InetAddress.getByName(multiCastAddress);
            socket.joinGroup(group);

            //InetSocketAddress isa = new InetSocketAddress(multiCastAddress, PORT);
            //List<NetworkInterface> nis = joinAllInterfaces(socket, isa);

            DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);

            System.out.println("Waiting for a packet\n");

            socket.receive(receivedPacket);

            InetAddress receiverAddress = receivedPacket.getAddress();
            int receiverPort = receivedPacket.getPort();

            String msg = new String(receivedPacket.getData()).trim();

            System.out.println("Received Packet " + msg +"\n");

            sendData = msg.getBytes();

            DatagramPacket sentPacket = new DatagramPacket(sendData, sendData.length, receiverAddress, receiverPort);

            socket.send(sentPacket);
            //both join methods work when the sender and receiver have the same platform.
            socket.leaveGroup(group);

            /*for(NetworkInterface ni : nis) {
                socket.leaveGroup(isa, ni);
            }*/
        } catch (Exception e) {System.out.println(e.getMessage());}
    }

    private void displayInformation(MulticastSocket socket) throws SocketException {

        System.out.println("Receiver LocalAddress: " + socket.getLocalAddress());
        System.out.println("Receiver InetAddress: " + socket.getInetAddress());
        System.out.println("Receiver Interface: " + socket.getInterface());
        System.out.println("Receiver NetworkInterface: " + socket.getNetworkInterface().getName());
        System.out.println();
    }

    //join all the interfaces that are running and support MultiCast, to receive the arriving packets on them.
    private List<NetworkInterface> joinAllInterfaces(MulticastSocket socket, InetSocketAddress address) throws IOException {

        List<NetworkInterface> runningInterfaces = new ArrayList<>();

        for(NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {

            if(ni.isUp() && ni.supportsMulticast()) {
                runningInterfaces.add(ni);
                socket.joinGroup(address, ni);
            }
        }
        return runningInterfaces;
    }

    public static void main(String[] args) {

        MultiCastReceiver server = new MultiCastReceiver();

        while (true) {
            server.receivePacket();
        }
    }
}
