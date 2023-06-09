package com.wujiejie.qushuge.utils;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.sql.Types;
import java.util.Collections;

/**
 * mp代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/qushuge?serverTimezone=GMT%2b8", "root", "123456").globalConfig(builder -> {
            builder.author("wujiejie") // 设置作者
//          .enableSwagger() // 开启 swagger 模式
                    .fileOverride() // 覆盖已生成文件
                    .outputDir("D:\\大学学习\\javacode\\趣书阁\\qushuge\\src\\main\\java\\"); // 指定输出目录
        }).packageConfig(builder -> {
            builder.parent("com.wujiejie.qushuge") // 设置父包名
                    .moduleName(null) // 设置父包模块名
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\大学学习\\javacode\\趣书阁\\qushuge\\src\\main\\resources\\mapper\\"));
        }).strategyConfig(builder -> {
            builder.entityBuilder().enableLombok();
            builder.mapperBuilder().enableMapperAnnotation().build();
            builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                    .enableRestStyle();  // 开启生成@RestController 控制器
            builder.addInclude("sys_user") // 设置需要生成的表名
                    .addTablePrefix("sys_"); // 设置过滤表前缀
        }).execute();

    }
}
