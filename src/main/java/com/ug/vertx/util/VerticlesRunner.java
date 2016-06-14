package com.ug.vertx.util;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Vertx.io verticles runner
 * Created by mhd.ug on 6/14/16.
 */
public class VerticlesRunner {

    /**
     * run verticle using its .class
     *
     * @param clazz
     */
    public static void runVerticle(Class clazz) {
        runVerticle(clazz, new VertxOptions().setClustered(false), null);
    }

    public static void runVerticle(Class clazz, VertxOptions options, DeploymentOptions
            deploymentOptions) {
        runVerticle(clazz.getName(), options, deploymentOptions);
    }

    public static void runVerticle(String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
        if (options == null) {
            // Default parameter
            options = new VertxOptions();
        }

        Consumer<Vertx> runner = vertx -> {
            try {
                if (deploymentOptions != null) {
                    vertx.deployVerticle(verticleID, deploymentOptions);
                } else {
                    vertx.deployVerticle(verticleID);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        if (options.isClustered()) {
            Vertx.clusteredVertx(options, res -> {
                if (res.succeeded()) {
                    Vertx vertx = res.result();
                    runner.accept(vertx);
                } else {
                    res.cause().printStackTrace();
                }
            });
        } else {
            Vertx vertx = Vertx.vertx(options);
            runner.accept(vertx);
        }
    }
}
