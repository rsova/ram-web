package app.websearch.rpc.helper;

//import org.custommonkey.xmlunit.*
import org.custommonkey.xmlunit.XMLTestCase
import org.custommonkey.xmlunit.XMLUnit
import org.junit.Test

import com.google.gson.Gson
import com.google.gson.GsonBuilder


class XmlRpcHelperTest extends XMLTestCase{

	void setUp() throws Exception {
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
	}

	public void testLists_Condo() {
		String expectedXml = getClass().getClassLoader().getResourceAsStream('requests/lists-condo.xml').getText()
		def actual = XmlRpcHelper.toXmlRpcRequest('lists',['dbid':'dbid1139259934','getlist':'condo'])
		println actual
		assertXMLEqual(expectedXml, actual)
	}

	public void test_xmlRpcCollection() {
		String expectedXml = getClass().getClassLoader().getResourceAsStream('requests/lists-condo.xml').getText()
		def actual = XmlRpcHelper.xmlRpcToCollection(expectedXml)
		println actual
		def expected = ['dbid':'dbid1139259934','getlist':'condo']
		assertEquals( expected, actual)

	}
	
	public void test_paramsToXmlRpcResonse() {
		//[[MLS, 82.0], [Type, Single Family], [District, Haiku], [ListPrice, 3995000.0], [AddressStreet, 0 GOVERNMENT RD], [Status, ACTIVE], [WaterFront, OceanFront], [View, Ocean], [AgentName, Adam Falb RS-65612], [AgentPhone, (808) 8799200], [ListingOffice1_Name, Century 21 All Islands (W)], [ListingOffice1_Phone, (808) 8759921], [CoListingAgent_Name, Steve Baxter RS-72604], [CoListingAgent_Phone, (808) 8799200], [ListingOffice2_Name, Century 21 All Islands (W)], [ListingOffice2_Phone, (808) 8759921], [ListDate, 2012-02-23], [HouseCarport, 0.0], [HouseGarage, 0.0], [LT, Fee Simple], [Beds, 1.0], [Baths, 1.0], [LivSQFT, 865.0], [LandSQFT, 0.0], [LandAcres, 9.55], [MaintFee, 0.0], [Ohana_Beds, 0.0], [Ohana_Baths, 0.0], [Ohana_LivSQFT, 0.0], [PotentialShortSale, false], [REO, false], [AgentEmail, AmazingMaui@gmail.com], [AgentWebPage, www.AdamFalb.com], [Lat, 20.6506343717598], [Lon, -156.074950695038], [PoolYN, false], [PV_Installed, false]]

		def map = ['MLS':'82.0', 'Type':'Single Family', 'District':'Haiku']
		//println map
		def actual = XmlRpcHelper.toXmlResponse(map)
		def expectedXml = '''<?xml version="1.0" encoding="UTF-8"?><methodResponse><params><param><value><struct><member><name>MLS</name><value><string>82.0</string></value></member><member><name>Type</name><value><string>Single Family</string></value></member><member><name>District</name><value><string>Haiku</string></value></member></struct></value></param></params></methodResponse>'''
		assertXMLEqual(expectedXml, actual)
	}

	public void test_SearchResponseToCollection() {
		String expectedXml = getClass().getClassLoader().getResourceAsStream('search/from_url_1.xml').getText()
		//def path = 'methodResponse.params.param.value.array.data.value[5]'
		//def path = [methodResponse:[params:[param:[value:[arra:[data: 'value[5]']]]]]]
		def actual = XmlRpcHelper.xmlRpcToCollection1(expectedXml,null)
		def mappings = [:]
		def order = 0
		for (pair in actual) {
			//if(order>=25)break;
			if(order>=86)break;
			mappings.put(pair.key, ++order)
		}
		println mappings
		def json = new GsonBuilder().setPrettyPrinting().create().toJson('mappings':mappings)
	    new File('mappings.json')<<json
	}

	void test_OdataToXmlRpcResponse() {
		String odataJson = getClass().getClassLoader().getResourceAsStream('odata/example1.json').getText()
		List oData = new Gson().fromJson(odataJson, Map.class).get('value')
		
		String mappingsJson = getClass().getClassLoader().getResourceAsStream('mappings/mappings.json').getText()
		Map<String, Object> mappings = new Gson().fromJson(mappingsJson, Map.class).get("mappings");
		//Map listing = oData.first()
		def list = []
		for (Map listing in oData){
		def toXmlRpc = []
		for(pair in mappings){
			
			if(pair.value instanceof Collection){
				def coll = []
				for (val in pair.value){
					coll.add(listing.get(val).toString())
				}
				//phone
				if(coll?.first() && coll?.last()){
					toXmlRpc.add([pair.key, "("+coll.first()+") "+coll.last()])
				}
	
			}else if(pair.value instanceof String){ //number means no match found
				toXmlRpc.add([pair.key, listing.get(pair.value).toString()])
			}
		}
			list.add(toXmlRpc)
		}
		//println toXmlRpc
		println XmlRpcHelper.toXmlResponse(list)
		
	}
}
