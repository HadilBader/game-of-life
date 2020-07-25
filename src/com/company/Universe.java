package com.company;

import java.util.Random;

public class Universe
{
    private int generation;
    private int alive;
    private boolean[][] currentGeneration;
    private boolean[][] nextGeneration;
    private Random random;


    public Universe(int height, int width, int seed, String pattern)
    {
       this.currentGeneration = new boolean[height][width];
       if (pattern.equalsIgnoreCase("Random"))
       {
           random = new Random(seed);
           for (int i = 0; i < height; i++) {
               for (int j = 0; j < width; j++) {
                   currentGeneration[i][j] = random.nextBoolean();
               }
           }
       }
       else if (pattern.equalsIgnoreCase("glider"))
       {
           getGlider(currentGeneration);
       }
        nextGeneration = generateNextGeneration(currentGeneration);
        generation = 1;
        alive = calculateAlive(currentGeneration);
    }



    //Getters and instance methods

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
        boolean[][] temp = generateNextGeneration(nextGeneration);
        currentGeneration = nextGeneration;
        nextGeneration = temp;
        alive = calculateAlive(currentGeneration);
        generation++;
    }
    void reset(int h, int w, int seed)
    {
        this.currentGeneration = new boolean[h][w];
        random = new Random(seed);
        for (int i = 0; i < h; i++)
        {
            for (int j = 0; j < w; j++)
            {
                this.currentGeneration[i][j] = random.nextBoolean();
            }
        }
        nextGeneration = generateNextGeneration(currentGeneration);
        generation = 1;
        alive = calculateAlive(currentGeneration);
    }

    //Utility methods

    static int calculateNeighbours(boolean[][] grid, int row, int column)
    {
        int neighbours = 0, r, c;
        int N = grid.length;
        int M = grid[0].length;

        for (int p = -1; p <= 1; p++)
        {
            for (int m = -1; m <= 1; m++)
            {
                r = row + p;
                c = column + m;

                if (r < 0)
                    r = N - 1;
                if (r > N - 1)
                    r = 0;
                if (c < 0)
                    c = M - 1;
                if (c > M - 1)
                    c = 0;

                if (grid[r][c] && (p != 0 || m != 0))
                    neighbours++;
            }
        }
        return neighbours;
    }

    static int calculateAlive(boolean[][] grid)
    {
        int alive = 0;
        for (int i = 0; i < grid.length; i++)
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                if (grid[i][j])
                    alive++;
            }
        }
        return alive;
    }

    static boolean[][] generateNextGeneration(boolean[][] currentGeneration)
    {
        int N = currentGeneration.length;
        int M = currentGeneration[0].length;
        boolean[][] nextGeneration = new boolean[N][M];
        int neighbours;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < M; j++)
            {
                neighbours = calculateNeighbours(currentGeneration, i, j);

                if (neighbours == 3 || (currentGeneration[i][j] && neighbours == 2))
                    nextGeneration[i][j] = true;
                else
                    nextGeneration[i][j] = false;
            }
        }
        return nextGeneration;
    }

    static boolean[][] generateNthGeneration(boolean[][] currentGeneration, int X)
    {
        if (X == 0)
            return currentGeneration;
        else
            return generateNthGeneration(generateNextGeneration(currentGeneration), X - 1);
    }
    static void printGeneration(boolean[][] generation)
    {
        for (int i = 0; i < generation.length; i++)
        {
            for (int j = 0; j < generation[0].length; j++)
                System.out.print(generation[i][j]? "O" : " ");
            System.out.println();
        }

    }

    static void getGlider(boolean currentGeneration[][])
    {
        for(int i = 0; i < 60; i++)
        {
            for (int j =0; j < 90; j++)
                currentGeneration[i][j] = false;
        }
        currentGeneration[1][3] = true;
        currentGeneration[2][3] = true;
        currentGeneration[3][3] = true;
        currentGeneration[2][1] = true;
        currentGeneration[3][2] = true;
    }
}
