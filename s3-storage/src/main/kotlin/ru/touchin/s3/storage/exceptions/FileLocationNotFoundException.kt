package ru.touchin.s3.storage.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException
import java.util.*

class FileLocationNotFoundException(id: String) : CommonNotFoundException("File location not found id=$id")
