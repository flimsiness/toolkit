package com.teleyi.tools.orm;

import java.util.HashMap;
import java.util.Map;

public class DataTypeMap {

    static Map<String, DataType> _MAP = new HashMap<String, DataType>();

    static {
        _MAP.put("bigint", new DataType("bigint", "BIGINT", "Long"));
        _MAP.put("varchar", new DataType("varchar", "VARCHAR", "String"));
        _MAP.put("char", new DataType("char", "VARCHAR", "String"));
        _MAP.put("decimal", new DataType("decimal", "DECIMAL", "BigDecimal"));
        _MAP.put("datetime", new DataType("datetime", "TIMESTAMP", "Date"));
        _MAP.put("timestamp", new DataType("timestamp", "TIMESTAMP", "Date"));
        _MAP.put("int", new DataType("int", "INTEGER", "Integer"));
        _MAP.put("tinyint", new DataType("tinyint", "TINYINT", "Integer"));
        _MAP.put("smallint", new DataType("smallint", "SMALLINT", "Integer"));
        _MAP.put("longtext", new DataType("longtext", "VARCHAR", "String"));
        _MAP.put("text", new DataType("text", "VARCHAR", "String"));
        _MAP.put("bit", new DataType("bit", "BIT", "Boolean"));
        _MAP.put("date", new DataType("date", "DATE", "Date"));
    }

    public static DataType get(String dbDataType) {
        return _MAP.get(dbDataType);
    }
}
