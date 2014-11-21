package app.service.odata

import org.apache.olingo.client.api.Configuration
import org.apache.olingo.client.api.uri.FilterArg
import org.apache.olingo.client.api.uri.URIFilter
import org.apache.olingo.client.api.uri.v4.FilterFactory
import org.apache.olingo.client.api.uri.v4.URIBuilder
import org.apache.olingo.client.core.ConfigurationImpl
import org.apache.olingo.client.core.uri.AndFilter
import org.apache.olingo.client.core.uri.EqFilter
import org.apache.olingo.client.core.uri.FilterConst
import org.apache.olingo.client.core.uri.FilterProperty
import org.apache.olingo.client.core.uri.v4.FilterFactoryImpl
import org.apache.olingo.client.core.uri.v4.URIBuilderImpl
import org.apache.olingo.client.core.v4.ODataClientImpl
import org.apache.olingo.commons.api.edm.constants.ODataServiceVersion
import org.junit.After
import org.junit.Before

class RamoServiceTest extends GroovyTestCase {
	RamoService oservice
	@Before
	public void setUp(){
		oservice = new RamoService()
	}
	
	@After
	public void tearDown(){
		oservice = null
	}
		
	void testUriBuilder() {
		final Configuration configuration = new ConfigurationImpl()
		final String serviceRoot = 'http://ramodata.azurewebsites.net/odata/'
		 
		URIBuilderImpl builder = new URIBuilderImpl(ODataServiceVersion.V40, new ConfigurationImpl() , serviceRoot)
		println builder.build().toString()
		builder.appendPropertySegment("Properties")
		println builder.build().toString()
		builder.top(10)
		println URLDecoder.decode(builder.build().toString())
		
		FilterFactory fFactory= new FilterFactoryImpl(ODataServiceVersion.V40)
		FilterArg pr = new FilterProperty('RAMOdata.Models.District')
		EqFilter filterArg = new EqFilter(new FilterConst('District'), new FilterProperty('RAMOdata.Models.District'))
		builder.filter(filterArg)
		println builder.build().toString()
		
		builder = new URIBuilderImpl(ODataServiceVersion.V40, configuration, serviceRoot)
		final URIFilter filter1 = fFactory.eq(fFactory.getArgFactory().property('District'), fFactory.getArgFactory().property('RAMOdata.Models.District'+"'Haiku'"));
		final URIFilter filter2 = fFactory.eq(fFactory.getArgFactory().property('View'), fFactory.getArgFactory().property("'Mountain/Ocean'"));
		
		AndFilter andFilter = fFactory.and(filter1, filter2)
		builder.appendEntitySetSegment("Properties").top(10).filter(andFilter)
		println builder.build().toString()
		println URLDecoder.decode(builder.build().toString())
	}	
	
	void testUriBuilderFromMapMiltiple() {
		Map map = [PropertyType:['Residential'], District:['Haiku'], View:['Mountain']]
		String entity = oservice.getEntityCollectionName(map)
		assertEquals 'Residentials', entity
		
		URIFilter filter = oservice.generateFilter(map)		
		URIBuilder builder = oservice.generateBuilder(entity, filter)
		println URLDecoder.decode(builder.build().toString())
		assertEquals URLDecoder.decode(builder.build().toString()), '''http://ramodata.azurewebsites.net/odata/Residentials?$top=10&$filter=((District eq RAMOdata.Models.District'Haiku') and (View eq 'Mountain'))'''
	}
		
	void testUriBuilderFromMapSingle() {
		Map map = [View:['Mountain'],PropertyType:['Residential']]
		String entity = oservice.getEntityCollectionName(map)
		assertEquals 'Residentials', entity
		URIFilter filter = oservice.generateFilter(map)
		URIBuilder builder = oservice.generateBuilder(entity, filter)
		assertEquals URLDecoder.decode(builder.build().toString()), '''http://ramodata.azurewebsites.net/odata/Residentials?$top=10&$filter=(View eq 'Mountain')'''
	}
	
	void testUriBuilderFromMapSingleBaseType() {
		Map map = [District:['Haiku'],PropertyType:['Property']]
		String entity = oservice.getEntityCollectionName(map)
		assertEquals 'Properties', entity
		def entityType = map.get('PropertyType')
		assertEquals 'Property', oservice.getEntityTypeName(map).get(0)
		
		URIFilter filter = oservice.generateFilter(map)
		URIBuilder builder = oservice.generateBuilder(entity, filter)
		assertEquals URLDecoder.decode(builder.build().toString()), "http://ramodata.azurewebsites.net/odata/Properties?\$top=10&\$filter=(District eq RAMOdata.Models.District'Haiku')"
	}
	void testUriBuilderFromMapTypedInGraph() {
		Map map = [District:['Haiku'],PropertyType:['LivableProperty']]
		String entity = oservice.getEntityCollectionName(map)
		assertEquals 'LivableProperties', entity
		assertEquals 'LivableProperty', oservice.getEntityTypeName(map).get(0)
		
		URIFilter filter = oservice.generateFilter(map)
		URIBuilder builder = oservice.generateBuilder(entity, filter)
//		println builder.build().toString()
//		println URLDecoder.decode(builder.build().toString())
		assertEquals URLDecoder.decode(builder.build().toString()), "http://ramodata.azurewebsites.net/odata/LivableProperties?\$top=10&\$filter=(District eq RAMOdata.Models.District'Haiku')"
	}
	
	void testUriCondoViewSingle() {
		Map map = [View:['Ocean'],PropertyType:['Condo']]
		String entity = oservice.getEntityCollectionName(map)
		assertEquals 'Condos', entity
		URIFilter filter = oservice.generateFilter(map)
		URIBuilder builder = oservice.generateBuilder(entity, filter)
		println URLDecoder.decode(builder.build().toString())
		assertEquals URLDecoder.decode(builder.build().toString()),'''http://ramodata.azurewebsites.net/odata/Condos?$top=10&$filter=(View eq 'Ocean')'''
	}
	
	void testUriCondoViewMultiple() {
		Map map = [View:['Ocean','Mountain'],PropertyType:['Condo']]
		String entity = oservice.getEntityCollectionName(map)
		assertEquals 'Condos', entity
		URIFilter filter = oservice.generateFilter(map)
		URIBuilder builder = oservice.generateBuilder(entity, filter)
		println URLDecoder.decode(builder.build().toString())
		assertEquals URLDecoder.decode(builder.build().toString()),'''http://ramodata.azurewebsites.net/odata/Condos?$top=10&$filter=((View eq 'Ocean') or (View eq 'Mountain'))'''
	}
	
//	void testClient() {
//		ODataClientImpl client = new ODataClientImpl('http://ramodata.azurewebsites.net/odata/')
//		
//		client.
//	}
	
//	void testFindEntity() {
//		Map map = [View:['Ocean','Mountain'],PropertyType:['Condo']]
//		oservice.getEntityNameFromParams(map)
//	}
	

}
