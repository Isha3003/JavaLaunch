package com.javalaunch.service.port;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;

@Service
public class PortService {

    public int findAvailablePort() {

        for (int port = 8081; port <= 9000; port++) {

            try (ServerSocket socket = new ServerSocket(port)) {

                return port;

            } catch (IOException ignored) {

            }

        }

        throw new RuntimeException("No free port available.");

    }

}