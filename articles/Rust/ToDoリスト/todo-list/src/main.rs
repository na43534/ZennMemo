// use rocket::{routes, Rocket};

// #[get("/")]
// fn index() -> &'static str {
//     "Hello, world!"
// }

// fn rocket() -> Rocket {
//     rocket::ignite().mount("/", routes![index])
// }

// fn main() {
//     rocket().launch();
// }


#![feature(proc_macro_hygiene, decl_macro)]

#[macro_use]
extern crate rocket;
#[macro_use]
extern crate rocket_contrib;
#[macro_use]
extern crate serde_derive;

use dotenv::dotenv;
use rocket::{Route, State};
use rocket_contrib::templates::Template;
use std::env;

mod db;
mod models;
mod schema;

#[derive(Serialize)]
struct Context {
    todos: Vec<models::Todo>,
    title: String,
}

#[get("/")]
fn index(conn: db::DbConn) -> Template {
    let todos = models::Todo::all(&conn);
    let context = Context {
        todos,
        title: "TODO List".to_string(),
    };
    Template::render("index", &context)
}

#[get("/todos/new")]
fn new() -> Template {
    let context = Context {
        todos: vec![],
        title: "New TODO".to_string(),
    };
    Template::render("create", &context)
}

#[post("/todos", data = "<todo>")]
fn create(todo: Form<models::Todo>, conn: db::DbConn) -> Redirect {
    let todo = todo.into_inner();
    todo.create(&conn);
    Redirect::to("/")
}

#[get("/todos/<id>/edit")]
fn edit(id: i32, conn: db::DbConn) -> Template {
    let todo = models::Todo::get(id, &conn);
    let context = Context {
        todos: todo,
        title: "Edit TODO".to_string(),
    };
    Template::render("update", &context)
}

#[put("/todos/<id>", data = "<todo>")]
fn update(id: i32, todo: Form<models::Todo>, conn: db::DbConn) -> Redirect {
    let todo = todo.into_inner();
    todo.update(id, &conn);
    Redirect::to("/")
}

#[delete("/todos/<id>")]
fn delete(id: i32, conn: db::DbConn) -> Redirect {
    let todo = models::Todo::get(id, &conn);
    if let Some(todo) = todo.first() {
        todo.delete(&conn);
    }
    Redirect::to("/")
}

fn main() {
    dotenv().ok();
    let database_url =



このアプリケーションでは、Rocket ウェブフレームワークを使用して、TODO の CRUD 操作を実装しています。Diesel ORM を使用して、MySQL データベースとやり取りしています。

### src/db.rs

```rust
use diesel::r2d2::{ConnectionManager, Pool};
use diesel::{r2d2, PgConnection, Result as DieselResult};
use rocket::http::Status;
use rocket::request::{self, FromRequest};
use rocket::{Outcome, Request, State};
use std::env;
use std::ops::Deref;

pub type DbPool = Pool<ConnectionManager<PgConnection>>;

pub fn init_pool(database_url: &str) -> DbPool {
    let manager = ConnectionManager::<PgConnection>::new(database_url);
    r2d2::Pool::new(manager).expect("db pool")
}

pub struct DbConn(pub r2d2::PooledConnection<ConnectionManager<PgConnection>>);

impl<'a, 'r> FromRequest<'a, 'r> for DbConn {
    type Error = ();

    fn from_request(request: &'a Request<'r>) -> request::Outcome<DbConn, ()> {
        let pool = request.guard::<State<DbPool>>()?;
        match pool.get() {
            Ok(conn) => Outcome::Success(DbConn(conn)),
            Err(_) => Outcome::Failure((Status::ServiceUnavailable, ())),
        }
    }
}

impl Deref for DbConn {
    type Target = PgConnection;

    fn deref(&self) -> &Self::Target {
        &self.0
    }
}
