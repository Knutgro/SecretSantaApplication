package com.kg.secretsanta.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kg.secretsanta.web.rest.TestUtil;

public class GiftTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gift.class);
        Gift gift1 = new Gift();
        gift1.setId(1L);
        Gift gift2 = new Gift();
        gift2.setId(gift1.getId());
        assertThat(gift1).isEqualTo(gift2);
        gift2.setId(2L);
        assertThat(gift1).isNotEqualTo(gift2);
        gift1.setId(null);
        assertThat(gift1).isNotEqualTo(gift2);
    }
}
