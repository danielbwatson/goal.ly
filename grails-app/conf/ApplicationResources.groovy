modules = {
	backbone {
		dependsOn 'jquery'

		resource url: '/js/lib/underscore-1.3.1.js'
		resource url: '/js/lib/backbone-0.9.1.js'
		resource url: '/js/lib/backbone.modelbinding-0.5.0.js'
		resource url: '/js/lib/backbone.validation-0.5.2.js'
	}

	application {
		dependsOn 'backbone'

		resource url: 'js/application.js'
	}
}
