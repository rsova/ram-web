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
		assertEquals 'RAMOdata.Models.District', mh.getTypeForProperty('Property', 'District')
		assertEquals 'Edm.String', mh.getTypeForProperty('Property', 'View')
		assertEquals 'RAMOdata.Models.PropertyType', mh.getTypeForProperty('Property', 'PropertyType')		
	}
	
	@Test
	public void testMatchEntity(){
		Map map = [View:['Ocean','Mountain'],PropertyType:['Condo']]
		assertEquals ('Condos', mh.getEntityNameFromParams(map))
	}
	
	@Test
	public void testEnumType_District(){
		List actual = mh.getEnumType("district")
		List expected = ['Haiku', 'Hana', 'Honokohau', 'Kaanapali', 'Kahakuloa', 'Kahului', 'Kapalua', 'Kaupo', 'Keanae', 'Kihei', 'Kipahulu', 'KulaUlupalakuaKanaio', 'Lahaina', 'Lanai', 'Maalaea', 'MakawaoOlindaHaliimaile', 'MauiMeadows', 'Molokai', 'Nahiku', 'NapiliKahanaHonokowai', 'Olowalu', 'Pukalani', 'SpreckelsvillePaiaKuau', 'WaileaMakena', 'Wailuku']
		assertEquals(actual, expected)
	}
	@Test
	public void testEnumType_Condo(){
		List actual = mh.getEnumType("condo")
		//println actual
		assertEquals(316, actual.size())
	}
	//@Test
	public void testEnumType_Office(){
		List actual = mh.getEnumType("district")
	    List expected = []
		assertEquals(actual, expected)
	}
	//@Test
	public void testEnumType_Agent(){
		List actual = mh.getEnumType("district")
		List expected = []
		assertEquals(actual, expected)
	}

}
