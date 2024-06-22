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

package com.tang.intellij.lua.editor.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.Language
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.intellij.util.Processor
import com.tang.intellij.lua.comment.LuaCommentUtil
import com.tang.intellij.lua.comment.psi.*
import com.tang.intellij.lua.comment.psi.api.LuaComment
import com.tang.intellij.lua.config.SwagConfigManager
import com.tang.intellij.lua.lang.LuaIcons
import com.tang.intellij.lua.lang.LuaParserDefinition
import com.tang.intellij.lua.psi.LuaClassField
import com.tang.intellij.lua.psi.LuaFuncBodyOwner
import com.tang.intellij.lua.psi.search.LuaShortNamesManager
import com.tang.intellij.lua.search.SearchContext
import com.tang.intellij.lua.ty.ITyClass

/**
 * doc 相关代码完成
 * Created by tangzx on 2016/12/2.
 */
class LuaDocCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, SHOW_DOC_TAG, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                val set = LuaParserDefinition.DOC_TAG_TOKENS
                for (type in set.types) {
                    completionResultSet.addElement(LookupElementBuilder.create(type).withIcon(LuaIcons.ANNOTATION))
                }
                ADDITIONAL_TAGS.forEach { tagName ->
                    completionResultSet.addElement(LookupElementBuilder.create(tagName).withIcon(LuaIcons.ANNOTATION))
                }
                SWAG_ROUTER_TAGS.forEach { tagName ->
                    completionResultSet.addElement(LookupElementBuilder.create(tagName).withIcon(LuaIcons.API))
                }
                SWAG_FIELD_TAGS.forEach { tagName ->
                    completionResultSet.addElement(LookupElementBuilder.create(tagName).withIcon(LuaIcons.API))
                }
                completionResultSet.addElement(LookupElementBuilder.create("Security").withIcon(LuaIcons.API).withTypeText("设置API安全签名"))

                completionResultSet.addElement(LookupElementBuilder.create("validate \"required,enums=mobile email,min=1,max=10,pattern=[\\d]+\"").withIcon(LuaIcons.API).withTypeText("设置字段：required,min,enums,max,正则"))
                completionResultSet.addElement(LookupElementBuilder.create("validate \"required\"").withIcon(LuaIcons.API).withTypeText("设置字段是否必传"))
                completionResultSet.addElement(LookupElementBuilder.create("validate \"required,pattern=[\\d]+\"").withIcon(LuaIcons.API).withTypeText("设置字段正则"))
                completionResultSet.addElement(LookupElementBuilder.create("validate \"required,enums=mobile email\"").withIcon(LuaIcons.API).withTypeText("设置字段枚举"))
                completionResultSet.stopHere()
            }
        })

        extend(CompletionType.BASIC, SHOW_OPTIONAL, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                completionResultSet.addElement(LookupElementBuilder.create("optional"))
            }
        })

        extend(CompletionType.BASIC, AFTER_PARAM, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                var element = completionParameters.originalFile.findElementAt(completionParameters.offset - 1)
                if (element != null && element !is LuaDocPsiElement)
                    element = element.parent

                if (element is LuaDocPsiElement) {
                    val owner = LuaCommentUtil.findOwner(element)
                    if (owner is LuaFuncBodyOwner) {
                        val body = owner.funcBody
                        if (body != null) {
                            val parDefList = body.paramNameDefList
                            for (def in parDefList) {
                                completionResultSet.addElement(
                                        LookupElementBuilder.create(def.text)
                                                .withIcon(LuaIcons.PARAMETER)
                                )
                            }
                        }
                    }
                }
            }
        })

        extend(CompletionType.BASIC, SHOW_CLASS, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                val project = completionParameters.position.project
                LuaShortNamesManager.getInstance(project).processClassNames(project, Processor{
                    completionResultSet.addElement(LookupElementBuilder.create(it).withIcon(LuaIcons.CLASS))
                    true
                })

                LuaShortNamesManager.getInstance(project).processAllAlias(project, Processor { key ->
                    completionResultSet.addElement(LookupElementBuilder.create(key).withIcon(LuaIcons.Alias))
                    true
                })
                completionResultSet.stopHere()
            }
        })

        extend(CompletionType.BASIC, SHOW_ACCESS_MODIFIER, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                completionResultSet.addElement(LookupElementBuilder.create("protected"))
                completionResultSet.addElement(LookupElementBuilder.create("public"))
            }
        })

        // 属性提示
        extend(CompletionType.BASIC, SHOW_FIELD, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                val position = completionParameters.position
                val comment = PsiTreeUtil.getParentOfType(position, LuaComment::class.java)
                val classDef = PsiTreeUtil.findChildOfType(comment, LuaDocTagClass::class.java)
                if (classDef != null) {
                    val classType = classDef.type
                    val ctx = SearchContext.get(classDef.project)
                    classType.processMembers(ctx) { _, member ->
                        if (member is LuaClassField)
                            completionResultSet.addElement(LookupElementBuilder.create(member.name!!).withIcon(LuaIcons.CLASS_FIELD))
                        Unit
                    }
                }
            }
        })

        // @see member completion
        extend(CompletionType.BASIC, SHOW_SEE_MEMBER, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                val position = completionParameters.position
                val seeRefTag = PsiTreeUtil.getParentOfType(position, LuaDocTagSee::class.java)
                if (seeRefTag != null) {
                    val classType = seeRefTag.classNameRef?.resolveType() as? ITyClass
                    val ctx = SearchContext.get(seeRefTag.project)
                    classType?.processMembers(ctx) { _, member ->
                        completionResultSet.addElement(LookupElementBuilder.create(member.name!!).withIcon(LuaIcons.CLASS_FIELD))
                        Unit
                    }
                }
                completionResultSet.stopHere()
            }
        })

        extend(CompletionType.BASIC, SHOW_LAN, object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(completionParameters: CompletionParameters, processingContext: ProcessingContext, completionResultSet: CompletionResultSet) {
                Language.getRegisteredLanguages().forEach {
                    val fileType = it.associatedFileType
                    val id = "\"${it.id}\""
                    var lookupElement = LookupElementBuilder.create(id)
                    if (fileType != null)
                        lookupElement = lookupElement.withIcon(fileType.icon)
                    completionResultSet.addElement(lookupElement)
                }
                completionResultSet.stopHere()
            }
        })
    }

    companion object {

        // 在 @ 之后提示 param class type ...
        private val SHOW_DOC_TAG = psiElement(LuaDocTypes.TAG_NAME)

        // 在 @param 之后提示方法的参数
        private val AFTER_PARAM = psiElement().withParent(LuaDocParamNameRef::class.java)

        // 在 @param 之后提示 optional
        private val SHOW_OPTIONAL = psiElement().afterLeaf(
                psiElement(LuaDocTypes.TAG_NAME_PARAM))

        // 在 extends 之后提示类型
        private val SHOW_CLASS = psiElement().withParent(LuaDocClassNameRef::class.java)

        // 在 @field 之后提示 public / protected
        private val SHOW_ACCESS_MODIFIER = psiElement().afterLeaf(
                psiElement().withElementType(LuaDocTypes.TAG_NAME_FIELD)
        )

        private val SHOW_FIELD = psiElement(LuaDocTypes.ID).inside(LuaDocTagField::class.java)

        //@see type#MEMBER
        private val SHOW_SEE_MEMBER = psiElement(LuaDocTypes.ID).inside(LuaDocTagSee::class.java)

        private val SHOW_LAN = psiElement(LuaDocTypes.ID).inside(LuaDocTagLan::class.java)

        private val ADDITIONAL_TAGS = arrayOf("deprecated", "author", "version", "since")

        private val SWAG_ROUTER_TAGS = arrayOf(
            "Router /api/login [GET]",
            "Response  200    {object}   ret:{errcode}  \"错误码\"  [@validate:\"required,enums=0 101 105\"]",
            "Param    token    query    string    true   \"登录token\"",
            "Summary 接口名",
            "Tags 接口所属标签目录",
            "Description 接口备注",
            "Header          406       {string}               ERROR-MSG       \"错误信息\"",
        )

        private val SWAG_FIELD_TAGS = arrayOf(
            "example \"字段实例值\"",
        )
    }
}
