package com.springboot.board.application.service;

import com.springboot.board.api.v1.dto.request.MessageCreateRequest;
import com.springboot.board.api.v1.dto.response.MessageResponse;
import com.springboot.board.domain.entity.Message;
import com.springboot.board.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageResponse createMessage(MessageCreateRequest request) {
        Message message = new Message();
        message.setUsername(request.getUsername());
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);
        return new MessageResponse(
                savedMessage.getId(),
                savedMessage.getUsername(),
                savedMessage.getContent(),
                savedMessage.getTimestamp());
    }

    public List<MessageResponse> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        message.getUsername(),
                        message.getContent(),
                        message.getTimestamp()))
                .collect(Collectors.toList());
    }
}
