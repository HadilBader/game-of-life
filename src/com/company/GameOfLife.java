package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class Cells extends JPanel
{
    boolean[][] grid;
    int h, w;
    Cells(boolean[][] grid)
    {
       this.grid = grid;
        h = grid.length;
        w = grid[0].length;
    }
    {
        setBounds(50, 20, 961, 620);
        setBackground(Color.DARK_GRAY);
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //g2.setColor(Color.BLUE);

        for (int x = 0; x < w * 10; x+=10)
        {
            for (int y = 0; y < h * 10; y+=10)
            {
                if (grid[y/10][x/10])
                {
                    g2.setColor(Color.BLUE);
                    g2.fillRect(x, y, 10, 10);
                }
                else
                {
                    g2.setColor(Color.gray);
                    g2.drawRect(x, y, 10, 10);
                }
            }
        }
    }
}
public class GameOfLife extends JFrame
{
    public final int height = 60;
    public final int width = 90;

    Universe universe = new Universe(height, width, (int) Math.random(), "Random");

    Cells cells = new Cells(universe.getCurrentGeneration());

    JLabel generationLabel = new JLabel("Generation#" + universe.getGeneration());
    JLabel aliveLabel = new JLabel("Alive: " + universe.getAlive());

    JButton resetButton, speedUpButton, slowDownButton;
    JToggleButton playToggleButton;

    String[] items = {"random", "Glider", "Gun", "Spaceship", "Beacon", "Pulsar"}; //to be added
    JComboBox patterns = new JComboBox(items); //to be added

    ActionListener repaint = e ->
    {
        universe.moveToNextState();
        generationLabel.setText("Generation #" + universe.getGeneration());
        aliveLabel.setText("Alive: " + universe.getAlive());
        cells.grid = universe.getCurrentGeneration();
        repaint();
        setVisible(true);
    };

    int speed = 100;
    Timer timer = new Timer(speed, repaint);

    public GameOfLife()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setBackground(Color.darkGray);
        getContentPane().setBackground(Color.darkGray);

        generationLabel.setName("GenerationLabel");
        aliveLabel.setName("AliveLabel");
        resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");
        playToggleButton = new JToggleButton("Pause");
        playToggleButton.setName("PlayToggleButton");
        speedUpButton = new JButton("Speed+");
        slowDownButton = new JButton("Speed-");

        add(cells);
        addLabels();
        addButtons();
        addFunctionality();

        timer.start();

        setVisible(true);
    }

    void addLabels()
    {
        JPanel labels = new JPanel()
        {
            {
                setBounds(50, 636, 200, 40);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBackground(Color.DARK_GRAY);
                generationLabel.setForeground(Color.LIGHT_GRAY);
                aliveLabel.setForeground(Color.LIGHT_GRAY);
                add(generationLabel);
                add(aliveLabel);
            }
        };
        add(labels);
    }
    void addButtons()
    {
        JPanel buttons = new JPanel()
        {
            {
                setBounds(250, 636, 500, 40);
                setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                setBackground(Color.DARK_GRAY);
                resetButton.setForeground(Color.darkGray);
                playToggleButton.setForeground(Color.darkGray);
                speedUpButton.setForeground(Color.darkGray);
                slowDownButton.setForeground(Color.darkGray);

                resetButton.setBackground(Color.LIGHT_GRAY);
                playToggleButton.setBackground(Color.LIGHT_GRAY);
                speedUpButton.setBackground(Color.LIGHT_GRAY);
                slowDownButton.setBackground(Color.LIGHT_GRAY);

                add(resetButton);
                add(playToggleButton);
                add(speedUpButton);
                add(slowDownButton);
            }
        };
        add(buttons);
    }
    void addFunctionality()
    {
        playToggleButton.addActionListener(e ->
        {
             if (playToggleButton.getText().equals("Play") && !timer.isRunning())
             {
                 timer.restart();
                 playToggleButton.setText("Pause");
             }
             else if (playToggleButton.getText().equals("Pause") && timer.isRunning())
             {
                 timer.stop();
                 playToggleButton.setText("Play");
             }
        });
        speedUpButton.addActionListener(e ->
        {
            if (speed == 0)
            {}
            else
                timer.setDelay(speed -= 50);
        });
        slowDownButton.addActionListener(e -> timer.setDelay(speed += 50));
        resetButton.addActionListener(e -> universe.reset(height, width, (int) Math.random()));
    }
    public static void main(String[] args)
    {
        new GameOfLife();
    }
}
