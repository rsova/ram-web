package app.repository

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service

import app.beans.District;
import app.beans.District

// TODO Normalize
@Service
class DistrictRepository {

	private final static Map<String,Object> STATIC_LIST = [
		'0': 'Haiku',
//		
//		'1': 'Hana',
//		'2': 'Honokohau',
//		'3': 'Kaanapali',
//		'4': 'Kahakuloa',
//		'5': 'Kahului',
//		'6': 'Kapalua',
//		'7': 'Kaupo',
//		'8': 'Keanae',
//		'9': 'Kihei',
//		
//		'10': 'Kipahulu',
//		'11': 'Kula/Ulupalakua/Kanaio',
//		'12': 'Lahaina',
//		'13': 'Lanai',
//		'14': 'Maalaea',
//		'15': 'Makawao/Olinda/Haliimaile',
//		'16': 'Maui Meadows',
//		'17': 'Molokai',
//		'18': 'Nahiku',
//		'19': 'Napili/Kahana/Honokowai',
//		
//		'20': 'Olowalu',
//		'21': 'Pukalani',
//		'22': 'Spreckelsville/Paia/Kuau',
//		'23': 'Wailea/Makena',
//		'24': 'Wailuku'
		
	];

	private final static String ALL_ELEMENTS_SQL =
		"SELECT LegacyName as name FROM Enum_District order by LegacyName";

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	private Map<Object,District> cache;
	private List cache = [];

	@PostConstruct
	public init () {
//		cache = new HashMap<>();
		
		// TODO fill cache from database instead of static list
		cache = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
//		for (Map row : rows) {
//			District type = new District();
//			type.setId(row.get("Id"));
//			type.setName(row.get("LegacyName"));
//			cache.put(type.getId(), type);
//		}
//		for (def e : STATIC_LIST) {
//			District type = new District();
//			type.setId(e.getKey());
//			type.setName(e.getValue());
//			cache.put(type.getId(), type);
//		}
	}

//	public District findDistrict (Object id) {
//		return cache.get(id);
//	}
	
	public List<District> listDistricts () {
//		List districts = [];
//		for (District d : cache.values()) {
//			districts.add(d);
//		}
		return cache;
	}

}
