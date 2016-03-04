insert into Enum_Waterfront (LegacyName, id) Values('BeachFront',0);
insert into Enum_Waterfront (LegacyName, id) Values( 'OceanFront', 1);
insert into Enum_Waterfront (LegacyName, id) Values( 'None', 2); 
insert into Enum_Waterfront (LegacyName, id) Values( 'Across Street from Ocean', 3);
--select * from Enum_Waterfront

--alter table Enum_PropertyClass add LegacyName varchar(50));
insert into Enum_PropertyClass (LegacyName, id) Values( 'Residential' , 0);
insert into Enum_PropertyClass (LegacyName, id) Values( 'Vacant Land' , 1);
insert into Enum_PropertyClass (LegacyName, id) Values( 'Commercial' , 2);
insert into Enum_PropertyClass (LegacyName, id) Values( 'Business' , 3);
insert into Enum_PropertyClass (LegacyName, id) Values( 'Condominium' , 4);
insert into Enum_PropertyClass (LegacyName, id) Values( 'Fraction-Partial-Interval' , 5);
insert into Enum_PropertyClass (LegacyName, id) Values( 'Multi-Dwelling Res' , 6);
--select * from Enum_PropertyClass);

--alter table Enum_PropertyType add LegacyName varchar(50));
insert into Enum_PropertyType (LegacyName, id) Values( 'Business' , 0);
insert into Enum_PropertyType (LegacyName, id) Values( 'Commercial-For Sale' , 1);
insert into Enum_PropertyType (LegacyName, id) Values( 'Commercial-Lease Unit' , 2);
insert into Enum_PropertyType (LegacyName, id) Values( 'Commercial Lease Land' , 3);
insert into Enum_PropertyType (LegacyName, id) Values( 'Condo' , 4);
insert into Enum_PropertyType (LegacyName, id) Values( 'Co Op' , 5);
insert into Enum_PropertyType (LegacyName, id) Values( 'PUD' , 6);
insert into Enum_PropertyType (LegacyName, id) Values( 'Time Interval' , 7);
insert into Enum_PropertyType (LegacyName, id) Values( 'Fractional' , 8);
insert into Enum_PropertyType (LegacyName, id) Values( 'Partial Interest' , 9);
insert into Enum_PropertyType (LegacyName, id) Values( 'Multi Dwelling' , 10);
insert into Enum_PropertyType (LegacyName, id) Values( 'Single Family' , 11);
insert into Enum_PropertyType (LegacyName, id) Values( 'Single Family w/Att Ohana' , 12);
insert into Enum_PropertyType (LegacyName, id) Values( 'SF w/Det Ohana or Cottage' , 13);
insert into Enum_PropertyType (LegacyName, id) Values( 'Vacant Land' , 14);
--select * from Enum_PropertyType);

--alter table Enum_View add LegacyName varchar(50));
insert into Enum_View (LegacyName, id) Values( 'Mountain/Ocean' , 0);
insert into Enum_View (LegacyName, id) Values( 'Ocean' , 1);
insert into Enum_View (LegacyName, id) Values( 'Mountain' , 2);
insert into Enum_View (LegacyName, id) Values( 'Golf Course' , 3);
insert into Enum_View (LegacyName, id) Values( 'Garden View' , 4);
insert into Enum_View (LegacyName, id) Values( 'Other' , 5);
--select * from Enum_View);

--alter table Enum_LandTenure add LegacyName varchar(50));
insert into Enum_LandTenure (LegacyName, id) Values( 'Fee Simple' , 0);
insert into Enum_LandTenure (LegacyName, id) Values( 'Leasehold' , 1);
insert into Enum_LandTenure (LegacyName, id) Values( 'Leasehold-FA' , 2);
--SELECT * FROM Enum_LandTenure);

