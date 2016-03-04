package app.websearch;

import org.custommonkey.xmlunit.*
import org.junit.*
import org.junit.rules.TestName;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import app.RamRpc
import app.controller.WebsearchController
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.diff.ShortSearchDiff;
import framework.BaseTest
import groovy.xml.XmlUtil

//WhatPage	 		1	Page of data to return
//WhatNumber		The maximum number of MLS numbers that can be passed is 200.	200	Number of listings to return //Max number is 200
//WhatMLS	 		None	//Comma delimited list of mls numbers.
//WhatPropType		Array: Residential, Vacant Land, Condominium, Commercial, Business, Time Interval, Multi-Dwelling Res	//If no value is passed, the default is all property types. If WhatCondo is passed, the default is Condominium. If WhatOhana is passed, the default is Residential.
//WhatAddressFull	None	Any part of an address
//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
//WhatCondo	 		All  	Array: See Condo List
//WhatStartPrice	No		Lower Limit	Price (Integer)
//WhatEndPrice	 	No 		Upper Limit	Price (Integer)

//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
//WhatDistrict	 	All 	Array: See District List
//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
//WhatAgent			All		Array: See Agent List (Use agent ID number)

//WhatOffice	 	All		Array: See Office List (Use Office ID Number)
//WhatStartBed	 	No 		Lower Limit	Integer
//WhatEndBed	 	No 		Upper Limit	Integer
//WhatStartBath	 	No 		Lower Limit	Integer
//WhatEndBath	 	No 		Upper Limit	Integer

//WhatStartIntArea	No 		Lower Limit	Integer (Square Feet)
//WhatEndIntArea	No 		Upper Limit	Integer (Square Feet)
//WhatStartExtArea	No 		Lower Limit	Integer (Square Feet)
//WhatEndExtArea	No 		Upper Limit	Integer (Square Feet)
//WhatDiv	 		Any?		Array: ?
//WhatZoneAny	 	Any?		Array: ?
//WhatSec	 		Any?		Array: ?
//WhatPlat	 		Any?		Array: ?
//WhatPar	 		Any?		Array: ?
//WhatStartDate	 	Any		List Date in the format: Year-Month-Day (xxxx-xx-xx)
//WhatEndDate	 	Any?		List Date in the format: Year-Month-Day (xxxx-xx-xx)
//WhatSortType1	 	MLS #	You may sort by any field returned.
//WhatSortDirection1|Ascending	Direction to sort by, ASC or DESC
//WhatSortType2	 	None	You may include a secondary sort using any field returned.
//WhatSortDirection2|None	Direction for secondary sort, ASC or DESC
//ExAgency	 		Yes?MAKES NO DIFF(RS)		Include exclusive agency listings in the query.
//These values represent two corners of a rectangle. Any listings who's Latitude and Longitude fall within these values are returned.
//WhatLat1	 		None	
//WhatLon1	 		None
//WhatLat2	 		None?MAKES NO DIFF(RS)
//WhatLon2	 		None?MAKES NO DIFF(RS)
//MeFirst	 		N	If set to Y, the Agent or Office listing will be returned first.

//final static def METHOD ='<methodCall><methodName>short</methodName><params><param><value><struct><member><name>dbid</name><value><string>dbid1139259934</string></value></member><member><name>WhatPropType</name><value><array><data><value><string>Residential</string></value></data></array></value></member></struct></value></param></params></methodCall>';
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RamRpc.class)
class WebsearchShortTest extends BaseTest {
	
	@Autowired
	WebsearchController controller
	
	// Used to identify the test name to save the xml from idx in resources
	@Rule public TestName test = new TestName();

