import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by zeon on 13.04.2017.
 */
public class FramePole {

    JFrame frame;
    MyGamePanel myGamePanel;
    Menu menu;
    int[][] pole = new int[20][10];
    private static Color color= Color.black;


    FramePole()
    {
        frame = new JFrame("TETRIS");
        frame.setLayout(null);
        myGamePanel = new MyGamePanel();
        menu = new  Menu();
        menu.StartMetod();


    }

    public void go()                                        //Рисуем главный фрэйм
    {
        frame.setSize(370,529);
        menu.setBounds(252,0,120,528);
        myGamePanel.setBounds(0,0,250,528);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
       // frame.getContentPane().add(BorderLayout.CENTER,myGamePanel);
       // frame.getContentPane().add(BorderLayout.EAST,menu);
        frame.getContentPane().add(menu);
        frame.getContentPane().add(myGamePanel);
        // frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    class MyGamePanel extends JPanel
    {


        public void paintComponent(Graphics g)                                   //Рисуем игровое поле
        {
            g.setColor(color);
             g.fillRect(0,0,this.getWidth(),this.getHeight());
            Graphics2D g2d = (Graphics2D)g ;

            for (int x=0;x<pole[0].length;x++)
            {for (int y = 0;y<pole.length;y++)
            {
                if(pole[y][x]!=0){
                switch (pole[y][x])
                {
                    case 1:g2d.setColor(Color.red);break;
                    case 2:g2d.setColor(Color.green);break;
                    case 3:g2d.setColor(Color.yellow);break;
                    case 4:g2d.setColor(Color.CYAN);break;
                    case 5:g2d.setColor(Color.blue);break;
                    case 6:g2d.setColor(Color.magenta);
                }
                g2d.fillRect((x*25)+1,(y*25)+1,24,24);}
            }
            }
        }

        public void paintFigure(Graphics g,Figure figure,Figure figureOld)
        {
            Graphics2D g2d = (Graphics2D)g ;
            for (int y=0;y<figureOld.getFigure()[0].length;y++)
            {for (int x = 0;x<figureOld.getFigure().length;x++)
            {
                if(figureOld.getFigure()[y][x]!=0)
                {
                    g2d.setColor(color);
                    g2d.fillRect(((x+figureOld.getXpos())*25)+1,((y+figureOld.getYpos())*25)+1,24,24);
                }
            }
            }
            for (int y=0;y<figure.getFigure()[0].length;y++)
            {for (int x = 0;x<figure.getFigure().length;x++)
            {
                if(figure.getFigure()[y][x]!=0)
                {   switch (figure.getFigure()[y][x])
                {
                    case 1:g2d.setColor(Color.red);break;
                    case 2:g2d.setColor(Color.green);break;
                    case 3:g2d.setColor(Color.yellow);break;
                    case 4:g2d.setColor(Color.cyan);break;
                    case 5:g2d.setColor(Color.blue);break;
                    case 6:g2d.setColor(Color.magenta);
                }
                    g2d.fillRect(((x+figure.getXpos())*25)+1,((y+figure.getYpos())*25)+1,24,24);
                }

            }
            }
        }
    }

    public int[][] getPole() {
        return pole;
    }

    public int Prizemlenie(Figure figure)                 //Перенос матрицы фигуры на матрицу поля и проверка полных линий
    {
        int UdalennieLinii =0;
        int shirinaPolya = pole[0].length;
        java.util.List list = new ArrayList<Integer[]>();

        for(int x = 0;x<figure.getFigure()[0].length;x++)
        {
            for (int y = 0;y<figure.getFigure().length;y++)
            {
                if(figure.getFigure()[y][x]!=0)
                {

                    pole[figure.Ypos+y][figure.Xpos+x]=figure.getFigure()[y][x];

                }

            }
        }
        for(int y = 0;y<pole.length;y++)
        {
            for (int x = 0;x<shirinaPolya;x++)
            {
                if(pole[y][x]!=0)
                {
                    if(x==shirinaPolya-1)
                    {UdalennieLinii++;
                        }
                }
                else
                {
                    list.add(pole[y]);
                    break;
                }

            }
        }

        for (int i = 0;i<UdalennieLinii;i++)
        {list.add(0,new int[shirinaPolya]);}

        for (int i = 0;i<list.size();i++)
        {pole[i]=(int[]) list.get(i);}
        return UdalennieLinii;

    }



}
