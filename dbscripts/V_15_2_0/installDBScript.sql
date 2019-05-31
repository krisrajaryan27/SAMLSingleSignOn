use talentpool;

SET FOREIGN_KEY_CHECKS=0;
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'send_email_notification_to_hr_for_employee_portal','0');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'send_email_to_hr_for_employee_portal',NULL);
insert into `tp_template_generic_vars`(`template_variable_type`,`template_variable`) values ( '24','APPOINTMENT_INTERVIEW_MODE');
insert into `tp_template_generic_vars`(`template_variable_type`,`template_variable`) values ( '24','APPOINTMENT_INTERVIEW_MODE_DETAILS');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '1','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '2','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '3','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '6','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '7','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '8','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '4','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '5','24');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '13','24');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'applicant_interview_mode','Skype,Telephonic,face_2_face,VC_Interview');



#naukri integration

INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('is_naukri_integration','0');
INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('hiring_org_website','');
INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('hiring_org_name','');
INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('org_description','');
INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('microsite_name','');
INSERT INTO tp_application_properties (application_property,application_property_value) 
VALUES ('template_name','');

INSERT INTO tp_template_vars (template_type_id, template_variable_type) values (12,3);

insert into tp_naukri_industry_codes (industry_id, industry) values 
('2','Travel / Hotels / Restaurants / Airlines / Railways'),
('3','Textiles / Garments / Accessories'),
('4','Automobile / Auto Anciliary / Auto Components'),
('5','Chemicals / PetroChemical / Plastic / Rubber'),
('6','BPO / Call Centre / ITES'),
('7','Accounting / Finance'),
('8','FMCG / Foods / Beverage'),
('9','Consumer Electronics / Appliances / Durables'),
('10','Construction / Engineering / Cement / Metals'),
('11','Export / Import'),
('12','Banking / Financial Services / Broking'),
('13','IT-Hardware & Networking'),
('14','Industrial Products / Heavy Machinery'),
('15','Insurance'),
('16','Courier / Transportation / Freight / Warehousing'),
('17','Media / Entertainment / Internet'),
('18','Medical / Healthcare / Hospitals'),
('19','Office Equipment / Automation'),
('20','Pharma / Biotech / Clinical Research'),
('21','Oil and Gas / Energy / Power / Infrastructure'),
('22','Retail / Wholesale'),
('23','IT-Software / Software Services'),
('24','Education / Teaching / Training'),
('25','Telecom/ISP'),
('26','Semiconductors / Electronics'),
('27','Other'),
('28','Architecture / Interior Design'),
('29','Fresher / Trainee / Entry Level'),
('30','Advertising / PR / MR / Event Management'),
('31','Agriculture / Dairy'),
('32','Recruitment / Staffing'),
('33','Gems / Jewellery'),
('34','Legal'),
('35','NGO / Social Services / Regulators / Industry Associations'),
('36','Printing / Packaging'),
('37','Real Estate / Property'),
('38','Security / Law Enforcement'),
('39','Fertilizers / Pesticides'),
('40','Government / Defence'),
('41','Pulp and Paper'),
('42','Shipping / Marine'),
('43','Tyres'),
('44','Aviation / Aerospace Firms'),
('45','Facility Management'),
('46','KPO / Research / Analytics'),
('47','Glass / Glassware'),
('48','Brewery / Distillery'),
('49','Water Treatment / Waste Management'),
('50','Strategy / Management Consulting Firms'),
('51','Iron and Steel'),
('52','Mining / Quarrying'),
('53','Electricals / Switchgears'),
('54','Animation / Gaming'),
('55','Food Processing'),
('56','Publishing'),
('57','Wellness / Fitness / Sports'),
('58','Ceramics / Sanitary ware'),
('59','Heat Ventilation / Air Conditioning'),
('60','Internet / Ecommerce'),
('61','Sugar'),
('62','Broadcasting'),
('63','Leather');	

insert into tp_naukri_country_codes (country_id, country) values
('-1','India'),
('1.0.0','Australia'),
('10.0.0','Ireland'),
('100.0.0','Equitorial Guinea'),
('101.0.0','Falkland Island'),
('102.0.0','Faroe Island'),
('103.0.0','Gibraltar'),
('104.0.0','Greenland'),
('105.0.0','Guadelope'),
('106.0.0','Guam'),
('107.0.0','Guinea Bissau'),
('108.0.0','Kampuchea'),
('109.0.0','Kiribati'),
('11.0.0','Japan'),
('110.0.0','Lesotho'),
('111.0.0','Macau'),
('112.0.0','Madeira Island'),
('113.0.0','Marshall Island'),
('114.0.0','Nauru'),
('115.0.0','Norfolk Island'),
('116.0.0','Palau'),
('117.0.0','Papua New Guinea'),
('118.0.0','Sao Tome'),
('119.0.0','Slovak Republic'),
('12.0.0','Kenya'),
('120.0.0','St. Kitts & Navis'),
('121.0.0','St. Vincent & Grenadian'),
('122.0.0','Togo'),
('123.0.0','Tuvalu'),
('124.0.0','Virgin Island'),
('125.0.0','Zaire'),
('13.0.0','Kuwait'),
('14.0.0','Malaysia'),
('15.0.0','Nepal'),
('16.0.0','New Zealand'),
('17.0.0','Nigeria'),
('18.0.0','Oman'),
('19.0.0','Saudi Arabia'),
('2.0.0','Bahrain'),
('20.0.0','Singapore'),
('21.0.0','Sri Lanka'),
('22.0.0','Thailand'),
('23.0.0','United Kingdom (UK)'),
('24.0.0','United States (USA)'),
('26.0.0','Other International Location '),
('27.0.0','Afghanistan'),
('28.0.0','Angola'),
('29.0.0','Argentina'),
('3.0.0','Bangladesh'),
('30.0.0','Austria'),
('31.0.0','Belgium'),
('32.0.0','Bhutan'),
('33.0.0','Brazil'),
('34.0.0','Bulgaria'),
('35.0.0','Cambodia'),
('36.0.0','Central African Republic'),
('37.0.0','Chile'),
('38.0.0','China'),
('39.0.0','Colombia'),
('4.0.0','Canada'),
('40.0.0','Costa Rica'),
('41.0.0','Czech Republic'),
('42.0.0','Denmark'),
('43.0.0','Doha'),
('44.0.0','Egypt'),
('45.0.0','Ethiopia'),
('46.0.0','Fiji'),
('47.0.0','Finland'),
('48.0.0','Georgia'),
('49.0.0','Ghana'),
('5.0.0','UAE/Dubai/Abu Dhabi'),
('51.0.0','Greece'),
('52.0.0','Guatemala'),
('53.0.0','Guinea'),
('54.0.0','Hungary'),
('55.0.0','Iran'),
('56.0.0','Iraq'),
('57.0.0','Israel'),
('58.0.0','Italy'),
('59.0.0','Kazakhstan'),
('6.0.0','France'),
('60.0.0','Lebanon'),
('61.0.0','Libya'),
('62.0.0','Luxembourg'),
('63.0.0','Maldives'),
('64.0.0','Mauritius'),
('65.0.0','Mexico'),
('66.0.0','Morocco'),
('67.0.0','Myanmar(Burma) '),
('68.0.0','Namibia'),
('69.0.0','Netherlands'),
('7.0.0','Germany'),
('70.0.0','Norway'),
('71.0.0','Pakistan'),
('72.0.0','Philippines'),
('73.0.0','Qatar'),
('74.0.0','Quilon'),
('75.0.0','Russia'),
('76.0.0','South Africa'),
('77.0.0','South Korea'),
('78.0.0','Spain'),
('79.0.0','Sweden'),
('8.0.0','Hong Kong'),
('80.0.0','Switzerland'),
('81.0.0','Sudan'),
('82.0.0','Taiwan'),
('83.0.0','Tanzania'),
('84.0.0','Uganda'),
('85.0.0','Yemen'),
('86.0.0','Zimbabwe'),
('87.0.0','Alaska'),
('88.0.0','Andorra'),
('89.0.0','Anguilla'),
('9.0.0','Indonesia'),
('90.0.0','Antigua'),
('91.0.0','Aruba'),
('92.0.0','Ascention Island'),
('93.0.0','Azores'),
('94.0.0','Bermuda'),
('95.0.0','Canary Island'),
('96.0.0','Cape Verde'),
('97.0.0','Christmas Island'),
('98.0.0','Ciskei'),
('99.0.0','Diego Garcia');

