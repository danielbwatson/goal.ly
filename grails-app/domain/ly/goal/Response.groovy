package ly.goal

class Response {
	static belongsTo = [question: Question]

	Date date
	int value
}