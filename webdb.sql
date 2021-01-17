/*****DROP**********/
DROP TABLE users;
DROP SEQUENCE seq_users_no;
DROP TABLE board;
DROP SEQUENCE seq_board_no;
/****CREATE*********/
CREATE TABLE users(
    no number,
    id varchar2(20) UNIQUE NOT NULL,
    password varchar(20) NOT NULL,
    name varchar(20) ,
    gender varchar2(10),
    primary key(no)
);
CREATE TABLE board(
    no number,
    title varchar2(500) not null,
    content varchar2(4000),
    hit number,
    reg_date date not null,
    user_no number not null,
    primary key(no),
    constraint c_board_fk foreign key (user_no)
    references users(no)
);
CREATE SEQUENCE seq_users_no
INCREMENT BY 1
START WITH 1
NOCACHE ;

CREATE SEQUENCE seq_board_no
INCREMENT BY 1
START WITH 1
NOCACHE ;

/****INSERT*********/
INSERT INTO users
values(seq_users_no.nextval,
       'aaaid',
       '123password',
       'aaaname',
       'gender'
       );
INSERT INTO users
values(seq_users_no.nextval,
       'bbbid',
       '123password',
       'bbbname',
       'gender'
       );
INSERT INTO users
values(seq_users_no.nextval,
       'cccid',
       '123password',
       'cccname',
       'gender'
       );
commit;
rollback;

INSERT INTO board
values(seq_board_no.nextval,
       '감사해요',
       '나는 아직 배가고프다',
       0,
       sysdate,
       1
       );
INSERT INTO board
values(seq_board_no.nextval,
       '안녕하세요',
       '나는 아직 배가고프다',
       0,
       sysdate,
       2
       );
INSERT INTO board
values(seq_board_no.nextval,
       '잘있어요',
       '나는 아직 배가고프다',
       0,
       sysdate,
       3
       );
commit;

/****UPDATE******/
UPDATE users
set password = '1212',
    name='누구세여',
    gender='female'
where no = 2;
commit;
rollback;
/**board update**/
update board
set title = '수정',
    content = '수정됌'
where no = 1; 

select * 
from board;

/****hit update**/
UPDATE board
set hit = hit + 1
where no = 1;

commit;


/****SELECT*******/
SELECT no,
       id,
       password,
       name,
       gender
FROM users;

/***********board Select*****/
select no,
       title,
       content,
       hit,
       reg_date,
       user_no
from board;

/***board list**/
select bo.no bno,
       bo.title btitle,
       us.name uname,
       bo.user_no,
       bo.hit bhit,
       to_char(bo.reg_date,'yy-mm-dd hh24:mi') as  bdate
from  users us ,  board bo
where us.no = bo.user_no
order by bo.no desc;

select *
from  users us ,  board bo
where us.no = bo.user_no;

/***board read***/

select b.title as title,
       b.content as content,
       b.hit as hit,
       b.user_no no,
       b.reg_date as reg_date,
       u.name as name
from board b,users u
where u.no = b.user_no
and b.no=23;

/***board modifyForm list***/
select u.name as name,
       b.hit as hit,
       b.reg_date as reg_date,
       b.title as titel,
       b.content as  content
from board b , users u
where b.user_no = u.no
and u.no = 4
and b.no = 23;

/**Board search*/
select bo.no as bno,
       bo.title as btitle,
       us.name  as uname,
       bo.user_no as userNo,
       bo.hit as bhit,
       to_char(bo.reg_date,'yy-mm-dd hh24:mi') as bdate
from board bo, users us
where bo.user_no = us.no
and (bo.title like '%시간%' or us.name like '%시간%')
order by bo.no desc;

/*****DELETE*****/
DELETE users
where no = 1;

DELETE board
where no = 23;