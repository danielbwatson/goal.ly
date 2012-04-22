package ly.goal.auth

class Role {

	String authority

	static mapping = {
		table 'auth_role'
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
