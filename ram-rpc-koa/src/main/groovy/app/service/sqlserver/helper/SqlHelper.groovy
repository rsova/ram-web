package app.service.sqlserver.helper

import java.util.List;

import app.service.sqlserver.RamSqlService;
import app.service.sqlserver.query.Sql

class SqlHelper {
	
//	final static List PRTY_TYPE_LIST = ['Residential', 'Vacant', 'Land', 'Condominium', 'Commercial', 'Business', 'Time Interval', 'Multi-Dwelling Res']
	final static  String AND = " AND "

	private static final String RES = 'Residential'
	private static final String CONDO = 'Condominium'
	
	public static String searchSqlFromRpcParamMap(Map map){
		//WhatNumber max  20 for search.
		if(!map.'WhatNumber'){	map.put('WhatNumber','20') }
		if(map.'WhatMLS'){ map.put('WhatNumber','WhatMLS'.size()) }//full search does'nt need to add empty tags to build up page 
		return Sql.SEARCH.toString() << shortParamsToWhereClause(map)
	}
	
	public static String openHouseSqlFromRpcParamMap(Map map){
		return Sql.OPEN_HOUSE.toString() << openHouseParamsToWhereClause(map) 
	}
	
	public static String shortSqlFromRpcParamMap(Map map){
		//WhatNumber max 200 for short
		if(!map.'WhatNumber'){	map.put('WhatNumber','200') }
		return Sql.SHORT.toString() << shortParamsToWhereClause(map)
	}
		
	public static String soldsSqlFromRpcParamMap(Map map){
		//WhatNumber max 100 for solds
		if(!map.'WhatNumber') {	map.put('WhatNumber','100') }
		return soldsSqlForProperty(map) << shortParamsToWhereClause(map, false)<<" ORDER BY SoldPrice DESC"
	}
	
	protected static String soldsSqlForProperty(Map map){
		def sql = Sql.SOLDS_VL
		if(map.'WhatPropType' == RES){sql = Sql.SOLDS_RES}
		if(map.'WhatPropType' == CONDO){sql = Sql.SOLDS_CND}
		return sql.toString()
	}

	public static String openHouseParamsToWhereClause(Map map){
		TimeZone.setDefault(TimeZone.getTimeZone('GMT-10'))// Hawaiian  time
		def now = new Date()

		// The easiest way to do it is to use: EventEndDateTime >= CURRENT_TIMESTAMP , but the time on database server is out of synch :(
		//def whereClause =" EventEndDateTime >= '${now.format('yyyy-MM-dd HH:mm:ss XXX')}'"
		def whereClause =" CONVERT(VARCHAR(10), EventEndDateTime,  120) >=  '${now.format('yyyy-MM-dd')}'"
		def and = AND
		if(map.'WhatOffice'){//	WhatOffice	 Array Office ID Numbers
			whereClause += " $and o.MainOfficeAbbreviation in (${ map.WhatOffice.collect{"'$it'"}.join(',')})"
			and = AND
		}
		
		if(map.'WhatAgent'){//	WhatAgent	 Array Agent ID Numbers
			whereClause += "$and a.UserCode in (${ map.WhatAgent.collect{"'$it'"}.join(',')})"
			and = AND
		}
		
		if(map.'WhatType'){//	WhatType	 Array Residential, Vacant Land, Condominium
			whereClause +=  "$and Class in (${ map.WhatType.collect{"'$it'"}.join(',')})"
			and = AND
		}
		
		if(map.'WhatDistrict'){//	WhatDistrict Array Use district
			whereClause +=  " $and District in (${ map.WhatDistrict.collect{"'$it'"}join(',')})"
			and = AND
		}
		
		whereClause += " ORDER by ${map.WhatSortType1 ?: 'MLSNumber'} ${map.WhatSortDirection1?: 'ASC'} ${map.WhatSortType2 ? ', '<< map.WhatSortType2: ''} ${map.WhatSortDirection2 ?: ''}"
		
		//WhatNumber max 200 for short, 100 for solds and 20 for search. Defaults Expected to be set accordingly
		int pageSize = (map.WhatNumber?:'200').toInteger()
		//WhatPage	 	1	Page of data to return
		whereClause += " OFFSET 0 ROWS FETCH NEXT $pageSize ROWS ONLY"

		return whereClause
	}
	
