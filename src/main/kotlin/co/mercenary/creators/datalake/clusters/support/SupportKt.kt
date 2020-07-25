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

typealias PostData = co.mercenary.creators.kotlin.json.util.typicode.TypicodePostData

typealias TodoData = co.mercenary.creators.kotlin.json.util.typicode.TypicodeTodoData

typealias DataLakeSupport = co.mercenary.creators.kotlin.boot.data.AbstractApplicationDataSupport

inline fun <reified T : Any> org.springframework.beans.factory.BeanFactory.beanOf(): T = getBean(T::class.java)
