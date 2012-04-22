package ly.goal.auth

import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class PersistentLoginController extends grails.plugins.springsecurity.ui.PersistentLoginController {
}
