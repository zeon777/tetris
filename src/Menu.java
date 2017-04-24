import sun.plugin.javascript.navig.Link;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by zeon on 13.04.2017.
 */
public class Menu extends JPanel {
    SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM.YY HH:mm");
    JButton startButton;
    JButton HighScoresButton;
    JButton HelpButton;
    JButton ExitButton;
    JTextArea textArea;

    JLabel label;
    JLabel name;
    Font font = new Font("Verdana", Font.BOLD, 14);
    String pName = "-";
    static JFrame HelpFrame;
    static JFrame HighScoresFrame;
    HighScore highScore = new HighScore();



    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
       // this.setSize(115,530);
        g.setColor(Color.blue);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        this.setBackground(Color.cyan);

    }


   public void StartMetod() {
        //this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.setLayout( new GridLayout(6,1));

        highScore = new HighScore();
        startButton = new JButton("START");
        startButton.addActionListener(new ButtonStartListener());
        HighScoresButton = new JButton("HighScores");
        HighScoresButton.addActionListener(new ButtonHighScoresListener());
        HelpButton = new JButton("   HELP   ");
        HelpButton.addActionListener(new ButtonHelpListener());
        ExitButton = new JButton("Exit");
        ExitButton.addActionListener(new ButtonExitListener());
        JPanel aa =new JPanel();
        name = new JLabel("<HTML>PlayerName:<br> <Center><p style=\"color:#ff0000\">"+ pName +"</HTML>");
        name.setFont(font);
        label = new JLabel("<HTML>You Score: <br> <Center><p style=\"color:#ff0000\">0</HTML>");
        label.setFont(font);
        aa.setBackground(Color.cyan);
        aa.add(name);
        JPanel bb =new JPanel();

        bb.add(label);

        bb.setBackground(Color.cyan);
        this.add(aa);
        this.add(startButton);
        this.add(HighScoresButton);
        this.add(HelpButton);
        this.add(ExitButton);
        this.add(bb);

    }




    public void EnterPName()
    {   textArea = new JTextArea();
        JFrame enterName = new JFrame("EnterName");

        JLabel pLable = new JLabel("Enter you name:");
        pLable.setForeground(Color.white);
        JButton button = new JButton("Save Name");
        button.addActionListener(new ButtonSaveNameListener());


        enterName.setBounds(0,0,150,100);
        enterName.setDefaultCloseOperation(HelpFrame.DISPOSE_ON_CLOSE);

        enterName.setLocationRelativeTo(null);
        enterName.add(BorderLayout.NORTH,pLable);
        enterName.add(BorderLayout.SOUTH,button);
        enterName.add(BorderLayout.CENTER,textArea);
        enterName.getContentPane().setBackground(new Color(100,50,150));
        enterName.setVisible(true);
        enterName.setFocusable(true);
    }

    class ButtonSaveNameListener  implements ActionListener      //отслеживание нажатия Save Name
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            pName = textArea.getText();
            name.setText("<HTML>PlayerName:<br> <Center><p style=\"color:#ff0000\">"+ pName +"</HTML>");

        }
    }



    class ButtonExitListener implements ActionListener      //отслеживание нажатия Кнопки Exit
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    class ButtonStartListener implements ActionListener      //отслеживание нажатия Кнопки Start
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(!Tetris.conTinue && !Tetris.gameover)
            { Tetris.conTinue = true;
            startButton.setText("PAUSE");
                Tetris.poleF.myGamePanel.requestFocus();
                 }
             else {
                Tetris.conTinue = false;
                startButton.setText("START");
            }

            if(Tetris.gameover)
            {Tetris.score = 0;
            Tetris.poleF.pole = new int[20][10];
            Tetris.figure = Tetris.FG.createRandomFigure(5, 0);
            Tetris.gameover = false;

                Tetris.poleF.myGamePanel.paintComponent(Tetris.poleF.myGamePanel.getGraphics());
                Tetris.conTinue = true;
                startButton.setText("PAUSE");
                Tetris.poleF.myGamePanel.requestFocus();



            }


        }
    }
    class ButtonHelpListener  implements ActionListener      //отслеживание нажатия Кнопки HELP
    {
        @Override
        public void actionPerformed(ActionEvent e)
           {
            HelpMetod();
           }
    }

    class ButtonHighScoresListener  implements ActionListener      //отслеживание нажатия Кнопки HighScores
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            HighScoreMetod(Tetris.gameover,true);
        }
    }


    public void HelpMetod()                                //Метод вывода окна помощи вызвываемый ButtonHelpListener'ом
    {
        if(HelpFrame ==null)
        HelpFrame = new JFrame("Help");
        else
        HelpFrame.setFocusable(true);

        HelpFrame.setBounds(0,0,200,200);
        HelpFrame.setDefaultCloseOperation(HelpFrame.DISPOSE_ON_CLOSE);
        HelpFrame.setLocationRelativeTo(null);
        HelpFrame.getContentPane().setBackground(new Color(100,50,150));


        JLabel jTextPane = new JLabel();
        jTextPane.setForeground(Color.white);
        jTextPane.setFont(font);
        jTextPane.setText("<html>For game use next key:<br> 'a' - for left move" +
                "<br> 'd' - for right move" + "<br> 's' - for force down move"+
                        "<br> 'w' - for figure rotate</html>");
        HelpFrame.add(BorderLayout.CENTER,jTextPane);
        HelpFrame.pack();
        HelpFrame.setVisible(true);


    }
    public void HighScoreMetod(boolean isGameOver,boolean clic)                           //Метод создаёт окно таблицы результатов (Каждый раз при вызове обновляет таблицу)
    {



        if(isGameOver && Tetris.score > 0 && !clic)
        {
            if(highScore.HighScoreArray.size()<15) {
                highScore.HighScoreArray.add(new Player(pName, new Date(), Tetris.score * 100));
                highScore.HighScoreArray.sort(Player::compareTo);
            }

            else if(highScore.HighScoreArray.get(15).getScore()<Tetris.score*100)
            {
                highScore.HighScoreArray.add(new Player(pName,new Date(),Tetris.score*100));
                highScore.HighScoreArray.sort(Player::compareTo);
                highScore.HighScoreArray.remove(16);
            }
            XML(true,highScore);
        }

        highScore= XML(false,highScore);
        highScore.HighScoreArray.sort(Player::compareTo);

        System.out.println(highScore.getHighScoreArray().size());



        if(HighScoresFrame ==null)
            HighScoresFrame = new JFrame("HighScores");
        else
            HighScoresFrame.setFocusable(true);

        HighScoresFrame.setBounds(0,0,300,400);
        HighScoresFrame.setDefaultCloseOperation(HelpFrame.DISPOSE_ON_CLOSE);
        HighScoresFrame.setLocationRelativeTo(null);
        HighScoresFrame.getContentPane().setBackground(new Color(100,50,150));


        highScore.HighScoreArray.sort(Player::compareTo);
        TableModel tableModel = new HScoreTable(highScore.HighScoreArray);

        JTable table = new JTable(tableModel);

        table.getTableHeader().setFont(font);
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setBackground(new Color(90,45,130));
        table.getColumn("Date").setPreferredWidth(130);

        JLabel label = new JLabel("<HTML><Center><p <font size=\"5\" color=\"white\" face=\"Arial\">HighScore:</font><HTML>");

        table.setFont(font);
        table.setForeground(Color.white);
        table.setGridColor(Color.white);
        table.setBackground(new Color(100,50,150));
        table.setSize(250,350);


        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);


        HighScoresFrame.setLayout(null);

        HighScoresFrame.add(scrollPane);
        HighScoresFrame.add(label);
        label.setBounds(95,0,100,50);
        scrollPane.setBounds(4,50,275,305);
        HighScoresFrame.setVisible(true);



    }
    @XmlRootElement
    @XmlType(name = "highScore")
    @XmlAccessorType(XmlAccessType.FIELD)
   static class HighScore                   //!!!!!!!Класс с таблицой результатов содержит класc строки результата Player
    {
        //@XmlElementWrapper(name="playersArray", nillable = true)
        public List<Player> HighScoreArray;


        public HighScore()
        {HighScoreArray= new ArrayList<>();}

        public void setHighScoreArray(List<Player> highScoreArray) {
            HighScoreArray = highScoreArray;
        }

        public List<Player> getHighScoreArray() {
            return HighScoreArray;
        }


    }


    @XmlRootElement
    @XmlType(name = "playerTclass")
    @XmlAccessorType(XmlAccessType.FIELD)
     public static class Player implements Comparable<Player>
    {


        private String name;

       private Date dateOfGame;

        private int score;



        Player(String name, Date dateOfGame, int score)
        {
            this.name = name;
            this.dateOfGame = dateOfGame;
            this.score = score;



        }

        public Player()
        {}

        public Date getDateOfGame() {
            return dateOfGame;
        }
        public String getName() {
            return name;
        }
        public int getScore() {
            return score;
        }

        public void setDateOfGame(Date dateOfGame) {
            this.dateOfGame = dateOfGame;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public int compareTo(Player o) {
            return o.getScore() - this.getScore();
        }
    }

HighScore XML(boolean save,HighScore HS)
{
    try {



        JAXBContext context = JAXBContext.newInstance(Menu.Player.class,Menu.HighScore.class);
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        //marshaller.marshal(highScore,fileWriter);
        //highScore.HighScoreArray.clear();
        if(!save) {
            FileReader reader = new FileReader("hstable.xml");
            HS = (HighScore) unmarshaller.unmarshal(reader);
            reader.close();
            return HS;
        }
        else if(save) {
            FileWriter fileWriter = new FileWriter("hstable.xml");
            marshaller.marshal(HS, fileWriter);
            fileWriter.close();

        }

    } catch (Exception e) {
        e.printStackTrace();
    }


    return HS;
}








    public class HScoreTable implements TableModel                                     // Тут реализуем интерфейс TableModel под таблицу из класса HighScore, чтобы отрисовывать таблицу в окне
    {
        private Set<TableModelListener> listeners = new HashSet<>();
        private List<Player> playerList;

        public HScoreTable(List<Player> players)
        {playerList = players;}
        @Override
        public int getRowCount() {
            return playerList.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:return "Name";
                case 1:return "Date";
                case 2:return "Score";
            }
            return "";
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            switch (columnIndex)
            {
                case 0:return String.class;
                case 1:return String.class;
                case 2:return int.class;
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Player player = playerList.get(rowIndex);
            switch (columnIndex) {
                case 0:return player.getName();
                case 1:return dateFormat.format(player.getDateOfGame());
                case 2:return player.getScore();
            }
            return "";
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        }

        @Override
        public void addTableModelListener(TableModelListener l) {
            listeners.add(l);
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
            listeners.remove(l);
        }
    }




}




