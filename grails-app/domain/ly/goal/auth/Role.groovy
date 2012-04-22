package ly.goal.auth

class Role {

	String authority

	static mapping = {
		id generator:'sequence', params:[sequence:'role_id_sequence']
		table 'auth_role'
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
