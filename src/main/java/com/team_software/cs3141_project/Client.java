package com.team_software.cs3141_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

    private static String serverIp = "141.219.194.213";
    private static int port = 6066;


    public static void main(String[] args) throws IOException {

        Scanner myObj = new Scanner(System.in);
        String input;
        Socket server = new Socket(serverIp, port);
        PrintWriter out = new PrintWriter(server.getOutputStream(), true);
        out.println("Austin has connected to the server");
        System.out.println("Input:  ");

        while(true) //Infinite loop
        {
            input = myObj.nextLine();
            if(input.equals("exit") || input.equals("Exit")) //Checks to see if exit is called from scanner input.
            {
                out.println("Closing closing due to exit call"); //Prompts server msg to close.
                server.close();
                break;
            }
            else
            {
                out.println(input); //Prints input to server.
            }
        }
    }
}