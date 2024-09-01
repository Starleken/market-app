package org.alfadev.mainservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.alfadev.mainservice.service.EncodingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncodingServiceImpl implements EncodingService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String notEncoded) {
        return passwordEncoder.encode(notEncoded);
    }

    @Override
    public boolean isMatch(String notEncoded, String encoded) {
        return passwordEncoder.matches(notEncoded, encoded);
    }
}
