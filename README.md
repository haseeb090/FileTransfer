# FileTransfer
Introduction
A java application that allows the user to send and receive a zip file of size greater than or equal to 1MB.

Approach
Using the unreliable UDP protocol, the file is split into datagrams and a sequence number is added to each datagram. Then this datagram is sent to a single threaded server, identified by an IP address and a port number where the server is listening for the incoming data. At the server end, the zip file is rebuilt by keeping a track of the incoming sequence numbers and acknowledging the successfully received packet number.
Share the IP address and port number of the server with your peer and receive the file sent by him. 

Usage
User A can swiftly transfer file to an ip specified by User B.
