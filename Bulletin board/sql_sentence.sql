
CREATE TABLE users(
	id int primary key AUTO_INCREMENT
	, login_id varchar(20) unique  not null
	, password varchar(255)  not null
	, name vachar(10)  not null
	, branch_id  int  not null
	, role_id  int  not null
	);

/*投稿*/
CREATE TABLE posts(	
	id int primary key  AUTO_INCREMENT
	, subject varchar(50) not null
	, body   text(1000)   not null
	, category varchar(10) not null
	, user_id int not null
	, insert_date TIMESTAMP
	, update_date TIMESTAMP
	);

/*コメント*/
CREATE TABLE comments(	
	id int primary key AUTO_INCREMENT
	,body text(1000) not null
	,user_id int not null
	,post_id int not null
	,insert_date TIMESTAMP
	,update_date TIMESTAMP
	);

/*支店*/
CREATE TABLE branches(	
	id int primary key AUTO_INCREMENT
	,branchname varchar(255) not null
	);

/*部署.役職など*/				
CREATE TABLE roles(
	id int primary key AUTO_INCREMENT
	,rolename varchar(255) not null
	);