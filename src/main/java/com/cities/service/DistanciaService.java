package com.cities.service;

import com.cities.model.Cidade;
import com.cities.repository.CidadeRepository;
import com.cities.utils.StringLocalizacaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;
import static java.lang.Math.sqrt;

@Service
public class DistanciaService {

    private final CidadeRepository cidadeRepository;
    Logger log = LoggerFactory.getLogger(DistanciaService.class);

    public DistanciaService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    /**
     * 1st option
     *
     * @param city1
     * @param city2
     * @param unit
     * @return
     */
    public Double distanceUsingMath(final Long city1, final Long city2, final EarthRadius unit) {
        log.info("distanceUsingMath({}, {}, {})", city1, city2, unit);
        final List<Cidade> cities = cidadeRepository.findAllById((Arrays.asList(city1, city2)));

        final Double[] location1 = StringLocalizacaoUtils.transform(cities.get(0).getGeolocation());
        final Double[] location2 = StringLocalizacaoUtils.transform(cities.get(1).getGeolocation());

        return doCalculation(location1[0], location1[1], location2[0], location2[1], unit);
    }

    /**
     * 2nd option
     *
     * @param city1
     * @param city2
     * @return
     */
    public Double distanceByPointsInMiles(final Long city1, final Long city2) {
        log.info("nativePostgresInMiles({}, {})", city1, city2);
        return cidadeRepository.distanceByPoints(city1, city2);
    }

    /**
     * 3rd option
     *
     * @param city1
     * @param city2
     * @param unit
     * @return
     */
    public Double distanceUsingPoints(final Long city1, final Long city2, final EarthRadius unit) {
        log.info("distanceUsingPoints({}, {}, {})", city1, city2, unit);
        final List<Cidade> cities = cidadeRepository.findAllById((Arrays.asList(city1, city2)));

        Point p1 = cities.get(0).getLocation();
        Point p2 = cities.get(1).getLocation();

        return doCalculation(p1.getX(), p1.getY(), p2.getX(), p2.getY(), unit);
    }

    /**
     * 4th option
     *
     * @param city1
     * @param city2
     * @return
     */
    public Double distanceByCubeInMeters(Long city1, Long city2) {
        log.info("distanceByCubeInMeters({}, {})", city1, city2);
        final List<Cidade> cities = cidadeRepository.findAllById((Arrays.asList(city1, city2)));

        Point p1 = cities.get(0).getLocation();
        Point p2 = cities.get(1).getLocation();

        return cidadeRepository.distanceByCube(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    private double doCalculation(final double lat1, final double lon1, final double lat2,
                                 final double lng2, final EarthRadius earthRadius) {
        double lat = toRadians(lat2 - lat1);
        double lon = toRadians(lng2 - lon1);
        double a = sin(lat / 2) * sin(lat / 2) + cos(toRadians(lat1)) * cos(toRadians(lat2)) * sin(lon / 2) * sin(lon / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return earthRadius.getValue() * c;
    }
}

