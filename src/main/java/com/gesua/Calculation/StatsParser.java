package com.gesua.Calculation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class StatsParser {

    static final int WATER_FILE_COLUMNS = 12;
    static final int PAIRS_FILE_COLUMNS = 11;
    static final int TECH_FILE_COLUMNS = 3;
    private static final String TAB_SEPARATOR = "\t";

    public static Map<Double, WaterStats> toWaterMapByTemperature(List<WaterStats> list) {
        return list.stream().collect(toMap(ws -> ws.t, ws -> ws));
    }

    public static Map<Double, PairsStats> toPairsMapByTemperature(List<PairsStats> list) {
        return list.stream().collect(toMap(ps -> ps.t, ps -> ps));
    }

    public static Map<Double, TechStats> toTechsMapByF(List<TechStats> list) {
        return list.stream().collect(toMap(ts -> ts.vnPlTrub, ts -> ts));
    }

    public static List<WaterStats> parserWater(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(StatsParser.class.getClassLoader().getResource(fileName).toURI());
        return Files.readAllLines(path).stream()
                .map(line -> line.split(TAB_SEPARATOR))
                .map(StatsParser::arrayToDoubleList)
                .map(WaterStats::fromList)
                .collect(toList());
    }

    public static List<PairsStats> parserPairs(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(StatsParser.class.getClassLoader().getResource(fileName).toURI());
        return Files.readAllLines(path).stream()
                .map(line -> line.split(TAB_SEPARATOR))
                .map(StatsParser::arrayToDoubleList)
                .map(PairsStats::fromList)
                .collect(toList());
    }

    public static List<TechStats> parserTech(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(StatsParser.class.getClassLoader().getResource(fileName).toURI());
        return Files.readAllLines(path).stream()
                .map(line -> line.split(TAB_SEPARATOR))
                .map(TechStats::fromArray)
                .collect(toList());
    }

    private static List<Double> arrayToDoubleList(String[] in) {
        return Arrays.stream(in)
                .map(str -> str.replace(',', '.'))
                .map(Double::parseDouble)
                .collect(toList());
    }

    public static TechStats getTech(double vnPlTrubki, Map<Double, TechStats> map) throws NoSuchFieldException {
        List<Double> keySet = new ArrayList<>(map.keySet());
        keySet.sort(Double::compareTo);
        double techKey = 0.0;
        for (double key : keySet) {
            if (vnPlTrubki <= key) {
                techKey = key;
                break;
            }
        }
        return map.get(techKey);
    }
}