--alter table Enum_StatusCategory add LegacyName varchar(50));
insert into Enum_StatusCategory (LegacyName, id) Values( 'ACTIVE' , 0);
insert into Enum_StatusCategory (LegacyName, id) Values( 'SOLD' , 1);
insert into Enum_StatusCategory (LegacyName, id) Values( 'PENDING' , 2);
insert into Enum_StatusCategory (LegacyName, id) Values( 'EXPIRED' , 3);
insert into Enum_StatusCategory (LegacyName, id) Values( 'WITHDRAWN' , 4);
--SELECT * FROM Enum_StatusCategory);

--alter table Enum_District add LegacyName varchar(50));
insert into Enum_District (LegacyName, id) Values( 'Haiku' , 0);
insert into Enum_District (LegacyName, id) Values( 'Hana' , 1);
insert into Enum_District (LegacyName, id) Values( 'Honokohau' , 2);
insert into Enum_District (LegacyName, id) Values( 'Kaanapali' , 3);
insert into Enum_District (LegacyName, id) Values( 'Kahakuloa' , 4);
insert into Enum_District (LegacyName, id) Values( 'Kahului' , 5);
insert into Enum_District (LegacyName, id) Values( 'Kapalua' , 6);
insert into Enum_District (LegacyName, id) Values( 'Kaupo' , 7);
insert into Enum_District (LegacyName, id) Values( 'Keanae' , 8);
insert into Enum_District (LegacyName, id) Values( 'Kihei' , 9);
insert into Enum_District (LegacyName, id) Values( 'Kipahulu' , 10);
insert into Enum_District (LegacyName, id) Values( 'Kula/Ulupalakua/Kanaio' , 11);
insert into Enum_District (LegacyName, id) Values( 'Lahaina' , 12);
insert into Enum_District (LegacyName, id) Values( 'Lanai' , 13);
insert into Enum_District (LegacyName, id) Values( 'Maalaea' , 14);
insert into Enum_District (LegacyName, id) Values( 'Makawao/Olinda/Haliimaile' , 15);
insert into Enum_District (LegacyName, id) Values( 'Maui Meadows' , 16);
insert into Enum_District (LegacyName, id) Values( 'Molokai' , 17);
insert into Enum_District (LegacyName, id) Values( 'Nahiku' , 18);
insert into Enum_District (LegacyName, id) Values( 'Napili/Kahana/Honokowai' , 19);
insert into Enum_District (LegacyName, id) Values( 'Olowalu' , 20);
insert into Enum_District (LegacyName, id) Values( 'Pukalani' , 21);
insert into Enum_District (LegacyName, id) Values( 'Spreckelsville/Paia/Kuau' , 22);
insert into Enum_District (LegacyName, id) Values( 'Wailea/Makena' , 23);
insert into Enum_District (LegacyName, id) Values( 'Wailuku' , 24);
--SELECT * FROM  Enum_District

--alter table Enum_Status add LegacyName varchar(50));
insert into Enum_Status (LegacyName, id) Values( 'ACTIVE' , 0);
insert into Enum_Status (LegacyName, id) Values( 'CNTNGNTWRELEASE' , 1);
insert into Enum_Status (LegacyName, id) Values( 'CNTNGNT ESCROW CANCELING' , 2);
insert into Enum_Status (LegacyName, id) Values( 'SOLD' , 3);
insert into Enum_Status (LegacyName, id) Values( 'PENDINGDONOTSHOW' , 4);
insert into Enum_Status (LegacyName, id) Values( 'PENDING - CONT. TO SHOW' , 5);
insert into Enum_Status (LegacyName, id) Values( 'EXPIRED' , 6);
insert into Enum_Status (LegacyName, id) Values( 'WITHDRAWN' , 7);
insert into Enum_Status (LegacyName, id) Values( 'TEMPOFFMARKET' , 8);
insert into Enum_Status (LegacyName, id) Values( 'CANCELED' , 9);
--SELECT * from  Enum_Status);

select distinct waterFront from property