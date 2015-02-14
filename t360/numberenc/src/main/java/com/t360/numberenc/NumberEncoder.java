package com.t360.numberenc;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.util.logging.Level.*;

/**
 * Encodes phone numbers.
 */
public class NumberEncoder {
    private static final Logger LOG = Logger.getLogger(NumberEncoder.class.getName());

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
                        LOG.log(FINE, "Write to output: " + s);
                        try {
                            writer.write(s + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public Stream<String> encode(Stream<String> numbers) {
        return numbers.filter(s -> s.length() <= NUM_MAX_LENGTH && s.matches(NUM_REGEX))
                .flatMap((n) -> dict.collect(n)
                        .filter((s) -> s.length() > 0 && !(s.length() == 1 && Character.isDigit(s.charAt(0))))
                        .map((s) -> n + ": " + s));
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            LOG.log(INFO, "Number Encoder:\n" +
                    "<dict> <nums> <out>, where:\n" +
                    "\tdict - dictionary file\n" +
                    "\tnums - phone numbers file\n" +
                    "\tout - output file\n");
            return;
        }
        if (args.length < 3) {
            LOG.log(INFO, "Wrong number of arguments, current: " + args.length + ", need: 3");
            return;
        }
        try (Stream<String> words = lines(Paths.get(args[0]))) {
            NumberEncoder enc = new NumberEncoder(new Dictionary(words));
            enc.encode(Paths.get(args[1]), Paths.get(args[2]));
        }
    }
}
