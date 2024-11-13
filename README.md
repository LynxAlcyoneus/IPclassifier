# IPclassifier

IPclassifier is a Java-based tool that allows you to analyze and classify IPv4 addresses. This project helps with validating, checking, and calculating network-related information for IP addresses, including determining IP class, subnet information, network and broadcast addresses, and the classification of private and public IP addresses.

## Features

- **IPv4 Address Validation**: Validates if a given string is a valid IPv4 address.
- **Private and Public IP Check**: Identifies whether the IP address is private or public.
- **IP Class Classification**: Classifies the IP address into Class A, B, C, D, or E based on its first octet.
- **Network and Broadcast Address Calculation**: Computes the network and broadcast addresses for a given IP address and subnet prefix.
- **Subnet Calculation**: Calculates the number of possible subnets based on the provided IP class and subnet prefix.
- **Host Calculation**: Determines the number of possible hosts within the subnet.

## Prerequisites

- **Java**: Ensure you have Java 8 or later installed on your system.

## Installation

To use the IPclassifier, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/LynxAlcyoneus/IPclassifier.git
   cd IPclassifier

## Run

- javac IP.java
- java IP

## Example
  ```bash
Enter an IP address (or type 'exit' to quit): 192.168.1.1
Enter subnet (in prefix format): 24

Valid IPv4: 192.168.1.1
IP Class: Class C
The IP is private.
Number of possible hosts: 254
Network Address: 192.168.1.0
Broadcast Address: 192.168.1.255
Number of possible subnets: 1

