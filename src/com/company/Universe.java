package com.company;

import java.util.Random;

public class Universe
{
    private int generation;
    private int alive;
    private boolean[][] currentGeneration;
    private boolean[][] nextGeneration;

    public Universe(int height, int width, int seed, String pattern)
    {
        this.currentGeneration = new boolean[height][width];
        if (pattern.equalsIgnoreCase("Random"))
        {
            randomize(currentGeneration, seed);
        }
        else if (pattern.equalsIgnoreCase("glider"))
        {
            gliderize(currentGeneration);
        }
        // to add more patterns
        nextGeneration = generateNextGeneration();
        generation = 1;
        alive = calculateAlive();
    }

    int getGeneration()
    {
        return generation;
    }
    int getAlive()
    {
        return alive;
    }
    boolean[][] getCurrentGeneration()
    {
        return currentGeneration;
    }
    boolean[][] getNextGeneration()
    {
        return nextGeneration;
    }
    void moveToNextState()
    {
        boolean[][] temp = generateNextGeneration();
        currentGeneration = nextGeneration;
        nextGeneration = temp;
        alive = calculateAlive();
        generation++;
    }
    void reset(int height, int width, int seed, String pattern)
    {
        this.currentGeneration = new boolean[height][width];
        if (pattern.equals("random"))
        {
            randomize(currentGeneration, seed);
        }
        // to add more cases
        nextGeneration = generateNextGeneration();
        generation = 1;
        alive = calculateAlive();
    }
    
    int calculateNeighbours(int x, int y)
    {
        int neighbours = 0, row, column;
        int height = currentGeneration.length;
        int width = currentGeneration[0].length;

        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                row = x + i;
                column = y + j;

                row = (row + height) % height;
                column = (column + width) % width;

                if (currentGeneration[row][column] && (i != 0 || j != 0))
                    neighbours++;
            }
        }
        return neighbours;
    }

    int calculateAlive()
    {
        int alive = 0;
        for (int x = 0; x < currentGeneration.length; x++)
        {
            for (int y = 0; y < currentGeneration[0].length; y++)
            {
                if (currentGeneration[x][y])
                    alive++;
            }
        }
        return alive;
    }

    boolean[][] generateNextGeneration()
    {
        boolean[][] nextGeneration = new boolean[currentGeneration.length][currentGeneration[0].length];
        int neighbours;
        for (int x = 0; x < currentGeneration.length; x++)
        {
            for (int y = 0; y < currentGeneration[0].length; y++)
            {
                neighbours = calculateNeighbours(x, y);

                if (neighbours == 3 || (currentGeneration[x][y] && neighbours == 2))
                    nextGeneration[x][y] = true;
                else
                    nextGeneration[x][y] = false;
            }
        }
        return nextGeneration;
    }

    boolean[][] generateNthGeneration(int n)
    {
        if (n == 0)
            return currentGeneration;
        else
            return generateNthGeneration(n - 1);
    }

    static void gliderize(boolean currentGeneration[][])
    {
        for(int x = 0; x < 60; x++)
        {
            for (int y =0; y < 90; y++)
                currentGeneration[x][y] = false;
        }
        currentGeneration[1][3] = true;
        currentGeneration[2][3] = true;
        currentGeneration[3][3] = true;
        currentGeneration[2][1] = true;
        currentGeneration[3][2] = true;
    }
    
    static void randomize(boolean[][] grid, int seed)
    {
        Random random = new Random(seed);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = random.nextBoolean();
            }
        }
    }

}

