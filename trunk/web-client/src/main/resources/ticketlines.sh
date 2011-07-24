mysql> desc INDKOEB;
+--------------+---------------+------+-----+------------+----------------+
| Field        | Type          | Null | Key | Default    | Extra          |
+--------------+---------------+------+-----+------------+----------------+
| indkoebid    | int(11)       | NO   | PRI | NULL       | auto_increment |
| vareid       | int(11)       | NO   | MUL | 0          |                |
| beloeb       | decimal(10,2) | NO   |     | 0.00       |                |
| Enhedsid     | int(11)       | YES  |     | NULL       |                |
| AntalEnheder | float         | YES  |     | NULL       |                |
| dato         | date          | NO   | MUL | 0000-00-00 |                |
| bonid        | int(11)       | YES  |     | NULL       |                |
+--------------+---------------+------+-----+------------+----------------+


    void setAmount(Double mAmount);
    void setNumber(Integer mNumber);
    void setProductId(Long mProductId);
    void setCategoryId(Long mCategoryId);
    void setTicketId(Long mTicketId);

mysql -u root -p --default-character-set=utf8 -e "select concat('{\"id\":\"',indkoebid,'\",\"categoryId\":\"', vareid, '\",\"amount\":\"', beloeb, '\",\"number\":\"', IF(AntalEnheder is null,0,AntalEnheder),'\",\"ticketId\":\"', IF(bonid is null,0,bonid),'\"},') json from INDKOEB" -B hushold | iconv -f latin1 > ticketlines.json