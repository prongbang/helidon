package com.prongbang.helidon;

import io.helidon.media.jackson.server.JacksonSupport;
import io.helidon.webserver.*;

import java.io.IOException;
import java.util.logging.LogManager;

public class Main {

    public static void main(String... args) throws IOException {

        new Main().startWebServer();
    }

    private void startWebServer() throws IOException {

        // load logging configuration
        LogManager.getLogManager()
                .readConfiguration(Main.class.getResourceAsStream("/logging.properties"));

        Routing routing = Routing
                .builder()
                .register(JacksonSupport.create())
                .get("/api/hello", this::sayHi)
                .build();

        ServerConfiguration config = ServerConfiguration
                .builder()
                .port(4000)
                .build();

        WebServer.create(config, routing).start()
                .thenAccept(ws -> {
                    System.out.println("WEB server is up! http://localhost:" + ws.port() + "/api/hello");
                    ws.whenShutdown().thenRun(() -> System.out.println("WEB server is DOWN. Good bye!"));
                })
                .exceptionally(t -> {
                    System.err.println("Startup failed: " + t.getMessage());
                    t.printStackTrace(System.err);
                    return null;
                });
    }

    private void sayHi(ServerRequest serverRequest, ServerResponse serverResponse) {

        serverResponse.send(new HelloResponse("Hello !!!"));
    }

    private class HelloResponse {
        private String message;

        HelloResponse(String message) {
            this.message =message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
