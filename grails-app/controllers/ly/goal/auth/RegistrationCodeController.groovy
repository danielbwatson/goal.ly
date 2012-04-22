package ly.goal.auth

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class RegistrationCodeController extends grails.plugins.springsecurity.ui.RegistrationCodeController {
}