insert into tp_naukri_location_codes (city_id, city) values
('25.31.162','Aligarh'),
('25.32.176','Dehradun'),
('25.12.41','Bhavnagar'),
('25.27.201','Bhilwara'),
('25.27.292','Bhiwadi'),
('25.6.25','Bilaspur'),
('25.14.259','Chamba'),
('25.26.23','Chandigarh'),
('25.29.146','Chennai'),
('25.29.148','Cuddalore'),
('25.14.62','Dharmasala'),
('25.20.276','Dhule'),
('25.16.70','Dhanbad'),
('25.18.86','Ernakulam / Kochi/ Cochin'),
('25.13.53','Faridabad'),
('25.26.291','Faridkot'),
('25.12.186','Gandhinagar'),
('25.12.245','Godhra'),
('25.26.286','Gurdaspur'),
('25.32.303','Haridwar'),
('25.13.55','Hisar'),
('25.1.4','Hyderabad / Secunderabad'),
('25.19.97','Indore'),
('25.17.78','Dharwad'),
('25.26.129','Jalandhar'),
('25.20.103','Jalgaon'),
('25.12.246','Junagadh'),
('25.1.5','Kakinada'),
('25.12.45','Kandla'),
('25.31.168','Kanpur'),
('25.24.118','Dimapur'),
('25.26.285','Firozpur'),
('25.31.167','Gorakhpur'),
('25.1.2','Guntakal'),
('25.1.3','Guntur'),
('25.3.17','Guwahati'),
('25.18.196','Idukki'),
('25.13.52','Ambala'),
('25.21.112','Imphal'),
('25.27.137','Jaisalmer'),
('25.27.138','Jodhpur'),
('25.1.237','Kamalapuram'),
('25.29.299','Kanchipuram'),
('25.19.96','Gwalior'),
('25.1.235','Adilabad'),
('25.12.36','Ahmedabad'),
('25.27.135','Ajmer'),
('25.20.273','Amravati'),
('25.31.163','Allahabad'),
('25.26.127','Amritsar'),
('25.1.1','Anantapur'),
('25.25.278','Angul'),
('25.12.38','Ankleshwar'),
('25.33.179','Asansol'),
('25.27.294','Banswara'),
('25.26.290','Banur'),
('25.20.274','Baramati'),
('25.26.128','Bathinda'),
('25.20.102','Aurangabad'),
('25.13.254','Bawal'),
('25.17.75','Belgaum'),
('25.17.76','Bellary'),
('25.4.20','Bhagalpur'),
('25.12.40','Bharuch'),
('25.6.24','Bhilai/Bhillai'),
('25.13.256','Bhiwani'),
('25.19.95','Bhopal'),
('25.12.42','Bhuj'),
('25.27.295','Bikaner'),
('25.26.287','Kala'),
('25.16.69','Bokaro'),
('25.20.275','Chiplun'),
('25.29.147','Coimbatore'),
('25.25.121','Cuttack'),
('25.14.61','Dalhousie'),
('25.10.29','Daman & Diu'),
('25.9.30','Delhi'),
('25.9.31','Delhi/NCR(National Capital Region)'),
('25.13.255','Dharuhera'),
('25.25.120','Bhubaneshwar'),
('25.33.180','Durgapur'),
('25.29.149','Erode'),
('25.31.165','Faizabad'),
('25.31.307','Gajraula'),
('25.27.296','Ganganagar'),
('25.28.142','Gangtok'),
('25.4.241','Gaya'),
('25.31.166','Ghaziabad'),
('25.12.43','Gir'),
('25.17.79','Gulbarga'),
('25.17.77','Bidar'),
('25.13.54','Gurgaon'),
('25.33.181','Haldia'),
('25.26.283','Hoshiarpur'),
('25.17.263','Hospet'),
('25.29.144','Hosur'),
('25.17.80','Hubli'),
('25.2.15','Itanagar'),
('25.19.98','Jabalpur'),
('25.27.136','Jaipur'),
('25.12.44','Jamnagar'),
('25.1.236','Cuddapah'),
('25.16.71','Jamshedpur'),
('25.15.66','Jammu'),
('25.31.308','Jhansi'),
('25.13.257','Jind'),
('25.29.301','Kalpakkam'),
('25.18.87','Kannur / Cannannore'),
('25.26.284','Kapurthala'),
('25.30.159','Agartala'),
('25.31.161','Agra'),
('25.20.101','Ahmednagar'),
('25.10.28','Dadra & Nagar Haveli / Silvassa'),
('25.23.116','Aizawl'),
('25.20.272','Akola'),
('25.18.195','Alappuzha / Alleppey'),
('25.27.293','Alwar'),
('25.12.37','Anand'),
('25.14.194','Baddi'),
('25.31.306','Bahraich'),
('25.31.164','Bareilly'),
('25.26.282','Barnala'),
('25.17.74','Bengaluru/Bangalore'),
('25.1.238','Karimnagar'),
('25.13.56','Karnal'),
('25.29.300','Karur'),
('25.18.197','Kasargode'),
('25.1.239','Khammam'),
('25.33.182','Kharagpur'),
('25.24.277','Kohima'),
('25.17.81','Kolar'),
('25.20.104','Kolhapur'),
('25.33.183','Kolkata'),
('25.18.88','Kollam / Quilon'),
('25.17.261','Koppal'),
('25.27.139','Kota'),
('25.18.89','Kottayam'),
('25.18.85','Kozhikode / Calicut'),
('25.14.63','Kullu/Manali'),
('25.1.6','Kurnool'),
('25.13.57','Kurukshetra'),
('25.12.247','Lakhtar'),
('25.10.202','Lakshadweep'),
('25.31.169','Lucknow'),
('25.26.130','Ludhiana'),
('25.29.150','Madurai'),
('25.18.198','Malappuram'),
('25.29.302','Mamandur'),
('25.14.260','Mandi'),
('25.13.249','Manesar'),
('25.17.82','Mangalore'),
('25.31.170','Mathura'),
('25.31.171','Meerut'),
('25.26.288','Moga'),
('25.26.131','Mohali'),
('25.31.175','Moradabad'),
('25.26.289','Morinda'),
('25.27.298','Mount Abu'),
('25.20.105','Mumbai'),
('25.20.106','Mumbai Suburbs'),
('25.12.243','Mundra'),
('25.4.242','Munger'),
('25.4.240','Muzaffarpur'),
('25.17.83','Mysore'),
('25.27.297','Nagar'),
('25.29.145','Nagercoil'),
('25.20.107','Nagpur'),
('25.20.108','Nasik'),
('25.20.190','Navi Mumbai'),
('25.1.7','Nellore'),
('25.1.8','Nizamabad'),
('25.31.172','Noida'),
('25.29.151','Ooty'),
('25.31.309','Orai'),
('50.50.50','Other National Locations'),
('25.18.90','Palakkad / Palghat'),
('25.13.258','Palwal'),
('25.13.251','Panchkula'),
('25.13.58','Panipat'),
('25.5.33','Panjim/Panaji'),
('25.32.305','Pantnagar'),
('25.25.122','Paradeep'),
('25.18.199','Pathanamthitta'),
('25.26.132','Pathankot'),
('25.26.133','Patiala'),
('25.4.21','Patna'),
('25.26.310','Phagwara'),
('25.10.126','Pondicherry'),
('25.12.46','Porbandar'),
('25.34.191','Port Blair'),
('25.20.109','Pune'),
('25.25.123','Puri'),
('25.20.268','Raigad'),
('25.6.193','Raigarh'),
('25.6.26','Raipur'),
('25.1.9','Rajahmundry'),
('25.12.47','Rajkot'),
('25.26.279','Rajpura'),
('25.16.72','Ranchi'),
('25.19.266','Ratlam'),
('25.19.267','Rewa'),
('25.13.253','Rewari'),
('25.13.59','Rohtak'),
('25.32.177','Roorkee'),
('25.25.124','Rourkela'),
('25.32.304','Rudrapur'),
('25.26.280','Rupnagar'),
('25.29.152','Salem'),
('25.26.281','Sangrur'),
('25.20.270','Satara'),
('25.19.265','Satna'),
('25.22.114','Shillong'),
('25.14.64','Shimla'),
('25.17.262','Shimoga'),
('25.3.18','Silchar'),
('25.33.184','Siliguri'),
('25.20.110','Solapur'),
('25.13.250','Sonepat'),
('25.15.67','Srinagar'),
('25.12.48','Surat'),
('25.1.248','Surendranagar'),
('25.20.271','Tarapur'),
('25.3.311','Tezpur'),
('25.20.269','Thane'),
('25.29.153','Thanjavur'),
('25.18.92','Thrissur / Trichur'),
('25.29.154','Tirunelveli'),
('25.1.10','Tirupati'),
('25.29.155','Trichy'),
('25.18.93','Trivandrum'),
('25.17.264','Tumkur'),
('25.29.156','Tuticorin'),
('25.27.140','Udaipur'),
('25.19.99','Ujjain'),
('25.12.39','Vadodara/Baroda'),
('25.12.49','Valsad'),
('25.12.50','Vapi'),
('25.31.173','Varanasi'),
('25.5.34','Vasco Da Gama'),
('25.29.157','Vellore'),
('25.12.244','Veraval'),
('25.1.11','Vijayawada'),
('25.1.12','Visakhapatnam/Vizag'),
('25.1.13','Warangal'),
('25.18.200','Wayanad'),
('25.13.252','Yamunanagar');

