import grails.converters.JSON
import ly.goal.Question
import ly.goal.QuestionType
import ly.goal.auth.Role
import ly.goal.auth.User
import ly.goal.auth.UserRole

class BootStrap {
	def grailsApplication

	def init = { servletContext ->
		JSON.registerObjectMarshaller(QuestionType) {
			return it.name()
		}

		JSON.registerObjectMarshaller(Question) {
			def responses = it.responses.collect {
				['id': it.id, 'date': it.date, 'value': it.value]
			}
			return ['id': it.id,
					'questionType': it.questionType,
					'text': it.text,
					'time': it.time,
					'responses': responses]
		}

		if (Role.count() == 0) {
			def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
			def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)

			def adminEmail = System.env.GOALLY_ADMIN_EMAIL ?: grailsApplication.config.goal.ly.admin.email
			def adminUser = new User(username: adminEmail, email: adminEmail, enabled: true, password: 'password')
					.save(flush: true, failOnError: true)

			UserRole.create adminUser, adminRole, true
			UserRole.create adminUser, userRole, true

			assert User.count() == 1
			assert Role.count() == 2
			assert UserRole.count() == 2
		}
	}

	def destroy = {
	}
}
