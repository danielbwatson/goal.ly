package ly.goal.auth

class RegisterController extends grails.plugins.springsecurity.ui.RegisterController {
	def register = {
		params.email = params.username
		super.register();
	}
}
