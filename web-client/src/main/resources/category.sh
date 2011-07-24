+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| vareid        | int(11)      | NO   | PRI | NULL    | auto_increment |
| navn          | varchar(100) | NO   |     |         |                |
| varekategorid | int(11)      | YES  |     | NULL    |                |
| antalenheder  | float        | YES  |     | NULL    |                |
| enhedsid      | int(11)      | YES  |     | NULL    |                |
| sidstepris    | float        | NO   |     | 0       |                |
+---------------+--------------+------+-----+---------+----------------+


    public enum Type {
        food,
        nonFood;
    }

    Long getId();
    String getName();
    Type getType();

mysql -u root -p --default-character-set=utf8 -e "select concat('{\"id\":\"',vareid,'\",\"name\":\"', navn, '\",\"type\":\"', IF(varekategorid=30,'nonFood','food'),'\"},') json from VARER" -B hushold | iconv -f latin1 > category.json