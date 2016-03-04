
--
--alter table Enum_Waterfront add LegacyName varchar(50);
--if exist (select * where id = 0)
--	update Enum_Waterfront set LegacyName = 'BeachFront' where id=0;
--else 
--	insert into Enum_Waterfront Values('id', 'LegacyName') LegacyName = 'BeachFront' where id=0;
alter table Enum_Waterfront add LegacyName varchar(50);
update Enum_Waterfront set LegacyName = 'BeachFront' where id=0;
update Enum_Waterfront set LegacyName = 'OceanFront' where id=1;
update Enum_Waterfront set LegacyName = 'None' where id=2; 
update Enum_Waterfront set LegacyName = 'Across Street from Ocean' where id=3;
--select * from Enum_Waterfront

alter table Enum_PropertyClass add LegacyName varchar(50);
update Enum_PropertyClass set LegacyName = 'Residential' where id = 0;
update Enum_PropertyClass set LegacyName = 'Vacant Land' where id = 1;
update Enum_PropertyClass set LegacyName = 'Commercial' where id = 2;
update Enum_PropertyClass set LegacyName = 'Business' where id = 3;
update Enum_PropertyClass set LegacyName = 'Condominium' where id = 4;
update Enum_PropertyClass set LegacyName = 'Fraction-Partial-Interval' where id = 5;
update Enum_PropertyClass set LegacyName = 'Multi-Dwelling Res' where id = 6;
--select * from Enum_PropertyClass;

alter table Enum_PropertyType add LegacyName varchar(50);
update Enum_PropertyType set LegacyName = 'Business' where id = 0;
update Enum_PropertyType set LegacyName = 'Commercial-For Sale' where id = 1;
update Enum_PropertyType set LegacyName = 'Commercial-Lease Unit' where id = 2;
update Enum_PropertyType set LegacyName = 'Commercial Lease Land' where id = 3;
update Enum_PropertyType set LegacyName = 'Condo' where id = 4;
update Enum_PropertyType set LegacyName = 'Co Op' where id = 5;
update Enum_PropertyType set LegacyName = 'PUD' where id = 6;
update Enum_PropertyType set LegacyName = 'Time Interval' where id = 7;
update Enum_PropertyType set LegacyName = 'Fractional' where id = 8;
update Enum_PropertyType set LegacyName = 'Partial Interest' where id = 9;
update Enum_PropertyType set LegacyName = 'Multi Dwelling' where id = 10;
update Enum_PropertyType set LegacyName = 'Single Family' where id = 11;
update Enum_PropertyType set LegacyName = 'Single Family w/Att Ohana' where id = 12;
update Enum_PropertyType set LegacyName = 'SF w/Det Ohana or Cottage' where id = 13;
update Enum_PropertyType set LegacyName = 'Vacant Land' where id = 14;
--select * from Enum_PropertyType;

alter table Enum_View add LegacyName varchar(50);
update Enum_View set LegacyName = 'Mountain/Ocean' where id = 0;
update Enum_View set LegacyName = 'Ocean' where id = 1;
update Enum_View set LegacyName = 'Mountain' where id = 2;
update Enum_View set LegacyName = 'Golf Course' where id = 3;
update Enum_View set LegacyName = 'Garden View' where id = 4;
update Enum_View set LegacyName = 'Other' where id = 5;
--select * from Enum_View;

alter table Enum_LandTenure add LegacyName varchar(50);
update Enum_LandTenure set LegacyName = 'Fee Simple' where id = 0;
update Enum_LandTenure set LegacyName = 'Leasehold' where id = 1;
update Enum_LandTenure set LegacyName = 'Leasehold-FA' where id = 2;
--SELECT * FROM Enum_LandTenure;

alter table Enum_StatusCategory add LegacyName varchar(50);
update Enum_StatusCategory set LegacyName = 'ACTIVE' where id = 0;
update Enum_StatusCategory set LegacyName = 'SOLD' where id = 1;
update Enum_StatusCategory set LegacyName = 'PENDING' where id = 2;
update Enum_StatusCategory set LegacyName = 'EXPIRED' where id = 3;
update Enum_StatusCategory set LegacyName = 'WITHDRAWN' where id = 4;
--SELECT * FROM Enum_StatusCategory;

alter table Enum_District add LegacyName varchar(50);
update Enum_District set LegacyName = 'Haiku' where id = 0;
update Enum_District set LegacyName = 'Hana' where id = 1;
update Enum_District set LegacyName = 'Honokohau' where id = 2;
update Enum_District set LegacyName = 'Kaanapali' where id = 3;
update Enum_District set LegacyName = 'Kahakuloa' where id = 4;
update Enum_District set LegacyName = 'Kahului' where id = 5;
update Enum_District set LegacyName = 'Kapalua' where id = 6;
update Enum_District set LegacyName = 'Kaupo' where id = 7;
update Enum_District set LegacyName = 'Keanae' where id = 8;
update Enum_District set LegacyName = 'Kihei' where id = 9;
update Enum_District set LegacyName = 'Kipahulu' where id = 10;
update Enum_District set LegacyName = 'Kula/Ulupalakua/Kanaio' where id = 11;
update Enum_District set LegacyName = 'Lahaina' where id = 12;
update Enum_District set LegacyName = 'Lanai' where id = 13;
update Enum_District set LegacyName = 'Maalaea' where id = 14;
update Enum_District set LegacyName = 'Makawao/Olinda/Haliimaile' where id = 15;
update Enum_District set LegacyName = 'Maui Meadows' where id = 16;
update Enum_District set LegacyName = 'Molokai' where id = 17;
update Enum_District set LegacyName = 'Nahiku' where id = 18;
update Enum_District set LegacyName = 'Napili/Kahana/Honokowai' where id = 19;
update Enum_District set LegacyName = 'Olowalu' where id = 20;
update Enum_District set LegacyName = 'Pukalani' where id = 21;
update Enum_District set LegacyName = 'Spreckelsville/Paia/Kuau' where id = 22;
update Enum_District set LegacyName = 'Wailea/Makena' where id = 23;
update Enum_District set LegacyName = 'Wailuku' where id = 24;
--SELECT * FROM  Enum_District

alter table Enum_Status add LegacyName varchar(50);
update Enum_Status set LegacyName = 'ACTIVE' where id = 0;
update Enum_Status set LegacyName = 'CNTNGNTWRELEASE' where id = 1;
update Enum_Status set LegacyName = 'CNTNGNT ESCROW CANCELING' where id = 2;
update Enum_Status set LegacyName = 'SOLD' where id = 3;
update Enum_Status set LegacyName = 'PENDINGDONOTSHOW' where id = 4;
update Enum_Status set LegacyName = 'PENDING - CONT. TO SHOW' where id = 5;
update Enum_Status set LegacyName = 'EXPIRED' where id = 6;
update Enum_Status set LegacyName = 'WITHDRAWN' where id = 7;
update Enum_Status set LegacyName = 'TEMPOFFMARKET' where id = 8;
update Enum_Status set LegacyName = 'CANCELED' where id = 9;
--SELECT * from  Enum_Status;

select distinct waterFront from property