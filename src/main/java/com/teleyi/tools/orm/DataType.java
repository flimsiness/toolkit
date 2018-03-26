package com.teleyi.tools.orm;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class DataType {
    private String dataTypeInDB;
    private String jdbcType;
    private String javaType;
}