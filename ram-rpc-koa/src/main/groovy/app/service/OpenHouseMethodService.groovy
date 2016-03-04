package app.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import app.beans.OpenHouse
import app.repository.OpenHouseRepository
import app.service.sqlserver.RamSqlService
import app.websearch.rpc.helper.XmlRpcHelper

@Service
class OpenHouseMethodService {

	public static final String METHOD_NAME = 'openhouse';
	
	public static final String AGENT_PARAM_NAME 	= 'WhatAgent';
	public static final String OFFICE_PARAM_NAME 	= 'WhatOffice';
	public static final String TYPE_PARAM_NAME 		= 'WhatType';
	public static final String DISTRICT_PARAM_NAME 	= 'WhatDistrict';
	
	public static final String ATTR_AGENT1_ID = "agent1_id";
	public static final String ATTR_AGENT2_ID = "agent2_id";
	public static final String ATTR_OFFICE1_ID = "office1_id";
	public static final String ATTR_OFFICE2_ID = "office2_id";
	public static final String ATTR_LISTING_ID = "L_ListingID";
	public static final String ATTR_CLASS = "L_Class";
	public static final String ATTR_AREA = "L_Area";
	public static final String ATTR_OH_START_DATE = "OH_StartDate";
	public static final String ATTR_OH_START_TIME = "OH_StartTime";
	public static final String ATTR_OH_END_TIME = "OH_EndTime";
	public static final String ATTR_IMAGE = "image";
	public static final String ATTR_TYPE = "L_Type_";
	public static final String ATTR_STATUS = "L_Status";
	public static final String ATTR_ADDRESS_NUMBER = "L_AddressNumber";
	public static final String ATTR_ADDRESS_DIRECTION = "L_AddressDirection";
	public static final String ATTR_ADDRESS_STREET = "L_AddressStreet";
	public static final String ATTR_CITY = "L_City";
	public static final String ATTR_STATE = "L_State";
	public static final String ATTR_ZIP = "L_Zip";
	public static final String ATTR_OH_COMMENTS = "OH_Comments";
	
	@Autowired
	private OpenHouseRepository openHRepo;
	
	// TODO do we need to denullify?
	public List openHouse (Map params) {
		def start = Date.getMillisOf(new Date());
		List<OpenHouse> list = openHRepo.listOpenHouses(params);
		def responseList = [];
		for (OpenHouse e : list) {
			Map val = new HashMap<>();
			val.put(ATTR_AGENT1_ID, e.agent ? e.agent.userCode : '');
			val.put(ATTR_AGENT2_ID, e.coListingAgent ? e.coListingAgent.userCode : '');
			val.put(ATTR_OFFICE1_ID, e.listingOffice1 ? e.listingOffice1.abbreviation : ''); // TODO should this be main office abbreviation?
			val.put(ATTR_OFFICE2_ID, e.coListingOffice ? e.coListingOffice.abbreviation : ''); // TODO should this be main office abbreviation?
			val.put(ATTR_LISTING_ID, e.mlsNumber);
			val.put(ATTR_CLASS, e.propertyClass ? e.propertyClass.name : '');
			val.put(ATTR_AREA, e.district ? e.district.name : '');
			val.put(ATTR_OH_START_DATE, e.startDate);
			val.put(ATTR_OH_START_TIME, e.startTime);
			val.put(ATTR_OH_END_TIME, e.endTime);
			val.put(ATTR_TYPE, e.propertyType ? e.propertyType.name : '');
			val.put(ATTR_STATUS, e.status ? e.status.name : '');
			val.put(ATTR_ADDRESS_NUMBER, e.address.number);
			val.put(ATTR_ADDRESS_DIRECTION, e.address.direction);
			val.put(ATTR_ADDRESS_STREET, e.address.street);
			val.put(ATTR_CITY, e.address.city);
			val.put(ATTR_STATE, e.address.state);
			val.put(ATTR_ZIP, e.address.zipCode5);
			val.put(ATTR_OH_COMMENTS, e.comments);
			val.put(ATTR_IMAGE, e.image);				
			responseList.add(val);
		}
//		list = XmlRpcHelper.escapeXml(list);
		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
		return [
			responseList.size(),
			time,
			RamSqlService.denullify(responseList)
		]
	}
}
