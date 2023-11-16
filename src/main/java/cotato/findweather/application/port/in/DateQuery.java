package cotato.findweather.application.port.in;

public record DateQuery(int year, int month, int day) {

    // Method to get the previous day's date
    public DateQuery previousDay() {
        int previousYear = year;
        int previousMonth = month;
        int previousDay = day - 1;

        if (previousDay == 0) {
            // If the previous day is in the previous month
            previousMonth--;
            if (previousMonth == 0) {
                // If the previous month is in the previous year
                previousYear--;
                previousMonth = 12; // Set to December
            }

            // Set the day to the last day of the previous month
            previousDay = getDaysInMonth(previousYear, previousMonth);
        }

        return new DateQuery(previousYear, previousMonth, previousDay);
    }

    // Helper method to get the number of days in a month
    private int getDaysInMonth(int year, int month) {
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12:
                return 31;
            case 4, 6, 9, 11:
                return 30;
            case 2:
                if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                    return 29; // Leap year
                } else {
                    return 28;
                }
            default:
                throw new IllegalArgumentException("Invalid month: " + month);
        }
    }
}
