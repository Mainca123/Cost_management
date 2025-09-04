package com.example.HeThongQuanLyTaiChinhThongMinh.config;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

@Component
public class BrowserLauncher implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port:8080}")
    private int port;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        new Thread(() -> {
            String url = "http://localhost:" + port + "/page/home";
            String os = System.getProperty("os.name").toLowerCase();
            try {
                if (os.contains("win")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } else if (os.contains("mac")) {
                    Runtime.getRuntime().exec("open " + url);
                } else if (os.contains("nix") || os.contains("nux")) {
                    Runtime.getRuntime().exec("xdg-open " + url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
}
