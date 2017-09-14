package com.ea.trade.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

import org.apache.commons.lang.StringUtils;

import com.ea.trade.thirdpart.hele.utils.Base64;


public class DES
{
	private static String desDefaultKey = "yiyatong";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

//    static{
////    	ResourceBundle resourceBundle = ResourceBundle.getBundle("cfg_"+System.getenv("cfg.env")+".properties");
////    	desDefaultKey = resourceBundle.getString("desDefaultKey");
//    	
//    	Properties prop = new Properties();  
//    	try {
//    		InputStream in = DES.class.getClassLoader().getResourceAsStream("cfg_"+System.getenv("cfg.env")+".properties");
//			prop.load(in);
//		} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//		}  
//    	desDefaultKey = prop.getProperty("desDefaultKey");
//    	System.out.println("desDefaultKey="+desDefaultKey);
//    }
//
//
//	public void setDesDefaultKey(String desDefaultKey) {
//		DES.desDefaultKey = desDefaultKey;
//	}

    
    /**
     * 默认构造方法，使用默认密钥
     * @throws Exception
     */
    public DES()
        throws Exception
    {
        this(desDefaultKey);
    }

	/**
     * 指定密钥构造方法
     * @param strKey 指定的密钥
     * @throws Exception
     */
    public DES(String strKey)
        throws Exception
    {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());

        encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        decryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * 加密字节数组
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public byte[] encryptBytes(byte[] arrB)
        throws Exception
    {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn)
        throws Exception
    {
        return encrypt(strIn, true);
    }
    
    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn, boolean isBase64)
        throws Exception
    {
    	return encrypt(strIn, isBase64, "UTF-8");
    }
    /**
     * 加密字符串
     * @param strIn 需加密的字符串
     * @param isBase64 是否同时采用Base64加密，true为是
     * @param charset 编码方式的字符串，例UTF-8
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn, boolean isBase64,String charset)
    throws Exception
    {
    	if(strIn==null || "".equals(strIn)){
    		return strIn;
    	}
    	byte[] b1 = null;
    	byte[] b = null;
    	if(StringUtils.isNotEmpty(charset)){
    		b1 = strIn.getBytes(charset);
    	}else{
    		b1 = strIn.getBytes();
    	}
		int l = b1.length%8;
		if (l!=0){
			int l1= 8 - l;
			b = new byte[b1.length+l1];
			/*int i=0;
			for(i=0;i<b.length;i++){
				b1[i]=b[1];
			}*/
			System.arraycopy(b1, 0, b, 0, b1.length);
			for(int j=0;j<l1;j++){
				b[b1.length+j]=0;
			}
		}else{
			b=b1;
		}
    	if(isBase64){
    		if(StringUtils.isNotEmpty(charset)){
    			return new String(Base64.encode(encryptBytes(b)));
    		}else{
    			return new String(Base64.encode(encryptBytes(b)));
    		}
    	}else{
    		if(StringUtils.isNotEmpty(charset)){
    			return byteArr2HexStr(encryptBytes(b));
    		}else{
    			return byteArr2HexStr(encryptBytes(b));
    		}
    	}	
    }

    /**
     * 解密字节数组
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decryptBytes(byte[] arrB)
        throws Exception
    {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn)
        throws Exception
    {
        return decrypt(strIn, true);
    }

    
    /**
     * 解密字符串
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn, boolean isBase64)
        throws Exception
    {
    	return decrypt(strIn, isBase64, "UTF-8");

    }
    /**
     * 解密字符串
     * @param strIn 需解密的字符串
     * @param isBase64 是否同时采用Base64解密，true为是
     * @param charset 编码方式的字符串，例UTF-8
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn, boolean isBase64,String charset)
    throws Exception
    {
    	if(strIn==null || "".equals(strIn)){
    		return strIn;
    	}
    	if(isBase64){
    		if(StringUtils.isNotEmpty(charset)){
    			return new String(decryptBytes(Base64.decode(strIn)),charset);
    		}else{
    			return new String(decryptBytes(Base64.decode(strIn)));
    		}
    	}else{
    		if(StringUtils.isNotEmpty(charset)){
    			return new String(decryptBytes(hexStr2ByteArr(strIn)),charset);
    		}else{
    			return new String(decryptBytes(hexStr2ByteArr(strIn)));
    		}
    		
    	}	
    }
    
    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位
     * 不足8位时后面补0，超出8位只取前8位
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp)
        throws Exception
    {
        //创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        //将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
        {
            arrB[i] = arrBTmp[i];
        }

        //生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * 单元测试方法
     * @param args
     */
    public static void main(String[] args)
    {
	    String password; 
	    boolean redo=false;
	    
	    do{

	        try
	        {
	            InputStreamReader stdin = new InputStreamReader(System.in);//键盘输入   
	            BufferedReader bufin = new BufferedReader(stdin); 
	        	System.out.print   ("请输入字符：   ");   
	        	password = bufin.readLine();        	        	
	            DES des = new DES();
//	            password= des.encrypt(password);            
//	            System.out.println("加密后:"+password);	 
	            password= des.decrypt(password);            
	            System.out.println("解密后:"+password);	
	            System.out.print   ("需要继续吗?(Y/N)：   ");
	            stdin = new InputStreamReader(System.in);//键盘输入   
	            bufin = new BufferedReader(stdin); 
	            String flag = bufin.readLine().toUpperCase();
	            redo="Y".equals(flag)?true:false;
	            	            
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }	    	

	    } while(redo);
	      
        
    }
    
    /**
     * 将byte数组转换为表示16进制值的字符串，
     * 如：byte[]{8,18}转换为：0813，
     * 和public static byte[] hexStr2ByteArr(String strIn)
     * 互为可逆的转换过程
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:zhangji@aspire-tech.com">ZhangJi</a>
     */
    private String byteArr2HexStr(byte[] arrB)
        throws Exception
    {
        int iLen = arrB.length;
        //每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++)
        {
            int intTmp = arrB[i];
            //把负数转换为正数
            while (intTmp < 0){
                intTmp = intTmp + 256;
            }
            //小于0F的数需要在前面补0
            if (intTmp < 16){
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }


    /**
     * 将表示16进制值的字符串转换为byte数组，
     * 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:zhangji@aspire-tech.com">ZhangJi</a>
     */
    private byte[] hexStr2ByteArr(String strIn)
        throws Exception
    {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        //两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2)
        {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
}