package org.alfadev.mainservice.service;

import org.alfadev.mainservice.BaseIntegrationTest;
import org.alfadev.mainservice.core.utils.FakerUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.alfadev.mainservice.core.utils.FakerUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class EncodingServiceTest extends BaseIntegrationTest {

    @Autowired
    private EncodingService encodingService;

    @Test
    void testEncode_happyPath() {
        //given
        var toEncode = FAKER.internet().password();

        //when
        var encoded = encodingService.encode(toEncode);

        //then
        assertNotEquals(toEncode, encoded);
    }

    @Test
    void testEncode_whenEncodeSamePasswords_thenReturnDifferentEncoded() {
        //given
        var password = FAKER.internet().password();

        //when
        var encoded = encodingService.encode(password);
        var secEncoded = encodingService.encode(password);

        //then
        assertNotEquals(encoded, secEncoded);
    }

    @Test
    void testIsMatch_happyPath() {
        //given
        var notEncoded = FAKER.internet().password();
        var encoded = encodingService.encode(notEncoded);

        //when
        var isMatch = encodingService.isMatch(notEncoded, encoded);

        //then
        assertTrue(isMatch);
        assertNotEquals(notEncoded, encoded);
    }

    @Test
    void testIsMatch_whenTextsIsNotMatch_thenReturnFalse() {
        //given
        var notEncoded = FAKER.internet().password();
        var encoded = encodingService.encode(notEncoded) + " fake";

        //when
        var isMatch = encodingService.isMatch(notEncoded, encoded);

        //then
        assertFalse(isMatch);
    }
}
