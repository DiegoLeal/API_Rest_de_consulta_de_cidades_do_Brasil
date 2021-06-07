package com.cities.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringLocalizacaoUtilsTest {

  @Test
  public void shouldExtractGeoLocationsFormString() {
    String geoLocation = "(123, 321)";

    Double[] transform = StringLocalizacaoUtils.transform(geoLocation);

    assertEquals(123.0, transform[0], "Not equal");
    assertEquals(321, transform[1], "Not equal");
  }
}
