package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DQLStatement<T> {
	
	private String sql;
	
	private Optional<InputMapper> inputMapper;
	
	private RowMapper<T> rowMapper;
	
	public DQLStatement(String sql, InputMapper inputMapper, RowMapper<T> rowMapper) {
		this.sql = Objects.requireNonNull(sql);
		this.inputMapper = Optional.ofNullable(inputMapper);
		this.rowMapper = Objects.requireNonNull(rowMapper);
	}
	
	public List<T> execute() throws SQLException {
		List<T> resultList = new ArrayList<>();
		ResultSet rs = H2Database.getInstance().executeQuery(this.sql, this.inputMapper);
		while (rs.next()) {
			resultList.add(this.rowMapper.mapRows(rs));
		}
		rs.close();
		return resultList;
	}
	
}
