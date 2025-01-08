package com.springboot.board.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.board.api.v1.dto.request.MessageCreateRequest;
import com.springboot.board.api.v1.dto.response.MessageResponse;
import com.springboot.board.application.service.MessageService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public ChatWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("새로운 WebSocket 연결: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        MessageCreateRequest request = new ObjectMapper().readValue(payload, MessageCreateRequest.class);

        // 메시지 데이터베이스 저장
        MessageResponse savedMessage = messageService.createMessage(request);

        // 연결된 모든 클라이언트에게 메시지 브로드캐스트
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(savedMessage)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket 연결 종료: " + session.getId());
    }
}
