package networkinterfaces;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

// A network interface is the point of interconnection between a computer and a private or public network.
// A network interface is generally a network interface card (NIC), but does not have to have a physical form.

// NetworkInterface is useful for a multi-homed system, which is a system with multiple NICs.
// Using NetworkInterface, you can specify which NIC to use for a particular network activity.

// InterfaceAddress is used instead of InetAddress when you need more information about an interface address beyond its IP address.
// For example, you might need additional information about the subnet mask and broadcast address when the address is an IPv4 address,
// and a network prefix length in the case of an IPv6 address.
public class NetworkInterfaceDemo {

    public static void main(String[] args) throws SocketException, IOException {

        //System.out.println("Host Address: " + InetAddress.getByName("localhost").getHostAddress());
        //displayNetworkInterfaces();
        //displayWifiNetworkInterface();
        System.out.println(getLocalAddress().getHostAddress());
        //upAndSupportMultiCast();

    }

    public static void displayNetworkInterfaces() throws SocketException {

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();

        for(NetworkInterface ni : Collections.list(nis)) {

            System.out.println("Display Name: " + ni.getDisplayName());
            System.out.println("Name: " + ni.getName());

            displayInterfaceAddresses(ni);

            System.out.println();
        }
    }

    public static void displayInterfaceAddresses(NetworkInterface ni) {

        for (InetAddress inetAddress : Collections.list(ni.getInetAddresses())) {
            System.out.println("InetAddress: %" + inetAddress);
        }
        System.out.println();
    }

    public static void displayWifiNetworkInterface() throws SocketException {

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();

        while(nis.hasMoreElements()) {

            NetworkInterface ni = nis.nextElement();
            if(ni.getName().contains("wl"))
                System.out.println(ni.getDisplayName());
        }
        System.out.println();
    }

    public static InetAddress getLocalAddress() throws SocketException {

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        while(nis.hasMoreElements()) {

            NetworkInterface ni = nis.nextElement();
            //if(ni.isUp() && !ni.isLoopback())
                //System.out.println(ni.getDisplayName());

            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while(ias.hasMoreElements()){

                InetAddress ia = ias.nextElement();
                if(!ia.isLoopbackAddress() && ia.isSiteLocalAddress())
                    return ia;
            }
        }
        //System.out.println();
        return null;
    }

    public static void upAndSupportMultiCast() throws IOException {

        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        while(nis.hasMoreElements()) {

            NetworkInterface ni = nis.nextElement();

            if(ni.isUp() && ni.supportsMulticast())
                System.out.println(ni.getDisplayName());
        }
    }
}
