package ru.kaulina.Refactor;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class StatusHandler implements Handler {
    private final HttpStatus httpStatus;

    public StatusHandler(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public void handle(com.sun.net.httpserver.Request request, BufferedOutputStream responseStream) throws IOException {
        ResponseInfo responseInfo = new ResponseInfo(httpStatus);
        responseStream.write(responseInfo.build().getBytes());
    }

    @Override
    public void handle(Request request, BufferedOutputStream responseStream) throws IOException {
        ResponseInfo responseInfo = new ResponseInfo(httpStatus);
        responseStream.write(responseInfo.build().getBytes());
    }
}