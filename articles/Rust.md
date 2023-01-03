// 性別クラス
enum Sex {
Man,
Woman,
Okama,
}

// 抽象クラス（生き物クラス）
struct Creature {
name: String,
hp: u32,
attack_min: u32,
attack_max: u32,
}

impl Creature {
fn set_name(&mut self, str: String) {
self.name = str;
}

Copy code
fn get_name(&self) -> String {
    self.name.clone()
}

fn set_hp(&mut self, num: u32) {
    self.hp = num;
}

fn get_hp(&self) -> u32 {
    self.hp
}

fn attack(&self, target_obj: &mut Creature) {
    let attack_point = rand::thread_rng().gen_range(self.attack_min, self.attack_max + 1);
    if rand::thread_rng().gen_range(0, 10) == 0 {
        let attack_point = (attack_point as f32 * 1.5) as u32;
        println!("{}のクリティカルヒット!!", self.get_name());
    }
    target_obj.set_hp(target_obj.get_hp() - attack_point);
    println!("{}ポイントのダメージ！", attack_point);
}
}

// 人クラス
struct Human {
creature: Creature,
sex: Sex,
}

impl Human {
fn new(name: String, sex: Sex, hp: u32, attack_min: u32, attack_max: u32) -> Human {
Human {
creature: Creature {
name,
hp,
attack_min,
attack_max,
},
sex,
}
}

Copy code
fn set_sex(&mut self, sex: Sex) {
    self.sex = sex;
}

fn get_sex(&self) -> Sex {
    self.sex
}

fn say_cry(&self) {
    println!("{}が叫ぶ！", self.creature.get_name());
    match self.get_sex() {
        Sex::Man => println!("ぐはぁっ！"),
        Sex::Woman => println!("きゃっ！"),
        Sex::Okama => println!("もっと！♡"),
    }
}
}

// モンスタークラス
struct Monster {
creature: Creature,
img: String,
}

impl Monster {
fn new(name: String, hp: u32, img: String, attack_min: u32, attack_max: u32) -> Monster {
Monster {
creature: Creature {
name,
hp,
attack_min,
attack_max,
},
img,
}
}

Copy code
fn get_img(&self) -> String {
    self.img.clone()
}

// モンスタークラス
struct Monster {
// プロパティ
img: String,
name: String,
hp: i32,
attack_min: i32,
attack_max: i32,
}

impl Monster {
// コンストラクタ
fn new(name: String, hp: i32, img: String, attack_min: i32, attack_max: i32) -> Self {
Monster {
name,
hp,
img,
attack_min,
attack_max,
}
}

Copy code
// ゲッター
fn get_img(&self) -> &String {
    &self.img
}

fn say_cry(&self) {
    History::set(format!("{}が叫ぶ！", self.name));
    History::set("はうっ！");
}
}

// 魔法を使えるモンスタークラス
struct MagicMonster {
monster: Monster,
magic_attack: i32,
}

impl MagicMonster {
fn new(name: String, hp: i32, img: String, attack_min: i32, attack_max: i32, magic_attack: i32) -> Self {
MagicMonster {
monster: Monster::new(name, hp, img, attack_min, attack_max),
magic_attack,
}
}

Copy code
fn get_magic_attack(&self) -> i32 {
    self.magic_attack
}

fn attack(&self, target: &mut Monster) {
    if rand::random::<i32>() % 5 == 0 {
        // 5分の1の確率で魔法攻撃
        History::set(format!("{}の魔法攻撃!!", self.monster.name));
        target.hp -= self.magic_attack;
        History::set(format!("{}ポイントのダメージを受けた！", self.magic_attack));
    } else {
        self.monster.attack(target);
    }
}
}

trait HistoryInterface {
fn set(str: String);
fn clear();
}

// 履歴管理クラス（インスタンス化して複数に増殖させる必要性がないクラスなので、staticにする）
struct History {}

impl HistoryInterface for History {
fn set(str: String) {
// セッションhistoryが作られてなければ作る
if let None = SESSION.get("history") {
SESSION.insert("history".to_string(), "".to_string());
}
let history = SESSION.get_mut("history").unwrap();
// 文字列をセッションhistoryへ格納
*history += &format!("{}<br>", str);

use std::collections::HashMap;

struct Human {
name: String,
sex: Sex,
hp: u32,
attack: u32,
defense: u32,
}

enum Sex {
Okama,
Male,
Female,
}

struct Monster {
name: String,
hp: u32,
img: String,
attack: u32,
defense: u32,
}

struct MagicMonster {
name: String,
hp: u32,
img: String,
attack: u32,
defense: u32,
magic_attack: u32,
}

struct History {
static history: Vec<String>,
}

impl History {
fn clear() {
history = Vec::new();
}

Copy code
fn set(s: String) {
    history.push(s);
}

fn get_history() -> Vec<String> {
    history
}
}

impl Human {
fn attack(&self, monster: &mut Monster) {
let mut damage = self.attack - monster.defense;
if damage < 0 {
damage = 0;
}
monster.hp -= damage;
}

Copy code
fn say_cry(&self) {
    println!("{}：うわあああああ！！", self.name);
}

fn get_name(&self) -> String {
    self.name.clone()
}

fn get_hp(&self) -> u32 {
    self.hp
}
}

impl Monster {
fn attack(&self, human: &mut Human) {
let mut damage = self.attack - human.defense;
if damage < 0 {
damage = 0;
}
human.hp -= damage;
}

Copy code
fn say_cry(&self) {
    println!("{}：がおーーーー！！", self.name);
}

fn get_name(&self) -> String {
    self.name.clone()
}

fn get_hp(&self) -> u32 {
    self.hp
}
}

impl MagicMonster {
fn attack(&self, human: &mut Human) {
let mut damage = self.attack - human.defense;
if damage < 0 {
damage = 0;
}
damage += self.magic_attack;
human.hp -= damage;
}

Copy code
fn say_cry(&self) {
    println!("{}：ふふっ、このまじょのまじょにかかったな！！", self.name);
}

fn get_name(&self) -> String {
    self.name.clone()
}

fn get_hp(&self) -> u32 {
    self.hp
}
}

fn create_monster() -> Monster {
let monsters = vec![
Monster {
name: "フランケン".to_string(),
hp: 100,
img: "img/monster01.png".to_string