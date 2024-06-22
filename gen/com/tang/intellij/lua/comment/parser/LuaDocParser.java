// This is a generated file. Not intended for manual editing.
package com.tang.intellij.lua.comment.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.tang.intellij.lua.comment.psi.LuaDocTypes.*;
import static com.tang.intellij.lua.psi.LuaParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class LuaDocParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return doc(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ARR_TY, FUNCTION_TY, GENERAL_TY, GENERIC_TY,
      PAR_TY, STRING_LITERAL_TY, TABLE_TY, TY,
      UNION_TY),
  };

  /* ********************************************************** */
  // PRIVATE | PUBLIC | PROTECTED | TAG_NAME_PRIVATE | TAG_NAME_PUBLIC | TAG_NAME_PROTECTED
  public static boolean access_modifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "access_modifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ACCESS_MODIFIER, "<access modifier>");
    r = consumeToken(b, PRIVATE);
    if (!r) r = consumeToken(b, PUBLIC);
    if (!r) r = consumeToken(b, PROTECTED);
    if (!r) r = consumeToken(b, TAG_NAME_PRIVATE);
    if (!r) r = consumeToken(b, TAG_NAME_PUBLIC);
    if (!r) r = consumeToken(b, TAG_NAME_PROTECTED);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // doc_item | STRING
  static boolean after_dash(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "after_dash")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = doc_item(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    exit_section_(b, l, m, r, false, LuaDocParser::after_dash_recover);
    return r;
  }

  /* ********************************************************** */
  // !(DASHES)
  static boolean after_dash_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "after_dash_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, DASHES);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean class_name_ref(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "class_name_ref")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, CLASS_NAME_REF, r);
    return r;
  }

  /* ********************************************************** */
  // STRING_BEGIN? STRING?
  public static boolean comment_string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_string")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMENT_STRING, "<comment string>");
    r = comment_string_0(b, l + 1);
    r = r && comment_string_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // STRING_BEGIN?
  private static boolean comment_string_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_string_0")) return false;
    consumeToken(b, STRING_BEGIN);
    return true;
  }

  // STRING?
  private static boolean comment_string_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_string_1")) return false;
    consumeToken(b, STRING);
    return true;
  }

  /* ********************************************************** */
  // (DASHES after_dash?)*
  static boolean doc(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc")) return false;
    while (true) {
      int c = current_position_(b);
      if (!doc_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "doc", c)) break;
    }
    return true;
  }

  // DASHES after_dash?
  private static boolean doc_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DASHES);
    r = r && doc_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // after_dash?
  private static boolean doc_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_0_1")) return false;
    after_dash(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '@' (tag_param
  //     | tag_alias
  //     | tag_suppress
  //     | tag_vararg
  //     | tag_return
  //     | tag_class
  //     | tag_field
  //     | tag_type
  //     | tag_lan
  //     | tag_overload
  //     | tag_see
  //     | tag_def
  //     | access_modifier
  //     | tag_generic_list
  //     | tag_swag_tags
  //     | tag_swag_summary
  //     | tag_swag_params
  //     | tag_swag_router
  //     | tag_swag_des
  //     | tag_swag_sign
  //     | tag_swag_response
  //     | tag_swag_header
  //     | tag_swag_sign_api
  //     | tag_swag_sign_name
  //     | tag_swag_sign_in
  //     | tag_swag_server
  //     | tag_swag_contact)
  static boolean doc_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_item")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AT);
    r = r && doc_item_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // tag_param
  //     | tag_alias
  //     | tag_suppress
  //     | tag_vararg
  //     | tag_return
  //     | tag_class
  //     | tag_field
  //     | tag_type
  //     | tag_lan
  //     | tag_overload
  //     | tag_see
  //     | tag_def
  //     | access_modifier
  //     | tag_generic_list
  //     | tag_swag_tags
  //     | tag_swag_summary
  //     | tag_swag_params
  //     | tag_swag_router
  //     | tag_swag_des
  //     | tag_swag_sign
  //     | tag_swag_response
  //     | tag_swag_header
  //     | tag_swag_sign_api
  //     | tag_swag_sign_name
  //     | tag_swag_sign_in
  //     | tag_swag_server
  //     | tag_swag_contact
  private static boolean doc_item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_item_1")) return false;
    boolean r;
    r = tag_param(b, l + 1);
    if (!r) r = tag_alias(b, l + 1);
    if (!r) r = tag_suppress(b, l + 1);
    if (!r) r = tag_vararg(b, l + 1);
    if (!r) r = tag_return(b, l + 1);
    if (!r) r = tag_class(b, l + 1);
    if (!r) r = tag_field(b, l + 1);
    if (!r) r = tag_type(b, l + 1);
    if (!r) r = tag_lan(b, l + 1);
    if (!r) r = tag_overload(b, l + 1);
    if (!r) r = tag_see(b, l + 1);
    if (!r) r = tag_def(b, l + 1);
    if (!r) r = access_modifier(b, l + 1);
    if (!r) r = tag_generic_list(b, l + 1);
    if (!r) r = tag_swag_tags(b, l + 1);
    if (!r) r = tag_swag_summary(b, l + 1);
    if (!r) r = tag_swag_params(b, l + 1);
    if (!r) r = tag_swag_router(b, l + 1);
    if (!r) r = tag_swag_des(b, l + 1);
    if (!r) r = tag_swag_sign(b, l + 1);
    if (!r) r = tag_swag_response(b, l + 1);
    if (!r) r = tag_swag_header(b, l + 1);
    if (!r) r = tag_swag_sign_api(b, l + 1);
    if (!r) r = tag_swag_sign_name(b, l + 1);
    if (!r) r = tag_swag_sign_in(b, l + 1);
    if (!r) r = tag_swag_server(b, l + 1);
    if (!r) r = tag_swag_contact(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (tableField (',' tableField)* (',')?)?
  static boolean fieldList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldList")) return false;
    fieldList_0(b, l + 1);
    return true;
  }

  // tableField (',' tableField)* (',')?
  private static boolean fieldList_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldList_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tableField(b, l + 1);
    r = r && fieldList_0_1(b, l + 1);
    r = r && fieldList_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' tableField)*
  private static boolean fieldList_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldList_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!fieldList_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "fieldList_0_1", c)) break;
    }
    return true;
  }

  // ',' tableField
  private static boolean fieldList_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldList_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && tableField(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',')?
  private static boolean fieldList_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldList_0_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // '<' generic_def (',' generic_def)* '>'
  static boolean function_generic(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_generic")) return false;
    if (!nextTokenIs(b, LT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, LT);
    p = r; // pin = 1
    r = r && report_error_(b, generic_def(b, l + 1));
    r = p && report_error_(b, function_generic_2(b, l + 1)) && r;
    r = p && consumeToken(b, GT) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' generic_def)*
  private static boolean function_generic_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_generic_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!function_generic_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_generic_2", c)) break;
    }
    return true;
  }

  // ',' generic_def
  private static boolean function_generic_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_generic_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && generic_def(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID (':' ty)?
  public static boolean function_param(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_PARAM, null);
    r = consumeToken(b, ID);
    p = r; // pin = 1
    r = r && function_param_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (':' ty)?
  private static boolean function_param_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_1")) return false;
    function_param_1_0(b, l + 1);
    return true;
  }

  // ':' ty
  private static boolean function_param_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && ty(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (function_param ',')* ((function_param|vararg_param) |& ')')
  static boolean function_param_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_param_list_0(b, l + 1);
    r = r && function_param_list_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (function_param ',')*
  private static boolean function_param_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_list_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!function_param_list_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_param_list_0", c)) break;
    }
    return true;
  }

  // function_param ','
  private static boolean function_param_list_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_list_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_param(b, l + 1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // (function_param|vararg_param) |& ')'
  private static boolean function_param_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_list_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = function_param_list_1_0(b, l + 1);
    if (!r) r = function_param_list_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // function_param|vararg_param
  private static boolean function_param_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_list_1_0")) return false;
    boolean r;
    r = function_param(b, l + 1);
    if (!r) r = vararg_param(b, l + 1);
    return r;
  }

  // & ')'
  private static boolean function_param_list_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_param_list_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RPAREN);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID (EXTENDS class_name_ref)?
  public static boolean generic_def(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_def")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, GENERIC_DEF, null);
    r = consumeToken(b, ID);
    p = r; // pin = 1
    r = r && generic_def_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (EXTENDS class_name_ref)?
  private static boolean generic_def_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_def_1")) return false;
    generic_def_1_0(b, l + 1);
    return true;
  }

  // EXTENDS class_name_ref
  private static boolean generic_def_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_def_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && class_name_ref(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (ty ',')* ty
  static boolean generic_param_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_param_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = generic_param_list_0(b, l + 1);
    r = r && ty(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (ty ',')*
  private static boolean generic_param_list_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_param_list_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!generic_param_list_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "generic_param_list_0", c)) break;
    }
    return true;
  }

  // ty ','
  private static boolean generic_param_list_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_param_list_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ty(b, l + 1, -1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // HTTP_METHOD_SWAG
  public static boolean http_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_method")) return false;
    if (!nextTokenIs(b, HTTP_METHOD_SWAG)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HTTP_METHOD_SWAG);
    exit_section_(b, m, HTTP_METHOD, r);
    return r;
  }

  /* ********************************************************** */
  // ID | STRING_LITERAL
  public static boolean lan_id(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "lan_id")) return false;
    if (!nextTokenIs(b, "<lan id>", ID, STRING_LITERAL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LAN_ID, "<lan id>");
    r = consumeToken(b, ID);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean param_name_ref(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "param_name_ref")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, PARAM_NAME_REF, r);
    return r;
  }

  /* ********************************************************** */
  // SWAGGER_PARAM_NME
  public static boolean swag_param_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "swag_param_name")) return false;
    if (!nextTokenIs(b, SWAGGER_PARAM_NME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWAGGER_PARAM_NME);
    exit_section_(b, m, SWAG_PARAM_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // SWAGGER_SIGN_NME
  public static boolean swag_sign(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "swag_sign")) return false;
    if (!nextTokenIs(b, SWAGGER_SIGN_NME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWAGGER_SIGN_NME);
    exit_section_(b, m, SWAG_SIGN, r);
    return r;
  }

  /* ********************************************************** */
  // tableField2
  public static boolean tableField(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tableField")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tableField2(b, l + 1);
    exit_section_(b, m, TABLE_FIELD, r);
    return r;
  }

  /* ********************************************************** */
  // ID ':' ty
  static boolean tableField2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tableField2")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeTokens(b, 1, ID, EXTENDS);
    p = r; // pin = 1
    r = r && ty(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '{' fieldList '}'
  public static boolean table_def(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_def")) return false;
    if (!nextTokenIs(b, LCURLY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TABLE_DEF, null);
    r = consumeToken(b, LCURLY);
    p = r; // pin = 1
    r = r && report_error_(b, fieldList(b, l + 1));
    r = p && consumeToken(b, RCURLY) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_ALIAS ID ty
  public static boolean tag_alias(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_alias")) return false;
    if (!nextTokenIs(b, TAG_NAME_ALIAS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_ALIAS, null);
    r = consumeTokens(b, 1, TAG_NAME_ALIAS, ID);
    p = r; // pin = 1
    r = r && ty(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (TAG_NAME_CLASS|TAG_NAME_MODULE|TAG_NAME_ENUM) ID (EXTENDS class_name_ref)? comment_string?
  public static boolean tag_class(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_class")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_CLASS, "<tag class>");
    r = tag_class_0(b, l + 1);
    r = r && consumeToken(b, ID);
    p = r; // pin = 2
    r = r && report_error_(b, tag_class_2(b, l + 1));
    r = p && tag_class_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // TAG_NAME_CLASS|TAG_NAME_MODULE|TAG_NAME_ENUM
  private static boolean tag_class_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_class_0")) return false;
    boolean r;
    r = consumeToken(b, TAG_NAME_CLASS);
    if (!r) r = consumeToken(b, TAG_NAME_MODULE);
    if (!r) r = consumeToken(b, TAG_NAME_ENUM);
    return r;
  }

  // (EXTENDS class_name_ref)?
  private static boolean tag_class_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_class_2")) return false;
    tag_class_2_0(b, l + 1);
    return true;
  }

  // EXTENDS class_name_ref
  private static boolean tag_class_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_class_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EXTENDS);
    r = r && class_name_ref(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // comment_string?
  private static boolean tag_class_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_class_3")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_NAME comment_string?
  public static boolean tag_def(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_def")) return false;
    if (!nextTokenIs(b, TAG_NAME_NAME)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_DEF, null);
    r = consumeToken(b, TAG_NAME_NAME);
    p = r; // pin = 1
    r = r && tag_def_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // comment_string?
  private static boolean tag_def_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_def_1")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_FIELD access_modifier? ('<' class_name_ref '>')? ID ty comment_string?
  public static boolean tag_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_field")) return false;
    if (!nextTokenIs(b, TAG_NAME_FIELD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_FIELD, null);
    r = consumeToken(b, TAG_NAME_FIELD);
    p = r; // pin = 1
    r = r && report_error_(b, tag_field_1(b, l + 1));
    r = p && report_error_(b, tag_field_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, ID)) && r;
    r = p && report_error_(b, ty(b, l + 1, -1)) && r;
    r = p && tag_field_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // access_modifier?
  private static boolean tag_field_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_field_1")) return false;
    access_modifier(b, l + 1);
    return true;
  }

  // ('<' class_name_ref '>')?
  private static boolean tag_field_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_field_2")) return false;
    tag_field_2_0(b, l + 1);
    return true;
  }

  // '<' class_name_ref '>'
  private static boolean tag_field_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_field_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && class_name_ref(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // comment_string?
  private static boolean tag_field_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_field_5")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_GENERIC generic_def (',' generic_def)*
  public static boolean tag_generic_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_generic_list")) return false;
    if (!nextTokenIs(b, TAG_NAME_GENERIC)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_GENERIC_LIST, null);
    r = consumeToken(b, TAG_NAME_GENERIC);
    p = r; // pin = 1
    r = r && report_error_(b, generic_def(b, l + 1));
    r = p && tag_generic_list_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' generic_def)*
  private static boolean tag_generic_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_generic_list_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tag_generic_list_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_generic_list_2", c)) break;
    }
    return true;
  }

  // ',' generic_def
  private static boolean tag_generic_list_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_generic_list_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && generic_def(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SWAG_HTTPSTATUS
  public static boolean tag_http_status(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_http_status")) return false;
    if (!nextTokenIs(b, SWAG_HTTPSTATUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWAG_HTTPSTATUS);
    exit_section_(b, m, TAG_HTTP_STATUS, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_LANGUAGE lan_id comment_string?
  public static boolean tag_lan(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_lan")) return false;
    if (!nextTokenIs(b, TAG_NAME_LANGUAGE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_LAN, null);
    r = consumeToken(b, TAG_NAME_LANGUAGE);
    p = r; // pin = 1
    r = r && report_error_(b, lan_id(b, l + 1));
    r = p && tag_lan_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // comment_string?
  private static boolean tag_lan_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_lan_2")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_OVERLOAD function_ty
  public static boolean tag_overload(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_overload")) return false;
    if (!nextTokenIs(b, TAG_NAME_OVERLOAD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_OVERLOAD, null);
    r = consumeToken(b, TAG_NAME_OVERLOAD);
    p = r; // pin = 1
    r = r && function_ty(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_PARAM param_name_ref ty comment_string?
  public static boolean tag_param(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_param")) return false;
    if (!nextTokenIs(b, TAG_NAME_PARAM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_PARAM, null);
    r = consumeToken(b, TAG_NAME_PARAM);
    p = r; // pin = 1
    r = r && report_error_(b, param_name_ref(b, l + 1));
    r = p && report_error_(b, ty(b, l + 1, -1)) && r;
    r = p && tag_param_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // comment_string?
  private static boolean tag_param_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_param_3")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_RETURN type_list comment_string?
  public static boolean tag_return(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_return")) return false;
    if (!nextTokenIs(b, TAG_NAME_RETURN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_RETURN, null);
    r = consumeToken(b, TAG_NAME_RETURN);
    p = r; // pin = 1
    r = r && report_error_(b, type_list(b, l + 1));
    r = p && tag_return_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // comment_string?
  private static boolean tag_return_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_return_2")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_SEE class_name_ref (SHARP ID)?
  public static boolean tag_see(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_see")) return false;
    if (!nextTokenIs(b, TAG_NAME_SEE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SEE, null);
    r = consumeToken(b, TAG_NAME_SEE);
    p = r; // pin = 1
    r = r && report_error_(b, class_name_ref(b, l + 1));
    r = p && tag_see_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (SHARP ID)?
  private static boolean tag_see_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_see_2")) return false;
    tag_see_2_0(b, l + 1);
    return true;
  }

  // SHARP ID
  private static boolean tag_see_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_see_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SHARP, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SUPPRESS ID (',' ID)*
  public static boolean tag_suppress(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_suppress")) return false;
    if (!nextTokenIs(b, TAG_NAME_SUPPRESS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SUPPRESS, null);
    r = consumeTokens(b, 1, TAG_NAME_SUPPRESS, ID);
    p = r; // pin = 1
    r = r && tag_suppress_2(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (',' ID)*
  private static boolean tag_suppress_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_suppress_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!tag_suppress_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_suppress_2", c)) break;
    }
    return true;
  }

  // ',' ID
  private static boolean tag_suppress_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_suppress_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, ID);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAG_CONTACT_EMAIL | TAG_NAME_SWAG_CONTACT_NAME | TAG_NAME_SWAG_CONTACT_URL comment_string
  public static boolean tag_swag_contact(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_contact")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_CONTACT, "<tag swag contact>");
    r = consumeToken(b, TAG_NAME_SWAG_CONTACT_EMAIL);
    if (!r) r = consumeToken(b, TAG_NAME_SWAG_CONTACT_NAME);
    if (!r) r = tag_swag_contact_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // TAG_NAME_SWAG_CONTACT_URL comment_string
  private static boolean tag_swag_contact_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_contact_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_NAME_SWAG_CONTACT_URL);
    r = r && comment_string(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAGDES comment_string
  public static boolean tag_swag_des(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_des")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAGDES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_DES, null);
    r = consumeToken(b, TAG_NAME_SWAGDES);
    p = r; // pin = 1
    r = r && comment_string(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_HEADER tag_http_status ("{")? ty ("}")? comment_string
  public static boolean tag_swag_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_header")) return false;
    if (!nextTokenIs(b, TAG_NAME_HEADER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_HEADER, null);
    r = consumeToken(b, TAG_NAME_HEADER);
    p = r; // pin = 1
    r = r && report_error_(b, tag_http_status(b, l + 1));
    r = p && report_error_(b, tag_swag_header_2(b, l + 1)) && r;
    r = p && report_error_(b, ty(b, l + 1, -1)) && r;
    r = p && report_error_(b, tag_swag_header_4(b, l + 1)) && r;
    r = p && comment_string(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ("{")?
  private static boolean tag_swag_header_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_header_2")) return false;
    consumeToken(b, LCURLY);
    return true;
  }

  // ("}")?
  private static boolean tag_swag_header_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_header_4")) return false;
    consumeToken(b, RCURLY);
    return true;
  }

  /* ********************************************************** */
  // '[' ID ']'
  public static boolean tag_swag_httpmethod(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_httpmethod")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_HTTPMETHOD, "<tag swag httpmethod>");
    r = consumeToken(b, "[");
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, ID));
    r = p && consumeToken(b, "]") && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // tag_swag_note|tag_swag_obj
  public static boolean tag_swag_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_line")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_LINE, "<tag swag line>");
    r = tag_swag_note(b, l + 1);
    if (!r) r = tag_swag_obj(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ("\"") comment_string ("\"")
  public static boolean tag_swag_note(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_note")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_NOTE, "<tag swag note>");
    r = tag_swag_note_0(b, l + 1);
    r = r && comment_string(b, l + 1);
    r = r && tag_swag_note_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ("\"")
  private static boolean tag_swag_note_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_note_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\"");
    exit_section_(b, m, null, r);
    return r;
  }

  // ("\"")
  private static boolean tag_swag_note_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_note_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "\"");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ("{")? SWAGRES_TYPE_OBJ? ("}")? SWAG_RES_KEY? ("{")? ty? ("}")?
  public static boolean tag_swag_obj(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_OBJ, "<tag swag obj>");
    r = tag_swag_obj_0(b, l + 1);
    r = r && tag_swag_obj_1(b, l + 1);
    r = r && tag_swag_obj_2(b, l + 1);
    r = r && tag_swag_obj_3(b, l + 1);
    r = r && tag_swag_obj_4(b, l + 1);
    r = r && tag_swag_obj_5(b, l + 1);
    r = r && tag_swag_obj_6(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ("{")?
  private static boolean tag_swag_obj_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_0")) return false;
    consumeToken(b, LCURLY);
    return true;
  }

  // SWAGRES_TYPE_OBJ?
  private static boolean tag_swag_obj_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_1")) return false;
    consumeToken(b, SWAGRES_TYPE_OBJ);
    return true;
  }

  // ("}")?
  private static boolean tag_swag_obj_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_2")) return false;
    consumeToken(b, RCURLY);
    return true;
  }

  // SWAG_RES_KEY?
  private static boolean tag_swag_obj_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_3")) return false;
    consumeToken(b, SWAG_RES_KEY);
    return true;
  }

  // ("{")?
  private static boolean tag_swag_obj_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_4")) return false;
    consumeToken(b, LCURLY);
    return true;
  }

  // ty?
  private static boolean tag_swag_obj_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_5")) return false;
    ty(b, l + 1, -1);
    return true;
  }

  // ("}")?
  private static boolean tag_swag_obj_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_obj_6")) return false;
    consumeToken(b, RCURLY);
    return true;
  }

  /* ********************************************************** */
  // 'query' | 'header'
  public static boolean tag_swag_param_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_param_type")) return false;
    if (!nextTokenIs(b, "<tag swag param type>", SWAGPARAM_HEADER, SWAGPARAM_QUERY)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_PARAM_TYPE, "<tag swag param type>");
    r = consumeToken(b, SWAGPARAM_QUERY);
    if (!r) r = consumeToken(b, SWAGPARAM_HEADER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAGPARAM swag_param_name tag_swag_query_type ("{")? ty ("}")? comment_string
  public static boolean tag_swag_params(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_params")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAGPARAM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_PARAMS, null);
    r = consumeToken(b, TAG_NAME_SWAGPARAM);
    p = r; // pin = 1
    r = r && report_error_(b, swag_param_name(b, l + 1));
    r = p && report_error_(b, tag_swag_query_type(b, l + 1)) && r;
    r = p && report_error_(b, tag_swag_params_3(b, l + 1)) && r;
    r = p && report_error_(b, ty(b, l + 1, -1)) && r;
    r = p && report_error_(b, tag_swag_params_5(b, l + 1)) && r;
    r = p && comment_string(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ("{")?
  private static boolean tag_swag_params_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_params_3")) return false;
    consumeToken(b, LCURLY);
    return true;
  }

  // ("}")?
  private static boolean tag_swag_params_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_params_5")) return false;
    consumeToken(b, RCURLY);
    return true;
  }

  /* ********************************************************** */
  // SWAGPARAM_QUERY | SWAGPARAM_BODY | SWAGPARAM_FORM | SWAGPARAM_PATH | SWAGPARAM_HEADER {
  // //   methods = [getReference]
  // //   implements = ['com.tang.intellij.lua.comment.psi.LuaDocType']
  // }
  public static boolean tag_swag_query_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_query_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_QUERY_TYPE, "<tag swag query type>");
    r = consumeToken(b, SWAGPARAM_QUERY);
    if (!r) r = consumeToken(b, SWAGPARAM_BODY);
    if (!r) r = consumeToken(b, SWAGPARAM_FORM);
    if (!r) r = consumeToken(b, SWAGPARAM_PATH);
    if (!r) r = tag_swag_query_type_4(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // SWAGPARAM_HEADER {
  // //   methods = [getReference]
  // //   implements = ['com.tang.intellij.lua.comment.psi.LuaDocType']
  // }
  private static boolean tag_swag_query_type_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_query_type_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWAGPARAM_HEADER);
    r = r && tag_swag_query_type_4_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // {
  // //   methods = [getReference]
  // //   implements = ['com.tang.intellij.lua.comment.psi.LuaDocType']
  // }
  private static boolean tag_swag_query_type_4_1(PsiBuilder b, int l) {
    return true;
  }

  /* ********************************************************** */
  // SWAG_RES_KEY
  public static boolean tag_swag_res_key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_res_key")) return false;
    if (!nextTokenIs(b, SWAG_RES_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWAG_RES_KEY);
    exit_section_(b, m, TAG_SWAG_RES_KEY, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAGRES tag_http_status tag_swag_line comment_string
  public static boolean tag_swag_response(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_response")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAGRES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_RESPONSE, null);
    r = consumeToken(b, TAG_NAME_SWAGRES);
    p = r; // pin = 1
    r = r && report_error_(b, tag_http_status(b, l + 1));
    r = p && report_error_(b, tag_swag_line(b, l + 1)) && r;
    r = p && comment_string(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAGROUTER URL http_method
  public static boolean tag_swag_router(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_router")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAGROUTER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TAG_NAME_SWAGROUTER, URL);
    r = r && http_method(b, l + 1);
    exit_section_(b, m, TAG_SWAG_ROUTER, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SERVER HTTPURL comment_string
  public static boolean tag_swag_server(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_server")) return false;
    if (!nextTokenIs(b, TAG_NAME_SERVER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TAG_NAME_SERVER, HTTPURL);
    r = r && comment_string(b, l + 1);
    exit_section_(b, m, TAG_SWAG_SERVER, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SIGN swag_sign
  public static boolean tag_swag_sign(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_sign")) return false;
    if (!nextTokenIs(b, TAG_NAME_SIGN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_SIGN, null);
    r = consumeToken(b, TAG_NAME_SIGN);
    p = r; // pin = 1
    r = r && swag_sign(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAG_SIGN_API swag_sign
  public static boolean tag_swag_sign_api(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_sign_api")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAG_SIGN_API)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_NAME_SWAG_SIGN_API);
    r = r && swag_sign(b, l + 1);
    exit_section_(b, m, TAG_SWAG_SIGN_API, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAG_SIGN_IN tag_swag_query_type
  public static boolean tag_swag_sign_in(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_sign_in")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAG_SIGN_IN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_NAME_SWAG_SIGN_IN);
    r = r && tag_swag_query_type(b, l + 1);
    exit_section_(b, m, TAG_SWAG_SIGN_IN, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAG_SIGN_NAME swag_param_name
  public static boolean tag_swag_sign_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_sign_name")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAG_SIGN_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TAG_NAME_SWAG_SIGN_NAME);
    r = r && swag_param_name(b, l + 1);
    exit_section_(b, m, TAG_SWAG_SIGN_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // SWAG_SPACE
  public static boolean tag_swag_space(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_space")) return false;
    if (!nextTokenIs(b, SWAG_SPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SWAG_SPACE);
    exit_section_(b, m, TAG_SWAG_SPACE, r);
    return r;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAGSUMMARY comment_string
  public static boolean tag_swag_summary(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_summary")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAGSUMMARY)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_SUMMARY, null);
    r = consumeToken(b, TAG_NAME_SWAGSUMMARY);
    p = r; // pin = 1
    r = r && comment_string(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_SWAGTAGS comment_string
  public static boolean tag_swag_tags(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_swag_tags")) return false;
    if (!nextTokenIs(b, TAG_NAME_SWAGTAGS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_SWAG_TAGS, null);
    r = consumeToken(b, TAG_NAME_SWAGTAGS);
    p = r; // pin = 1
    r = r && comment_string(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TAG_NAME_TYPE ty comment_string?
  public static boolean tag_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_type")) return false;
    if (!nextTokenIs(b, TAG_NAME_TYPE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_TYPE, null);
    r = consumeToken(b, TAG_NAME_TYPE);
    p = r; // pin = 1
    r = r && report_error_(b, ty(b, l + 1, -1));
    r = p && tag_type_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // comment_string?
  private static boolean tag_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_type_2")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TAG_NAME_VARARG ty comment_string?
  public static boolean tag_vararg(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_vararg")) return false;
    if (!nextTokenIs(b, TAG_NAME_VARARG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, TAG_VARARG, null);
    r = consumeToken(b, TAG_NAME_VARARG);
    p = r; // pin = 1
    r = r && report_error_(b, ty(b, l + 1, -1));
    r = p && tag_vararg_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // comment_string?
  private static boolean tag_vararg_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_vararg_2")) return false;
    comment_string(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ty(',' ty)*
  public static boolean type_list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TYPE_LIST, "<type list>");
    r = ty(b, l + 1, -1);
    r = r && type_list_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (',' ty)*
  private static boolean type_list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!type_list_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "type_list_1", c)) break;
    }
    return true;
  }

  // ',' ty
  private static boolean type_list_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && ty(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // VARARG ty
  public static boolean vararg_param(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "vararg_param")) return false;
    if (!nextTokenIs(b, VARARG)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, VARARG_PARAM, null);
    r = consumeToken(b, VARARG);
    p = r; // pin = 1
    r = r && ty(b, l + 1, -1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // Expression root: ty
  // Operator priority table:
  // 0: N_ARY(union_ty)
  // 1: ATOM(function_ty)
  // 2: ATOM(table_ty)
  // 3: POSTFIX(generic_ty)
  // 4: POSTFIX(arr_ty)
  // 5: ATOM(general_ty)
  // 6: ATOM(par_ty)
  // 7: ATOM(string_literal_ty)
  public static boolean ty(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "ty")) return false;
    addVariant(b, "<ty>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<ty>");
    r = function_ty(b, l + 1);
    if (!r) r = table_ty(b, l + 1);
    if (!r) r = general_ty(b, l + 1);
    if (!r) r = par_ty(b, l + 1);
    if (!r) r = string_literal_ty(b, l + 1);
    p = r;
    r = r && ty_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean ty_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "ty_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && consumeTokenSmart(b, OR)) {
        while (true) {
          r = report_error_(b, ty(b, l, 0));
          if (!consumeTokenSmart(b, OR)) break;
        }
        exit_section_(b, l, m, UNION_TY, r, true, null);
      }
      else if (g < 3 && leftMarkerIs(b, GENERAL_TY) && generic_ty_0(b, l + 1)) {
        r = true;
        exit_section_(b, l, m, GENERIC_TY, r, true, null);
      }
      else if (g < 4 && consumeTokenSmart(b, ARR)) {
        r = true;
        exit_section_(b, l, m, ARR_TY, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // fun function_generic? '(' function_param_list ')' (':' type_list)?
  public static boolean function_ty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_ty")) return false;
    if (!nextTokenIsSmart(b, FUN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_TY, null);
    r = consumeTokenSmart(b, FUN);
    p = r; // pin = 1
    r = r && report_error_(b, function_ty_1(b, l + 1));
    r = p && report_error_(b, consumeToken(b, LPAREN)) && r;
    r = p && report_error_(b, function_param_list(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, RPAREN)) && r;
    r = p && function_ty_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // function_generic?
  private static boolean function_ty_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_ty_1")) return false;
    function_generic(b, l + 1);
    return true;
  }

  // (':' type_list)?
  private static boolean function_ty_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_ty_5")) return false;
    function_ty_5_0(b, l + 1);
    return true;
  }

  // ':' type_list
  private static boolean function_ty_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_ty_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, EXTENDS);
    r = r && type_list(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // table_def
  public static boolean table_ty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "table_ty")) return false;
    if (!nextTokenIsSmart(b, LCURLY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = table_def(b, l + 1);
    exit_section_(b, m, TABLE_TY, r);
    return r;
  }

  // '<' generic_param_list '>'
  private static boolean generic_ty_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "generic_ty_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, LT);
    r = r && generic_param_list(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // class_name_ref
  public static boolean general_ty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "general_ty")) return false;
    if (!nextTokenIsSmart(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = class_name_ref(b, l + 1);
    exit_section_(b, m, GENERAL_TY, r);
    return r;
  }

  // '(' ty ')'
  public static boolean par_ty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "par_ty")) return false;
    if (!nextTokenIsSmart(b, LPAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PAR_TY, null);
    r = consumeTokenSmart(b, LPAREN);
    p = r; // pin = 1
    r = r && report_error_(b, ty(b, l + 1, -1));
    r = p && consumeToken(b, RPAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // STRING_LITERAL
  public static boolean string_literal_ty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string_literal_ty")) return false;
    if (!nextTokenIsSmart(b, STRING_LITERAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, STRING_LITERAL);
    exit_section_(b, m, STRING_LITERAL_TY, r);
    return r;
  }

}
