CREATE TABLE "geoip2_network" (
    "id"                             BIGSERIAL NOT NULL PRIMARY KEY,
    "network"                        CIDR      NOT NULL,
    "geoname_id"                     INT,
    "registered_country_geoname_id"  INT,
    "represented_country_geoname_id" INT,
    "is_anonymous_proxy"             BOOL,
    "is_satellite_provider"          BOOL
);

CREATE INDEX ON "geoip2_network" USING "gist" ("network" "inet_ops");
