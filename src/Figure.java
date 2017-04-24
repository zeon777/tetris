import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Integer.SIZE;

/**
 * Created by zeon on 13.04.2017.
 */
public class Figure {
   private int[][] figure;
    int Xpos;
    int Ypos;

   static boolean dvizhenie;
       Figure(int[][] nFigure,int Xpos,int Ypos)
    {
        this.figure = nFigure;
        this.Xpos = Xpos;
        this.Ypos = Ypos;

    }
    Figure()
    {
        figure = new int[4][4];
    }


    public void right()
    {
        Xpos++;
        if(!CorrectPosition(figure))
        Xpos--;}
    public void left()
    {Xpos--;
        if(!CorrectPosition(figure))
        Xpos++;}
    public void down()
    {Ypos++;
    if(!CorrectPosition(figure))
        up();

    }
    public void up()
    {Ypos--;
    Tetris.PrizIProv();

    }
    public void rotate()
    {

        int temp = 0;
        final int size = figure.length -1;
        int[][] figurTemp = new int[size+1][size+1]; // temp array for figure matrix before rotate

//rotate src rows to dest columns:
        for (int x=0; x <= size; x++) {
            for (int y = 0; y <= size; y++) {

                    figurTemp[y][x] = figure[x][size-y];
            }
        }


        for (int i = 0,t=0;i<size&& t==0;i++) {
            for (int x = 0; x <= size && t == 0;x++){

                if(figurTemp[0][x]!=0)
                {t=1;}
                else if (x == size) {
                    for (int y = 0; y < size; y++) {
                        figurTemp[y] = figurTemp[y + 1];
                    }
                    figurTemp[size] = new int[size + 1];
                }

            }
        }

        if(CorrectPosition(figurTemp))
        System.arraycopy(figurTemp,0,figure,0,figure.length);// copy result tu carrent matrix
    }

    public int getXpos() {
        return Xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public int[][] getFigure() {
        return figure;
    }

    public void setFigure(int[][] figure) {
        this.figure = figure;
    }

    public  boolean CorrectPosition(int[][] figure)
    {

       for(int x = 0;x<figure[0].length;x++)
       {
           for (int y = 0;y<figure.length;y++)
           {
               if(figure[y][x]!=0)
               {

                       if(Xpos+x>9||Xpos+x<0||Ypos+y>19||Ypos+y<0)
                           return false;
                       else if( Tetris.poleF.getPole()[Ypos+y][Xpos+x]!=0)
                           return false;



               }

           }
       }

        return true;
    }



}
