public class Main {
  public static void main(String[] args) throws SQLException {
    // TODOリストを作成
    TodoList todoList = new TodoList();

    // HTMLを表示
    String html = "<html>" +
      "<head>" +
      "<link rel=\"stylesheet\" href=\"
      "<link rel=\"stylesheet\" href=\"style.css\">" +
      "</head>" +
      "<body>" +
      "<h1>TODOリスト</h1>" +
      "<table>" +
      "<tr>" +
      "<th>ID</th>" +
      "<th>タスク</th>" +
      "<th>操作</th>" +
      "</tr>";
    // TODOリストを取得してHTMLに追加
    List<String> tasks = todoList.getAll();
    for (String task : tasks) {
      html += "<tr><td>1</td><td>" + task + "</td><td><a href=\"/delete?id=1\">削除</a></td></tr>";
    }
    html += "</table>" +
      "<form action=\"/create\" method=\"post\">" +
      "<label for=\"task\">新しいタスク:</label>" +
      "<input type=\"text\" name=\"task\" id=\"task\">" +
      "<input type=\"submit\" value=\"追加\">" +
      "</form>" +
      "</body>" +
      "</html>";
    // HTMLを表示
    System.out.println(html);
  }
}
