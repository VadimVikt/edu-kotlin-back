package exceptions

import kotlin.reflect.KClass

class UnknownRequestValue(cls: KClass<*>): RuntimeException("Class $cls can't be mapping and not supported")