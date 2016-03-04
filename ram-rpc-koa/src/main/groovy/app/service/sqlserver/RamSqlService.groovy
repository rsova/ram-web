package app.service.sqlserver

import java.math.MathContext
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.service.sqlserver.helper.SqlHelper

@Service
class RamSqlService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Deprecated
	final static String AGENT_SQL = "SELECT UserCode, FullName FROM Agent where Active = 'true' ORDER BY LastName ASC";
	@Deprecated
	final static String OFFICE_SQL = "SELECT MainOfficeAbbreviation, MainOfficeName FROM Office ORDER BY MainOfficeName ASC";
	@Deprecated
	final static String DISTRICT_SQL = "SELECT Name FROM dbo.Enum_District ORDER BY Id ASC";
	@Deprecated
	final static int SQL = 0
	@Deprecated
	final static int LISTINGS = 1
	@Deprecated
	final static List CONDO_LIST = ['515 Liholiho Street', '71 Miner Place', '85 Walaka Street', 'Aina o Kane', 'aina-nalu', 'Alaeloa', 'Aloha Pualani', 'Andaz Residences', 'Auhana Kuleana', 'Awihi Townhouse', 'Bay Vista Apts', 'Boardwalk', 'Carmel Apts', 'Chandelier Center', 'Channel House', 'Coconut Grove', 'Cottages at Kulamalu', 'Gardens at West Maui', 'Gardens Upcountry', 'Grand Champions', 'Hale Hui Kai', 'Hale Iliili', 'Hale Kai I', 'Hale Kai O Kihei I', 'Hale Kai O Kihei II', 'Hale Kamaole', 'Hale Kanani', 'Hale Mahialani', 'Hale Mahina', 'Hale Nani', 'Hale Napili Apts', 'Hale Ono Loa', 'Hale Pau Hana', 'Hale Royale', 'Hale Wailana', 'Haleakala Gardens', 'Haleakala Shores', 'Hana Kai-Maui', 'Happy Valley Village', 'Harbor Lights', 'Hawealani Condominium', 'Hokulani Golf Villas', 'Hololani', 'Hono Kai', 'Hono Koa', 'Honokeana Cove', 'Honokowai East', 'Honokowai Palms', 'Honua Kai - Hoku Lani', 'Honua Kai - Konea', 'Hoolea Terrace at Kehalani', 'Hoolei', 'Hotel Hana-Maui Condominiums', 'Hotel Molokai', 'Hoyochi Nikko', 'Iao Gardens', 'Iao Parkside I', 'Iao Parkside II', 'Iao Parkside III', 'Iao Parkside IV-A', 'Iao Parkside IV-B', 'Iao Parkside IV-C', 'Iliahi at Kehalani', 'International Colony Club I', 'International Colony Club II', 'Island Sands', 'Island Surf', 'Kaanapali Alii', 'Kaanapali Beach Club', 'Kaanapali Beach Vacation Resort', 'Kaanapali Plantation', 'Kaanapali Royal', 'Kaanapali Shores', 'Kahana Beach Resort', 'Kahana Falls', 'Kahana Manor', 'Kahana Outrigger', 'Kahana Reef', 'Kahana Sunset', 'Kahana Villa', 'Kahana Village', 'Kahekili Grove', 'Kahului Ikena', 'Kai Ani Village', 'Kai Makani', 'Kai Malu', 'Kalama Gardens', 'Kalama Terrace', 'Kalama Townhouses', 'Kalama Villa', 'Kaleialoha', 'Kamaole Beach Club', 'Kamaole Beach Royale', 'Kamaole Nalu', 'Kamaole One', 'Kamaole Sands', 'Kamoa Views', 'Kanai A Nalu', 'Kanani Wailea', 'Kane', 'Kanoe Apts', 'Kanoe Palms', 'Kanoelani Apts', 'Kapalua Bay Villas I', 'Kapalua Bay Villas II', 'Kapalua Golf Villas', 'Kapalua Ironwoods', 'Kapalua Ridge', 'Kapu Townhouses', 'Kauhale Makai', 'Ke Alii Ocean Villas', 'Ke Nani Kai', 'Kealaloa Court', 'Kealia', 'Keawakapu', 'Kehalani Gardens', 'Keonekai Villages', 'Kihei Akahi', 'Kihei Alii Kai', 'Kihei Bay Surf', 'Kihei Bay Vista', 'Kihei Beach', 'Kihei Cove', 'Kihei Garden Estates', 'Kihei Holiday', 'Kihei Islana', 'Kihei Kai', 'Kihei Kai Nani', 'Kihei Manor', 'Kihei Pacific Plaza', 'Kihei Palms', 'Kihei Parkshore', 'Kihei Resort', 'Kihei Sands', 'Kihei Shores', 'Kihei Surfside', 'Kihei View Apts', 'Kihei Villa', 'Kihei Villages I', 'Kihei Villages II', 'Kihei Villages III', 'Kihei Villages IV', 'Kihei Villages V', 'Kihei Villages VI', 'Kipa Village', 'Koa Kai', 'Koa Lagoon', 'Koa Resort', 'Kuau Plaza', 'Kulakane', 'Kulaview', 'Kuleana I', 'Kuleana II', 'Lahaina Residential', 'Lahaina Roads', 'Lahaina Shores', 'Lanai City Apartments', 'Lanakila', 'Laulea', 'Laulima Lane', 'Lauloa', 'Leilani Kai', 'Leinaala', 'Leinani Apts', 'Lokahi', 'Loke Hale', 'Lokelani', 'Luana Kai', 'Maalaea Banyans', 'Maalaea Kai', 'Maalaea Mermaid', 'Maalaea Surf', 'Maalaea Yacht Marina', 'Mahana', 'Mahina Surf', 'Mahinahina Beach', 'Makani A Kai', 'Makani Sands', 'Makena Place', 'Makena Sunset', 'Makena Surf', 'Mana Kai', 'Masters', 'Maui Banyan', 'Maui Beach Club', 'Maui Beachfront Resort', 'Maui Eldorado I', 'Maui Eldorado II', 'Maui Gardens', 'Maui Hill', 'Maui Isana Resort', 'Maui Kaanapali Villas', 'Maui Kai', 'Maui Kamaole', 'Maui Lani Terraces', 'Maui Ocean Club, Lahaina Tower', 'Maui Parkshore', 'Maui Realty Suites', 'Maui Sands I', 'Maui Sands II', 'Maui Sunset', 'Maui View Villas', 'Maui Vista', 'Mauian Hotel', 'Menehune Shores', 'Milowai', 'Miranda', 'Molokai Beach Cottages', 'Molokai Shores', 'Montage Residences Kapalua Bay', 'Mount Thomas', 'Na Hale O Makena', 'Na Holokai', 'Nani Kai Hale', 'Napili Bay', 'Napili Gardens', 'Napili Lani', 'Napili Point I', 'Napili Point II', 'Napili Puamala', 'Napili Ridge', 'Napili Shores', 'Napili Sunset', 'Napili Surf', 'Napili Village', 'Napili Villas', 'Napilihau Villages I', 'Noelani', 'Nohonani', 'One Konou Place', 'One Napili Way', 'Opukea at Lahaina', 'Pacific Shores', 'Paki Maui I II', 'Paki Maui III', 'Palms at Manele I', 'Palms at Wailea I', 'Paniolo Country Estates', 'Paniolo Hale', 'Papakea Resort I II', 'Papali Wailea', 'Parkview Square', 'Pikake', 'Pines at Koele', 'Pires Place', 'Pohailani Maui', 'Poinciana Place', 'Polo Beach Club', 'Polynesian Shores', 'Puamana', 'Puna II', 'Puna Point I', 'Punahoa Beach Apts', 'Puunoa Beach Estates', 'Puuone Gardens', 'Puuone Hale Alii', 'Puuone Terrace', 'Puuone Towers and Plaza', 'Royal Kahana', 'Royal Maui Kai', 'Royal Mauian', 'Royal Menehune', 'Sands of Kahana', 'Shores of Maui', 'Southpointe at Waiakoa', 'Spinnaker', 'Sugar Beach Resort', 'Sugar Cove', 'Terraces Manele Bay I', 'Terraces Manele Bay II', 'Terraces Manele Bay III', 'Terraces Manele Bay IV', 'Terraces Manele Bay V', 'The Breakers', 'The Ritz Hotel Suites', 'The Suites at Wailea', 'Uluniu Townhouse', 'US Duplex', 'Valley Isle Resort', 'Villas at Kahana Ridge', 'Villas at Kehalani', 'Villas at Kenolio I', 'Villas at Kenolio II', 'Villas at Koele I', 'Villas at Koele II', 'Vintage at Kaanapali', 'Wailana Kai', 'Wailana Sands', 'Wailea Beach Villas', 'Wailea Ekahi I', 'Wailea Ekahi II', 'Wailea Ekahi III', 'Wailea Ekolu', 'Wailea Elua I A', 'Wailea Elua I B', 'Wailea Elua II', 'Wailea Fairway Villas', 'Wailea Palms', 'Wailea Point I II III', 'Wailea Town Center', 'Wailuku Manor', 'Wailuku Townhouses', 'Waiohuli Beach Duplex', 'Waiohuli Beach Hale', 'Waipuilani', 'Walaka Apartments', 'Walaka Maui', 'Wavecrest', 'Wells St Prof Center', 'West Maui Trades', 'West Molokai Resort', 'Whaler I', 'Whaler II', 'White Sea Terrace', '_Residential House', '_Vacant Land']
