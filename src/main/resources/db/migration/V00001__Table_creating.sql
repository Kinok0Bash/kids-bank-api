CREATE TABLE IF NOT EXISTS "users" (
    "id" uuid NOT NULL UNIQUE ,
    "lastname" varchar NOT NULL ,
    "name" varchar NOT NULL ,
    "father_name" varchar NOT NULL ,
    "login" varchar NOT NULL UNIQUE ,
    "password" text NOT NULL ,
    "birth_date" date NOT NULL ,
    "city" varchar(50) NOT NULL ,
    "role" varchar(10) NOT NULL ,
    "child" uuid,
    PRIMARY KEY ("id"),
    CONSTRAINT "users_fk9" FOREIGN KEY ("child") REFERENCES "users"("id")
);

CREATE TABLE IF NOT EXISTS "accounts" (
    "id" uuid NOT NULL UNIQUE ,
    "user_id" uuid NOT NULL ,
    "balance" int NOT NULL DEFAULT 0,
    PRIMARY KEY ("id"),
    CONSTRAINT "accounts_fk1" FOREIGN KEY ("user_id") REFERENCES "users"("id")
);

CREATE TABLE IF NOT EXISTS "shop_categories" (
    "id" int NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY ,
    "name" varchar(20) NOT NULL UNIQUE ,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "categories_limit" (
    "id" int NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY ,
    "category" int NOT NULL ,
    "child" uuid NOT NULL ,
    PRIMARY KEY ("id"),
    CONSTRAINT "limit_fk1" FOREIGN KEY ("category") REFERENCES "shop_categories"("id"),
    CONSTRAINT "limit_fk2" FOREIGN KEY ("child") REFERENCES "users"("id")
);

CREATE TABLE IF NOT EXISTS "shops" (
    "id" int NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY ,
    "name" varchar(50) NOT NULL ,
    "category" int NOT NULL ,
    PRIMARY KEY ("id"),
    CONSTRAINT "shops_fk2" FOREIGN KEY ("category") REFERENCES "shop_categories"("id")
);

CREATE TABLE IF NOT EXISTS "transactions" (
    "id" uuid NOT NULL UNIQUE ,
    "from" uuid NOT NULL ,
    "to" int NOT NULL ,
    "time" timestamp with time zone NOT NULL ,
    "sum" int NOT NULL ,
    PRIMARY KEY ("id"),
    CONSTRAINT "transactions_fk1" FOREIGN KEY ("from") REFERENCES "accounts"("id"),
    CONSTRAINT "transactions_fk2" FOREIGN KEY ("to") REFERENCES "shops"("id")
);