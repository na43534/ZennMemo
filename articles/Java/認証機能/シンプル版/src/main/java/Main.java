import org.skife.jdbi.v2.DBI;
import spark.Spark;

public class Main {
  public static void main(String[] args) {
    DBI dbi = new DBI(/* DataSourceを指定する */);
    UserDao userDao = dbi.onDemand(UserDao.class);

    Spark.staticFiles.location("/web");

    Spark.before((req, res) -> {
      // ログインしているか確認する
      if (req.session().attribute("user") == null && !req.pathInfo().equals("/login")) {
        // ログインしていない場合はログイン画面にリダイレクトする
        res.redirect("/login");
      }
    });

    Spark.get("/", (req, res) -> {
      // ログイン済みのユーザーのホーム画面を表示する
      User user = req.session().attribute("user");
      return Spark.modelAndView(Map.of("user", user), "home.html");
    });

    Spark.get("/login", (req, res) -> {
      // ログイン画面を表示する
      return Spark.modelAndView(null, "login.html");
    });

    Spark.post("/login", (req, res) -> {
      // ログイン処理を行う
      String username = req.queryParams("username");
      String password = req.queryParams("password");
      Optional<User> user = userDao.findByUsernameAndPassword(username, password);
      if (user.isPresent()) {
        // ログイン成功
        req.session(true).attribute("user", user.get());
        res.redirect("/");
      } else {
        // ログイン失敗
        res.redirect("/login");
      }
      return null;
    });

    Spark.get("/logout", (req, res) -> {
        // ログアウト処理
        req.session().removeAttribute("user");
        res.redirect("/login");
        return null;
        });

        Spark.get("/change-password", (req, res) -> {
        // パスワード変更画面を表示する
        User user = req.session().attribute("user");
        return Spark.modelAndView(Map.of("user", user), "change-password.html");
        });

        Spark.post("/change-password", (req, res) -> {
        // パスワード変更処理を行う
        User user = req.session().attribute("user");
        String password = req.queryParams("password");
        userDao.updatePassword(user.getId(), password);
        res.redirect("/");
        return null;
    });

Spark.awaitInitialization();