package liga.medical.messageanalyzer.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.dto.annotations.DbLog;
import liga.medical.dto.dto.RabbitMessageDto;
import liga.medical.messageanalyzer.core.api.MessageSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSenderServiceImpl implements MessageSenderService {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public MessageSenderServiceImpl(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    @DbLog
    public void sendMessage(RabbitMessageDto messageDto, String queue) throws JsonProcessingException {
        String messageStr = objectMapper.writeValueAsString(messageDto);
        amqpTemplate.convertAndSend(queue, messageStr);
        log.info("Сообщение " + messageStr + " добавлено в очередь " + queue);
    }
}
