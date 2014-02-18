/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

/**
 *
 * @author Tomas
 */
public class receiver extends Thread
{

    private JTextArea messages;
    private gui parent;

    receiver(JTextArea textArea, gui parent) {
        messages = textArea;
        this.parent=parent;
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(4321, 20);
            for (;;) {
                Socket client = server.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String message = reader.readLine();
                messages.append(parent.getAdressName(client.getInetAddress().getHostAddress()) + ":\n");
                while (message != null) {
                    messages.append(message + "\n");
                    message = reader.readLine();
                }
                client.close();
                messages.append("\n");
                messages.setCaretPosition(messages.getDocument().getLength());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
