package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.Roads;

@Service
class RoadsRepository {
	
	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_Roads";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,Roads> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Roads elem = new Roads();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public Roads findRoads (Object id) {
		return cache.get(id);
	}

	public List<Roads> listRoads () {
		List list = [];
		for (Roads e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
