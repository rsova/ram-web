package app.service.odata.helper;

import static org.junit.Assert.*

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

import app.service.odata.resolver.MetadataResolver;

class MetadataResolverTest {
	static MetadataResolver mh
	
	@BeforeClass
	static public void setUp() {
		mh = new MetadataResolver()
	}
	@AfterClass
	static public void tearDown() {
		mh = null 
	}
	
	@Test
	public void testProperties() {
		mh = new MetadataResolver()
		assertEquals 'RAMOdata.Models.District', mh.getTypeForProperty('Property', 'District')
		assertEquals 'Edm.String', mh.getTypeForProperty('Property', 'View')
		assertEquals 'RAMOdata.Models.PropertyType', mh.getTypeForProperty('Property', 'PropertyType')		
	}
	
	@Test
	public void testMatchEntity(){
		mh = new MetadataResolver()
		Map map = [View:['Ocean','Mountain'],PropertyType:['Condo']]
		assertEquals ('Condos', mh.getEntityNameFromParams(map))
		
		
	}


}
