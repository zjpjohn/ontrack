-- 27 . Adding missing indexes (#372)

CREATE INDEX IF NOT EXISTS ENTITY_DATA_IX_NAME ON ENTITY_DATA(NAME);
