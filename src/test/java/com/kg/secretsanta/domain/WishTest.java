package com.kg.secretsanta.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.kg.secretsanta.web.rest.TestUtil;

public class WishTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wish.class);
        Wish wish1 = new Wish();
        wish1.setId(1L);
        Wish wish2 = new Wish();
        wish2.setId(wish1.getId());
        assertThat(wish1).isEqualTo(wish2);
        wish2.setId(2L);
        assertThat(wish1).isNotEqualTo(wish2);
        wish1.setId(null);
        assertThat(wish1).isNotEqualTo(wish2);
    }
}
