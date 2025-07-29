-- 🧹 Recommended single-column indexes
-- (if log is NOT partitioned)
CREATE INDEX IF NOT EXISTS idx_audit_table_name
    ON audit.log (table_name);

CREATE INDEX IF NOT EXISTS idx_audit_changed_by
    ON audit.log (changed_by);

CREATE INDEX IF NOT EXISTS idx_audit_changed_at_desc
    ON audit.log (changed_at DESC);

CREATE INDEX IF NOT EXISTS idx_audit_row_pk_gin
    ON audit.log USING GIN (row_pk);

-- ===================================================================
-- 🧹 Composite (multi-column) indexes
-- (based on common query patterns)

-- Example: Queries filtered by table_name + changed_at
CREATE INDEX IF NOT EXISTS idx_audit_table_changed_at
    ON audit.log (table_name, changed_at DESC);

-- Example: Queries filtered by table_name + changed_by
CREATE INDEX IF NOT EXISTS idx_audit_table_changed_by
    ON audit.log (table_name, changed_by);

-- Example: Queries filtered by table_name + changed_by + changed_at
CREATE INDEX IF NOT EXISTS idx_audit_table_changed_by_at
    ON audit.log (table_name, changed_by, changed_at DESC);
