package com.example.backend.values;

public enum AccountResultStatusValues {
    PASSWORD_WRONG(-1),
    NO_ACCOUNT(-2),
    PASSWORD_VAILDATION_WRONG(-3),
    OTHER(-999);

    private final long result;

    private AccountResultStatusValues(long result) {
        this.result = result;
    }

    public long getResult() {
        return result;
    }

}
