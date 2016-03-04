select 
a.UserCode as agent1_id,
ca.UserCode as agent2_id,
o.MainOfficeAbbreviation as office1_id,
co.MainOfficeAbbreviation as office2_id,
MLSNumber as L_ListingID,
Class as L_Class,
District as L_Area,
-- CAST(StartDate AS date) as OH_StartDate,
--CAST(CAST(StartDate AS date) as varchar(10)) as OH_StartDate,
--CAST(CAST(StartTime AS time)as varchar(10)) as OH_StartTime, 
--CAST(CAST(EndTime AS date) as varchar(10)) as OH_EndTime,
--2015-02-09 14:00:00 -10:00
CONVERT(VARCHAR(20), StartDate, 105) as  OH_StartDate,
CONVERT(VARCHAR(20), StartTime, 108) as  OH_StartTime,
CONVERT(VARCHAR(20), EndTime, 108) as  OH_StartTime,
concat('http://www.ramidx.com/MLSImages/363000/200/maui',MLSNumber,'-1.jpg')image,
Type as L_Type_,
L_Satus =
CASE Status
 WHEN 0 THEN 'Active'
 WHEN 1 THEN 'Sold'
 WHEN 2 THEN 'Penging'
 WHEN 3 THEN 'Expired'
 WHEN 4 THEN 'Withdrawn'
 ELSE 'N/A'
END,
-- as L_Status, -- Status, StatusCategory,StatusDetail
AddressNumber as L_AddressNumber,
AddressDirection as L_AddressDirection,
AddressStreet as L_AddressStreet,
City as L_City,
State as L_State,
ZipCode5 as L_Zip,
comments as OH_Comments
from OpenHouse oh
left join Agent as a on oh.agent = a.Identifier
left join Agent as ca on oh.CoListingAgent = ca.Identifier
left join Office as o on oh.ListingOffice1 = o.Identifier 
left join Office as co on oh.CoListingOffice = co.Identifier
--WHERE o.MainOfficeAbbreviation in (9986)
where  mlsnumber = 363260
-- select * from openhouse where  mlsnumber in (363697,358876)
-- select * from openhouse where  ListingOffice1 in (1623)

--where oh.MLSNumber = '363697' -- ok
-- where oh.MLSNumber = '363857' 
--CoListingAgent
-- CoListingOffice
-- where MLSNumber = '363857' and oh.agent = a.Identifier;
select * from office WHERE MainOfficeAbbreviation in (8770)
select top 10 * from openhouse;
select * from openhouse where MLSNumber = '363857';
select * from openhouse where comments = 'Hosted By: Chris Marzoeki';
select * from agent where UserCode = '15357'
select * from agent where UserCode = '18623'
select * from agent where Identifier = '5768' -- yes user code 20185 
select top 10 * from agent
select * from agent where lastname = 'Marzoeki'
select * from agent where officeId = '8399'
select * from property where MLSNumber = 363857

select * from agent where UserCode = '7703' -- no
select * from agent where Identifier = '7703' -- yes user code 20185 
select * from openhouse where MLSNumber = '363697'
select * from office where Identifier ='1623'