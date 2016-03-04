package scrap;

import static org.junit.Assert.*
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

import java.math.MathContext
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.SimpleDateFormat

import org.apache.commons.lang.CharUtils
import org.apache.commons.lang.StringEscapeUtils
import org.junit.Test

import app.websearch.rpc.helper.XmlRpcHelper
import app.websearch.url.helper.UrlHelper

class UtilsTest {
	
	 public String TEST = 'Testing test'
	 public double TESTD = 2.5
	 
	 @Test
	 public void test_0() {
		def oldXml = new File("/Users/romansova/tools/tfs-ram-v1/ram-rpc-md/src/test/resources/search/mls_1.old.xml").text
		 def newXml = new File("/Users/romansova/tools/tfs-ram-v1/ram-rpc-md/src/test/resources/search/mls_1.new.xml").text
		 
		 def oldArray = XmlRpcHelper.xmlRpcToCollection(oldXml);
		 def newArray = XmlRpcHelper.xmlRpcToCollection(newXml);
		 
		 def oMls = oldArray[4]*.MLS
		 def nMls = oldArray[4]*.MLS
		 
		 if(oMls != nMls){
			 System.err.println 'Results are different '
			 System.err.println "Old MSL: ${oMls}"
			 System.err.println "New MSL: ${nMls}"		 
		 }
		 
		 for(mls in oMls){
			 System.err.println "Comparing listings with ${mls}"
			 Map o = oldArray[4].find{it.MLS == mls}
			 Map n = newArray[4].find{it.MLS == mls}
			 def errors = compare(o,n)
			 
			 if(errors){ errors.each{ System.err.println it} }
		 }
		 
	}
	 
	 def List compare(Map o, Map n){
		 def errors = []
		 def keys = o.keySet()
		 def nKeys = n.keySet()
		 if (keys.size() != nKeys.size()){
			errors << " Missing elements !!! old: ${keys.size()} ;  new: ${nKeys.size()}"
			return
		 }
		 
		 for (key in keys) {
			if( o.get(key) != n.get(key)){
				errors << "Diff: $key old: '${o.get(key)}' new : '${n.get(key)}'"
			}
		}
	 	return errors
	 }
 
	@Test
	public void test1() {
		def list =[[identifier:1000, fullName:'Richard E. Lopez RB-13603'], [identifier:1004, fullName:'Evelyn Proctor']]
		//def a = list.get(0)
		//println list*.values()
		//assertArrayEquals(list*.values(),[[1000, 'Richard E. Lopez RB-13603'], [1004, 'Evelyn Proctor']])
	}
	@Test
	public void test2() {
		//RamSqlService 	b;
		def a = 'test'
		//String myString = (MyClass.getField('myStringField').getType())MyClass.getField('myStringField).get()
		println UtilsTest.class.getField('TEST')
		println UtilsTest.class.getField('TEST').get(this)
		//println (Utils.class.getField('TEST').get()) as Utils.class.getField('TEST').getType().toString()
		
		println UtilsTest.class.getField('TESTD').get(this)
		println "$a"
		def dets = [] as Set
		UtilsTest.fields.each{ //public fields only
			dets << [ it.name, it.type.name , ] //name of field and name of type
		  }
		println dets
	}
	
	@Test
	public void test3() {
	 String AGENT = "select UserCode, FullName from dbo.Agent order by LastName asc";
	 String OFFICE = "select distinct MainOfficeAbbreviation, MainOfficeName from dbo.Office order by MainOfficeName asc";
	 Map lists= ['agent':AGENT, 'office':OFFICE]
	 println lists.get('agent')
	}
	
