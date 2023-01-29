--company

create table if not exists Company
(
    company_id long auto_increment primary key,
    id         long         not null,
    name       varchar(100) not null,
    signed_up  timestamp    not null
);

-- conversation

create table if not exists Conversation
(
    conversation_id long auto_increment primary key,
    id              long not null,
    number          long,
    user_id         long,
    `from`          varchar(100),
    received        timestamp,
    company         long,
    foreign key (company) references Company (company_id)
);

-- thread

create table if not exists Thread
(
    thread_id    long auto_increment primary key,
    id           long not null,
    payload      text not null,
    conversation long,
    foreign key (conversation) references Conversation (conversation_id)
);

-- duplicate thread

create table if not exists Duplicate_Thread
(
    duplicate_thread_id long auto_increment primary key,
    id                  long not null,
    payload             text not null,
    conversation        long,
    foreign key (conversation) references Conversation (conversation_id)
);