package com.ibank.ibanking.cards.action;

import com.ibank.ibanking.cards.bo.CreditCardReversalBO;
import com.ibank.ibanking.cards.dao.CreditCardReversalDAO;
import com.ibank.ibanking.cards.formbean.CreditCardReversalForm;
import com.ibank.utility.RemoveNullValue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CreditCardReversalAction extends Action {
   public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
      new CreditCardReversalBO();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      CreditCardReversalDAO oCreditCardReversalDAO = new CreditCardReversalDAO();
      CreditCardReversalForm oCreditCardReversalForm = (CreditCardReversalForm)form;
      new CreditCardReversalBO();
      RemoveNullValue oRemoveNullValue = new RemoveNullValue();
      oRemoveNullValue.removeNullValue(oCreditCardReversalForm);
      String sActionPath = "";
      sActionPath = mapping.getPath();
      HttpSession session = request.getSession(true);
      String sSuccessAction = "success";
      String sFailureAction = "failure";
      String sFatalErrorAction = "fatalError";
      String sSessionExpireAction = "sessionExpire";
      String sSessionMyBankMenuAction = "sessionMyBankMenu";
      String sSuccess = sFatalErrorAction;
      String sActionPathName = "";
      String gsUserID = (String)session.getAttribute("GSUserID");
      String gsUserTitle = (String)session.getAttribute("GSUserTitle");
      String gsLastLogInDate = (String)session.getAttribute("GSLastLogInDate");
      String gsLogInUserID = (String)session.getAttribute("GSLogInUserID");
      String gsSessionID = (String)session.getAttribute("GSSessionID");
      String gsInternalCardID = (String)session.getAttribute("GSInternalCardID");
      String gsHeaderName = (String)session.getAttribute("GSHeaderName");
      String gsHeaderLogIn = (String)session.getAttribute("GSHeaderLogIn");
      String gsCompanyID = (String)session.getAttribute("GSCompanyCode");
      String gsBranchID = (String)session.getAttribute("GSBranchCode");
      String gsBranchName = (String)session.getAttribute("GSBranchName");
      String gsTellerID = (String)session.getAttribute("GSTellerID");
      String gsCompanyName = (String)session.getAttribute("GSCompanyName");
      String gsBranchOpenDateDDFormat = (String)session.getAttribute("GSBranchOpenDateDDFormat");
      String sBranchActionPathName = "/ibanking/creditCardReversal.do";
      String NPSBOutgoingReversalMessageBO="";
      if (sActionPath.equals("/creditCardReversal")) {
         session.setAttribute("oNPSBOutgoingReversalMessageBO", " ");
         this.clearForm(oCreditCardReversalForm);
         sSuccess = sSuccessAction;
      } else if (sActionPath.equals("/reversalDetailsOfCreditCardReversal")) {
         session.setAttribute("oNPSBOutgoingReversalMessageBO", " ");
         session.setAttribute("oCreditCardReversalBO", (Object)null);
         oCreditCardReversalForm.setFromDate(gsBranchOpenDateDDFormat);
         oCreditCardReversalForm.setToDate(gsBranchOpenDateDDFormat);
         oCreditCardReversalForm.setStatus("ALL");
         oCreditCardReversalForm.setSourceAccount("ALL");
         oCreditCardReversalForm.setDestinationAccount("ALL");
         oCreditCardReversalForm.setAmount("");
         sSuccess = sSuccessAction;
      } else {
        
        // String NPSBOutgoingReversalMessageBO;
         if (sActionPath.equals("/searchReversalDetailsOfCreditCardReversal")) {
            session.setAttribute("oNPSBOutgoingReversalMessageBO", " ");
            session.setAttribute("oCreditCardReversalBO", (Object)null);
            oCreditCardReversalBO = oCreditCardReversalDAO.getNPSBOutgoingReversalInfoPro(gsUserID, gsSessionID, gsCompanyID, "S", oCreditCardReversalForm.getFromDate(), oCreditCardReversalForm.getToDate(), oCreditCardReversalForm.getStatus(), oCreditCardReversalForm.getSourceAccount(), oCreditCardReversalForm.getDestinationAccount(), oCreditCardReversalForm.getAmount());
            if (oCreditCardReversalBO.getErrorCode().equals("0")) {
               oCreditCardReversalBO = oCreditCardReversalDAO.getNPSBOutgoingReversalInfoData(gsUserID, gsSessionID);
               session.setAttribute("oCreditCardReversalBO", oCreditCardReversalBO);
               sSuccess = sSuccessAction;
            } else if (oCreditCardReversalBO.getErrorCode().equals("1")) {
               NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
               session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
               sSuccess = sFailureAction;
            } else if (oCreditCardReversalBO.getErrorCode().equals("2")) {
               this.clearSession(session);
               sSuccess = sSessionExpireAction;
            } else if (oCreditCardReversalBO.getErrorCode().equals("3")) {
               this.clearSession(session);
               sSuccess = sSessionMyBankMenuAction;
            } else {
               this.clearSession(session);
               sSuccess = sFatalErrorAction;
            }
         }
            if (sActionPath.equals("/executeCreditCardReversal")) {
                session.setAttribute("oNPSBOutgoingReversalMessageBO", " ");
                session.setAttribute("oCreditCardReversalBO", " ");
               
                
                
                oCreditCardReversalBO.setMailId(gsUserID);
                oCreditCardReversalBO.setSessionId(gsSessionID);
                oCreditCardReversalBO.setDebitDocNo(oCreditCardReversalForm.getDebitDocNo());                
                oCreditCardReversalBO.setRRNNo(oCreditCardReversalForm.getRRNNo());
                oCreditCardReversalBO.setReason(oCreditCardReversalForm.getReason());
                oCreditCardReversalBO.setErrorMessage(oCreditCardReversalForm.getErrorMessage());
                                                            
                oCreditCardReversalBO = oCreditCardReversalDAO.getCreditCardAPI(oCreditCardReversalBO);
                
                System.out.println("errorCode 222 =>" + oCreditCardReversalBO.getErrorCode());
                System.out.println("errorMessage  222 => " + oCreditCardReversalBO.getErrorMessage());
                
                if (oCreditCardReversalBO.getErrorCode().equals("0")) {
                	
                	//oCreditCardReversalBO =oCreditCardReversalDAO.responseCreditCardAPI(oCreditCardReversalBO);         
                    System.out.println("errorCode 333 =>" + oCreditCardReversalBO.getErrorCode());
                    System.out.println("errorMessage 333 => " + oCreditCardReversalBO.getErrorMessage());
                    
                    NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
                    session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
                   session.setAttribute("oCreditCardReversalBO", oCreditCardReversalBO);
                     sSuccess = sSuccessAction;
                    
                	/*if("0".equals(oCreditCardReversalBO.getErrorCode())) {
                		  System.out.println("errorCode 444 =>" + oCreditCardReversalBO.getErrorCode());
                          System.out.println("errorMessage 444 => " + oCreditCardReversalBO.getErrorMessage());
                      	
                		NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
                        session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
                       session.setAttribute("oCreditCardReversalBO", oCreditCardReversalBO);
                         sSuccess = sSuccessAction;
                         
                	}else {
                		
                		 NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
                         session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
                         sSuccess = sFailureAction;
                	}
                	*/
                	
                	
                  
                } else if (oCreditCardReversalBO.getErrorCode().equals("1")) {
                   NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
                   session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
                   sSuccess = sFailureAction;
                } else if (oCreditCardReversalBO.getErrorCode().equals("2")) {
                   this.clearSession(session);
                   sSuccess = sSessionExpireAction;
                } else if (oCreditCardReversalBO.getErrorCode().equals("3")) {
                   this.clearSession(session);
                   sSuccess = sSessionMyBankMenuAction;
                } else {
                   this.clearSession(session);
                   sSuccess = sFatalErrorAction;
                }
            } else if (sActionPath.equals("/cancelCreditCardReversal")) {
            session.setAttribute("oNPSBOutgoingReversalMessageBO", (Object)null);
            oCreditCardReversalBO = oCreditCardReversalDAO.getMenuCheckPro(gsUserID, gsSessionID, gsCompanyID, gsBranchID, request.getRemoteAddr(), sBranchActionPathName);
            if (oCreditCardReversalBO.getErrorCode().equals("0")) {
               this.clearSession(session);
               NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
               session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
               sSuccess = sSuccessAction;
            } else if (oCreditCardReversalBO.getErrorCode().equals("1")) {
               NPSBOutgoingReversalMessageBO = oCreditCardReversalBO.getErrorMessage();
               session.setAttribute("oNPSBOutgoingReversalMessageBO", NPSBOutgoingReversalMessageBO);
               sSuccess = sFailureAction;
            } else if (oCreditCardReversalBO.getErrorCode().equals("2")) {
               this.clearSession(session);
               sSuccess = sSessionExpireAction;
            } else if (oCreditCardReversalBO.getErrorCode().equals("3")) {
               this.clearSession(session);
               sSuccess = sSessionMyBankMenuAction;
            } else {
               this.clearSession(session);
               sSuccess = sFatalErrorAction;
            }
         }
      }

      return mapping.findForward(sSuccess);
   }

   public void populateMenu(CreditCardReversalForm oCreditCardReversalForm, CreditCardReversalBO oCreditCardReversalBO) {
      oCreditCardReversalForm.setMenuList(oCreditCardReversalBO.getMenuList());
      oCreditCardReversalForm.setMenuNameList(oCreditCardReversalBO.getMenuNameList());
   }

   public void populateMessage(CreditCardReversalForm oCreditCardReversalForm, CreditCardReversalBO oCreditCardReversalBO) {
      oCreditCardReversalForm.setList(oCreditCardReversalBO.getList());
   }

   private void clearForm(CreditCardReversalForm oCreditCardReversalForm) {
      oCreditCardReversalForm.setSourceAccount("");
      oCreditCardReversalForm.setAccountTile("");
      oCreditCardReversalForm.setTransactionAmount("");
      oCreditCardReversalForm.setTransactionDate("");
      oCreditCardReversalForm.setStatus("");
      oCreditCardReversalForm.setFromDate("");
      oCreditCardReversalForm.setToDate("");
      oCreditCardReversalForm.setDestinationAccount("");
      oCreditCardReversalForm.setAmount("");
      oCreditCardReversalForm.setRRNNo("");
      oCreditCardReversalForm.setDebitDocNo("");
      oCreditCardReversalForm.setReason("");
      oCreditCardReversalForm.setSerialNo("");
      oCreditCardReversalForm.setErrorCode("");
      oCreditCardReversalForm.setErrorMessage("");
      oCreditCardReversalForm.setMenu("");
   }

   private void clearSession(HttpSession session) {
      session.setAttribute("oCreditCardReversalBO", " ");
      session.setAttribute("oNPSBOutgoingReversalMessageBO", " ");
   }
}