insert into tp_naukri_functional_area_codes (farea_id, farea) values
('1','Accounts / Finance / Tax / Company Secretary / Audit'),
('6','Financial Services/Banking/Investments/Insurance'),
('21','Engineering Design / R&D'),
('12','HR /Recruitment/Administration / IR'),
('8','ITES / BPO / KPO /LPO/ Customer Service / Operations'),
('15','Marketing / Advertising / MR / PR / Media Planning'),
('19','Production / Manufacturing / Maintenance'),
('22','Sales / Retail / Business Development'),
('11','Executive Assistant / Front Office / Data Entry'),
('20','Site Engineering / Project Management'),
('2','Architecture / Interior Design'),
('5','Journalism/Editing/Content'),
('7','Strategy / Management Consulting/ Corporate Planning'),
('10','Export / Import / Merchandising'),
('42','Fashion Designing/ Merchandising'),
('45','Defence Forces / Security Services'),
('4','Hotels / Restaurants'),
('24.01','IT Software - Application Programming / Maintenance'),
('24.02','IT Software - Client/Server Programming'),
('24.03','IT Software - DBA / Datawarehousing'),
('24.04','IT Software - ERP / CRM'),
('24.05','IT Software - Embedded / EDA / VLSI / ASIC / Chip Design'),
('24.06','IT Software - Network Administration / Security'),
('24.07','IT Software - Other'),
('24.08','IT Software - QA & Testing'),
('24.09','IT Software - System Programming'),
('24.1','IT Software - Telecom Software'),
('24.11','IT Software - Systems / EDP / MIS'),
('24.12','IT Software - E-Commerce / Internet Technologies'),
('24.13','IT Software - Mainframe'),
('24.14','IT Software - Mobile'),
('24.15','IT Software - Middleware'),
('37','IT- Hardware / Technical Support/ Telecom Engineering'),
('13','Legal / Regulatory / Intellectual Property'),
('18','Packaging'),
('16','Medical / Healthcare / R&D / Pharmaceuticals / Biotechnology  '),
('14','Supply Chain / Logistics / Purchase / Materials'),
('9','Self Employed / Entrepreneur / Independent Consultant'),
('36','Teaching / Education / Training / Counselling'),
('44','Travel / Tours / Ticketing / Airlines'),
('39','Top Management'),
('43','TV / Films / Production / Broadcasting'),
('3','Design/Creative/User Experience'),
('41','Other');



insert into tp_naukri_role_codes (role_id, role_name) values
('1.01','Accounts Executive/Accountant'),
('1.02','Cost Accountant'),
('1.03','Taxation(Direct) Manager'),
('1.04','Taxation(Indirect) Manager'),
('1.05','Accounts Manager'),
('1.06','Financial Accountant'),
('1.07','ICWA'),
('1.08','Chartered Accountant'),
('1.09','Finance Executive'),
('1.10','Credit/Control Executive'),
('1.11','Investor Relationship-Executive/Manager'),
('1.12','Credit/Control Manager'),
('1.13','Financial Analyst'),
('1.14','Audit Manager'),
('1.15','Forex Manager'),
('1.16','Treasury Manager'),
('1.17','Finance/Budgeting Manager'),
('1.18','Head/VP/GM-Finance/Audit'),
('1.19','Head/VP/GM-Accounts'),
('1.20','Head/VP/GM/CFO/Financial Controller'),
('1.21','Head/VP/GM-Regulatory Affairs'),
('1.22','Company Secretary'),
('1.23','Outside Consultant'),
('1.24','Fresher'),
('1.26','Trainee'),

('6.01','Customer Service Executive'),
('6.02','Customer Service Manager'),
('6.03','Collections Officer'),
('6.04','Collections Manager'),
('6.05','CRM/Phone/Internet Banking Executive'),
('6.06','Sales Officer'),
('6.07','Credit Officer'),
('6.08','Branch Manager'),
('6.09','Regional Manager'),
('6.10','National Head'),
('6.11','Asset Operations/Documentation-Executive/Manager'),
('6.12','Domestic Private Banking-Executive/Manager'),
('6.13','Product Manager-Auto/Home Loans'),
('6.14','Cards-Sales Officer/Executive'),
('6.15','Cards-Operations Executive'),
('6.16','Cards-Operations Manager'),
('6.17','Collections Executive'),
('6.18','Card Approvals Officer'),
('6.19','Merchant Acquisition Executive'),
('6.20','Business Alliances Manager'),
('6.21','Product Manager-Cards'),
('6.22','Back Office Executive'),
('6.23','Money Markets Dealer'),
('6.24','Forex Dealer'),
('6.25','Sales/Business Development Manager-Forex'),
('6.26','Forex Operations Manager'),
('6.27','Debt Instrument Dealer'),
('6.28','Sales/Business Development Manager-Debt Instruments'),
('6.29','Debt Operations Manager'),
('6.30','Derivatives Dealer'),
('6.31','Sales/Business Development Manager-Derivatives'),
('6.32','Treasury Operations Manager'),
('6.33','Clearing Officer'),
('6.34','Cash Officer'),
('6.35','Operations Officer'),
('6.36','Operations Manager'),
('6.37','Depository Services-Executive/Manager'),
('6.38','Legal Officer'),
('6.39','Legal Manager'),
('6.40','Operations Manager'),
('6.41','Trade Finance Operations Manager'),
('6.42','Technology Manager'),
('6.43','ATM Operations Manager'),
('6.44','Audit Manager'),
('6.45','Finance/Budgeting Manager'),
('6.46','Relationship Executive'),
('6.47','Client Servicing/Key Account Manager'),
('6.48','Credit Analyst-Corporate Banking'),
('6.49','Credit Manager-Corporate Banking'),
('6.50','Bad Debts/Workouts Manager'),
('6.52','Debt Analyst'),
('6.53','Mergers & Acquisitions Analyst'),
('6.54','Equity Analyst'),
('6.55','Equity Manager'),
('6.56','Domestic Debt Manager'),
('6.57','Offshore Debt Manager'),
('6.58','Mergers & Acquisitions Manager'),
('6.59','Corporate Advisory Manager'),
('6.60','Project Finance Manager'),
('6.61','Issues/IPO Manager'),
('6.62','Legal Officer'),
('6.63','Legal Manager'),
('6.64','Insurance Analyst'),
('6.65','Actuary Manager'),
('6.66','Underwriter'),
('6.67','Insurance Advisor'),
('6.68','Unit Manager'),
('6.69','Sales/Business Development-Manager'),
('6.70','Branch Manager'),
('6.71','Product Manager'),
('6.72','Sales Head'),
('6.73','Regional Manager'),
('6.74','Legal Officer'),
('6.75','Legal Manager'),
('6.77','Insurance Analyst'),
('6.78','Actuary Manager'),
('6.79','Underwriter'),
('6.80','Head-Underwriting'),
('6.81','Insurance Advisor'),
('6.82','Unit Manager'),
('6.83','Sales/Business Development-Manager'),
('6.84','Branch Manager'),
('6.85','Product Manager'),
('6.86','Sales Head'),
('6.87','Regional Manager'),
('6.88','Legal Officer'),
('6.89','Legal Manager'),
('6.90','Bancassurance'),
('6.91','Insurance Operations Officer'),
('6.92','Insurance Operations Manager'),
('6.93','CRM/Customer Service Executive'),
('6.94','CRM/Customer Service Manager'),
('6.95','Claims Executive'),
('6.96','Claims Manager'),
('6.97','Investment/Treasury Manager'),
('6.98','Analyst'),
('6.99','Broker/Trader'),
('6.10','National Head'),
('6.10','Sales Executive/Investment Advisor'),
('6.10','Sales/Business Development Manager'),
('6.10','Marketing Manager'),
('6.10','Portfolio Manager'),
('6.11','Analyst'),
('6.11','CRM/Customer Service Executive'),
('6.11','CRM/Customer Service Manager'),
('6.11','Operations Executive'),
('6.11','Operations Manager'),
('6.11','Asset Operations/Documentation-Executive/Manager'),
('6.11','Fund Manager-Equity'),
('6.11','Private Equity/Hedge Fund/VC-Manager'),
('6.11','Head/VP/GM-Treasury'),
('6.11','Head/VP/GM-Legal'),
('6.12','Head/VP/GM-Operations'),
('6.12','Head/VP/GM/CFO/Financial Controller'),
('6.12','Head/VP/GM-Depository Services'),
('6.12','Head/VP/GM-Relationships'),
('6.12','Head/VP/GM-Credit Risk'),
('6.12','Domestic Private Banking-Executive/Manager'),
('6.12','Head/VP/GM-Domestic/Offshore Debt'),
('6.12','Head/VP/GM-Mergers & Acquisitions'),
('6.12','Head/VP/GM-Corporate Advisory'),
('6.12','Head/VP/GM-Project Finance'),
('6.13','Head/VP/GM-Investment Banking'),
('6.13','Head/VP/GM-Underwriting'),
('6.13','Head/VP/GM-Marketing'),
('6.13','Head/VP/GM-Insurance Operations'),
('6.13','Head/VP/GM-Claims'),
('6.13','Product Manager-Auto/Home Loans'),
('6.13','Head/VP/GM-Fund Management'),
('6.13','Head/VP/GM-Private Equity/Hedge Fund/VC'),
('6.13','Head/VP/GM-Broking'),

('21.01','R&D Executive'),
('21.02','Clinical Research Associate/Scientist'),
('21.03','Clinical Research Manager'),
('21.04','Analytical Chemistry Associate/Scientist'),
('21.05','Analytical Chemistry Manager'),
('21.06','Chemical Research Associate/Scientist'),
('21.07','Chemical Research Manager'),
('21.08','Bio/Pharma Informatics-Associate/Scientist'),
('21.09','Formulation Scientist'),
('21.10','Microbiologist'),
('21.11','Molecular Biology'),
('21.13','Nutritionist'),
('21.14','Research Scientist'),
('21.15','Bio-Technical Research Associate/Scientist'),
('21.16','Bio-Technical Research Manager'),
('21.17','Pharmacist/Chemist/Bio Chemist'),
('21.18','Bio-Statistician'),
('21.19','Lab Technician/Medical Technician/Lab Staff'),
('21.20','Product Development Executive'),
('21.21','Product Development Manager'),
('21.22','Drug Regulatory Director'),
('21.23','Documentation/Medical Writing'),
('21.24','Regulatory Affairs Manager'),
('21.25','Quality Assurance & Quality Control-Executive'),
('21.26','Quality Assurance & Quality Control-Manager'),
('21.27','Design Engineer'),
('21.28','Senior Design Engineer'),
('21.29','Technical Lead/Project Lead'),
('21.30','Head/VP/GM-R&D'),
('21.31','Head/VP/GM-Production'),
('21.32','Head/VP/GM-Formulations'),
('21.33','Head/VP/GM-Quality Assurance/Quality Control'),
('21.34','Head/VP/GM-Regulatory Affairs'),
('21.35','Research Associate'),
('21.36','Fresher'),
('21.39','Postdoc Position/Fellowship'),
('21.40','Practical Training/Internship'),
('21.38','Trainee'),

