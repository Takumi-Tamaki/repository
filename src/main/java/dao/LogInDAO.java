package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.LogIn;

public class LogInDAO {

	private static final String SELECT_ALL_QUERY = "select \n" +
			"* \n" +
			"from \n" +
			"password \n";


	public List<LogIn> findAll() {
		List<LogIn> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while (rs.next()) {
				LogIn logIn = new LogIn();

				logIn.setId(rs.getInt("ID"));
				logIn.setPass(rs.getString("PASS"));

				System.out.println(logIn.getId()+":"+logIn.getPass());
				result.add(logIn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

}
