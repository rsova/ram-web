package app.service

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import app.repository.SoldRepository
import app.service.sqlserver.RamSqlService

@Service
class SoldsMethodService {

	public static final String METHOD_NAME = 'solds';
	
	public static final Integer MAX_RESULT_COUNT = 100;
	
	public static final String DEFAULT_PROP_TYPE = "Residential";
	
	@Autowired
	private SoldRepository shortRepo;
	
	public List soldSearch (Map params) {
		def start = Date.getMillisOf(new Date());
		setDefaults(params);
		List response;
		if (params.'WhatPropType'.equals('Residential')) {
			response = shortRepo.listSoldResidential(params);
		} else if (params.'WhatPropType'.equals('Condominium')) {
			response = shortRepo.listSoldCondo(params);
		} else if (params.'WhatPropType'.equals('Vacant Land')){
			response = shortRepo.listSoldVacantLand(params);
		}
		// TODO handle case when WhatPropType is invalid
		
		double time = (Date.getMillisOf(new Date()) - start)/1000 //sec
		int numberOfListings = response.isEmpty() ? 0 : response?.get(0)?.getAt(RamSqlService.TOTAL_CNT)
		return [numberOfListings, time, RamSqlService.denullify(response)];
	}
	
	private static void setDefaults (Map params) {
		if (!params.'WhatPropType') {
			params.put('WhatPropType', DEFAULT_PROP_TYPE);
		}
	}
}
