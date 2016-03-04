package app.service.sqlserver.helper;

import static org.junit.Assert.*

import org.junit.Test

import app.service.sqlserver.RamSqlService

class SqlHelperTest {

	@Test
	public void testParamsToSql_OpenHouse() {
		//	WhatOffice	 Array Officße ID Numbers
		//	WhatAgent	 Array Agent ID Numbers
		//	WhatType	 Array Residential, Vacant Land, Condominium
		//	WhatDistrict Array Use district
		Map map = [WhatOffice:[9986,1]]
		println  map.'WhatOffice'.collect{"'$it'"}.join(',')
		
		println SqlHelper.openHouseParamsToWhereClause(map)
		map = [WhatType:['Condominium']]
		println SqlHelper.openHouseParamsToWhereClause(map)
		map = [WhatOffice:[9986],WhatType:['Condominium']]
		println SqlHelper.openHouseParamsToWhereClause(map)
		map = [WhatOffice:[9986],WhatType:['Condominium'],WhatAgent:[5678]]
		println SqlHelper.openHouseParamsToWhereClause(map)
		
	}
	@Test
	public void testParamsToSql_Short() {
		Map map = [WhatMLS:[123,345]]
//		println "BuildingName1 in (${ map.WhatView.collect{"'$it'"}.join(',')})"
//		println "BuildingName1 in (${map.WhatView.collect{RamSqlService.VIEW_LIST.indexOf(it)}.join(',')})"
		println SqlHelper.shortParamsToWhereClause(map)
		map.remove('WhatMLS')
		map.put('WhatPropType', ['Residential', 'Vacant'])
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatAddressFull', 'South Kihei')
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatCondo', ['71 Miner Place','aina-nalu'])
		println SqlHelper.shortParamsToWhereClause(map)
		map.remove('WhatCondo')
		map.put('WhatStartPrice', 170000)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatEndPrice', 190000)
		println SqlHelper.shortParamsToWhereClause(map)
		map.remove('WhatPropType')
		map.put('WhatREO', 'Y')
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatPSS', 'Y')
		map.put('WhatPropType', [ 'Commercial'])
		
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatView', ['Ocean', 'Mountain'])
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatDistrict', [ 'Kaanapali', 'Kahakuloa'])
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatAgent', [ 1233, 568])
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatOffice', [ 54332, 54568])
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatStartBed', 2)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatEndBed', 4)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatStartBath', 1)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatEndBath', 2)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatStartIntArea', 1000)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatEndIntArea', 1000)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatStartExtArea', 2000)
		map.put('WhatEndExtArea', 5000)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatStartDate', '2014-11-12')
		map.put('WhatEndDate', '2014-12-21')
		println SqlHelper.shortParamsToWhereClause(map)

		map.put('WhatLat1', '20.9183216094971') 
		map.put('WhatLon1', '-156.695465087891')
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatLat2', '20.9183216094971') 
		map.put('WhatLon2', '-156.695465087891')
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatRadius', 25)
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatPool', 'Y')
		println SqlHelper.shortParamsToWhereClause(map)
				
		map.put('WhatSortType1', 'ListPrice')
		println SqlHelper.shortParamsToWhereClause(map)
		
		map.put('WhatSortType1', 'ListPrice')
		println SqlHelper.shortParamsToWhereClause(map)
		
		map.put('WhatPage', 1)
		println SqlHelper.shortParamsToWhereClause(map)
		map.remove('WhatPage')
		map.put('WhatPage', '2')
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatNumber', '5')
		println SqlHelper.shortParamsToWhereClause(map)
		
		
//		def displayName = map.WhatSortType1 ? map.WhatSortType1 : "Anonymous" //traditional ternary operator usage
//		println displayName
//		displayName = map.WhatSortType1 ?: "Anonymous" //traditional ternary operator usage
//		println displayName
	}
	
	@Test
	void testMissingSecondSortType() {
		def map = [MLS:'329222', WhatDistrict:['Haiku'], WhatSortType1:'ListPrice', WhatSortDirection1:'ASC', WhatSortDirection2:'ASC']
		println SqlHelper.shortParamsToWhereClause(map)
		map.put('WhatSortType2','MLSNumber')
		println SqlHelper.shortParamsToWhereClause(map)
		
	}
	
	@Test
	public void testParamsToSql_Solds() {
		Map map = [WhatPropType: 'Residential']
		println SqlHelper.soldsSqlFromRpcParamMap(map)
		//map.remove('WhatMLS')
	}


}
