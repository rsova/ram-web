package app.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ShortSearchRepository {
	
	static final SHORT_SQL_PREFIX = '''
		select 
		MLSNumber AS MLS,
		EPC.LegacyName AS Class,
		EST.LegacyName AS Status,
		CAST(ListPrice AS MONEY) AS ListPrice,
		ELT.LegacyName AS LT,
		CONVERT(VARCHAR(10), ListDate,  120) AS ListDate,
		CONVERT(VARCHAR(10), LivingAreaSqFt) AS LivSQFT,
		LandSQFT,
		LA1AgentUserCode AS AgentID, 
		LO1OfficeAbbreviation AS ListingOffice1_ID,
		LA2UserCode AS CoListingAgent_ID,
		LO2OfficeAbbreviation AS ListingOffice2_ID,
		AddressNumber,
		AddressDirection,
		AddressStreet,
		AddressLotUnit,
		EDI.LegacyName AS District,
		BuildingName,
		Unit,
		CONVERT(VARCHAR(10), Beds) AS Beds,
		CONVERT(VARCHAR(10), Baths) AS Baths,
		PotentialShortSale = CASE PotentialShortSale WHEN 0 THEN 'N' WHEN 1 THEN 'Y' ELSE '' END,
		REO = CASE REO WHEN 0 THEN 'N' WHEN 1 THEN 'Y' ELSE '' END,
		--GeoLatitude AS Lat,
		--GeoLongitude AS Lon,
		CONVERT(VARCHAR(10), GeoLatitude) as Lat,
		CONVERT(VARCHAR(10), GeoLongitude) as Lon,
		PhotoCount AS PhotoCount,
		COUNT(*) OVER() AS _Total
		
		FROM Property 
		LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
		LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
		LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id
		LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
		LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
		LEFT JOIN Enum_District AS EDI on District = EDI.Id
		LEFT JOIN Enum_Status AS EST on Status = EST.Id
	''';
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ShortSearchRepository() {
		super();
		// TODO make the timezone universal in application
		TimeZone.setDefault(TimeZone.getTimeZone('GMT-10'))// Hawaiian  time
	}

	public List<Map<String,Object>> listShort (Map params) {
		String whereClause = " WHERE";
		String filterClause = makeFilterClause(params);
		whereClause += filterClause;
		String orderByClause = makeOrderByClause(params);
		String offsetClause = makeOffsetClause(params);
		String sql = SHORT_SQL_PREFIX + whereClause + orderByClause + offsetClause;
		println sql;
		return jdbcTemplate.queryForList(sql);
	}
	
	private static String makeOffsetClause (Map params) {
		int pageSize = (params.'WhatNumber').toInteger();	
		int pageNumber = (params.'WhatPage').toInteger();
		int offset = pageSize * (pageNumber - 1);
		return " OFFSET $offset ROWS FETCH NEXT $pageSize ROWS ONLY";
	}
	
	private static String makeOrderByClause (Map params) {
		String orderByClause = " ORDER BY ";
		if (params.'MeFirst' == 'Y') {
			if (params.'WhatAgent') {
				String set = params.'WhatAgent'.collect{"'$it'"}.join(',');
				String meFirst = " CASE WHEN LA1AgentUserCode in (${set}) THEN 1 ELSE 2 END ASC, LA1AgentUserCode";
			} else if (params.'WhatOffice') {
				String set = params.'WhatOffice'.collect{"'$it'"}.join(',');
				String meFirst = " CASE WHEN LO1OfficeAbbreviation in (${set}) THEN 1 ELSE 2 END ASC, LO1OfficeAbbreviation"
			}
		} else {
			String sortType1 = params.'WhatSortType1';
			String sortDir1 = params.'WhatSortDirection1';
			orderByClause += " ${sortType1} ${sortDir1}";
			String sortType2 = params.'WhatSortType2';
			if (sortType2) {
				String sortDir2 = params.'WhatSortDirection2' ?: '';
				orderByClause += ", ${sortType2} ${sortDir2}";
			}
		}
		return orderByClause;
	}
	
	private static String makeFilterClause (Map params) {
//		String filterClause = ' ';
		//only get active listings
		String filterClause = " EST.LegacyName NOT IN ('SOLD','EXPIRED','WITHDRAWN','TEMPOFFMARKET','CANCELED') AND "; // 3,6,7,8,9
		
		//if the WhatMLS array is passed all other parameters are ignored, except sort parameters.
		//WhatMLS	 		None	//Comma delimited list of mls numbers.
		if (params.'WhatMLS'){
			filterClause += "MLSNumber in (${ params.WhatMLS.collect{"'$it'"}join(',')})"
		} else {
			
			String conjunct = '';
		
			if (params.'WhatPropType') {
				filterClause +=  " $conjunct EPC.LegacyName in (${ params.WhatPropType.collect{"'$it'"}.join(',')})"
				conjunct = 'AND';
			}
			
			//These values represent two corners of a rectangle. Any listings who's Latitude and Longitude fall within these values are returned.
			//WhatLat1	 		None
			//WhatLon1	 		None
			//WhatLat2	 		None?MAKES NO DIFF(RS)
			//WhatLon2	 		None?MAKES NO DIFF(RS)
			if(params.'WhatLat1' || params.'WhatLat2'){
				def crds = []
				if(params.'WhatLat1'){crds.add("GeoLatitude LIKE '${params.WhatLat1.replaceAll(/0*$/, "")}%'")}//def crd = map.WhatLon1.replaceAll(/0*$/, "")
				if(params.'WhatLat2'){crds.add("GeoLatitude LIKE '${params.WhatLat2.replaceAll(/0*$/, "")}%'")}
				if(!crds.empty){
					filterClause += (crds.size == 1)? " $conjunct ${crds.get(0)}":" $conjunct (${crds.get(0)} OR ${crds.get(1)})"
					conjunct = 'AND';
				}
			}
			if(params.'WhatLon1' || params.'WhatLon2'){
				def crds = []
				if(params.'WhatLon1'){crds.add("GeoLongitude LIKE '${params.WhatLon1.replaceAll(/0*$/, "")}%'")}//def crd = map.WhatLon1.replaceAll(/0*$/, "")
				if(params.'WhatLon2'){crds.add("GeoLongitude LIKE '${params.WhatLon2.replaceAll(/0*$/, "")}%'")}
				if(!crds.empty){
					filterClause += (crds.size == 1)? " $conjunct ${crds.get(0)}":" $conjunct (${crds.get(0)} OR ${crds.get(1)})"
					conjunct = 'AND';
				}
			}
	
			//WhatAddressFull	None	Any part of an address
			if(params.'WhatAddressFull'){
				filterClause +=  " $conjunct Address LIKE '%$params.WhatAddressFull%'"
				conjunct = 'AND';
			}
	
			//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
			if(params.'WhatWater'){
				filterClause +=  " $conjunct EWF.LegacyName in (${ params.WhatWater.collect{"'$it'"}.join(',')})"
				conjunct = 'AND';
			}
			
			//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
			if(params.'WhatOhana'){
				filterClause +=  " $conjunct EPT.LegacyName in (${ params.WhatOhana.collect{"'$it'"}.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatCondo	 		All  	Array: See Condo List
			if(params.'WhatCondo'){
				filterClause +=  " $conjunct BuildingName in (${params.WhatCondo.collect{"'$it'"}.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatStartPrice	No		Lower Limit	Price (Integer)
			if(params.'WhatStartPrice'){
				filterClause +=  " $conjunct ListPrice >= ${params.WhatStartPrice}"
				conjunct = 'AND';
			}
	
			//WhatEndPrice	 	No 		Upper Limit	Price (Integer)
			if(params.'WhatEndPrice'){
				filterClause +=  " $conjunct ListPrice <= ${params.WhatEndPrice}"
				conjunct = 'AND';
			}
	
			//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
			if(params.'WhatView'){
				//filterClause +=  " $conjunct EVI.LegacyName in (${ params.WhatView.collect{"'$it'"}.join(',')})"
				filterClause += " $conjunct ( " + params.WhatView.collect{" EVI.LegacyName LIKE ('%$it%') "}.join(' OR ') + ')'
				conjunct = 'AND';
			}
			
			//WhatDistrict	 	All 	Array: See District List
			if(params.'WhatDistrict'){//	WhatDistrict Array Use district
				filterClause +=  " $conjunct EDI.LegacyName in (${params.WhatDistrict.collect{"'$it'"}.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
			if(params.'WhatLandTenure'){
				filterClause +=  " $conjunct ELT.LegacyName in (${params.WhatLandTenure.collect{"'$it'"}.join(',')})"
				conjunct = 'AND';
			}
				
			//WhatAgent			All		Array: See Agent List (Use agent ID number)
			if(params.'WhatAgent'){
				filterClause += " $conjunct LA1AgentUserCode in (${ params.WhatAgent.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatOffice	 	All		Array: See Office List (Use Office ID Number)
			if(params.'WhatOffice'){
				filterClause += " $conjunct LO1OfficeAbbreviation in (${ params.WhatOffice.join(',')})"
				conjunct = 'AND';
			}
			
			//WhatStartBed	 	No 		Lower Limit	Integer
			if(params.'WhatStartBed'){
				filterClause +=  " $conjunct Beds >= ${params.WhatStartBed}"
				conjunct = 'AND';
			}
	
			//WhatEndBed	 	No 		Upper Limit	Integer
			if(params.'WhatEndBed'){
				filterClause +=  " $conjunct Beds <= ${params.WhatEndBed}"
				conjunct = 'AND';
			}
			
			//WhatStartBath	 	No 		Lower Limit	Integer
			if(params.'WhatStartBath'){
				filterClause +=  " $conjunct Baths >= ${params.WhatStartBath}"
				conjunct = 'AND';
			}
	
			//WhatEndBath	 	No 		Upper Limit	Integer
			if(params.'WhatEndBath'){
				filterClause +=  " $conjunct Baths <= ${params.WhatEndBath}"
				conjunct = 'AND';
			}
			
			//WhatStartIntArea	No 		Lower Limit	Integer (Square Feet)
			if(params.'WhatStartIntArea'){
				filterClause +=  " $conjunct LivingAreaSqFt >= ${params.WhatStartIntArea}"
				conjunct = 'AND';
			}
	
			//WhatEndIntArea	No 		Upper Limit	Integer (Square Feet)
			if(params.'WhatEndIntArea'){
				filterClause +=  " $conjunct LivingAreaSqFt <= ${params.WhatEndIntArea}"
				conjunct = 'AND';
			}
	
			//WhatStartExtArea	No 		Lower Limit	Integer (Square Feet)
			if(params.'WhatStartExtArea'){
				filterClause +=  " $conjunct LandSQFT >= ${params.WhatStartExtArea}"
				conjunct = 'AND';
			}
	
			//WhatEndExtArea	No 		Upper Limit	Integer (Square Feet)
			if(params.'WhatEndExtArea'){
				filterClause +=  " $conjunct LandSQFT <= ${params.WhatEndExtArea}"
				conjunct = 'AND';
			}
			
			//ExAgency Yes?Include exclusive agency listings in the query.
			if(params.'ExAgency' && params.'ExAgency' == 'Yes'){
				filterClause +=  " $conjunct ListingAgreement = 1"
				conjunct = 'AND';
			}
			
			//WhatDivAny?Array: ?
			if(params.'WhatDiv'){
				filterClause += " $conjunct Div in (${ params.Div.join(',')})"
				conjunct = 'AND';
			}
		
			//WhatZoneAnyAny?Array: ?
			if(params.'WhatZone'){
				filterClause += " $conjunct Zone in (${ params.Zone.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatSecAny?Array: ?
			if(params.'WhatSec'){
				filterClause += " $conjunct Sec in (${ params.Sec.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatPlatAny?Array: ?
			if(params.'WhatPlat'){
				filterClause += " $conjunct Plat in (${ params.Plat.join(',')})"
				conjunct = 'AND';
			}
	
			//WhatParAny?Array: ?
			if(params.'WhatPar'){
				filterClause += " $conjunct Par in (${ params.Par.join(',')})"
				conjunct = 'AND';
			}
					
			//WhatStartDate	 	Any		List Date in the format: Year-Month-Day (xxxx-xx-xx)
			if(params.'WhatStartDate'){
				filterClause +=  " $conjunct ListDate >= '${params.WhatStartDate}'"
				conjunct = 'AND';
			}
	
			//WhatEndDate	 	Any?		List Date in the format: Year-Month-Day (xxxx-xx-xx)
			if(params.'WhatEndDate'){
				filterClause +=  " $conjunct ListDate <= '${params.WhatEndDate}'"
				conjunct = 'AND';
			}
		}
		
		return filterClause;

	}
	
	

}
