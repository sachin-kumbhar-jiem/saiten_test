<#--
/*
 * $Id: Action.java 502296 2007-02-01 17:33:39Z niallp $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#-- 
File edited by HJ
-->

<#--
Modificated contents of controlheader-core.ftl-->
<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->

<#-- end of controlheader-core.ftl-->
<#include "/${parameters.templateDir}/simple/password.ftl" />
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if hasFieldErrors>

<#list fieldErrors[parameters.name] as error>
    <#rt/>
        </BR><span class="errorMessage">${error?html}</span><#t/>
    <#lt/>
</#list>

</#if>