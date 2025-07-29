-- 📄 Create the audit schema
CREATE SCHEMA IF NOT EXISTS audit;

-- 📄 Create the audit log table
CREATE TABLE IF NOT EXISTS audit.log (
    id BIGSERIAL PRIMARY KEY,
    table_name TEXT NOT NULL,
    operation TEXT NOT NULL CHECK (operation IN ('INSERT', 'UPDATE', 'DELETE')),
    row_pk JSONB NOT NULL,
    old_data JSONB,
    new_data JSONB,
    changed_at TIMESTAMP DEFAULT NOW(),
    changed_by TEXT DEFAULT session_user
);

-- 📄 Create the generic trigger function

CREATE OR REPLACE FUNCTION audit.if_modified_func()
RETURNS TRIGGER AS $$
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
$$ LANGUAGE plpgsql;


-- 📄 Attach triggers to all business tables

-- hotel
DROP TRIGGER IF EXISTS audit_trigger_hotel ON thelodge.hotel;
CREATE TRIGGER audit_trigger_hotel
AFTER INSERT OR UPDATE OR DELETE ON thelodge.hotel
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- guest
DROP TRIGGER IF EXISTS audit_trigger_guest ON thelodge.guest;
CREATE TRIGGER audit_trigger_guest
AFTER INSERT OR UPDATE OR DELETE ON thelodge.guest
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- employee
DROP TRIGGER IF EXISTS audit_trigger_employee ON thelodge.employee;
CREATE TRIGGER audit_trigger_employee
AFTER INSERT OR UPDATE OR DELETE ON thelodge.employee
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- room_type
DROP TRIGGER IF EXISTS audit_trigger_room_type ON thelodge.room_type;
CREATE TRIGGER audit_trigger_room_type
AFTER INSERT OR UPDATE OR DELETE ON thelodge.room_type
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- room
DROP TRIGGER IF EXISTS audit_trigger_room ON thelodge.room;
CREATE TRIGGER audit_trigger_room
AFTER INSERT OR UPDATE OR DELETE ON thelodge.room
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- booking
DROP TRIGGER IF EXISTS audit_trigger_booking ON thelodge.booking;
CREATE TRIGGER audit_trigger_booking
AFTER INSERT OR UPDATE OR DELETE ON thelodge.booking
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- booking_room
DROP TRIGGER IF EXISTS audit_trigger_booking_room ON thelodge.booking_room;
CREATE TRIGGER audit_trigger_booking_room
AFTER INSERT OR UPDATE OR DELETE ON thelodge.booking_room
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();

-- payment
DROP TRIGGER IF EXISTS audit_trigger_payment ON thelodge.payment;
CREATE TRIGGER audit_trigger_payment
AFTER INSERT OR UPDATE OR DELETE ON thelodge.payment
FOR EACH ROW EXECUTE FUNCTION audit.if_modified_func();