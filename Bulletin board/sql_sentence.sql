
CREATE TABLE users(
					id int primary key AUTO_INCREMENT
					, login_id varchar(20) unique  not null
					, password varchar(255)  not null
					, name vachar(10)  not null
					, branch_id  int  not null
					, role_id  int  not null
				);


/*投稿*/
CREATE TABLE postings(	
						id int primary key  AUTO_INCREMENT
						, subject varchar(50) not null
						, body   text   not null
						, category varchar(10) not null
						, insert_date TIMESTAMP
						, update_date TIMESTAMP
						, user_id int not null
					);

/*コメント*/
CREATE TABLE comments(	
						id int primary key AUTO_INCREMENT
						,body text not null
						,insert_date TIMESTAMP
						,update_date TIMESTAMP
						,user_id int not null
						,posting_id int not null
					);
					
/*支店*/
CREATE TABLE branches(	
						id int primary key AUTO_INCREMENT
						,branch varchar(10) not null
					);
					
/*部署.役職など*/				
CREATE TABLE roles(
					id int primary key AUTO_INCREMENT
					,role varchar(10) not null
				);