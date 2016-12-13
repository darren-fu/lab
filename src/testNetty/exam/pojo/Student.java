package testNetty.exam.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darren-fu
 * @version 1.0.0
 * @contact 13914793391
 * @date 2016/12/1
 */
@Data
@NoArgsConstructor
public class Student implements Serializable {

    private static final long serialVersionUID = 1066616112456330252L;

    private int id;

    private String name;

    private Date birthday;

    private Float height;

    private short age;

}
