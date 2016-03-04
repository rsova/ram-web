package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.StateZoning;

@Service
class StateZoningRepository {
	
	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_StateZoning";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,StateZoning> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			StateZoning elem = new StateZoning();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public StateZoning findStateZoning (Object id) {
		return cache.get(id);
	}

	public List<StateZoning> listStateZonings () {
		List list = [];
		for (StateZoning e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
