package book.chapter01;

class PerformanceCalculatorFactory {

    static PerformanceCalculator createCalculator(
            final Performance performance,
            final Play play
    ) {
        return switch (play.type()) {
            case "tragedy" -> new TragedyCalculator(performance);
            case "comedy" -> new ComedyCalculator(performance);
            default -> throw new RuntimeException("알 수 없는 타입입니다.");
        };
    }
}
