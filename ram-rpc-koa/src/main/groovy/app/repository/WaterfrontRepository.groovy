package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.Waterfront;

@Service
class WaterfrontRepository {
	
	private final static String ALL_ELEMENTS_SQL =
		"SELECT * FROM Enum_Waterfront";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,Waterfront> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Waterfront elem = new Waterfront();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public Waterfront findWaterfront (Object id) {
		return cache.get(id);
	}

	public List<Waterfront> listWaterfronts () {
		List list = [];
		for (Waterfront e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
