package com.teleyi.tools.orm;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@AllArgsConstructor
public class ModelGenerator implements Generator {
    private String tableName;
    private List<Element> elements;

    public void generate() {
        String modelFile = String.format(
                "package " + Setting.MODEL_PACKGE + ";\n" +
                "\n" +
                "import lombok.Data;\n" +
                "\n" +
                "@Data\n" +
                        "public class %s extends BaseModel {\n", Util.snakeToCamel(tableName, true));

        elements = Util.filterElements(elements, "id", "create_date", "update_date");

        for (Element element : elements) {
            String comment = "";
            if (StringUtils.isNotBlank(element.getComment())) {
                comment = "   /**\n" +
                        "    * " + element.getComment() + "\n" +
                        "    */\n";
            }
            modelFile += String.format(comment + "   private %s %s;\n", DataTypeMap.get(element.getColumnDataType()).getJavaType(), element.getFieldName());
        }

        modelFile += "}";

        System.out.println(modelFile);
    }
}
