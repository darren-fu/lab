package cons;

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
 * @date 2016/5/30
 */
public class Cons {
    public enum EnumUserType {

        SUBADMIN("0"), STORE("1"), ADMIN("2"), SUBACCOUNT("3"), DISTRIBUTOR("4"), POTENTIAL("5"), SALE("6");

        private String userType;

        private EnumUserType(String userType) {
            this.userType = userType;
        }

        public String getUserType() {
            return userType;
        }

        public int getUserTypeInt() {
            return Integer.parseInt(userType);
        }

    }

    public enum EnumDelFlag {

        DEL(2),NORMAL(0) ;
        private int status;

        private EnumDelFlag(int status) {
            this.status = status;
        }



    }
}