	//@Test
	public void test4() {
		String path = 'src/test/resources/lists/condo1.xml'
		String xml = new File(path).text
		println XmlRpcHelper.xmlRpcToCollection(xml)
		//['515 Liholiho Street', '71 Miner Place', '85 Walaka Street', 'Aina o Kane', 'aina-nalu', 'Alaeloa', 'Aloha Pualani', 'Andaz Residences', 'Auhana Kuleana', 'Awihi Townhouse', 'Bay Vista Apts', 'Boardwalk', 'Carmel Apts', 'Chandelier Center', 'Channel House', 'Coconut Grove', 'Cottages at Kulamalu', 'Gardens at West Maui', 'Gardens Upcountry', 'Grand Champions', 'Hale Hui Kai', 'Hale Iliili', 'Hale Kai I', 'Hale Kai O Kihei I', 'Hale Kai O Kihei II', 'Hale Kamaole', 'Hale Kanani', 'Hale Mahialani', 'Hale Mahina', 'Hale Nani', 'Hale Napili Apts', 'Hale Ono Loa', 'Hale Pau Hana', 'Hale Royale', 'Hale Wailana', 'Haleakala Gardens', 'Haleakala Shores', 'Hana Kai-Maui', 'Happy Valley Village', 'Harbor Lights', 'Hawealani Condominium', 'Hokulani Golf Villas', 'Hololani', 'Hono Kai', 'Hono Koa', 'Honokeana Cove', 'Honokowai East', 'Honokowai Palms', 'Honua Kai - Hoku Lani', 'Honua Kai - Konea', 'Hoolea Terrace at Kehalani', 'Hoolei', 'Hotel Hana-Maui Condominiums', 'Hotel Molokai', 'Hoyochi Nikko', 'Iao Gardens', 'Iao Parkside I', 'Iao Parkside II', 'Iao Parkside III', 'Iao Parkside IV-A', 'Iao Parkside IV-B', 'Iao Parkside IV-C', 'Iliahi at Kehalani', 'International Colony Club I', 'International Colony Club II', 'Island Sands', 'Island Surf', 'Kaanapali Alii', 'Kaanapali Beach Club', 'Kaanapali Beach Vacation Resort', 'Kaanapali Plantation', 'Kaanapali Royal', 'Kaanapali Shores', 'Kahana Beach Resort', 'Kahana Falls', 'Kahana Manor', 'Kahana Outrigger', 'Kahana Reef', 'Kahana Sunset', 'Kahana Villa', 'Kahana Village', 'Kahekili Grove', 'Kahului Ikena', 'Kai Ani Village', 'Kai Makani', 'Kai Malu', 'Kalama Gardens', 'Kalama Terrace', 'Kalama Townhouses', 'Kalama Villa', 'Kaleialoha', 'Kamaole Beach Club', 'Kamaole Beach Royale', 'Kamaole Nalu', 'Kamaole One', 'Kamaole Sands', 'Kamoa Views', 'Kanai A Nalu', 'Kanani Wailea', 'Kane', 'Kanoe Apts', 'Kanoe Palms', 'Kanoelani Apts', 'Kapalua Bay Villas I', 'Kapalua Bay Villas II', 'Kapalua Golf Villas', 'Kapalua Ironwoods', 'Kapalua Ridge', 'Kapu Townhouses', 'Kauhale Makai', 'Ke Alii Ocean Villas', 'Ke Nani Kai', 'Kealaloa Court', 'Kealia', 'Keawakapu', 'Kehalani Gardens', 'Keonekai Villages', 'Kihei Akahi', 'Kihei Alii Kai', 'Kihei Bay Surf', 'Kihei Bay Vista', 'Kihei Beach', 'Kihei Cove', 'Kihei Garden Estates', 'Kihei Holiday', 'Kihei Islana', 'Kihei Kai', 'Kihei Kai Nani', 'Kihei Manor', 'Kihei Pacific Plaza', 'Kihei Palms', 'Kihei Parkshore', 'Kihei Resort', 'Kihei Sands', 'Kihei Shores', 'Kihei Surfside', 'Kihei View Apts', 'Kihei Villa', 'Kihei Villages I', 'Kihei Villages II', 'Kihei Villages III', 'Kihei Villages IV', 'Kihei Villages V', 'Kihei Villages VI', 'Kipa Village', 'Koa Kai', 'Koa Lagoon', 'Koa Resort', 'Kuau Plaza', 'Kulakane', 'Kulaview', 'Kuleana I', 'Kuleana II', 'Lahaina Residential', 'Lahaina Roads', 'Lahaina Shores', 'Lanai City Apartments', 'Lanakila', 'Laulea', 'Laulima Lane', 'Lauloa', 'Leilani Kai', 'Leinaala', 'Leinani Apts', 'Lokahi', 'Loke Hale', 'Lokelani', 'Luana Kai', 'Maalaea Banyans', 'Maalaea Kai', 'Maalaea Mermaid', 'Maalaea Surf', 'Maalaea Yacht Marina', 'Mahana', 'Mahina Surf', 'Mahinahina Beach', 'Makani A Kai', 'Makani Sands', 'Makena Place', 'Makena Sunset', 'Makena Surf', 'Mana Kai', 'Masters', 'Maui Banyan', 'Maui Beach Club', 'Maui Beachfront Resort', 'Maui Eldorado I', 'Maui Eldorado II', 'Maui Gardens', 'Maui Hill', 'Maui Isana Resort', 'Maui Kaanapali Villas', 'Maui Kai', 'Maui Kamaole', 'Maui Lani Terraces', 'Maui Ocean Club, Lahaina Tower', 'Maui Parkshore', 'Maui Realty Suites', 'Maui Sands I', 'Maui Sands II', 'Maui Sunset', 'Maui View Villas', 'Maui Vista', 'Mauian Hotel', 'Menehune Shores', 'Milowai', 'Miranda', 'Molokai Beach Cottages', 'Molokai Shores', 'Montage Residences Kapalua Bay', 'Mount Thomas', 'Na Hale O Makena', 'Na Holokai', 'Nani Kai Hale', 'Napili Bay', 'Napili Gardens', 'Napili Lani', 'Napili Point I', 'Napili Point II', 'Napili Puamala', 'Napili Ridge', 'Napili Shores', 'Napili Sunset', 'Napili Surf', 'Napili Village', 'Napili Villas', 'Napilihau Villages I', 'Noelani', 'Nohonani', 'One Konou Place', 'One Napili Way', 'Opukea at Lahaina', 'Pacific Shores', 'Paki Maui I II', 'Paki Maui III', 'Palms at Manele I', 'Palms at Wailea I', 'Paniolo Country Estates', 'Paniolo Hale', 'Papakea Resort I II', 'Papali Wailea', 'Parkview Square', 'Pikake', 'Pines at Koele', 'Pires Place', 'Pohailani Maui', 'Poinciana Place', 'Polo Beach Club', 'Polynesian Shores', 'Puamana', 'Puna II', 'Puna Point I', 'Punahoa Beach Apts', 'Puunoa Beach Estates', 'Puuone Gardens', 'Puuone Hale Alii', 'Puuone Terrace', 'Puuone Towers and Plaza', 'Royal Kahana', 'Royal Maui Kai', 'Royal Mauian', 'Royal Menehune', 'Sands of Kahana', 'Shores of Maui', 'Southpointe at Waiakoa', 'Spinnaker', 'Sugar Beach Resort', 'Sugar Cove', 'Terraces Manele Bay I', 'Terraces Manele Bay II', 'Terraces Manele Bay III', 'Terraces Manele Bay IV', 'Terraces Manele Bay V', 'The Breakers', 'The Ritz Hotel Suites', 'The Suites at Wailea', 'Uluniu Townhouse', 'US Duplex', 'Valley Isle Resort', 'Villas at Kahana Ridge', 'Villas at Kehalani', 'Villas at Kenolio I', 'Villas at Kenolio II', 'Villas at Koele I', 'Villas at Koele II', 'Vintage at Kaanapali', 'Wailana Kai', 'Wailana Sands', 'Wailea Beach Villas', 'Wailea Ekahi I', 'Wailea Ekahi II', 'Wailea Ekahi III', 'Wailea Ekolu', 'Wailea Elua I A', 'Wailea Elua I B', 'Wailea Elua II', 'Wailea Fairway Villas', 'Wailea Palms', 'Wailea Point I II III', 'Wailea Town Center', 'Wailuku Manor', 'Wailuku Townhouses', 'Waiohuli Beach Duplex', 'Waiohuli Beach Hale', 'Waipuilani', 'Walaka Apartments', 'Walaka Maui', 'Wavecrest', 'Wells St Prof Center', 'West Maui Trades', 'West Molokai Resort', 'Whaler I', 'Whaler II', 'White Sea Terrace', '_Residential House', '_Vacant Land']]		
		
		}
	
