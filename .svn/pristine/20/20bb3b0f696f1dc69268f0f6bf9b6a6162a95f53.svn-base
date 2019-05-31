
/*
 * Copyright 2007 Soren Davidsen, Tanesha Networks
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.talentPool.recaptcha;

public interface ReCaptcha {

	/**
	 * Validates a reCaptcha challenge and response.
	 * 
	 * @param remoteAddr The address of the user, eg. request.getRemoteAddr()
	 * @param challenge The challenge from the reCaptcha form, this is usually request.getParameter("recaptcha_challenge_field") in your code.
	 * @param response The response from the reCaptcha form, this is usually request.getParameter("recaptcha_response_field") in your code.
	 * @return
	 */
	public ReCaptchaResponse checkAnswer(String remoteAddr, String challenge, String response); 
}

