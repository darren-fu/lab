package com.company.cache.mybatis.sql;

import java.util.List;

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
 * @date 2016/6/15
 */
public interface Sql {



    /**
     * 主体SQL String
     * @return
     */
    public String sqlMainStr();

    /**
     * 完整SQL
     * @return
     */
    public String sqlIntactStr();

    /**
     * 主体关联的table
     * @return
     */
    public List<Table> mainRelTables();

    /**
     * 完整关联的table
     * @return
     */
    public List<Table> intactRelTables();

}
