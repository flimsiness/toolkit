package com.teleyi.tools.orm;

import java.util.List;

/**
 * 已知问题, 近期不打算修改:
 * 1. TINYINT(1)将映射成java的Boolean类型, 如需映射成Integer,请手工修改DataTypeMap
 * 2. 不识别jicai, pay目录结构, 默认主站包结构
 *
 * 打算修改:
 * 1. 替换String.format方式，改成velocity之类的模板方式。
 * 2. 改成插件方式，在菜单中输入表名，自动生成文件。
 */
public class Main {
    public static void main(String[] args) throws Exception {

        String tableName = Setting.SCHEMA;//在这里输入表名，然后执行吧

        if (args.length > 0) {
            tableName = args[0];
        }

        List<Element> elements = Util.fetchElements(tableName);
        Util.batchGenerate(new ModelGenerator(tableName, elements),  new MapperGenerator(tableName, elements));
    }
}