	//@Test
	public void test5() {
		String path = 'src/test/resources/lists/district.xml'
				String xml = new File(path).text
				println XmlRpcHelper.xmlRpcToCollection(xml)
				//[Haiku', 'Hana', 'Honokohau', 'Kaanapali', 'Kahakuloa', 'Kahului', 'Kapalua', 'Kaupo', 'Keanae', 'Kihei', 'Kipahulu', 'Kula/Ulupalakua/Kanaio', 'Lahaina', 'Lanai', 'Maalaea', 'Makawao/Olinda/Haliimaile', 'Maui Meadows', 'Molokai', 'Nahiku', 'Napili/Kahana/Honokowai', 'Olowalu', 'Pukalani', 'Spreckelsville/Paia/Kuau', 'Wailea/Makena', 'Wailuku']
				
	}
	@Test
	void test6() {
		def url = '''http://www.ramaui.com/Results.php?MLS=329222&WhatStartPrice=&WhatEndPrice=&WhatDistrict%5B%5D=Haiku&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'''
		println url.split("\\?|&")
		def map = UrlHelper.urlToMap(url)
		println map
	}
	
	@Test
	void test7() {
	def list = [132, 1, 7.0, 7794.0, [[MLS:349727, Class:'Residential', Type:0, District:6, ListPrice:(Double)7200000, AddressNumber:1212, AddressDirection:'', AddressStreet:'SUMMER RD', AddressLotUnit:'', City:'Lahaina', State:'HI', Zip:96761, Status:0, WaterFront:2, View:1, AgentID:11521, AgentName:'Roy,Sakamoto', ListingOffice1_ID:214, ListingOffice1_Name:'Sakamoto Properties Limited', ListingOffice1_Phone:6690070, CoListingAgent_ID:1277, ListingOffice2_ID:214, ListingOffice2_Name:'Sakamoto Properties Limited', ListingOffice2_Phone:6690070, ListDate:'2011-10-11 00:00:00 -10:00', Div:2, Zone:4, Sec:2, Plat:003, Par:045, Unit:'']]]//, CPR:0000, HouseCarport:true, HouseGarage:true, OhanaCarport:true, OhanaGarage:true, LT:Fee Simple, PartialOwnership:null, PercentOwnership:0, BuildingName:null, FloorLevel:null, Beds:8, Baths:8.50, LivSQFT:8178, LandSQFT:34626, LandAcres:0.7949, MaintFee:800.00, PublicRemarks:'One of a kind home spread out over 3 lots at the exclusive gated Pineapple Hill at Kapalua subdivision.  Located directly overlooking the 11th green and 12th tee of the world famous Kapalua Bay Course!  Approximately 8,178 interior square feet of luxury living in one of the best resorts in the world, Kapalua!  Designer furnished and ready for immediate occupancy.  8 bedrooms, 8.5 baths ideal for a large family or corporate use.  Magnificent, huge free form pool and outdoor landscaping.  Expansive covered patio areas idea for al fresco living.  Walk in and you are greeted by two unique indoor atria and high ceilings.  Designed by Master Architect Rick Ryniak and built by master builder Gary Dixon.  Short convenient drive to Maui Preparatory Academy, one of Hawaii\'s finest prep schools.  A Lifestyle of Casual Hawaiian Elegance awaits you!!!! Seller will consider trade for a home or condominium plus cash!', Ohana_Beds:0, Ohana_Baths:0.00, Ohana_LivSQFT:0, FixedBeginDate:null, FixedEndDate:null, Res_Condo:false, VOWAddress:Yes, VOWComment:Yes, VOWAVM:Yes, VOWInclude:true, PotentialShortSale:false, REO:false, ListingOffice1_Email:sakamoto@maui.net, CoListingAgent_Email:Betty@SakamotoProperties.com, CoListingAgent_FullName:Betty Sakamoto RB-12644, Lat:20.9926700592041, Lon:-156.659301757812, PoolYN:true, PV_Installed:null, PV_Ownership:]]]
	println XmlRpcHelper.toXmlResponse(list)
	def map = [[a:1, b:2, c:null, d:null]]
	//[{MLS=349727,
//	map.each{if (it.value==null) it.value='!'}
	map.each{ it.each{if (it.value==null) it.value='!'}}
	//def a = map.findAll{ it.value == null }.each { it.value = '' }
	println map
	}
	
	@Test
	void test8(){
		def map = [WhatAgent:['a','b']]
		def whereClause = ''
		whereClause +=  " and a.Identifier in (${ map.'WhatAgent'.join(',')})"
		println whereClause
	}
	
