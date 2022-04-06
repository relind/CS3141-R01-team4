package com.team_software.cs3141_project;

import java.io.*;

import java.util.Scanner;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Client {

    private static String serverIp = "141.219.196.118";
    private static int port = 6066;


    static String input;
    static Socket server;
    static PrintWriter out;
    static Scanner in;
    static String userName = "PC";

    private Executor executor = Executors.newCachedThreadPool();

    public String getIP(String PeerID)
    {
        out.println(PeerID);
        return in.nextLine();
    }

    public void startUp(String UserID) throws IOException {
        server = new Socket(serverIp, port);

        in = new Scanner(server.getInputStream());

        out = new PrintWriter(server.getOutputStream());


        out.println(UserID);
        out.flush();

        File directoryPath = new File("CS3141-R01-team4/conversations");
        String contents[] = directoryPath.list();

        for(int i = 0; i < contents.length; i++)
        {
            String peerID = contents[i].replace(".txt", "");

            String peerIP = getIP(peerID);

            Socket peerSocket = new Socket(peerIP, port);
            System.out.println("client connected to peer at " + peerIP);

            this.handleConnection(peerSocket);
        }
    }

    public void handleConnection(Socket peer) throws IOException {
        ClientConnection newPeer = new ClientConnection(this, peer);
        this.executor.execute(newPeer);
    }

    public void getMessage(String peerID, String message)
    {
        File file = new File(peerID + ".txt");
        try (PrintWriter out = new PrintWriter(file)){
            out.append("R " + message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String peerID, String message) throws IOException {
        String peerIP = getIP(peerID);

        Socket peerSocket = new Socket(peerIP, port);

        PrintWriter out = new PrintWriter(peerSocket.getOutputStream());

        out.println(peerID + " " + message);
        out.close();
    }

    public static void main(String[] args) throws IOException {

        Client client = new Client();

        Scanner systemIn = new Scanner(System.in);

        System.out.println("what is your username?");

        client.startUp(systemIn.nextLine());





    }
}
