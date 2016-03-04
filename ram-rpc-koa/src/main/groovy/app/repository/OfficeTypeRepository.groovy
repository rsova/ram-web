package app.repository

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service

import app.beans.OfficeType;
import app.beans.OfficeType

@Service
class OfficeTypeRepository {
	
	private final static Map<String,Object> STATIC_LIST = [
		'0': 'REALESTATEOFFICE',
		
		'1': 'LENDINGINSTITUTION',
		'2': 'APPRAISER',
		'3': 'NONMEMBER',
		'4': 'AFFILIATEOFFICE',
		'5': 'EXCHANGE',
		'6': 'ACCOUNTINGSERVICES',
		'7': 'ARCHITECT',
		'8': 'ATTORNEYS',
		'9': 'BANKS',
		
		'10': 'CARPETCLEANERS',
		'11': 'COMPUTERSERVICES',
		'12': 'DEVELOPERS',
		'13': 'FINANCIALADVISORS',
		'14': 'FLOORING',
		'15': 'FURNITURE',
		'16': 'HANDYMAN',
		'17': 'HAWAIINEWSANDINFO',
		'18': 'HOMEINPSECTIONSERVICE',
		'19': 'HOMESTAGING',
		
		'20': 'INSURANCECOMPANCY',
		'21': 'INTERIORDECORATING',
		'22': 'LANDSURVEYOR',
		'23': 'LANDSCAPE',
		'24': 'LENDORSMORTGAGECOMPANY',
		'25': 'NEIGHBORISLAND',
		'26': 'PESTCONTROL',
		'27': 'ONLINEMEMBER',
		'28': 'PHOTOGRAPHY',
		'29': 'REALESTATEMAGAZINES',
		
		'30': 'REALESTATESCHOOL',
		'31': 'STATEBOARDHAR',
		'32': 'SPECIALTYITEMS',
		'33': 'TITLECOMPANIES',
		'34': 'VACATIONRENTALS',
		'35': 'VIRTUALTOURS'
		
	];

	private final static String ALL_ELEMENTS_SQL =
		"SELECT * FROM Enum_OfficeType";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,OfficeType> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			OfficeType type = new OfficeType();
			type.setId(row.get("Id"));
			type.setName(row.get("Name"));
			cache.put(type.getId(), type);
		}
	}

	public OfficeType findOfficeType (Object id) {
		return cache.get(id);
	}

}
