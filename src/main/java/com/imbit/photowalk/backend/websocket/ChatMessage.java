package com.imbit.photowalk.backend.websocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class ChatMessage {
	private String sender;
	private String receiver;
	private String message;
}
