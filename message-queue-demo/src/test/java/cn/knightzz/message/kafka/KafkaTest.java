package cn.knightzz.message.kafka;

import cn.knightzz.message.MessageQueueDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 王天赐
 * @title: KafkaTest
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-09 10:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MessageQueueDemoApplication.class)
public class KafkaTest {
    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @Test
    public void testProducer() {
        producer.sendMessage("test", "你好");
        producer.sendMessage("test", "在吗");
        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
