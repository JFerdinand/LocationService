package com.lingtu.mapper;

import com.lingtu.entity.TabDeviceType;

public interface TabDeviceTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_devicetype
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int deleteByPrimaryKey(Integer typeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_devicetype
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int insert(TabDeviceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_devicetype
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int insertSelective(TabDeviceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_devicetype
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    TabDeviceType selectByPrimaryKey(Integer typeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_devicetype
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int updateByPrimaryKeySelective(TabDeviceType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_devicetype
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int updateByPrimaryKey(TabDeviceType record);
}