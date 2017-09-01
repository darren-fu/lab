package jdk.reference;

import lombok.Data;

import java.util.function.Consumer;

/**
 * author: fuliang
 * date: 2017/9/1
 */
@Data
public class ConsumerHolder {


    private Consumer consumer;


    public ConsumerHolder() {
        consumer = (v) -> {
            System.out.println("consumer is here:" + v);
        };

    }
}
