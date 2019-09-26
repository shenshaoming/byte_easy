package com.mbyte.easy.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.mbyte.easy.generator.model.MybatisFieldModel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * <p>
 *  自动生成工具类
 * </p>
 *
 * @author 申劭明
 * @since 2019-03-11
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws IOException {
        /**
         * 输入表名和模块名
         */
        String moduleName = scanner("模块名");
        String tableName = scanner("表名");
        String ignoreFlag = scanner("是否忽略指定前缀(1为是其它为否)");
        String ignorePrefix = null;
        if("1".equals(ignoreFlag.trim())){
            ignorePrefix = scanner("前缀");
        }
        String isRest =  scanner("是否生成Rest接口（1为是其他为否）");
        createCode(moduleName, tableName, ignoreFlag, ignorePrefix, isRest);
    }
    /**
     * 功能描述
     * 根据传入的参数自动生成所有需要的文件
     * controller层,dao层,entity层,service层,serviceImpl
     * mapper.xml和增删改查的页面

     * @param moduleName 模型名称
     * @param tableName 数据库中表的名称
     * @param ignoreFlag 是否忽略前缀
     * @param ignorePrefix 前缀
     * @param isRest 是否生成Rest接口
     * @return : void
     * @author : 申劭明
     * @date : 2019/7/19 22:02
    */
    public static void createCode(String moduleName, String tableName,
                                  String ignoreFlag,  String ignorePrefix,
                                  String isRest) throws IOException {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("申劭明");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream("application.properties");
        // 使用properties对象加载输入流
        properties.load(in);

        //获取key对应的value值
        String url = properties.getProperty("spring.datasource.druid.url");
        String driverName = properties.getProperty("spring.datasource.druid.driver-class-name");
        String userName = properties.getProperty("spring.datasource.druid.username");
        String password = properties.getProperty("spring.datasource.druid.password");
        String uploadSuffixPath = properties.getProperty("file.upload.suffix.path");


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setSchemaName("public");
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent("com.mbyte.easy");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<>();

                map.put("restController", "com.mbyte.easy.rest");
                map.put("uploadSuffixPath", uploadSuffixPath);
                map.put("superController", "com.mbyte.easy.common.controller.BaseController");
                //组装属性,适配xml
                List<TableInfo> listTableInfo = mpg.getConfig().getTableInfoList();
                List<MybatisFieldModel> mybatisFields = new ArrayList<>();
                for(TableInfo tableInfo : listTableInfo){
                    if(!tableInfo.getName().equals(tableName)){
                        continue;
                    }
                    List<TableField> fields = new ArrayList<>();
                    fields.addAll(tableInfo.getCommonFields());
                    fields.addAll(tableInfo.getFields());
                    for(TableField field : fields){
                        String type = field.getType();
                        if("TEXT".equalsIgnoreCase(type)){
                            type = "VARCHAR";
                        }
                        if(type.contains("(")){
                            type = type.substring(0, type.indexOf("(")).toUpperCase();
                        }

                        if("INT".equalsIgnoreCase(type)){
                            type = "INTEGER";
                        }

                        if("DATETIME".equalsIgnoreCase(type)){
                            type = "TIMESTAMP";
                        }

                        MybatisFieldModel mybatisModel =
                                new MybatisFieldModel(field.getName(), type.toUpperCase(), field.getPropertyName(), field.isKeyFlag());
                        mybatisFields.add(mybatisModel);
                    }

                }
                map.put("mybatisFields", mybatisFields);
                this.setMap(map);
            }
        };

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.mbyte.easy.common.entity.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(false);
        strategy.setSuperControllerClass("com.mbyte.easy.common.controller.BaseController");
        strategy.setInclude(tableName);
        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);

        List<FileOutConfig> focList = new ArrayList<>();

        if(StringUtils.isNotEmpty(ignorePrefix)){
            strategy.setTablePrefix(ignorePrefix);
        }
        //添加接口生成配置
        if("1".equals(isRest)){
            focList.add(new FileOutConfig("/generator/java/rest/controller.java.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    String tableName = tableInfo.getEntityName().substring(0,1).toLowerCase()+ tableInfo.getEntityName().substring(1);
                    String expand = projectPath + File.separator + "src/main/java/com/mbyte/easy/rest" + File.separator + tableName;
                    String entityFile = String.format((expand + File.separator + "Rest%s" + ".java"), tableInfo.getControllerName());
                    return entityFile;
                }
            });
        }


        focList.add(new FileOutConfig("/generator/xml/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper.xml文件存放的路径
                return projectPath + "/src/main/resources/mybatis/mapper/" + tableInfo.getEntityName().toLowerCase()
                        + File.separator + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        focList.add(new FileOutConfig("/generator/java/controller/controller.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                System.out.println("tableInfo:"+tableInfo);
                String expand = projectPath + File.separator + "src/main/java/com/mbyte/easy" + File.separator  + pc.getModuleName() + "/controller";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getControllerName());
                return entityFile;
            }
        });

        String templatesPath = projectPath + File.separator + "src/main/resources/templates" + File.separator + pc.getModuleName();
        focList.add(new FileOutConfig("/generator/template/list.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String tableName = tableInfo.getEntityName().substring(0,1).toLowerCase()+ tableInfo.getEntityName().substring(1);
                String entityFile = String.format((templatesPath+File.separator+tableName + File.separator + "%s" + ".html"), "list");
                return entityFile;
            }
        });
        focList.add(new FileOutConfig("/generator/template/add.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String tableName = tableInfo.getEntityName().substring(0,1).toLowerCase()+ tableInfo.getEntityName().substring(1);
                String entityFile = String.format(( templatesPath+File.separator+tableName+ File.separator + "%s" + ".html"), "add");
                return entityFile;
            }
        });
        focList.add(new FileOutConfig("/generator/template/edit.html.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                String tableName = tableInfo.getEntityName().substring(0,1).toLowerCase()+ tableInfo.getEntityName().substring(1);
                String entityFile = String.format((templatesPath +File.separator+tableName+ File.separator + "%s" + ".html"), "edit");
                return entityFile;
            }
        });


        injectionConfig.setFileOutConfigList(focList);
        mpg.setCfg(injectionConfig);
        mpg.setTemplate(new TemplateConfig().setXml(null));


        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


}
