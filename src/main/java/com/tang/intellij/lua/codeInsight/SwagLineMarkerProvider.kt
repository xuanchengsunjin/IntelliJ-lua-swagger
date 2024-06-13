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

package com.tang.intellij.lua.codeInsight

import com.intellij.codeHighlighting.Pass
import com.intellij.codeInsight.daemon.AbstractLineMarkerProvider
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.codeInsight.daemon.createLineMarkerInfo
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.util.FunctionUtil
import com.tang.intellij.lua.comment.psi.impl.LuaDocTagSwagRouterImpl
import com.tang.intellij.lua.lang.LuaIcons
import javax.swing.Icon

class SwagLineMarkerProvider : AbstractLineMarkerProvider() {
    private fun collectNavigationMarkers(element: PsiElement, result: MutableCollection<in LineMarkerInfo<*>>) {
        if (element is LuaDocTagSwagRouterImpl){
            val classIcon = LineMarkerInfo(
                element,
                element.textRange,
                LuaIcons.API,
                Pass.LINE_MARKERS,
                FunctionUtil.constant("Swagger"),
                null,
                GutterIconRenderer.Alignment.RIGHT,
            )
            result.add(classIcon)
        }
    }

    override fun getLineMarkerInfo(p0: PsiElement): LineMarkerInfo<*>? {
        return null
    }

    override fun collectSlowLineMarkersExt(list: List<PsiElement>, collection: MutableCollection<in LineMarkerInfo<*>>) {
        for (element in list) {
            ProgressManager.checkCanceled()
            collectNavigationMarkers(element, collection)
        }
    }
}
