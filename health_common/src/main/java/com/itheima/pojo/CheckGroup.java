package com.itheima.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 检查组实体类，实现了Serializable接口以支持序列化
 * 该类用于封装检查组的相关信息，包括基本信息和包含的检查项列表
 */
public class CheckGroup implements Serializable
{
    private Integer id;          //主键ID，唯一标识一个检查组
    private String code;         //检查组编码，用于系统识别
    private String name;         //检查组名称，用于显示
    private String helpCode;     //助记码，方便快速检索
    private String sex;          //适用性别，如男、女、不限等
    private String remark;       //检查组说明，对检查组的详细描述
    private String attention;    //注意事项，提醒用户需要注意的内容
    private List<CheckItem> checkItems;//一个检查组包含多个检查项(重要)，用于存储该检查组下的所有检查项


    /**
     * 获取检查项列表
     * @return 检查项列表
     */
    public List<CheckItem> getCheckItems() {
        return checkItems;
    }

    /**
     * 设置检查项列表
     * @param checkItems 要设置的检查项列表
     */
    public void setCheckItems(List<CheckItem> checkItems) {
        this.checkItems = checkItems;
    }

    /**
     * 获取注意事项
     * @return 注意事项内容
     */
    public String getAttention() {
        return attention;
    }

    /**
     * 设置注意事项
     * @param attention 要设置的注意事项内容
     */
    public void setAttention(String attention) {
        this.attention = attention;
    }

    /**
     * 获取检查组说明
     * @return 检查组说明内容
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置检查组说明
     * @param remark 要设置的检查组说明内容
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取适用性别
     * @return 适用性别信息
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置适用性别
     * @param sex 要设置的适用性别信息
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取助记码
     * @return 助记码内容
     */
    public String getHelpCode() {
        return helpCode;
    }

    /**
     * 设置助记码
     * @param helpCode 要设置的助记码内容
     */
    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }

    /**
     * 获取检查组名称
     * @return 检查组名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置检查组名称
     * @param name 要设置的检查组名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取检查组编码
     * @return 检查组编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置检查组编码
     * @param code 要设置的检查组编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取主键ID
     * @return 检查组的主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     * @param id 要设置的主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
