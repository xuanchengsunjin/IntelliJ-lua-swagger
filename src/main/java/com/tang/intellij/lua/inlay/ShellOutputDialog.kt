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
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class ShellOutputDialog(project: Project?) : DialogWrapper(project) {
    private val textArea = JBTextArea(20, 100) // 设置初始大小
    private val panel = JPanel()

    init {
        textArea.isEditable = false
        textArea.background = JBTextArea().background
        textArea.foreground = JBTextArea().foreground
        textArea.font = JBTextArea().font
        panel.add(JBScrollPane(textArea))
        init()
        title = "Shell Output"
    }

    fun appendText(text: String) {
        textArea.append("$text\n")
        textArea.caretPosition = textArea.document.length
    }

    override fun createCenterPanel(): JComponent {
        return panel
    }
}
