package ${packageName};

<#list importList as importItem>
import ${importItem};
</#list>
import com.gmp.common.service.IMyBatisService;

/**
 * @author ${modelName}
 * auto generater service
 */
public interface ${serviceName} extends IMyBatisService<${modelName}> {

}