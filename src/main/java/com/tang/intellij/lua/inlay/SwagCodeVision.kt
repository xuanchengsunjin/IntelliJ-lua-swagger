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

import java.io.BufferedReader
import java.io.InputStreamReader
import com.intellij.codeInsight.hints.*
import com.intellij.codeInsight.hints.presentation.MouseButton
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.externalSystem.autoimport.AutoImportProjectTracker
import com.intellij.openapi.ui.Messages.showInfoMessage
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.ui.content.ContentFactory
import com.jetbrains.rd.util.ExecutionException
import com.tang.intellij.lua.comment.psi.impl.LuaDocTagSwagRouterImpl
import com.tang.intellij.lua.config.SwagConfigManager
import com.tang.intellij.lua.lang.LuaFileType
import io.ktor.utils.io.errors.*
import javax.swing.JPanel

class SwagCodeVision: InlayHintsProvider<NoSettings> {
    override fun getCollectorFor(file: PsiFile, editor: Editor, settings: NoSettings, sink: InlayHintsSink): InlayHintsCollector? {
        return when {
            file.fileType == LuaFileType.INSTANCE -> Collector(editor)
            else -> null
        }
    }

    private class Collector(editor: Editor): FactoryInlayHintsCollector(editor) {
        override fun collect(element: PsiElement, editor: Editor, sink: InlayHintsSink): Boolean {
            if (editor.getUserData(DISABLE_TABLE_INLAYS) == true) {
                return true
            }
            if (!element.isValid) {
                return true
            }

            if (element !is LuaDocTagSwagRouterImpl){
                return  true
            }

            val routerPresentation = factory.smallText("生成route代码")
            val genSwaggerPresentation = factory.smallText("|生成swagger文档")
            val yapiPresentation = factory.smallText("|同步到yapi")
            val routerPresentationRet = factory.onClick(routerPresentation, MouseButton.Left ){ _, _ ->
                var cmd = genCmd(element.project, element, "router")
                if (cmd != null) {
                    executeCommandAndShowOutput(element.project, cmd, "生成Route代码")
                }
            }
            val genSwaggerPresentationRet = factory.onClick(genSwaggerPresentation, MouseButton.Left ){ _, _ ->
                var cmd = genCmd(element.project, element, "genSwag")
                if (cmd != null) {
                    executeCommandAndShowOutput(element.project, cmd, "生成swagger文档")
                }
            }
            val yapiPresentationRet = factory.onClick(yapiPresentation, MouseButton.Left ){ _, _ ->
                var cmd = genCmd(element.project, element, "yapiImport")
                if (cmd != null) {
                    executeCommandAndShowOutput(element.project, cmd, "同步到yapi")
                }
            }
            sink.addInlineElement(element.textOffset, false, routerPresentationRet, true)
            sink.addInlineElement(element.textOffset, false, genSwaggerPresentationRet, true)
            sink.addInlineElement(element.textOffset, false, yapiPresentationRet, true)

            return true
        }

        private fun genCmd(project: Project, element: PsiElement, kind: String, ): String?{
            var swagConfig = SwagConfigManager(element.project).getConfig()
            if (swagConfig == null){
                return null
            }
            var routerPath = "/internal/purchase/notify_inherit_paylog"
            if (kind == "router") {
                val cmd = "cd ${project.basePath} && luatools swag-gen --SearchDir=\"${swagConfig.get("swagger.search_dirs")}\" --MainAPIFile=\"${swagConfig.get("swagger.main.lua.path")}\" --genMode=\"*\" --GenRoutePatterns=\"${routerPath}\" --dtoDir=\"${swagConfig.get("dto_dir")}\" --validatorDir=\"${swagConfig.get("validator_dir")}\""
                return cmd
            }else if (kind == "genSwag") {
                var cmd = "cd ${project.basePath} && luatools swag-resty --SearchDir=\"${swagConfig.get("swagger.search_dirs")}\" --MainAPIFile=\"${swagConfig.get("swagger.main.lua.path")}\" --OutputDir=\"${swagConfig.get("swagger.docs.path")}\" --OutputType=\"${swagConfig.get("swagger.file.type")}\" --outputName=\"${swagConfig.get("swagger.name")}\" --excludes=\"${swagConfig.get("swagger.excludes")}\" --apiTool=\"swagger,yapi\""
                return cmd
            }else if (kind == "yapiImport") {
                var cmd = "cd ${project.basePath} && luatools yapi-import --yapiFile=\"${swagConfig.get("yapi.config.file")}\" --yapiToken=\"${swagConfig.get("yapi.config.file")}\" --yapiServer=\"${swagConfig.get("yapi.config.server")}\" --yapiMode=\"${swagConfig.get("yapi.config.mode")}\" --RoutePatterns=\"${routerPath}\""
                return cmd
            }
            return null
        }

