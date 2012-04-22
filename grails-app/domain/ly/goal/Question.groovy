package ly.goal

import ly.goal.auth.User

class Question {
	static belongsTo = [user: User]
	static hasMany = [responses: Response]

	Date lastAsked
	QuestionType questionType
	String text
	Byte time

	static constraints = {
		lastAsked nullable: true
		questionType nullable: false
		text blank: false, nullable: false
		time nullable: false, range: 0..23
	}

	static mapping = {
		id generator:'sequence', params:[sequence:'question_id_sequence']
		responses lazy: true, sort: 'date'
		time sqlType: 'integer'
	}
}
