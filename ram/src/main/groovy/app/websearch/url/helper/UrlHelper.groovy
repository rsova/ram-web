package app.websearch.url.helper

class UrlHelper {
	public static Map urlToMap(String url){
		url  =  java.net.URLDecoder.decode(url)
		//def tokens = url.replaceFirst('http://www.ramaui.com/Results.php?','').split('&')
		def tokens = url.split('&')
		def map =[:]
		//tokens.each {  token->
		for (token in tokens) {
			def element = token.split('=')
			if(element.size() <2){
				continue
			}
		
			def key = element.first()
			def val = element.last()
		
			if(key.contains('[]')){
				key = element.first().replace("[]",'')
				if(map.containsKey(key)){
					map."$key".add(val)
				}else{
					map.put(key, [val])
				}
			}else{map.put(key, val)}
		}
		map.remove('Task')
		return map
	}
}
