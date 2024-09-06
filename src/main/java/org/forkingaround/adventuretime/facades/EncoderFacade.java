package org.forkingaround.adventuretime.facades;

import org.forkingaround.adventuretime.facades.encryptations.Base64Encoder;
import org.forkingaround.adventuretime.implementations.IEncryptFacade;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncoderFacade implements IEncryptFacade {

    PasswordEncoder bCryptPasswordEncoder;
    Base64Encoder base64Encoder;

    public EncoderFacade(PasswordEncoder bCryptPasswordEncoder, Base64Encoder base64Encoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.base64Encoder = base64Encoder;
    }

    @Override
    public String encode(String type, String data) {
        String dataEncrypted = "";

        if (type == "bcrypt") dataEncrypted = bCryptPasswordEncoder.encode(data);
        if (type == "base64") dataEncrypted = base64Encoder.encode(data);

        return dataEncrypted;
    }

    @Override
    public String decode(String type, String data) {
        String dataDecoded = "";

        if (type == "base64") dataDecoded = base64Encoder.decode(data);

        return dataDecoded;
    }

}
