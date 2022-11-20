package com.ibank.ibanking.cards.dao;
import com.ibank.dbconnection.application.DBCPNewConnection;
import com.ibank.ibanking.cards.bo.CreditCardReversalBO;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CreditCardReversalDAO {
	 private static SecretKeySpec secretKey;
	    private static byte[] key;
   SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

   public CreditCardReversalBO getPermissionCheckPro(String sUserID, String sSessionID, String sCompanyID, String sBranchID, String sIPAddress, String sBranchActionPath) throws Exception {
      Connection oConn = null;
      oConn = DBCPNewConnection.getConnection();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      CallableStatement oStmt = oConn.prepareCall("{call MyBank.DPR_MYBANK_BANK_USER_CHECK(?,?,?,?,?,?,?)}");
      oStmt.setString(1, sUserID);
      oStmt.setString(2, sSessionID);
      oStmt.setString(3, sCompanyID);
      oStmt.setString(4, sBranchID);
      oStmt.setString(5, sIPAddress);
      oStmt.setString(6, sBranchActionPath);
      oStmt.registerOutParameter(7, 4);

      try {
         oStmt.execute();
         oCreditCardReversalBO.setErrorCode("" + oStmt.getInt(7));
      } catch (Exception var17) {
         var17.printStackTrace();

         try {
            oConn.rollback();
         } catch (Exception var16) {
         }
      } finally {
         if (oStmt != null) {
            oStmt.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getMenuCheckPro(String sUserID, String sSessionID, String sCompanyID, String sBranchID, String sRemoteIPAddress, String sActionPath) throws Exception {
      Connection oConn = null;
      oConn = DBCPNewConnection.getConnection();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      CallableStatement oStmt = oConn.prepareCall("{call IBANKING.dpr_ibk_main_menu_check(?,?,?,?,?,?,?,?)}");
      oStmt.setString(1, sUserID);
      oStmt.setString(2, sSessionID);
      oStmt.setString(3, sCompanyID);
      oStmt.setString(4, sBranchID);
      oStmt.setString(5, sRemoteIPAddress);
      oStmt.setString(6, sActionPath);
      oStmt.registerOutParameter(7, 4);
      oStmt.registerOutParameter(8, 12);

      try {
         oStmt.execute();
         oCreditCardReversalBO.setErrorCode("" + oStmt.getInt(7));
         oCreditCardReversalBO.setErrorMessage(oStmt.getString(8));
      } catch (Exception var17) {
         var17.printStackTrace();

         try {
            oConn.rollback();
         } catch (Exception var16) {
         }
      } finally {
         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getSessionCheckPro(String sUserID, String sSessionID, String sCompanyID, String sBranchID) throws Exception {
      Connection oConn = null;
      oConn = DBCPNewConnection.getConnection();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      CallableStatement oStmt = oConn.prepareCall("{CALL MYBANK.DPR_SESSION_VERIFY(?,?,?,?,?)}");
      oStmt.setString(1, sUserID);
      oStmt.setString(2, sSessionID);
      oStmt.setString(3, sCompanyID);
      oStmt.setString(4, sBranchID);
      oStmt.registerOutParameter(5, 4);

      try {
         oStmt.execute();
         oCreditCardReversalBO.setErrorCode("" + oStmt.getInt(5));
      } catch (Exception var15) {
         var15.printStackTrace();

         try {
            oConn.rollback();
         } catch (Exception var14) {
         }
      } finally {
         if (oStmt != null) {
            oStmt.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getMessageInformation(String sUserID, String sSessionID) throws Exception {
      Connection oConn = null;
      Statement oStmt = null;
      ResultSet oRs = null;
      ArrayList aTmpList = new ArrayList();
      oConn = DBCPNewConnection.getConnection();
      oStmt = oConn.createStatement();
      new StringBuffer();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();

      try {
         StringBuffer sql = new StringBuffer();
         sql.append("SELECT NVL(ERRMSG,' ')");
         sql.append("FROM MyBank.SY_MESSAGE ");
         sql.append("WHERE MAILID = '");
         sql.append(sUserID);
         sql.append("' AND SESSIONID = '");
         sql.append(sSessionID);
         sql.append("' ORDER BY SERNUM ");
         oRs = oStmt.executeQuery(sql.toString());

         while(oRs.next()) {
            CreditCardReversalBO oLoanDisbursementListBO = new CreditCardReversalBO();
            oLoanDisbursementListBO.setErrorMessage(oRs.getString(1));
            aTmpList.add(oLoanDisbursementListBO);
         }

         oCreditCardReversalBO.setList(aTmpList);
      } catch (Exception var13) {
         var13.printStackTrace();
      } finally {
         if (oRs != null) {
            oRs.close();
         }

         if (oStmt != null) {
            oStmt.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getMenuList(String sUserID, String sSessionID) throws Exception {
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      Connection oConn = null;
      Statement oStmt = null;
      oConn = DBCPNewConnection.getConnection();
      oStmt = oConn.createStatement();
      ArrayList aMenuList = new ArrayList();
      ArrayList aMenuNameList = new ArrayList();
      ResultSet oRs = null;
      new StringBuffer();

      try {
         StringBuffer sql = new StringBuffer();
         sql.append("SELECT NVL(URL,' '), NVL(NODENAME,' ')");
         sql.append("FROM MyBank.SY_FAVORITES ");
         sql.append("WHERE MAILID = '");
         sql.append(sUserID);
         sql.append("' AND SESSIONID = '");
         sql.append(sSessionID);
         sql.append("' ORDER BY SERNUM ");
         oRs = oStmt.executeQuery(sql.toString());

         while(oRs.next()) {
            aMenuList.add(oRs.getString(1));
            aMenuNameList.add(oRs.getString(2));
         }

         oCreditCardReversalBO.setMenuList(aMenuList);
         oCreditCardReversalBO.setMenuNameList(aMenuNameList);
      } catch (Exception var17) {
         var17.printStackTrace();

         try {
            oConn.rollback();
         } catch (Exception var16) {
         }
      } finally {
         if (oStmt != null) {
            oStmt.close();
         }

         if (oRs != null) {
            oRs.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getNPSBOutgoingReversalInfoPro(String sUserID, String sSessionID, String sCompanyID, String sOperationMode, String sFromDate, String sToDate, String sStatus, String sSourceAccount, String sDestinationAccount, String sAmount) throws Exception {
      Connection oConn = null;
      oConn = DBCPNewConnection.getConnection();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      CallableStatement oStmt = oConn.prepareCall("{call IBANKING.dpr_ccard_outgoing_rev_inf(?,?,?,?,?,?,?,?,?,?,?,?)}");
      oStmt.setString(1, sUserID);
      oStmt.setString(2, sSessionID);
      oStmt.setString(3, sCompanyID);
      oStmt.setString(4, sOperationMode);
      oStmt.setString(5, sFromDate);
      oStmt.setString(6, sToDate);
      oStmt.setString(7, sStatus);
      oStmt.setString(8, sSourceAccount);
      oStmt.setString(9, sDestinationAccount);
      oStmt.setString(10, sAmount);
      oStmt.registerOutParameter(11, 4);
      oStmt.registerOutParameter(12, 12);

      try {
         oStmt.execute();
         oCreditCardReversalBO.setErrorCode("" + oStmt.getInt(11));
         oCreditCardReversalBO.setErrorMessage(oStmt.getString(12));
      } catch (Exception var21) {
         var21.printStackTrace();

         try {
            oConn.rollback();
         } catch (Exception var20) {
         }
      } finally {
         if (oStmt != null) {
            oStmt.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getNPSBOutgoingReversalInfoData(String sUserID, String sSessionID) throws Exception {
      Connection oConn = null;
      Statement oStmt = null;
      ResultSet oRs = null;
      ArrayList aTmpList = new ArrayList();
      oConn = DBCPNewConnection.getConnection();
      oStmt = oConn.createStatement();
      new StringBuffer();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();

      try {
         StringBuffer sql = new StringBuffer();
         sql.append("SELECT SERNUM, ");
         sql.append("NVL (AMOUNT, '') RRN_NUMBER,");
         sql.append("NVL (REFDAT, '') TRAN_DATE,");
         sql.append("NVL (BRANCD, '') SOURCE_ACC, ");
         sql.append("NVL (ACSTAT,'') SOURCE_ACC_TITLE,");
         sql.append("NVL (ACTYPE, '') DEST_ACC,");
         sql.append("NVL (ACTNUM, 0) TRAN_AMT, ");
         sql.append("NVL (CURDES, 0) DEBIT_DOCUMENT_NO, ");
         sql.append("NVL (EBNNAM, 0) REASON, ");
         sql.append("NVL (ACTTIT, '') TRAN_STATUS  ");
         sql.append("FROM IBANKING.DT_ACTLIST   ");
         sql.append("WHERE MAILID = '");
         sql.append(sUserID);
         sql.append("' AND SESSIONID = '");
         sql.append(sSessionID);
         sql.append("' ORDER BY SERNUM ");
         oRs = oStmt.executeQuery(sql.toString());

         while(oRs.next()) {
            CreditCardReversalBO oNPSBOutgoingReversalReverseListBO = new CreditCardReversalBO();
            oNPSBOutgoingReversalReverseListBO.setSerialNo(oRs.getString(1));
            oNPSBOutgoingReversalReverseListBO.setRRNNo(oRs.getString(2));
            oNPSBOutgoingReversalReverseListBO.setTransactionDate(oRs.getString(3));
            oNPSBOutgoingReversalReverseListBO.setSourceAccount(oRs.getString(4));
            oNPSBOutgoingReversalReverseListBO.setAccountTile(oRs.getString(5));
            oNPSBOutgoingReversalReverseListBO.setDestinationAccount(oRs.getString(6));
            oNPSBOutgoingReversalReverseListBO.setTransactionAmount(oRs.getString(7));
            oNPSBOutgoingReversalReverseListBO.setDebitDocNo(oRs.getString(8));
            oNPSBOutgoingReversalReverseListBO.setReason(oRs.getString(9));
            oNPSBOutgoingReversalReverseListBO.setStatus(oRs.getString(10));
            aTmpList.add(oNPSBOutgoingReversalReverseListBO);
         }

         oCreditCardReversalBO.setReversalList(aTmpList);
      } catch (Exception var13) {
         var13.printStackTrace();
      } finally {
         if (oRs != null) {
            oRs.close();
         }

         if (oStmt != null) {
            oStmt.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }

   public CreditCardReversalBO getExecutePro(String sUserID, String sSessionID, String sCompanyID, String sOperationMode, String sDocumentNo, String sAccountNo, String sTransactionDate, String sStatus) throws Exception {
      Connection oConn = null;
      oConn = DBCPNewConnection.getConnection();
      CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
      CallableStatement oStmt = oConn.prepareCall("{call IBANKING.DPR_NPS_OUTGOING_REV_EXE(?,?,?,?,?,?,?,?,?,?)}");
      oStmt.setString(1, sUserID);
      oStmt.setString(2, sSessionID);
      oStmt.setString(3, sCompanyID);
      oStmt.setString(4, sOperationMode);
      oStmt.setString(5, sDocumentNo);
      oStmt.setString(6, sAccountNo);
      oStmt.setString(7, sTransactionDate);
      oStmt.setString(8, sStatus);
      oStmt.registerOutParameter(9, 4);
      oStmt.registerOutParameter(10, 12);

      try {
         oStmt.execute();
         oCreditCardReversalBO.setErrorCode("" + oStmt.getInt(9));
         oCreditCardReversalBO.setErrorMessage(oStmt.getString(10));
      } catch (Exception var19) {
         var19.printStackTrace();

         try {
            oConn.rollback();
         } catch (Exception var18) {
         }
      } finally {
         if (oStmt != null) {
            oStmt.close();
         }

         DBCPNewConnection.releaseConnection(oConn);
      }

      return oCreditCardReversalBO;
   }
   
   
   public static void setKey(String myKey) throws NoSuchAlgorithmException {

       byte[] key = myKey.getBytes();
       key = Arrays.copyOf(key, 16);
       secretKey = new SecretKeySpec(key, "AES");
       
   }

   public static String encrypt(String strToEncrypt, String ENCRYPTION_KEY) {
       System.out.println("strToEncrypt : " + strToEncrypt);
       System.out.println("ENCRYPTION_KEY : " + ENCRYPTION_KEY);
       String hexString = "";
       try {
           IvParameterSpec iv = new IvParameterSpec(new byte[16]);
           setKey(ENCRYPTION_KEY);
           Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
           cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
           byte[] arr = cipher.doFinal(strToEncrypt.getBytes());
           hexString=  Hex.encodeHexString(arr);
        
           return hexString;
       } catch (Exception e) {
           System.out.println("Error while encrypting: " + e.toString());
       }
       return null;
   }
   
   public CreditCardReversalBO getCreditCardAPI(CreditCardReversalBO model) {
	   CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();
	   String ENCRYPTION_KEY = "MyEncryptionKeyERA_IBAPP_BANKING";
       String responseString = "";
       String responseCode = "";
       String responseMessage = "";
       try {
           JSONObject json = new JSONObject();
           json.put("key", ENCRYPTION_KEY);
           json.put("terminalID", "webadmin");
           json.put("operationMode", "I");

           String jsonString = json.toString();
           System.out.println("jsonString = " + jsonString);
           String encrypt = encrypt(jsonString, ENCRYPTION_KEY);
           System.out.println("encrypt = " + encrypt);

           String url = "http://192.183.155.22/ERABAsiaCreditCardTransfer/V01/tokenGenerateCreditCardMoneyTransfer";
           System.out.println("url = " + url);

           CloseableHttpClient httpClient = HttpClients.createDefault();
           HttpPost httppost = new HttpPost(url);
           JSONObject jsonObject = new JSONObject();
           StringEntity enttity = new StringEntity(encrypt);
           httppost.setEntity(enttity);

           CloseableHttpResponse httpResponse = httpClient.execute(httppost);

           int status = httpResponse.getStatusLine().getStatusCode();

           if (status == 200) {
               System.out.println("status = " + status);
               responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
               System.out.println("response = " + responseString);

               JSONObject obj = new JSONObject(responseString);

               String keyId = "";
               String key1 = "";
               String key2 = "";

               if (obj.has("keyId") && !obj.isNull("keyId")) {
                   keyId = (String) obj.get("keyId");
               }
              
               if (obj.has("key1") && !obj.isNull("key1")) {
                   key1 = (String) obj.get("key1");
                   
               }
               
               if (obj.has("key2") && !obj.isNull("key2")) {
                   key2 = (String) obj.get("key2");
               } 
               
               model.setKeyId(keyId);
               model.setKey1(key1);
               model.setKey2(key2);
               
               
               CreditCardReversalBO bo = responseCreditCardAPI(model);
               System.out.println("errorCode 111 =>" + bo.getErrorCode());
               System.out.println("errorCode 111 =>" + bo.getErrorMessage());
               
              
               oCreditCardReversalBO.setErrorCode(bo.getErrorCode());
        	   oCreditCardReversalBO.setErrorMessage(bo.getErrorMessage());
        	   return oCreditCardReversalBO;

       } else {
    	   oCreditCardReversalBO.setErrorCode("1");
    	   oCreditCardReversalBO.setErrorMessage("Credit Card API problem " + status);
       }

   }
   catch (Exception var17) {
    	  oCreditCardReversalBO.setErrorCode("1");
    	  oCreditCardReversalBO.setErrorMessage("Server not Found.. Please check Internet Connection...");
   }
   return oCreditCardReversalBO;
}

   
         
   
 
	public static String encryptText(byte[] keyID1, byte[] keyID2, String plainText) throws Exception {

		byte[] plaintext = plainText.getBytes();
		Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		SecretKeySpec myKey = new SecretKeySpec(keyID1, "DESede");
		IvParameterSpec ivspec = new IvParameterSpec(keyID2);
		c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
		byte[] cipherText = c3des.doFinal(plaintext);
		return Base64.getEncoder().encodeToString(cipherText);
	}
   
      
   
   
	public CreditCardReversalBO responseCreditCardAPI(CreditCardReversalBO model) {
		CreditCardReversalBO oCreditCardReversalBO = new CreditCardReversalBO();

		String ENCRYPTION_KEY = "MyEncryptionKeyERA_IBAPP_BANKING";
		String sEncryptedPassword = "";
		String responseString = "";
		String sPassword = "BALUSER";		
	    String errorCode = "";
        String errorMessage = "";
        String authResCode = "";
        String authResMessage = "";
        String approvalCode = "";
        String debitTransactionID = "";
        String creditTransactionID = "";
		try {
			sEncryptedPassword = CreditCardReversalDAO.encryptText(model.getKey1().getBytes(), model.getKey2().getBytes(), sPassword);
			System.out.println("sEncryptedPassword " + sEncryptedPassword);
			
			JSONObject json = new JSONObject();
			json.put("encryptedPassword", sEncryptedPassword);

			json.put("key", model.getKeyId());
			json.put("itclTranID", model.getDebitDocNo());
			json.put("requestNo", model.getRRNNo());
			json.put("reason", model.getReason());
			json.put("terminalID", "admin");
			json.put("mailID", model.getMailId());
			json.put("sessionID", model.getSessionId());
			json.put("operationMode", "I");

			String jsonString = json.toString();
			System.out.println("jsonString = " + jsonString);
			String encrypt = encrypt(jsonString, ENCRYPTION_KEY);
			System.out.println("encrypt = " + encrypt);

			String url = "http://192.183.155.22/ERABAsiaCreditCardTransfer/V01/creditCardTransferReversal";
			System.out.println("url = " + url);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(url);
			JSONObject jsonObject = new JSONObject();
			StringEntity enttity = new StringEntity(encrypt);
			httppost.setEntity(enttity);

			CloseableHttpResponse httpResponse = httpClient.execute(httppost);
			
			int status = httpResponse.getStatusLine().getStatusCode();
			
			  System.out.println("httpResponse = " + status);
			  
	        if (status == 200) {
	        	
	      
			responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			System.out.println("response = " + responseString);
			
			JSONObject obj = new JSONObject(responseString);
			System.out.println("obj test= " + obj);

         
             

             if (obj.has("errorMessage") && !obj.isNull("errorMessage")) {
            	 errorMessage = (String) obj.get("errorMessage");      
            	// oCreditCardReversalBO.setErrorMessage(errorMessage);
             }
                        
             if (obj.has("errorCode") && !obj.isNull("errorCode")) {
            	 errorCode = (String) obj.get("errorCode");      
            	// oCreditCardReversalBO.setErrorCode(errorCode);
                 
             } 
             System.out.println("errorCode = >" + errorCode);
             System.out.println("errorMessage => " + errorMessage);
             
             if ("0".equals(errorCode)) {          
            	 
            	 
                 System.out.println("errorCode 11= >" + errorCode);
                 System.out.println("errorMessage 11=> " + errorMessage);
            	 
                /* if (obj.has("authResCode") && !obj.isNull("authResCode")) {
                	 authResCode = obj.getString("authResCode");
                 	oCreditCardReversalBO.setAuthResCode(authResCode);

                 }
                 if (obj.has("authResMessage") && !obj.isNull("authResMessage")) {
                	 authResMessage = obj.getString("authResMessage");
                 	oCreditCardReversalBO.setAuthResMessage(authResMessage);

                 }

                 if (obj.has("approvalCode") && !obj.isNull("approvalCode")) {
                	 approvalCode = obj.getString("approvalCode");
                 	oCreditCardReversalBO.setApprovalCode(approvalCode);

                 }

                 if (obj.has("debitTransactionID") && !obj.isNull("debitTransactionID")) {
                	 debitTransactionID = obj.getString("debitTransactionID");
                 	oCreditCardReversalBO.setDebitTransactionID(debitTransactionID);
                 }
                 if (obj.has("creditTransactionID") && !obj.isNull("creditTransactionID")) {
                	 creditTransactionID = obj.getString("creditTransactionID");
                 	oCreditCardReversalBO.setCreditTransactionID(creditTransactionID);
                 }*/
                 
                 oCreditCardReversalBO.setErrorCode(errorCode);
                 oCreditCardReversalBO.setErrorMessage(errorMessage);
                 return oCreditCardReversalBO;

                 
             } else {
            	 oCreditCardReversalBO.setErrorCode(errorCode);
            	 oCreditCardReversalBO.setErrorMessage(errorMessage);
            	 return oCreditCardReversalBO;
             }
             
	        } else {
	        	oCreditCardReversalBO.setErrorCode("1");
	        	oCreditCardReversalBO.setErrorMessage("API does not response properly...." );
            }
		
	   
		}catch (Exception var17) {
			var17.printStackTrace();
			oCreditCardReversalBO.setErrorCode("1");
			oCreditCardReversalBO.setErrorMessage("Server not Found.. Please check Internet Connection...");
		}

		return oCreditCardReversalBO;
	}
  
}
