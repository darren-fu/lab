package testObject;

import lombok.Getter;
import lombok.Setter;

/**
 * author: fuliang
 * date: 2017/6/1
 */
public class TestObject {


    public static void main(String[] args) {
        User user1 = new User();
        User user2 = new User();

        System.out.println(user1.hashCode());
        System.out.println(user2.hashCode());






    }


    private static class User{
        @Getter
        @Setter
        private String name;
    }
}
