package app.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

import app.service.SoldsMethodService

@Service
class SoldRepository {
	
	static final VACANT_LAND_SQL_PREFIX = '''
		SELECT
		MLSNumber AS MLS,
		EPC.Name AS Class,
		EDI.Name AS District,
		CONVERT(VARCHAR(10), ClosingDate,  120) AS ClosingDate,
		LA1AgentUserCode AS AgentID, 
		LO1OfficeAbbreviation AS ListingOffice1_ID,
		CAST(ListPrice AS MONEY) AS ListPrice,
		CONVERT(VARCHAR(10), SoldPrice) SoldPrice,
		VOWAddress,
		AddressNumber,
		AddressDirection,
		AddressStreet,
		ELT.Name AS LT,
		--CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
		CAST(LandSQFT AS FLOAT) AS LandSQFT,
		EVI.Name as [View],
		EWF.Name as WaterFront,
		--Images
		CAST(0 AS FLOAT) AS LivSQFT,
		CAST(0 AS FLOAT) AS Ohana_LivSQFT,
		COUNT(*) OVER() AS _Total
		FROM Property 
		LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
		LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
		LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
		LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
		LEFT JOIN Enum_District AS EDI on District = EDI.Id
		LEFT JOIN Enum_Status AS EST on Status = EST.Id
		LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id 

	''';
	
	// TODO missing images
	// TODO missing CoListingAgent_ID
	// TODO missing ListingOffice2_ID
	static final RESIDENTIAL_SQL_PREFIX = '''
		SELECT
		MLSNumber AS MLS,
		EPC.Name AS Class,
		EDI.Name AS District,
		CONVERT(VARCHAR(10), ClosingDate,  120) AS ClosingDate,
		LA1AgentUserCode AS AgentID, 
		LO1OfficeAbbreviation AS ListingOffice1_ID,
		CAST(ListPrice AS MONEY) AS ListPrice,
		CONVERT(VARCHAR(10), SoldPrice) SoldPrice,
		VOWAddress,
		AddressNumber,
		AddressDirection,
		AddressStreet,
		CONVERT(VARCHAR(10), Beds) AS Beds,
		CONVERT(VARCHAR(10), Baths) AS Baths,
		ELT.Name AS LT,
		CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
		CAST(LandSQFT AS FLOAT) AS LandSQFT,
		EVI.Name as [View],
		EWF.Name as WaterFront,
		CONVERT(VARCHAR(10), OhanaBeds) AS Ohana_Beds,
		CONVERT(VARCHAR(10), OhanaBaths) AS Ohana_Baths,
		CAST(OhanaSqFt AS FLOAT) AS Ohana_LivSQFT,
		COUNT(*) OVER() AS _Total
		FROM Property 
		LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
		LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
		LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
		LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
		LEFT JOIN Enum_District AS EDI on District = EDI.Id
		LEFT JOIN Enum_Status AS EST on Status = EST.Id
		LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id 

	''';
		
	static final CONDO_SQL_PREFIX = '''
		SELECT
		MLSNumber AS MLS,
		EPC.Name AS Class,
		EDI.Name AS District,
		EPT.Name AS Type,
		CONVERT(VARCHAR(10), ClosingDate,  120) AS ClosingDate,
		LA1AgentUserCode AS AgentID, 
		LO1OfficeAbbreviation AS ListingOffice1_ID,
		CAST(ListPrice AS MONEY) AS ListPrice,
		CONVERT(VARCHAR(10), SoldPrice) SoldPrice,
		VOWAddress,
		AddressNumber,
		AddressDirection,
		AddressStreet,
		CONVERT(VARCHAR(10), Beds) AS Beds,
		CONVERT(VARCHAR(10), Baths) AS Baths,
		ELT.Name AS LT,
		CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
		CAST(LandSQFT AS FLOAT) AS LandSQFT,
		EVI.Name as [View],
		EWF.Name as WaterFront,
		BuildingName,
		Unit,
		CONVERT(VARCHAR(10), MonthlyMaintFee) AS MaintFee,
		--Images PhotoCount
		CAST(0 AS FLOAT) AS Ohana_LivSQFT,
		COUNT(*) OVER() AS _Total
		FROM Property 
		LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
		LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
		LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
		LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
		LEFT JOIN Enum_District AS EDI on District = EDI.Id
		LEFT JOIN Enum_Status AS EST on Status = EST.Id
		LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id
 
	''';
	
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public SoldRepository() {
		super();
		// TODO make the timezone universal in application
		TimeZone.setDefault(TimeZone.getTimeZone('GMT-10'))// Hawaiian  time
	}

