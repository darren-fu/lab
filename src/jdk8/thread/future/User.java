package jdk8.thread.future;

import lombok.Data;

import java.util.concurrent.CompletableFuture;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016/11/1
 */
@Data
public class User {
    String name;
    int age = 0;

    @Override
    public String toString() {
        return "name:" + name + ",age:" + age;
    }


    public int addAge() {
        age += 10;
        return age;
    }

    public int reduceAge() {
        System.out.println("age:" + this.age);
        this.age = 2;
        return this.age;
    }

    public void doAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("run...");
            addAge();
            return "DOWN";
        }).whenComplete((res, ex) -> {
            this.reduceAge();
            System.out.println("complete:" + res);
        });

        future.whenComplete((res, ex) -> {
            System.out.println("res2:" + res);
        });
        System.out.println(this);
        future.join();
    }

    public static void main(String[] args) {
        User user = new User();
        user.doAsync();

    }
}
