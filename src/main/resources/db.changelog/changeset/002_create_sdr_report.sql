--liquibase formatted sql
--changeset tsoyka:2 runOnChange:true
create table report_sdr(
    report_id int generated by default as identity,

    call_direction char(2) not null,

    caller_number char(11) not null,
    receive_number char(11) not null,

    beginning_date datetime not null,
    ending_date datetime not null

);