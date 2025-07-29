-- Cleaned: PostgreSQL schema-only dump for audit, auth, thelodge schemas

-- Session and encoding setup
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = FALSE;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = OFF;
SET default_tablespace = '';
SET default_table_access_method = heap;

-- === Schemas ===
CREATE SCHEMA audit AUTHORIZATION user1;
CREATE SCHEMA auth AUTHORIZATION postgres;
CREATE SCHEMA thelodge AUTHORIZATION user1;

-- === Audit Logging Function ===
CREATE FUNCTION audit.if_modified_func() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
  IF TG_OP = 'INSERT' THEN
    INSERT INTO audit.log(table_name, operation, row_pk, new_data)
      VALUES (TG_TABLE_NAME, TG_OP, to_jsonb(ROW(NEW.id)), to_jsonb(NEW));
    RETURN NEW;
  ELSIF TG_OP = 'UPDATE' THEN
    INSERT INTO audit.log(table_name, operation, row_pk, old_data, new_data)
      VALUES (TG_TABLE_NAME, TG_OP, to_jsonb(ROW(OLD.id)), to_jsonb(OLD), to_jsonb(NEW));
    RETURN NEW;
  ELSIF TG_OP = 'DELETE' THEN
    INSERT INTO audit.log(table_name, operation, row_pk, old_data)
      VALUES (TG_TABLE_NAME, TG_OP, to_jsonb(ROW(OLD.id)), to_jsonb(OLD));
    RETURN OLD;
  END IF;
END;
$$;

ALTER FUNCTION audit.if_modified_func() OWNER TO postgres;

-- === Audit Table & Sequence ===
CREATE TABLE audit.log (
  id bigint NOT NULL DEFAULT nextval('audit.log_id_seq'),
  table_name text NOT NULL,
  operation text NOT NULL CHECK (operation = ANY (ARRAY['INSERT','UPDATE','DELETE'])),
  row_pk jsonb NOT NULL,
  old_data jsonb,
  new_data jsonb,
  changed_at timestamp DEFAULT now(),
  changed_by text DEFAULT SESSION_USER
);
ALTER TABLE audit.log OWNER TO postgres;

CREATE SEQUENCE audit.log_id_seq START 1 INCREMENT 1 OWNED BY audit.log.id;
ALTER SEQUENCE audit.log_id_seq OWNER TO postgres;

-- === Audit Indexes ===
CREATE INDEX idx_audit_changed_at_desc ON audit.log (changed_at DESC);
CREATE INDEX idx_audit_changed_by ON audit.log (changed_by);
CREATE INDEX idx_audit_row_pk_gin ON audit.log USING gin (row_pk);
CREATE INDEX idx_audit_table_changed_at ON audit.log (table_name, changed_at DESC);
CREATE INDEX idx_audit_table_changed_by ON audit.log (table_name, changed_by);
CREATE INDEX idx_audit_table_changed_by_at ON audit.log (table_name, changed_by, changed_at DESC);
CREATE INDEX idx_audit_table_name ON audit.log (table_name);

-- === Auth Tables & Sequences ===
CREATE TABLE auth.permissions (
  permission_id integer NOT NULL DEFAULT nextval('auth.permissions_permission_id_seq'),
  permission_name text NOT NULL UNIQUE,
  description text
);
ALTER TABLE auth.permissions OWNER TO postgres;
CREATE SEQUENCE auth.permissions_permission_id_seq START 1 INCREMENT 1 OWNED BY auth.permissions.permission_id;

CREATE TABLE auth.roles (
  role_id integer NOT NULL DEFAULT nextval('auth.roles_role_id_seq'),
  role_name text NOT NULL UNIQUE,
  description text
);
ALTER TABLE auth.roles OWNER TO postgres;
CREATE SEQUENCE auth.roles_role_id_seq START 1 INCREMENT 1 OWNED BY auth.roles.role_id;

