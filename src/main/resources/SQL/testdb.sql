ALTER TABLE "product" DROP CONSTRAINT IF EXISTS "product_fk0";
ALTER TABLE "product" DROP CONSTRAINT IF EXISTS "product_fk1";
ALTER TABLE "orders" DROP CONSTRAINT IF EXISTS "orders_fk0";
ALTER TABLE "order_data" DROP CONSTRAINT IF EXISTS "order_data_fk0";
ALTER TABLE "order_data" DROP CONSTRAINT IF EXISTS "order_data_fk1";
ALTER TABLE "user_address" DROP CONSTRAINT IF EXISTS "user_address_fk0";

DROP TABLE IF EXISTS "product";
DROP TABLE IF EXISTS "category";
DROP TABLE IF EXISTS "supplier";
DROP TABLE IF EXISTS "users";
DROP TABLE IF EXISTS "orders";
DROP TABLE IF EXISTS "order_data";
DROP TABLE IF EXISTS "user_address";


CREATE TABLE "product" (
  "id" serial NOT NULL,
  "category_id" integer NOT NULL,
  "supplier_id" integer NOT NULL,
  "title" VARCHAR(255) NOT NULL,
  "price" float NOT NULL,
  "description" VARCHAR(255) NOT NULL,
  CONSTRAINT product_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "category" (
  "id" serial NOT NULL,
  "name" VARCHAR(255) NOT NULL,
  "description" VARCHAR(255) NOT NULL,
  "department" VARCHAR(255) NOT NULL,
  CONSTRAINT category_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "supplier" (
  "id" serial NOT NULL,
  "name" VARCHAR(255) NOT NULL,
  "description" VARCHAR(255) NOT NULL,
  CONSTRAINT supplier_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "users" (
  "id" serial NOT NULL,
  "first_name" VARCHAR(255) NOT NULL,
  "last_name" VARCHAR(255) NOT NULL,
  "shipping_address" VARCHAR(255) NOT NULL,
  "billing_address" VARCHAR(255) NOT NULL,
  "username" VARCHAR(255) NOT NULL UNIQUE,
  "password" VARCHAR(255) NOT NULL,
  "email" VARCHAR(255) NOT NULL UNIQUE,
  "phone_number" VARCHAR(255) NOT NULL,
  "salt" VARCHAR(255) NOT NULL,
  CONSTRAINT users_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "orders" (
  "id" serial NOT NULL,
  "user_id" integer NOT NULL,
  "status" VARCHAR(255) NOT NULL,
  "order_date" TIMESTAMP NOT NULL,
  CONSTRAINT orders_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "order_data" (
  "id" serial NOT NULL,
  "order_id" integer NOT NULL,
  "product_id" integer NOT NULL,
  "name" VARCHAR(255) NOT NULL,
  "quantity" integer NOT NULL,
  "unit_price" float NOT NULL,
  CONSTRAINT order_data_pk PRIMARY KEY ("id")
) WITH (
OIDS=FALSE
);

CREATE TABLE "user_address" (
  "user_id" integer NOT NULL,
  "country" VARCHAR(255) NOT NULL,
  "city" VARCHAR(255) NOT NULL,
  "address" VARCHAR(255) NOT NULL,
  "zip_code" VARCHAR(255) NOT NULL,
  "type" VARCHAR(255) NOT NULL,
  "is_default" BOOLEAN NOT NULL,
  "name" VARCHAR(255) NOT NULL
) WITH (
OIDS=FALSE
);

ALTER TABLE "product" ADD CONSTRAINT "product_fk0" FOREIGN KEY ("category_id") REFERENCES "category"("id");
ALTER TABLE "product" ADD CONSTRAINT "product_fk1" FOREIGN KEY ("supplier_id") REFERENCES "supplier"("id");
ALTER TABLE "orders" ADD CONSTRAINT "orders_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id");
ALTER TABLE "order_data" ADD CONSTRAINT "order_data_fk0" FOREIGN KEY ("order_id") REFERENCES "orders"("id");
ALTER TABLE "order_data" ADD CONSTRAINT "order_data_fk1" FOREIGN KEY ("product_id") REFERENCES "product"("id");
ALTER TABLE "user_address" ADD CONSTRAINT "user_address_fk0" FOREIGN KEY ("user_id") REFERENCES "users"("id");

INSERT INTO category VALUES (1, 'PC Game', 'Category of PC games', 'Gaming');
INSERT INTO supplier VALUES (1, 'Codecool', 'Codecool''s Powerpuff Girlsy stuff');
