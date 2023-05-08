package cn.knightzz.message.jdk;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @author 王天赐
 * @title: Consumer
 * @projectName technical-tutorials
 * @description:
 * @website <a href="https://knightzz.cn/">https://knightzz.cn/</a>
 * @github <a href="https://github.com/knightzz1998">https://github.com/knightzz1998</a>
 * @create: 2023-05-08 20:40
 */
public class Consumer extends Thread {

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(new Random().nextInt(1000));
                Integer take = queue.take();
                System.out.println(Thread.currentThread().getName() + "消费:" + take + "  当前队列容量 : " + queue.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
