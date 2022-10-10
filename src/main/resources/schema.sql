--company

create table Company
(
    company_id   long auto_increment primary key,
    id           long         not null,
    name         varchar(100) not null
);

-- conversation

create table Conversation
(
    conversation_id long auto_increment primary key,
    id              long not null,
    company         long,
    foreign key (company) references Company (company_id)
);

-- thread

create table Thread
(
    thread_id    long auto_increment primary key,
    id           long not null,
    payload      text not null,
    conversation long,
    foreign key (conversation) references Conversation (conversation_id)
);
