package com.laker.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.*;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {
    // 1.父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名,一般无需改动
    private static final String PARENT_PACKAGE = "com.laker.admin";
    // 2.每个生成类的作者
    private static final String AUTHOR = "laker";
    // 3.数据库连接地址
    private static final String MYSQL_URL = "localhost:3306/laker";
    // 4.数据库连接用户
    private static final String MYSQL_USER = "root";
    // 5.数据库连接密码
    private static final String MYSQL_PWD = "123456";
    // 6.生成文件的输出目录 System.getProperty("user.dir") + OUT_FILE_PATH + "/src/main/java"
    // 如果是多moudle，则为：/moudle,不是多moudle，则为：空串
    private static final String OUT_FILE_PATH = "";

    /**
     * 运行并根据提示生成代码
     *
     * @param args
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        String projectPath = globalConfig(mpg);

        // 数据源配置
        dataSourceConfig(mpg);

        // 生成代码包配置
        PackageConfig pc = packageConfig(mpg);

        // 策略配置
        String[] tables = strategyConfig(mpg, pc);

        // 自定义配置
        diyConfig(mpg, projectPath, pc, tables);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        templateConfig.setController("templates/controller.java");

        // 这里默认的xml生成的位置与mapper在一个目录，不实用，改为diyConfig中 自定义到 resources/mapper下
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 设置引擎为Freemarker
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 执行
        mpg.execute();

        // 生成对应前端html js代码
        try {
            CodeWebGenerator.web(mpg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void diyConfig(AutoGenerator mpg, String projectPath, PackageConfig pc, String[] tables) {
        // 自定义配置
        // 自定义属性注入abc=${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                String table = tables[0];
                List<String> split = StrUtil.split(table, "_");
                map.put("easyModule", split.get(0));
                map.put("easyMain", split.get(1));
                this.setMap(map);
            }
        };


        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        // 自定义输出文件
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }

    private static String[] strategyConfig(AutoGenerator mpg, PackageConfig pc) {
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // "你自己的父类实体,没有就不用设置!"
//        strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // 【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 公共父类 "你自己的父类控制器,没有就不用设置!"
//        strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        System.out.println("[※3.数据库表名，多个就用英文逗号分割，例如：sys_user,sys_dept]");
        String[] table = scanner("数据库表名").split(",");
        strategy.setInclude(table);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        return table;
    }

    private static PackageConfig packageConfig(AutoGenerator mpg) {
        PackageConfig pc = new PackageConfig();
        // sys 父包模块名
        System.out.println("[※2.建表请遵循规范，规范为表名为模块名_表名，例如 sys_user]");
        String moduleName = scanner("模块名(该表隶属的模块，例如:sys)");
        // 要保证带上module. 例如module.ext
        pc.setModuleName(moduleName.contains("module.") ? moduleName : "module." + moduleName);
        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(PARENT_PACKAGE);

        /*
          上面的代码生成位置及包名称
          com.laker.admin.module.sys
         */
        mpg.setPackageInfo(pc);
        return pc;
    }

    private static void dataSourceConfig(AutoGenerator mpg) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + MYSQL_URL + "?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(MYSQL_USER);
        dsc.setPassword(MYSQL_PWD);
        mpg.setDataSource(dsc);
    }

    private static String globalConfig(AutoGenerator mpg) {
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + OUT_FILE_PATH;
        // 生成文件的输出目录
        gc.setOutputDir(projectPath + "/src/main/java");
        System.out.println("[※1.当前代码生成输出目录为：]");
        System.out.println(projectPath + "/src/main/java");
        // 作者姓名
        gc.setAuthor(AUTHOR);
        // 是否打开输出目录
        gc.setOpen(true);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        // 是否覆盖已有文件
        gc.setFileOverride(false);
        // 开启 BaseResultMap
        gc.setBaseResultMap(true);
        // 开启 baseColumnList
        gc.setBaseColumnList(true);

        mpg.setGlobalConfig(gc);
        return projectPath;
    }


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
