package com.picsart.service;

import com.picsart.model.Timer;

import javax.swing.*;

public class ShowTimer implements Runnable {
    private final Timer timer;
    private JLabel jLabel;

    public ShowTimer(Timer timer, JLabel jLabel) {
        this.timer = timer;
        this.jLabel = jLabel;
    }

    private static String print(int hour, int minute, int second) {

        StringBuilder stringBuilder = new StringBuilder();
        if (hour < 10) {
            stringBuilder.append("0").append(hour).append(":");
        } else {
            stringBuilder.append(hour).append(":");
        }
        if (minute < 10) {
            stringBuilder.append("0").append(minute).append(":");
        } else {
            stringBuilder.append(minute).append(":");
        }
        if (second < 10) {
            stringBuilder.append("0").append(second);
        } else {
            stringBuilder.append(second);
        }
        return stringBuilder.toString();
    }

    @Override
    public void run() {

        int hour = timer.getHour();
        int minute = timer.getMinute();
        int second = timer.getSecond();
      while (hour != 0 || minute != 0 || second != 0){
            if (second == 0) {
                if (minute == 0) {
                    hour--;
                    minute = 59;
                } else {
                    minute--;
                }
                second = 59;
            } else {
                second--;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
               hour=0;
               minute=0;
               second=0;
            }
            jLabel.setText(print(hour, minute, second));
        }
    }
}