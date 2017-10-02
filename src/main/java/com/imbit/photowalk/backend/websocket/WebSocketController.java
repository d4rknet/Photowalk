package com.imbit.photowalk.backend.websocket;


import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

	@MessageMapping("/sendMessage")
	@SendTo("/topic/chat")
	public Message<ChatMessage> chat(ChatMessage message) throws Exception {
		Message<ChatMessage> msg = MessageBuilder.withPayload(message)
				.setReplyChannelName("/topic/chat/" + message.getReceiver())
				.build();
		message.setSender(null);
		return msg;
	}

	@MessageMapping("/updatePos")
	@SendTo
	public Message<Position> updatePosition(Position position) throws Exception {
		Message<Position> posMsg = MessageBuilder.withPayload(position)
				.setReplyChannelName("/topic/pos/" + position.getPhotowalkName())
				.build();
		position.setPhotowalkName(null);
		return posMsg;
	}


}
