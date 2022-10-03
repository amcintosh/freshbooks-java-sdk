package net.amcintosh.freshbooks.models.reports;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.models.Money;

import java.time.LocalDate;
import java.util.List;

public class ProfitAndLoss extends GenericJson {

    @Key("cash_based") boolean cashBased;
    @Key("company_name") String companyName;
    @Key("currency_code") String currencyCode;
    @Key List<ReportDates> dates;
    @Key("download_token") String downloadToken;
    @Key("end_date") String endDate;
    @Key List<ProfitAndLossEntry> expenses;
    @Key("gross_margin") ProfitAndLossEntry grossMargin;
    @Key List<ProfitAndLossEntry> income;
    @Key List<String> labels;
    @Key("net_profit") ProfitAndLossEntry netProfit;
    @Key String resolution;
    @Key("start_date") String startDate;
    @Key("total_expenses") ProfitAndLossEntry totalExpenses;
    @Key("total_income") ProfitAndLossEntry totalIncome;

    public boolean isCashBased() {
        return cashBased;
    }

    /**
     * Name of the business.
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The three-letter currency code
     *
     * @return (Eg. USD, CAD, EUR, GBP)
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Get the start and end dates for the report intervals, which could be monthly or quarterly.
     * @return
     */
    public List<ReportDates> getDates() {
        return dates;
    }

    public String getDownloadToken() {
        return downloadToken;
    }

    public LocalDate getEndDate() {
        return LocalDate.parse(endDate);
    }

    public List<ProfitAndLossEntry> getExpenses() {
        return expenses;
    }

    public ProfitAndLossEntry getGrossMargin() {
        return grossMargin;
    }

    public List<ProfitAndLossEntry> getIncome() {
        return income;
    }

    public List<String> getLabels() {
        return labels;
    }

    public ProfitAndLossEntry getNetProfit() {
        return netProfit;
    }

    public String getResolution() {
        return resolution;
    }

    public LocalDate getStartDate() {
        return LocalDate.parse(startDate);
    }

    public ProfitAndLossEntry getTotalExpenses() {
        return totalExpenses;
    }

    public ProfitAndLossEntry getTotalIncome() {
        return totalIncome;
    }

    public static class ReportDates {
        @Key("end_date") String endDate;
        @Key("start_date") String startDate;

        public LocalDate getEndDate() {
            return LocalDate.parse(endDate);
        }

        public LocalDate getStartDate() {
            return LocalDate.parse(startDate);
        }
    }

    public static class ProfitAndLossEntry {
        @Key List<ProfitAndLossEntry> children;
        @Key List<Money> data;
        @Key String description;
        @Key("entry_type") String entryType;
        @Key Money total;

        public List<ProfitAndLossEntry> getChildren() {
            return children;
        }

        public List<Money> getData() {
            return data;
        }

        public String getDescription() {
            return description;
        }

        public String getEntryType() {
            return entryType;
        }

        public Money getTotal() {
            return total;
        }
    }
}
