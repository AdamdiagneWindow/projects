package bucky;

import java.awt.FlowLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class jText extends JFrame {

    JTextField textField;
    JPanel panel;
    JButton button1;
    JButton button2;

    public jText() {
        //setSize(300, 300);  // better to use pack() (after components added)
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // better to use
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setLocationRelativeTo(null);  // better to use..
        setLocationByPlatform(true);
        setTitle("Bla Blubb");
        setResizable(false);
        //setLayout(null); // better to use layouts with padding & borders

        // set a flow layout with large hgap and vgap.
        panel = new JPanel(new FlowLayout(SwingConstants.LEADING, 100, 100));
        // panel.setBounds(5, 5, 290, 290); // better to pack()
        add(panel);

        //textField = new JTextField(); // suggest a size in columns
        textField = new JTextField(8);
        //textField.setBounds(5, 5, 280, 50); // to get height, set large font
        textField.setFont(textField.getFont().deriveFont(16f));
        panel.add(textField, BorderLayout.SOUTH);

        pack(); // make the GUI the minimum size needed to display the content
        setVisible(true);
        
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                String text = textField.getText();

                System.out.println(text);
            }
        }); 
        
        
    }

    public static void main(String[] args) {
        // GUIS should be constructed on the EDT.
        JFrame tt = new jText();
        
        
        
    }
}