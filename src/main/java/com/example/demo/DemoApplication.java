package com.example.demo;

import com.example.demo.task.EngineTask;
import com.example.utlis.o;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Timer;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    @Autowired
    EngineTask engineTask ;
    public DemoApplication() {
        o.e("DemoApplication");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(engineTask , 3*1000,3*1000);
            }
        }).start();
   
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
