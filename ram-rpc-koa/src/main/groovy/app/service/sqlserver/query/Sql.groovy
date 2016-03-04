package app.service.sqlserver.query

class Sql {
static final SEARCH =
'''
SELECT
MLSNumber AS MLS,
EPC.Name AS Class,
EPT.Name AS Type,
EDI.Name AS District,
CAST(ListPrice AS MONEY) AS ListPrice,
AddressNumber,
AddressDirection,
AddressStreet,
AddressLotUnit,
City,
State,
ZipCode5 AS Zip,
EST.Name AS Status,
EWF.Name as WaterFront,
EVI.Name as [View],
LA1AgentUserCode AS AgentID, 
CONCAT(LA1AgentLastName, ',', LA1AgentFirstName) AS AgentName,
LA1AgentPhone1Number AS AgentPhone,
LO1OfficeAbbreviation AS ListingOffice1_ID,
LO1OfficeName AS ListingOffice1_Name,
LO1OfficePhone1Number AS ListingOffice1_Phone,
LA2UserCode AS CoListingAgent_ID,
--LA2AgentFullName AS CoListingAgent_Name,
CONCAT(LA2AgentLastName, ',', LA2AgentFirstName) AS CoListingAgent_Name,
LA2AgentPhone1Number AS CoListingAgent_Phone,
LO2OfficeAbbreviation AS ListingOffice2_ID,
LO2OfficName AS ListingOffice2_Name,-- misspelled
LO2OfficePhone1Number AS ListingOffice2_Phone,
CONVERT(VARCHAR(10), ListDate,  120) AS ListDate,
VideoVirtualTour as VirtualTour,
Div,
Zone,
Sec,
--CAST(CAST(Plat AS FLOAT) AS VARCHAR(10)) AS Plat,
--CAST(CAST(Par AS FLOAT) AS VARCHAR(10)) AS Par,
Plat,
Par,
Unit,
CPR,
ISNULL(CONVERT(VARCHAR(10),Carport),'0') AS HouseCarport,
ISNULL(CONVERT(VARCHAR(10),Garage),'0') AS HouseGarage,
ISNULL(CONVERT(VARCHAR(10),OhanaCarport),'0') AS OhanaCarport,
ISNULL(CONVERT(VARCHAR(10),OhanaGarage),'0') AS OhanaGarage,
ELT.Name AS LT,
CAST(PartialOwnership AS VARCHAR(10)) AS PartialOwnership,
CAST(PercentOwnership AS VARCHAR(10)) AS PercentOwnership,
BuildingName,
ISNULL(CONVERT(VARCHAR(10),FloorLevel),'0') AS FloorLevel,
ISNULL(CONVERT(VARCHAR(10),Beds),'0') AS Beds,
ISNULL(CONVERT(VARCHAR(10),Baths),'0') AS Baths,
CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
CAST(LandSQFT AS FLOAT) AS LandSQFT,
LandAcres,
-- SELECT ISNULL(NULLIF(MonthlyMaintFee,''),'-')
--CONVERT(VARCHAR(10),MonthlyMaintFee) AS MaintFee,
CONVERT(VARCHAR(10),null) AS MaintFee,
PublicRemarks,
OhanaBeds AS Ohana_Beds,
OhanaBaths AS Ohana_Baths,
CAST(ISNULL(OhanaSqFt,0) AS FLOAT) AS Ohana_LivSQFT,
FixedBeginDate,
FixedEndDate,
ResidentialCondo AS Res_Condo,
VOWAddress =
CASE VOWAddress
 WHEN 'Yes' THEN 'Y'
 WHEN 'No' THEN 'N'
 ELSE ''
END,
VOWComment =
CASE VOWComment
 WHEN 'Yes' THEN 'Y'
 WHEN 'No' THEN 'N'
 ELSE ''
END,
VOWAVM =
CASE VOWAVM
WHEN 'Yes' THEN 'Y'
WHEN 'No' THEN 'N'
ELSE ''
END,
VOWInclude =
CASE VOWInclude
WHEN 'true' THEN 'Y'
WHEN 'false' THEN 'N'
ELSE ''
END,
PotentialShortSale =
CASE PotentialShortSale
WHEN 'true' THEN 'Y'
WHEN 'false' THEN 'N'
ELSE ''
END,
REO =
CASE REO
WHEN 'true' THEN 'Y'
WHEN 'false' THEN 'N'
ELSE ''
END,
LA1AgentEmail AS AgentEmail,
LA1AgentPhone2Number AS AgentFax,
LA1AgentUrl AS AgentWebPage,
LA1AgentFulltName AS AgentFullName,
LO1OfficeEmail AS  ListingOffice1_Email,
LO1.Phone2Number AS ListingOffice1_Fax,  
LO1OfficeUrl AS ListingOffice1_WebPage,
LA2AgentEmail AS CoListingAgent_Email,
LA2AgentPhone2Number AS CoListingAgent_Fax,
LA2AgentUrl AS CoListingAgent_WebPage,
LA2AgentFullName AS CoListingAgent_FullName,
LO2OfficeEmail AS  ListingOffice2_Email,
CLO.Phone2Number AS ListingOffice2_Fax, 
LO2OfficeUrl AS ListingOffice2_WebPage,
GeoLatitude AS Lat,
GeoLongitude AS Lon,
PoolYN = 
CASE Pool
WHEN 'true' THEN 'Y'
WHEN 'false' THEN 'N'
ELSE ''
END, 
PhotovoltaicInstalled AS PV_Installed, 
PhotovoltaicInstalledO AS PVO_Installed,
PhotovoltaicOwnership AS PV_Ownership,
PhotovoltaicOwnershipO AS PVO_Ownership,
COUNT(*) OVER() AS _Total
FROM Property 
LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id
LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
LEFT JOIN Enum_District AS EDI on District = EDI.Id
LEFT JOIN Enum_Status AS EST on Status = EST.Id
LEFT JOIN Office AS LO1 on ListingOffice1 = LO1.Identifier 
LEFT JOIN Office AS CLO on CoListingOffice = CLO.Identifier 
WHERE ''';

static final OPEN_HOUSE = '''
SELECT 
a.UserCode AS agent1_id,
ca.UserCode AS agent2_id,
o.MainOfficeAbbreviation AS office1_id,
co.MainOfficeAbbreviation AS office2_id,
MLSNumber AS L_ListingID,
Class AS L_Class,
District AS L_Area,
CONVERT(VARCHAR(10), StartDate,  120) AS  OH_StartDate,
CONVERT(VARCHAR(8), StartTime, 108) AS  OH_StartTime,
CONVERT(VARCHAR(8), EndTime, 108) AS  OH_EndTime,
concat('http://www.ramidx.com/MLSImages/363000/200/maui',MLSNumber,'-1.jpg') as Image,
EPT.Name AS L_Type_,
EST.Name AS L_Status,
AddressNumber AS L_AddressNumber,
AddressDirection AS L_AddressDirection,
AddressStreet AS L_AddressStreet,
City AS L_City,
State AS L_State,
ZipCode5 AS L_Zip,
Comments AS OH_Comments
FROM OpenHouse
LEFT JOIN Agent AS a on Agent = a.Identifier
LEFT JOIN Agent AS ca on CoListingAgent = ca.Identifier
LEFT JOIN Office AS o on ListingOffice1 = o.Identifier 
LEFT JOIN Office AS co on CoListingOffice = co.Identifier
LEFT JOIN Enum_Status AS EST on Status = EST.Id 
LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id 
WHERE ''';

static final SHORT_RES_VF = '''
CONVERT(VARCHAR(10), OhanaBeds) AS Ohana_Beds,
CONVERT(VARCHAR(10), OhanaBaths)AS Ohana_Baths,
CAST(OhanaSqFt AS FLOAT) AS Ohana_LivSQFT,
''';

static final SHORT_CONDO_VF = '''
BuildingName,
Unit,
MonthlyMaintFee AS MaintFee,
''';

static final String VF_SOLDS = "<_VF>"

static final SOLDS_RES = '''
SELECT
MLSNumber AS MLS,
EPC.Name AS Class,
EDI.Name AS District,
EPT.Name AS Type,
CONVERT(VARCHAR(10), ClosingDate,  120) AS ClosingDate,
LA1AgentUserCode AS AgentID, 
LO1OfficeAbbreviation AS ListingOffice1_ID,
CAST(ListPrice AS MONEY) AS ListPrice,
CONVERT(VARCHAR(10), SoldPrice) SoldPrice,
VOWAddress,
AddressNumber,
AddressDirection,
AddressStreet,
CONVERT(VARCHAR(10), Beds) AS Beds,
CONVERT(VARCHAR(10), Baths) AS Baths,
ELT.Name AS LT,
CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
CAST(LandSQFT AS FLOAT) AS LandSQFT,
EVI.Name as [View],
EWF.Name as WaterFront,
CONVERT(VARCHAR(10), OhanaBeds) AS Ohana_Beds,
CONVERT(VARCHAR(10), OhanaBaths)AS Ohana_Baths,
CAST(OhanaSqFt AS FLOAT) AS Ohana_LivSQFT,
--Images PhotoCount
COUNT(*) OVER() AS _Total
FROM Property 
LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
LEFT JOIN Enum_District AS EDI on District = EDI.Id
LEFT JOIN Enum_Status AS EST on Status = EST.Id
LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id 
WHERE 
EST.Name = 'SOLD' AND
''';

static final SOLDS_CND = '''
SELECT
MLSNumber AS MLS,
EPC.Name AS Class,
EDI.Name AS District,
EPT.Name AS Type,
CONVERT(VARCHAR(10), ClosingDate,  120) AS ClosingDate,
LA1AgentUserCode AS AgentID, 
LO1OfficeAbbreviation AS ListingOffice1_ID,
CAST(ListPrice AS MONEY) AS ListPrice,
CONVERT(VARCHAR(10), SoldPrice) SoldPrice,
VOWAddress,
AddressNumber,
AddressDirection,
AddressStreet,
CONVERT(VARCHAR(10), Beds) AS Beds,
CONVERT(VARCHAR(10), Baths) AS Baths,
ELT.Name AS LT,
CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
CAST(LandSQFT AS FLOAT) AS LandSQFT,
EVI.Name as [View],
EWF.Name as WaterFront,
BuildingName,
Unit,
CONVERT(VARCHAR(10), MonthlyMaintFee) AS MaintFee,
--Images PhotoCount
CAST(0 AS FLOAT) AS Ohana_LivSQFT,
COUNT(*) OVER() AS _Total
FROM Property 
LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
LEFT JOIN Enum_District AS EDI on District = EDI.Id
LEFT JOIN Enum_Status AS EST on Status = EST.Id
LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id 
WHERE 
EST.Name = 'SOLD' AND
''';

static final SOLDS_VL = '''
SELECT
MLSNumber AS MLS,
EPC.Name AS Class,
EDI.Name AS District,
CONVERT(VARCHAR(10), ClosingDate,  120) AS ClosingDate,
LA1AgentUserCode AS AgentID, 
LO1OfficeAbbreviation AS ListingOffice1_ID,
CAST(ListPrice AS MONEY) AS ListPrice,
CONVERT(VARCHAR(10), SoldPrice) SoldPrice,
VOWAddress,
AddressNumber,
AddressDirection,
AddressStreet,
ELT.Name AS LT,
--CAST(LivingAreaSqFt AS FLOAT) AS LivSQFT,
CAST(LandSQFT AS FLOAT) AS LandSQFT,
EVI.Name as [View],
EWF.Name as WaterFront,
--Images
CAST(0 AS FLOAT) AS LivSQFT,
CAST(0 AS FLOAT) AS Ohana_LivSQFT,
COUNT(*) OVER() AS _Total
FROM Property 
LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
LEFT JOIN Enum_District AS EDI on District = EDI.Id
LEFT JOIN Enum_Status AS EST on Status = EST.Id
LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id 
WHERE 
EST.Name = 'SOLD' AND
''';

static final SHORT = '''
select 
MLSNumber AS MLS,
EPC.Name AS Class,
EST.Name AS Status,
CAST(ListPrice AS MONEY) AS ListPrice,
ELT.Name AS LT,
CONVERT(VARCHAR(10), ListDate,  120) AS ListDate,
CONVERT(VARCHAR(10), LivingAreaSqFt) AS LivSQFT,
LandSQFT,
LA1AgentUserCode AS AgentID, 
LO1OfficeAbbreviation AS ListingOffice1_ID,
LA2UserCode AS CoListingAgent_ID,
LO2OfficeAbbreviation AS ListingOffice2_ID,
AddressNumber,
AddressDirection,
AddressStreet,
AddressLotUnit,
EDI.Name AS District,
BuildingName,
EPT.Name AS Type,
Unit,
CONVERT(VARCHAR(10), Beds) AS Beds,
CONVERT(VARCHAR(10), Baths) AS Baths,
PotentialShortSale =
CASE PotentialShortSale
WHEN 0 THEN 'N'
WHEN 1 THEN 'Y'
ELSE ''
END,
REO =
CASE REO
WHEN 0 THEN 'N'
WHEN 1 THEN 'Y'
ELSE ''
END,
GeoLatitude AS Lat,
GeoLongitude AS Lon,
COUNT(*) OVER() AS _Total
FROM Property 
LEFT JOIN Enum_PropertyClass AS EPC on Class = EPC.Id
LEFT JOIN Enum_LandTenure AS ELT on LT = ELT.Id
LEFT JOIN Enum_PropertyType AS EPT on PropertyType = EPT.Id
LEFT JOIN Enum_Waterfront AS EWF on WaterFront = EWF.Id
LEFT JOIN Enum_View AS EVI on [View] = EVI.Id
LEFT JOIN Enum_District AS EDI on District = EDI.Id
LEFT JOIN Enum_Status AS EST on Status = EST.Id
WHERE 
''';

//static final OPEN_HOUSE = '''
//select 
//a.UserCode AS agent1_id,
//ca.UserCode AS agent2_id,
//o.MainOfficeAbbreviation AS office1_id,
//co.MainOfficeAbbreviation AS office2_id,
//MLSNumber AS L_ListingID,
//Class AS L_Class,
//District AS L_Area,
//CONVERT(VARCHAR(10), StartDate,  120) AS  OH_StartDate,
//CONVERT(VARCHAR(8), StartTime, 108) AS  OH_StartTime,
//CONVERT(VARCHAR(8), EndTime, 108) AS  OH_EndTime,
//concat('http://www.ramidx.com/MLSImages/363000/200/maui',MLSNumber,'-1.jpg')image,
//Type AS L_Type_,
//L_Satus =
//CASE Status
//WHEN 0 THEN 'Active'
//WHEN 1 THEN 'Sold'
//WHEN 2 THEN 'Pending'
//WHEN 3 THEN 'Expired'
//WHEN 4 THEN 'Withdrawn'
//ELSE 'N/A'
//END,
//AddressNumber AS L_AddressNumber,
//AddressDirection AS L_AddressDirection,
//AddressStreet AS L_AddressStreet,
//City AS L_City,
//State AS L_State,
//ZipCode5 AS L_Zip,
//comments AS OH_Comments
//from OpenHouse oh
//left join Agent AS a on oh.agent = a.Identifier
//left join Agent AS ca on oh.CoListingAgent = ca.Identifier
//left join Office AS o on oh.ListingOffice1 = o.Identifier 
//left join Office AS co on oh.CoListingOffice = co.Identifier 
//WHERE 
//''';

}
