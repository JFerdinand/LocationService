package com.lingtu.entity.mapper;

import com.lingtu.entity.TabUserInfor;

public interface TabUserInforMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_userinfor
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int deleteByPrimaryKey(Integer userinfoid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_userinfor
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int insert(TabUserInfor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_userinfor
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int insertSelective(TabUserInfor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_userinfor
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    TabUserInfor selectByPrimaryKey(Integer userinfoid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_userinfor
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int updateByPrimaryKeySelective(TabUserInfor record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tab_userinfor
     *
     * @mbggenerated Wed Dec 21 16:45:23 CST 2016
     */
    int updateByPrimaryKey(TabUserInfor record);
}