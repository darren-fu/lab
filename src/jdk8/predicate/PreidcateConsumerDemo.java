package jdk8.predicate;

import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
public class PreidcateConsumerDemo {
    public static Student updateStudentFee(Student student, Predicate<Student> predicate, Consumer<Student> consumer) {

        //Use the predicate to decide when to update the discount.
        if (predicate.test(student)) {
            //Use the consumer to update the discount value.
            consumer.accept(student);
        }

        return student;
    }

    public static void main(String[] args) {

        Collections.emptyList().add("One");


        Student student1 = new Student("Ashok", "Kumar", 9.5);

        student1 = updateStudentFee(student1,
                //Lambda expression for Predicate interface
                student -> student.grade > 8.5,
                //Lambda expression for ConsumerTest inerface
                student -> student.feeDiscount = 30.0);

        student1.printFee();

        Student student2 = new Student("Rajat", "Verma", 8.0);

        student2 = updateStudentFee(student2,
                student -> student.grade >= 8,
                student -> student.feeDiscount = 20.0);
        student2.printFee();
    }





}
