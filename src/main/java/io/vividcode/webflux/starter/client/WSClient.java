package io.vividcode.webflux.starter.client;

import java.net.URI;
import java.time.Duration;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;

public class WSClient {

	public static void main(final String[] args) {
		final WebSocketClient client = new ReactorNettyWebSocketClient();
		client.execute(URI.create("ws://localhost:8080/echo"), session ->
				session.send(Flux.just(session.textMessage("Hello")))
						.thenMany(session.receive().take(1).map(WebSocketMessage::getPayloadAsText))
						.doOnNext(System.out::println)
						.then())
				.block(Duration.ofMillis(5000));
	}
}
