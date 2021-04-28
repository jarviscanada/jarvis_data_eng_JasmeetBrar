package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

    @Override
    public Stream<String> createStrStream(String... strings) {
        return Arrays.stream(strings);
    }

    @Override
    public Stream<String> toUpperCase(String... strings) {
        return createStrStream(strings).map(String::toUpperCase);
    }

    @Override
    public Stream<String> filter(Stream<String> stringStream, String pattern) {
        Pattern regexPattern = Pattern.compile(pattern);

        return stringStream.filter(string -> {
            Matcher matcher = regexPattern.matcher(string);
            return !matcher.find();
        });
    }

    @Override
    public IntStream createIntStream(int[] arr) {
        return Arrays.stream(arr);
    }

    @Override
    public <E> List<E> toList(Stream<E> stream) {
        return stream.collect(Collectors.toList());
    }

    @Override
    public List<Integer> toList(IntStream intStream) {
        return intStream.boxed().collect(Collectors.toList());
    }

    @Override
    public IntStream createIntStream(int start, int end) {
        return IntStream.range(start, end);
    }

    @Override
    public DoubleStream squareRootIntStream(IntStream intStream) {
        return intStream.asDoubleStream().map(Math::sqrt);
    }

    @Override
    public IntStream getOdd(IntStream intStream) {
        return intStream.filter(integer -> integer % 2 == 1);
    }

    @Override
    public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
        return message -> System.out.println(prefix + message + suffix);
    }

    @Override
    public void printMessages(String[] messages, Consumer<String> printer) {
        createStrStream(messages).forEach(printer);
    }

    @Override
    public void printOdd(IntStream intStream, Consumer<String> printer) {
        printMessages((String[]) getOdd(intStream).mapToObj(Integer::toString).toArray(String[]::new), printer);
    }

    @Override
    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {

        // Solution 1: Use reduce to reduce all the lists of integers to a single list
        // and then convert it to a stream so we can square the values
//        return ints.reduce(new ArrayList<Integer>(), (firstList, secondList) -> {
//            List<Integer> result = new ArrayList<>(firstList);
//            result.addAll(new ArrayList<Integer>(secondList));
//            return result;
//        }).stream().map(value -> value * value);

        // Solution 2: Use flatmap to convert each list of integers into a stream of integers
        // that are all merged into one stream, and then we square the values
        return ints.flatMap(Collection::stream).map(value -> value * value);
    }
}
