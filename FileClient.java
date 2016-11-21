/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.nio.file.Files;
/**
 *
 * @author Haseeb Khizar
 */
/**
 *
 * @author deser_000
 */
public class FileClient {

    private DatagramSocket socket = null;
    private FileHandler event = null;
    private String sourceFilePath;
    private String destinationPath;
    private String hostName;

    public FileClient() {
        this.hostName = "localHost";
        this.sourceFilePath = "C:\\Users\\Haseeb Khizar\\Documents\\NetBeansProjects\\filetransfer\\src\\FileHandler.java";
        this.destinationPath = "";
    }

    public void createConnection() {
        try {
            //send the file out to the server
            socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(hostName);
            byte[] incomingData = new byte[1024];
            //The file that is to be sent
            event = getFileHandler();
            
            //send the file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(event);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            socket.send(sendPacket);
            System.out.println("File sent from client");
            
            //receive confirmation
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            socket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());
            System.out.println("Response from server:" + response);
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileHandler getFileHandler() {
        FileHandler  fileHandle = new FileHandler();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
        fileHandle.setDestinationDirectory(destinationPath);
        fileHandle.setFilename(fileName);
        fileHandle.setSourceDirectory(sourceFilePath);
        File file = new File(sourceFilePath);
        if (file.isFile()) {
            try{
                byte[] filedata = Files.readAllBytes(file.toPath());
                fileHandle.setFileData(filedata);
                fileHandle.setFileSize(filedata.length);
                fileHandle.setStatus("OK");        
            } catch (Exception e) {
                e.printStackTrace();
                 fileHandle.setStatus("Error");
            }
        } else {
            System.out.println("path specified is not pointing to a file");
             fileHandle.setStatus("Error");
        }
        return  fileHandle;
    }

    public static void main(String[] args) {
        FileClient client = new FileClient();
        client.createConnection();
    }

}

