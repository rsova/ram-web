package app.service.odata.resolver

import groovy.util.slurpersupport.GPathResult

import java.util.HashMap.Entry

import org.hamcrest.core.IsInstanceOf;

class MetadataResolver {

	static final String RAM_ODATA_MODELS = 'RAMOdata.Models'
	static final String PROPERTY_TYPE = 'PropertyType'
	
	final GPathResult root = null
	final GPathResult models = null
	final GPathResult container = null
	
	public MetadataResolver() {
		String schema = new File('./src/main/resources/metadata/edm-schema.xml').text
		//http://ramodata.azurewebsites.net/odata/$metadata
		root = new XmlSlurper().parseText(schema)
		models = root.DataServices.Schema.find{ it.@Namespace == RAM_ODATA_MODELS}
		container = root.DataServices.Schema.find{ it.@Namespace == 'Default'}.EntityContainer
	}
	
    
	public String getTypeForProperty(String entity, String property){
		return models.EntityType.find{ it.@Name == entity}.Property.find{it.@Name == property}.@Type.text()
	}
	
	public String getTypeInGraph(String entity, String property){
		def type = null
		while(!type){
			type = getTypeForProperty( entity, property)
			if(!type){
				def baseType = models.EntityType.find{ it.@Name == entity}.@BaseType.text()
				entity = getNameFromEntityType(baseType)
			}
		}
		
		return type	
	}

	public String getNameFromEntityType(String entityType){
		return entityType.replaceFirst(RAM_ODATA_MODELS, '').replaceFirst('.','')
	}
	
	/*
	 * Match the websearch parameter to the model
	 */
	public String getEntityNameFromParams(Map params){
		
		def entity = (params.get(PROPERTY_TYPE))
		if (entity instanceof Collection){
			entity = entity.getAt(0)
		}
		def modelEntity = RAM_ODATA_MODELS<<"."<<entity
		def node  = container.EntitySet.find{ it.@EntityType == modelEntity}		
		return node.@Name.text()
	}
	
	public Object oDataTypeWrapValue(Object entry, String entityType) {
		//def value = entry.value
		def type = getTypeInGraph(entityType, entry.key)
		//def oDataTypedValue = (type!='Edm.String')?type:'' +"'$value'"
		type = (type!='Edm.String')?type:''
		if(entry.value instanceof Collection){
			def vals = []
			entry.value.each {
				vals.add( type +"'$it'")
			}
			entry.value = vals
			//entry.value.each { it = (type!='Edm.String')?type:'' +"'$it'"}
		}
		return entry
	}
	public List getEnumType(String type){
		return models.EnumType.find{ it.@Name?.text()?.equalsIgnoreCase(type)}.Member.collect{it.@Name?.text() }
	}

}
