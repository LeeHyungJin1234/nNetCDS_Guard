package nnsp.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import org.w3c.dom.*;
import javax.xml.parsers.*; 
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
public class XmlUtil {
	public static void setServerXml(String fileName, String pw, String secret) {
		try {	
			File file = new File(fileName);
			pw = nnsp.util.XmlUtil.encrypt(pw, secret);
			
			//Create instance of DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			 
			//Get the DocumentBuilder
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			 
			//Using existing XML Document
			Document doc = docBuilder.parse(file);
			 
			//create the root element
			NodeList connectorList = doc.getElementsByTagName("Connector");
			for (int i = 0, len = connectorList.getLength(); i < len; i++) {
		       Element elm = (Element)connectorList.item(i);
		       if (elm.getAttribute("port").equals("8443")) {
		    	   elm.setAttribute("SSLPassword", pw);
		    	   elm.setAttribute("protocol", "release.CustomHttp11AprProtocol");
		       }
			}
			
			 
			//create child element
			//Element childElement = doc.createElement("number");
			 
			//Add the attribute to the child
			//childElement.setAttribute("id","3");
			//root.appendChild(childElement);
			
			//set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
 
			//create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();
 
			OutputStream f0;
			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream(fileName);
			for(int i=0;i<buf .length;i++) {
			   f0.write(buf[i]);
			}
			f0.close();
			buf = null;
		}
		catch(SAXException e) {
			e.printStackTrace();		
		}
		catch(IOException e) {
			e.printStackTrace();		
		}
		catch(ParserConfigurationException e) {
			e.printStackTrace();		
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//암호화
	private static SecretKeySpec secretKey;
    private static byte[] key;
    private static byte[] ivBytes = { 0x4E, 0x4E, 0x53, 0x50, 0x4E, 0x4E, 0x53, 0x50, 0x4E, 0x4E, 0x53, 0x50, 0x4E, 0x4E, 0x53, 0x50 };

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 32);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
}