package app.repository

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import app.beans.CountyZoning;

class CountyZoningRepository {
	
	private final static String ALL_ELEMENTS_SQL =
		"SELECT * FROM Enum_CountyZoning";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Map<Object,CountyZoning> cache;
	
	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			CountyZoning type = new CountyZoning();
			type.setId(row.get("Id"));
			type.setName(row.get("Name"));
			cache.put(type.getId(), type);
		}
	}
	
	public CountyZoning findCountyZoning (Object id) {
		return cache.get(id);
	}

}
