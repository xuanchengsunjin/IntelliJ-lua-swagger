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

package com.tang.intellij.lua.inlay

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import java.nio.file.Files
import java.nio.file.Paths
import com.intellij.openapi.startup.ProjectActivity

class SwagStartAc : ProjectActivity {
    override suspend fun execute(project: Project) {
        val projectBasePath = project.basePath ?: return
        val ideaPath = Paths.get(projectBasePath, ".idea")
        val configFilePath = ideaPath.resolve("settings.json")

        if (Files.notExists(configFilePath)) {
            val defaultConfig = """
{
    "dto_dir": "./ngx_conf/dto",
    "swagger.docs.path": "./docs",
    "swagger.excludes": "./client",
    "swagger.file.type": "json",
    "swagger.main.lua.path": "./main.lua",
    "swagger.name": "swagger",
    "swagger.search_dirs": "./ngx_conf,./config/cn/online",
    "validator_dir": "./ngx_conf/validator",
    "yapi.config.file": "docs/swagger-yapi.json",
    "yapi.config.mode": "mergin",
    "yapi.config.server": "https://web-api.intsig.net",
    "yapi.config.token": "19005757da6de5b9f374996358fd186646e1fa3a82fce3a4ba41a6ea5785b8c4"
}
            """
            Files.createDirectories(ideaPath)
            Files.write(configFilePath, defaultConfig.toByteArray())
        }
    }
}