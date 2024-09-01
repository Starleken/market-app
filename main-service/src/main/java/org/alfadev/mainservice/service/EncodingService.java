package org.alfadev.mainservice.service;

public interface EncodingService {

    String encode(String notEncoded);

    boolean isMatch(String notEncoded, String encoded);
}
