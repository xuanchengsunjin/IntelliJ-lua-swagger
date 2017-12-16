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

package com.tang.intellij.lua.stubs

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.util.io.StringRef
import com.tang.intellij.lua.Constants
import com.tang.intellij.lua.psi.*
import com.tang.intellij.lua.psi.impl.LuaNameExprImpl
import com.tang.intellij.lua.search.SearchContext
import com.tang.intellij.lua.stubs.index.LuaClassMemberIndex
import com.tang.intellij.lua.stubs.index.LuaGlobalIndex
import com.tang.intellij.lua.stubs.index.LuaShortNameIndex
import com.tang.intellij.lua.ty.ITy
import com.tang.intellij.lua.ty.Ty

/**
 * name expr stub
 * Created by TangZX on 2017/4/12.
 */
class LuaNameExprType : LuaStubElementType<LuaNameExprStub, LuaNameExpr>("NAME_EXPR") {

    override fun createPsi(luaNameStub: LuaNameExprStub) = LuaNameExprImpl(luaNameStub, this)

    override fun shouldCreateStub(node: ASTNode): Boolean {
        return createStubIfParentIsStub(node)
    }

    override fun createStub(luaNameExpr: LuaNameExpr, stubElement: StubElement<*>): LuaNameExprStub {
        val psiFile = luaNameExpr.containingFile
        val name = luaNameExpr.name
        val module = if (psiFile is LuaPsiFile) psiFile.moduleName ?: Constants.WORD_G else Constants.WORD_G
        val isGlobal = resolveLocal(luaNameExpr, SearchContext(luaNameExpr.project, psiFile)) == null

        val stat = luaNameExpr.assignStat
        val docTy = stat?.comment?.ty

        return LuaNameExprStubImpl(name,
                module,
                stat != null,
                isGlobal,
                docTy,
                stubElement,
                this)
    }

    override fun serialize(luaNameStub: LuaNameExprStub, stubOutputStream: StubOutputStream) {
        stubOutputStream.writeName(luaNameStub.name)
        stubOutputStream.writeName(luaNameStub.module)
        stubOutputStream.writeBoolean(luaNameStub.isName)
        stubOutputStream.writeBoolean(luaNameStub.isGlobal)
        Ty.serializeNullable(luaNameStub.docTy, stubOutputStream)
    }

    override fun deserialize(stubInputStream: StubInputStream, stubElement: StubElement<*>): LuaNameExprStub {
        val nameRef = stubInputStream.readName()
        val moduleRef = stubInputStream.readName()
        val isName = stubInputStream.readBoolean()
        val isGlobal = stubInputStream.readBoolean()
        val docTy = Ty.deserializeNullable(stubInputStream)
        return LuaNameExprStubImpl(StringRef.toString(nameRef),
                StringRef.toString(moduleRef),
                isGlobal,
                isName,
                docTy,
                stubElement,
                this)
    }

    override fun indexStub(luaNameStub: LuaNameExprStub, indexSink: IndexSink) {
        if (luaNameStub.isGlobal &&luaNameStub.isName) {
            val module = luaNameStub.module

            LuaClassMemberIndex.indexStub(indexSink, module, luaNameStub.name)

            if (module == Constants.WORD_G)
                indexSink.occurrence(LuaGlobalIndex.KEY, luaNameStub.name)
            indexSink.occurrence(LuaShortNameIndex.KEY, luaNameStub.name)
        }
    }
}

interface LuaNameExprStub : LuaExprStub<LuaNameExpr>, LuaDocTyStub {
    val name: String
    val module: String
    val isName: Boolean
    val isGlobal: Boolean
}

class LuaNameExprStubImpl(
        override val name: String,
        override val module: String,
        override val isName: Boolean,
        override val isGlobal: Boolean,
        override val docTy: ITy?,
        parent: StubElement<*>,
        elementType: LuaStubElementType<*, *>
) : LuaStubBase<LuaNameExpr>(parent, elementType), LuaNameExprStub