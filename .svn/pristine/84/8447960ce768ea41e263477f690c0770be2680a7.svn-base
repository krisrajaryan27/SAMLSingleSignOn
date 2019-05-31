use talentpool;
    
CREATE TABLE tp_user_oauthToken (
	userid  INT, 
	oauth_token VARCHAR(255)  NOT NULL, 
	source_id INT NOT NULL, 
	PRIMARY KEY (userid,source_id) , 
	FOREIGN KEY (source_id) REFERENCES tp_sources(source_id)
);

CREATE TABLE tp_social_settings (     
	social_id VARCHAR(20) NOT NULL,       
    social_name VARCHAR(100) NOT NULL,     
    url VARCHAR(150) DEFAULT NULL,      
    implementation_id INT(11) NOT NULL,
    UNIQUE (social_id,implementation_id)
);
    
CREATE TABLE tp_position_social_posting_history ( 
	position_id BIGINT(20) NOT NULL , 
  	source_id INT(11) NOT NULL , 
  	social_id VARCHAR(20) , 
  	date_posted DATE NOT NULL
);

CREATE TABLE tp_social_api_implementation(
	implementation_id INT NOT NULL, 
	api_type VARCHAR(40) NOT NULL,
	source_id INT(11) NOT NULL,
	PRIMARY KEY (implementation_id), 
	FOREIGN KEY (source_id) REFERENCES tp_sources(source_id)
);

CREATE TABLE tp_applicant_user_person_map ( 
	person_id BIGINT(20) NOT NULL AUTO_INCREMENT , 
	applicant_id BIGINT(20) , 
	user_id BIGINT(20) , 
	graph_status BOOLEAN NOT NULL DEFAULT '0' , 
	PRIMARY KEY (person_id)
);

CREATE TABLE tp_uuid_oauth_map (      
	uuid VARCHAR(150) NOT NULL,         
	oauth_token VARCHAR(255) NOT NULL,
	source_id INT(11) NOT NULL,
	date_created DATETIME NOT NULL,     
	PRIMARY KEY  (uuid),
	KEY FK_tp_uuid_oauth_map_source_id (source_id),                                                          
	CONSTRAINT FK_tp_uuid_oauth_map_source_id FOREIGN KEY (source_id) REFERENCES tp_sources (source_id)
);

CREATE TABLE tp_applicant_oauthtoken (                                                                                                           
	applicant_id bigint(20) NOT NULL,                                                                                                              
    oauth_token varchar(255) NOT NULL,                                                                                                             
	source_id int(11) NOT NULL,                                                                                                                    
	UNIQUE KEY unique_applicant_source (applicant_id,source_id),                                                                               
	KEY FK_tp_applicant_oauthtoken_source_id (source_id),                                                                                        
	CONSTRAINT FK_tp_applicant_oauthtoken_source_id FOREIGN KEY (source_id) REFERENCES tp_sources (source_id),                               
	CONSTRAINT FK_tp_applicant_oauthtoken_applicant_id FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE  
);

CREATE TABLE tp_social_credentials (                                                                                                           
	client_id varchar(255) NOT NULL,                                                                                                              
	client_secret varchar(255) NOT NULL,                                                                                                             
	source_id int(11) NOT NULL, 
	company_id varchar(255) NOT NULL,
	PRIMARY KEY (source_id),
	KEY Fk_source_id(source_id),
	CONSTRAINT Fk_source_id FOREIGN KEY (source_id) REFERENCES tp_sources (source_id) ON DELETE CASCADE
);

CREATE TABLE `tp_user_information_shared` (                                                                       
                      `userid` int(11) NOT NULL default '0',                                                                  
                      `source_id` varchar(11),                                                                           
                      `information_shared` tinyint(4) NOT NULL default '0',                                                   
                      PRIMARY KEY  (`userid`)                                                               
);
