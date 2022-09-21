
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Operace {
    JFrame fr = new JFrame("hodiny");


    JTable tabulka = new JTable(new DefaultTableModel(new String[][]{
            {"Čas 1", ""},
            {"Čas 2", ""},
            {"Čas 3", ""},
    }, new String[]{"Úsek", "Čas"}));
    JButton start = new JButton("Start");
    JButton lap = new JButton("Lap");
    JButton stop = new JButton("Stop");
    JButton reset = new JButton("Reset");
    JLabel timer = new JLabel("00:00:00.00");
    boolean bezi = false;
    boolean vynulovano = true;
    long timeBegin = -1;
    int cas = 0;

    public void aktivace() {
        fr.setBounds(200, 300, 600, 600);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setLayout(null);
        fr.setResizable(false);


        timer.setBounds(20, 270, 560, 150);
        fr.add(timer);

        start.setBounds(100, 425, 175, 25);
        start.addActionListener(this::startButton);
        fr.add(start);

        lap.setBounds(100, 460, 175, 25);
        lap.addActionListener(this::lapButton);
        fr.add(lap);

        stop.setBounds(300, 425, 175, 25);
        stop.addActionListener(this::stopButton);
        fr.add(stop);

        reset.setBounds(300, 460, 175, 25);
        reset.addActionListener(this::resetButton);
        fr.add(reset);

        tabulka.setBounds(20, 30, 544, 228);
        tabulka.setDefaultRenderer(Object.class, new Sloupec());
        tabulka.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        tabulka.setRowHeight(76);
        fr.add(tabulka);

        fr.setVisible(true);
    }

    public void startButton(ActionEvent e){
        

        if (!bezi) {
            timeBegin = System.currentTimeMillis();
            bezi = true;
            vynulovano = false;
            setTimer();
        } 
    }
    public void lapButton(ActionEvent e){

        if (bezi) {
            tabulka.setValueAt(getTime(), cas++, 1);
            if (cas == 3) {
                bezi = false;
                timer.setText(tabulka.getValueAt(cas - 1, 1).toString());
            }
        } 
    }
    public void stopButton(ActionEvent e){
        

        if (bezi) {
            bezi = false;
            timer.setText(tabulka.getValueAt(cas - 1, 1).toString());
        } 
    }
    public void resetButton(ActionEvent e){
        
            if (!vynulovano) {
            vynulovano = true;
            timer.setText("00:00:00.00");
            cas = 0;
            while (cas != 3)
                tabulka.setValueAt("", cas++, 1);
            cas = 0;

        }
    }
    
    public String getTime() {
        int diff = (int) (System.currentTimeMillis() - timeBegin);
        int hour = diff / (1000 * 60 * 60);
        diff = diff - hour * (1000 * 60 * 60);
        int mins = diff / (1000 * 60);
        diff = diff - mins * (1000 * 60);
        int secs = diff / 1000;
        diff = diff - secs * 1000;
        int mili = diff / 10;
        return String.format("%02d:%02d:%02d.%02d", hour, mins, secs, mili);
    }

    public void setTimer() {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        if (bezi) {
                            timer.setText(getTime());
                            setTimer();
                        }
                    }
                },
                50
        );
    }
}