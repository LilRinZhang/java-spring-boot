DROP TABLE IF EXISTS CUSTOMER, ACCOUNT CASCADE;

CREATE TABLE
    CUSTOMER (
        ID SERIAL NOT NULL,
        NAME VARCHAR(20),
        PHONE VARCHAR(11),
        EMAIL VARCHAR(30),
        PRIMARY KEY(id)
    );

CREATE TABLE
    ACCOUNT (
        ID SERIAL NOT NULL,
        NAME VARCHAR(20) NOT NULL,
        ACCOUNT VARCHAR(30) NOT NULL,
        PASSWORD VARCHAR(30) NOT NULL,
        EMAIL VARCHAR(30) NOT NULL,
        VAILD BOOLEAN NOT NULL,
        UPDATE_DATE TIMESTAMP NOT NULL,
        CREATE_DATE TIMESTAMP NOT NULL,
        PRIMARY KEY(id)
    );

ALTER TABLE ACCOUNT ADD UNIQUE (ACCOUNT, EMAIL);

INSERT INTO
    CUSTOMER(NAME, PHONE, EMAIL)
VALUES (
        'LilRin',
        '12345678901',
        'lilrin@xon.jp'
    ), (
        'yamada',
        '12345678901',
        'yamada@xon.jp'
    ), (
        'Ye',
        '12345678901',
        'Ye@xon.jp'
    ), (
        'West',
        '12345678901',
        'West@xon.jp'
    ), (
        'Justin',
        '12345678901',
        'Justin@xon.jp'
    ), (
        'JayZ',
        '12345678901',
        'JayZ@xon.jp'
    ), (
        'LilUzi',
        '12345678901',
        'LilUzi@xon.jp'
    ), (
        '21Savage',
        '12345678901',
        '21Savage@xon.jp'
    ), (
        'JCole',
        '12345678901',
        'JCole@xon.jp'
    ), (
        'Drake',
        '12345678901',
        'Drake@xon.jp'
    );

INSERT INTO
    ACCOUNT(
        NAME,
        ACCOUNT,
        PASSWORD,
        EMAIL,
        VAILD,
        UPDATE_DATE,
        CREATE_DATE
    )
VALUES (
        'LilRin',
        'lilrin',
        'Xon123',
        'lilrin@xon.jp',
        true,
        current_timestamp,
        current_timestamp
    );