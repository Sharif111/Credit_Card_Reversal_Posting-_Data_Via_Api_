/* Decompiler 83ms, total 365ms, lines 170 */
package com.ibank.ibanking.cards.bo;

import java.io.Serializable;
import java.util.ArrayList;

public class CreditCardReversalBO implements Serializable {
   private String sSourceAccount = "";
   private String sAccountTile = "";
   private String sTransactionAmount = "";
   private String sTransactionDate = "";
   private String sStatus = "";
   private String sFromDate = "";
   private String sToDate = "";
   private String sDestinationAccount = "";
   private String sAmount = "";
   private String sSerialNo = "";
   private String sRRNNo = "";
   private String mailId = "";
   private String sessionId = "";
   private ArrayList aReversalList = new ArrayList();
   private String sErrorCode = "";
   private String sErrorMessage = "";
   private ArrayList aList = new ArrayList();
   private String sMenu = "";
   private ArrayList aMenuList = new ArrayList();
   private ArrayList aMenuNameList = new ArrayList();
   
  private String encryptedPassword = "";
  private String key = "";
  private String itclTranID = "";
  private String requestNo = "";
  private String reason = "";
  private String debitDocNo="";
  private String keyId="";
  private String key1="";
  private String key2="";
  private String authResCode = "";
  private String authResMessage = "";
  private String approvalCode = "";
  private String debitTransactionID = "";
  private String creditTransactionID = "";
  
  public String getAuthResCode() {
	return authResCode;
}

public void setAuthResCode(String authResCode) {
	this.authResCode = authResCode;
}

public String getAuthResMessage() {
	return authResMessage;
}

public void setAuthResMessage(String authResMessage) {
	this.authResMessage = authResMessage;
}

public String getApprovalCode() {
	return approvalCode;
}

public void setApprovalCode(String approvalCode) {
	this.approvalCode = approvalCode;
}

public String getDebitTransactionID() {
	return debitTransactionID;
}

public void setDebitTransactionID(String debitTransactionID) {
	this.debitTransactionID = debitTransactionID;
}

public String getCreditTransactionID() {
	return creditTransactionID;
}

public void setCreditTransactionID(String creditTransactionID) {
	this.creditTransactionID = creditTransactionID;
}

  
          public String getKeyId() {
			return keyId;
		}

		public void setKeyId(String keyId) {
			this.keyId = keyId;
		}

		public String getKey1() {
			return key1;
		}

		public void setKey1(String key1) {
			this.key1 = key1;
		}

		public String getKey2() {
			return key2;
		}

		public void setKey2(String key2) {
			this.key2 = key2;
		}

		

   public String getDebitDocNo() {
			return debitDocNo;
		}

		public void setDebitDocNo(String debitDocNo) {
			this.debitDocNo = debitDocNo;
		}

public String getEncryptedPassword() {
	return encryptedPassword;
}

public void setEncryptedPassword(String encryptedPassword) {
	this.encryptedPassword = encryptedPassword;
}

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public String getItclTranID() {
	return itclTranID;
}

public void setItclTranID(String itclTranID) {
	this.itclTranID = itclTranID;
}

public String getRequestNo() {
	return requestNo;
}

public void setRequestNo(String requestNo) {
	this.requestNo = requestNo;
}

public String getReason() {
	return reason;
}

public void setReason(String reason) {
	this.reason = reason;
}

public String getSourceAccount() {
      return this.sSourceAccount;
   }

   public void setSourceAccount(String sSourceAccount) {
      this.sSourceAccount = sSourceAccount;
   }

   public String getAccountTile() {
      return this.sAccountTile;
   }

   public void setAccountTile(String sAccountTile) {
      this.sAccountTile = sAccountTile;
   }

   public String getTransactionAmount() {
      return this.sTransactionAmount;
   }

   public void setTransactionAmount(String sTransactionAmount) {
      this.sTransactionAmount = sTransactionAmount;
   }

   public String getTransactionDate() {
      return this.sTransactionDate;
   }

   public void setTransactionDate(String sTransactionDate) {
      this.sTransactionDate = sTransactionDate;
   }

   public String getStatus() {
      return this.sStatus;
   }

   public void setStatus(String sStatus) {
      this.sStatus = sStatus;
   }

   public String getErrorCode() {
      return this.sErrorCode;
   }

   public void setErrorCode(String sErrorCode) {
      this.sErrorCode = sErrorCode;
   }

   public String getErrorMessage() {
      return this.sErrorMessage;
   }

   public void setErrorMessage(String sErrorMessage) {
      this.sErrorMessage = sErrorMessage;
   }

   public ArrayList getList() {
      return this.aList;
   }

   public void setList(ArrayList aList) {
      this.aList = aList;
   }

   public String getMenu() {
      return this.sMenu;
   }

   public void setMenu(String sMenu) {
      this.sMenu = sMenu;
   }

   public ArrayList getMenuList() {
      return this.aMenuList;
   }

   public void setMenuList(ArrayList aMenuList) {
      this.aMenuList = aMenuList;
   }

   public ArrayList getMenuNameList() {
      return this.aMenuNameList;
   }

   public void setMenuNameList(ArrayList aMenuNameList) {
      this.aMenuNameList = aMenuNameList;
   }

   public String getFromDate() {
      return this.sFromDate;
   }

   public void setFromDate(String sFromDate) {
      this.sFromDate = sFromDate;
   }

   public String getToDate() {
      return this.sToDate;
   }

   public void setToDate(String sToDate) {
      this.sToDate = sToDate;
   }

   public String getDestinationAccount() {
      return this.sDestinationAccount;
   }

   public void setDestinationAccount(String sDestinationAccount) {
      this.sDestinationAccount = sDestinationAccount;
   }

   public String getAmount() {
      return this.sAmount;
   }

   public void setAmount(String sAmount) {
      this.sAmount = sAmount;
   }

   public String getSerialNo() {
      return this.sSerialNo;
   }

   public void setSerialNo(String sSerialNo) {
      this.sSerialNo = sSerialNo;
   }

   public String getRRNNo() {
      return this.sRRNNo;
   }

   public void setRRNNo(String sRRNNo) {
      this.sRRNNo = sRRNNo;
   }

   public ArrayList getReversalList() {
      return this.aReversalList;
   }

   public void setReversalList(ArrayList aReversalList) {
      this.aReversalList = aReversalList;
   }

public String getMailId() {
	return mailId;
}

public void setMailId(String mailId) {
	this.mailId = mailId;
}

public String getSessionId() {
	return sessionId;
}

public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
}
   
   
}
