package com.tang.intellij.lua.comment.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.tang.intellij.lua.comment.psi.LuaDocTypes;
import java.util.Stack;
%%

%class _LuaDocLexer
%implements FlexLexer, LuaDocTypes


%unicode
%public

%function advance
%type IElementType

%eof{ return;
%eof}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////// User code //////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

%{ // User code
    private int _typeLevel = 0;
    private boolean _typeReq = false;
    private Stack<Integer> _stack = new Stack<>();
    public _LuaDocLexer() {
        this((java.io.Reader) null);
    }

    private void pushState(int state) {
        _stack.push(zzLexicalState);
        yybegin(state);
    }

    private void popState() {
        var state = _stack.pop();
        yybegin(state);
    }

    private void beginType() {
        yybegin(xTYPE_REF);
        _typeLevel = 0;
        _typeReq = true;
    }
%}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////// LuaDoc lexems ////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+
STRING=[^\r\n\t\f]*
ID=[:jletter:] ([:jletterdigit:]|\.)*
SWAGGER_PARAM_NME=[:jletter:] ([:jletterdigit:]|\.)*
URL=[A-Za-z0-9_\?&/=]+
HTTPURL=http[s]?:\/\/[A-Za-z0-9_\-\\?\.:&/=]+
HTTP_METHOD=\[[A-Z]+\]+
SWAG_HTTPSTATUS=[1-5][0-9]{2}
SWAG_RES_KEY=[A-Za-z0-9_]+:
AT=@
COMMA = "\""
SWAG_NOTE =".*"
//三个-以上
DOC_DASHES = --+
//Strings
DOUBLE_QUOTED_STRING=\"([^\\\"]|\\\S|\\[\r\n])*\"?  //\"([^\\\"\r\n]|\\[^\r\n])*\"?
SINGLE_QUOTED_STRING='([^\\\']|\\\S|\\[\r\n])*'?    //'([^\\'\r\n]|\\[^\r\n])*'?

%state xTAG
%state xTAG_WITH_ID
%state xTAG_NAME
%state xCOMMENT_STRING
%state xPARAM
%state xTYPE_REF
%state xCLASS
%state xCLASS_EXTEND
%state xFIELD
%state xFIELD_ID
%state xGENERIC
%state xALIAS
%state xSUPPRESS
%state xDOUBLE_QUOTED_STRING
%state xSINGLE_QUOTED_STRING
%state xSWAG_TAGS
%state xSWAG_PARAMS
%state xSWAG_SUMMARY
%state xSWAG_QUERY_TYPE
%state xSWAG_QUERY_TY
%state xSWAG_QUERY_OPTIONAL
%state xSWAG_ROUTER
%state xSWAG_DES
%state xSWAG_SIGN
%state xSWAG_METHOD
%state xSWAG_RESPONSE
%state xSWAG_RESPONSE_TYPE
%state xSWAG_RESPONSE_TY
%state xSWAG_COMON_NOTE
%state xSWAG_HEADER
%state xSWAG_HEADER_TYPE
%state xSWAG_HEADER_TY
%state xSWAG_SIGN_NAME
%state xSWAG_QUERY_COMMY_TY
%state xSWAG_SERVER

%%

<YYINITIAL> {
    {EOL}                      { yybegin(YYINITIAL); return com.intellij.psi.TokenType.WHITE_SPACE;}
    {LINE_WS}+                 { return com.intellij.psi.TokenType.WHITE_SPACE; }
    {DOC_DASHES}               { return DASHES; }
    "@"                        { yybegin(xTAG_NAME); return AT; }
    .                          { yybegin(xCOMMENT_STRING); yypushback(yylength()); }
}

<xTAG, xTAG_WITH_ID, xTAG_NAME, xPARAM, xTYPE_REF, xCLASS, xCLASS_EXTEND, xFIELD, xFIELD_ID, xCOMMENT_STRING, xGENERIC, xALIAS, xSUPPRESS, xSWAG_PARAMS, xSWAG_TAGS, xSWAG_SUMMARY, xSWAG_ROUTER, xSWAG_DES, xSWAG_SIGN, xSWAG_RESPONSE, xSWAG_HEADER, xSWAG_SIGN_NAME, xSWAG_QUERY_COMMY_TY, xSWAG_SERVER> {
    {EOL}                      { yybegin(YYINITIAL);return com.intellij.psi.TokenType.WHITE_SPACE;}
    {LINE_WS}+                 { return com.intellij.psi.TokenType.WHITE_SPACE; }
}