	static final String SEARCH_TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]'
	static final List ALL_STATS =	["/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[1]/int[1]/text()[1]","/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[2]/int[]/text()[1]","/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[3]/double[1]/text()[1]","/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]"]
	final static def METHOD ='short'
	
	//un-comment this to skip xml compare
	//final boolean SKIP_CMP	
	
	@Test
	void test_mls() {		
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatMLS':['351706','353027','353832'], 'WhatNumber':20])
		
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, [SEARCH_TIME_TO_RUN_DOUBLE], true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_msl_nolimit() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatMLS':['353027']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, [SEARCH_TIME_TO_RUN_DOUBLE], true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_msl_fraction_partial_interval() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatMLS':['363922']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml, [SEARCH_TIME_TO_RUN_DOUBLE], true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
   @Test
	void test_lat1_lon1_sortdirection1_number() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatLat1': '20.0','WhatLon1':'-156.0','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatNumber':5])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
   @Test
   void test_lat1_lon1_lat2_lon2_sortdirection1_number() {
	   def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatLat1': '20.0','WhatLon1':'-156.0','WhatLat2': '21.0','WhatLon2':'-157.0','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatNumber':5])
	   println rpcXml
	   websearch_raidx.add("-d " + rpcXml)
	   def oldXml = websearch_raidx.execute().text
	  //println oldXml
	   
	   def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
	  //println newXml
//	   compareXml(oldXml, newXml, ALL_STATS, true)
	   
	   ShortSearchDiff differ = new ShortSearchDiff();
	   compareData(differ, oldXml, newXml);
	   assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
   }
	
	@Test
	void test_startdate_enddate_sortype1_sortdirection1_sortype2_sortdirection2_number() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatStartDate':'2014-08-10','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatSortType2':'MLS','WhatSortDirection2':'DESC','WhatNumber':5])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text		
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))	
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_startintarea_endintarea_startextarea_endextarea_number() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatStartIntArea':'800','WhatEndIntArea':'2000','WhatStartExtArea':'200','WhatEndExtArea':'1000', 'WhatNumber':1])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)

		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_condo_agent_startbed_endbed_startbath_endbath_number() {
//		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Pacific Shores'],'WhatAgent':['21851'],'WhatStartBed':'1','WhatEndBed':'2', 'WhatStartBath':'1','WhatEndBath':'2', 'WhatNumber':2])
		//agent is not in old
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Pacific Shores'],'WhatAgent':['14406'],'WhatStartBed':'1','WhatEndBed':'2', 'WhatStartBath':'1','WhatEndBath':'2', 'WhatNumber':2])
		println XmlUtil.serialize(rpcXml)
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)

		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_ohana_endprice_number() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOhana':['Single Family'],'WhatSortType1':'ListPrice','WhatSortDirection1':'DESC','WhatNumber':2])
		//def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatType':['Residential'],'WhatDistrict':['Kihei'],'WhatAgent':['22052'],'WhatSortType1':'ListPrice','WhatNumber':2])
		
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)

		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_district_agent() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatType':['Residential'],'WhatDistrict':['Kihei'],'WhatAgent':['22052']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)

		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_view_district_tenure_endprice_number() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatView':['Ocean',' Mountain'],'WhatDistrict':['Wailea/Makena'],'WhatLandTenure':['Fee Simple', 'Leasehold'],'WhatEndPrice':'700000','WhatNumber':5])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);

	}
	//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
	//WhatDistrict	 	All 	Array: See District List
	//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
	@Test
	void test_water_startprice_number() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatWater':['Beach Front'],'WhatStartPrice':'3700000','WhatNumber':2])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)

		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_addressfull() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Condominium'],'WhatAddressFull':'4909 Hana Highway','WhatNumber':20])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_page_number() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Commercial'], 'WhatPage':1, 'WhatNumber':20])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_number() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Multi-Dwelling Res'], 'WhatNumber':20])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_startDate() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatStartDate':'2015-02-02','WhatNumber':20])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_endDate() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':['Commercial'], 'WhatEndDate':'2014-12-01','WhatNumber':20])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_condo() {
		def rpcXml =XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatCondo':['515 Liholiho Street', '71 Miner Place', '85 Walaka Street', 'Aina o Kane'], 'WhatNumber':20])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
		//println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		//println newXml
//		compareXml(oldXml, newXml, ALL_STATS, true)
		
		ShortSearchDiff differ = new ShortSearchDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
}
