package ru.touchin.auth.security.jwt.exceptions

import ru.touchin.common.exceptions.CommonException

class JwtMissingClaimException(claim: String) : CommonException("missing jwt claim $claim")
