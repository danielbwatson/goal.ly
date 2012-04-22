package goal.ly

class HelpersTagLib {
	static namespace = "h"

	def script_template = { attrs, body ->
		out << "<script type='text/html' id='" << attrs.id << "'>" << body() << "</script>"
	}
}
