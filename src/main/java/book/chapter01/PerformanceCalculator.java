package book.chapter01;

class PerformanceCalculator {

    protected final Performance performance;

    protected PerformanceCalculator(final Performance performance) {
        this.performance = performance;
    }

    long amount() {
        throw new UnsupportedOperationException("서브 클래스에서 처리하도록 설계되었습니다.");
    }

    long volumeCredits() {
        throw new UnsupportedOperationException("서브 클래스에서 처리하도록 설계되었습니다.");
    }
}
