package app.repository

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import app.beans.View;

@Service
class ViewRepository {
	
	private final static String ALL_ELEMENTS_SQL =
	"SELECT * FROM Enum_View";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<Object,View> cache;

	@PostConstruct
	public init () {
		cache = new HashMap<>();
		List rows = jdbcTemplate.queryForList(ALL_ELEMENTS_SQL);
		for (Map row : rows) {
			View elem = new View();
			elem.setId(row.get("Id"));
			elem.setName(row.get("Name"));
			cache.put(elem.getId(), elem);
		}
	}

	public View findView (Object id) {
		return cache.get(id);
	}

	public List<View> listViews () {
		List list = [];
		for (View e : cache.values()) {
			list.add(e);
		}
		return list;
	}

}
