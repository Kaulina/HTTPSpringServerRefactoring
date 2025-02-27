package ru.kaulina.Refactor;

import com.sun.net.httpserver.Request;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Handler;

public class ConnectionHandler implements Runnable {

    protected final Socket socket;
    protected final Server rootHandler;
    protected final BufferedReader in;
    protected final BufferedOutputStream out;

    private final RequestReader requestReader;

    public ConnectionHandler(Socket socket, Server rootHandler) throws IOException {
        this.socket = socket;
        this.rootHandler = rootHandler;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedOutputStream(socket.getOutputStream());

        this.requestReader = new RequestReader();
    }

    @Override
    public void run() {
        try {
            final var request = parseRequest(in);
            if (request != null) {
                rootHandler.handle(request, out);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    protected Request parseRequest(BufferedReader in) throws IOException {
        return (Request) requestReader.read(in);
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}