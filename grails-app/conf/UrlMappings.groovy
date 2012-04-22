class UrlMappings {

	static mappings = {
		"/question/$id?"(controller: "question") {
			action = [GET: "index", POST: "add", PUT: "update", DELETE: "delete"]
		}

		"/$controller/$action?/$id?" {
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'question', action: 'index')
		"500"(view: '/error')
	}
}
