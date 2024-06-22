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

package com.tang.intellij.lua.constants;

public class Constants {
    public static final String SwagComment = "---@Tags 登录\n---@Summary 登录\n---@Produce json\n---@Security Sign\n---@Description 接口描述\n---@Param      common      query       {common_request_dto}   true   \"公共请求参数\"\n---@Param      timestamp  query       integer               false  \"接口调用时的时间戳(毫秒)\"\n---@Header          406             string                 X-IS-Error-Msg      \"错误提示信息\"\n---@Response  200    {object}     ret:{errcode}        \"错误码\"  [@validate:\"required,enums=0 101 105\"]\n---@Response  200    {object}     data:{reply} \"\"\n---@Router /api/login [GET]";
    public  static  final  String SwagMain = "---@title API\n---@description API\n---@version 1.0\n---@contact.name swagger\n---@contact.url https://api.swagger.net\n---@contact.email api@swagger.net\n\n---@server https://online.swagger.net 线上环境域名\n---@server https://sandbox.swagger.net 测试环境公网域名\n\n---@termsofservice https://swagger.io/ 服务条款\n\n---@securityDefinitions.apikey Sign\n---@in query\n---@name sign\n---@description 请求签名，请求参数按照字典序排序按照key=val&拼接，加上salt值的MD5\n";
}