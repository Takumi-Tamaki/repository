package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Expense;

/**
 * 経費データを扱うDAO
 */
public class ExpenseDAO {
	/**
	 * クエリ文字列
	 */
	private static final String SELECT_ALL_QUERY = "SELECT  \n" +
			"APPRICATION_ID \n" +
			", EXPENSE_TITLE \n" +
			", APPRICATION_DATE \n" +
			", APPRICANT \n" +
			", PRICE \n" +
			", STATUS \n" +
			"FROM MS_EXPENSE  \n" +
			"ORDER BY  \n" +
			"APPRICATION_ID \n";
	private static final String SELECT_BY_ID_QUERY = "SELECT \n" +
			"APPRICATION_ID \n" +
			", EXPENSE_TITLE \n" +
			", APPRICATION_DATE \n" +
			", APPRICANT \n" +
			", PRICE \n" +
			", STATUS \n" +
			"FROM MS_EXPENSE \n" +
			"WHERE APPRICATION_ID = ? \n";
	private static final String INSERT_QUERY = "INSERT INTO MS_EXPENSE( \n" +
			"EXPENSE_TITLE \n" +
			", APPRICATION_DATE \n" +
			", APPRICANT \n" +
			", PRICE \n" +
			", STATUS \n" +
			") \n" +
			"VALUES( \n" +
			"? \n" +
			", ? \n" +
			", ? \n" +
			", ? \n" +
			", ? \n" +
			") \n";
	private static final String UPDATE_QUERY = "UPDATE MS_EXPENSE SET \n" +
			"EXPENSE_TITLE = ? \n" +
			", APPRICATION_DATE = ? \n" +
			", APPRICANT = ? \n" +
			", PRICE = ? \n" +
			", STATUS = ? \n" +
			"WHERE \n" +
			" APPRICATION_ID = ? \n";
	private static final String DELETE_QUERY = "DELETE FROM MS_EXPENSE \n" +
			"WHERE \n" +
			" APPRICATION_ID = ? \n";

	/**
	 * 部署の全件を取得する。
	 *
	 * @return DBに登録されている経費データ一部を収めたリスト。途中でエラーが発生した場合は空のリストを返す。
	 */
	public List<Expense> findAll() {
		List<Expense> result = new ArrayList<>();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (Statement statement = connection.createStatement();) {
			ResultSet rs = statement.executeQuery(SELECT_ALL_QUERY);

			while (rs.next()) {
//				result.add(processRow(rs));
//↑エラーの原因これ。多分すべてのカラムを指定していないから
				Expense expense = new Expense();
//				"APPRICATION_ID \n" +
//				", EXPENSE_TITLE \n" +
//				", APPRICATION_DATE \n" +
//				", APPRICANT \n" +
//				", PRICE \n" +
//				", STATUS \n"
				expense.setAppricationId(rs.getInt("APPRICATION_ID"));
				expense.setExpenseTitle(rs.getString("EXPENSE_TITLE"));
				expense.setAppricationDate(rs.getString("APPRICATION_DATE"));
				expense.setAppricant(rs.getString("APPRICANT"));
				expense.setPrice(rs.getInt("PRICE"));
				expense.setStatus(rs.getString("STATUS"));

				result.add(expense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * ID指定の検索を実施する。
	 *
	 * @param id 検索対象のID
	 * @return 検索できた場合は検索結果データを収めたExpenseインスタンス。検索に失敗した場合はnullが返る。
	 */
	public Expense findById(int id) {
		Expense result = new Expense();

		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return result;
		}

		try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
//				result = processRow(rs);

//				"APPRICATION_ID \n" +
//				", EXPENSE_TITLE \n" +
//				", APPRICATION_DATE \n" +
//				", APPRICANT \n" +
//				", PRICE \n" +
//				", STATUS \n" +
				result.setAppricationId(rs.getInt("APPRICATION_ID"));
				result.setExpenseTitle(rs.getString("EXPENSE_TITLE"));
				result.setAppricationDate(rs.getString("APPRICATION_DATE"));
				result.setAppricant(rs.getString("APPRICANT"));
				result.setPrice(rs.getInt("PRICE"));
				result.setStatus(rs.getString("STATUS"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return result;
	}

	/**
	 * 指定されたPostオブジェクトを新規にDBに登録する。
	 * 登録されたオブジェクトにはDB上のIDが上書きされる。
	 * 何らかの理由で登録に失敗した場合、IDがセットされない状態（=0）で返却される。
	 *
	 * @param post 登録対象オブジェクト
	 * @return DB上のIDがセットされたオブジェクト
	 */
	public Expense create(Expense post) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return post;
		}

		try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);) {
			// INSERT実行
			statement.setString(1, post.getExpenseTitle());
			statement.setString(2, post.getAppricationDate());
			statement.setString(3, post.getAppricant());
			statement.setInt(4, post.getPrice());
			statement.setString(5, post.getStatus());
			statement.executeUpdate();

			// INSERTできたらKEYを取得
			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			post.setAppricationId(id);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return post;
	}

	/**
	 * 指定されたPostオブジェクトを使ってDBを更新する。
	 *
	 * @param post 更新対象オブジェクト
	 * @return 更新に成功したらtrue、失敗したらfalse
	 */
	public boolean update(Expense post) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			statement.setString(1, post.getExpenseTitle());
			statement.setString(2, post.getAppricationDate());
			statement.setString(3, post.getAppricant());
			statement.setInt(4, post.getPrice());
			statement.setString(5, post.getStatus());
			statement.setInt(6,post.getAppricationId());

			count = statement.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}

		return count == 1;
	}

	/**
	 * 指定されたIDのExpenseデータを削除する。
	 *
	 * @param id 削除対象のPostデータのID
	 * @return 削除が成功したらtrue、失敗したらfalse
	 */
	public boolean remove(int id) {
		Connection connection = ConnectionProvider.getConnection();
		if (connection == null) {
			return false;
		}

		int count = 0;
		try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
			// DELETE実行
			statement.setInt(1, id);
			count = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionProvider.close(connection);
		}
		return count == 1;
	}

	/**
	 * 検索結果行をオブジェクトとして構成する。
	 * @param rs 検索結果が収められているResultSet
	 * @return 検索結果行の各データを収めたPostインスタンス
	 * @throws SQLException ResultSetの処理中発生した例外
	 */
	private Expense processRow(ResultSet rs) throws SQLException {
		Expense result = new Expense();
		result.setAppricationId(rs.getInt("APPRICATION_ID"));
		result.setExpenseTitle(rs.getString("EXPENSE_TITLE"));
		result.setAppricationDate(rs.getString("APPRICATION_DATE"));
		result.setUpdatePerson(rs.getString("UPDATE_PERSON"));
		result.setPrice(rs.getInt("PRICE"));
		result.setStatus(rs.getString("STATUS"));

		return result;
	}
}
