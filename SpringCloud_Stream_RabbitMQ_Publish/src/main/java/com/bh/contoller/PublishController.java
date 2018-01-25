package com.bh.contoller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bh.entity.Order;
import com.bh.service.ProductProcessor;

@RestController
@RequestMapping(value="/publish")//通过这里配置使下面的映射都在/users下  
public class PublishController {

	@Autowired
	@Qualifier("output")
	MessageChannel output;

	@Autowired
	@Qualifier("outputOrder")
	MessageChannel outputOrder;

	@Autowired
	ProductProcessor productProcessor;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public void publish() {

		// 字符串类型发送MQ
		System.out.println("字符串信息发送");
		output.send(MessageBuilder.withPayload("大家好").build());

		// 使用 定义的接口的方式来发送
		System.out.println("新增产品发送");
		productProcessor.outputProductAdd().send(MessageBuilder.withPayload("添加一个产品").build());

		// 实体类型发送MQ
		System.out.println("订单实体发送");
		Order appleOrder = new Order();
		appleOrder.setOrderNum("0000001");
		appleOrder.setNum(10);
		appleOrder.setType("APPLE");
		appleOrder.setCreateAt(new Date());
		// 使用 注入 MessageChannel 方式发送
		outputOrder.send(MessageBuilder.withPayload(appleOrder).build());
	}
}
