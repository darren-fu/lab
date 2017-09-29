package jdk.base;

/**
 * author: fuliang
 * date: 2017/9/8
 */
public class GenericTest {


    public static class Node<T> {
        public T data;
        public Node(T data) { this.data = data; }
        public void setData(T data) {
            System.out.println("Node.setData");
            this.data = data;
        }
    }
    public static class MyNode extends Node<Integer> {
        public MyNode(Integer data) { super(data); }
        public void setData(Integer data) {
            System.out.println("MyNode.setData");
            super.setData(data);
        }
    }

    public static void main(String[] args) {
        MyNode mn = new MyNode(5);
        Node n = mn; // A raw type - compiler throws an unchecked warning
//        mn.setData("Hello"); // Causes a ClassCastException to be thrown.
        n.setData("Hello"); // Causes a ClassCastException to be thrown.
// Integer x = mn.data;
    }

}
