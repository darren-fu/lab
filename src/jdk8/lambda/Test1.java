package jdk8.lambda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company: 江苏千米网络科技有限公司
 * <p/>
 *
 * @author 付亮(OF2101)
 * @version 1.0.0
 * @date 2016/5/4
 */
public class Test1 {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);

        JButton button1 = new JButton("点我!");
        JButton button2 = new JButton("也点我!");

        frame.getContentPane().add(button1);
        frame.getContentPane().add(button2);
        //这里addActionListener方法的参数是ActionListener，是一个函数式接口
        //使用lambda表达式方式
        button1.addActionListener(e -> { System.out.println("这里是Lambda实现方式"); });
        //使用方法引用方式
        button2.addActionListener(Test1::doSomething2);

    }

    private static void doSomething2(ActionEvent actionEvent) {

    }

    /**
     * 这里是函数式接口ActionListener的实现方法
     * @param e
     */
    public static void doSomething(ActionEvent e) {

        System.out.println("这里是方法引用实现方式");

    }

    /**
     * 这里是函数式接口ActionListener的实现方法
     * @param
     */
    public static void doSomething2() {

        System.out.println("这里是方法引用实现方式");

    }

}
