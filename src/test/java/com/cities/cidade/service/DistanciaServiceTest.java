package com.cities.cidade.service;

import com.cities.model.Cidade;
import com.cities.repository.CidadeRepository;
import com.cities.service.DistanciaService;
import com.cities.service.EarthRadius;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Point;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class DistanciaServiceTest {

    @Autowired
    private DistanciaService service;

    @MockBean
    private CidadeRepository cidadeRepository;

    private Cidade saoCarlos;
    private Cidade ibate;

    @BeforeEach
    public void setUp() {
        ibate = new Cidade(4929L, "Ibaté", 26, 3519303, "(-21.95840072631836,-47.98820114135742)",
                new Point(-21.95840072631836, -47.98820114135742));
        saoCarlos =
                new Cidade(5254L, "São Carlos", 26, 3548906, "(-22.01740074157715,-47.88600158691406)",
                        new Point(-22.01740074157715, -47.88600158691406));
    }

    @Test
    public void shouldCalculateInMetersUsingMath() {
        given(cidadeRepository.findAllById(anyList())).willReturn(Arrays.asList(ibate, saoCarlos));

        Double distance = service.distanceUsingMath(4929L, 5254L, EarthRadius.METERS);

        assertThat(distance).isEqualTo(12426.810463475855);
    }

    @Test
    public void shouldCalculateInKilometersUsingMath() {
        given(cidadeRepository.findAllById(anyList())).willReturn(Arrays.asList(ibate, saoCarlos));

        Double distance = service.distanceUsingMath(4929L, 5254L, EarthRadius.KILOMETERS);

        assertThat(distance).isCloseTo(12.426, offset(0.001d));
    }

    @Test
    public void shouldCalculateInMilesUsingMath() {
        given(cidadeRepository.findAllById(anyList())).willReturn(Arrays.asList(ibate, saoCarlos));

        Double distance = service.distanceUsingMath(4929L, 5254L, EarthRadius.MILES);

        assertThat(distance).isCloseTo(7.71, offset(0.01d));
    }

    @Test
    public void shouldCalculateInMetersUsingPoints() {
        given(cidadeRepository.findAllById(anyList())).willReturn(Arrays.asList(ibate, saoCarlos));

        Double distance = service.distanceUsingPoints(4929L, 5254L, EarthRadius.METERS);

        assertThat(distance).isEqualTo(12426.810463475855);
    }
}