package ${packageName};

<#list importList as importItem>
import ${importItem};
</#list>

import com.gmp.common.service.abs.MybatisServiceImpl;

import javax.annotation.Resource;

import ${(packageName?replace('service', 'dao'))?replace('.impl', '')}.${daoName};

/**
 * @author ${modelName}
 * auto generater serviceImpl
 */
@Service("${(serviceImplName?replace('Impl', ''))?uncap_first}")
public class ${serviceImplName} extends MybatisServiceImpl<${modelName}> implements ${serviceName}{

	@Resource
	private ${daoName} dao;

}