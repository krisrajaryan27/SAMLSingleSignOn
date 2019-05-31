use talentpool;

-- moved the below scripts to 13.0.0 in order to make upgrades from 12.9.9 to 13.0.0 work properly
-- UPDATE tp_users SET password_date_modified =  now() where password_date_modified is null;