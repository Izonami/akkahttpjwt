package service;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import controller.RouteController;

import java.util.concurrent.CompletionStage;

public final class MasterService implements Service {

    private static final String SYSTEM_NAME = "route";
    private static final String URL = "localhost";
    private static final int PORT = 8080;

    private static Service instance;

    private ActorSystem system;
    private Http http;
    private ActorMaterializer materializer;
    private Flow<HttpRequest, HttpResponse, NotUsed> routeFlow;
    private CompletionStage<ServerBinding> bind;

    private MasterService() {}

    @Override
    public void start() {
        //Создание акторной системы
        system = ActorSystem.create(SYSTEM_NAME);
        //
        http = Http.get(system);
        materializer = ActorMaterializer.create(system);
        //Мапинг путей
        routeFlow = new RouteController().createRoute().flow(system, materializer);
        //Поднимаем слушателя с роутами
        bind = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost(URL, PORT), materializer);
    }

    @Override
    public void stop() {
        bind
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }

    @Override
    public void restart() {
        //TODO: Почитать как можно перезапустить
    }

    public static Service getInstance() {
        if(instance == null) {
            instance = new MasterService();
        }

        return instance;
    }
}
