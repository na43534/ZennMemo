use rocket::{routes, Rocket};

#[get("/")]
fn index() -> &'static str {
    "Hello, world!"
}

fn rocket() -> Rocket {
    rocket::ignite().mount("/", routes![index])
}

fn main() {
    rocket().launch();
}
