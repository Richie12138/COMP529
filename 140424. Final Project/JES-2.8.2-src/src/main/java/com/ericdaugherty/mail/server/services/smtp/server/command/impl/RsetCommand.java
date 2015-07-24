/******************************************************************************
 * This program is a 100% Java Email Server.
 ******************************************************************************
 * Copyright (c) 2001-2014, Eric Daugherty (http://www.ericdaugherty.com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the copyright holder nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 ******************************************************************************
 * For current versions and more information, please visit:
 * http://javaemailserver.sf.net/
 *
 * or contact the author at:
 * andreaskyrmegalos@hotmail.com
 *
 ******************************************************************************
 * This program is based on the CSRMail project written by Calvin Smith.
 * http://crsemail.sourceforge.net/
 ******************************************************************************
 *
 * $Rev$
 * $Date$
 *
 ******************************************************************************/

package com.ericdaugherty.mail.server.services.smtp.server.command.impl;

//Java imports
import java.io.IOException;

//Local imports
import com.ericdaugherty.mail.server.errors.TooManyErrorsException;
import com.ericdaugherty.mail.server.errors.SMTPReplyException;
import com.ericdaugherty.mail.server.services.smtp.server.CommandVerb;
import com.ericdaugherty.mail.server.services.smtp.server.SMTPServerSessionControl;
import com.ericdaugherty.mail.server.services.smtp.server.action.CommandAction;
import com.ericdaugherty.mail.server.services.smtp.server.action.PostCommandAction;
import com.ericdaugherty.mail.server.services.smtp.server.command.AbstractCommand;
import com.ericdaugherty.mail.server.services.smtp.server.parser.impl.RsetInterpreter;

/**
 *
 * @author Andreas Kyrmegalos
 */
public class RsetCommand extends AbstractCommand{
   
   public RsetCommand() {
      super(CommandVerb.RSET, null, new RsetInterpreter());
      reset();
   }
   
   @Override
   public void reset() {
      super.reset();
      setCommandAction(new RsetCommandAction());
      setPostCommandAction(new RsetPostCommandAction());
   }

   static class RsetCommandAction extends CommandAction {
      
      @Override
      public void execute(SMTPServerSessionControl control) throws SMTPReplyException, TooManyErrorsException, IOException {
         control.setReplyAny(SMTPServerSessionControl.MESSAGE_OK);
      }
   }

   static class RsetPostCommandAction extends PostCommandAction {
      
      @Override
      public void execute(SMTPServerSessionControl control) throws SMTPReplyException {
         if (!(control.isLastCommand(CommandVerb.RSET) || control.isLastCommand(CommandVerb.EHLO)
               || control.isLastCommand(CommandVerb.HELO))) {
            control.setInitState(false);
         }
      }
   }
}