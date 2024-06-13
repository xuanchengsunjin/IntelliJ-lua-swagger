/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tang.intellij.lua.config

import com.intellij.openapi.project.Project
import java.nio.file.Files
import java.nio.file.Paths
import com.google.gson.reflect.TypeToken;

class SwagConfigManager(private val project: Project) {
    private val configFilePath = Paths.get(project.basePath, ".idea", "settings.json")
    fun getConfig(): Map<String, String>? {
        return if (Files.exists(configFilePath)) {
            val json = Files.readString(configFilePath)
            parseConfig(json)
        } else {
            null
        }
    }

    private fun parseConfig(json: String): Map<String, String> {
        // 使用 Gson 或 Jackson 解析 JSON
        val gson = com.google.gson.Gson()
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(json, mapType)
    }
}
