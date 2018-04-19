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
  "first_name" VARCHAR(255),
  "last_name" VARCHAR(255),
  "username" VARCHAR(255) NOT NULL UNIQUE,
  "password" VARCHAR(255) NOT NULL,
  "email" VARCHAR(255) NOT NULL UNIQUE,
  "phone_number" VARCHAR(255),
  "salt" bytea NOT NULL,
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

INSERT INTO category VALUES (1, 'Costume Accessory', 'Category of costume accessories', 'Costume');
INSERT INTO category VALUES (2, 'Phone Case', 'Category of phone cases', 'Phone Accesory');
INSERT INTO category VALUES (3, 'Mug', 'Category of mugs', 'Drinkware');
INSERT INTO category VALUES (4, 'Socks', 'Category of socks', 'Footwear');
INSERT INTO category VALUES (5, 'Plush Toy', 'Category of plush toys', 'Toy');
INSERT INTO category VALUES (6, 'Doll', 'Category of dolls', 'Toy');
INSERT INTO category VALUES (7, 'Dollhouse', 'Category of dollhouses', 'Toy');
INSERT INTO category VALUES (8, 'PC Game', 'Category of PC games', 'Gaming');
SELECT pg_catalog.setval('category_id_seq', 8, true);

INSERT INTO supplier VALUES (1, 'Ebay', 'Ebay''s Powerpuff Girlsy stuff');
INSERT INTO supplier VALUES (2, 'Walmart', 'Walmart''s Powerpuff Girlsy stuff');
INSERT INTO supplier VALUES (3, 'Codecool', 'Codecool''s Powerpuff Girlsy stuff');
INSERT INTO supplier VALUES (4, 'Cartoon Network', 'Cartoon Network''s Powerpuff Girlsy stuff');
SELECT pg_catalog.setval('supplier_id_seq', 4, true);

INSERT INTO product VALUES (1, 1, 4, 'Blossom Child Wig', 25, 'With this wig, you''ll have Blossom''s long red hair tied neatly back with eye-catching red bow.');
INSERT INTO product VALUES (2, 1, 4, 'Bubbles Child Wig', 25, 'Join Blossom and Buttercup as you fight the baddies that threaten Townsville with this wig');
INSERT INTO product VALUES (3, 1, 4, 'Buttercup Child Wig', 25, 'Villains who dare threaten Townsville won''t stand a chance when they see you coming wearing this wig.');
INSERT INTO product VALUES (4, 2, 4, 'Bubbles Phone Case', 35, 'This phone case features the sweet and innocent Bubbles and will protect your phone in style.');
INSERT INTO product VALUES (5, 3, 4, 'White Mug', 15, 'This ceramic mug holds 11 oz. of your favorite hot or cold beverage and is microwave and dishwasher safe.');
INSERT INTO product VALUES (6, 4, 4, 'Low Cut Socks', 15, 'This pack of five pairs of socks features cute close-ups of the Powerpuff Girls in all their adorable glory.');
INSERT INTO product VALUES (7, 4, 4, 'Buttercup Rugby Knee High Socks', 13, 'Cute and comfy knee-high socks featuring the Powerpuff Girls'' mightiest fighter.');
INSERT INTO product VALUES (8, 5, 1, 'Plush Toy 9" Doll (3pcs/set)', 16, 'Size: Approximately measure 9", Color: Full Color as Pictured, 100% Brand New, Material: polyester');
INSERT INTO product VALUES (9, 5, 1, 'Plush Toy 4" Doll (3pcs/set)', 6, 'Item Material: High quality soft plush');
INSERT INTO product VALUES (10, 6, 1, 'Funko POP! Vinyl Figures (5pcs/set)', 57, 'Vinyl Figure by Funko LLC. These figures are brand new and come in a display box. Approximate size: 4 inches');
INSERT INTO product VALUES (11, 5, 2, 'Interactive Plush Bubbles with Recording Mode', 20, 'Press the belly to record what you say and hear it back in Bubble''s voice');
INSERT INTO product VALUES (12, 5, 2, 'Interactive Plush Buttercup with Recording Mode', 19, '12", Press the belly to record what you say and hear it back in Buttercup''s voice');
INSERT INTO product VALUES (13, 5, 2, 'Interactive Plush Blossom with Recording Mode', 20, 'Press the belly to record what you say and hear it back in Blossom''s voice');
INSERT INTO product VALUES (14, 7, 2, '2 in 1 Playset 3 pc Box', 25, '2-in-1 playset flips from the girls'' bedroom to the superhero lab.');
INSERT INTO product VALUES (15, 7, 2, 'Mojo Jojo Jewelry Store Playset', 13, 'Age Range: 4 to 6 years, No batteries required, Includes: 1 Playset, 1 Figure');
INSERT INTO product VALUES (16, 8, 3, 'PPG Klondike Solitaire', 25, 'THE BEST KLONDIKE SOLITAIRE - BUY IT!!');
INSERT INTO product VALUES (17, 8, 3, 'PPG Snake', 25, 'THE BEST SNAKE GAME EVER');
SELECT pg_catalog.setval('product_id_seq', 17, true);

SELECT pg_catalog.setval('users_id_seq', 1, true);