	public List<Map<String,Object>> listSoldResidential (Map params) {
		String whereClause = " WHERE";
		String filterClause = makeFilterClause(params);
		whereClause += filterClause;
		String orderByClause = makeOrderByClause(params);
		String offsetClause = makeOffsetClause(params);
		String sql = RESIDENTIAL_SQL_PREFIX + whereClause + orderByClause + offsetClause;
		println sql;
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String,Object>> listSoldCondo (Map params) {
		String whereClause = " WHERE";
		String filterClause = makeFilterClause(params);
		whereClause += filterClause;
		String orderByClause = makeOrderByClause(params);
		String offsetClause = makeOffsetClause(params);
		String sql = CONDO_SQL_PREFIX + whereClause + orderByClause + offsetClause;
		println sql;
		return jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String,Object>> listSoldVacantLand (Map params) {
		String whereClause = " WHERE";
		String filterClause = makeFilterClause(params);
		whereClause += filterClause;
		String orderByClause = makeOrderByClause(params);
		String offsetClause = makeOffsetClause(params);
		String sql = VACANT_LAND_SQL_PREFIX + whereClause + orderByClause + offsetClause;
		println sql;
		return jdbcTemplate.queryForList(sql);
	}
	
	private static String makeOffsetClause (Map params) {
		Integer maxRows = SoldsMethodService.MAX_RESULT_COUNT;
		return " OFFSET 0 ROWS FETCH NEXT ${maxRows} ROWS ONLY";
	}
	
	private static String makeOrderByClause (Map params) {	
		return " ORDER BY SoldPrice DESC";
	}
	
	private static String makeFilterClause (Map params) {
		String filterClause = " EST.Name = 'SOLD'";
		String conjunct = "";
		
		//WhatAddressFull	None	Any part of an address
		if(params.'WhatPropType'){
			filterClause +=  " AND EPC.Name LIKE '$params.WhatPropType'"			
		}

		//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
		if(params.'WhatWater'){
			filterClause +=  " AND EWF.Name in (${ params.WhatWater.collect{"'$it'"}.join(',')})"		
		}
		
		//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
		//		PropertyType //WhatOhanaAll  Array: Single Family, Single Family w/AttOhana, SF w/DetOhana or Cottage
		if(params.'WhatOhana'){
			filterClause +=  " AND EPT.Name in (${ params.WhatOhana.collect{"'$it'"}.join(',')})"			
		}

		//WhatCondo	 		All  	Array: See Condo List
		if(params.'WhatCondo'){
			filterClause +=  " AND BuildingName in (${params.WhatCondo.collect{"'$it'"}.join(',')})"		
		}

		//WhatStartPrice	No		Lower Limit	Price (Integer)
		if(params.'WhatStartPrice'){
			filterClause +=  " AND ListPrice >= ${params.WhatStartPrice}"			
		}

		//WhatEndPrice	 	No 		Upper Limit	Price (Integer)
		if(params.'WhatEndPrice'){
			filterClause +=  " AND ListPrice <= ${params.WhatEndPrice}"			
		}

		//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
		if(params.'WhatView'){
			filterClause +=  " AND EVI.Name in (${ params.WhatView.collect{"'$it'"}.join(',')})"			
		}
		
		//WhatDistrict	 	All 	Array: See District List
		if(params.'WhatDistrict'){//	WhatDistrict Array Use district
			filterClause +=  " AND EDI.Name in (${params.WhatDistrict.collect{"'$it'"}.join(',')})"			
		}

		//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
		if(params.'WhatLandTenure'){
			filterClause +=  " AND ELT.Name in (${params.WhatLandTenure.collect{"'$it'"}.join(',')})"			
		}
			
		//WhatAgent			All		Array: See Agent List (Use agent ID number)
		if(params.'WhatAgent'){
			filterClause += " AND LA1AgentUserCode in (${ params.WhatAgent.join(',')})"			
		}

		//WhatOffice	 	All		Array: See Office List (Use Office ID Number)
		if(params.'WhatOffice'){
			filterClause += " AND LO1OfficeAbbreviation in (${ params.WhatOffice.join(',')})"			
		}
		
		//WhatStartBed	 	No 		Lower Limit	Integer
		if(params.'WhatStartBed'){
			filterClause +=  " AND Beds >= ${params.WhatStartBed}"			
		}

		//WhatEndBed	 	No 		Upper Limit	Integer
		if(params.'WhatEndBed'){
			filterClause +=  " AND Beds <= ${params.WhatEndBed}"			
		}
		
		//WhatStartBath	 	No 		Lower Limit	Integer
		if(params.'WhatStartBath'){
			filterClause +=  " AND Baths >= ${params.WhatStartBath}"			
		}

		//WhatEndBath	 	No 		Upper Limit	Integer
		if(params.'WhatEndBath'){
			filterClause +=  " AND Baths <= ${params.WhatEndBath}"			
		}
		
		//WhatStartIntArea	No 		Lower Limit	Integer (Square Feet)
		if(params.'WhatStartIntArea'){
			filterClause +=  " AND LivingAreaSqFt >= ${params.WhatStartIntArea}"			
		}

		//WhatEndIntArea	No 		Upper Limit	Integer (Square Feet)
		if(params.'WhatEndIntArea'){
			filterClause +=  " AND LivingAreaSqFt <= ${params.WhatEndIntArea}"			
		}

		//WhatStartExtArea	No 		Lower Limit	Integer (Square Feet)
		if(params.'WhatStartExtArea'){
			filterClause +=  " AND LandSQFT >= ${params.WhatStartExtArea}"			
		}

		//WhatEndExtArea	No 		Upper Limit	Integer (Square Feet)
		if(params.'WhatEndExtArea'){
			filterClause +=  " AND LandSQFT <= ${params.WhatEndExtArea}"			
		}
		
		//WhatDivAny?Array: ?
		if(params.'Div'){
			filterClause += " AND Div in (${ params.Div.join(',')})"			
		}
	
		//WhatZoneAnyAny?Array: ?
		if(params.'Zone'){
			filterClause += " AND Zone in (${ params.Zone.join(',')})"			
		}

		//WhatSecAny?Array: ?
		if(params.'Sec'){
			filterClause += " AND Sec in (${ params.Sec.join(',')})"			
		}

		//WhatPlatAny?Array: ?
		if(params.'Plat'){
			filterClause += " AND Plat in (${ params.Plat.join(',')})"			
		}

		//WhatParAny?Array: ?
		if(params.'Par'){
			filterClause += " AND Par in (${ params.Par.join(',')})"			
		}
		
		return filterClause;
	}

}
