package tcp.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static boolean isOnline() {

        boolean isOnline = true;

        try (Socket socket = new Socket(InetAddress.getLocalHost(), 5354)) {
            socket.setSoTimeout(1000);

            if (socket.getInputStream().read() == -1)
                isOnline = false;
        } catch (IOException e) {
            isOnline = false;
        }
        return isOnline;
    }

    private static void testCase6() throws IOException {

        //throws ConnectException if the ServerSocket is not created/closed.
        Socket socket = new Socket(InetAddress.getLocalHost(), 5354);

        //throws IOException if THIS socket is closed/THIS socket is not connected.
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //If no byte is available because the end of the stream has been reached, the value -1 is returned.
        // This method blocks until input data is available, the end of the stream is detected, or an exception is thrown.
        //int r = is.read();

        printWriter.println("hello");
        printWriter.flush();

        System.out.println("waiting for server...");
        System.out.println(bufferedReader.readLine());

        //Closing this socket will also close the socket's InputStream and OutputStream.
        socket.close();
    }

    private static void testCase7() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5354);

        System.out.println("waiting for server...");
        int read = socket.getInputStream().read();

        if (read == -1)
            System.out.println("server is not Online");
        else System.out.println("server is Online");

        socket.close();
    }

    private static void testAcceptDecline1() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5354);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

        printWriter.println("SEND");
        printWriter.flush();

        socket.close();
    }

    private static void testAcceptDecline2() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 5354);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        printWriter.println("SEND");
        printWriter.flush();

        String m = bufferedReader.readLine();
        if(m.equals("ACCEPT")){
            printWriter.println("FILE");
            printWriter.flush();
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        testAcceptDecline2();
    }
}
