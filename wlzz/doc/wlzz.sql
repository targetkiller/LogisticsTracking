drop table if exists order_detail;

drop table if exists order_form;

drop table if exists role;

drop table if exists station;

drop table if exists station_manager;

drop table if exists user;

drop table if exists user_info;

/*==============================================================*/
/* Table: order_detail                                          */
/*==============================================================*/
create table order_detail
(
   id                   int not null auto_increment,
   order_form_id        int,
   station_id           int,
   user_id              int,
   next_station_id      int,
   update_time          bigint,
   description          varchar(100),
   is_finish            int,
   primary key (id)
);

/*==============================================================*/
/* Table: order_form                                            */
/*==============================================================*/
create table order_form
(
   id                   int not null auto_increment,
   address              varchar(108),
   user_name            varchar(32),
   zip_code             varchar(6),
   content              varchar(100),
   amount               int,
   weight               float,
   code                 varchar(32),
   barcode              varchar(32),
   station_id           int,
   create_time          bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: role                                                  */
/*==============================================================*/
create table role
(
   id                   int not null auto_increment,
   name                 varchar(32),
   primary key (id)
);

/*==============================================================*/
/* Table: station                                               */
/*==============================================================*/
create table station
(
   id                   int not null auto_increment,
   account              varchar(32),
   password             varchar(32),
   address              varchar(108),
   phone                varchar(32),
   online_num           tinyint,
   primary key (id)
);

/*==============================================================*/
/* Table: station_manager                                       */
/*==============================================================*/
create table station_manager
(   
   user_id              int,
   id                   int not null auto_increment,
   station_id           int,
   ip                   varchar(32),
   last_login_time      bigint,
   primary key (id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   id                   int not null auto_increment,
   role_id              int,
   account               varchar(32),
   password             varchar(32),
   primary key (id)
);

/*==============================================================*/
/* Table: user_info                                             */
/*==============================================================*/
create table user_info
(
   id                   int not null auto_increment,
   name					varchar(16),
   email                varchar(32),
   age                  int,
   gender               tinyint,
   phone                varchar(32),
   zip_code             varchar(6),
   address              varchar(108),
   primary key (id)
);

alter table order_detail add constraint FK_Reference_10 foreign key (station_id)
      references station (id) on delete restrict on update restrict;

alter table order_detail add constraint FK_Reference_12 foreign key (user_id)
      references user (id) on delete restrict on update restrict;

alter table order_detail add constraint FK_Reference_13 foreign key (next_station_id)
      references station (id) on delete restrict on update restrict;

alter table order_detail add constraint FK_Reference_9 foreign key (order_form_id)
      references order_form (id) on delete restrict on update restrict;


alter table station_manager add constraint FK_Reference_5 foreign key (station_id)
      references station (id) on delete restrict on update restrict;

alter table user add constraint FK_Reference_8 foreign key (role_id)
      references role (id) on delete restrict on update restrict;
