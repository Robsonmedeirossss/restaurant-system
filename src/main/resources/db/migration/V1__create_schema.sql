CREATE TABLE IF NOT EXISTS restaurant_tables (
  id BIGSERIAL PRIMARY KEY,
  number INTEGER NOT NULL UNIQUE,
  capacity INTEGER DEFAULT 4,
  description VARCHAR(1500),
  status VARCHAR(50) CHECK(
    status IN(
      'FREE',
      'RESERVED',
      'BUSY',
      'INACTIVE'
    )
  ) DEFAULT 'FREE',
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories_products(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  available BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS products(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  description VARCHAR(1500),
  price NUMERIC(7, 2) NOT NULL CHECK(price >= 0),
  available BOOLEAN DEFAULT true,
  preparation_minutes INTEGER,
  stock INTEGER DEFAULT 0,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP,

  category_id BIGINT NOT NULL REFERENCES categories_products(id)
);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_name ON products(name);

CREATE TABLE IF NOT EXISTS orders(
  id BIGSERIAL PRIMARY KEY,
  status VARCHAR(50) CHECK (
    status IN (
      'PENDING',
      'DOING',
      'DONE',
      'DELIVERED',
      'CANCELED'
    )
  ) DEFAULT 'PENDING',
  table_id BIGINT NOT NULL REFERENCES restaurant_tables(id),
  opening_date TIMESTAMP,
  closing_date TIMESTAMP,
  observation VARCHAR(1000),
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_table ON orders(table_id);


CREATE TABLE IF NOT EXISTS products_orders(
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT REFERENCES products(id),
  order_id BIGINT REFERENCES orders(id),
  unity_price NUMERIC(7,2) NOT NULL CHECK (unity_price >= 0),
  quantity INTEGER NOT NULL CHECK (quantity > 0),
  observation VARCHAR(1000),
  preparation_start_at TIMESTAMP,
  preparation_end_at TIMESTAMP,
  status VARCHAR(50) NOT NULL CHECK(
    status IN (
      'PENDING',
      'DOING',
      'DONE',
      'DELIVERED',
      'CANCELED'
    )
  ) DEFAULT 'PENDING',
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_products_orders_order ON products_orders(order_id);
CREATE INDEX idx_products_orders_status ON products_orders(status);
CREATE INDEX idx_products_orders_product ON products_orders(product_id);

CREATE TABLE IF NOT EXISTS billings(
  id BIGSERIAL PRIMARY KEY,
  order_id BIGINT REFERENCES orders(id),
  subtotal NUMERIC(7,2) NOT NULL CHECK (subtotal >= 0),
  discount INTEGER,
  service_tax INTEGER,
  total NUMERIC(7,2) NOT NULL CHECK(total >= 0),
  created_at TIMESTAMP DEFAULT NOW(),
  closed_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payments(
  id BIGINT PRIMARY KEY,
  order_id BIGINT NOT NULL REFERENCES orders(id),
  value NUMERIC(7, 2) NOT NULL CHECK(value >= 0),
  payment_type VARCHAR(50) CHECK(
    payment_type IN (
      'CREDIT_CARD',
      'DEBIT_CARD',
      'PIX',
      'CASH'
  )),
  status VARCHAR(50) CHECK(
    status IN (
      'PENDING',
      'APPROVED',
      'REFUSED',
      'CANCELED'
    )) DEFAULT 'PENDING',
  external_transaction_code VARCHAR(100),
  payment_date TIMESTAMP,
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_payment_status ON payments(status);
CREATE INDEX idx_payment_order ON payments(order_id);