package org.rochlitz.K2Converter.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterAnalyzer {

    private static final Logger LOG = LoggerFactory.getLogger(CharacterAnalyzer.class);

    public static void main(String[] args) {
        String filePath = "/home/andre/IdeaProjects/K2Converter/src/test/resources/GES010413/FAM_L.GES";
        Map<Character, Integer> charCountMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int character;
            while ((character = reader.read()) != -1) {
                char ch = (char) character;
//                LOG.info(String.valueOf(ch));
                charCountMap.put(ch, charCountMap.getOrDefault(ch, 0) + 1);
            }
        } catch (IOException e) {
            LOG.error("Error reading file", e);
        }

        // Ergebnisse anzeigen
        LOG.info("Character Analysis:");

        for (Map.Entry<Character, Integer> entry : charCountMap.entrySet()) {
            LOG.info("Character: '%s' (ASCII: %d), Count: %d%n", entry.getKey(), (int) entry.getKey(), entry.getValue());
        }
    }
}