	@Test
	void test9(){
		def xml = '''
<methodResponse><params><param><value><array>
								<data>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>361300</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Condominium</string>
												</value>
											</member>
											<member>
												<name>Status</name>
												<value>
													<string>ACTIVE</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>1700000</double>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>ListDate</name>
												<value>
													<string>2014-07-01</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<string>1441</string>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<string>348916</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>14155</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>5336</string>
												</value>
											</member>
											<member>
												<name>CoListingAgent_ID</name>
												<value>
													<string>14014</string>
												</value>
											</member>
											<member>
												<name>ListingOffice2_ID</name>
												<value>
													<string>8071</string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>50</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Nohea Kai Dr</string>
												</value>
											</member>
											<member>
												<name>AddressLotUnit</name>
												<value>
													<string>2-1002</string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Kaanapali</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string>Kaanapali Alii</string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string>2-1002</string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>1</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>2.00</string>
												</value>
											</member>
											<member>
												<name>PotentialShortSale</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>REO</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>Lat</name>
												<value>
													<string>20.918321609497100</string>
												</value>
											</member>
											<member>
												<name>Lon</name>
												<value>
													<string>-156.695465087891000</string>
												</value>
											</member>
											</struct>
											</value>
											</data>
										</array></value></param></params></methodResponse>

'''

				def array = XmlRpcHelper.xmlRpcToCollection(xml)
				//array[0].keySet().each{ println it}
				println array

	}
	
	@Test
	void test10() {
		def d = ['Haiku', 'Hana', 'Honokohau', 'Kaanapali', 'Kahakuloa', 'Kahului', 'Kapalua', 'Kaupo', 'Keanae', 'Kihei', 'Kipahulu', 'Kula/Ulupalakua/Kanaio', 'Lahaina', 'Lanai', 'Maalaea', 'Makawao/Olinda/Haliimaile', 'Maui Meadows', 'Molokai', 'Nahiku', 'Napili/Kahana/Honokowai', 'Olowalu', 'Pukalani', 'Spreckelsville/Paia/Kuau', 'Wailea/Makena', 'Wailuku']
		d.eachWithIndex{o, i -> println "WHEN $i THEN '$o'"}
	}
	@Test
	void test11() {
		def d = ['MountainOcean','Ocean','Mountain','GolfCourse','GardenView','Other']
		def a = ['Ocean','Other']
		println d.indexOf('GolfCourse')
		println a.collect{d.indexOf(it)}.join(',')
	}
	
