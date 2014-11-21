package app.websearch.url.helper;

import static org.junit.Assert.*

import org.junit.Test

class UrlHelperTest {

	@Test
	public void test() {
		def url ='''http://www.ramaui.com/Results.php?MLS=&WhatPropType%5B%5D=Residential&WhatStartPrice=450000&WhatEndPrice=2000000&WhatPool=N&WhatLandTenure%5B%5D=Fee+Simple&WhatDistrict%5B%5D=Kahului&WhatDistrict%5B%5D=Maalaea&WhatDistrict%5B%5D=Maui+Meadows&WhatDistrict%5B%5D=Napili%2FKahana%2FHonokowai&WhatDistrict%5B%5D=Wailuku&WhatStartBed=2&WhatEndBed=&WhatStartBath=2&WhatEndBath=&WhatStartIntArea=1000&WhatEndIntArea=2000&WhatStartExtArea=21780&WhatEndExtArea=&WhatSortType1=District&WhatSortDirection1=ASC&WhatSortType2=ListPrice&WhatSortDirection2=DESC&Task=Search'''
		println UrlHelper.urlToMap(url)
	}

}
