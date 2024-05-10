package database;

import java.math.BigDecimal;
import java.sql.Date;

public record Transaction(int userId, String transactionType, BigDecimal transactionAmount, Date transactionDate) {
}
