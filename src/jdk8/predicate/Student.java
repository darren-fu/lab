package jdk8.predicate;

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
public class Student {
    String firstName;

    String lastName;

    Double grade;

    Double feeDiscount = 0.0;

    Double baseFee = 20000.0;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Double getFeeDiscount() {
        return feeDiscount;
    }

    public void setFeeDiscount(Double feeDiscount) {
        this.feeDiscount = feeDiscount;
    }

    public Double getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(Double baseFee) {
        this.baseFee = baseFee;
    }

    public Student(String firstName, String lastName, Double grade) {

        this.firstName = firstName;

        this.lastName = lastName;

        this.grade = grade;
    }

    public void printFee() {

//        Double newFee = baseFee - ((baseFee * feeDiscount) / 100);

        System.out.println("The fee after discount: " + feeDiscount);

    }

}
