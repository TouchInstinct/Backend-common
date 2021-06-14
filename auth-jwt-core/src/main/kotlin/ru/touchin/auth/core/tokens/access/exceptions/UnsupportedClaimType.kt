package ru.touchin.auth.core.tokens.access.exceptions

import ru.touchin.common.exceptions.CommonException

class UnsupportedClaimType(typeName: String?) : CommonException("Unsupported claim type: $typeName")
