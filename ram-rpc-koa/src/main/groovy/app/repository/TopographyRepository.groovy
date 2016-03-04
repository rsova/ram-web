package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.Topography;

@Service
class TopographyRepository {
	
	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_Topography";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,Topography> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			Topography elem = new Topography();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public Topography findTopography (Object id) {
		return cache.get(id);
	}

	public List<Topography> listTopographys () {
		List list = [];
		for (Topography e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