        private fun executeCommandAndShowOutput(project: Project, command: String, title:String) {
            val processBuilder = ProcessBuilder(command.split(" "))
            val process = processBuilder.start()
            val processHandler = OSProcessHandler(process, command)
            ApplicationManager.getApplication().executeOnPooledThread {
                try {
                    val processBuilder = ProcessBuilder("sh", "-c", command)
                    val process = processBuilder.start()
                    val output = process.inputStream.bufferedReader().readText()
                    val errorOutput = process.errorStream.bufferedReader().readText()
                    process.waitFor()
                    ApplicationManager.getApplication().invokeLater {
                        showOutputInToolWindow( output + errorOutput, title, project)
                    }
                } catch (e: Exception) {
                    ApplicationManager.getApplication().invokeLater {
                        showOutputInToolWindow("Error executing command: ${e.message}", title, project)
                    }
                }
            }
        }

        fun executeShellCommand(command: String, project: Project) {
            val dialog = ShellOutputDialog(project)
            dialog.show()

            val process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val errorReader = BufferedReader(InputStreamReader(process.errorStream))

            // 启动一个新的线程来读取标准输出流
//            Thread {
//                var line: String?
//                while (reader.readLine().also { line = it } != null) {
//                    println(line!!)
//                    dialog.appendText(line!!)
//                }
//            }.start()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println(line!!)
                dialog.appendText(line!!)
            }

            // 启动一个新的线程来读取错误输出流
            Thread {
                var line: String?
                while (errorReader.readLine().also { line = it } != null) {
                    println(line!!)
                    dialog.appendText(line!!)
                }
            }.start()

            process.waitFor()
        }


        private fun showOutputInToolWindow(output: String, title: String, project:Project) {
            println(output)
            val dialog = ShellOutputDialog(project)

            dialog.appendText(output)
            dialog.show()
//            showInfoMessage( output, title)
        }


        private fun openTerminalAndExecuteCommand(project: Project, command: String) {
            ApplicationManager.getApplication().invokeLater {
                try {
                    val commandLine = GeneralCommandLine(command)
                    var processHandler = ProcessHandlerFactory.getInstance().createColoredProcessHandler(commandLine);
//                    val processHandler = OSProcessHandler(commandLine)
                    ProcessTerminatedListener.attach(processHandler)
                    processHandler.startNotify()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                }
            }
        }

        private fun executeCommand(command: String) {
            ApplicationManager.getApplication().executeOnPooledThread {
                try {
                    println("command:" + command)
                    Runtime.getRuntime().exec(command)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

    }

    override fun createSettings() = NoSettings()

    override val name: String
        get() = "InProvider"

    override val description: String
        get() = "My Inlay Hints Provider"

    override val key: SettingsKey<NoSettings>
        get() = settingsKey

    override val previewText: String? = null

    override fun createConfigurable(settings: NoSettings): ImmediateConfigurable {
        return object: ImmediateConfigurable {
            override fun createComponent(listener: ChangeListener) = JPanel()
        }
    }

    companion object {
        private val settingsKey = SettingsKey<NoSettings>("MarkdownTableInlayProviderSettingsKey")

        val DISABLE_TABLE_INLAYS = Key<Boolean>("MarkdownDisableTableInlaysKey")
    }
}