package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DMLStatement {

	private String sql;

	private Optional<InputMapper> inputMapper;

	public DMLStatement(String sql, InputMapper inputMapper) {
		this.sql = Objects.requireNonNull(sql);
		this.inputMapper = Optional.ofNullable(inputMapper);
	}

	public List<Integer> execute() throws SQLException {
		List<Integer> resultList = new ArrayList<>();
		ResultSet rs = H2Database.getInstance().executeUpdate(this.sql, this.inputMapper);
		while (rs.next()) {
			resultList.add(rs.getInt(1));
		}
		rs.close();
		return resultList;
	}
}
