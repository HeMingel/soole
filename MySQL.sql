drop table if exists sl_action_navigation;

drop table if exists sl_article;

drop table if exists sl_article_type;

drop table if exists sl_bind_card;

drop table if exists sl_member;

drop table if exists sl_message;

drop table if exists sl_message_type;

drop table if exists sl_my_collection;

drop table if exists sl_order;

drop table if exists sl_order_detail;

drop table if exists sl_product;

drop table if exists sl_product_comment;

drop table if exists sl_product_comment_image;

drop table if exists sl_product_image;

drop table if exists sl_product_type;

drop table if exists sl_product_type_tags;

drop table if exists sl_repository;

drop table if exists sl_shipping_address;

drop table if exists sl_shop;

drop table if exists sl_specification;

drop table if exists sl_system_config;

drop table if exists sl_tag;

drop table if exists sl_user;

/*==============================================================*/
/* table: sl_action_navigation                                  */
/*==============================================================*/
create table sl_action_navigation
(
   id                   varchar(36) not null,
   title                varchar(255),
   description          varchar(255),
   image_url            varchar(255),
   url                  varchar(255),
   page_android         varchar(255),
   page_ios             varchar(255),
   type_id              varchar(36),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_action_navigation comment '主要用于处理按钮导航信息，比如点击某个按钮，唤起app界面或调用外部浏览器';

alter table sl_action_navigation
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_action_navigation
 modify column title varchar(255)   comment '元素文本';

alter table sl_action_navigation
 modify column description varchar(255)   comment '元素描述';

alter table sl_action_navigation
 modify column image_url varchar(255)   comment '元素图片';

alter table sl_action_navigation
 modify column url varchar(255)   comment '链接地址';

alter table sl_action_navigation
 modify column page_android varchar(255)   comment '动作页面_android';

alter table sl_action_navigation
 modify column page_ios varchar(255)   comment '动作页面_ios';

alter table sl_action_navigation
 modify column type_id varchar(36)   comment '导航类型唯一标识符';

alter table sl_action_navigation
 modify column creator varchar(36)   comment '创建人';

alter table sl_action_navigation
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_action_navigation
 modify column modifier varchar(36)   comment '修改人';

alter table sl_action_navigation
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_article                                            */
/*==============================================================*/
create table sl_article
(
   id                   varchar(36) not null,
   title                varchar(255),
   content              varchar(2000),
   image_url            varchar(255),
   read_count           numeric(8,0),
   praise_count         numeric(8,0),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_article comment '文章信息';

alter table sl_article
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_article
 modify column title varchar(255)   comment '标题';

alter table sl_article
 modify column content varchar(2000)   comment '内容';

alter table sl_article
 modify column image_url varchar(255)   comment '图片';

alter table sl_article
 modify column read_count numeric(8,0)   comment '阅读数量';

alter table sl_article
 modify column praise_count numeric(8,0)   comment '赞扬数量';

alter table sl_article
 modify column creator varchar(36)   comment '创建人';

alter table sl_article
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_article
 modify column modifier varchar(36)   comment '修改人';

alter table sl_article
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_article_type                                       */
/*==============================================================*/
create table sl_article_type
(
   id                   varchar(36) not null,
   name                 varchar(255),
   "interval"           numeric(8,2),
   primary key (id)
);

alter table sl_article_type comment '文章类型信息';

alter table sl_article_type
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_article_type
 modify column name varchar(255)   comment '名称';

alter table sl_article_type
 modify column "interval" numeric(8,2)   comment '播放间隔';

/*==============================================================*/
/* table: sl_bind_card                                          */
/*==============================================================*/
create table sl_bind_card
(
   id                   varchar(36) not null,
   user_id              varchar(36),
   card_number          numeric(8,0),
   name                 varchar(255),
   bank                 varchar(255),
   "default"            bool,
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_bind_card comment '绑卡信息';

alter table sl_bind_card
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_bind_card
 modify column user_id varchar(36)   comment '用户唯一标识符';

alter table sl_bind_card
 modify column card_number numeric(8,0)   comment '卡号';

alter table sl_bind_card
 modify column name varchar(255)   comment '持卡人';

alter table sl_bind_card
 modify column bank varchar(255)   comment '开户银行';

alter table sl_bind_card
 modify column "default" bool   comment '是否为默认卡';

alter table sl_bind_card
 modify column creator varchar(36)   comment '创建人';

alter table sl_bind_card
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_bind_card
 modify column modifier varchar(36)   comment '修改人';

alter table sl_bind_card
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_member                                             */
/*==============================================================*/
create table sl_member
(
   id                   varchar(36) not null,
   user_id              varchar(36),
   coin                 numeric(8,0),
   money                decimal(8,3),
   level                numeric(8,0),
   exp                  numeric(8,0),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_member comment '会员信息';

alter table sl_member
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_member
 modify column user_id varchar(36)   comment '用户唯一标识符';

alter table sl_member
 modify column coin numeric(8,0)   comment '金币';

alter table sl_member
 modify column money decimal(8,3)   comment '余额';

alter table sl_member
 modify column level numeric(8,0)   comment '等级';

alter table sl_member
 modify column exp numeric(8,0)   comment '经验值';

alter table sl_member
 modify column creator varchar(36)   comment '创建人';

alter table sl_member
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_member
 modify column modifier varchar(36)   comment '修改人';

alter table sl_member
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_message                                            */
/*==============================================================*/
create table sl_message
(
   id                   varchar(36) not null,
   type_id              varchar(36),
   title                varchar(255),
   content              varchar(2000),
   sender_id            varchar(36),
   receiver_id          varchar(36),
   "read"               bool,
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_message comment '消息信息';

alter table sl_message
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_message
 modify column type_id varchar(36)   comment '消息类型唯一标识符';

alter table sl_message
 modify column title varchar(255)   comment '标题';

alter table sl_message
 modify column content varchar(2000)   comment '内容';

alter table sl_message
 modify column sender_id varchar(36)   comment '发送人';

alter table sl_message
 modify column receiver_id varchar(36)   comment '接收人';

alter table sl_message
 modify column "read" bool   comment '已读';

alter table sl_message
 modify column creator varchar(36)   comment '创建人';

alter table sl_message
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_message
 modify column modifier varchar(36)   comment '修改人';

alter table sl_message
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_message_type                                       */
/*==============================================================*/
create table sl_message_type
(
   id                   varchar(36) not null,
   name                 varchar(255),
   primary key (id)
);

alter table sl_message_type comment '新闻类型信息';

alter table sl_message_type
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_message_type
 modify column name varchar(255)   comment '名称';

/*==============================================================*/
/* table: sl_my_collection                                      */
/*==============================================================*/
create table sl_my_collection
(
   id                   varchar(36) not null,
   user_id              varchar(36),
   collection_id        varchar(36),
   type                 tinyint comment '1：收藏的为店铺
            2：收藏的为商品',
   primary key (id)
);

alter table sl_my_collection comment '我的收藏信息';

alter table sl_my_collection
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_my_collection
 modify column user_id varchar(36)   comment '用户唯一标识符';

alter table sl_my_collection
 modify column collection_id varchar(36)   comment '收藏唯一标识符';

alter table sl_my_collection
 modify column type tinyint   comment '1：收藏的为店铺
                                      2：收藏的为商品';

/*==============================================================*/
/* table: sl_order                                              */
/*==============================================================*/
create table sl_order
(
   id                   varchar(36) not null,
   serial_number        varchar(255),
   user_id              varchar(36),
   shipping_address_id  varchar(36),
   total_amount         decimal(8,3),
   fee                  decimal(8,3),
   state                numeric(8,0) comment '0：待支付
            1：支付成功
            2：支付失败',
   type                 int comment '1：普通订单
            2：拼团订单',
   payment_channel      int comment '1：微信支付
            2：支付宝支付
            3：厦门银行支付',
   remark               varchar(255),
   create_time          varchar(20),
   pay_time             varchar(20),
   primary key (id)
);

alter table sl_order comment '订单信息';

alter table sl_order
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_order
 modify column serial_number varchar(255)   comment '编号';

alter table sl_order
 modify column user_id varchar(36)   comment '用户唯一标识符';

alter table sl_order
 modify column shipping_address_id varchar(36)   comment '收货地址唯一标识符';

alter table sl_order
 modify column total_amount decimal(8,3)   comment '总金额';

alter table sl_order
 modify column fee decimal(8,3)   comment '手续费';

alter table sl_order
 modify column state numeric(8,0)   comment '0：待支付
                                            1：支付成功
                                            2：支付失败';

alter table sl_order
 modify column type int   comment '1：普通订单
                                  2：拼团订单';

alter table sl_order
 modify column payment_channel int   comment '1：微信支付
                                             2：支付宝支付
                                             3：厦门银行支付';

alter table sl_order
 modify column remark varchar(255)   comment '备注';

alter table sl_order
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_order
 modify column pay_time varchar(20)   comment '支付时间';

/*==============================================================*/
/* table: sl_order_detail                                       */
/*==============================================================*/
create table sl_order_detail
(
   id                   varchar(36) not null,
   order_id             varchar(36),
   shop_id              varchar(36),
   product_id           varchar(36),
   amount               decimal(8,3),
   quantity             varchar(50),
   price                varchar(255),
   discount             int,
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_order_detail comment '订单明细信息';

alter table sl_order_detail
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_order_detail
 modify column order_id varchar(36)   comment '订单唯一标识符';

alter table sl_order_detail
 modify column shop_id varchar(36)   comment '店铺唯一标识符';

alter table sl_order_detail
 modify column product_id varchar(36)   comment '商品唯一标识符';

alter table sl_order_detail
 modify column amount decimal(8,3)   comment '总额';

alter table sl_order_detail
 modify column quantity varchar(50)   comment '数量';

alter table sl_order_detail
 modify column price varchar(255)   comment '价格';

alter table sl_order_detail
 modify column discount int   comment '折扣';

alter table sl_order_detail
 modify column creator varchar(36)   comment '创建人';

alter table sl_order_detail
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_order_detail
 modify column modifier varchar(36)   comment '修改人';

alter table sl_order_detail
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_product                                            */
/*==============================================================*/
create table sl_product
(
   id                   varchar(36) not null,
   name                 varchar(255),
   remark               varchar(255),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_product comment '商品基础信息表';

alter table sl_product
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_product
 modify column name varchar(255)   comment '名称';

alter table sl_product
 modify column remark varchar(255)   comment '备注';

alter table sl_product
 modify column creator varchar(36)   comment '创建人';

alter table sl_product
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_product
 modify column modifier varchar(36)   comment '修改人';

alter table sl_product
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_product_comment                                    */
/*==============================================================*/
create table sl_product_comment
(
   id                   varchar(36) not null,
   product_id           varchar(36),
   commentator_id       varchar(36),
   content              varchar(2000),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_product_comment comment '商品评论信息';

alter table sl_product_comment
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_product_comment
 modify column product_id varchar(36)   comment '商品唯一标识符';

alter table sl_product_comment
 modify column commentator_id varchar(36)   comment '评论人';

alter table sl_product_comment
 modify column content varchar(2000)   comment '评论内容';

alter table sl_product_comment
 modify column creator varchar(36)   comment '创建人';

alter table sl_product_comment
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_product_comment
 modify column modifier varchar(36)   comment '修改人';

alter table sl_product_comment
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_product_comment_image                              */
/*==============================================================*/
create table sl_product_comment_image
(
   id                   varchar(36) not null,
   product_comment_id   varchar(36),
   image_url            varchar(36),
   primary key (id)
);

alter table sl_product_comment_image comment '商品评论图片信息';

alter table sl_product_comment_image
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_product_comment_image
 modify column product_comment_id varchar(36)   comment '商品评论唯一标识符';

alter table sl_product_comment_image
 modify column image_url varchar(36)   comment '图片地址';

/*==============================================================*/
/* table: sl_product_image                                      */
/*==============================================================*/
create table sl_product_image
(
   id                   varchar(36) not null,
   product_id           varchar(36),
   image_url            varchar(36),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_product_image comment '商品图片信息';

alter table sl_product_image
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_product_image
 modify column product_id varchar(36)   comment '商品唯一标识符';

alter table sl_product_image
 modify column image_url varchar(36)   comment '图片地址';

alter table sl_product_image
 modify column creator varchar(36)   comment '创建人';

alter table sl_product_image
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_product_image
 modify column modifier varchar(36)   comment '修改人';

alter table sl_product_image
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_product_type                                       */
/*==============================================================*/
create table sl_product_type
(
   id                   varchar(36) not null,
   name                 varchar(255),
   parent_id            varchar(36),
   tag_id               varchar(36),
   primary key (id)
);

alter table sl_product_type comment '商品类别';

alter table sl_product_type
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_product_type
 modify column name varchar(255)   comment '名称';

alter table sl_product_type
 modify column parent_id varchar(36)   comment '上级类别唯一标识符';

alter table sl_product_type
 modify column tag_id varchar(36)   comment '标签唯一标识符';

/*==============================================================*/
/* table: sl_product_type_tags                                  */
/*==============================================================*/
create table sl_product_type_tags
(
   id                   varchar(36) not null,
   product_type_id      varchar(36),
   product_tag_id       varchar(36),
   primary key (id)
);

alter table sl_product_type_tags comment '商品类别标签信息';

alter table sl_product_type_tags
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_product_type_tags
 modify column product_type_id varchar(36)   comment '商品类别唯一标识符';

alter table sl_product_type_tags
 modify column product_tag_id varchar(36)   comment '标签唯一标识符';

/*==============================================================*/
/* table: sl_repository                                         */
/*==============================================================*/
create table sl_repository
(
   id                   varchar(36) not null,
   shop_id              varchar(36),
   product_id           varchar(36),
   remark               varchar(255),
   price                decimal(8,2),
   discount             decimal(8,2),
   state                numeric(8,0),
   count                numeric(8,0),
   score                decimal(8,2),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_repository comment '店铺仓库信息';

alter table sl_repository
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_repository
 modify column shop_id varchar(36)   comment '店铺唯一标识';

alter table sl_repository
 modify column product_id varchar(36)   comment '商品唯一标识';

alter table sl_repository
 modify column remark varchar(255)   comment '备注';

alter table sl_repository
 modify column price decimal(8,2)   comment '价格';

alter table sl_repository
 modify column discount decimal(8,2)   comment '折扣';

alter table sl_repository
 modify column state numeric(8,0)   comment '状态';

alter table sl_repository
 modify column count numeric(8,0)   comment '数量';

alter table sl_repository
 modify column score decimal(8,2)   comment '评分';

alter table sl_repository
 modify column creator varchar(36)   comment '创建人';

alter table sl_repository
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_repository
 modify column modifier varchar(36)   comment '修改人';

alter table sl_repository
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_shipping_address                                   */
/*==============================================================*/
create table sl_shipping_address
(
   id                   varchar(36) not null,
   user_id              varchar(36),
   name                 varchar(50),
   phone                varchar(20),
   address              varchar(255),
   longitude            decimal(12,6),
   latitude             decimal(12,6),
   "default"            bool,
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_shipping_address comment '收货地址信息';

alter table sl_shipping_address
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_shipping_address
 modify column user_id varchar(36)   comment '用户唯一标识符';

alter table sl_shipping_address
 modify column name varchar(50)   comment '收货人姓名';

alter table sl_shipping_address
 modify column phone varchar(20)   comment '收货人电话';

alter table sl_shipping_address
 modify column address varchar(255)   comment '收货人地址';

alter table sl_shipping_address
 modify column longitude decimal(12,6)   comment '经度';

alter table sl_shipping_address
 modify column latitude decimal(12,6)   comment '维度';

alter table sl_shipping_address
 modify column "default" bool   comment '是否为默认地址';

alter table sl_shipping_address
 modify column creator varchar(36)   comment '创建人';

alter table sl_shipping_address
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_shipping_address
 modify column modifier varchar(36)   comment '修改人';

alter table sl_shipping_address
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_shop                                               */
/*==============================================================*/
create table sl_shop
(
   id                   varchar(36) not null,
   owner_id             varchar(36),
   name                 varchar(255),
   description          varchar(255),
   image_url            varchar(255),
   address              varchar(255),
   longitude            decimal(12,6),
   latitude             decimal(12,6),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_shop comment '店铺信息';

alter table sl_shop
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_shop
 modify column owner_id varchar(36)   comment '店主唯一标识符';

alter table sl_shop
 modify column name varchar(255)   comment '名称';

alter table sl_shop
 modify column description varchar(255)   comment '描述';

alter table sl_shop
 modify column image_url varchar(255)   comment '图片';

alter table sl_shop
 modify column address varchar(255)   comment '地址';

alter table sl_shop
 modify column longitude decimal(12,6)   comment '经度';

alter table sl_shop
 modify column latitude decimal(12,6)   comment '维度';

alter table sl_shop
 modify column creator varchar(36)   comment '创建人';

alter table sl_shop
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_shop
 modify column modifier varchar(36)   comment '修改人';

alter table sl_shop
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_specification                                      */
/*==============================================================*/
create table sl_specification
(
   id                   varchar(36) not null,
   product_id           varchar(36),
   content              varchar(255),
   remark               varchar(255),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_specification comment '商品规格信息';

alter table sl_specification
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_specification
 modify column product_id varchar(36)   comment '商品唯一标识符';

alter table sl_specification
 modify column content varchar(255)   comment '规格';

alter table sl_specification
 modify column remark varchar(255)   comment '备注';

alter table sl_specification
 modify column creator varchar(36)   comment '创建人';

alter table sl_specification
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_specification
 modify column modifier varchar(36)   comment '修改人';

alter table sl_specification
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_system_config                                      */
/*==============================================================*/
create table sl_system_config
(
   id                   varchar(36) not null,
   name                 varchar(255),
   content              varchar(255),
   remark               varchar(255),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_system_config comment '系统配置信息';

alter table sl_system_config
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_system_config
 modify column name varchar(255)   comment '名称';

alter table sl_system_config
 modify column content varchar(255)   comment '内容';

alter table sl_system_config
 modify column remark varchar(255)   comment '备注';

alter table sl_system_config
 modify column creator varchar(36)   comment '创建人';

alter table sl_system_config
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_system_config
 modify column modifier varchar(36)   comment '修改人';

alter table sl_system_config
 modify column modification_time varchar(20)   comment '修改时间';

/*==============================================================*/
/* table: sl_tag                                                */
/*==============================================================*/
create table sl_tag
(
   id                   varchar(36) not null,
   name                 varchar(255),
   primary key (id)
);

alter table sl_tag comment '标签信息';

alter table sl_tag
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_tag
 modify column name varchar(255)   comment '名称';

/*==============================================================*/
/* table: sl_user                                               */
/*==============================================================*/
create table sl_user
(
   id                   varchar(36) not null,
   username             varchar(255),
   password             varchar(255),
   phone                varchar(20),
   email                varchar(255),
   avatar               varchar(255),
   creator              varchar(36),
   create_time          varchar(20),
   modifier             varchar(36),
   modification_time    varchar(20),
   primary key (id)
);

alter table sl_user comment '用户基础信息表';

alter table sl_user
 modify column id varchar(36) not null  comment '唯一标识符';

alter table sl_user
 modify column username varchar(255)   comment '账号';

alter table sl_user
 modify column password varchar(255)   comment '密码';

alter table sl_user
 modify column phone varchar(20)   comment '手机号码';

alter table sl_user
 modify column email varchar(255)   comment '电子邮箱';

alter table sl_user
 modify column avatar varchar(255)   comment '头像';

alter table sl_user
 modify column creator varchar(36)   comment '创建人';

alter table sl_user
 modify column create_time varchar(20)   comment '创建时间';

alter table sl_user
 modify column modifier varchar(36)   comment '修改人';

alter table sl_user
 modify column modification_time varchar(20)   comment '修改时间';