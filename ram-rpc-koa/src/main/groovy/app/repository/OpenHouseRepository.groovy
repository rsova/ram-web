package app.repository

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.beans.Agent
import app.beans.District
import app.beans.GeoData
import app.beans.Office
import app.beans.OpenHouse
import app.beans.PropertyAddress
import app.beans.PropertyClass
import app.beans.PropertyType
import app.beans.Status
import app.beans.StatusCategory

// TODO normalize this
@Service
class OpenHouseRepository {
	
	private static final OPEN_HOUSE_SQL_PREFIX = '''
		SELECT 
		Agent,
		CoListingAgent,
		ListingOffice1,
		CoListingOffice,
		MLSNumber,
		Class,
		District,
		CONVERT(VARCHAR(10), StartDate,  120) AS StartDate,
		CONVERT(VARCHAR(8), StartTime, 108) AS StartTime,
		CONVERT(VARCHAR(8), EndTime, 108) AS EndTime,
		PropertyType,
		Status,
		AddressNumber,
		AddressDirection,
		AddressStreet,
		City,
		State,
		ZipCode5,
		Comments
		FROM OpenHouse

	''';
	
	private static final String DATE_FORMAT_SPEC = 'yyyy-MM-dd';
	
	@Autowired
	private AgentRepository agentRepo;
	
	@Autowired
	private OfficeRepository officeRepo;
	
	@Autowired
	private DistrictRepository districtRepo;
	
	@Autowired
	private StatusRepository statusRepo;
	
	@Autowired
	private StatusCategoryRepository statusCatRepo;
	
	@Autowired
	private PropertyTypeRepository typeRepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public OpenHouseRepository() {
		super();
		// TODO make the timezone universal in application
		TimeZone.setDefault(TimeZone.getTimeZone('GMT-10'))// Hawaiian  time
	}
	
	protected String makeFilterClause (Map params){
		String filterClause = ' ';		
		String conjunct = 'AND';
		
		if (params.'WhatAgent') {
			List<String> ids = [];
			
			for (String userCode : params.'WhatAgent'.collect{"$it"}) {
				Agent agent = agentRepo.findAgentByUserCode(userCode);
				if (agent) {
					ids.add("'${agent.identifier}'");
				}
			}
			
			if(ids==null || ids.size() == 0){
				//if there is a miss on id, use one that does not exist.
				ids.add('-1')
			}
			
			String set = ids.join(',');
			filterClause +=  " $conjunct Agent in (${set})";
		}
		
		if (params.'WhatDistrict') {
			String set = params.'WhatDistrict'.collect{"'$it'"}.join(',');
			filterClause +=  " $conjunct District in (${set})";
		}
		
		if (params.'WhatType') {
			String set = params.'WhatType'.collect{"'$it'"}.join(',');
			filterClause +=  " $conjunct Class in (${set})";
		}
		
		if (params.'WhatOffice') {
			List<String> ids = [];
			for (String abbrev : params.'WhatOffice'.collect{"$it"}) {
				Office office = officeRepo.findOfficeByAbbrev(abbrev);
				if (office) {
					ids.add("'${office.identifier}'");
				}
			}
			
			if(ids==null || ids.size() == 0){
				//if there is a miss on id, use one that does not exist.
				ids.add('-1')
			}

			String set = ids.join(',');
			filterClause +=  " $conjunct ListingOffice1 in (${set})";
		}
		
		return filterClause;
	}

