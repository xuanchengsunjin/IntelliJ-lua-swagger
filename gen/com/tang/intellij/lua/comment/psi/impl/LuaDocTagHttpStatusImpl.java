// This is a generated file. Not intended for manual editing.
package com.tang.intellij.lua.comment.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.tang.intellij.lua.comment.psi.LuaDocTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.tang.intellij.lua.comment.psi.*;

public class LuaDocTagHttpStatusImpl extends ASTWrapperPsiElement implements LuaDocTagHttpStatus {

  public LuaDocTagHttpStatusImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull LuaDocVisitor visitor) {
    visitor.visitTagHttpStatus(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof LuaDocVisitor) accept((LuaDocVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getSwagHttpstatus() {
    return notNullChild(findChildByType(SWAG_HTTPSTATUS));
  }

}
