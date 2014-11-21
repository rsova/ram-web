/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@RestController
class Ram {
//
//	@RequestMapping("/")
//	def websearch() {
//		[message: "Hello World"]
//	}
	
//	@Autowired
//	RamoService oService

//	@RequestMapping(value="/websearch/{name}", method = RequestMethod.POST)// consumes="application/json")
//	def handleWebsearch(@RequestBody String xml, @PathVariable String name) {
//		RamoService oService = new RamoService()
//		def wsResponseData = [:]
//		Map serviceParams = generateServiceParameterMap(xml)
//		if(name.equals('lists')){
//			//			service.collection = COLLECTION_LIST
//			//			wsResponseData = service.findOne(serviceParams)
//			//			method = serviceParams.get(METHOD_NAME_KEY).capitalize()
//		}else{
//			wsResponseData = oService.findAllFromParams(serviceParams)
//			//method = 'search'
//		}
//
//		[message: wsResponseData]
//	}
//
//
//	private Map generateServiceParameterMap(String xmlRpc) {
//		def xmlRpcMethodParams = XmlRpcHelper.xmlRpcToCollection(xmlRpc)
//		//xmlRpcMethodParams.remove(DBID)
//		
//		return xmlRpcMethodParams
//	}

	static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(Ram.class, args)
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}

	}

}