('12.01','HR Executive'),
('12.02','HR Manager'),
('12.03','Recruitment Executive'),
('12.04','Recruitment Manager'),
('12.05','Pay Roll/Compensation Manager'),
('12.06','Performance Management Manager'),
('12.07','Industrial/Labour Relations Manager'),
('12.08','Training Manager'),
('12.09','Administration/Facilities Executive'),
('12.10','Administration/Facilities Manager'),
('12.11','Head/VP/GM-HR'),
('12.12','Head/VP/GM-Training & Development'),
('12.13','Head/VP/GM-Administration & Facilities'),
('12.14','Head/VP/GM-Recruitment'),
('12.15','Outside Consultant'),
('12.17','Trainee'),
('12.18','Fresher'),

('8.01','Associate/Senior Associate -(NonTechnical)'),
('8.02','Associate/Senior Associate -(Technical)'),
('8.03','Team Leader -(NonTechnical)'),
('8.04','Team Leader -(Technical)'),
('8.05','Assistant Manager/Manager -(NonTechnical)'),
('8.06','Assistant Manager/Manager -(Technical)'),
('8.07','Telecalling/Telemarketing Executive'),
('8.08','Associate/Senior Associate -(NonTechnical)'),
('8.09','Associate/Senior Associate -(Technical)'),
('8.10','Team Leader -(NonTechnical)'),
('8.11','Team Leader -(Technical)'),
('8.12','Assistant Manager/Manager -(Technical)'),
('8.13','Assistant Manager / Manager -(NonTechnical)'),
('8.14','Process Flow Analyst'),
('8.15','Business/EDP Analyst'),
('8.16','Business Development Manager'),
('8.17','Transitions/Migrations Manager'),
('8.18','Operations Manager'),
('8.19','Infrastructure & Technology Manager'),
('8.20','Dialer Manager'),
('8.21','Technical/Process Trainer'),
('8.22','Voice & Accent Trainer'),
('8.23','Soft Skills Trainer'),
('8.24','Quality Assurance/Quality Control Executive'),
('8.25','Quality Assurance/Quality Control Manager'),
('8.26','Quality Coach'),
('8.27','Team Leader-Quality Assurance/Quality Control'),
('8.28','Head/VP/GM-Operations'),
('8.29','Head/VP/GM-Training & Development'),
('8.30','Head/VP/GM-Transitions'),
('8.31','Service Delivery Leader'),
('8.32','Head/VP/GM-Quality Assurance & Quality Control'),
('8.33','Medical Transcriptionist'),
('8.34','Fresher'),
('8.35','Trainee'),
('8.36','Outside Consultant'),

('15.08','Client Servicing Executive'),
('15.09','Client Servicing/Key Account Manager'),
('15.10','Account Director'),
('15.11','Creative Director'),
('15.12','Media Planning Executive/Manager'),
('15.13','Media Buying Executive/Manager'),
('15.14','Events/Promotion Executive'),
('15.15','Events/Promotion Manager'),
('15.01','Corporate Communication Executive'),
('15.02','Direct Marketing Executive'),
('15.03','Direct Marketing Manager'),
('15.04','Product Executive'),
('15.05','Product/Brand Manager'),
('15.06','Business Alliances Manager'),
('15.07','Marketing Manager'),
('15.16','Art Director/Senior Art Director'),
('15.17','Visualiser'),
('15.18','Copywriter'),
('15.19','Graphic Designer'),
('15.20','Marketing Research Executive/Manager'),
('15.21','Marketing Research Field Supervisor'),
('15.22','Public Relations Executive'),
('15.23','Public Relations & Media Relations Manager'),
('15.24','Head/Manager/GM-Media Planning'),
('15.25','Head/Manager/GM-Media Buying'),
('15.26','Head/VP/GM-Public Relations/Corporate Communication'),
('15.27','Head/VP/GM-Marketing'),
('15.28','Head/VP/GM-Business Alliances'),
('15.29','Head/VP/GM- Marketing Research'),
('15.30','Head/VP/GM-Client Servicing'),
('15.31','National Creative Director/VP-Creative'),
('15.33','Outside Consultant'),
('15.34','Trainee'),
('15.35','Fresher'),

('19.01','Industrial Engineer'),
('19.02','Design Engineer/Manager'),
('19.03','Factory Head'),
('19.04','Engineering Manager'),
('19.05','Production Manager'),
('19.06','Quality Assurance/Quality Control Executive'),
('19.07','Quality Assurance/Quality Control Manager'),
('19.08','Product Development Executive'),
('19.09','Product Development Manager'),
('19.10','Workman/Foreman/Technician'),
('19.11','Service/Maintenance Engineer'),
('19.12','Service/Maintenance Supervisor'),
('19.13','Project Manager-Production/Manufacturing/Maintenance'),
('19.14','Safety Officer/Manager'),
('19.15','Environment Engineer/Officer'),
('19.16','Health-Officer/Manager'),
('19.17','Head/VP/GM-Quality Assurance/Quality Control'),
('19.18','Head/VP/GM-Production/Manufacturing/Maintenance'),
('19.19','Head/VP/GM-Operations'),
('19.20','SBU Head/Profit Centre Head'),
('19.21','Head/VP/GM-Regulatory Affairs'),
('19.22','Outside Consultant'),
('19.23','Trainee'),
('19.24','Fresher'),

('22.01','Sales Executive/Officer'),
('22.02','Counter Sales'),
('22.03','Medical Representative'),
('22.04','Merchandiser'),
('22.05','Sales/Business Development Manager'),
('22.06','Sales Promotion Manager'),
('22.07','Retail Store Manager'),
('22.08','Branch Manager'),
('22.09','Regional Manager'),
('22.10','Sales Executive/Officer'),
('22.11','Sales/Business Development Manager'),
('22.12','Client Servicing/Key Account Manager'),
('22.13','Branch Manager/Regional Manager'),
('22.14','Sales Executive/Officer'),
('22.15','Sales/Business Development Manager'),
('22.16','Sales Promotion Manager'),
('22.17','Banquet Sales Executive/Manager'),
('22.18','Institutional Sales/Business Development Manager'),
('22.19','Sales Trainer'),
('22.20','Telesales/Telemarketing Executive/Officer'),
('22.21','Sales Promotion Manager'),
('22.22','Front Desk/Cashier/Billing'),
('22.23','Head/VP/GM/National Manager -Sales'),
('22.26','Trainee'),
('22.27','Fresher'),

('11.01','Stenographer/Data Entry Operator'),
('11.02','Receptionist'),
('11.03','Secretary/PA'),
('11.05','Fresher'),
('11.06','Trainee'),

('20.01','Project Manager-Telecom'),
('20.02','Project Manager-IT/Software'),
('20.03','Project Manager-Production/Manufacturing/Maintenance'),
('20.04','Civil Engineer-Telecom'),
('20.05','Civil Engineer-Municipal'),
('20.06','Civil Engineer-Water/Wastewater'),
('20.07','Civil Engineer-Land Development'),
('20.08','Civil Engineer-Aviation'),
('20.09','Civil Engineer-Highway/Roadway'),
('20.10','Civil Engineer-Traffic'),
('20.12','Electrical Engineer-Telecom'),
('20.13','Electrical Engineer-Commercial'),
('20.14','Electrical Engineer-Industrial'),
('20.15','Electrical Engineer-Utility'),
('20.17','Geotechnical Engineer'),
('20.18','Mechanical Engineer-Telecom'),
('20.19','Mechanical Engineer-HVAC'),
('20.20','Mechanical Engineer-Plumbing/Fire Protection'),
('20.22','Process Engineer-Plant Design'),
('20.23','Structural Engineer-Bridge'),
('20.24','Structural Engineer-Building'),
('20.26','Geographic Information Systems/GIS'),
('20.27','Construction-General Building'),
('20.28','Construction-Heavy'),
('20.29','Construction-Residential'),
('20.30','Construction-Specialty'),
('20.31','Construction-Construction Management'),
('20.33','Maintenance Engineer'),
('20.35','Fresher'),
('20.36','Trainee'),

('2.01','Architect'),
('2.02','Draughtsman'),
('2.03','Project Architect'),
('2.04','Naval Architect'),
('2.05','Landscape Architect'),
('2.06','Town Planner'),
('2.08','Interior Designer'),
('2.09','Outside Consultant'),
('2.10','Fresher'),
('2.12','Trainee'),

('5.01','Content Developer'),
('5.02','Freelance Journalist'),
('5.03','Business Content Developer'),
('5.04','Fashion Content Developer'),
('5.05','Features Content Developer'),
('5.06','International Business Content Developer'),
('5.07','IT/Technical Content Developer'),
('5.08','Sports Content Developer'),
('5.09','Political Content Developer'),
('5.10','Journalist'),
('5.11','Sub Editor/Reporter'),
('5.12','Senior Sub Editor/Senior Reporter'),
('5.13','Correspondent/Assistant Editor/Associate Editor'),
('5.14','Principal Correspondent/Features Writer/Resident Writer'),
('5.15','Chief of Bureau/Editor in Chief'),
('5.16','Investigative Journalist'),
('5.17','Proof Reader'),
('5.18','Business Editor'),
('5.19','Fashion Editor'),
('5.20','Features Editor'),
('5.21','International Business Editor'),
('5.22','IT/Technical Editor'),
('5.23','Managing Editor'),
('5.24','Sports Editor'),
('5.25','Political Editor'),
('5.27','Trainee'),
('5.28','Fresher'),

