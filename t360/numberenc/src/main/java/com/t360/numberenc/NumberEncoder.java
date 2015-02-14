package com.t360.numberenc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

/**
 * Created by asa on 07.02.2015.
 */
public class NumberEncoder {
    private static final Logger LOG = LoggerFactory.getLogger(NumberEncoder.class);

    public static final String NUM_REGEX = "[0-9-/]+";

    private static int NUM_MAX_LENGTH = 50;

    private Dictionary dict;

    public NumberEncoder(Dictionary dict) {
        this.dict = dict;
    }

    public void encode(Path from, Path to) throws IOException {
        try (Writer writer = new PrintWriter(new BufferedWriter(new FileWriter(to.toString())));
             Stream<String> numbers = lines(from))
        {
            encode(numbers)
                    .forEach((s) -> {
                        LOG.debug("Write to output: {}", s);
                        try {
                            writer.write(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public Stream<String> encode(Stream<String> numbers) {
        return numbers.filter(s -> s.length() <= NUM_MAX_LENGTH && s.matches(NUM_REGEX))
                .flatMap((n) -> dict.collect(n)
                        .filter((s) -> s.length() > 0 && !(s.length() == 1 && Character.getNumericValue(s.charAt(0)) >= 0))
                        .map((s) -> n + ": " + s));
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            LOG.info("Wrong number of arguments, current: {}, need: {}", args.length, 3);
            return;
        }
        try (Stream<String> numbers = lines(Paths.get(args[0]))) {
            NumberEncoder enc = new NumberEncoder(new Dictionary(numbers));
            enc.encode(Paths.get(args[1]), Paths.get(args[2]));
        }
    }
}
