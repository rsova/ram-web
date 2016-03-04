package app.service

import java.math.MathContext

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import app.repository.ShortSearchRepository
import app.service.sqlserver.RamSqlService
import app.websearch.rpc.helper.XmlRpcHelper

@Service
class ShortMethodService {

	public static final String METHOD_NAME = 'short';
	
	public static final Integer MAX_RESULT_COUNT = 200;
	
	public static final Integer DEFAULT_PAGE = 1;
	
	public static final String DEFAULT_SORT_DIRECTION = 'ASC';
	
	public static final String DEFAULT_SORT_TYPE = 'MLSNumber';
	
	public static final String DEFAULT_CONDO_PROP_TYPE = 'Condominium';
	
	public static final String DEFAULT_OHANA_PROP_TYPE = 'Residential';
	
	
	@Autowired
	private ShortSearchRepository shortRepo;
	
	
	public List shortSearch (Map params) {
		def start = Date.getMillisOf(new Date())
		setDefaults(params);
		List propertyList = shortRepo.listShort(params);
		propertyList = XmlRpcHelper.escapeXml(propertyList);
		// Use photoCount property to generate images
		// when done remove the photoCount from final result
		for (property in propertyList) {
			RamSqlService.generateImages(property, 'Image')
			property?.remove('PhotoCount')
		}

		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
		
	    int numberOfListings = propertyList.isEmpty() ? 0 : propertyList?.get(0)?.getAt(RamSqlService.TOTAL_CNT);
		int pageNumber = (params.'WhatPage').toInteger();
		int pageSize = (params.'WhatNumber').toInteger();
		def numberOfPages = (numberOfListings < pageSize) ? 1 : new BigDecimal(numberOfListings/pageSize, new MathContext(1, java.math.RoundingMode.UP));
		
		def buffSize =  pageSize - numberOfListings > 0 ?pageSize - numberOfListings: 0
		def optimizedList = RamSqlService.optimizeForDisplay(propertyList, buffSize);
		return [numberOfListings, pageNumber, numberOfPages as Double, time, optimizedList, RamSqlService.LEGAL] 
	
	}
	
	private static void setDefaults (Map params) {
		// WhatNumber	Default: 200		Number of listings to return; Max number is 200
		if (!params.'WhatNumber') {
			params.put('WhatNumber', MAX_RESULT_COUNT.toString());
		} else {
			Integer num = params.'WhatNumber'.toInteger();
			if (num > MAX_RESULT_COUNT) {
				params.put('WhatNumber', MAX_RESULT_COUNT.toString());
			}
		}
		
		// WhatPage	 	Default: 1			Page of data to return
		if (!params.'WhatPage') {
			params.put('WhatPage', DEFAULT_PAGE.toString());
		}
		
		// WhatSortType1	 	Default: 'MLSNUMBER'		You may sort by any field returned.
		if (!params.'WhatSortType1') {
			params.put('WhatSortType1', DEFAULT_SORT_TYPE);
		}
		
		// WhatSortDirection1	 	Default: 'ASC'			Direction to sort by, ASC or DESC
		if (!params.'WhatSortDirection1') {
			params.put('WhatSortDirection1', DEFAULT_SORT_DIRECTION);
		}
		
		if (!params.'WhatPropType') {
			if (params.'WhatCondo') {
				params.put('WhatPropType', [DEFAULT_CONDO_PROP_TYPE]);
			} else if (params.'WhatOhana') {
				params.put('WhatPropType', [DEFAULT_OHANA_PROP_TYPE]);
			}
		} else if (!(params.'WhatPropType' instanceof Collection)) {
			//if property type sent as single string, wrap it in collection
			params.put('WhatPropType', [params.'WhatPropType']);
		}

	}
}
