package com.github.adeynack.finances.service.service

import org.apache.log4j.LogManager
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class LogService {

    fun <T : Any> getLogger(clazz: KClass<T>) = LogManager.getLogger(clazz.java)

}
