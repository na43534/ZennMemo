#[get("/todos")]
fn index(conn: DbConn) -> Template {
    let todos = Todo::all(&conn);
    let context = Context {
        title: "TODO List".to_owned(),
        todos: todos,
    };
    Template::render("index", &context)
}

#[get("/todos/new")]
fn new() -> Template {
    let context = Context {
        title: "New TODO".to_owned(),
        todos: vec![],
    };
    Template::render("create", &context)
}

#[get("/todos/<id>/edit")]
fn edit(id: i32, conn: DbConn) -> Template {
    let todos = Todo::get(id, &conn);
    let context = Context {
        title: "Edit TODO".to_owned(),
        todos: todos,
    };
    Template::render("update", &context)
}

#[delete("/todos/<id>")]
fn delete(id: i32, conn: DbConn) -> Redirect {
    let todo = Todo::get(id, &conn).remove(0);
    todo.delete(&conn);
    Redirect::to("/todos")
}

#[post("/todos", data = "<todo>")]
fn create(todo: Form<Todo>, conn: DbConn) -> Redirect {
    let todo = todo.into_inner();
    todo.create(&conn);
    Redirect::to("/todos")
}

#[put("/todos/<id>", data = "<todo>")]
fn update(id: i32, todo: Form<Todo>, conn: DbConn) -> Redirect {
    let todo = todo.into_inner();
    todo.update(id, &conn);
    Redirect::to("/todos")
}
