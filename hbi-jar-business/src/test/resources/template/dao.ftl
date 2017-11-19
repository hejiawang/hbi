package ${packageName};

<#list importList as importItem>
import ${importItem};
</#list>
import java.util.List;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author HeJiawang
 * auto generater dao
 */
@Repository
public interface ${daoName} extends Mapper<${modelName}> {

}