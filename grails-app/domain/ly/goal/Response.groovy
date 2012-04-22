package ly.goal

class Response {
	static belongsTo = [question: Question]

	static mapping = {
		id generator:'sequence', params:[sequence:'response_id_sequence']
	}

	Date date
	int value
}