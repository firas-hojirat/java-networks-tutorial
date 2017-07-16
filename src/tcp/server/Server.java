package tcp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 3/3/17.
 */
public class Server {

    private static void testCase1() {// throws ConnectException: Connection refused at the client side.

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCase2() { //throws ConnectException: Connection refused at the client side.
        try {
            ServerSocket serverSocket = new ServerSocket(5354);
            serverSocket.close();
            Thread.sleep(10000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCase3() { //throws SocketException: Connection reset at the client side

        try {
            ServerSocket serverSocket = new ServerSocket(5354);
            Thread.sleep(10000);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCase4() { // returns -1 at the client side.
        try {
            ServerSocket serverSocket = new ServerSocket(5354);
            Socket socket = serverSocket.accept();
            Thread.sleep(10000);
            socket.close();
            Thread.sleep(10000);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //closing the socket will also close the IO Streams
    // in which the client side will get end of stream and reading the InputStream will yield -1.
    private static void testCase5() { // returns -1 at the client side.

        try {
            ServerSocket serverSocket = new ServerSocket(5354);
            Socket socket = serverSocket.accept();
            socket.close();
            Thread.sleep(10000);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCase6() {

        try {
            ServerSocket serverSocket = new ServerSocket(5354);

            Thread.sleep(1 * 60 * 1000);
            Socket socket = serverSocket.accept();

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printWriter.println(bufferedReader.readLine().toUpperCase());
            printWriter.flush();

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testCase7() throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(5354);
        Socket socket = ss.accept();

        Thread.sleep(1 * 60 * 1000);
        socket.close();
        ss.close();
    }

    private static void testCase8() throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(5354);

        Thread.sleep(1 * 60 * 1000);
        Socket socket = ss.accept();
        socket.getOutputStream().write(1);
        socket.getOutputStream().flush();

        Thread.sleep(1 * 60 * 1000);
        socket.close();
        ss.close();
    }

    private static void testAcceptDecline1(boolean accept) {
        try {
            ServerSocket serverSocket = new ServerSocket(5354);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    if (accept)
                        System.out.println(bufferedReader.readLine());

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testAcceptDecline2(boolean accept) {
        try {
            ServerSocket serverSocket = new ServerSocket(5354);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String m = bufferedReader.readLine();
                    if (m.equals("SEND")) {
                        if (!accept) {
                            printWriter.println("DECLINE");
                            printWriter.flush();
                        } else {
                            printWriter.println("ACCEPT");
                            printWriter.flush();
                            System.out.println(bufferedReader.readLine());
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        testAcceptDecline2(false);
    }
}
