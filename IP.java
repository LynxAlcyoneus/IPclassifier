import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.regex.Matcher;

class ipformating {

    // Regular expression for IPv4 addresses
    private static final String IPv4_PATTERN =
            "^((25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}" +
            "(25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$";

    private static final Pattern pattern = Pattern.compile(IPv4_PATTERN);

    // Method to validate IPv4 address
    public static boolean isValidIPv4(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    // Method to check if the IP is private
    public static boolean isPrivateIPv4(String ip) {
        if (!isValidIPv4(ip)) {
            return false;
        }
        int[] octets = new int[4];
        String[] parts = ip.split("\\.");
        for (int i = 0; i < 4; i++) {
            octets[i] = Integer.parseInt(parts[i]);
        }
        // Private IP ranges
        return (octets[0] == 10) ||
               (octets[0] == 172 && octets[1] >= 16 && octets[1] <= 31) ||
               (octets[0] == 192 && octets[1] == 168);
    }

    // Method to calculate the network address
    public static String getNetworkAddress(String ip, int prefix) {
        int ipAddr = ipToInt(ip);
        int mask = 0xFFFFFFFF << (32 - prefix);
        int network = ipAddr & mask;
        return intToIp(network);
    }

    // Method to calculate the broadcast address
    public static String getBroadcastAddress(String ip, int prefix) {
        int ipAddr = ipToInt(ip);
        int mask = 0xFFFFFFFF << (32 - prefix);
        int broadcast = ipAddr | ~mask;
        return intToIp(broadcast);
    }

    // Convert IP string to integer
    private static int ipToInt(String ip) {
        String[] parts = ip.split("\\.");
        int result = 0;
        for (String part : parts) {
            result = result << 8;
            result |= Integer.parseInt(part);
        }
        return result;
    }

    // Convert integer to IP string
    private static String intToIp(int ip) {
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." +
               ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
    }

    // Method to classify the IP address
    public static String classifyIPv4(String ip) {
        if (!isValidIPv4(ip)) {
            return "Invalid IP";
        }
        int firstOctet = Integer.parseInt(ip.split("\\.")[0]);
        if (firstOctet >= 1 && firstOctet <= 126) {
            return "Class A";
        } else if (firstOctet >= 128 && firstOctet <= 191) {
            return "Class B";
        } else if (firstOctet >= 192 && firstOctet <= 223) {
            return "Class C";
        } else if (firstOctet >= 224 && firstOctet <= 239) {
            return "Class D";
        } else if (firstOctet >= 240 && firstOctet <= 255) {
            return "Class E";
        } else {
            return "Invalid IP";
        }
    }

    // Method to calculate the number of subnets
    public static int getNumberOfSubnets(int prefix, String ipClass) {
        int defaultPrefix;
        switch (ipClass) {
            case "Class A":
                defaultPrefix = 8;
                break;
            case "Class B":
                defaultPrefix = 16;
                break;
            case "Class C":
                defaultPrefix = 24;
                break;
            default:
                return -1; // Not applicable for Class D and E
        }
        return (int) Math.pow(2, prefix - defaultPrefix);
    }
}

class ipchk extends ipformating {

    void check() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter an IP address (or type 'exit' to quit): ");
            String ip = scanner.nextLine();

            if (ip.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter subnet (in prefix format): ");
            String subnetInput = scanner.nextLine();
            int subnet;

            try {
                subnet = Integer.parseInt(subnetInput);
                if (subnet < 0 || subnet > 32) {
                    System.out.println("Invalid subnet value. Please enter a value between 0 and 32.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid subnet format. Please enter a numeric value.");
                continue;
            }

            double possibleAddresses = Math.pow(2, 32 - subnet);
            String networkAddress = getNetworkAddress(ip, subnet);
            String broadcastAddress = getBroadcastAddress(ip, subnet);

            if (isValidIPv4(ip)) {
                System.out.println("Valid IPv4: " + ip);
                String ipClass = classifyIPv4(ip);
                System.out.println("IP Class: " + ipClass);
                if (isPrivateIPv4(ip)) {
                    System.out.println("The IP is private.");
                } else {
                    System.out.println("The IP is public.");
                }
                System.out.println("Number of possible hosts: " + (int) possibleAddresses);
                System.out.println("Network Address: " + networkAddress);
                System.out.println("Broadcast Address: " + broadcastAddress);

                if (ipClass.equals("Class A") || ipClass.equals("Class B") || ipClass.equals("Class C")) {
                    int numberOfSubnets = getNumberOfSubnets(subnet, ipClass);
                    System.out.println("Number of possible subnets: " + numberOfSubnets);
                }
            } else {
                System.out.println("Invalid IPv4: " + ip);
            }

            System.out.println();
        }

        scanner.close();
    }
}

public class IP {

    public static void main(String[] args) {
        ipchk chk = new ipchk();
        chk.check();
    }
}
