CREATE USER '@db.username@'@'%' IDENTIFIED BY '@db.password@';
GRANT ALL PRIVILEGES ON *.* TO '@db.username@'@'%' WITH GRANT OPTION;
use mysql;
update user set Process_priv='N', Super_priv='Y', File_priv='N' WHERE User='nitman';
update user set authentication_string =PASSWORD('@db.password@') where User='root';
update user set user='nitmanadmin' where user='root';
FLUSH PRIVILEGES;