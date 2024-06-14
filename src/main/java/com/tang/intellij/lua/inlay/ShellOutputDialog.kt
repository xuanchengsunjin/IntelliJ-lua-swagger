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
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextPane
import javax.swing.text.AttributeSet
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument

class ShellOutputDialog(project: Project?) : DialogWrapper(project) {
    private val textPane = JTextPane().apply {
        isEditable = false
    }

    private val scrollPane = JBScrollPane(textPane).apply {
        preferredSize = Dimension(800, 400)
    }

    private val panel = JPanel(BorderLayout()).apply {
        add(scrollPane, BorderLayout.CENTER)
    }

    init {
        init()
        title = "Shell Output"
    }

    fun appendColoredText(text: String, color: Color) {
        val doc: StyledDocument = textPane.styledDocument
        val style: AttributeSet = SimpleAttributeSet().apply {
            StyleConstants.setForeground(this, color)
        }
        doc.insertString(doc.length, text, style)
    }

    override fun createCenterPanel(): JComponent {
        return panel
    }
}