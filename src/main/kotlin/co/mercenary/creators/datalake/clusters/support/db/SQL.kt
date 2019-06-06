/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.datalake.clusters.support.db

import org.intellij.lang.annotations.Language
import kotlin.annotation.AnnotationRetention.SOURCE
import kotlin.annotation.AnnotationTarget.*

@Retention(SOURCE)
@Language("Generic SQL")
@Target(ANNOTATION_CLASS, FIELD, FUNCTION, PROPERTY, PROPERTY_GETTER, PROPERTY_SETTER, LOCAL_VARIABLE, VALUE_PARAMETER, EXPRESSION)
@MustBeDocumented
annotation class SQL