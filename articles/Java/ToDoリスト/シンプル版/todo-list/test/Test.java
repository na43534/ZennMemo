public class Test {
  public static void main(String[] args) throws SQLException {
    // TODOリストを作成
    TodoList todoList = new TodoList();

    // タスクを追加
    todoList.create("新しいタスク1");
    todoList.create("新しいタスク2");

    // タスクを取得
    List<String> tasks = todoList.getAll();
    System.out.println(tasks); // ["新しいタスク1", "新しいタスク2"]

    // タスクを更新
    todoList.update(1, "更新されたタスク1");
    tasks = todoList.getAll();
    System.out.println(tasks); // ["更新されたタスク1", "新しいタスク2"]

    // タスクを削除
    todoList.delete(1);
    tasks = todoList.getAll();
    System.out.println(tasks); // ["新しいタスク2"]
  }
}