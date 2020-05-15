package com.sahan.t_mobile.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sahan.t_mobile.web.rest.TestUtil;

public class OrderTariffPlanTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderTariffPlan.class);
        OrderTariffPlan orderTariffPlan1 = new OrderTariffPlan();
        orderTariffPlan1.setId(1L);
        OrderTariffPlan orderTariffPlan2 = new OrderTariffPlan();
        orderTariffPlan2.setId(orderTariffPlan1.getId());
        assertThat(orderTariffPlan1).isEqualTo(orderTariffPlan2);
        orderTariffPlan2.setId(2L);
        assertThat(orderTariffPlan1).isNotEqualTo(orderTariffPlan2);
        orderTariffPlan1.setId(null);
        assertThat(orderTariffPlan1).isNotEqualTo(orderTariffPlan2);
    }
}
