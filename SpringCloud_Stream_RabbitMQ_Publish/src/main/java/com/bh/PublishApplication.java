package com.bh;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;

import com.bh.service.OrderProcessor;
import com.bh.service.ProductProcessor;

/**事件发布
 * @author bh
 */
@SpringBootApplication
@EnableBinding({Processor.class, OrderProcessor.class, ProductProcessor.class})
public class PublishApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublishApplication.class);
	}

	// 定时轮询发送消息到 binding 为 Processor.OUTPUT
	@Bean
	@InboundChannelAdapter(value = Processor.OUTPUT, poller = @Poller(fixedDelay = "3000", maxMessagesPerPoll = "1"))
	public MessageSource<String> timerMessageSource() {
		return () -> MessageBuilder.withPayload("短消息-" + new Date()).build();
	}
}