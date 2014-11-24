package app.websearch;

import org.custommonkey.xmlunit.*
import org.junit.*

import app.controller.WebsearchController
import app.service.odata.RamOdataService
import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.transformer.TransformationService
import app.websearch.url.helper.UrlHelper
import framework.BaseTest

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

class WebsearchSearchTest extends BaseTest {
//	final static def WS='http://websearch.ramidx.com/smartframe/ramxml.php'
	final static def METHOD ='search'
//	static final String SRC_TEST_RESOURCES = 'src/test/resources/'
//	static final String TIME_TO_RUN_DOUBLE = '/methodResponse[1]/params[1]/param[1]/value[1]/array[1]/data[1]/value[4]/double[1]/text()[1]'
//	static final String DBID = 'dbid1139259934'//dbid1142571627
	//un-comment this to skip xml compare
	final boolean SKIP_CMP

	def curl = []
		
	void test_lat1_lon1_sortdirection1_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatLat1':'20.0','WhatLon1':'-156.0','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatNumber':'5']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_startdate_enddate_sortype1_sortdirection1_sortype2_sortdirection2_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatStartDate':'2014-08-10','WhatSortType1':'ListPrice','WhatSortDirection1':'ASC','WhatSortType2':'MLS','WhatSortDirection2':'DESC','WhatNumber':'5']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_startintarea_endintarea_startextarea_endextarea_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatStartIntArea':'800','WhatEndIntArea':'2000','WhatStartExtArea':'200','WhatEndExtArea':'1000', 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_condo_agent_startbed_endbed_startbath_endbath_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Pacific Shores'],'WhatAgent':['12496'],'WhatStartBed':'1','WhatEndBed':'2', 'WhatStartBath':'1','WhatEndBath':'2', 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_ohana_endprice_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatOhana':['Single Family'],'WhatEndPrice':'6700000','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_water_startprice_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatWater':['BeachFront'],'WhatStartPrice':'3700000','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
////WhatPropType		Array: Residential, Vacant Land, Condominium, Commercial, Business, Time Interval, Multi-Dwelling Res	

	void test_type_addressfull() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Condominium'],'WhatAddressFull':'Bay DR']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_district_endprice_number() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Commercial'],'WhatDistrict':['Kihei'],'WhatEndPrice':'250000','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_mls() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatMLS':['329222', '359341']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_page_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Time Interval'], 'WhatPage':'1', 'WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Condominium', 'Residential'],'WhatPage':'2','WhatNumber':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_water_startprice_endprice() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Residential', 'WhatWater':['OceanFront'],'WhatStartPrice':'700000', 'WhatEndPrice':'10000000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_startprice_endprice() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID, 'WhatPropType':'Residential', 'WhatStartPrice':'650000', 'WhatEndPrice':'750000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}

	void test_type_district_startintarea_endintarea_startextarea_endextarea_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Residential', 'WhatDistrict':['Kahului'],'WhatStartIntArea':'1200','WhatEndIntArea':'1800','WhatStartExtArea':'1000','WhatEndExtArea':'7000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)		
	}
	
	void test_condo_startbed_endbed_startbath_endbath_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatCondo':['Iao Gardens'],'WhatStartBed':'1','WhatEndBed':'2', 'WhatStartBath':'1','WhatEndBath':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}

	void test_view_ohana_endprice_number() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatView':['Mountain/Ocean'],'WhatOhana':['Single Family w/Att Ohana'], 'WhatEndPrice':'1700000']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)
	}
	
	void test_type_landtenure() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Residential','WhatLandTenure':['Leasehold']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_type_district_startprice_water() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium', 'WhatDistrict':['Kihei'],'WhatStartPrice':'500000','WhatWater':['OceanFront','BeachFront']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_type_agent() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatAgent':['10202','10475']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_type_agent_startbed() {
		
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Condominium','WhatAgent':['10202','10475'],'WhatStartBed':'2']))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_type_office() {
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':'Commercial','WhatOffice':['2987','8956','8700']]))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	void test_type_residential() {
		def request = XmlRpcHelper.toXmlRpcRequest(METHOD,['dbid':DBID,'WhatPropType':['Residential'], 'WhatNumber':'2'])
		println request
		curl.add("-d " + request)
		def actual = curl.execute().text
		println actual
