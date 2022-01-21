package dev.thelabradors.yorkpirates;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class YorkPiratesGame extends JFrame {
    
    public YorkPiratesGame() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setSize(500, 400);

        setTitle("Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }    
    
    public static void main(String[] args) {
        
        YorkPiratesGame ex = new YorkPiratesGame();
        ex.setVisible(true);
        ;
    }
}