-- =====================================================================
-- 📋 RBAC Schema (Application-level) with Sample Data
-- =====================================================================

-- Schema for RBAC (optional, remove if not needed)
CREATE SCHEMA IF NOT EXISTS auth;

-- =============================
-- Tables
-- =============================

-- Users
CREATE TABLE auth.users (
    user_id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now()
);

-- Roles
CREATE TABLE auth.roles (
    role_id SERIAL PRIMARY KEY,
    role_name TEXT UNIQUE NOT NULL,
    description TEXT
);

-- Permissions
CREATE TABLE auth.permissions (
    permission_id SERIAL PRIMARY KEY,
    permission_name TEXT UNIQUE NOT NULL,
    description TEXT
);

-- User ↔ Role
CREATE TABLE auth.user_roles (
    user_id INT REFERENCES auth.users(user_id) ON DELETE CASCADE,
    role_id INT REFERENCES auth.roles(role_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

-- Role ↔ Permission
CREATE TABLE auth.role_permissions (
    role_id INT REFERENCES auth.roles(role_id) ON DELETE CASCADE,
    permission_id INT REFERENCES auth.permissions(permission_id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);

-- =====================================================================
-- 📋 Add mapping tables to link auth.users ↔ thelodge.employees & guests
-- =====================================================================

-- 🧾 Mapping: auth.users ↔ thelodge.employees
CREATE TABLE IF NOT EXISTS auth.user_employees (
    user_id INT NOT NULL REFERENCES auth.users(user_id) ON DELETE CASCADE,
    emp_id INT NOT NULL REFERENCES thelodge.employee(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, emp_id)
);

-- 🧾 Mapping: auth.users ↔ thelodge.guests
CREATE TABLE IF NOT EXISTS auth.user_guests (
    user_id INT NOT NULL REFERENCES auth.users(user_id) ON DELETE CASCADE,
    guest_id INT NOT NULL REFERENCES thelodge.guest(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, guest_id)
);

-- =====================================================================
-- Sample Data
-- =====================================================================

-- Users
INSERT INTO auth.users (username, password_hash) VALUES
('alice', 'hash_alice'),
('bob', 'hash_bob'),
('carol', 'hash_carol');

-- Roles
INSERT INTO auth.roles (role_name, description) VALUES
('admin', 'Administrator with full access'),
('manager', 'Manager with limited write access'),
('viewer', 'Read-only access');

-- Permissions
INSERT INTO auth.permissions (permission_name, description) VALUES
('read_bookings', 'Can view bookings'),
('edit_bookings', 'Can modify bookings'),
('delete_bookings', 'Can delete bookings'),
('read_hotels', 'Can view hotels'),
('edit_hotels', 'Can modify hotel info');

-- Assign users to roles
INSERT INTO auth.user_roles VALUES
(1, 1), -- alice → admin
(2, 2), -- bob → manager
(3, 3); -- carol → viewer

-- Assign permissions to roles
-- admin: all permissions
INSERT INTO auth.role_permissions
SELECT 1, permission_id FROM auth.permissions;

-- manager: limited permissions
INSERT INTO auth.role_permissions VALUES
(2, 1), -- read_bookings
(2, 2), -- edit_bookings
(2, 4), -- read_hotels

-- viewer: only read permissions
INSERT INTO auth.role_permissions VALUES
(3, 1), -- read_bookings
(3, 4); -- read_hotels


-- Link users to employees
INSERT INTO auth.user_employees (user_id, emp_id) VALUES
(1, 101), -- Alice
(2, 102); -- Bob

-- Link users to guests
INSERT INTO auth.user_guests (user_id, guest_id) VALUES
(2, 202), -- Bob
(3, 201); -- Carol

-- =====================================================================
-- 📋 Optional: drop all (for testing)
-- =====================================================================
-- DROP SCHEMA auth CASCADE;
