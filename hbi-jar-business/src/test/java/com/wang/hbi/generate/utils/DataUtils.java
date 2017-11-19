package com.wang.hbi.generate.utils;

import com.wang.hbi.generate.beans.BusinessName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 初始化自动代码数据
 * @Author HeJiawang
 * @Date 2017/11/19 20:10
 */
public class DataUtils {

    /**
     * 基础包信息
     */
    private static final String BASE_PACKAGE = "com.wang.hbi.";

    private static final String ENTITY_PACKAGE = ".entity";
    private static final String SERVICE_PACKAGE = ".service";
    private static final String SERVICEIMPL_PACKAGE = ".service.impl";
    private static final String DAO_PACKAGE = ".dao";
    private static final String MAPPER_PACKAGE = ".mapper";

    public static void initializationData (Map<String, String> packageMap, Map<String, List<String>> importMap, BusinessName businessName) {
        initializationPackageData(packageMap, businessName);
        initializationImportData (importMap, businessName);
    }

    /**
     * 初始化包信息
     * @param packageMap packageMap
     * @param businessName businessName
     */
    private static void initializationPackageData(Map<String, String> packageMap, BusinessName businessName) {
        packageMap.put("model", BASE_PACKAGE + businessName + ENTITY_PACKAGE);
        packageMap.put("service", BASE_PACKAGE + businessName + SERVICE_PACKAGE);
        packageMap.put("serviceImpl", BASE_PACKAGE + businessName + SERVICEIMPL_PACKAGE);
        packageMap.put("dao", BASE_PACKAGE + businessName + DAO_PACKAGE);
        packageMap.put("mapper", BASE_PACKAGE + businessName + MAPPER_PACKAGE);
    }

    /**
     * 类文件需要引入的类
     * @param importMap importMap
     * @param businessName businessName
     */
    private static void initializationImportData (Map<String, List<String>> importMap,  BusinessName businessName) {
        //model
        List<String> modelList = new ArrayList<>();
        modelList.add("java.io.Serializable");
        modelList.add("java.util.Date");
        importMap.put("model", modelList);

        //service
        List<String> serviceList = new ArrayList<>();
        serviceList.add(BASE_PACKAGE + businessName + ".entity.*");
        importMap.put("service", serviceList);

        //service impl
        List<String> serviceImplList = new ArrayList<>();
        serviceImplList.add(BASE_PACKAGE + businessName + ".entity.*");
        serviceImplList.add("org.springframework.stereotype.Service");
        serviceImplList.add(BASE_PACKAGE + businessName + ".service.*");
        importMap.put("serviceImpl", serviceImplList);

        //dao
        List<String> daoList = new ArrayList<>();
        daoList.add(BASE_PACKAGE + businessName + ".entity.*");
        daoList.add("org.springframework.stereotype.Repository");
        importMap.put("dao", daoList);

        //mapper
        List<String> mapperList = new ArrayList<>();
        mapperList.add(BASE_PACKAGE + businessName + ".dao");
        importMap.put("mapper", mapperList);
    }
}
