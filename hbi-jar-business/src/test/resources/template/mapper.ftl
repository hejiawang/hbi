<?xml version="1.0" encoding="UTF-8" ?>     
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.gmp.bus.${busName?lower_case}.dao.${daoName}">

	<resultMap id="resultMap" type="com.gmp.bus.${busName?lower_case}.entity.${modelName}">
		<#list properties as p>
		<#if p?? && p.isId>
		<id property="${p.propertyName}" column="${p.columnName}" jdbcType="${p.type}" />
		<#else>
		<result property="${p.propertyName}" column="${p.columnName}" jdbcType="<#if p.type == 'DATETIME'>DATE<#else>${p.type}</#if>" />				
		</#if>
		</#list>
	</resultMap>
	
	<!-- *****************************************************通用定义**************************************************** -->
	<sql id="allColumns">
		<#list properties as p><#if p?? && p_index != 0>, </#if>${p.columnName}</#list>
	</sql>
	
</mapper>