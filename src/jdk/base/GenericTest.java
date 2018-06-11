package jdk.base;

import java.util.Collection;
import java.util.List;

/**
 * author: fuliang
 * date: 2017/9/8
 */
public class GenericTest {


    public static class Node<T> {
        public T data;

        public Node(T data) {
            this.data = data;
        }

        public void setData(T data) {
            System.out.println("Node.setData");
            this.data = data;
        }
    }

    public static class MyNode extends Node<Integer> {
        public MyNode(Integer data) {
            super(data);
        }

        public void setData(Integer data) {
            System.out.println("MyNode.setData");
            super.setData(data);
        }
    }

    public static void main(String[] args) {
        Test test = new TestImpl();
        String s = test.get(String.class);

        System.out.println(Collection.class.isAssignableFrom(List.class));
    }


    interface Test {

        <T> T get(Class<T> clz);
    }


    static class TestImpl implements Test {

        @Override
        public <T> T get(Class<T> clz) {
            System.out.println(clz);
            return null;
        }
    }


}
