package ${packageName};

<#list importList as importItem>
import ${importItem};
</#list>
import java.math.BigDecimal;

import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

/**
 * @author HeJiawang
 * auto generater entity
 */
 @Table(name = "${tableName}")
public class ${modelName} {

	<#list properties as attr>
	<#if attr?? && attr.isId>
	@Id
	@Column(name = "${attr.columnName}")
	<#else>
	@Column(name = "${attr.columnName}")
	</#if>
	private <#if attr.columnType == 'Timestamp'>Date<#else>${attr.columnType}</#if> ${attr.propertyName};
	
	</#list>	
	<#list properties as attr>
	
	public ${modelName} set${attr.propertyName?cap_first}(<#if attr.columnType == 'Timestamp'>Date<#else>${attr.columnType}</#if> ${attr.propertyName}){
        this.${attr.propertyName} = ${attr.propertyName};
        return this;
    }
    
    public <#if attr.columnType == 'Timestamp'>Date<#else>${attr.columnType}</#if> get${attr.propertyName?cap_first}(){
        return this.${attr.propertyName};
    }
	</#list>
	
}