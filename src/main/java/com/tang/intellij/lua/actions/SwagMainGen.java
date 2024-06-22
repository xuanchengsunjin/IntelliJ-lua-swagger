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

package com.tang.intellij.lua.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.tang.intellij.lua.constants.Constants;

public class SwagMainGen extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        // 获取当前项目实例
        Project project = e.getProject();

        if (editor == null || project == null) {
            return;
        }

        // 获取当前光标位置
        Caret caret = editor.getCaretModel().getCurrentCaret();
        int offset = caret.getOffset();

        // 获取文档实例
        Document document = editor.getDocument();

        // 执行写操作
        Application application = ApplicationManager.getApplication();
        WriteCommandAction.runWriteCommandAction(project, () -> {
            // 在光标处插入字符串
            document.insertString(offset, Constants.SwagMain);
        });
    }
}