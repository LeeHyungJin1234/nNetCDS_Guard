package nnsp.services;

import java.security.PrivateKey;
import org.springframework.stereotype.Component;

@Component 
public class NcSecurity {
	private PrivateKey privatekey;
	
	public void setPrivateKey(PrivateKey key) {
		privatekey = key;
	}
	public PrivateKey getPrivateKey() {
		return privatekey;
	}
}