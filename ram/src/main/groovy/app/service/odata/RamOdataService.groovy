package app.service.odata

import org.apache.olingo.client.api.uri.URIFilter
import org.apache.olingo.client.api.uri.v4.FilterFactory
import org.apache.olingo.client.api.uri.v4.URIBuilder
import org.apache.olingo.client.core.v4.ODataClientImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

import app.service.odata.resolver.MetadataResolver

@Service
public class RamOdataService {
	int top
	final static String PROPERTY_TYPE = 'PropertyType'
	static final String SERVICE_ROOT = 'http://ramodata.azurewebsites.net/odata/'
	static final String GET_LIST = 'getlist'
	
	@Autowired CacheManager cacheManager
	@Autowired MetadataResolver resolver
	@Autowired ODataClientImpl v4Client

	public RamOdataService(){
//		resolver = new MetadataResolver()
//		v4Client = new ODataClientImpl()
		top = 10
	}
	
//	@Cacheable("lists")
	public Map getLists(Map params){
		String type = params.get(GET_LIST)		
		def data = cacheManager?.getCache('lists')?.get(type)?.get()
		if(!data){
			String xml = new URL(SERVICE_ROOT + '$metadata').getText()
			data = resolver.getEnumType(type)
			data = ['type':type.capitalize(), 'data':data]
			cacheManager.getCache('lists').put(type, data)
		}
		return data
	}

	public String execute(Map params){
		return generateUri(params).toURL().getText()
	}

	public URI generateUri (Map map){
		return generateBuilder(getEntityCollectionName(map), generateFilter(map)).build()

	}
	public URIBuilder generateBuilder(String entity, URIFilter filter){
		URIBuilder builder =  v4Client.newURIBuilder(SERVICE_ROOT).appendEntitySetSegment(entity).top(top)
		if(filter){
			builder.filter(filter)
		}
		return builder
	}

	public URIFilter generateFilter(Map params){
		def filters = []
		URIFilter filter = null

		//TODO: figure our how multiple entitytypes work
		def entityType = getEntityTypeName(params)
		if(entityType instanceof Collection){
			entityType = entityType.getAt(0)
		}
		removeEntity(params)

		for(param in params){
			param =  resolver.oDataTypeWrapValue(param, entityType)
			filter = generateEqFilter(param)
			filters.add(filter)
		}

		return (!filters?.empty)?generateAndFilter(filters):null
	}

	private URIFilter generateAndFilter( List filters) {
		URIFilter filter = filters?.first()
		for(right in filters?.tail()){
			filter = v4Client.getFilterFactory().and(filter, right)
		}
		return filter
	}

	private URIFilter generateEqFilter(Object param) {
		URIFilter filter = null
		FilterFactory ff =  v4Client.getFilterFactory()

		boolean isCollection = (param.value instanceof Collection)
		def value = (isCollection)?param.value?.first():param.value

		filter = ff.eq(ff.getArgFactory().property(param.key), ff.getArgFactory().property(value))

		if(param.value instanceof Collection){
			filter = generateOrFilter(param, filter)
		}
		return filter
	}

	protected URIFilter generateOrFilter(Object param, URIFilter filter) {
		FilterFactory ff =  v4Client.getFilterFactory()
		for(val in param.value.tail()){
			URIFilter right= ff.eq(ff.getArgFactory().property(param.key), ff.getArgFactory().property(val))
			filter = ff.or(filter, right)
		}
		return filter
	}

	public void removeEntity(Map params){
		def entity = params.find{it.key == PROPERTY_TYPE}
		if(entity){
			params.remove(entity.key)
		}
	}

	public Object getEntityTypeName(Map params){
		return params.get(PROPERTY_TYPE)
	}

	public String getEntityCollectionName(Map params){
		return resolver.getEntityNameFromParams(params)
	}

}

