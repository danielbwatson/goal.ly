package ly.goal

class PollUserJob {
	// Only execute one thread
	def concurrent = false
	def mailService
	static triggers = {
		// Check every 15s
		simple name: 'pollUser', startDelay: 0, repeatInterval: 15000, repeatCount: -1
	}

	def execute() {
		poll(this.processQuestion)
	}

	def timeInfo() {
		def now = new Date()
		def hour = now.hours as Byte
		hour ?: 24
		def today = now.clearTime()

		[hour, today]
	}

	def poll(Closure processQuestion) {
		def (hour, today) = timeInfo();

		def questions = Question.createCriteria().list {
			lt("time", hour)
			or {
				isNull("lastAsked")
				lt("lastAsked", today)
			}

			// Arbitrary limit
			maxResults(1000)
		}
		log.debug("Questions: ${questions}")

		questions.each { question ->
			processQuestion(question)
		}
	}

	def processQuestion = { Question question ->
		def code = "u${question.userId}q${question.id}t${new Date().time}"

		mailService.sendMail {
			to question.user.email
			subject 'Question from goal.ly'
			body "${question.text}\n\n${code}"
		}

		question.lastAsked = new Date()
		question.save(flush: true)
	}
}