('7.01','Outside Consultant'),
('7.02','Senior Outside Consultant'),
('7.03','Corporate Planning/Strategy Manager'),
('7.04','Research Associate'),
('7.05','Business Analyst'),
('7.06','EA to Chairman/President/VP'),
('7.07','Head/VP/GM-Corporate Planning/Strategy'),
('7.08','VP/Principal/Partner'),
('7.09','CEO/MD/Director'),
('7.11','Trainees'),
('7.12','Freshers'),

('10.01','Documentation/Shipping-Executive/Manager'),
('10.02','Production Executive'),
('10.03','Purchase Officer'),
('10.04','Floor Manager'),
('10.05','Production Manager'),
('10.06','Merchandiser'),
('10.07','Quality Assurance/Quality Control Executive'),
('10.08','Quality Assurance/Quality Control Manager'),
('10.09','Business Development Manager'),
('10.10','Head/VP/GM-Documentation/Shipping'),
('10.11','Head/VP/GM-Production'),
('10.12','Head/VP/GM-Purchase'),
('10.13','VP/GM-Quality'),
('10.14','CEO/MD/Director'),
('10.15','Liaison Officer/Manager'),
('10.16','Trader'),
('10.17','Agent'),
('10.19','Fresher'),
('10.20','Trainee'),

('42.01','Accessory Designer'),
('42.02','Apparel/Garment Designer'),
('42.03','Footwear Designer'),
('42.04','Merchandiser'),
('42.05','Textile Designer'),
('42.06','Jewellery Designer'),
('42.07','Freelancer'),
('42.09','Fresher'),
('42.10','Trainee'),

('45.01','Security Guard'),
('45.02','Security Supervisor'),
('45.03','Security Manager'),
('45.04','Policeman'),
('45.05','Army/Navy/Airforce Personnel'),
('45.06','Chief Security Officer'),
('45.08','Trainee'),
('45.09','Fresher'),

('4.01','Bartender'),
('4.02','Commis'),
('4.03','Steward'),
('4.04','Captain'),
('4.05','Host/Hostess'),
('4.06','Butler'),
('4.07','Chef De Partis'),
('4.08','Executive Sous Chef/Chef De Cuisine'),
('4.09','Sous Chef'),
('4.10','Banquet Sales Executive/ Manager'),
('4.11','Restaurant Manager'),
('4.12','F&B Manager'),
('4.13','General Manager'),
('4.14','Housekeeping Executive/Assistant'),
('4.15','Housekeeping Manager'),
('4.16','Cashier'),
('4.17','Front Office/Guest Relations Executive/Manager'),
('4.18','Travel Desk Manager'),
('4.19','Lobby/Duty Manager'),
('4.21','Executive/Master Chef'),
('4.22','Head/VP/GM-F&B'),
('4.23','Head/VP/GM/National Manager-Sales'),
('4.24','Head/VP-Public Relations/Corporate Communication'),
('4.25','Head/VP/GM-Accounts'),
('4.26','CEO/MD/Director'),
('4.27','Health Club Assistant/Manager'),
('4.28','Masseur'),
('4.31','Fresher'),
('4.32','Trainee'),

('24.01','Software Developer'),
('24.02','Team Lead/Technical Lead'),
('24.03','System Analyst'),
('24.04','Technical Architect'),
('24.05','Database Architect/Designer'),
('24.06','Project Lead'),
('24.07','Testing Engineer'),
('24.08','Product Manager'),
('24.09','Graphic/Web Designer'),
('24.10','Release Manager'),
('24.11','DBA'),
('24.12','Network Administrator'),
('24.13','System Administrator'),
('24.14','System Security Administrator'),
('24.15','Technical Support Engineer'),
('24.16','Maintenance Engineer'),
('24.17','Webmaster'),
('24.18','IT/Networking-Manager'),
('24.19','Information Systems(MIS)-Manager'),
('24.20','System Integration Technician'),
('24.21','Business Analyst'),
('24.22','Datawarehousing Technician'),
('24.23','Outside Technical Consultant'),
('24.24','Functional Outside Consultant'),
('24.25','EDP Analyst'),
('24.26','Technical Writer'),
('24.27','Instructional Designer'),
('24.28','Technical Documentor'),
('24.29','Quality Assurance/Quality Control Executive'),
('24.30','Quality Assurance/Quality Control Manager'),
('24.31','Project Manager-IT/Software'),
('24.32','Program Manager'),
('24.33','Head/VP/GM-Quality'),
('24.34','Head/VP/GM-Technology(IT)/CTO'),
('24.35','CIO'),
('24.36','Trainer/Faculty'),
('24.37','Trainee'),
('24.38','Fresher'),
('24.39','Outside Consultant'),
('24.40','IT/Technical Content Developer'),


('37.01','Customer Support Engineer/Technician'),
('37.02','Technical Support Manager'),
('37.03','Head/VP/GM-Technical Support'),
('37.04','RF Engineer'),
('37.05','RF Installation Engineer'),
('37.06','RF System Designer'),
('37.07','GPRS Engineer'),
('37.08','GSM Engineer'),
('37.09','Embedded Technologies Engineer'),
('37.10','Switching/Router Engineer'),
('37.11','Mechanical Engineer -Telecom'),
('37.12','Civil Engineer -Telecom'),
('37.13','Electrical Engineer -Telecom'),
('37.14','Network Planning Engineer'),
('37.15','Network Planning Manager'),
('37.16','Security Engineer'),
('37.17','Maintenance Engineer'),
('37.18','Hardware Design Engineer'),
('37.19','Technical Lead -Hardware Design'),
('37.20','Hardware Installation Technician'),
('37.21','Quality Assurance/Quality Control Executive'),
('37.22','Quality Assurance/Quality Control Manager'),
('37.23','Network Administrator'),
('37.24','System Administrator'),
('37.25','Project Manager-Telecom'),
('37.27','Head/VP/GM-Operations'),
('37.28','Head/VP/GM-Quality'),
('37.29','CEO/MD/Director'),
('37.30','SBU Head/Profit Centre Head'),
('37.31','CTO/Head/VP-Technology (Telecom/ISP)'),
('37.32','CIO'),
('37.33','Outside Consultant'),
('37.35','Trainee'),
('37.36','Fresher'),

('13.01','Apprentice/Intern'),
('13.02','Private Attorney/Lawyer'),
('13.03','Advisor/Outside Consultant'),
('13.04','Law Officer'),
('13.05','Legal Manager'),
('13.06','Company Secretary'),
('13.07','Head/VP/GM-Legal'),
('13.08','Drug Regulatory Director'),
('13.09','Documentation/Medical Writing'),
('13.10','Regulatory Affairs Manager'),
('13.11','Head/VP/GM-Regulatory Affairs'),
('13.14','Fresher'),
('13.15','Trainee'),

('18.01','Scientist'),
('18.02','Packaging Development Executive/Manager'),
('18.03','Head/VP/GM-Packaging Development'),
('18.04','Fresher'),
('18.05','Trainee'),
('18.07','Outside Consultant'),

('16.01','Clinical Research Associate/Scientist'),
('16.02','Clinical Research Manager'),
('16.03','Analytical Chemistry Associate/Scientist'),
('16.04','Analytical Chemistry Manager'),
('16.05','Chemical Research Associate/Scientist'),
('16.06','Chemical Research Manager'),
('16.07','Bio/Pharma Informatics-Associate/Scientist'),
('16.08','Formulation Scientist'),
('16.09','Microbiologist'),
('16.10','Molecular Biology'),
('16.12','Nutritionist'),
('16.13','Research Scientist'),
('16.14','Bio-Technical Research Associate/Scientist'),
('16.15','Bio-Technical Research Manager'),
('16.16','Pharmacist/Chemist/Bio Chemist'),
('16.17','Bio-Statistician'),
('16.18','Chief Medical Officer/Head Medical Services'),
('16.19','Clinical Researcher'),
('16.20','Intern'),
('16.21','Administration Services/Medical Facilities'),
('16.22','Lab Technician/Medical Technician/Lab Staff'),
('16.23','Medical Officer'),
('16.24','Nurse'),
('16.25','Medical Superintendent/Director'),
('16.26','Anaesthetist'),
('16.27','Cardiologist'),
('16.28','Dermatologist'),
('16.29','Dietician/Nutritionist'),
('16.30','ENT Specialist'),
('16.31','General Practitioner'),
('16.32','Gynaeocologist'),
('16.33','Hepatologist'),
('16.34','Microbiologist'),
('16.35','Nephrologist'),
('16.36','Neurologist'),
('16.37','Oncologist'),
('16.38','Opthamologist'),
('16.39','Orthopaedist'),
('16.40','Paramedic'),
('16.41','Pathologist'),
('16.42','Pediatrician'),
('16.43','Pharmacist/Chemist/Bio Chemist'),
('16.44','Physiotherapist'),
('16.45','Psychiatrist'),
('16.46','Radiologist'),
('16.47','Surgeon'),
('16.48','Medical Representative'),
('16.49','Drug Regulatory Director'),
('16.50','Documentation/Medical Writing'),
('16.51','Regulatory Affairs Manager'),