//	final static List DISTRICT_LIST =['Haiku', 'Hana', 'Honokohau', 'Kaanapali', 'Kahakuloa', 'Kahului', 'Kapalua', 'Kaupo', 'Keanae', 'Kihei', 'Kipahulu', 'Kula/Ulupalakua/Kanaio', 'Lahaina', 'Lanai', 'Maalaea', 'Makawao/Olinda/Haliimaile', 'Maui Meadows', 'Molokai', 'Nahiku', 'Napili/Kahana/Honokowai', 'Olowalu', 'Pukalani', 'Spreckelsville/Paia/Kuau', 'Wailea/Makena', 'Wailuku']
//	final static List VIEW_LIST = ['MountainOcean','Ocean','Mountain','GolfCourse','GardenView','Other']
//	final static List WATER_LIST = ['OceanFront', 'BeachFront', 'Across Street from Ocean']
//    final static List PROPERTY_LIST = ['OceanFront', 'BeachFront', 'Across Street from Ocean']

	private static final String TOTAL_CNT = '_Total'
	
	private static final String PAGE_NUM_KEY = 'WhatPage'
	
	private static final String PAGE_SIZE_KEY = 'WhatNumber'
	
//	private static final String LEGAL = '''&lt;div style=&quot;font-size:10px&quot;&gt;This information is believed to be accurate.&lt;br /&gt;It has been provided by sources other than the Realtors Assoc. of Maui&lt;br /&gt;and should not be relied upon without independent verification.&lt;/div&gt;
//   &lt;div style=&quot;font-size:10px&quot;&gt;You should conduct your own investigation and consult&lt;br /&gt;with appropriate professionals to determine the accuracy of the information provided&lt;br /&gt;and to answer any questions concerning the property and structures located thereon.&lt;/div&gt;
//   &lt;div style=&quot;font-size:10px&quot;&gt;Featured properties may or may not be listed by the office/agent presenting this brochure.&lt;/div&gt;
//   &lt;div style=&quot;font-size:10px;padding-top:10px;&quot;&gt;Copyright, 1995-2014, REALTORS&lt;sup&gt;&amp;reg;&lt;/sup&gt; Association of Maui, Inc. All Rights Reserved.&lt;/div&gt;'''

	public static final String LEGAL = '''<div style="font-size:10px">This information is believed to be accurate.<br />It has been provided by sources other than the Realtors Assoc. of Maui<br />and should not be relied upon without independent verification.</div>
	   <div style="font-size:10px">You should conduct your own investigation and consult<br />with appropriate professionals to determine the accuracy of the information provided<br />and to answer any questions concerning the property and structures located thereon.</div>
	   <div style="font-size:10px">Featured properties may or may not be listed by the office/agent presenting this brochure.</div>
	   <div style="font-size:10px;padding-top:10px;">Copyright, 1995-2014, REALTORS<sup>&reg;</sup> Association of Maui, Inc. All Rights Reserved.</div>''';
	
   @Deprecated
   final static Map lists = [
	   'agent'		: new Marker(SQL, AGENT_SQL), 
	   'office'		: new Marker(SQL, OFFICE_SQL), 
	   'condo'		: new Marker(LISTINGS, CONDO_LIST), 
	   'district'	: new Marker(SQL, DISTRICT_SQL,true)
    ];

	@Deprecated
	public List<Map<String, Object>> getList(String method){

		List responseList = []
		def responseName = method.capitalize()
		def start = Date.getMillisOf(new Date())

		Marker load = lists.get(method)
		if(load.type == LISTINGS){
			responseList = load.content
		}else if(load.type == SQL){
			println load.content
			def list = jdbcTemplate.queryForList(load.content)
			responseList = list*.values()
		}

		double time = (Date.getMillisOf(new Date()) - start)
		return [responseName,time, load.flatten ? responseList.flatten() : responseList]
	}
	
	@Deprecated
	public List<Map<String, Object>> search(Map params){
		def sql = SqlHelper.searchSqlFromRpcParamMap(params)
		println sql
		def start = Date.getMillisOf(new Date())
		List list = jdbcTemplate.queryForList(sql)
		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
		
	    int numberOfListings = list.isEmpty() ? 0 : list?.get(0)?.getAt(TOTAL_CNT)
		int pageNumber = (params[PAGE_NUM_KEY]?:'1').toInteger()		
		int pageSize = (params[PAGE_SIZE_KEY]?:'20').toInteger()
		def numberOfPages = (numberOfListings<pageSize)?1:new BigDecimal(numberOfListings/pageSize, new MathContext(1, java.math.RoundingMode.UP))
		def buff =  pageSize - numberOfListings > 0 ?pageSize - numberOfListings: 0
		
		return [numberOfListings, pageNumber, numberOfPages as Double, time, optimizeForDisplay(list,buff), LEGAL] 
	}
	 
	@Deprecated
 	public List<Map<String, Object>> openHouse(Map params){
 		def sql = SqlHelper.openHouseSqlFromRpcParamMap(params)
		println sql
		def start = Date.getMillisOf(new Date())
		List list = jdbcTemplate.queryForList(sql)
		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
 		return [ list.size(), time, denullify(list)]
 	}
	 
	@Deprecated
	public List<Map<String, Object>> shortSearch(Map params){
		def sql = SqlHelper.shortSqlFromRpcParamMap(params)
		println sql
		def start = Date.getMillisOf(new Date())
		List list = jdbcTemplate.queryForList(sql)
		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
		
	    int numberOfListings = list.isEmpty() ? 0 : list?.get(0)?.getAt(TOTAL_CNT)
		int pageNumber = (params[PAGE_NUM_KEY]?:'1').toInteger()		
		int pageSize = (params[PAGE_SIZE_KEY]?:'200').toInteger()
		def numberOfPages = (numberOfListings<pageSize)?1:new BigDecimal(numberOfListings/pageSize, new MathContext(1, java.math.RoundingMode.UP))
		def buff =  pageSize - numberOfListings > 0 ?pageSize - numberOfListings: 0
		
		return [numberOfListings, pageNumber, numberOfPages as Double, time, optimizeForDisplay(list,buff), LEGAL] 
		//return [numberOfListings, pageNumber, numberOfPages.round() as Double, time, optimizeForDisplay(list,buff), LEGAL] //pageSize nubmerOfListings
	}
	
	@Deprecated
	public List<Map<String, Object>> solds(Map params){
		def sql = SqlHelper.soldsSqlFromRpcParamMap(params)
		println sql
		def start = Date.getMillisOf(new Date())
		List list = jdbcTemplate.queryForList(sql)
		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
		int numberOfListings = list.isEmpty() ? 0 : list?.get(0)?.getAt(TOTAL_CNT)
		return [numberOfListings,time, denullify(list)] //pageSize nubmerOfListings
	}

	public static Object optimizeForDisplay(List list, int buffer){
		
		//create and initialize array to buffer
		def bufferList = [].withDefault{new Object()}
		buffer.times { 
			bufferList.get(it)
		}
		
		//take care of nulls, extra field for count, and add empty records <value/> to match page count
		if (!list.isEmpty()) {
			list = denullify(list)
		}
		
		return (bufferList.isEmpty()) ? list : list + bufferList
		
		return list;		
	}
	
	public static Object denullify(List list){
		list.each{ o ->
			o.remove(TOTAL_CNT)
			o.each{if (it.value==null) it.value=''}
		}
		return (list.isEmpty())?new Object():list
	}
	public static void generateImages(Map property, String name){
		String mls = property.get('MLS')
		def mlsFolder = mls.replaceFirst(/.{3}$/, "000")
		def photoCount = property.PhotoCount
		List full = [];
		List p100 = [];
		List p200 = [];
		List p400 = [];
		def url = 'http://www.ramidx.com/MLSImages/' + mlsFolder
		
		(1..photoCount).each{
			full.add(url + '/maui' + mls + '-' + it + '.jpg')
			p100.add(url + '/100/maui' + mls + '-' + it + '.jpg')
			p200.add(url + '/200/maui' + mls + '-' + it + '.jpg')
			p400.add(url + '/400/maui' + mls + '-' + it + '.jpg')
		}
		property."${name}" =  [ 'Full' :full, 'P100' : p100, 'P200':p200, 'P400':p400]

	}

	 
}

//Inner class
@Deprecated
class Marker{
	int type
	Object content
	boolean flatten
	
	public Marker(int type, Object content, boolean flatten = false) {
		this.type = type
		this.content = content
		this.flatten = flatten
	}
}
