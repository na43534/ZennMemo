public class TodoList {
  private Database db;

  public TodoList() {
    db = new Database();
  }

  public void create(String task) throws SQLException {
    // データベースに新しいタスクを追加
    db.executeUpdate("INSERT INTO tasks (task) VALUES ('" + task + "')");
  }

  public List<String> getAll() throws SQLException {
    // データベースからすべてのタスクを取得
    ResultSet rs = db.executeQuery("SELECT task FROM tasks");
    List<String> tasks = new ArrayList<>();
    while (rs.next()) {
      tasks.add(rs.getString("task"));
    }
    return tasks;
  }

  public void update(int id, String task) throws SQLException {
    // データベースのタスクを更新
    db.executeUpdate("UPDATE tasks SET task = '" + task + "' WHERE id = " + id);
  }

    public void delete(int id) throws SQLException {
    // データベースのタスクを削除
    db.executeUpdate("DELETE FROM tasks WHERE id = " + id);
  }
}