//		compareXml(actual)
	}

	void test_from_url_1() {
		def url ='''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=450000&WhatEndPrice=2000000&WhatPool=N&WhatLandTenure%5B%5D=Fee+Simple&WhatDistrict%5B%5D=Kahului&WhatDistrict%5B%5D=Maalaea&WhatDistrict%5B%5D=Maui+Meadows&WhatDistrict%5B%5D=Napili%2FKahana%2FHonokowai&WhatDistrict%5B%5D=Wailuku&WhatStartBed=2&WhatEndBed=&WhatStartBath=2&WhatEndBath=&WhatStartIntArea=1000&WhatEndIntArea=2000&WhatStartExtArea=21780&WhatEndExtArea=&WhatSortType1=District&WhatSortDirection1=ASC&WhatSortType2=ListPrice&WhatSortDirection2=DESC&Task=Search'''
		def map = UrlHelper.urlToMap(url)		
		map.put('dbid',DBID)
		println map
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,map))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_from_url_2() {
		def url ='''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Condominium&WhatStartPrice=&WhatEndPrice=800000&WhatLandTenure%5B%5D=Fee+Simple&WhatView%5B%5D=Garden+View&WhatCondo%5B%5D=Wailea+Beach+Villas&WhatCondo%5B%5D=Wailea+Ekahi+I&WhatCondo%5B%5D=Wailea+Ekahi+II&WhatCondo%5B%5D=Wailea+Ekahi+III&WhatCondo%5B%5D=Wailea+Ekolu&WhatCondo%5B%5D=Wailea+Elua+I+A&WhatCondo%5B%5D=Wailea+Elua+I+B&WhatCondo%5B%5D=Wailea+Elua+II&WhatCondo%5B%5D=Wailea+Fairway+Villas&WhatCondo%5B%5D=Wailea+Palms&WhatCondo%5B%5D=Wailea+Point+I+II+III&WhatCondo%5B%5D=Wailea+Town+Center&WhatStartBed=1&WhatEndBed=2&WhatStartBath=2&WhatEndBath=&WhatStartIntArea=700&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=BuildingName&WhatSortDirection2=ASC&Task=Search'''
		def map = UrlHelper.urlToMap(url)		
		map.put('dbid',DBID)
		println map
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,map))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_from_url_3() {
		def url ='''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Condominium&WhatStartPrice=&WhatEndPrice=600000&WhatLandTenure%5B%5D=Leasehold-FA&WhatWater%5B%5D=OceanFront&WhatWater%5B%5D=BeachFront&WhatDistrict%5B%5D=Maalaea&WhatStartBed=&WhatEndBed=2&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		def map = UrlHelper.urlToMap(url)		
		map.put('dbid',DBID)
		println map
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,map))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	void test_from_url_4() {
		def url ='''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatREO=Y&WhatLandTenure%5B%5D=Fee+Simple&WhatView%5B%5D=Mountain%2FOcean&WhatStartBed=2&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
				def map = UrlHelper.urlToMap(url)		
				map.put('dbid',DBID)
				println map
				curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,map))
				def actual = curl.execute().text
				println actual
				compareXml(actual)			
	}
	
	void test_from_url_5() {
		def url ='''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Vacant+Land&WhatStartPrice=&WhatEndPrice=&WhatDistrict%5B%5D=Molokai&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=15000&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=LandSQFT&WhatSortDirection2=ASC&Task=Search'''
		def map = UrlHelper.urlToMap(url)		
		map.put('dbid',DBID)
		println map
		curl.add("-d " + XmlRpcHelper.toXmlRpcRequest(METHOD,map))
		def actual = curl.execute().text
		println actual
		compareXml(actual)			
	}
	
	
	
	void testResidentialOne() {
		//def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search&WhatNumber=1'''
		def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&Task=Search&WhatNumber=1'''
//		<methodCall><methodName>search</methodName><params><param><value><struct><member><name>WhatPropType</name><value><array><data><value><string>Residential</string></value></data></array></value></member><member><name>WhatNumber</name><value><string>1</string></value></member><member><name>dbid</name><value><string>dbid1139259934</string></value></member></struct></value></param></params></methodCall>
//		[WhatPropType:[Residential], WhatNumber:1, dbid:dbid1139259934]		
		def map = UrlHelper.urlToMap(url)
		map.put('dbid',DBID)
		println map
		def request = XmlRpcHelper.toXmlRpcRequest(METHOD,map)
		println request
		curl.add("-d " + request)
		def webIdxXml = curl.execute().text
		println webIdxXml
		println '~~~~~~~~~~~~~~~~~~'
		service.top = 1
		TransformationService transformer = new TransformationService()
		WebsearchController controller = new WebsearchController()
		controller.oService = service
		controller.transformer = transformer
		def odata =  controller.executeSearch(map)
		println odata


		def odataXml = XmlRpcHelper.toXmlResponse([odata.cnt1, odata.top, odata.cnt2,odata.total,odata.data])
		println odataXml
	}
	
	void testCondoOne() {
		//def url = '''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search&WhatNumber=1'''
		def url = '''http://www.ramaui.com/Results.php?MLS=329222&WhatStartPrice=&WhatEndPrice=&WhatDistrict%5B%5D=Haiku&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
//		<methodCall><methodName>search</methodName><params><param><value><struct><member><name>WhatPropType</name><value><array><data><value><string>Residential</string></value></data></array></value></member><member><name>WhatNumber</name><value><string>1</string></value></member><member><name>dbid</name><value><string>dbid1139259934</string></value></member></struct></value></param></params></methodCall>
//		[WhatPropType:[Residential], WhatNumber:1, dbid:dbid1139259934]
		def map =[WhatMLS:['329222'],  WhatSortType1:'ListPrice', WhatSortDirection1:'ASC', WhatSortDirection2:'ASC']
		
//		//def map = UrlHelper.urlToMap(url)
//		def map = [WhatPropType:['Condo'], WhatAddress:'500 Bay DR']
		map.put('dbid',DBID)
		println map
		def request = XmlRpcHelper.toXmlRpcRequest(METHOD,map)
		println request
		curl.add("-d " + request)
		def webIdxXml = curl.execute().text
		println webIdxXml
		println '~~~~~~~~~~~~~~~~~~'
		http://ramodata.azurewebsites.net/odata/Condos?$top=1&$filter=Address eq '500 Bay DR'
		map = [WhatPropType:['Condo'], WhatAddress:['500 Bay DR']]
		service.top = 1
		TransformationService transformer = new TransformationService()
		WebsearchController controller = new WebsearchController()
		controller.oService = service
		controller.transformer = transformer
		def odata =  controller.executeSearch(map)
		println odata

		def odataXml = XmlRpcHelper.toXmlResponse(odata)
		println odataXml
	}
	
}
