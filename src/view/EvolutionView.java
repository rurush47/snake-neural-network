package view;

import utils.TextAreaOutputStream;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.PrintStream;

public class EvolutionView
{
    JTextArea textArea;

    public EvolutionView()
    {
        JFrame window = new JFrame();
        window.add( new JLabel(" Outout" ), BorderLayout.NORTH );
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(30, 30);
        textArea.setEditable(false);
        textArea.setFocusTraversalKeysEnabled(false);

        PrintStream out = new PrintStream( new TextAreaOutputStream( textArea ) );
        System.setOut( out );
        System.setErr( out );
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.OUT_BOTTOM);

        JScrollPane pane = new JScrollPane(textArea);

        window.add(pane);
        window.pack();

        textArea.setFocusable(true);
        textArea.requestFocusInWindow();
        window.setVisible(true);

        System.out.println( "Window init" );

    }

    public void addListener(KeyListener k)
    {
        textArea.addKeyListener(k);
    }
}