CREATE TABLE auth.user_roles (
  user_id integer NOT NULL,
  role_id integer NOT NULL,
  PRIMARY KEY (user_id, role_id)
);
ALTER TABLE auth.user_roles OWNER TO postgres;

CREATE TABLE auth.user_employees (
  user_id integer NOT NULL,
  emp_id integer NOT NULL,
  PRIMARY KEY (user_id, emp_id)
);
ALTER TABLE auth.user_employees OWNER TO postgres;

CREATE TABLE auth.user_guests (
  user_id integer NOT NULL,
  guest_id integer NOT NULL,
  PRIMARY KEY (user_id, guest_id)
);
ALTER TABLE auth.user_guests OWNER TO postgres;

CREATE TABLE auth.users (
  user_id integer NOT NULL DEFAULT nextval('auth.users_user_id_seq'),
  username text NOT NULL UNIQUE,
  password_hash text NOT NULL,
  is_active boolean DEFAULT true,
  created_at timestamp DEFAULT now(),
  PRIMARY KEY (user_id)
);
ALTER TABLE auth.users OWNER TO postgres;
CREATE SEQUENCE auth.users_user_id_seq START 1 INCREMENT 1 OWNED BY auth.users.user_id;

-- === TheLodge Tables & Sequences ===
CREATE TABLE thelodge.address (
  id integer NOT NULL DEFAULT nextval('thelodge.address_id_seq'),
  line1 varchar(100) NOT NULL,
  line2 varchar(100),
  line3 varchar(100),
  city varchar(45) NOT NULL,
  state varchar(45) NOT NULL,
  pincode varchar(8) NOT NULL,
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.address OWNER TO postgres;
CREATE SEQUENCE thelodge.address_id_seq START 1 INCREMENT 1 OWNED BY thelodge.address.id;

CREATE TABLE thelodge.designation (
  id integer NOT NULL DEFAULT nextval('thelodge.designation_id_seq'),
  name varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.designation OWNER TO postgres;
CREATE SEQUENCE thelodge.designation_id_seq START 1 INCREMENT 1 OWNED BY thelodge.designation.id;

CREATE TABLE thelodge.employee (
  id integer NOT NULL DEFAULT nextval('thelodge.employee_id_seq'),
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  designation_id integer,
  phone varchar(12),
  email varchar(100),
  id_proof_type varchar(45),
  id_proof_no varchar(45),
  id_proof_file varchar(255),
  dob date,
  address_id integer,
  hotel_id integer,
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.employee OWNER TO postgres;
CREATE SEQUENCE thelodge.employee_id_seq START 1 INCREMENT 1 OWNED BY thelodge.employee.id;

CREATE TABLE thelodge.guest (
  id integer NOT NULL DEFAULT nextval('thelodge.guest_id_seq'),
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  phone varchar(12),
  email varchar(100),
  id_proof_type varchar(45),
  id_proof_no varchar(45),
  id_proof_file varchar(255),
  address_id integer,
  age smallint CHECK (age >= 0),
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.guest OWNER TO postgres;
CREATE SEQUENCE thelodge.guest_id_seq START 1 INCREMENT 1 OWNED BY thelodge.guest.id;

CREATE TABLE thelodge.hotel (
  id integer NOT NULL DEFAULT nextval('thelodge.hotel_id_seq'),
  name varchar(100) NOT NULL,
  contact1 varchar(12),
  contact2 varchar(12),
  contact3 varchar(12),
  email varchar(100),
  website varchar(100),
  description text,
  room_capacity integer NOT NULL CHECK (room_capacity > 0),
  address_id integer,
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.hotel OWNER TO postgres;
CREATE SEQUENCE thelodge.hotel_id_seq START 1 INCREMENT 1 OWNED BY thelodge.hotel.id;

CREATE TABLE thelodge.payment_method (
  id integer NOT NULL DEFAULT nextval('thelodge.payment_method_id_seq'),
  name varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.payment_method OWNER TO postgres;
CREATE SEQUENCE thelodge.payment_method_id_seq START 1 INCREMENT 1 OWNED BY thelodge.payment_method.id;

CREATE TABLE thelodge.room_type (
  id integer NOT NULL DEFAULT nextval('thelodge.room_type_id_seq'),
  name varchar(45) NOT NULL UNIQUE,
  description text,
  cost numeric(10,2) NOT NULL CHECK (cost >= 0),
  smoke_friendly boolean DEFAULT FALSE,
  pet_friendly boolean DEFAULT FALSE,
  created_at timestamp DEFAULT now(),
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.room_type OWNER TO postgres;
CREATE SEQUENCE thelodge.room_type_id_seq START 1 INCREMENT 1 OWNED BY thelodge.room_type.id;

CREATE TABLE thelodge.room (
  id integer NOT NULL DEFAULT nextval('thelodge.room_id_seq'),
  room_number integer NOT NULL,
  room_type_id integer,
  hotel_id integer,
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.room OWNER TO postgres;
CREATE SEQUENCE thelodge.room_id_seq START 1 INCREMENT 1 OWNED BY thelodge.room.id;

CREATE TABLE thelodge.booking (
  id integer NOT NULL DEFAULT nextval('thelodge.booking_id_seq'),
  guest_id integer,
  employee_id integer,
  hotel_id integer,
  payment_method_id integer,
  travel_mode_id integer,
  booking_date timestamp DEFAULT now(),
  check_in timestamp NOT NULL,
  check_out timestamp NOT NULL,
  total_rooms integer NOT NULL CHECK (total_rooms > 0),
  total_amount numeric(10,2) NOT NULL CHECK (total_amount >= 0),
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.booking OWNER TO postgres;
CREATE SEQUENCE thelodge.booking_id_seq START 1 INCREMENT 1 OWNED BY thelodge.booking.id;

CREATE TABLE thelodge.booking_room (
  id integer NOT NULL DEFAULT nextval('thelodge.booking_room_id_seq'),
  booking_id integer,
  room_id integer,
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  deleted_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.booking_room OWNER TO postgres;
CREATE SEQUENCE thelodge.booking_room_id_seq START 1 INCREMENT 1 OWNED BY thelodge.booking_room.id;

CREATE TABLE thelodge.payment (
  id integer NOT NULL DEFAULT nextval('thelodge.payment_id_seq'),
  booking_id integer NOT NULL,
  method_id integer NOT NULL,
  amount numeric(10,2) NOT NULL CHECK (amount >= 0),
  paid_at timestamp DEFAULT now(),
  transaction_ref varchar(100),
  status varchar(30) DEFAULT 'SUCCESS',
  created_at timestamp DEFAULT now(),
  updated_at timestamp,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.payment OWNER TO postgres;
CREATE SEQUENCE thelodge.payment_id_seq START 1 INCREMENT 1 OWNED BY thelodge.payment.id;

CREATE TABLE thelodge.travel_mode (
  id integer NOT NULL DEFAULT nextval('thelodge.travel_mode_id_seq'),
  name varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);
ALTER TABLE thelodge.travel_mode OWNER TO postgres;
CREATE SEQUENCE thelodge.travel_mode_id_seq START 1 INCREMENT 1 OWNED BY thelodge.travel_mode.id;

-- === Foreign Keys ===
ALTER TABLE auth.role_permissions
  ADD CONSTRAINT role_permissions_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES auth.permissions(permission_id) ON DELETE CASCADE,
  ADD CONSTRAINT role_permissions_role_id_fkey FOREIGN KEY (role_id) REFERENCES auth.roles(role_id) ON DELETE CASCADE;

ALTER TABLE auth.user_employees
  ADD CONSTRAINT user_employees_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(user_id) ON DELETE CASCADE,
  ADD CONSTRAINT user_employees_emp_id_fkey FOREIGN KEY (emp_id) REFERENCES thelodge.employee(id) ON DELETE CASCADE;

ALTER TABLE auth.user_guests
  ADD CONSTRAINT user_guests_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(user_id) ON DELETE CASCADE,
  ADD CONSTRAINT user_guests_guest_id_fkey FOREIGN KEY (guest_id) REFERENCES thelodge.guest(id) ON DELETE CASCADE;

ALTER TABLE auth.user_roles
  ADD CONSTRAINT user_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES auth.users(user_id) ON DELETE CASCADE,
  ADD CONSTRAINT user_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES auth.roles(role_id) ON DELETE CASCADE;

ALTER TABLE thelodge.booking
  ADD CONSTRAINT booking_guest_id_fkey FOREIGN KEY (guest_id) REFERENCES thelodge.guest(id),
  ADD CONSTRAINT booking_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES thelodge.employee(id),
  ADD CONSTRAINT booking_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES thelodge.hotel(id),
  ADD CONSTRAINT booking_payment_method_id_fkey FOREIGN KEY (payment_method_id) REFERENCES thelodge.payment_method(id),
  ADD CONSTRAINT booking_travel_mode_id_fkey FOREIGN KEY (travel_mode_id) REFERENCES thelodge.travel_mode(id);

ALTER TABLE thelodge.booking_room
  ADD CONSTRAINT booking_room_booking_id_fkey FOREIGN KEY (booking_id) REFERENCES thelodge.booking(id),
  ADD CONSTRAINT booking_room_room_id_fkey FOREIGN KEY (room_id) REFERENCES thelodge.room(id);

ALTER TABLE thelodge.employee
  ADD CONSTRAINT employee_designation_id_fkey FOREIGN KEY (designation_id) REFERENCES thelodge.designation(id),
  ADD CONSTRAINT employee_address_id_fkey FOREIGN KEY (address_id) REFERENCES thelodge.address(id),
  ADD CONSTRAINT employee_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES thelodge.hotel(id);

ALTER TABLE thelodge.guest
  ADD CONSTRAINT guest_address_id_fkey FOREIGN KEY (address_id) REFERENCES thelodge.address(id);

ALTER TABLE thelodge.hotel
  ADD CONSTRAINT hotel_address_id_fkey FOREIGN KEY (address_id) REFERENCES thelodge.address(id);

ALTER TABLE thelodge.room
  ADD CONSTRAINT room_room_type_id_fkey FOREIGN KEY (room_type_id) REFERENCES thelodge.room_type(id),
  ADD CONSTRAINT room_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES thelodge.hotel(id);

ALTER TABLE thelodge.payment
  ADD CONSTRAINT payment_booking_id_fkey FOREIGN KEY (booking_id) REFERENCES thelodge.booking(id),
  ADD CONSTRAINT payment_method_id_fkey FOREIGN KEY (method_id) REFERENCES thelodge.payment_method(id);

-- === Triggers for Audit ===
CREATE TRIGGER audit_trigger_booking
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.booking
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_booking_room
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.booking_room
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_employee
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.employee
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_guest
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.guest
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_hotel
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.hotel
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_payment
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.payment
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_room
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.room
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

CREATE TRIGGER audit_trigger_room_type
  AFTER INSERT OR UPDATE OR DELETE ON thelodge.room_type
  FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- === Privileges (ACL) ===
-- Schemas
GRANT USAGE ON SCHEMA audit TO rw_user;
GRANT USAGE ON SCHEMA auth, thelodge TO rw_user, ro_user;

GRANT ALL ON SCHEMA auth TO user1;
GRANT USAGE ON SCHEMA thelodge TO user1;

-- Function
GRANT ALL ON FUNCTION audit.if_modified_func() TO rw_user, user1;

-- Tables & Sequences
-- [Abbreviated: grant SELECT/INSERT/DELETE/UPDATE on each table to ro_user and rw_user, plus ALL to user1; similarly for sequences]
