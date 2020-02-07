/*
 * Copyright (c) 2020, Mercenary Creators Company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:kotlin.jvm.JvmName("SupportKt")

package co.mercenary.creators.datalake.clusters.support

import org.springframework.beans.factory.BeanFactory
import org.springframework.security.crypto.password.PasswordEncoder

typealias PostData = co.mercenary.creators.kotlin.json.util.typicode.TypicodePostData

typealias TodoData = co.mercenary.creators.kotlin.json.util.typicode.TypicodeTodoData

typealias DataLakeSupport = co.mercenary.creators.kotlin.boot.data.AbstractApplicationDataSupport

data class UserPartialData(val username: String, val enabled: Boolean)

data class UserAndPassData(val username: String, val password: String) {
    fun encode(pass: PasswordEncoder) = UserAndPassData(username, pass.encode(password))
}

inline fun <reified T : Any> BeanFactory.beanOf(): T = getBean(T::class.java)
