package com.upi.sdk.domain;

import java.math.BigDecimal;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class InputUpdateBeneficiary {

    private String beneficiaryId;
    private String beneficiaryName;
    //LVB Requirement.....
    private BigDecimal maxLimit;

    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(BigDecimal maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }



    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }





}
