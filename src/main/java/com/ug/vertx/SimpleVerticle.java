package com.ug.vertx;

import com.ug.vertx.util.VerticlesRunner;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;

/**
 * Created by kancy on 6/14/16.
 */
public class SimpleVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        VerticlesRunner.runVerticle(SimpleVerticle.class);
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(event -> {
            event.response().end("Hello Vertx");
        }).listen(8090);
    }
}
