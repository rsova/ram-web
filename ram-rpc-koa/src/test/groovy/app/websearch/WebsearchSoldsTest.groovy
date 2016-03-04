package app.websearch;

import static org.junit.Assert.*

import org.custommonkey.xmlunit.*
import org.junit.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import app.RamRpc
import app.controller.WebsearchController
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.xml.diff.SoldCondoDiff
import app.websearch.xml.diff.SoldResidentialDiff
import app.websearch.xml.diff.SoldResponseDiff
import app.websearch.xml.diff.SoldVacantLandDiff
import framework.BaseTest
import groovy.xml.XmlUtil

//WhatPropType			Value of the property being viewed. Note that, unlike the search method, this variable is not an array, but a single value.
//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
//WhatCondo	 		All  	Array: See Condo List
//WhatStartPrice	No		Lower Limit	Price (Integer)
//WhatEndPrice	 	No 		Upper Limit	Price (Integer)
//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
//WhatDistrict	 	All 	Array: See District List
//WhatLandTenure    All		Array:Fee Simple, Leasehold, Leasehold-FA
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
//WhatDiv			Any?	Array:?
//WhatZone		 	Any?	Array: ?
//----WhatZoneAny in Short
//WhatSec	 		Any?	Array: ?
//WhatPlat	 		Any?	Array: ?
//WhatPar	 		Any?	Array: ?

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RamRpc.class)
class WebsearchSoldsTest  extends BaseTest {
	
	@Autowired
	WebsearchController controller
	// Used to identify the test name to save the xml from idx in resources
	@Rule public TestName test = new TestName();

	final static def METHOD ='solds'

	//un-comment this to skip xml compare
	//final boolean SKIP_CMP
	
	@Test
	void test_type_condo_water_startprice_endprice() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Condominium', 'WhatWater':['OceanFront'],'WhatStartPrice':'100000', 'WhatEndPrice':'500000'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml,[], true )
		
		SoldResponseDiff differ = new SoldCondoDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_residential_startprice_endprice() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Residential', /*'WhatView':['Mountain'],*/ 'WhatStartPrice':'765000', 'WhatEndPrice':'999000'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml,[], true)
		
		SoldResponseDiff differ = new SoldResidentialDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_startprice_endprice() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Vacant Land', 'WhatStartPrice':'650000', 'WhatEndPrice':'650500'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml,[], true)
		
		SoldResponseDiff differ = new SoldVacantLandDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}

	@Test
	void test_type_district_startintarea_endintarea_startextarea_endextarea_number() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Residential', 'WhatDistrict':['Kahului'],'WhatStartIntArea':'1200','WhatEndIntArea':'1800','WhatStartExtArea':'1000','WhatEndExtArea':'7000'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldResidentialDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_condo_startbed_endbed_startbath_endbath_number() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Pacific Shores'],'WhatStartBed':'1','WhatEndBed':'5', 'WhatStartBath':'1','WhatEndBath':'5'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldCondoDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}

	@Test
	void test_view_ohana_endprice_number() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatView':['Mountain/Ocean'],'WhatOhana':['Single Family w/Att Ohana'], 'WhatEndPrice':'1700000'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldResidentialDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_landtenure() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Residential','WhatLandTenure':['Leasehold']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldResidentialDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_district_startprice_water() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium', 'WhatDistrict':['Kihei'],'WhatStartPrice':'500000','WhatWater':['OceanFront','BeachFront']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldCondoDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_agent() {	
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatAgent':['10202','10475']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldCondoDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_agent_startbed() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatAgent':['10202','10475'],'WhatStartBed':'2'])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldCondoDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
	@Test
	void test_type_office() {
		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatOffice':['2987','8956','8700']])
		println rpcXml
		websearch_raidx.add("-d " + rpcXml)
		def oldXml = websearch_raidx.execute().text
//		println oldXml
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//		println newXml
//		compareXml(oldXml, newXml)
		
		SoldResponseDiff differ = new SoldCondoDiff();
		compareData(differ, oldXml, newXml);
		assertTrue("No results were returned for this query!", differ.getElementCount() > 0);
	}
	
}
