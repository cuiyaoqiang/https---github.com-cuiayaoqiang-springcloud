package com.bh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.bh.entity.Order;
import com.bh.service.OrderProcessor;
import com.bh.service.ProductProcessor;

 
/**事件订阅
 * @author bh
 *
 */
@SpringBootApplication
@EnableBinding({Processor.class, OrderProcessor.class, ProductProcessor.class})
public class SubScribeApplication {

	@Autowired
	@Qualifier("outputOrder")
	MessageChannel outputOrder;

	@Autowired
	ProductProcessor productProcessor;

	public static void main(String[] args) {
		SpringApplication.run(SubScribeApplication.class);
	}

	// 监听 binding 为 Processor.INPUT 的消息
	@StreamListener(Processor.INPUT)
	public void input(Message<String> message) {
		System.out.println("一般监听收到：" + message.getPayload());
	}

	// 监听 binding 为 OrderIntf.INPUT_ORDER 的消息
	@StreamListener(OrderProcessor.INPUT_ORDER)
	public void inputOrder(Order order) {
		System.out.println("=====订单监听收到=====");
		System.out.println("订单编号：" + order.getOrderNum());
		System.out.println("订单类型：" + order.getType());
		System.out.println("订单数量：" + order.getNum());
		System.out.println("=====订单处理完成=====");
	}

	@StreamListener(ProductProcessor.INPUT_PRODUCT_ADD)
	public void inputProductAdd(Message<String> message) {
		System.out.println("新增产品监听收到：" + message.getPayload());
	}
}