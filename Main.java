//import java.net.Inet4Address;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Main {
//    public static void main(String[] args) {
//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//        List<Integer> filterNumber = numbers.stream().filter(number -> number % 3 == 0 || number % 2 == 0).toList();
//        List<String> transformedList = filterNumber.stream().map(number -> "A"+number + 1+"B").toList();
//        String concatenated = transformedList.stream().collect(Collectors.joining(""));
//        System.out.println(concatenated);
//
//        List<Integer> numbersbis = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,14,15);
//        List<Integer> filterednumberbis = numbersbis.stream().filter(number -> number%4!=0).toList();
//        List<Integer> incremented = filterednumberbis.stream().map(number -> number+1).toList();
//        Integer res = incremented.stream().mapToInt(number -> number).sum() % 2;
//        Integer resbis = incremented.stream().reduce(0, (acc,number) -> (acc+number)%2);
//        System.out.println(resbis);
//    }
//}
