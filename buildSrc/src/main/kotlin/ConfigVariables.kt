object ConfigVariables {

    object Names {
        const val baseHost = "API_BASE_HOST"
        const val apiKey = "API_X_API_KEY"
    }
    object Type {
        const val string: String = "String"
    }

    interface Values {
        val baseHost: String
        val apiKey: String
    }

    object Debug : Values {
        override val baseHost: String = "\"https://frontend-test-assignment-api.abz.agency/api/v1\""
        override val apiKey: String = "\"\""
    }
    object Release : Values {
        override val baseHost: String = "\"https://frontend-test-assignment-api.abz.agency/api/v1\""
        override val apiKey: String = "\"\""
    }
}