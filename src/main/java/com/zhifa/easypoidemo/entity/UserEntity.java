package com.zhifa.easypoidemo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @version 1.0
 * @date
 * @effect :
 * 用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Excel(name = "用户ID", width = 15)
    private Integer userId;

    @Excel(name = "用户名", width = 15)
    private String userName;

    @Excel(name = "用户性别", width = 15)
    private String userSex;

    @Excel(name = "用户年龄", width = 15)
    private Integer userAge;
}