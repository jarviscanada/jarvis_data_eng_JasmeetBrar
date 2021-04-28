package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {

    /**
     * Create a String stream from array
     *
     * note: arbitrary number of value will be stored in an array
     *
     * @param strings any strings
     * @return a stream of strings
     */
    Stream<String> createStrStream(String ... strings);

    /**
     * Convert all strings to uppercase
     * please use createStrStream
     *
     * @param strings any strings
     * @return a stream of capitalized strings
     */
    Stream<String> toUpperCase(String ... strings);

    /**
     * filter strings that contains the pattern
     * e.g.
     * filter(stringStream, "a") will return another stream which no element contains a
     *
     *
     * @param stringStream a stream of strings
     * @param pattern a regex string
     * @return a stream of string where strings were filtered out using the regex string
     */
    Stream<String> filter(Stream<String> stringStream, String pattern);

    /**
     * Create a intStream from a arr[]
     * @param arr an int array
     * @return an integer stream of the given int array
     */
    IntStream createIntStream(int[] arr);

    /**
     * Convert a stream to list
     *
     * @param stream a stream of any type
     * @param <E> any type
     * @return the list form of the given stream
     */
    <E> List<E> toList(Stream<E> stream);

    /**
     * Convert a intStream to list
     * @param intStream an integer stream
     * @return the list form of the given integer stream
     */
    List<Integer> toList(IntStream intStream);

    /**
     * Create a IntStream range from start to end inclusive
     * @param start an integer
     * @param end an integer that is greater than or equal to start
     * @return an integer stream containing integers from start to end inclusively
     */
    IntStream createIntStream(int start, int end);

    /**
     * Convert a intStream to a doubleStream
     * and compute square root of each element
     * @param intStream an integer stream
     * @return a double stream of the given integer stream containing the square roots of all the values
     */
    DoubleStream squareRootIntStream(IntStream intStream);

    /**
     * filter all even number and return odd numbers from a intStream
     * @param intStream an integer stream
     * @return an integer stream containing only the odd numbers from the given integer stream
     */
    IntStream getOdd(IntStream intStream);

    /**
     * Return a lambda function that print a message with a prefix and suffix
     * This lambda can be useful to format logs
     *
     * You will learn:
     *   - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig
     *   - lambda syntax
     *
     * e.g.
     * LambdaStreamExc lse = new LambdaStreamImp();
     * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end");
     * printer.accept("Message body");
     *
     * sout:
     * start>Message body<end
     *
     * @param prefix prefix str
     * @param suffix suffix str
     * @return a function that would take a string and print it out along with the given prefix
     * and suffix
     */
    Consumer<String> getLambdaPrinter(String prefix, String suffix);

    /**
     * Print each message with a given printer
     * Please use `getLambdaPrinter` method
     *
     * e.g.
     * String[] messages = {"a","b", "c"};
     * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
     *
     * sout:
     * msg:a!
     * msg:b!
     * msg:c!
     *
     * @param messages a string array
     * @param printer a function that would print a given string
     */
    void printMessages(String[] messages, Consumer<String> printer);

    /**
     * Print all odd number from a intStream.
     * Please use `createIntStream` and `getLambdaPrinter` methods
     *
     * e.g.
     * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
     *
     * sout:
     * odd number:1!
     * odd number:3!
     * odd number:5!
     *
     * @param intStream an integer stream
     * @param printer a function that would print the given strinbg
     */
    void printOdd(IntStream intStream, Consumer<String> printer);

    /**
     * Square each number from the input.
     * Please write two solutions and compare difference
     *   - using flatMap
     *
     * @param ints a stream of lists of integers
     * @return a stream of all the integers from the given stream
     */
    Stream<Integer> flatNestedInt(Stream<List<Integer>> ints);

}
