package ly.goal.auth

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class SecurityInfoController extends grails.plugins.springsecurity.ui.SecurityInfoController {
}
