
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Operace {
    JFrame frame = new JFrame("Je čas měřit čas");


    JTable tabulka = new JTable(new DefaultTableModel(new String[][]{
            {"Bertík", ""},
            {"Žertík", ""},
            {"Alfons", ""}
    }, new String[]{"Vážení občané", "Časy"}));
    JButton startOrLap = new JButton("Start");
    JButton stopOrReset = new JButton("Reset");
    JLabel timer = new JLabel("00:00:00.00");
    boolean bezi = false;
    boolean vynulovano = true;
    long timeBegin = -1;
    int finishers = 0;

    public void aktivace() {
        funguj();
    }

    public void funguj() {
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);


        timer.setBounds(20, 270, 560, 150);
        timer.setFont(new Font("Ms Sans Serif Regular", Font.PLAIN, 100));
        prepare(timer);

        startOrLap.setBounds(100, 425, 175, 25);
        startOrLap.addActionListener(this::clickityClickIsThatADick);
        prepare(startOrLap);

        stopOrReset.setBounds(300, 425, 175, 25);
        stopOrReset.addActionListener(this::clickityClickIsThatADick);
        stopOrReset.setEnabled(false);
        prepare(stopOrReset);

        tabulka.setBounds(20, 30, 544, 228);
        tabulka.setDefaultRenderer(Object.class, new Sloupec());
        tabulka.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        tabulka.setRowHeight(76);
        prepare(tabulka);

        frame.setVisible(true);
    }


    public void clickityClickIsThatADick(ActionEvent e) {
        Object source = e.getSource();
        if (startOrLap.equals(source) && startOrLap.isEnabled()) {
            if (!bezi) {
                timeBegin = System.currentTimeMillis();
                startOrLap.setText("Lap");
                stopOrReset.setText("Stop");
                stopOrReset.setEnabled(true);
                bezi = true;
                vynulovano = false;
                setTimer();
            } else {
                tabulka.setValueAt(getTime(), finishers++, 1);
                if (finishers == 3) {
                    bezi = false;
                    startOrLap.setEnabled(false);
                    stopOrReset.setText("Reset");
                    timer.setText(tabulka.getValueAt(finishers - 1, 1).toString());
                }
            }
        } else if (stopOrReset.equals(source)) {
            if (bezi) {
                bezi = false;
                startOrLap.setEnabled(false);
                stopOrReset.setText("Reset");
                timer.setText(tabulka.getValueAt(finishers - 1, 1).toString());
            } else if (!vynulovano) {
                vynulovano = true;
                startOrLap.setText("Start");
                startOrLap.setEnabled(true);
                stopOrReset.setEnabled(false);

                timer.setText("00:00:00.00");
                finishers = 0;
                while (finishers != 3)
                    tabulka.setValueAt("", finishers++, 1);
                finishers = 0;

            }
        }

        frame.update(frame.getGraphics());
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

    public void prepare(JComponent o) {
        o.setVisible(true);
        frame.add(o);
    }


}