	@Test
	void test12() {
def xml = '''
<?xml version="1.0" ?>
<methodResponse>
	<params>
		<param>
			<value>
				<array>
					<data>
						<value>
							<int>1484</int>
						</value>
						<value>
							<int>1</int>
						</value>
						<value>
							<double>297</double>
						</value>
						<value>
							<double>1.7485420703888</double>
						</value>
						<value>
							<array>
								<data>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>363909</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Commercial</string>
												</value>
											</member>
											<member>
												<name>Status</name>
												<value>
													<string>ACTIVE</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>1206</double>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>ListDate</name>
												<value>
													<string>2015-02-05</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<string>693</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>11611</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>9606</string>
												</value>
											</member>
											<member>
												<name>CoListingAgent_ID</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>ListingOffice2_ID</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>270</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Hookahi</string>
												</value>
											</member>
											<member>
												<name>AddressLotUnit</name>
												<value>
													<string>205</string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Wailuku</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>0.00</string>
												</value>
											</member>
											<member>
												<name>PotentialShortSale</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>REO</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>Lat</name>
												<value>
													<string>20.903821000000000</string>
												</value>
											</member>
											<member>
												<name>Lon</name>
												<value>
													<string>-156.484786000000000</string>
												</value>
											</member>
											<member>
												<name>Image</name>
												<value>
													<struct>
														<member>
															<name>Full</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363909-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363909-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363909-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363909-4.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P100</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363909-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363909-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363909-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363909-4.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P200</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363909-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363909-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363909-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363909-4.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P400</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363909-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363909-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363909-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363909-4.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
													</struct>
												</value>
											</member>
										</struct>
									</value>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>363417</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Commercial</string>
												</value>
											</member>
											<member>
												<name>Status</name>
												<value>
													<string>ACTIVE</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>1225</double>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>ListDate</name>
												<value>
													<string>2015-01-05</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<string>10001</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>13326</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>9112</string>
												</value>
											</member>
											<member>
												<name>CoListingAgent_ID</name>
												<value>
													<string>22524</string>
												</value>
											</member>
											<member>
												<name>ListingOffice2_ID</name>
												<value>
													<string>9112</string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>346</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Market St</string>
												</value>
											</member>
											<member>
												<name>AddressLotUnit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Wailuku</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>0.00</string>
												</value>
											</member>
											<member>
												<name>PotentialShortSale</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>REO</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>Lat</name>
												<value>
													<string>20.892672000000000</string>
												</value>
											</member>
											<member>
												<name>Lon</name>
												<value>
													<string>-156.504120000000000</string>
												</value>
											</member>
											<member>
												<name>Image</name>
												<value>
													<struct>
														<member>
															<name>Full</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363417-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363417-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363417-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P100</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363417-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363417-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363417-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P200</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363417-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363417-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363417-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P400</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363417-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363417-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363417-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
													</struct>
												</value>
											</member>
										</struct>
									</value>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>363418</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Commercial</string>
												</value>
											</member>
											<member>
												<name>Status</name>
												<value>
													<string>ACTIVE</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>2462</double>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>ListDate</name>
												<value>
													<string>2015-01-05</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<string>10001</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>13326</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>9112</string>
												</value>
											</member>
											<member>
												<name>CoListingAgent_ID</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>ListingOffice2_ID</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>346</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Market St</string>
												</value>
											</member>
											<member>
												<name>AddressLotUnit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Wailuku</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>0.00</string>
												</value>
											</member>
											<member>
												<name>PotentialShortSale</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>REO</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>Lat</name>
												<value>
													<string>20.892672000000000</string>
												</value>
											</member>
											<member>
												<name>Lon</name>
												<value>
													<string>-156.504120000000000</string>
												</value>
											</member>
											<member>
												<name>Image</name>
												<value>
													<struct>
														<member>
															<name>Full</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363418-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363418-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363418-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P100</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363418-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363418-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363418-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P200</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363418-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363418-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363418-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P400</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363418-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363418-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363418-3.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
													</struct>
												</value>
											</member>
										</struct>
									</value>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>363922</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Fraction-Partial-Interval</string>
												</value>
											</member>
											<member>
												<name>Status</name>
												<value>
													<string>ACTIVE</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>2500</double>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>ListDate</name>
												<value>
													<string>2015-02-09</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<string>597</string>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<string>535047</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>12798</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>8071</string>
												</value>
											</member>
											<member>
												<name>CoListingAgent_ID</name>
												<value>
													<string>24710</string>
												</value>
											</member>
											<member>
												<name>ListingOffice2_ID</name>
												<value>
													<string>8071</string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>3543</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Lower Honoapiilani RD</string>
												</value>
											</member>
											<member>
												<name>AddressLotUnit</name>
												<value>
													<string>B-309 10B</string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Napili/Kahana/Honokowai</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string>Papakea Resort I II</string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string>B-309 10B</string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>1</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>1.00</string>
												</value>
											</member>
											<member>
												<name>PotentialShortSale</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>REO</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>Lat</name>
												<value>
													<string>20.950944900512700</string>
												</value>
											</member>
											<member>
												<name>Lon</name>
												<value>
													<string>-156.688674926758000</string>
												</value>
											</member>
											<member>
												<name>Image</name>
												<value>
													<struct>
														<member>
															<name>Full</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-10.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-11.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363922-12.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P100</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-10.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-11.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363922-12.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P200</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-10.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-11.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363922-12.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P400</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-10.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-11.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363922-12.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
													</struct>
												</value>
											</member>
										</struct>
									</value>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>363255</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Commercial</string>
												</value>
											</member>
											<member>
												<name>Status</name>
												<value>
													<string>ACTIVE</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>2500</double>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>ListDate</name>
												<value>
													<string>2014-12-17</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<string>9148</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>14145</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>9670</string>
												</value>
											</member>
											<member>
												<name>CoListingAgent_ID</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>ListingOffice2_ID</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>1923A</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Vineyard St</string>
												</value>
											</member>
											<member>
												<name>AddressLotUnit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Wailuku</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>0</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>0.00</string>
												</value>
											</member>
											<member>
												<name>PotentialShortSale</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>REO</name>
												<value>
													<string>N</string>
												</value>
											</member>
											<member>
												<name>Lat</name>
												<value>
													<string>20.890144184231800</string>
												</value>
											</member>
											<member>
												<name>Lon</name>
												<value>
													<string>-156.500940173864000</string>
												</value>
											</member>
											<member>
												<name>Image</name>
												<value>
													<struct>
														<member>
															<name>Full</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/maui363255-10.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P100</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/100/maui363255-10.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P200</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/200/maui363255-10.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P400</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-9.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/363000/400/maui363255-10.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
													</struct>
												</value>
											</member>
										</struct>
									</value>
								</data>
							</array>
						</value>
						<value>
							<string>&lt;div style=&quot;font-size:10px&quot;&gt;This information is believed to be accurate.&lt;br /&gt;It has been provided by sources other than the Realtors Assoc. of Maui&lt;br /&gt;and should not be relied upon without independent verification.&lt;/div&gt;
   &lt;div style=&quot;font-size:10px&quot;&gt;You should conduct your own investigation and consult&lt;br /&gt;with appropriate professionals to determine the accuracy of the information provided&lt;br /&gt;and to answer any questions concerning the property and structures located thereon.&lt;/div&gt;
   &lt;div style=&quot;font-size:10px&quot;&gt;Featured properties may or may not be listed by the office/agent presenting this brochure.&lt;/div&gt;
   &lt;div style=&quot;font-size:10px;padding-top:10px;&quot;&gt;Copyright, 1995-2014, REALTORS&lt;sup&gt;&amp;reg;&lt;/sup&gt; Association of Maui, Inc. All Rights Reserved.&lt;/div&gt;</string>
						</value>
					</data>
				</array>
			</value>
		</param>
	</params>
</methodResponse>
'''
//def a = 
def response = new XmlSlurper().parseText(xml.replace('''<?xml version="1.0" ?>''', ''))
		response.params.param.value.array.data.value*.each{ x ->
			x.array.data.value*.struct.member*.each { y ->
				 if(y.name == 'Image') y.replaceNode {}
			}
		}
		//response.params.param.value.array.data.value*.array.data.value*.each {println it}
		
		
//		def list = response.depthFirst().find{it.name == 'Image'}
//		response.'**'.findAll { it.name() == 'Image' }.each {
//			println it
//		  }
//		list = response.'*'.find{it.name == 'Image'}
		//def list = response.params.param.value*.array.data.value*.struct.member.find{it.name() == 'Image'; println it}
//		list.replaceNode {}
		//def list = response.params.param.value.array.data.value[4].array.data.value*.struct.member
//		for(l in list){
//			l.each{ if(it.name == 'Image') it.replaceNode {}}
//		}

def noImageXml = XmlUtil.serialize(new StreamingMarkupBuilder().bind { mkp.yield response} )
println noImageXml








//def list = response.params.param.value.array.data.value[4].array.data.value*.struct.member
//for(l in list){
//	l.each{ if(it.name == 'Image') it.replaceNode {}}
//	
//}
////println list
//def noImageXml = XmlUtil.serialize(new StreamingMarkupBuilder().bind { mkp.yield response} )
//println noImageXml
//
}
	@Test
	public void test13(){
		Double numberOfPages = new BigDecimal(1/200 + 0.5, new MathContext(1, java.math.RoundingMode.UP))
		println numberOfPages.round() as Double
	}
	
