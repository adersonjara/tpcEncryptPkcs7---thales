package com.example.tpcEncryptPkcs7;

import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.bc.BcCMSContentEncryptorBuilder;
import org.bouncycastle.cms.jcajce.JceKeyTransEnvelopedRecipient;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaAlgorithmParametersConverter;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.MGF1ParameterSpec;
import java.util.Collection;

@Service
public class TpcEncryptPkcs7 {

    private static  PrivateKey privKey;
    private static  PublicKey pubKey;

    public TpcEncryptPkcs7() {
        // Asegurar que BouncyCastleProvider está registrado como proveedor de seguridad
        Security.addProvider(new BouncyCastleProvider());
    }

    public void setPrivateKey(PrivateKey privKey) {
        TpcEncryptPkcs7.privKey = privKey;
    }

    public void setPublicKey(PublicKey pubKey) {
        TpcEncryptPkcs7.pubKey = pubKey;
    }

    public PrivateKey getPrivateKey() {
        return privKey;
    }

    public PublicKey getPublicKey() {
        return pubKey;
    }

    public byte[] encrypt(String plainData) throws Exception {
        if (pubKey == null) {
            throw new IllegalStateException("Public key not set");
        }
        return encryptPKCS7(plainData.getBytes("UTF-8"), pubKey);
    }

    public byte[] decrypt(byte[] encryptedData) throws Exception {
        if (privKey == null) {
            throw new IllegalStateException("Private key not set");
        }
        return decryptPKCS7(encryptedData, privKey);
    }

    private byte[] encryptPKCS7(byte[] plainData, PublicKey pubKey) throws Exception {
        CMSEnvelopedDataGenerator gen = new CMSEnvelopedDataGenerator();

        JcaAlgorithmParametersConverter paramsConverter = new JcaAlgorithmParametersConverter();
        OAEPParameterSpec oaepParamSpec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        AlgorithmIdentifier algoId = paramsConverter.getAlgorithmIdentifier(PKCSObjectIdentifiers.id_RSAES_OAEP, oaepParamSpec);

        JceKeyTransRecipientInfoGenerator recipInfo = new JceKeyTransRecipientInfoGenerator("a_key_id".getBytes(), algoId, pubKey)
                .setProvider("BC");

        gen.addRecipientInfoGenerator(recipInfo);

        CMSProcessableByteArray data = new CMSProcessableByteArray(plainData);
        BcCMSContentEncryptorBuilder builder = new BcCMSContentEncryptorBuilder(CMSAlgorithm.AES256_CBC);

        CMSEnvelopedData enveloped = gen.generate(data, builder.build());

        return enveloped.getEncoded();
    }

    private byte[] decryptPKCS7(byte[] encryptedData, PrivateKey privKey) throws Exception {
        CMSEnvelopedData enveloped = new CMSEnvelopedData(encryptedData);
        Collection<RecipientInformation> recip = enveloped.getRecipientInfos().getRecipients();
        KeyTransRecipientInformation rinfo = (KeyTransRecipientInformation) recip.iterator().next();
        return rinfo.getContent(new JceKeyTransEnvelopedRecipient(privKey).setProvider("BC"));
    }

}
