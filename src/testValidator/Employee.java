package testValidator;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Locale;
import java.util.Set;


/**
 * author: fuliang
 * date: 2017/9/8
 */
public class Employee {
    @NotNull(message = "{id.notnull}")
    @DecimalMax(value = "10",message = "{id.max}")
    private Integer id;

    @NotNull(message = "{name.notempty}")
    @Size(min = 1,max = 10,message="{name.lengtherror}")
    private String name;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public static void main(String[] args) {
        Employee employee = new Employee();
        System.out.println(Locale.getDefault().getDisplayCountry());
        employee.setId(5);
        employee.setName("Zhang Guan Nan");
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<Employee>> set = validator.validate(employee);
        for (ConstraintViolation<Employee> constraintViolation : set) {
            System.out.println(constraintViolation.getInvalidValue());
            System.out.println(constraintViolation.getPropertyPath());
            System.out.println(constraintViolation.getMessage());
            System.out.println(constraintViolation.getMessageTemplate());
        }
    }
}