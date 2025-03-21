package nnsp.common;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.dbcp2.BasicDataSource;

import java.security.InvalidKeyException;
import java.util.logging.Logger;

public class DecryptDataSource extends BasicDataSource{
	
	public Logger getParentLogger() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDriverClassName(String driverClassName) {
        // TODO Auto-generated method stub
        String firstSetup = nnsp.common.Config.getInstance().getProperty("nncCfg.common.firstSetup");
        if (firstSetup != null && firstSetup.equals("1")) {
        	super.setDriverClassName((driverClassName));
        } else {
            super.setDriverClassName((driverClassName));
        }
    }

    @Override
    public void setPassword(String password) {
        // TODO Auto-generated method stub
        String firstSetup = nnsp.common.Config.getInstance().getProperty("nncCfg.common.firstSetup");

        if (firstSetup != null && firstSetup.equals("1")) {
            super.setPassword(encryptor(password));
        } else {
            super.setPassword((password));
        }

    }

    @Override
    public synchronized void setUrl(String url) {
        // TODO Auto-generated method stub
        String firstSetup = nnsp.common.Config.getInstance().getProperty("nncCfg.common.firstSetup");
        if (firstSetup != null && firstSetup.equals("1")) {
        	super.setUrl((url));
        } else {
            super.setUrl((url));
        }

    }

    @Override
    public void setUsername(String username) {
        // TODO Auto-generated method stub
        String firstSetup = nnsp.common.Config.getInstance().getProperty("nncCfg.common.firstSetup");
        if (firstSetup != null && firstSetup.equals("1")) {
        	super.setUsername((username));
        } else {
            super.setUsername((username));
        }
    }

    public String encryptor(String param) {
        String strResult = "";
        if (param != null && !param.equals("")) {

            String strDek = nnsp.services.NcConfigService.getInstance().getDek();

            if (strDek != null) {
                try {
                    strResult = nnsp.security.ARIAUtil.ariaDecrypt(param, strDek);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (DecoderException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return strResult;
    }
}