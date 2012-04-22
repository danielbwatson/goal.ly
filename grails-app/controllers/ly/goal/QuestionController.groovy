package ly.goal

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_USER'])
class QuestionController {
	static allowedMethods = [add: 'POST', update: 'PUT', delete: 'DELETE']
	def springSecurityService
	def times = (0..23).toList().collect({
		def str = it.toString()
		if (str.size() < 2) { str = "0" + str }
		str += ":00"
		[key:it, value:str]
	})

	def index() {
		def result = Question.findAllByUser(springSecurityService.currentUser)
		render view: 'index', model: [questions: result, times: times]
	}

	def add() {
		def question = new Question(
				questionType: QuestionType.valueOf(QuestionType.class, request?.JSON?.questionType?.trim()),
				text: request?.JSON?.text?.trim(),
				time: request?.JSON?.time,
				user: springSecurityService.currentUser)
		assert question.validate(), "Invalid question"
		assert question.save()

		render question as JSON
	}

	def update() {
		Long id = request?.JSON?.id
		assert id, 'id is required'

		def question = Question.findByIdAndUser(id, springSecurityService.currentUser)
		assert question, 'id is invalid'
		question.text = request?.JSON?.text?.trim()
		question.time = request?.JSON?.time as Byte
		assert question.validate(), "Invalid question"
		assert question.save()

		render question as JSON
	}

	def delete() {
		Long id = request?.JSON?.id
		assert id, 'id is required'

		def question = Question.findByIdAndUser(id, springSecurityService.currentUser)
		assert question, 'id is invalid'
		question.delete()

		def result = [success: 'true']
		render result as JSON
	}
}
