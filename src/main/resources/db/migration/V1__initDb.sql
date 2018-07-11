
create table pdv (
    id integer not null auto_increment,
    address GEOMETRY,
    coverage_area GEOMETRY,
    document varchar(255),
    owner_name varchar(255),
    trading_name varchar(255),
    primary key (id)
) engine=InnoDB default charset=utf8