public class Database {
  private Connection conn;

  public void connect() throws SQLException {
    // データベースに接続
    conn = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/todo_db", "root", "password");
  }

  public void disconnect() throws SQLException {
    // データベースから切断
    if (conn != null) {
      conn.close();
    }
  }

  public void executeUpdate(String sql) throws SQLException {
    // SQL文を実行
    Statement stmt = conn.createStatement();
    stmt.executeUpdate(sql);
  }

  public ResultSet executeQuery(String sql) throws SQLException {
    // SQL文を実行し、結果を返す
    Statement stmt = conn.createStatement();
    return stmt.executeQuery(sql);
  }
}
