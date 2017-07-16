package multicast.client;

import java.net.*;

/**
 * Created by root on 1/23/17.
 */
public class MultiCastSender {

    private byte[] sendData;
    private byte[] receiveData = new byte[1024];
    private static final int PORT = 5354;
    private static final String multiCastAddress = "225.4.5.6";
    private static final int TIMEOUT = 10000;

    public MultiCastSender() {

    }

    public void sendPacket(String msg) {

        try(DatagramSocket socket = new DatagramSocket()) {

            InetAddress receiverAddress = InetAddress.getByName(multiCastAddress);

            sendData = msg.getBytes();

            DatagramPacket sentPacket = new DatagramPacket(sendData, sendData.length, receiverAddress, PORT);

            socket.send(sentPacket);

            DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);

            socket.setSoTimeout(TIMEOUT); //the window in which all of the packets can be received
            while(true) {
                try {
                    socket.receive(receivedPacket);

                    String retMsg = new String(receivedPacket.getData()).trim();
                    System.out.println(retMsg);

                } catch (SocketTimeoutException e) { //thrown when the timeout occurs.
                    break;
                }
            }
        } catch (Exception e) {}

    }

    private void displayInformation(DatagramSocket socket) {
        System.out.println("Sender LocalAddress: " + socket.getLocalAddress());
        System.out.println("Sender InetAddress: " + socket.getInetAddress());
        System.out.println();
    }

    public static void main(String[] args) {
        MultiCastSender client = new MultiCastSender();

        client.sendPacket(new String("hello"));
    }
}