use diesel::prelude::*;
use diesel::mysql::MysqlConnection;

#[derive(Queryable, Insertable, Debug)]
#[table_name = "todos"]
pub struct Todo {
    pub id: i32,
    pub title: String,
    pub completed: bool,
}

impl Todo {
    pub fn create(todo: Todo, connection: &MysqlConnection) -> Todo {
        diesel::insert_into(todos::table)
            .values(&todo)
            .execute(connection)
            .expect("Error creating new todo");

        todos::table.order(todos::id.desc()).first(connection).unwrap()
    }

    pub fn read(connection: &MysqlConnection) -> Vec<Todo> {
        todos::table.order(todos::id.asc()).load::<Todo>(connection).unwrap()
    }

    pub fn update(id: i32, todo: Todo, connection: &MysqlConnection) -> bool {
        diesel::update(todos::table.find(id)).set(&todo).execute(connection).is_ok()
    }

    pub fn delete(id: i32, connection: &MysqlConnection) -> bool {
        diesel::delete(todos::table.find(id)).execute(connection).is_ok()
    }
}
