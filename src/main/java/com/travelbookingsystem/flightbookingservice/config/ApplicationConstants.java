package com.travelbookingsystem.flightbookingservice.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final String ROLE_EMPLOYEE = "employee";

    public static final String ROLE_CUSTOMER = "customer";

    public static final String AUTHORITY_EMPLOYEE = "ROLE_employee";

    public static final String AUTHORITY_CUSTOMER = "ROLE_customer";

}
