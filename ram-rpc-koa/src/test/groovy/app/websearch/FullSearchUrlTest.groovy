package app.websearch;

//import org.custommonkey.xmlunit.*
import java.util.List;
import java.util.Map;

import org.junit.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import app.RamRpc
import app.controller.WebsearchController
import app.service.sqlserver.RamSqlService
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.url.helper.UrlHelper
import app.websearch.xml.diff.FullSearchDiff
import framework.BaseTest
import groovy.xml.XmlUtil

//WhatPage	 		1	Page of data to return
//WhatNumber		The maximum number of MLS numbers that can be passed is 200.	200	Number of listings to return, Max number is 200
//WhatMLS	 		None	//Comma delimited list of mls numbers.
//WhatPropType		Array: Residential, Vacant, Land, Condominium, Commercial, Business, Time Interval, Multi-Dwelling Res
// ----- If no value is passed, the default is all property types. If WhatCondo is passed, the default is Condominium. If WhatOhana is passed, the default is Residential.
//WhatAddressFull	None	Any part of an address
//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
//WhatCondo	 		All  	Array: See Condo List

//WhatStartPrice	No		Lower Limit	Price (Integer)
//WhatEndPrice	 	No 		Upper Limit	Price (Integer)
//WhatREO	 		All	Set to Y to pull only REO - foreclosure
//WhatPSS	 		All	Set to Y to pull only Potential Short Sales

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
//WhatRadius	 	None	If WhatRadius is passed, WhatLat1 and WhatLon1 are used as the center of a circle and returned listings are those that fall within the radius. If a radius is passed, WhatLat2 and WhatLon2 are ignored.
//WhatPool	 		None	Boolean field for properties with Pool
//MeFirst	 		N	If set to Y, the Agent or Office listing will be returned first.


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RamRpc.class)
class FullSearchUrlTest extends BaseTest {

	RamSqlService service
	@Autowired
	WebsearchController controller
	// Used to identify the test name to save the xml from idx in resources
	@Rule public TestName test = new TestName();
	final static def METHOD ='search'
	//un-comment this to skip xml compare
	//final boolean SKIP_CMP
	private Map fullSearchUrlRunner(url){

		def map = UrlHelper.urlToMap(url)
		map.put('dbid',DBID)

		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD, map)
		println XmlUtil.serialize(rpcXml)

		def oldPath = SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".old.xml"
		File oldFile = new File(oldPath)
		def oldXml
		if(!oldFile.exists()){
			websearch_raidx.add("-d " + rpcXml)
			oldXml = websearch_raidx.execute().text
			saveToFile(oldPath, oldXml);			
		}else{
			 oldXml = oldFile.text
		}
		
		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
		FullSearchDiff differ = new FullSearchDiff()
		compareData(differ, oldXml, newXml);
	}	
	
//	private Map fullSearchUrlRunner(url){
//		
//		def map = UrlHelper.urlToMap(url)
//		map.put('dbid',DBID)
//		
//		def rpcXml = XmlRpcHelper.toXmlRpcRequest(METHOD, map)
//		println XmlUtil.serialize(rpcXml)
//		
//		def oldPath = SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".old.xml"
//		File oldFile = new File(oldPath)
//		def oldXml
//		
//		if(oldFile.exists()){
//			

//		}else{		
//			websearch_raidx.add("-d " + rpcXml)
//			oldXml = websearch_raidx.execute().text	
//			saveToFile(oldPath, oldXml);
//		}
//
//		def newXml = XmlUtil.serialize(controller.handleWebsearch(rpcXml))
//
//		// compare		
//		def oldArray = XmlRpcHelper.xmlRpcToCollection(oldXml);
//		def newArray = XmlRpcHelper.xmlRpcToCollection(newXml);
//		
//		saveToFile(SRC_TEST_RESOURCES + "$METHOD/${test.getMethodName().replaceFirst('test_', '')}"+".new.xml", newXml);
//		
//		def oMls = [] //oldArray[4]*.MLS
//		oldArray[4].each {entry -> if (entry instanceof Map) {oMls += entry.MLS}}
//		def nMls = []
//		newArray[4].each {entry -> if (entry instanceof Map) {nMls += entry.MLS}}
//		
//		if(oMls != nMls){
//			System.err.println 'Results are different '
//			System.err.println "Old MSL: ${oMls}"
//			System.err.println "New MSL: ${nMls}"
//		}
//		
//		Map results = [:];
//		for(mls in oMls){
//			Map o = oldArray[4].find{it.MLS == mls}
//			Map n = newArray[4].find{it.MLS == mls}
//			def errors = compare(o,n)
//			results.put(mls, errors)
//		}
//		return results
//	}

