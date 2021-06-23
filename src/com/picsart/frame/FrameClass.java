package com.picsart.frame;

import com.picsart.model.Timer;
import com.picsart.service.ShowTimer;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrameClass {
    //Executor vor clear karenam tam
    private static ExecutorService service;
    private static JPanel jPanel = getPanel();
    private static final JLabel label = new JLabel("00:00:00");

    //Frame
    public static JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Timer");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        jFrame.setBounds(dimension.width / 2 - 200, dimension.height / 2 - 50, 400, 100);

        jPanel.add(label);
        jFrame.add(jPanel);

        return jFrame;
    }

    //Panel
    private static JPanel getPanel() {
        JPanel jPanel = new JPanel();

        JButton start = new JButton("Start");
        JButton clear = new JButton("Clear");


        JTextField hour = new JTextField("00", 2);
        JTextField minute = new JTextField("00", 2);
        JTextField second = new JTextField("00", 2);

        jPanel.add(hour);
        jPanel.add(minute);
        jPanel.add(second);

        jPanel.add(start);
        jPanel.add(clear);

        start.addActionListener(e -> {
           //Stugum enq tvyalner@ valide te voch
            boolean isValid = validate(hour.getText(), minute.getText(), second.getText());

            //Sksum enq Timer@
            if (isValid) {
                startTimer(Integer.parseInt(hour.getText()),
                        Integer.parseInt(minute.getText()),
                        Integer.parseInt(second.getText()));
                //nra hamara vor mi qani click i depqum treadner chstextsi
                //vorovhetev erkrordi depqum arajini kontrol@ koruma
                start.setEnabled(false);
            } else {
                label.setText("Input is incorrect");
            }

        });

        clear.addActionListener(e -> {
            start.setEnabled(true);
            service.shutdownNow();
            hour.setText("00");
            minute.setText("00");
            second.setText("00");
        });
        return jPanel;
    }

    private static boolean validate(String hour, String minute, String second) {
        try {
            if (Integer.parseInt(hour) < 0 || Integer.parseInt(hour) > 23 ||
                    Integer.parseInt(minute) < 0 || Integer.parseInt(minute) > 59 ||
                    Integer.parseInt(second) < 0 || Integer.parseInt(second) > 59) {
                return false;
            } else
                return true;

        } catch (Exception e) {
            return false;
        }
    }

    private static void startTimer(int hour, int minute, int second) {

         service = Executors.newSingleThreadExecutor();
            Timer timer = new Timer(hour, minute, second);
            service.execute(new ShowTimer(timer, label));

    }

}