	public List<OpenHouse> listOpenHouses (Map params) {
		String whereClause = " WHERE" + makeEventNotEndedClause();
		String filterClause = makeFilterClause(params);
		whereClause += filterClause;
		String orderByClause = makeOrderByClause();
		String sql = OPEN_HOUSE_SQL_PREFIX + whereClause + orderByClause;
		println sql;
		List<OpenHouse> result = [];
		List rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			OpenHouse elem = new OpenHouse();
			elem.setOpenHouseId(row.get("OpenHouseID"));
			
			PropertyAddress address = new PropertyAddress();
			address.setFullAddress(row.get("Address"));
			address.setDirection(row.get("AddressDirection"));
			address.setLotUnit(row.get("AddressLotUnit"));
			address.setNumber(row.get("AddressNumber"));
			address.setSearchNumber(row.get("AddressSearchNumber"));
			address.setStreet(row.get("AddressStreet"));
			address.setCity(row.get("City"));
			address.setState(row.get("State"));	
			address.setZipCode5(row.get("ZipCode5"));
			elem.setAddress(address);
			
			Agent agent = agentRepo.findAgent(row.get("Agent"));
			elem.setAgent(agent);
			
			// TODO use PropertyClassRepository
			PropertyClass propClass = new PropertyClass();
			propClass.setName(row.get("Class"));
			elem.setPropertyClass(propClass);
			
			Agent coListingAgent = agentRepo.findAgent(row.get("CoListingAgent"));
			elem.setCoListingAgent(coListingAgent);
			
			Office colistingOffice = officeRepo.findOffice(row.get("CoListingOffice"));
			elem.setCoListingOffice(colistingOffice);
			
			elem.setComments(row.get("Comments"));
			elem.setCreateDateTime(row.get("CreateDateTime"));
			
			// TODO use DistrictRepository
			District district = new District();
			district.setName(row.get("District"));
			elem.setDistrict(district);
			
			elem.setDocTimestamp(row.get("DocTimestamp"));
			elem.setEndTime(row.get("EndTime"));
			elem.setEventEndDateTime(row.get("EventEndDateTime"));
			
			GeoData geoData = new GeoData();
			geoData.setAddressLine(row.get("GeoAddressLine"));
			geoData.setLatitude(row.get("GeoLatitude"));
			geoData.setLongitude(row.get("GeoLongitude"));
			geoData.setMatchCode(row.get("GeoMatchCode"));
			geoData.setMatchedMethod(row.get("GeoMatchedMethod"));
			geoData.setPostalCode(row.get("GeoPostalCode"));
			geoData.setPrimaryCity(row.get("GeoPrimaryCity"));
			geoData.setQuality(row.get("GeoQuality"));
			geoData.setSecondaryCity(row.get("GeoSecondaryCity"));
			geoData.setSubdivision(row.get("GeoSubdivision"));
			geoData.setUpdateTimestamp(row.get("GeoUpdateTimestamp"));
			geoData.setZoomLevel(row.get("GeoZoomLevel"));
			elem.setGeoData(geoData);
			
			elem.setIdxInclude(sql);
			
			Office listingOffice1 = officeRepo.findOffice(row.get("ListingOffice1"));
			elem.setListingOffice1(listingOffice1);
			
			elem.setMlsNumber(row.get("MLSNumber"));
			elem.setSearchPrice(row.get("SearchPrice"));
			elem.setStartDate(row.get("StartDate"));
			elem.setStartDateTime(row.get("StartDateTime"));
			elem.setStartTime(row.get("StartTime"));
			
			Status status = statusRepo.findStatus(row.get("Status"));
			elem.setStatus(status);
			
			StatusCategory statusCat = statusCatRepo.findStatusCategory(row.get("StatusCategory"));
			elem.setStatusCategory(statusCat);
			
			elem.setStatusDate(row.get("StatusDate"));
			elem.setStatusDetail(row.get("StatusDetail"));
			
			PropertyType propType = typeRepo.findPropertyType(row.get("PropertyType"));
			elem.setPropertyType(propType);
			
			elem.setUniqueID(row.get("UniqueID"));
			elem.setUpdateDateTime(row.get("UpdateDate"));
			elem.setUpdateDateTime(row.get("UpdateDateTime"));
			elem.setVowAddress(row.get("VOWAddress"));
			elem.setVowAVM(row.get("VOWAVM"));
			elem.setVowComment(row.get("VOWComment"));
			elem.setVowInclude(row.get("VOWInclude"));
			elem.setDiscriminator(row.get("Discriminator"));
			
			result.add(elem);
		}
		return result;
	}
	
	private static String makeEventNotEndedClause () {
		// The easiest way to do it is to use: EventEndDateTime >= CURRENT_TIMESTAMP , but the time on database server is out of synch :(
		// return " EventEndDateTime >= '${now.format('yyyy-MM-dd HH:mm:ss XXX')}'"
		def now = new Date();
		String formattedNow = now.format(DATE_FORMAT_SPEC);
		return  " CONVERT(VARCHAR(10), EventEndDateTime,  120) >= '${formattedNow}'";
	}
	
	private static String makeOrderByClause () {
		return " ORDER by MLSNumber ASC";
	}

}
