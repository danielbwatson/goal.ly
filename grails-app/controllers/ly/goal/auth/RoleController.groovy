package ly.goal.auth

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class RoleController extends grails.plugins.springsecurity.ui.RoleController {
}
