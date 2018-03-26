package com.teleyi.tools.orm;

import lombok.AllArgsConstructor;

@lombok.Data
@AllArgsConstructor
public class Element {
    private String columnName;
    private String columnDataType;
    private String comment;

    public String getFieldName() {
        return Util.snakeToCamel(columnName);
    }
}