	@Test
	void test14() {
		//def arr = new ArrayList<Object>(10).withEagerDefault{new Object() }//.each{it = new Object()}	
//		arr.withEagerDefault {it = "*"}	
//		int a = 10
//		def arr = new Object[a] 
		
//		def arr = []
//		arr.each{it = "*"}
//		(1..4).each{
//			arr.add( new Object())
//		}
		//arr.eachWithIndex {idx -> arr[idx] = new Object()}
		def arr = [].withDefault{new Object()}
		//100.times{ println it; arr.add(new Object())}
		100.times{arr.get(it)}
		//(0..3).eachWithIndex{idx ->
//		for
//			arr.get(0)
//		//}
			
		
//		arr.ensureCapacity(200)
		println arr.size()
		println arr
		
	}
	@Test
	void test15() {
	    int numberOfListings = 310
		int pageNumber = 1		
		int pageSize = 200
		def numberOfPages = (numberOfListings<pageSize)?1:new BigDecimal(numberOfListings/pageSize, new MathContext(1, java.math.RoundingMode.UP))
		def buff =  pageSize - numberOfListings > 0 ?pageSize - numberOfListings: 0
		println buff
	}
	@Test
	void test16() {
		//RamSqlService.DISTRICT_LIST.eachWithIndex {o,i -> println "update Enum_District set LegacyName = '$o' where id = $i;"}
		['ACTIVE',
		'CNTNGNTWRELEASE',
		'CNTNGNTESCROWCANCELING',
		'SOLD',
		'PENDINGDONOTSHOW',
		'PENDINGCONTTOSHOW',
		'EXPIRED',
		'WITHDRAWN',
		'TEMPOFFMARKET',
		'CANCELED'].eachWithIndex {o,i -> println "update Enum_Status set LegacyName = '$o' where id = $i;"}
	}
	
	@Test
	void test17() {
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		Double d = Double.parseDouble("123.07800");
		String s = twoDForm.format(d);
		println(s);
		
		println "123.0".replace('''.0*$''', "");
		s = '10.0007000'
		
		println '17.3380080000'.replaceAll(/0*$/, "")
			
	}
	
	@Test
	void test18(){
		def xml = '''
				<methodResponse><params><param><value><array>
				<data>
									<value>
										<struct>
											<member>
												<name>MLS</name>
												<value>
													<string>360701</string>
												</value>
											</member>
											<member>
												<name>Class</name>
												<value>
													<string>Condominium</string>
												</value>
											</member>
											<member>
												<name>District</name>
												<value>
													<string>Molokai</string>
												</value>
											</member>
											<member>
												<name>ClosingDate</name>
												<value>
													<string>2014-08-22</string>
												</value>
											</member>
											<member>
												<name>AgentID</name>
												<value>
													<string>13872</string>
												</value>
											</member>
											<member>
												<name>ListingOffice1_ID</name>
												<value>
													<string>9571</string>
												</value>
											</member>
											<member>
												<name>ListPrice</name>
												<value>
													<double>95000</double>
												</value>
											</member>
											<member>
												<name>SoldPrice</name>
												<value>
													<string>87500</string>
												</value>
											</member>
											<member>
												<name>VOWAddress</name>
												<value>
													<string>Yes</string>
												</value>
											</member>
											<member>
												<name>AddressNumber</name>
												<value>
													<string>50</string>
												</value>
											</member>
											<member>
												<name>AddressDirection</name>
												<value>
													<string></string>
												</value>
											</member>
											<member>
												<name>AddressStreet</name>
												<value>
													<string>Kepuhi PL</string>
												</value>
											</member>
											<member>
												<name>Beds</name>
												<value>
													<string>1</string>
												</value>
											</member>
											<member>
												<name>Baths</name>
												<value>
													<string>1.00</string>
												</value>
											</member>
											<member>
												<name>LT</name>
												<value>
													<string>Fee Simple</string>
												</value>
											</member>
											<member>
												<name>LivSQFT</name>
												<value>
													<double>681</double>
												</value>
											</member>
											<member>
												<name>LandSQFT</name>
												<value>
													<double>652093</double>
												</value>
											</member>
											<member>
												<name>View</name>
												<value>
													<string>Other</string>
												</value>
											</member>
											<member>
												<name>WaterFront</name>
												<value>
													<string>OceanFront</string>
												</value>
											</member>
											<member>
												<name>BuildingName</name>
												<value>
													<string>Ke Nani Kai</string>
												</value>
											</member>
											<member>
												<name>Unit</name>
												<value>
													<string>260</string>
												</value>
											</member>
											<member>
												<name>MaintFee</name>
												<value>
													<string>555.00</string>
												</value>
											</member>
											<member>
												<name>Images</name>
												<value>
													<struct>
														<member>
															<name>Full</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/maui360701-9.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P100</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/100/maui360701-9.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P200</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/200/maui360701-9.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
														<member>
															<name>P400</name>
															<value>
																<array>
																	<data>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-1.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-2.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-3.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-4.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-5.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-6.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-7.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-8.jpg</string>
																		</value>
																		<value>
																			<string>http://www.ramidx.com/MLSImages/360000/400/maui360701-9.jpg</string>
																		</value>
																	</data>
																</array>
															</value>
														</member>
													</struct>
												</value>
											</member>
											<member>
												<name>Ohana_LivSQFT</name>
												<value>
													<double>0</double>
												</value>
											</member>
										</struct>
									</value>
								</data>	
</array></value></param></params></methodResponse>
				
				'''
				
				def array = XmlRpcHelper.xmlRpcToCollection(xml)
				array[0].keySet().each{ println it}
				println array
				
	}
	
	@Test
	void test19() {
		def a = 'b'
		println ''' 123  ''' << (
			  (a == 'a') ? 'a' : (a == 'c')?:'c' 
	  ) <<''' rest '''
	}
	
	@Test
	void test20() {
		println new SimpleDateFormat("yyyy-MM-dd hh:mm:ss XXX").format(new Date())
		TimeZone.setDefault(TimeZone.getTimeZone('GMT-1000'))
		def now = new Date()
		println now.format("yyyy-MM-dd HH:mm:ss XXX")
		
		
	}
	 