	public static String shortParamsToWhereClause(Map map, Boolean isPaginationOn = true){
		def whereClause =""
		def and = ""
		
		// ----- If no value is passed, the default is all property types. If WhatCondo is passed, the default is Condominium. If WhatOhana is passed, the default is Residential.
		//If WhatCondo is passed, the default is Condominium.
		//If WhatOhana is passed, the default is Residential.
		//WhatPropType		Array: Residential, Vacant, Land, Condominium, Commercial, Business, Time Interval, Multi-Dwelling Res
		//
		def propClass =  map.'WhatPropType' instanceof Collection ? map.'WhatPropType' : map.'WhatPropType' ? [map.'WhatPropType'] : [] //if property type sent as single string, wrap it in collection
		if (map.'WhatCondo') propClass = [CONDO]
		if (map.'WhatOhana') propClass = [RES]
		if(propClass){
			//shorts 
			whereClause +=  "$and EPC.Name in (${ propClass.collect{"'$it'"}.join(',')})"
			and = AND
		}

		//WhatAddressFull	None	Any part of an address
		if(map.'WhatAddressFull'){
			whereClause +=  "$and Address LIKE '%$map.WhatAddressFull%'"
			and = AND
		}

		//WhatWater	 		All  	Array: OceanFront,BeachFront, Across Street from Ocean
		if(map.'WhatWater'){
			whereClause +=  "$and EWF.Name in (${ map.WhatWater.collect{"'$it'"}.join(',')})"
			and = AND
		}
		
		//WhatOhana	 		All  	Array: Single Family, Single Family w/Att Ohana, SF w/Det Ohana or Cottage
		//		PropertyType //WhatOhanaAll  Array: Single Family, Single Family w/AttOhana, SF w/DetOhana or Cottage
		if(map.'WhatOhana'){
			whereClause +=  "$and EPT.Name in (${ map.WhatOhana.collect{"'$it'"}.join(',')})"
			and = AND
		}

		//WhatCondo	 		All  	Array: See Condo List
		if(map.'WhatCondo'){
			whereClause +=  "$and BuildingName in (${map.WhatCondo.collect{"'$it'"}.join(',')})"
			and = AND
		}

		//WhatStartPrice	No		Lower Limit	Price (Integer)
		if(map.'WhatStartPrice'){
			whereClause +=  "$and ListPrice >= ${map.WhatStartPrice}"
			and = AND
		}

		//WhatEndPrice	 	No 		Upper Limit	Price (Integer)
		if(map.'WhatEndPrice'){
			whereClause +=  "$and ListPrice <= ${map.WhatEndPrice}"
			and = AND
		}
		//WhatREO	 		All	Set to Y to pull only REO - foreclosure
		if(map.'WhatREO'){
			whereClause +=  "$and REO = 'true'"
			and = AND
		}
		//WhatPSS	 		All	Set to Y to pull only Potential Short Sales
		if(map.'WhatPSS'){
			whereClause +=  "$and PotentialShortSale = 'true'"
			and = AND
		}

		//WhatView	 		All		Array: Ocean, Mountain, Mountain/Ocean, Golf Course, Garden View
		if(map.'WhatView'){
			whereClause +=  "$and EVI.Name in (${ map.WhatView.collect{"'$it'"}.join(',')})"
			and = AND
		}
		
		//WhatDistrict	 	All 	Array: See District List
		if(map.'WhatDistrict'){//	WhatDistrict Array Use district
			whereClause +=  "$and EDI.Name in (${map.WhatDistrict.collect{"'$it'"}.join(',')})"
			and = AND
		}

		//WhatLandTenure	All		Array: Fee Simple, Leasehold ,Leasehold-FA
		if(map.'WhatLandTenure'){
			whereClause +=  "$and ELT.Name in (${map.WhatLandTenure.collect{"'$it'"}.join(',')})"
			and = AND
		}
			
		//WhatAgent			All		Array: See Agent List (Use agent ID number)
		if(map.'WhatAgent'){
			whereClause += "$and LA1AgentUserCode in (${ map.WhatAgent.join(',')})"
			and = AND
		}

		//WhatOffice	 	All		Array: See Office List (Use Office ID Number)
		if(map.'WhatOffice'){
			whereClause += "$and LO1OfficeAbbreviation in (${ map.WhatOffice.join(',')})"
			and = AND
		}
		//WhatStartBed	 	No 		Lower Limit	Integer
		if(map.'WhatStartBed'){
			whereClause +=  "$and Beds >= ${map.WhatStartBed}"
			and = AND
		}

		//WhatEndBed	 	No 		Upper Limit	Integer
		if(map.'WhatEndBed'){
			whereClause +=  "$and Beds <= ${map.WhatEndBed}"
			and = AND
		}
		
		//WhatStartBath	 	No 		Lower Limit	Integer
		if(map.'WhatStartBath'){
			whereClause +=  "$and Baths >= ${map.WhatStartBath}"
			and = AND
		}

		//WhatEndBath	 	No 		Upper Limit	Integer
		if(map.'WhatEndBath'){
			whereClause +=  "$and Baths <= ${map.WhatEndBath}"
			and = AND
		}
		
		//WhatStartIntArea	No 		Lower Limit	Integer (Square Feet)
		if(map.'WhatStartIntArea'){
			whereClause +=  "$and LivingAreaSqFt >= ${map.WhatStartIntArea}"
			and = AND
		}

		//WhatEndIntArea	No 		Upper Limit	Integer (Square Feet)
		if(map.'WhatEndIntArea'){
			whereClause +=  "$and LivingAreaSqFt <= ${map.WhatEndIntArea}"
			and = AND
		}

		//WhatStartExtArea	No 		Lower Limit	Integer (Square Feet)
		if(map.'WhatStartExtArea'){
			whereClause +=  "$and LandSQFT >= ${map.WhatStartExtArea}"
			and = AND
		}

		//WhatEndExtArea	No 		Upper Limit	Integer (Square Feet)
		if(map.'WhatEndExtArea'){
			whereClause +=  "$and LandSQFT <= ${map.WhatEndExtArea}"
			and = AND
		}
		
		//ExAgency Yes?Include exclusive agency listings in the query.
		if(map.'ExAgencyYes'){
			whereClause +=  "$and ListingAgreement = 1"
			and = AND
		}
		
		//WhatDivAny?Array: ?
		if(map.'Div'){
			whereClause += "$and Div in (${ map.Div.join(',')})"
			and = AND
		}
	
		//WhatZoneAnyAny?Array: ?
		if(map.'Zone'){
			whereClause += "$and Zone in (${ map.Zone.join(',')})"
			and = AND
		}

		//WhatSecAny?Array: ?
		if(map.'Sec'){
			whereClause += "$and Sec in (${ map.Sec.join(',')})"
			and = AND
		}

		//WhatPlatAny?Array: ?
		if(map.'Plat'){
			whereClause += "$and Plat in (${ map.Plat.join(',')})"
			and = AND
		}

		//WhatParAny?Array: ?
		if(map.'Par'){
			whereClause += "$and Par in (${ map.Par.join(',')})"
			and = AND
		}
				
		//WhatStartDate	 	Any		List Date in the format: Year-Month-Day (xxxx-xx-xx)
		if(map.'WhatStartDate'){
			whereClause +=  "$and ListDate >= '${map.WhatStartDate}'"
			and = AND
		}

		//WhatEndDate	 	Any?		List Date in the format: Year-Month-Day (xxxx-xx-xx)
		if(map.'WhatEndDate'){
			whereClause +=  "$and ListDate <= '${map.WhatEndDate}'"
			and = AND
		}
				
		//These values represent two corners of a rectangle. Any listings who's Latitude and Longitude fall within these values are returned.
		//WhatLat1	 		None
		//WhatLon1	 		None
		//WhatLat2	 		None?MAKES NO DIFF(RS)
		//WhatLon2	 		None?MAKES NO DIFF(RS)
		//WhatRadius	 	None	If WhatRadius is passed, WhatLat1 and WhatLon1 are used as the center of a circle and returned listings are those that fall within the radius. If a radius is passed, WhatLat2 and WhatLon2 are ignored.
		if(map.'WhatRadius'){
			whereClause += "$and acos(sin(1.3963) * sin(${map.'WhatLat1'}) + cos(1.3963) * cos(${map.'WhatLat1'}) * cos(${map.'WhatLon1'} - (-0.6981))) * 6371 <= ${map.'WhatRadius'}"
			and = AND
		}else{
			if(map.'WhatLat1' || map.'WhatLat2'){
				def crds = []
				if(map.'WhatLat1'){crds.add("GeoLatitude LIKE '${map.WhatLat1.replaceAll(/0*$/, "")}%'")}//def crd = map.WhatLon1.replaceAll(/0*$/, "")
				if(map.'WhatLat2'){crds.add("GeoLatitude LIKE '${map.WhatLat2.replaceAll(/0*$/, "")}%'")}
				if(!crds.empty){
					whereClause += (crds.size == 1)? "$and ${crds.get(0)}":" $and (${crds.get(0)} OR ${crds.get(1)})"
					and = AND
				}
			}
			
			if(map.'WhatLon1' || map.'WhatLon2'){
				def crds = []
				if(map.'WhatLon1'){crds.add("GeoLongitude LIKE '${map.WhatLon1.replaceAll(/0*$/, "")}%'")}//def crd = map.WhatLon1.replaceAll(/0*$/, "")
				if(map.'WhatLon2'){crds.add("GeoLongitude LIKE '${map.WhatLon2.replaceAll(/0*$/, "")}%'")}
				if(!crds.empty){
					whereClause += (crds.size == 1)? "$and ${crds.get(0)}":" $and (${crds.get(0)} OR ${crds.get(1)})"
					and = AND
				}
			}

		}
		
		//WhatPool	 		None	Boolean field for properties with Pool
		if(map.'WhatPool'){
			whereClause +=  "$and Pool = '${map.'WhatPool' == 'Y'}'"
			and = AND
		}
		
		//if the WhatMLS array is passed all other variables are ignored, except sort variables.
		//WhatMLS	 		None	//Comma delimited list of mls numbers.
		if(map.'WhatMLS'){
			whereClause =  " MLSNumber in (${ map.WhatMLS.collect{"'$it'"}join(',')})"
			//map.'WhatNumber'= map.'WhatMLS'.size()
			//and = AND
		}

		if(isPaginationOn){
			
			//if agent or office bring listings for them first
			//MeFirst N If set to Y, the Agent or Office listing will be returned first.
			if(map.'MeFirst' == 'Y'){
				def ordMF =	(map.'WhatAgent')?" ORDER BY CASE WHEN LA1AgentUserCode in (${ map.WhatAgent.join(',')} THEN 1 ELSE 2 END ASC, LA1AgentUserCode":""
				ordMF = (map.'WhatOffice')?"ORDER BY CASE WHEN LO1OfficeAbbreviation in (${ map.WhatOffice.join(',')} THEN 1 ELSE 2 END ASC, LO1OfficeAbbreviation":""
				whereClause =  ordMF
			}else{
				//WhatSortType1	 	MLS #	You may sort by any field returned.
				//WhatSortDirection1|Ascending	Direction to sort by, ASC or DESC
				//WhatSortType2	 	None	You may include a secondary sort using any field returned.
				//WhatSortDirection2|None	Direction for secondary sort, ASC or DESC
				whereClause += " ORDER by ${map.WhatSortType1 ?: 'MLSNumber'} ${map.WhatSortDirection1?: 'ASC'}"
				whereClause += (map.'WhatSortType2')?", ${map.WhatSortType2} ${map.WhatSortDirection2 ?:''}":''
			}	
			
			//WhatNumber max 200 for short, 100 for solds and 20 for search. Defaults Expected to be set accordingly
			int pageSize = (map.WhatNumber?:'20').toInteger()
			//WhatPage	 	1	Page of data to return
			int pageNumber =( map.WhatPage?:'1').toInteger()
			int offset = pageSize * (pageNumber - 1)
			whereClause += " OFFSET $offset ROWS FETCH NEXT $pageSize ROWS ONLY"
		}
		
		return whereClause
	}

}