('14.01','Store Keeper/Warehouse Assistant'),
('14.02','Warehouse Manager'),
('14.03','Carry Forward Agent (CFA)'),
('14.04','Logistics Executive'),
('14.05','Logistics Manager'),
('14.06','Transport/Distribution Manager'),
('14.07','Purchase Executive'),
('14.08','Purchase/Vendor Development Manager'),
('14.09','Material Management Executive/Manager'),
('14.10','Commercial Manager'),
('14.11','Quality Assurance/Quality Control Executive'),
('14.12','Quality Assurance/Quality Control Manager'),
('14.13','Commodity Trading Manager'),
('14.14','Head/VP/GM-SCM/Logistics'),
('14.15','Head/VP/GM-Commercial'),
('14.16','Head/VP/GM-Purchase/Material Management'),
('14.18','Trainee'),
('14.19','Fresher'),

('9.01','CEO/MD/Director'),
('9.02','Outside Consultant'),
('9.03','Director'),
('9.04','VP/President/Partner'),

('36.01','Counselor'),
('36.02','Lecturer/Professor'),
('36.03','Librarian'),
('36.04','Teacher/ Private Tutor'),
('36.05','Special Education Teacher'),
('36.06','Translator'),
('36.07','Transcriptionist'),
('36.09','Trainee'),
('36.10','Fresher'),

('44.01','Travel Agent'),
('44.02','Reservations Executive'),
('44.03','Reservations Manager'),
('44.04','Tour Management Executive'),
('44.05','Tour Management Manager/Senior Manager'),
('44.06','Operations Executive'),
('44.07','Business Development Manager'),
('44.08','Marketing Manager'),
('44.09','Branch Manager'),
('44.10','Regional Manager'),
('44.11','General Manager'),
('44.12','Cashier/Billing Manager'),
('44.13','Operations Manager'),
('44.14','Cabin Crew'),
('44.15','Ground Staff'),
('44.16','Aviation Engineer'),
('44.17','Maintenance Engineer'),
('44.18','SBU/Profit Center Head'),
('44.19','Head/VP/GM-Tour Management'),
('44.20','CEO/MD/Director'),
('44.22','Fresher'),
('44.23','Trainee'),
('44.24','Outside Consultant'),

('39.01','CEO/MD/Director'),
('39.02','CIO'),
('39.03','Creative Director'),
('39.04','National Creative Director/VP-Creative'),
('39.05','CTO/Head/VP-Technology (Telecom/ISP)'),
('39.06','Executive/Master Chef'),
('39.07','Head/VP/GM-Documentation/Shipping'),
('39.08','Head/VP/GM-Business Development'),
('39.09','Head/VP/GM-Relationships'),
('39.10','Head/VP/GM-Transitions'),
('39.11','Head/VP/GM-HR'),
('39.12','Head/VP/GM-Training and Development'),
('39.13','Head/VP/GM-Technology (IT)/CTO'),
('39.14','Head/Manager/GM-Media Buying'),
('39.15','Head/Manager/GM-Media Planning'),
('39.16','Head/VP/GM-Operations'),
('39.17','Head/VP/GM-SCM/Logistics'),
('39.18','Head/VP/GM-Administration & Facilities'),
('39.19','Head/VP/GM-Commercial'),
('39.20','Head/VP/GM-Marketing'),
('39.21','Head/VP/GM- Marketing Research'),
('39.22','Head/VP/GM- Purchase/Material Management'),
('39.23','Head/VP/GM -Accounts'),
('39.24','Head/VP/GM -F&B'),
('39.25','Head/VP/GM-Business Alliances'),
('39.26','Head/VP/GM-Finance/Audit'),
('39.27','Head/VP/GM-Investment Banking'),
('39.29','Head/VP/GM-Private Equity/Hedge Fund/VC'),
('39.30','Head/VP/GM-Project Finance'),
('39.31','Head/VP/GM-Quality Assurance & Quality Control'),
('39.32','Head/VP/GM-Quality'),
('39.33','Head/VP/GM-Sales'),
('39.34','Head/VP/GM-Underwriting'),
('39.36','Head/VP/GM-Fund Management'),
('39.37','Head/VP/GM -Credit Risk'),
('39.38','Head/VP/GM-Depository Services'),
('39.39','Head/VP/GM-Legal'),
('39.40','Head/VP/GM-Production/Manufacturing/Maintenance'),
('39.41','Head/VP/GM-Tour Management'),
('39.42','Head/VP/-Public Relations/Corporate Communication'),
('39.43','Head/VP/GM-Broking'),
('39.44','Head/VP/GM/CFO/Financial Controller'),
('39.45','Head/VP/GM-Credit'),
('39.46','Head/VP/GM-R&D'),
('39.47','Head/VP/GM-Regulatory Affairs'),
('39.48','Head/VP/GM-Claims'),
('39.49','Head/VP/GM-Client Servicing'),
('39.50','Head/VP/GM-Equity'),
('39.51','Head/VP/GM-Mergers & Acquisitions'),
('39.52','Head/VP/GM-Packaging Development'),
('39.53','Head/VP/GM-Corporate Planning/Strategy'),
('39.54','Head/VP/GM-Production'),
('39.55','Head/VP/GM-Treasury'),
('39.56','Head/VP/GM-Corporate Advisory'),
('39.57','Head/VP/GM-Domestic Debt'),
('39.58','Head/VP/GM-Formulations'),
('39.59','Head/VP/GM-Insurance Operations'),
('39.60','Head/VP/GM-Offshore Debt'),
('39.61','Head/VP/GM/National Manager-Sales'),
('39.62','SBU/Profit Center Head'),
('39.63','Service Delivery Leader'),
('39.64','VP/President/Partner'),
('39.65','Head/VP/GM-Recruitment'),

('43.01','News Anchor/TV Presenter'),
('43.02','News Compiler'),
('43.03','Correspondent'),
('43.04','Senior/Principal Correspondent'),
('43.05','News Editor'),
('43.06','News/Features Head'),
('43.07','Spot Boy'),
('43.08','Animation/Graphic Artist'),
('43.09','Stunt Coordinator'),
('43.10','Wardrobe/Make-Up/Hair Artist'),
('43.11','AV Editor'),
('43.12','Visualiser'),
('43.13','Sound Mixer/Engineer'),
('43.14','Locations Manager'),
('43.15','Lighting Technician'),
('43.16','Special Effects Technician'),
('43.17','Photographer'),
('43.18','Camera Man/Technician'),
('43.19','Choreographer'),
('43.20','Assistant Editor/Editor'),
('43.21','Head-Lighting'),
('43.22','Head-Special Effects'),
('43.23','Music Director'),
('43.24','Cinematographer'),
('43.25','Assistant Director/Director'),
('43.26','TV Producer'),
('43.27','Film Producer'),
('43.29','Fresher'),
('43.30','Trainee'),

('3.01','Art Director/Senior Art Director'),
('3.02','Visualiser'),
('3.03','Web Designer'),
('3.04','Copywriter'),
('3.05','Graphic Designer'),
('3.06','Creative Director'),
('3.07','National Creative Director/VP-Creative'),
('3.08','Commercial Artist'),
('3.10','Fresher'),
('3.11','Trainee'),

('41.01','Other');

insert into tp_ug_education_codes(course_id, course) values
('18','Any Graduate'),
('17','Graduation Not Required'),
('1','B.A'),
('2','B.Arch'),
('4','B.B.A'),
('5','B.Com'),
('6','B.Ed'),
('9','B.Pharma'),
('10','B.Sc'),
('11','B.Tech/B.E.'),
('3','BCA'),
('7','BDS'),
('8','BHM'),
('15','BVSC'),
('14','Diploma'),
('12','LLB'),
('13','MBBS'),
('16','Other Graduate');


