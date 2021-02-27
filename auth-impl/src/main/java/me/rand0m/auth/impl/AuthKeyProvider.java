package me.rand0m.auth.impl;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.mcg.jwt.PrivateKeyProvider;
import com.mcg.jwt.PublicKeyProvider;
import com.mcg.jwt.crypto.DefaultKeyGenerator;
import com.mcg.jwt.entities.EncodedPrivateKey;
import com.mcg.jwt.entities.EncodedPublicKey;

@Component
public class AuthKeyProvider implements PrivateKeyProvider, PublicKeyProvider {

	
	private DefaultKeyGenerator kg = new DefaultKeyGenerator("EC");
	private EncodedPrivateKey privateKey;
	private EncodedPublicKey publicKey;


	@PostConstruct
	public void init() throws NoSuchAlgorithmException {
		KeyPair kp = kg.generateKeyPair();
		
		long serial = System.currentTimeMillis();
		
		EncodedPrivateKey eprivk = new EncodedPrivateKey();
		eprivk.setAlgorithm(kg.getAlgorithm());
		eprivk.setSerial(serial);
		eprivk.setPrivateKey(kp.getPrivate());
		eprivk.setNotAfter(DateUtils.addYears(new Date(), 3));
		
		EncodedPublicKey epubk = new EncodedPublicKey();
		epubk.setAlgorithm(kg.getAlgorithm());
		epubk.setSerial(serial);
		epubk.setPublicKey(kp.getPublic());
		epubk.setNotAfter(DateUtils.addYears(new Date(), 3));
		
		this.publicKey = epubk;
		this.privateKey = eprivk;
	}


	@Override
	public EncodedPublicKey getPublicKey(long arg0) throws NoSuchAlgorithmException {
		return this.publicKey;
	}


	@Override
	public EncodedPrivateKey getPrivateKey() throws NoSuchAlgorithmException {
		return this.privateKey;
	}
	
}
