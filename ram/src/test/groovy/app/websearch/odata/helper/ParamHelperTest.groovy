package app.websearch.odata.helper;

import static org.junit.Assert.*

import org.junit.Test

import app.service.odata.Mappings
import app.websearch.rpc.helper.XmlRpcHelper;
import app.websearch.url.helper.UrlHelper

import com.google.gson.Gson

class ParamHelperTest {

	@Test
	public void testUrl1() {
		def control = "http://ramodata.azurewebsites.net/odata/Properties?\$top=10&\$filter=District eq RAMOdata.Models.District%27Haiku%27 and View eq 'Mountain/Ocean'"
		def url ='http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=&WhatEndPrice=&WhatView%5B%5D=Mountain&WhatDistrict%5B%5D=Haiku&WhatStartBed=&WhatEndBed=&WhatStartBath=&WhatEndBath=&WhatStartIntArea=&WhatEndIntArea=&WhatStartExtArea=&WhatEndExtArea=&WhatSortType1=ListPrice&WhatSortDirection1=ASC&WhatSortType2=&WhatSortDirection2=ASC&Task=Search'
		def params = UrlHelper.urlToMap(url)
		println params
		
		
		//District
		//Condo
		//Agent
		//Office
		
		
	}

}