	@Test
	void test21() {
		//def a = "Now.     Ã¢Â€Â¢ HaikuÃ¢Â€Â™s largest neighborhood"
		//def b = "Now.     &amp;bull; Haiku&amp;rsquo;s largest neighborhood"
	    //<string>For Lease ~ Retail / Office / Warehouse Suites Available Now.     Ã¢Â€Â¢ HaikuÃ¢Â€Â™s largest neighborhood shopping center anchored by a grocery store, hardware store, and restaurants.      Ã¢Â€Â¢ High traffic and visibility with ample parking.      Ã¢Â€Â¢ Convenient stop on the Ã¢Â€ÂœMaui BusÃ¢Â€Â&#157; route.    Ã¢Â€Â¢ Rates starting at $1.00 - $2.00 psf/month plus CAM and GET.    Location: Haiku Marketplace   810 Haiku Road   Haiku, Maui, HI 96708      Tax Map Key #: (2)-2-7-11-50      Building Size: 86,510 Square Feet approximately      Available: 680 SF Ã¢Â€Â“ 2,750 SF      Description: Multi-building warehouse and retail/office complex, featuring high-ceiling warehouses with bay doors and built-out interior offices.      Opportunity: Affordable and customizable retail and warehouse suites at HaikuÃ¢Â€Â™s busiest retail center.      Parking: Ample common parking for employees and customers.      Lease Rate:   Unit 107 1,400 SF $2.00 psf/month   Unit 157A 680 SF $2.00 psf/month   Unit 157 930 SF $2.00 psf/month   Unit 157B 930 SF $2.00 psf/month   Unit 202 536 SF $1.55 psf/month   Unit 209 2,750 SF $1.55 psf/month    Unit 324 1,800 SF $1.00 psf/month   Unit 359 1,200 SF $1.00 psf/month      Operating Cost: Warehouse CAM: $0.48 psf/month plus GET.     Retail CAM: $0.78 psf/month plus GET.     Remarks: Call now for low rates with a 3-5 year lease term.</string>
		def c ='Ã¢Â€Â¢ Haiku'
//		for (Character chr in c) {
//			println Character.UnicodeBlock.of(chr) 
////			println Character.getType(chr)
//			//ISO 8859-1
//			
//			println chr.toString()
//		}
		
		
		
		
//		ByteBuffer inputBuffer = ByteBuffer.wrap(c.getBytes());
//		CharBuffer charBuffer = Charset.forName("UTF-8").decode(inputBuffer) // decode UTF-8
//		ByteBuffer outputBuffer = Charset.forName("WINDOWS-1252").encode(charBuffer) // encode ISO-8559-15
//		def o = outputBuffer.array()
//		println o

//		println "Ã¢Â€Â¢ Haiku : &amp;bull; Haiku"
//		println "&amp;bull; Haiku".getBytes("ISO-8859-1")
//		println "&amp;bull; Haiku".getBytes("WINDOWS-1252")
//		println "Ã¢Â€Â¢ Haiku".getBytes("ISO-8859-8")
		//println htmlEncode("Ã¢Â€Â¢ Haiku ")
		println htmlEncode("&• Haiku ")
		println '&• Haiku'.getBytes("UTF-8")
		println StringEscapeUtils.unescapeHtml("&amp;bull; Haiku")//.getBytes()
		println StringEscapeUtils.escapeJavaScript("Ã¢Â€Â¢ Haiku : &amp;bull; Haiku")//.getBytes()
		println StringEscapeUtils.unescapeJavaScript("Ã¢Â€Â¢ Haiku : &amp;bull; Haiku")//.getBytes()
		
		
		def latin = c.getBytes("ISO-8859-1")
		def utf8 = c.getBytes("UTF-8")	
		//def "&•"
		println "latin : " + latin	+ " " + new String(latin)
		println "utf8 : " + utf8
			
		def s = new String(latin)
		println 'latin to utf8 : \n' + s.getBytes("ISO-8859-1")
		println StringEscapeUtils.escapeHtml(s)//.getBytes("ISO-8859-1")
		println StringEscapeUtils.escapeHtml(s).getBytes()
		println StringEscapeUtils.unescapeHtml(s)//.getBytes("ISO-8859-1")
		println StringEscapeUtils.unescapeHtml(s)//.getBytes("UTF-8")
		println htmlEncode(s)//.getBytes("ISO-8859-1")
		println htmlEncode(s).getBytes("UTF-8")
		println "-------"
		
//		s = new String(utf8, "ISO-8859-1")
//		println 'utf8 to latin : ' + s.getBytes("UTF-8")
//		println StringEscapeUtils.escapeHtml(s)
//		println StringEscapeUtils.unescapeHtml(s)
//		println htmlEncode(s)
//		println "-------"
		

		//byte[] utf8 = new String(latin1, "ISO-8859-1").getBytes("UTF-8").to;
//		println latin1
//		//byte[] utf8 = new String(latin1, "ISO-8859-1").getBytes("UTF-8").to;
//		
//		byte[] utf8 = a.getBytes("UTF-8")
//		println utf8
//		//println new String(latin1, "UTF-8")
//		String f = new String(latin1, "ISO-8859-1")
//		println StringEscapeUtils.escapeHtml(f)
//		println htmlEncode(f)
	}
	@Test
	void test22() {
		def a ='Ã¢Â€Â¢'// Ã¢Â€Â¢ HaikuÃ¢Â€Â™s = • Haiku’s 
		//a ="¢"
		a ="Â¢"
		//a ="™"
		def c =	'•'// Haiku'	
		c =	'’'// Haiku'	
//		println a.getBytes("UTF-8")		
//		println a.getBytes("ISO-8859-15")		
//		println a.getBytes("ISO-8859-1")		
//		println a.getBytes("WINDOWS-1252")
		//println a.getBytes(StandardCharsets.UTF_16)
	byte[] someUtf8Bytes = a.getBytes(StandardCharsets.UTF_8)
	println someUtf8Bytes
	String decoded = new String(someUtf8Bytes, StandardCharsets.UTF_16);
	println decoded + decoded.size()
	byte[] someIso15Bytes = decoded.getBytes(StandardCharsets.UTF_16);
	println new String(decoded.getBytes("ISO-8859-15"))
	println someIso15Bytes
	byte[] someCp863Bytes = decoded.getBytes("cp863");
	println someCp863Bytes	
	
	
	decoded = new String(a.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_16);
	println decoded + ' :' +  decoded.size()
	decoded = new String(a.getBytes(StandardCharsets.UTF_16), StandardCharsets.UTF_8);
 	println decoded + ' :' +  decoded.size()
 	decoded = new String(a.getBytes(StandardCharsets.UTF_16), StandardCharsets.ISO_8859_1);
 	println decoded + ' :' +  decoded.size()
 	decoded = new String(a.getBytes(StandardCharsets.US_ASCII), StandardCharsets.UTF_16);
	 
 	println decoded + ' :' +  decoded.size() + ', ' + decoded.getBytes()
	println Character.codePointAt(decoded, 0)
	String z = new String(a.getBytes(StandardCharsets.UTF_8),)
	
 	//0b11100010:10000000:10100010
	Integer l = 0b111000101000000010100010
	println l
	l = 0b11100010_10000000_10100010
	println 0b11100010
	println 0b10000000
	println 0b10100010
	
	
//		println new String(a.getBytes(StandardCharsets.ISO_8859_1), ,StandardCharsets.UTF_8)
//		println a.getBytes(StandardCharsets.ISO_8859_1)
//		println new String(a.getBytes(StandardCharsets.ISO_8859_1), ,StandardCharsets.UTF_8)
//		println a.getBytes(StandardCharsets.UTF_16)
//		println new String(a.getBytes(StandardCharsets.UTF_16), ,StandardCharsets.UTF_8)
//		println a.getBytes(StandardCharsets.UTF_16BE)
//		println a.getBytes(StandardCharsets.UTF_16LE)
//	  	println a.getBytes(StandardCharsets.UTF_8)	
////		println a.getBytes(StandardCharsets.US_ASCII)
//		println new String(a.getBytes(StandardCharsets.UTF_16BE), StandardCharsets.ISO_8859_1)
//		println new String(a.getBytes(StandardCharsets.UTF_16BE), StandardCharsets.ISO_8859_1).getBytes()
//		println StringEscapeUtils.escapeHtml(a)
//		println	""
//		println c.getBytes(StandardCharsets.ISO_8859_1)
//		println c.getBytes(StandardCharsets.UTF_16)
//		println c.getBytes(StandardCharsets.UTF_16BE)
//		println c.getBytes(StandardCharsets.UTF_16LE)
//		println c.getBytes(StandardCharsets.UTF_8)
//		println StringEscapeUtils.escapeHtml(c)
//		println new String(c.getBytes(StandardCharsets.UTF_16BE), StandardCharsets.ISO_8859_1)
//		println new String(c.getBytes(StandardCharsets.UTF_16BE), StandardCharsets.ISO_8859_1).getBytes()
		
//		//println htmlEncode(c)
//		println StringEscapeUtils.escapeHtml(c)//.getBytes()
//		println StringEscapeUtils.escapeHtml(a)//.getBytes()
		
		//byte[] utf8 = new String(c.getBytes("ISO-8859-1"), "ISO-8859-1").getBytes("UTF-8");
//		def x = new String(a.getBytes("ISO-8859-1"), "UTF-8")//.getBytes("UTF-8");
//		println x
//		println htmlEncode(new String (a.getBytes("ISO-8859-1")))//.getBytes("UTF-8");
//		
//		x = new String(a.getBytes("UTF-8"), "ISO-8859-1")//.getBytes("UTF-8");
//		println x
//		println new String(x.getBytes("UTF-16"), "ISO-8859-1")//.getBytes("UTF-8");
		//println new String(c.getBytes("ISO-8859-15"), "UTF-8")//.getBytes("UTF-8");
				
	}
	
