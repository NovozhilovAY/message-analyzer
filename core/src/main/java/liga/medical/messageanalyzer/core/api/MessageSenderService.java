package liga.medical.messageanalyzer.core.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import liga.medical.dto.dto.RabbitMessageDto;

public interface MessageSenderService {
    void sendMessage(RabbitMessageDto messageDto, String queue) throws JsonProcessingException;
}
