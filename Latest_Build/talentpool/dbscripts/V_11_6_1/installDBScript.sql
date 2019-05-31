USE talentpool;

ALTER TABLE tp_applicants add column applicant_hrms_code varchar (50) NULL COLLATE latin1_swedish_ci  after employee_code;
