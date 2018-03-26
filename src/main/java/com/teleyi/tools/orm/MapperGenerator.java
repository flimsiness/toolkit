package com.teleyi.tools.orm;


import lombok.Data;

import java.util.List;

@Data
public class MapperGenerator implements Generator {
    private String tableName;
    private List<Element> elements;
    private String className;

    MapperGenerator(String tableName, List<Element> elements) {
        this.tableName = tableName;
        this.elements = elements;
        this.className = Util.snakeToCamel(tableName, true);
    }

    public void generate() {
        String mapper = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n";

        mapper += String.format("<mapper namespace=\"" + Setting.MAPPER_PACKGE + ".%sMapper\">\n", className);

        mapper += String.format("<resultMap id=\"BaseResultMap\" type=\"" + Setting.MODEL_PACKGE + ".%s\">\n", className);
        mapper += "<id column=\"id\" property=\"id\" jdbcType=\"BIGINT\"/>\n";
        List<Element> resultMapElements = Util.filterElements(elements, "id");
        for (Element element : resultMapElements) {
            mapper += String.format("<result column=\"%s\" property=\"%s\" jdbcType=\"%S\"/>\n",
                    element.getColumnName(),
                    element.getFieldName(),
                    DataTypeMap.get(element.getColumnDataType()).getJdbcType()
            );
        }
        mapper += "</resultMap>\n\n";

        mapper += "<sql id=\"Base_Column_List\">\n";
        for (Element element : elements) {
            mapper += String.format("`%s`.`%s`,\n", tableName, element.getColumnName());
        }
        mapper = mapper.substring(0, mapper.length() - 2);
        mapper += "\n</sql>\n\n";

        mapper += String.format("<insert id=\"create\" parameterType=\"" + Setting.MODEL_PACKGE + ".%s\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n", className);
        mapper += String.format("insert into `%s`\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n", tableName);
        List<Element> insertElements = Util.filterElements(elements, "id", "update_date");
        for (Element element : insertElements) {
            mapper += String.format("`%s`,\n", element.getColumnName());
        }
        mapper += "</trim>\n<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\nnow(),\n";
        List<Element> insertValueElements = Util.filterElements(elements, "id", "create_date", "update_date");
        for (Element element : insertValueElements) {
            mapper += String.format("#{%s, jdbcType=%S},\n", element.getFieldName(), DataTypeMap.get(element.getColumnDataType()).getJdbcType());
        }
        mapper += "</trim>\n</insert>\n\n";

        mapper += String.format("<select id=\"getById\" resultMap=\"BaseResultMap\" parameterType=\"long\">\n" +
                        "select\n" +
                        "<include refid=\"Base_Column_List\" />\n" +
                        "from `%s`\n" +
                        "where id = #{id, jdbcType=BIGINT}\n" +
                        "</select>\n\n",
                tableName);

        mapper += String.format("<update id=\"update\" parameterType=\"" + Setting.MODEL_PACKGE + ".%s\">\n", className);
        mapper += String.format("update `%s`\n<set>\n", tableName);
        for (Element element : insertValueElements) {
            mapper += String.format("<if test=\"%s != null\">\n", element.getFieldName());
            mapper += String.format("`%s` = ", element.getColumnName());
            mapper += String.format("#{%s, jdbcType=%S},", element.getFieldName(), DataTypeMap.get(element.getColumnDataType()).getJdbcType());
            mapper += "\n</if>\n";

        }
        mapper += "</set>\n</update>\n\n";
        mapper += "</mapper>";

        System.out.println(mapper);
    }
}
