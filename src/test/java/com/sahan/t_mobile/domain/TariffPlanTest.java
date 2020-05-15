package com.sahan.t_mobile.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sahan.t_mobile.web.rest.TestUtil;

public class TariffPlanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TariffPlan.class);
        TariffPlan tariffPlan1 = new TariffPlan();
        tariffPlan1.setId(1L);
        TariffPlan tariffPlan2 = new TariffPlan();
        tariffPlan2.setId(tariffPlan1.getId());
        assertThat(tariffPlan1).isEqualTo(tariffPlan2);
        tariffPlan2.setId(2L);
        assertThat(tariffPlan1).isNotEqualTo(tariffPlan2);
        tariffPlan1.setId(null);
        assertThat(tariffPlan1).isNotEqualTo(tariffPlan2);
    }
}
