package com.github.adeynack.finances.service.service

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class LogService {

    fun <T : Any> getLogger(clazz: KClass<T>): Logger = LogManager.getLogger(clazz.java)

}
