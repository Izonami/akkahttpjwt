package controller;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;

public final class RouteController extends AllDirectives {

    public Route createRoute() {
        return route(
          path("hello", ()-> get(()->complete("Hello World!")))
        );
    }
}
