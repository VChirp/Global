package com.gesua;

import com.gesua.Calculation.PairsStats;
import com.gesua.Calculation.TechStats;
import com.gesua.Calculation.StatsParser;
import com.gesua.Calculation.WaterStats;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class ParsersTest {

    private static final String WATERFILE = "water_from_pdf.tsv";
    private static final String PAIRSFILE = "pairs.tsv";
    private static final String TECHSFILE = "tech.tsv";
    private static final double DELTA = 0.000001;


    @Test
    public void testToMapWithStreams() throws Exception {
        Map<Double, WaterStats> map = StatsParser.toWaterMapByTemperature(StatsParser.parserWater(WATERFILE));
        assertEquals(68, map.get(90.0).getLamda(), DELTA);
    }

    @Test
    public void testToMapWithStreams2() throws Exception {
        Map<Double, WaterStats> map = StatsParser.toWaterMapByTemperature(StatsParser.parserWater(WATERFILE));
        assertEquals(977.8, map.get(70.0).getRo(), DELTA);
    }

    @Test
    public void testToMapPairsWithStreams() throws Exception {
        Map<Double, PairsStats> map = StatsParser.toPairsMapByTemperature(StatsParser.parserPairs(PAIRSFILE));
        assertEquals(2202.8, map.get(120.0).getR(), DELTA);
    }

    @Test
    public void testToMapTechWithStreams() throws Exception {
        Map<Double, TechStats> map = StatsParser.toTechsMapByF(StatsParser.parserTech(TECHSFILE));
        assertEquals("Nain",map.get(67.2).getName());
    }

    @Test
    public void getTechByF() throws Exception {
        Map<Double, TechStats> map = StatsParser.toTechsMapByF(StatsParser.parserTech(TECHSFILE));

        assertEquals("Теплообменник ВТА_H_SS1FV DN50", StatsParser.getTech(0.15646,map).getName());
    }
}