/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HashApp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Sean
 */
public class HashApp extends JPanel implements ActionListener {

    private String hashOutput;
    private JButton hashFunctionButton;
    private JTextField inputField;
    private JPanel topDisplayPanel;
    private JLabel outputLabel;
    private JLabel inputLabel;
    private static int width = 500;             // starting width of screen
    private static int height = 200;            // starting height of screen

    public HashApp() {        // init function sets starting state for GUI
        super();
        setLayout(new FlowLayout());    // sets layout for GUI elements
        setSize(width, height);         // sets initial size of window

        topDisplayPanel = new JPanel(new BorderLayout());

        inputLabel = new JLabel("Please input a phrase to be hashed: ");
        topDisplayPanel.add(inputLabel, BorderLayout.NORTH);

        outputLabel = new JLabel(" ");

        inputField = new JTextField(25);
        inputField.addActionListener(this);
        topDisplayPanel.add(inputField, BorderLayout.SOUTH);

        hashFunctionButton = new JButton("Hash!");
        hashFunctionButton.addActionListener(this);

        add(topDisplayPanel);
        add(hashFunctionButton);
        add(outputLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == hashFunctionButton)||(e.getSource() == inputField)) {
            try {
                MessageDigest m = MessageDigest.getInstance("MD5");
                m.reset();
                m.update(inputField.getText().getBytes());
                byte[] digest = m.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                //Gets the new byte array of the Encrypted text
                hashOutput = bigInt.toString(16);
                //Turns the Byte Array into a string.
                while (hashOutput.length() < 32) {
                    hashOutput = 0 + hashOutput;
                }   //Makes the MD5 Encryption readable. 0 = padding.

                outputLabel.setText(hashOutput);

                repaint();

                // output hashes to a txt file
                try {
                    BufferedWriter output;
                    output = new BufferedWriter(new FileWriter("Output.txt", true));
                    output.newLine();
                    output.append(hashOutput);
                    output.close();
                } catch (IOException e1) {
                    System.out.println("Error during reading/writing");
                }

            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Proper Algorithm for MD5 Missing.");
            }
        }
    }
        // Driver function to initialize window
    public static void main(String[] args) {
        HashApp panel = new HashApp();  // window for drawing 
        JFrame application = new JFrame(); // the program itself 

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set frame to exit when it is closed 

        application.add(panel);        // set up DrawBullseye within the JFrame
        application.setSize(500, 200); // window is 500 pixels wide, 400 high 
        application.setVisible(true);

        application.setTitle("MD5 Hash Creator");   // Set the title of the window
    }
}