	private String htmlEncode( String string) {
		final StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
		  final Character character = string.charAt(i);
		  if (CharUtils.isAscii(character)) {
			// Encode common HTML equivalent characters
			stringBuffer.append(
				StringEscapeUtils.escapeHtml(character.toString()));
		  } else {
			// Why isn't this done in escapeHtml4()?
			stringBuffer.append(
				String.format("&#x%x;",Character.codePointAt(string, i)));
			//String.format("&#h%h;",Character.codePointAt(string, i)));
		  }
		}
		return stringBuffer.toString();
	  }
	//For Lease ~ Retail / Office / Warehouse Suites Available Now.     • Haiku’s largest neighborhood shopping center anchored by a grocery store, hardware store, and restaurants.      • High traffic and visibility with ample parking.      • Convenient stop on the “Maui Bus” route.    • Rates starting at $1.00 - $2.00 psf/month plus CAM and GET.    Location: Haiku Marketplace   810 Haiku Road   Haiku, Maui, HI 96708      Tax Map Key #: (2)-2-7-11-50      Building Size: 86,510 Square Feet approximately      Available: 680 SF – 2,750 SF      Description: Multi-building warehouse and retail/office complex, featuring high-ceiling warehouses with bay doors and built-out interior offices.      Opportunity: Affordable and customizable retail and warehouse suites at Haiku’s busiest retail center.      Parking: Ample common parking for employees and customers.      Lease Rate:   Unit 107 1,400 SF $2.00 psf/month   Unit 157A 680 SF $2.00 psf/month   Unit 157 930 SF $2.00 psf/month   Unit 157B 930 SF $2.00 psf/month   Unit 202 536 SF $1.55 psf/month   Unit 209 2,750 SF $1.55 psf/month    Unit 324 1,800 SF $1.00 psf/month   Unit 359 1,200 SF $1.00 psf/month      Operating Cost: Warehouse CAM: $0.48 psf/month plus GET.     Retail CAM: $0.78 psf/month plus GET.     Remarks: Call now for low rates with a 3-5 year lease term.
}