<xTAG_NAME> {
    "field"                    { yybegin(xFIELD); return TAG_NAME_FIELD; }
    "param"                    { yybegin(xPARAM); return TAG_NAME_PARAM; }
    "vararg"                   { yybegin(xPARAM); return TAG_NAME_VARARG; }
    "class"                    { yybegin(xCLASS); return TAG_NAME_CLASS; }
    "module"                   { yybegin(xCLASS); return TAG_NAME_MODULE; }
    "enum"                     { yybegin(xCLASS); return TAG_NAME_ENUM; }
    "return"                   { beginType(); return TAG_NAME_RETURN; }
    "type"                     { beginType(); return TAG_NAME_TYPE;}
    "overload"                 { beginType(); return TAG_NAME_OVERLOAD; }
    "private"                  { return TAG_NAME_PRIVATE; }
    "protected"                { return TAG_NAME_PROTECTED; }
    "public"                   { return TAG_NAME_PUBLIC; }
    "language"                 { yybegin(xTAG_WITH_ID); return TAG_NAME_LANGUAGE;}
    "generic"                  { yybegin(xGENERIC); return TAG_NAME_GENERIC; }
    "see"                      { yybegin(xTAG); return TAG_NAME_SEE; }
    "alias"                    { yybegin(xALIAS); return TAG_NAME_ALIAS; }
    "suppress"                 { yybegin(xSUPPRESS); return TAG_NAME_SUPPRESS; }
    "Param"                    { yybegin(xSWAG_PARAMS); return TAG_NAME_SWAGPARAM; }
    "Tags"                     { yybegin(xSWAG_TAGS); return TAG_NAME_SWAGTAGS; }
    "Summary"                  { yybegin(xSWAG_SUMMARY); return TAG_NAME_SWAGSUMMARY; }
    "Router"                   { yybegin(xSWAG_ROUTER); return TAG_NAME_SWAGROUTER; }
    "Description"              { yybegin(xSWAG_DES); return TAG_NAME_SWAGDES; }
    "description"              { yybegin(xSWAG_DES); return TAG_NAME_SWAGDES; }
    "Security"                 { yybegin(xSWAG_SIGN); return TAG_NAME_SIGN; }
    "server"                   { yybegin(xSWAG_SERVER); return TAG_NAME_SERVER;}
    "securityDefinitions.apikey"                 { yybegin(xSWAG_SIGN); return TAG_NAME_SWAG_SIGN_API; }
    "name"                     { yybegin(xSWAG_SIGN_NAME); return TAG_NAME_SWAG_SIGN_NAME; }
    "in"                       { yybegin(xSWAG_QUERY_COMMY_TY); return TAG_NAME_SWAG_SIGN_IN;}
    "Response"                 { yybegin(xSWAG_RESPONSE);  return TAG_NAME_SWAGRES; }
    "Header"                   { yybegin(xSWAG_HEADER); return TAG_NAME_HEADER; }
    "contact.name"             { yybegin(xCOMMENT_STRING); return TAG_NAME_SWAG_CONTACT_NAME; }
    "contact.email"             { yybegin(xCOMMENT_STRING); return TAG_NAME_SWAG_CONTACT_EMAIL; }
    "contact.url"             { yybegin(xCOMMENT_STRING); return TAG_NAME_SWAG_CONTACT_URL; }
    {ID}                       { yybegin(xCOMMENT_STRING); return TAG_NAME; }
    [^]                        { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}

<xSUPPRESS> {
    {ID}                       { return ID; }
    ","                        { return COMMA; }
    [^]                        { yybegin(YYINITIAL); yypushback(yylength()); }
}

<xALIAS> {
    {ID}                       { beginType(); return ID; }
    [^]                        { yybegin(YYINITIAL); yypushback(yylength()); }
}

<xGENERIC> {
    {ID}                       { return ID; }
    ":"                        { return EXTENDS;}
    ","                        { return COMMA; }
    [^]                        { yybegin(YYINITIAL); yypushback(yylength()); }
}

<xCLASS> {
    {ID}                       { yybegin(xCLASS_EXTEND); return ID; }
}
<xCLASS_EXTEND> {
    ":"                        { beginType(); return EXTENDS;}
    [^]                        { yybegin(xCOMMENT_STRING); yypushback(yylength()); }
}

<xSWAG_SERVER> {
    {WHITE_SPACE}           { yybegin(xCOMMENT_STRING); return com.intellij.psi.TokenType.WHITE_SPACE;}
    {HTTPURL}               {yybegin(xCOMMENT_STRING); return HTTPURL;}
}

<xSWAG_TAGS> {
    [^]                        { yybegin(xCOMMENT_STRING); yypushback(yylength()); }
}

<xSWAG_SUMMARY> {
   [^]                        { yybegin(xCOMMENT_STRING); yypushback(yylength()); }
}

<xSWAG_DES> {
    [^]                        { yybegin(xCOMMENT_STRING); yypushback(yylength()); }
}

<xSWAG_SIGN> {
    {ID}                     { yybegin(YYINITIAL); return SWAGGER_SIGN_NME; }
}

<xSWAG_SIGN_NAME> {
    {WHITE_SPACE}            { yybegin(xSWAG_SIGN_NAME); return com.intellij.psi.TokenType.WHITE_SPACE;}
    {ID}                     { yybegin(YYINITIAL); return ID; }
}

<xSWAG_ROUTER> {
    {URL}                     { yybegin(xSWAG_METHOD); return  URL; }
}

<xSWAG_METHOD> {
    {WHITE_SPACE}                   { yybegin(xSWAG_METHOD); return com.intellij.psi.TokenType.WHITE_SPACE;}
    {HTTP_METHOD}                   {return  HTTP_METHOD_SWAG; }
}

<xSWAG_PARAMS> {
     {SWAGGER_PARAM_NME}                        { yybegin(xSWAG_QUERY_TYPE); return SWAGGER_PARAM_NME; }
}

<xSWAG_RESPONSE> {
     {WHITE_SPACE}               { yybegin(xSWAG_RESPONSE); return com.intellij.psi.TokenType.WHITE_SPACE;}
     {SWAG_HTTPSTATUS}           { yybegin(xSWAG_RESPONSE_TYPE); return SWAG_HTTPSTATUS; }
}

<xSWAG_HEADER> {
    {WHITE_SPACE}               { yybegin(xSWAG_HEADER); return com.intellij.psi.TokenType.WHITE_SPACE;}
    {SWAG_HTTPSTATUS}           { yybegin(xSWAG_HEADER_TYPE); return SWAG_HTTPSTATUS;}
}

<xSWAG_HEADER_TYPE> {
    {WHITE_SPACE}               { yybegin(xSWAG_HEADER_TYPE); return com.intellij.psi.TokenType.WHITE_SPACE;}
    "{"                         { yybegin(xSWAG_HEADER_TY);   return LCURLY;}
    {ID}                        { beginType();   return ID;}
}

<xSWAG_HEADER_TY> {
    {WHITE_SPACE}               { yybegin(xSWAG_HEADER_TYPE); return com.intellij.psi.TokenType.WHITE_SPACE;}
    {ID}                        { beginType(); return ID; }
}

<xSWAG_RESPONSE_TYPE> {
     {WHITE_SPACE}                  { yybegin(xSWAG_RESPONSE_TYPE); return com.intellij.psi.TokenType.WHITE_SPACE;}
     "object"                       { yybegin(xSWAG_RESPONSE_TYPE); return SWAGRES_TYPE_OBJ;}
     "{object}"                     { yybegin(xSWAG_RESPONSE_TYPE); return SWAGRES_TYPE_OBJ;}
     {SWAG_RES_KEY}                 { yybegin(xSWAG_RESPONSE_TYPE); return SWAG_RES_KEY;}
     "{"                            { yybegin(xSWAG_RESPONSE_TY);   return LCURLY;}
     "\""                           { yybegin(xCOMMENT_STRING); return SWAG_NOTE;}
}

<xSWAG_COMON_NOTE> {
     [^]                      { yybegin(xCOMMENT_STRING); return SWAG_NOTE;}
}

<xSWAG_RESPONSE_TY> {
     {WHITE_SPACE}               { yybegin(xSWAG_RESPONSE_TY); return com.intellij.psi.TokenType.WHITE_SPACE;}
     "{"                         { yybegin(xSWAG_RESPONSE_TY); return LCURLY;}
     {ID}                        { beginType(); return ID; }
}

<xSWAG_QUERY_TYPE> {
     {WHITE_SPACE}                  { yybegin(xSWAG_QUERY_TYPE); return com.intellij.psi.TokenType.WHITE_SPACE;}
     "query"                        { yybegin(xSWAG_QUERY_TY); return SWAGPARAM_QUERY; }
     "header"                       { yybegin(xSWAG_QUERY_TY); return SWAGPARAM_HEADER; }
     "body"                         { yybegin(xSWAG_QUERY_TY); return SWAGPARAM_BODY; }
     "formData"                     { yybegin(xSWAG_QUERY_TY); return SWAGPARAM_FORM; }
     "path"                         { yybegin(xSWAG_QUERY_TY); return SWAGPARAM_PATH; }
}

<xSWAG_QUERY_COMMY_TY> {
    "query"                        { return SWAGPARAM_QUERY; }
    "header"                       { return SWAGPARAM_HEADER; }
    "body"                         { return SWAGPARAM_BODY; }
    "formData"                     { return SWAGPARAM_FORM; }
    "path"                         { return SWAGPARAM_PATH; }
}

<xSWAG_QUERY_TY> {
     {WHITE_SPACE}               { yybegin(xSWAG_QUERY_TY); return com.intellij.psi.TokenType.WHITE_SPACE;}
     "{"                         { yybegin(xSWAG_QUERY_TY); return LCURLY;}
     {ID}                        { beginType(); return ID; }
}

<xSWAG_QUERY_OPTIONAL> {
     "true"                        { yybegin(xCOMMENT_STRING); return SWAGPARAM_TRUE; }
     "false"                       { yybegin(xCOMMENT_STRING); return SWAGPARAM_FALSE; }
}

<xPARAM> {
    {ID}                       { beginType(); return ID; }
    "..."                      { beginType(); return ID; } //varargs
}

<xFIELD> {
    "private"                  { yybegin(xFIELD_ID); return PRIVATE; }
    "protected"                { yybegin(xFIELD_ID); return PROTECTED; }
    "public"                   { yybegin(xFIELD_ID); return PUBLIC; }
    {ID}                       { beginType(); return ID; }
}
<xFIELD_ID> {
    {ID}                       { beginType(); return ID; }
}

<xTYPE_REF> {
    "@"                        { yybegin(xCOMMENT_STRING); return STRING_BEGIN; }
    ","                        { _typeReq = true; return COMMA; }
    "|"                        { _typeReq = true; return OR; }
    ":"                        { _typeReq = true; return EXTENDS;}
    "<"                        { _typeLevel++; return LT; }
    ">"                        { _typeLevel--; _typeReq = false; return GT; }
    "("                        { _typeLevel++; return LPAREN; }
    ")"                        { _typeLevel--; _typeReq = false; return RPAREN; }
    "{"                        { _typeLevel++; return LCURLY; }
    "}"                        { _typeLevel--; _typeReq = false; return RCURLY; }
    "\""                       { pushState(xDOUBLE_QUOTED_STRING); yypushback(yylength()); }
    "'"                        { pushState(xSINGLE_QUOTED_STRING); yypushback(yylength()); }
    "[]"                       { _typeReq = false; return ARR; }
    "fun"                      { return FUN; }
    "vararg"                   { _typeReq = true; return VARARG; }
    "..."|{ID}                 { if (_typeReq || _typeLevel > 0) { _typeReq = false; return ID; } else { yybegin(xCOMMENT_STRING); yypushback(yylength()); } }
}

<xDOUBLE_QUOTED_STRING> {
    {DOUBLE_QUOTED_STRING}    { popState(); return STRING_LITERAL; }
}

<xSINGLE_QUOTED_STRING> {
    {SINGLE_QUOTED_STRING}    { popState(); return STRING_LITERAL; }
}

<xTAG> {
    "@"                        { yybegin(xCOMMENT_STRING); return STRING_BEGIN; }
    "#"                        { return SHARP; }
    {ID}                       { return ID; }
    [^]                        { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
<xTAG_WITH_ID> {
    "\""                       { pushState(xDOUBLE_QUOTED_STRING); yypushback(yylength()); }
    "'"                        { pushState(xSINGLE_QUOTED_STRING); yypushback(yylength()); }
    {ID}                       { yybegin(xCOMMENT_STRING); return ID; }
}

<xCOMMENT_STRING> {
    {STRING}                   { yybegin(YYINITIAL); return STRING; }
}

[^]                            { return com.intellij.psi.TokenType.BAD_CHARACTER; }