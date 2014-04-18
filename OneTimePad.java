import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class OneTimePad implements ActionListener {
   
   public static final int ALPHABET = 26; //Size of character set to encipher
   
   //Creating the text boxes that hold the different parts of the cipher, as well as the message box.
   JTextArea clearText = new JTextArea(4, 30);
   JTextArea oneTimePad = new JTextArea(4, 30);
   JTextArea cipherText = new JTextArea(4, 30);
   JLabel message = new JLabel();
   
   public static void main(String[] args) {
      OneTimePad main = new OneTimePad();
   }
   
   public OneTimePad() {
      JFrame window = new JFrame();
      
      window.setTitle("One Time Pad");
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setLocation(new Point(640, 380));
      window.setLayout(new BorderLayout());
      //window.setResizable(false);
      
      //Setting up all of the swing UI stuff
      JPanel textBoxes = new JPanel(new GridLayout(3, 1));
      JPanel buttons = new JPanel(new FlowLayout());
      
      JPanel set1 = new JPanel(new BorderLayout());
      JPanel set2 = new JPanel(new BorderLayout());
      JPanel set3 = new JPanel(new BorderLayout());
     
      set1.add(new JLabel("Clear Text:"), BorderLayout.NORTH);
      set2.add(new JLabel("One Time Pad:"), BorderLayout.NORTH);
      set3.add(new JLabel("Cipher Text:"), BorderLayout.NORTH);
      
      clearText.setLineWrap(true);
      oneTimePad.setLineWrap(true);
      cipherText.setLineWrap(true);
      
      set1.add(clearText);
      set2.add(oneTimePad);
      set3.add(cipherText);
      
      textBoxes.add(set1);
      textBoxes.add(set2);
      textBoxes.add(set3);  
      
      JButton encodeButton = new JButton("Encode");
      JButton decodeButton = new JButton("Decode");
      JButton randomKey = new JButton("Random Key");
      JButton clearButton = new JButton("Clear");
      
      encodeButton.addActionListener(this);
      decodeButton.addActionListener(this);
      randomKey.addActionListener(this);
      clearButton.addActionListener(this);
      
      buttons.add(encodeButton);
      buttons.add(decodeButton);
      buttons.add(randomKey);
      buttons.add(clearButton);
      buttons.add(message);
      
      window.add(textBoxes, BorderLayout.CENTER);
      window.add(buttons, BorderLayout.SOUTH);
      
      window.setPreferredSize(new Dimension(400, 400));
      window.pack();
         
      window.setVisible(true);
   }
   
   public char convert(char input, char pad, boolean encoding) {
      int padVal = pad - 'A'; //figure out the shift for the encoding or decoding
      //The basic algorithm is to for encoding and decoding respectively, get the index of the letter within the alphabet,
      //add the pad value, and then modulo that such that the index is between 0 and 25, then shift it back by the value of 'A'/'a'
      //giving you respectively your cipher or clear text
      
      if (encoding) {
         if (Character.isUpperCase(input)) {
            int index = input - 'A';
            index += padVal;
            index %= 26;
            index += 'A';
            return (char) index;
         } else 
         if (Character.isLowerCase(input)) {
            int index = input - 'a';
            index += padVal;
            index %= 26;
            index += 'a';
            return (char) index;
         } else {
            return input;
         }
      } else {
         if (Character.isUpperCase(input)) {
            int index = input - 'A';
            index -= padVal;
            while (index < 0) index += 26;
            index += 'A';
            return (char) index;
         } else 
         if (Character.isLowerCase(input)) {
            int index = input - 'a';
            index -= padVal;
            while (index < 0) index += 26;
            index += 'a';
            return (char) index;
         } else {
            return input;
         }
      }   
   }
   
   /**
    * Action handlers for the various buttons in the GUI
    */
   public void actionPerformed(ActionEvent event) {
      if (event.getActionCommand().equals("Encode") || event.getActionCommand().equals("Decode")) {
         char[] input = null;
         boolean encode = event.getActionCommand().equals("Encode");
         oneTimePad.setText(oneTimePad.getText().toUpperCase().replaceAll("[^A-Z]", ""));
         char[] pad = oneTimePad.getText().toCharArray();
         if (encode) {input = clearText.getText().toCharArray();}
         if (!encode) {input = cipherText.getText().toCharArray();}
         char[] output = new char[input.length];
         
         if (pad.length < input.length) {
            message.setText("Error: Pad is too short");
         } else {
            for (int ii=0; ii < input.length; ii++) {  
               output[ii] = convert(input[ii], pad[ii], encode);
            }   
            if (encode) {cipherText.setText(new String(output));}
            if (!encode) {clearText.setText(new String(output));}
               
            message.setText(event.getActionCommand() + " Successful!");
         }   
      } else 
      if (event.getActionCommand().equals("Clear")) {
         clearText.replaceRange("", 0, clearText.getText().length());
         oneTimePad.replaceRange("", 0, oneTimePad.getText().length());
         cipherText.replaceRange("", 0, cipherText.getText().length());
         message.setText("");

      } else 
      if (event.getActionCommand().equals("Random Key")) {
         Random r = new Random();
         int length = clearText.getText().length();
         String newKey = "";
         while (newkey.length() < length) {
            newKey += (char) ('A' + r.nextInt(26));
         }
         oneTimePad.setText(newKey);
      }
   }
}
