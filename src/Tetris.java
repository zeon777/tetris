import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

/**
 * Created by zeon on 13.04.2017.
 */
public class Tetris {
static boolean gameover = true;
static boolean conTinue = false;
   public static FramePole poleF;
    static Figure figure;
    static Figure figureOld;
    static FigureGenerator FG = new FigureGenerator();
    static int score;
    static KeyUlovitel KU;
    static Thimer TMR;

   public static void PrizIProv()                                           // Вызов проверки полний, смещение поля и создание новой фигуры
   {
      score += poleF.Prizemlenie(figure);
      poleF.menu.label.setText("<HTML> You Score: <br><center><p style=\"color:#ff0000\">"+String.valueOf(score*100)+"</HTML>");

       poleF.myGamePanel.paintComponent(poleF.myGamePanel.getGraphics());

       figure = FG.createRandomFigure(5, 0);

       figureOld.setFigure(figure.getFigure().clone());
       figureOld.Ypos = figure.getYpos();
       figureOld.Xpos = figure.getXpos();
       if (!figure.CorrectPosition(figure.getFigure()) && !gameover)                   //Проверка возможности создать новую фигуру и если нет то ГеймОвер
       {gameover = true;
       conTinue = false;
       poleF.menu.HighScoreMetod(true,false);
       poleF.menu.startButton.setText("AGAIN?");
       }


   }


    public static void main(String...args)  {

Tetris tetris = new Tetris();
TMR = tetris.new Thimer();


        poleF = new FramePole();
        KU = new KeyUlovitel();
        figureOld = new Figure();

        poleF.go();
       // Menu.HighScore HS = poleF.menu.highScore;   //new Player("Nic",new Date().getTime(),500);
      //  HS.HighScoreArray.add(poleF.menu.new Player("Nic",new Date(),500));
       // HS.HighScoreArray.add(poleF.menu.new Player("Fich",new Date(),800));
       // HS.HighScoreArray.add(poleF.menu.new Player("Bill",new Date(),500));

        poleF.myGamePanel.setFocusable(true);
        poleF.myGamePanel.setSize(251,530);
       // figure = FG.createRandomFigure(5, 0);
       // poleF.myGamePanel.paintComponent(poleF.myGamePanel.getGraphics());

        poleF.myGamePanel.addKeyListener(KU);
        poleF.menu.EnterPName();

       TMR.run();

    }

    public static class KeyUlovitel implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            System.out.println(e.getKeyChar());
            figureOld.setFigure(figure.getFigure().clone());
            figureOld.Ypos = figure.getYpos();
            figureOld.Xpos = figure.getXpos();
            switch (e.getKeyChar())
            {case 'a':{figure.left();poleF.myGamePanel.paintFigure(poleF.myGamePanel.getGraphics(), figure,figureOld); };  break; //129
                case 'd':{figure.right();
                    poleF.myGamePanel.paintFigure(poleF.myGamePanel.getGraphics(), figure,figureOld); };  break; //132
                  case 's':{figure.down();
                      poleF.myGamePanel.paintFigure(poleF.myGamePanel.getGraphics(), figure,figureOld); };  break; //147
                case 'w':{figure.rotate();poleF.myGamePanel.paintFigure(poleF.myGamePanel.getGraphics(),figure,figureOld);



                }  break; //64
            }
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            if(e.getKeyChar()=='s')
                figure.dvizhenie=false;
        }
    }

    public class Thimer implements Runnable
    {
        int thimeout = 500;

        int count = 400;
        @Override
        public void run() {

            while (true)
            {
                if(conTinue) {
                    poleF.myGamePanel.setFocusable(true);

                    figureOld.setFigure(figure.getFigure().clone());
                    figureOld.Ypos = figure.getYpos();
                    figureOld.Xpos = figure.getXpos();
                    figure.down();
                    poleF.myGamePanel.paintFigure(poleF.myGamePanel.getGraphics(), figure,figureOld);

                    count++;
                }

                if(TMR.count == 600 || TMR.count == 800 || TMR.count == 1100)
                 TMR.thimeout = (int) (TMR.thimeout*1.8f/(TMR.count/300));

                try {
                    Thread.sleep(thimeout);

                   // System.out.println(count + " "+ thimeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
