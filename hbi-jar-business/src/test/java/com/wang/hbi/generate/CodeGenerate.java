package com.wang.hbi.generate;

import com.wang.hbi.generate.beans.BusinessName;
import com.wang.hbi.generate.beans.GenerConfig;
import com.wang.hbi.generate.beans.Type;
import com.wang.hbi.generate.jdbc.JdbcConnection;
import com.wang.hbi.generate.utils.DataUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码自动生成
 *
 * @Author HeJiawang
 * @Date 2017/11/19 19:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class CodeGenerate {

    @Autowired
    private JdbcConnection conn;

    /**
     * 包信息
     */
    private static Map<String, String> packageMap = new HashMap<>();

    /**
     * 需要导入的类信息
     */
    private static Map<String, List<String>> importMap = new HashMap<>();

    /**
     * 业务包名
     */
    private static final BusinessName businessName = BusinessName.BUSINESS;

    /**
     * 表前缀需要过滤掉的
     */
    private static final String TABLE_PREFIX = "";

    /**
     * 生成文件用来区分的前缀
     */
    private static final String FILE_PREFIX = "";

    /**
     * 初始化包信息、导入的类信息
     */
    @Before
    public void init(){
        DataUtils.initializationData(packageMap, importMap, businessName);
    }

    /**
     * 自动生成代码
     */
    @Test
    public void autoGenerater() throws SQLException, IOException, TemplateException, URISyntaxException{
        generater("table_name", true);
    }

    /**
     * 自动生成代码
     * @param tableName 表名
     * @param isForce 是否强制覆盖已存在文件
     * @throws SQLException SQLException
     * @throws IOException IOException
     * @throws TemplateException TemplateException
     * @throws URISyntaxException URISyntaxException
     */
    public void generater(String tableName, boolean isForce) throws SQLException, IOException, TemplateException, URISyntaxException {
        generater(tableName, isForce, Type.MAPPER, Type.MODEL, Type.SERVICE, Type.SERVICEIMPL, Type.DAO);
    }

    /**
     * 自动生成代码
     * @param tableName 表名
     * @param isForce 否强制覆盖已存在文件
     * @param types 生成代码类型
     * @throws SQLException SQLException
     * @throws IOException IOException
     * @throws TemplateException TemplateException
     * @throws URISyntaxException URISyntaxException
     */
    public void generater(String tableName, boolean isForce, Type ...types) throws SQLException, IOException, TemplateException, URISyntaxException {
        if( null == types || types.length <= 0 ) return;

        for (Type type : types) {
            GenerConfig config = initializationConfig(tableName, type);
            renderTemplate(config, isForce, type);
        }
    }

    /**
     * 初始化自动生成代码的配置信息
     * @param tableName 表明
     * @param type 包类型
     * @return 配置信息
     * @throws SQLException
     */
    public GenerConfig initializationConfig (String tableName, Type type) throws SQLException {
        return new GenerConfig()
                .setPackageName(packageMap.get(type.toString()))
                .setImportList(importMap.get(type.toString()))
                .setTableName(tableName)
                .setModelName(getModelName(tableName))
                .setServiceName(getServiceName(tableName))
                .setServiceImplName(getServiceImplName(tableName))
                .setDaoName(getDaoName(tableName))
                .setMapperName(getMapperName(tableName))
                .setProperties(conn.findMetaDatas(tableName))
                .setBusinessName(businessName.name());
    }

    /**
     * 生成实体名称
     * @param tableName 表名
     * @return 实体名称
     */
    private String getModelName(String tableName) {
        StringBuilder modelName = new StringBuilder(convertTableName(tableName));
        return modelName.toString();
    }

    /**
     * 生成service名称
     * @param tableName 表名
     * @return service名称
     */
    private String getServiceName(String tableName) {
        StringBuilder serviceName = new StringBuilder("I").append(convertTableName(tableName)).append("Service");
        return serviceName.toString();
    }

    /**
     * 生成serviceImpl名称
     * @param tableName 表名
     * @return serviceImpl名称
     */
    private String getServiceImplName(String tableName) {
        StringBuilder serviceName = new StringBuilder(convertTableName(tableName)).append("ServiceImpl");
        return serviceName.toString();
    }

    /**
     * 生成Dao名称
     * @param tableName 表名
     * @return Dao名称
     */
    private String getDaoName(String tableName) {
        StringBuilder daoName = new StringBuilder("I").append(convertTableName(tableName)).append("Dao");
        return daoName.toString();
    }

    /**
     * 生成Mapper名称
     * @param tableName 表名
     * @return Mapper名称
     */
    private String getMapperName(String tableName) {
        StringBuilder mapperName = new StringBuilder(convertTableName(tableName));
        return mapperName.append("Mapper").toString();
    }

    /**
     * 表名转换为文件名前缀
     * @param tableName 表名
     * @param flg false --> 加文件前缀标志
     * @return 文件名前缀
     */
    private StringBuilder convertTableName(String tableName, boolean flg) {
        StringBuilder className = new StringBuilder();

        tableName = tableName.toLowerCase().replaceFirst(TABLE_PREFIX, "");
        if (!flg) tableName = FILE_PREFIX + tableName;

        String[] tableNameArr = tableName.split("_");
        Arrays.asList(tableNameArr).forEach(name -> {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            className.append(name);
        });

        return className;
    }

    /**
     * 表名转换为文件名前缀
     * @param tableName 表名
     * @return 文件名前缀
     */
    private StringBuilder convertTableName(String tableName) {
        return convertTableName(tableName, false);
    }

    public void renderTemplate(GenerConfig generConfig, boolean isForce,  Type type) throws IOException, TemplateException, URISyntaxException {
        Configuration cfg = null;
        String tmplName = "";
        {

            cfg = new Configuration(Configuration.VERSION_2_3_22);
            tmplName = (this.getClass().getResource("/").toURI().getPath() + "template").replaceFirst("/", "");
            cfg.setDirectoryForTemplateLoading(new File(tmplName));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            tmplName = tmplName.substring(0, tmplName.lastIndexOf("/"));
            tmplName = tmplName.substring(0, tmplName.lastIndexOf("/"));
            tmplName = tmplName.substring(0, tmplName.lastIndexOf("/"));
            tmplName = tmplName.substring(0, tmplName.lastIndexOf("/"));
            tmplName += "/hbi-jar-business";

        }
        String outDir = "", resourceDir = "/src/main/java/", fileName = "", ext = ".java";
        switch (type) {
            case MODEL: fileName = generConfig.getModelName(); break;
            case SERVICE: fileName = generConfig.getServiceName(); break;
            case SERVICEIMPL: fileName = generConfig.getServiceImplName(); break;
            case DAO: fileName = generConfig.getDaoName(); break;
            case MAPPER: resourceDir = "/src/main/resources/"; fileName = generConfig.getMapperName(); ext = ".xml"; break;
            default: break;
        }
        outDir = new StringBuilder(tmplName).append(resourceDir).append(generConfig.getPackageName().replaceAll("\\.", "/")).toString();
        Template temp = cfg.getTemplate(new StringBuilder().append(type).append(".ftl").toString());

        File dir = new File(outDir);

        if (dir.exists() && isForce) {
            createFile(generConfig, temp, new File(dir, new StringBuilder(fileName).append(ext).toString()));
        } else if (!dir.exists()) {
            dir.mkdirs();
            createFile(generConfig, temp, new File(dir, new StringBuilder(fileName).append(ext).toString()));
        }

    }

    private void createFile(GenerConfig config, Template temp, File file) throws TemplateException, IOException {
        OutputStream fos = new FileOutputStream(file); //java文件的生成目录
        Writer out = new OutputStreamWriter(fos);
        temp.process(config, out);
        fos.flush();
        fos.close();
    }

}
