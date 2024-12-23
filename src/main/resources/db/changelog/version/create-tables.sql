CREATE TABLE CURRENCY (
    ID BIGSERIAL PRIMARY KEY,
    CODE VARCHAR(3) NOT NULL UNIQUE,
    CREATION_DATE TIMESTAMP NOT NULL
);

CREATE TABLE EXCHANGE_RATE_PACK (
    ID BIGSERIAL PRIMARY KEY,
    BASE_CURRENCY_CODE VARCHAR(3),
    SUCCESS BOOLEAN NOT NULL,
    API_TIMESTAMP BIGINT,
    FETCH_DATE TIMESTAMP NOT NULL,
    CONSTRAINT fk_base_currency FOREIGN KEY (BASE_CURRENCY_CODE) REFERENCES CURRENCY(CODE)
);

CREATE TABLE EXCHANGE_RATE (
    ID BIGSERIAL PRIMARY KEY,
    CURRENCY_CODE VARCHAR(3) NOT NULL,
    RATE DECIMAL(25,20) NOT NULL,
    EXCHANGE_RATE_PACK_ID BIGINT NOT NULL,
    CONSTRAINT fk_exchange_rate_pack FOREIGN KEY (EXCHANGE_RATE_PACK_ID) REFERENCES EXCHANGE_RATE_PACK(ID)
);

CREATE TABLE DATA_PROVIDER_CONFIG (
    ID BIGSERIAL PRIMARY KEY,
    PROVIDER_NAME VARCHAR(30) NOT NULL,
    CREATION_DATE TIMESTAMP NOT NULL
);

CREATE INDEX idx_currency_code ON CURRENCY (CODE);
CREATE INDEX idx_exchange_rate_pack_fetch_date ON EXCHANGE_RATE_PACK (FETCH_DATE);
CREATE INDEX idx_exchange_rate_currency_code ON EXCHANGE_RATE (CURRENCY_CODE);

INSERT INTO DATA_PROVIDER_CONFIG (PROVIDER_NAME, CREATION_DATE)
VALUES ('fixerIo', CURRENT_TIMESTAMP);

--INSERT INTO CURRENCY (CODE, CREATION_DATE)
--VALUES ('EUR', CURRENT_TIMESTAMP);