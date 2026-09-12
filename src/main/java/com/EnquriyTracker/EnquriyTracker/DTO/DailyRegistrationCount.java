package com.EnquriyTracker.EnquriyTracker.DTO;

import java.time.LocalDate;

public interface DailyRegistrationCount {

	LocalDate getDate();
    Long getCount();
}