insert into tp_ug_education_specialization(specialization_id, specialization, course_id) values
('114','Any Specialization','18'),
('109','None','17'),
('1','Arts&Humanities','1'),
('2','Communication','1'),
('3','Economics','1'),
('4','English','1'),
('5','Film','1'),
('6','Fine Arts','1'),
('7','Hindi','1'),
('8','History','1'),
('9','Journalism','1'),
('10','Maths','1'),
('11','Pass Course','1'),
('12','Political Science','1'),
('13','PR/Advertising','1'),
('14','Psychology','1'),
('15','Sanskrit','1'),
('16','Sociology','1'),
('17','Statistics','1'),
('18','Vocational Course','1'),
('19','Other Specialization','1'),
('110','Any Specialization','1'),
('20','Architecture','2'),
('21','Other','2'),
('24','Management','4'),
('25','Other','4'),
('26','Commerce','5'),
('27','Other','5'),
('28','Education','6'),
('29','Other','6'),
('34','Pharmacy','9'),
('35','Other','9'),
('36','Agriculture','10'),
('37','Anthropology','10'),
('38','Bio-Chemistry','10'),
('39','Biology','10'),
('40','Botany','10'),
('41','Chemistry','10'),
('42','Computers','10'),
('43','Dairy Technology','10'),
('44','Electronics','10'),
('45','Environmental science','10'),
('46','Food Technology','10'),
('47','Geology','10'),
('48','Home science','10'),
('49','Maths','10'),
('50','Microbiology','10'),
('51','Nursing','10'),
('52','Physics','10'),
('53','Statistics','10'),
('54','Zoology','10'),
('55','General','10'),
('56','Other Specialization','10'),
('111','Any Specialization','10'),
('57','Agriculture','11'),
('58','Automobile','11'),
('59','Aviation','11'),
('60','Bio-Chemistry/Bio-Technology','11'),
('61','Biomedical','11'),
('62','Ceramics','11'),
('63','Chemical','11'),
('64','Civil','11'),
('65','Computers','11'),
('66','Electrical','11'),
('67','Electronics/Telecomunication','11'),
('68','Energy','11'),
('69','Environmental','11'),
('70','Instrumentation','11'),
('71','Marine','11'),
('72','Mechanical','11'),
('73','Metallurgy','11'),
('74','Mineral','11'),
('75','Mining','11'),
('76','Nuclear','11'),
('77','Paint/Oil','11'),
('78','Petroleum','11'),
('79','Plastics','11'),
('80','Production/Industrial','11'),
('81','Textile','11'),
('82','Other Specialization','11'),
('83','Other','11'),
('112','Any Specialization','11'),
('22','Computers','3'),
('23','Other','3'),
('30','Dentistry','7'),
('31','Other','7'),
('32','Hotel Management','8'),
('33','Other','8'),
('106','Veterinary Science','15'),
('107','Other','15'),
('88','Architecture','14'),
('89','Chemical','14'),
('90','Civil','14'),
('91','Computers','14'),
('92','Electrical','14'),
('93','Electronics/Telecomunication','14'),
('94','Engineering','14'),
('95','Export/Import','14'),
('96','Fashion Designing/Other Designing','14'),
('97','Graphic/ Web Designing','14'),
('98','Hotel Management','14'),
('99','Insurance','14'),
('100','Management','14'),
('101','Mechanical','14'),
('102','Tourism','14'),
('103','Visual Arts','14'),
('104','Vocational Course','14'),
('105','Other Specialization','14'),
('113','Any Specialization','14'),
('84','Law','12'),
('85','Other','12'),
('86','Medicine','13'),
('87','Other','13'),
('108','Any Specialization','16');

insert into tp_pg_education_codes(course_id, course) values
('19','Any PG Course'),
('20','Post Graduation Not Required'),
('1','CA'),
('2','CS'),
('3','ICWA'),
('4','Integrated PG'),
('5','LLM'),
('6','M.A'),
('7','M.Arch'),
('8','M.Com'),
('9','M.Ed'),
('10','M.Pharma'),
('11','M.Sc'),
('12','M.Tech'),
('13','MBA/PGDM'),
('14','MCA'),
('15','M.S/M.D'),
('17','MVSC'),
('16','PG Diploma'),
('18','Other');

insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('130','Any Specialization','19');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('139','None','20');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('1','CA','1');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('3','CS','2');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('5','ICWA','3');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('7','Journalism / Mass Communication','4');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('8','Management','4');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('9','PR/ Advertising','4');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('10','Tourism','4');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('11','Other','4');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('132','Any Specialization','4');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('12','Law','5');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('14','Anthropology','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('15','Arts & Humanities','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('16','Communication','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('17','Economics','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('18','English','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('19','Film','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('20','Fine arts','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('21','Hindi','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('22','History','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('23','Journalism','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('24','Maths','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('25','Political Science','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('26','PR/ Advertising','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('27','Psychology','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('28','Sanskrit','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('29','Sociology','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('30','Statistics','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('31','Other','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('133','Any Specialization','6');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('32','Architecture','7');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('34','Commerce','8');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('36','Education','9');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('38','Pharmacy','10');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('40','Agriculture','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('41','Anthropology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('42','Bio-Chemistry','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('43','Biology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('44','Botany','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('45','Chemistry','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('46','Computers','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('47','Dairy Technology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('48','Electronics','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('49','Environmental science','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('50','Food Technology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('51','Geology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('52','Home science','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('53','Maths','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('54','Microbiology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('55','Nursing','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('56','Physics','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('57','Statistics','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('58','Zoology','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('59','Other','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('134','Any Specialization','11');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('60','Agriculture','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('61','Automobile','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('62','Aviation','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('63','Bio-Chemistry/Bio-Technology','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('64','Biomedical','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('65','Ceramics','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('66','Chemical','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('67','Civil','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('68','Computers','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('69','Electrical','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('70','Electronics/Telecomunication','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('71','Energy','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('72','Environmental','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('73','Instrumentation','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('74','Marine','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('75','Mechanical','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('76','Metallurgy','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('77','Mineral','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('78','Mining','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('79','Nuclear','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('80','Paint/Oil','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('81','Petroleum','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('82','Plastics','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('83','Production/Industrial','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('84','Textile','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('85','Other Engineering','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('135','Any Specialization','12');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('87','Advertising/Mass Communication','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('88','Finance','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('89','HR/Industrial Relations','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('90','Information Technology','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('91','International Business','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('92','Marketing','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('93','Systems','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('94','Other Management','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('136','Any Specialization','13');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('96','Computers','14');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('98','Cardiology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('99','Dermatology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('100','ENT','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('101','General Practitioner','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('102','Gyneocology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('103','Hepatology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('104','Immunology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('105','Microbiology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('106','Neonatal','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('107','Nephrology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('108','Urology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('109','Obstretrics','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('110','Oncology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('111','Opthalmology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('112','Orthopaedic','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('113','Pathology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('114','Pediatrics','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('115','Psychiatry','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('116','psychology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('117','Radiology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('118','Rheumatology','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('119','Other','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('137','Any Specialization','15');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('127','Veterinary Science','17');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('120','Chemical','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('121','Civil','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('122','Computers','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('123','Electrical','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('124','Electronics','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('125','Mechanical','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('126','Other','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('138','Any Specialization','16');
insert into tp_pg_education_specialization (specialization_id,specialization,course_id) values('129','Other','18');

insert into tp_naukri_experience (min_exp,max_allowed_exp) values('0','5');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('1','6');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('2','7');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('3','8');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('4','9');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('5','10');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('6','11');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('7','12');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('8','13');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('9','14');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('10','20');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('11','21');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('12','22');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('13','23');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('14','24');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('15','25');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('16','26');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('17','27');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('18','28');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('19','29');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('20','30');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('21','31');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('22','32');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('23','33');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('24','34');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('25','35');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('26','36');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('27','37');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('28','38');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('29','39');
insert into tp_naukri_experience (min_exp,max_allowed_exp) values('30','30');


-- 3dplm customization

INSERT INTO tp_application_properties (application_property, application_property_value) 
VALUES ('all_employees_hrgroup_email_id', '');

-- 3dplm customization


-- sbi customization 

INSERT INTO tp_cr_report_types (cr_report_type_id,cr_report_type_name,cr_report_type_kind_id) 
	VALUES(4,'Candidate Detail Report','3');
	

INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C03');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C04');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C05');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C06');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C07');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C08');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C14');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C15');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C16');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C18');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C19');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C20');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C21');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C22');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C25');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C26');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C27');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C28');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C29');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C30');   
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C31');

INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P01');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P02');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P05');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P12');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P13');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P17');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P18');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P21');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P23');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P24');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P26');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'P30');


INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF1','Custom Field 1()','1','','custom_field_1','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF2','Custom Field 2()','1','','custom_field_2','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF3','Custom Field 3()','1','','custom_field_3','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF4','Custom Field 4()','1','','custom_field_4','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF5','Custom Field 5()','1','','custom_field_5','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF6','Custom Field 6()','1','','custom_field_6','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF7','Custom Field 7()','1','','custom_field_7','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF8','Custom Field 8()','1','','custom_field_8','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF9','Custom Field 9()','1','','custom_field_9','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF10','Custom Field 10()','1','','custom_field_10','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF11','Custom Field 11()','1','','custom_field_11','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF12','Custom Field 12()','1','','custom_field_12','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF13','Custom Field 13()','1','','custom_field_13','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF14','Custom Field 14()','1','','custom_field_14','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF15','Custom Field 15()','1','','custom_field_15','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF16','Custom Field 16()','1','','custom_field_16','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF17','Custom Field 17()','1','','custom_field_17','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF18','Custom Field 18()','1','','custom_field_18','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF19','Custom Field 19()','1','','custom_field_19','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF20','Custom Field 20()','1','','custom_field_20','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF21','Custom Field 21()','1','','custom_field_21','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF22','Custom Field 22()','1','','custom_field_22','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF23','Custom Field 23()','1','','custom_field_23','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF24','Custom Field 24()','1','','custom_field_24','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF25','Custom Field 25()','1','','custom_field_25','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF26','Custom Field 26()','1','','custom_field_26','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF27','Custom Field 27()','1','','custom_field_27','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF28','Custom Field 28()','1','','custom_field_28','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF29','Custom Field 29()','1','','custom_field_29','100',NULL,NULL,'1','0','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES('CCF30','Custom Field 30()','1','','custom_field_30','100',NULL,NULL,'1','0','1','1');

INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C33','Offered Ctc','1','java.lang.String','offered_ctc','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C34','Level Offered','1','java.lang.String','level_offered','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C35','Designation Offered','1','java.lang.String','designation_offered','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C36','Offer Code','1','java.lang.String','offer_code','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C37','Offer Date','1','java.util.Date','offer_date','75',NULL,'C37','0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) 
	VALUES ('C38','Age','1','java.lang.String','concat((YEAR(now()) - YEAR(${C42}) - (DATE_FORMAT(now(),"%m%d") <  DATE_FORMAT(${C42},"%m%d"))), "yrs")','75',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C39','Degree 2','1','java.lang.String','degrees','100',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C40','Last Employer','1','java.lang.String','employer2','100',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C41','2nd Last Employer','1','java.lang.String','employer3','100',NULL,NULL,'0','1','1','1');
INSERT INTO tp_custom_report_columns (column_property, column_display_name, column_category, column_data_type, column_dbname, column_width, group_by_column, order_by_column, is_custom_field, is_active, table_type, value_type) VALUES ('C42','DoB-String','1','java.lang.String','date_of_birth','75',NULL,NULL,'0','0','1','1');

UPDATE tp_custom_report_columns SET column_data_type='java.util.Date' WHERE column_property='C14';

INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C33');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C34');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C35');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C36');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C37');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C38');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C39');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C40');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'C41');


INSERT INTO tp_cr_filters(filter_name) VALUES('Process User filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Activity Date filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Offer Date filter');
INSERT INTO tp_cr_filters(filter_name) VALUES('Joining Date filter');

INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('C18',16);
INSERT INTO tp_cr_column_filter_map(column_property,filter_id) VALUES('C37',15);


INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF1');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF2');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF3');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF4');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF5');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF6');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF7');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF8');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF9');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF10');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF11');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF12');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF13');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF14');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF15');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF16');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF17');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF18');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF19');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF20');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF21');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF22');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF23');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF24');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF25');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF26');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF27');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF28');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF29');
INSERT INTO tp_cr_report_type_column_mapping (cr_report_type_id,column_property) VALUES (4,'CCF30');


INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF1', 'Custom Field 1', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF2', 'Custom Field 2', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF3', 'Custom Field 3', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF4', 'Custom Field 4', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF5', 'Custom Field 5', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF6', 'Custom Field 6', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF7', 'Custom Field 7', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF8', 'Custom Field 8', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF9', 'Custom Field 9', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF10', 'Custom Field 10', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF11', 'Custom Field 11', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF12', 'Custom Field 12', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF13', 'Custom Field 13', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF14', 'Custom Field 14', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF15', 'Custom Field 15', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF16', 'Custom Field 16', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF17', 'Custom Field 17', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF18', 'Custom Field 18', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF19', 'Custom Field 19', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF20', 'Custom Field 20', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF21', 'Custom Field 21', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF22', 'Custom Field 22', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF23', 'Custom Field 23', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF24', 'Custom Field 24', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF25', 'Custom Field 25', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF26', 'Custom Field 26', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF27', 'Custom Field 27', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF28', 'Custom Field 28', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF29', 'Custom Field 29', '1');
INSERT INTO tp_cr_column_customfield_map (column_property, column_display_name, entity_type) VALUES ('CCF30', 'Custom Field 30', '1');

UPDATE tp_applicants ta, tp_custom_field_values_applicant tcfva, tp_custom_fields tcf
SET ta.date_of_birth = tcfva.date_value 
WHERE ta.applicant_id = tcfva.entity_id 
	AND tcfva.custom_field_id = tcf.custom_field_id 
	AND tcf.custom_field_display_name = 'Date.Of.Birth';
	
	
UPDATE tp_custom_report_columns SET column_data_type='java.util.Date' WHERE column_property='C14';	

INSERT INTO tp_applicant_status_messages (applicant_id, position_step_id, status_message, user_id, date_created)
SELECT applicant_id, (SELECT position_step_id FROM tp_position_steps tps 
		WHERE tajh.position_id = tps.position_id ORDER BY position_step_rank DESC LIMIT 0,1) AS position_step_id, 
	'Joined', 1, now() 
FROM tp_applicant_joining_history tajh;

-- CALL PROC_OFFER_DATE_MIGRATION();
-- sbi customization 

-- aspire customization
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'enable_hr_manager_cc_while_sending_email','1');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'keep_HRmanager_cc_for_all_position_approval','1');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'send_mail_to_recruiter_for_rejected_candidate','1');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'send_mail_to_recruiter_for_On_Hold_candidate','1');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'send_mail_to_recruiter_for_candidate_status','1');
insert into `tp_template_types`(`template_type_id`,`template_type`) values ( '56','Candidate On Hold Email To Recruiter');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '56','2');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '56','3');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '56','14');
insert into `tp_templates`(`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`template_is_save_as_draft`,`do_show_save_as_draft_option`,`user_id`,`is_repeat`) values ( '125','onHoldEmailToRecruiter','Candidate On Hold Email To Recruiter','onHoldEmailToRecruiterSubject.vm','onHoldEmailToRecruiterContent.vm','0','0','0','56','0','2015-09-01 12:55:32','0','0','1','0');

insert into `tp_template_types`(`template_type_id`,`template_type`) values ( '57','Candidate Progress Notification To Recruiter');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '57','1');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '57','3');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '57','4');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '57','11');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '57','14');

insert into `tp_templates`(`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`template_is_save_as_draft`,`do_show_save_as_draft_option`,`user_id`,`is_repeat`) values ( '126','candidateProgressToRecruiter','Candidate Progress Notification To Recruiter','candidateProgreeNotificationToRecruiterSubject.vm','candidateProgreeNotificationToRecruiterContent.vm','0','0','0','57','0','2015-09-02 12:15:32','0','0','1','0');
insert into `tp_template_types`(`template_type_id`,`template_type`) values ( '58','Requisition Approval Progress Track Email');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '58','1');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '58','2');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '58','13');

insert into `tp_templates`(`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`template_is_save_as_draft`,`do_show_save_as_draft_option`,`user_id`,`is_repeat`) values ( '127','requisitionApprovalProgressTrack','Requisition Approval Progress Track Email','requisitionApprovalProgressSubject.vm','requisitionApprovalProgressContent.vm','0','0','0','58','0','2015-09-02 12:15:35','0','0','1','0');
insert into `tp_templates`(`template_id`,`template_code`,`template_name`,`template_subject_file`,`template_content_file`,`template_auto`,`template_format`,`template_private`,`template_type_id`,`template_is_default`,`template_date_created`,`template_is_save_as_draft`,`do_show_save_as_draft_option`,`user_id`,`is_repeat`) values ( '128','requisitionProgressNotificationTorequestedBy','requisitionProgressNotificationTorequestedBy','requisitionApprovalNotificationToRequestedBySubject.vm','requisitionApprovalNotificationToRequestedByContent.vm','0','0','0','59','1','2015-09-11 11:10:15','0','0','1','0');

insert into `tp_template_types`(`template_type_id`,`template_type`) values ( '59','Requisition approval progress notification email to requestedBy');

insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '59','1');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '59','2');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '59','13');
insert into `tp_template_vars`(`template_type_id`,`template_variable_type`) values ( '59','18');
-- aspire customization

-- Master reports not visible
INSERT INTO tp_report_levels (level_id, report_id, report_type) VALUES ('1','19','0');

-- for migrating doc file path entries from old table to new
insert into tp_applicant_applied_position_resume_mapping (applicant_id,position_id,applicant_original_resume_path,applicant_original_doc_path,applied_status) 
select applicant_id,applicant_position_id,applicant_original_resume_path,applicant_original_doc_path,case when isnull(applicant_step_id) then 1 else 0 end 
from tp_applicants;

-- for updating positionids from applicants to text resume mapping table for old entries
update tp_applicant_text_resumes tat
inner join (select t.applicant_id from (select applicant_id , count(applicant_text_resume) as cnt from tp_applicant_text_resumes tat1 group by applicant_id) as t where t.cnt<2) as tat1
on tat.applicant_id = tat1.applicant_id
set tat.applied_position_id =  (select applicant_position_id from tp_applicants where applicant_id = tat.applicant_id);
-- sbi related
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'show_reason_for_reject','1');
insert into `tp_screen_configurations`(`field_id`,`field_type`,`is_process_field`,`field_import_show`,`field_edit_show`,`field_import_mandatory`,`field_vendor_show`,`field_vendor_mandatory`,`field_employee_show`,`field_employee_mandatory`,`field_applicant_show_on_site`,`field_applicant_mandatory_on_site`,`field_confidential`)values('sub_category','0','0','1','1','0','0','0','1','0','1','0','0');
insert into `tp_screen_configurations`(`field_id`,`field_type`,`is_process_field`,`field_import_show`,`field_edit_show`,`field_import_mandatory`,`field_vendor_show`,`field_vendor_mandatory`,`field_employee_show`,`field_employee_mandatory`,`field_applicant_show_on_site`,`field_applicant_mandatory_on_site`,`field_confidential`)values('category','0','0','1','1','0','0','0','1','0','1','0','0');
insert into `tp_application_properties`(`application_property`,`application_property_value`) values ( 'send_mail_to_friend','1');
delete from `tp_role_permissions` where `role_id`='3' and `permission_id`='7';
insert into `tp_role_permissions`(`role_id`,`permission_id`) values ( '2','8');
insert into `tp_role_permissions`(`role_id`,`permission_id`) values ( '3','8');
insert into `tp_role_permissions`(`role_id`,`permission_id`) values ( '4','8');
insert into `tp_role_permissions`(`role_id`,`permission_id`) values ( '5','8');
insert into `tp_role_permissions`(`role_id`,`permission_id`) values ( '8','8');

-- permissions for inprocess candidates role access

DELETE FROM tp_permissions where permission_id=100;
INSERT IGNORE INTO tp_permissions (permission_id, permission_rank, permission_desc, parent_id, is_permission) VALUES (100, 96, 'In process Candidates', 0, 1);
INSERT IGNORE  INTO tp_permissions (permission_id, permission_rank, permission_desc, parent_id, is_permission) VALUES (101, 97, 'Show all candidates', 100, 1);
SET FOREIGN_KEY_CHECKS=1;