package app.websearch.transformer;

import static org.junit.Assert.*

import org.junit.Test

import app.websearch.url.helper.UrlHelper

class XmlRpcToOdataParameterTransformerTest {
	
	@Test
	void testToOdataParams() {
		TransformationService transformer = new TransformationService()
		def url ='http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatView%5B%5D=Mountain&WhatDistrict%5B%5D=Haiku&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'
		def websearchParams = UrlHelper.urlToMap(url)
		println websearchParams		
		Map odataParams = transformer.fromXmlrpcToOdata(websearchParams)
		println odataParams
		assertEquals([View:['Mountain'], District:['Haiku'], PropertyType:['Residential']], odataParams )
		def metaModel = odataParams.containsKey('PropertyType')?'Property':''//?
		
		def dataFilter = "District eq RAMOdata.Models.District%27Haiku%27 and View eq 'Mountain/Ocean'"
		def control = "http://ramodata.azurewebsites.net/odata/$metaModel?\$top=10&\$filter=$dataFilter"
		
		//def control = "http://ramodata.azurewebsites.net/odata/Properties?\$top=10&\$filter=District eq RAMOdata.Models.District%27Haiku%27 and View eq 'Mountain/Ocean'"
		//http://ramodata.azurewebsites.net/odata/Properties?\$top=10&\$filter=District eq RAMOdata.Models.District'Haiku' and View eq 'Mountain/Ocean'
		
	}

}