//	private def List compare(Map o, Map n){
//		def errors = []
//		def keys = o.keySet()
//		def nKeys = n.keySet()
//		if (keys.size() != nKeys.size()){
//		   errors << " Missing elements !!! old: ${keys.size()} ;  new: ${nKeys.size()}"
//		   return
//		}
//		
//		for (key in keys) {
//		   if( o.get(key) != n.get(key)){
//			   errors << "Diff: $key old: '${o.get(key)}' new : '${n.get(key)}'"
//		   }
//	   }
//		return errors
//	}
	
	@Test
	void test1() {
		//WHERE EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') 
		// AND EPC.LegacyName in ('Residential','Multi-Dwelling Res') AND ListPrice >= 450000 AND ListPrice <= 500000 AND EDI.LegacyName in ('Kihei') ORDER BY  ListPrice ASC OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY		
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatPropType%5B%5D=Multi-Dwelling+Res&WhatStartPrice=450000&WhatEndPrice=500000&WhatDistrict%5B%5D=Kihei&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		def results = fullSearchUrlRunner(url)
	}
	
	@Test
	void test2() {
		//WHERE EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') 
		//AND   EPC.LegacyName in ('Condominium','Multi-Dwelling Res') AND ListPrice >= 450000 AND ListPrice <= 500000 AND EDI.LegacyName in ('Kihei') ORDER BY  ListPrice ASC OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Condominium&WhatPropType%5B%5D=Multi-Dwelling+Res&WhatStartPrice=450000&WhatEndPrice=500000&WhatDistrict%5B%5D=Kihei&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		fullSearchUrlRunner(url)
	}
	
	@Test
	void test3() {
		//WHERE EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') 
		//AND MLSNumber in ('364682') ORDER BY  ListPrice ASC OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY
		def url = '''http://www.ramaui.com/Results.php?MLS=364682&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		fullSearchUrlRunner(url)
	}
	
	@Test
	void test4() {
		//WHERE EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') 
		//AND EPC.LegacyName in ('Vacant Land') AND (  EVI.LegacyName LIKE ('%Ocean%') ) AND EDI.LegacyName in ('Haiku') ORDER BY  ListPrice ASC OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Vacant+Land&WhatStartPrice=&WhatEndPrice=&WhatView%5B%5D=Ocean&WhatDistrict%5B%5D=Haiku&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		fullSearchUrlRunner(url)
	}
	@Test
	void test5() {
		//WHERE EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') AND 
		//EPC.LegacyName in ('Residential') AND (  EVI.LegacyName LIKE ('%Ocean%')  OR  EVI.LegacyName LIKE ('%Mountain%') ) AND EDI.LegacyName in ('Napili/Kahana/Honokowai') AND Beds <= 3 ORDER BY  ListPrice ASC OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatView%5B%5D=Ocean&WhatView%5B%5D=Mountain&WhatDistrict%5B%5D=Napili%2FKahana%2FHonokowai&WhatStartBed=&WhatEndBed=3&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		fullSearchUrlRunner(url)
	}
	
	@Test
	void test6() {
		//WHERE EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') 
		//AND EPC.LegacyName in ('Residential','Condominium') AND EWF.LegacyName in ('OceanFront','BeachFront') AND ListPrice >= 500000 AND (  EVI.LegacyName LIKE ('%Mountain%') ) AND EDI.LegacyName in ('Wailea/Makena') AND ELT.LegacyName in ('Fee Simple') AND Beds <= 3 AND Baths >= 2 ORDER BY  ListPrice ASC OFFSET 0 ROWS FETCH NEXT 200 ROWS ONLY
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatPropType%5B%5D=Condominium&WhatStartPrice=500000&WhatEndPrice=&WhatPool=Y&WhatLandTenure%5B%5D=Fee+Simple&WhatWater%5B%5D=OceanFront&WhatWater%5B%5D=BeachFront&WhatView%5B%5D=Mountain&WhatDistrict%5B%5D=Wailea%2FMakena&WhatStartBed=&WhatEndBed=3&WhatStartBath=2&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		fullSearchUrlRunner(url)
	}
	
	@Test
	void test7() {
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatPropType%5B%5D=Fraction-Partial-Interval&WhatStartPrice=&WhatEndPrice=700000&WhatREO=Y&WhatLandTenure%5B%5D=Fee+Simple&WhatStartBed=&WhatEndBed=&WhatStartBath=2&WhatEndBath=&WhatStartIntArea=1000&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		fullSearchUrlRunner(url)
	}
	
//	@Test
//	void test() {
//		def url = ''''''
//		assertTrue("No results were returned for this query!", fullSearchUrlRunner(url).getElementCount() > 0);
//	}
}