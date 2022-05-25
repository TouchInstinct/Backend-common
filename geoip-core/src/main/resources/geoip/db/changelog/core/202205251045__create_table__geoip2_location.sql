CREATE TABLE "geoip2_location" (
    "geoname_id"             INT  NOT NULL,
    "locale_code"            TEXT NOT NULL,
    "continent_code"         TEXT NOT NULL,
    "continent_name"         TEXT NOT NULL,
    "country_iso_code"       TEXT,
    "country_name"           TEXT,
    "is_in_european_union"   BOOL NOT NULL,
    PRIMARY KEY ("geoname_id", "locale_code")
);
