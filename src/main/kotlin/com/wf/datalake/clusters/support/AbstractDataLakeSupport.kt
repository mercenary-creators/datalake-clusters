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

package com.wf.datalake.clusters.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.context.*

abstract class AbstractDataLakeSupport : AbstractLogging(), ApplicationContextAware {

    private var context: ApplicationContext? = null

    override fun setApplicationContext(context: ApplicationContext) {
        this.context = context
    }

    protected open fun getApplicationContext(): ApplicationContext? = this.context

    protected open fun getEnvironmentProperty(name: String): String? {
        return getApplicationContext()?.environment?.getProperty(name)
    }

    protected open fun getEnvironmentPropertyOrElse(name: String, other: String): String {
        return getApplicationContext()?.environment?.getProperty(name, other) ?: other
    }

    protected open fun getEnvironmentPropertyOrElse(name: String, other: () -> String): String {
        return getApplicationContext()?.environment?.getProperty(name) ?: other()
    }

    protected open fun <T> timed(block: () -> T): T = timed({ info { it } }, block)
}