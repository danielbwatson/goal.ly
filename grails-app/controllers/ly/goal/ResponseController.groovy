package ly.goal

class ResponseController {
	static allowedMethods = [save: "POST"]
	def idPattern = /u(\d+)q(\d+)t(\d+)/
	def numberPattern = /([0-9\.])/
	def truePattern = /(?i)yes|true|yeah|1/

	def save() {
		log.error("Called with params ${params}")
		def match = params["body-plain"] =~ idPattern
		assert match, "Unable to parse response from ${params}"

		def userId = Long.valueOf(match[0][1])
		def questionId = Long.valueOf(match[0][2])

		def question = Question.findById(questionId)
		assert question, "No matching question found in response to ${params}"
		assert question.userId == userId, "Question ${questionId} does not belong to user ${userId}"

		def response = new Response(date: new Date())
		switch (question.questionType) {
			case QuestionType.Boolean:
				match = params["body-plain"] =~ truePattern
				break;
			default:
				match = params["body-plain"] =~ numberPattern
				break;
		}

		if (match) {
			if (question.questionType == QuestionType.Boolean) {
				response.value = 1;
			} else {
				response.value = Integer.valueOf(match[0][1])
			}
		} else {
			response.value = 0;
		}
		question.addToResponses(response)
		question.save()

		render('OK')
	}
}
