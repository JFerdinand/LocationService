package com.lingtu.entity.mapper;

import com.lingtu.entity.TabDevelopment;

public interface TabDevelopmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_development
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int deleteByPrimaryKey(Integer developid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_development
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int insert(TabDevelopment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_development
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int insertSelective(TabDevelopment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_development
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    TabDevelopment selectByPrimaryKey(Integer developid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_development
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int updateByPrimaryKeySelective(TabDevelopment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_development
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int updateByPrimaryKey(TabDevelopment record);
}