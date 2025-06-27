package labwork1.createsettings;

import labwork1.valueobjects.Money;
import labwork1.valueobjects.Percent;

import java.util.AbstractMap;

public class CreateBankSettings {
    public final String name;
    public final Percent fixedPercent;
    public final AbstractMap<Money, Percent> depositPercents;
    public final Money transactionLimitForDoubtfulClients;
    public final Money commission;

    CreateBankSettings(
            String name,
            Percent fixedPercent,
            AbstractMap<Money, Percent> depositPercents,
            Money transactionLimit,
            Money commission) {
        this.name = name;
        this.fixedPercent = fixedPercent;
        this.depositPercents = depositPercents;
        this.transactionLimitForDoubtfulClients = transactionLimit;
        this.commission = commission;
    }

    public static CreateBankSettingsBuilder builder() {
        return new CreateBankSettingsBuilder();
    }

    public static class CreateBankSettingsBuilder {
        private String name;
        private Percent fixedPercent;
        private AbstractMap<Money, Percent> depositPercents;
        private Money transactionLimitForDoubtfulClients;
        private Money commission;

        public CreateBankSettingsBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CreateBankSettingsBuilder withFixedPercent(Percent fixedPercent) {
            this.fixedPercent = fixedPercent;
            return this;
        }

        public CreateBankSettingsBuilder addDepositPercents(Money limit, Percent value) {
            this.depositPercents.put(limit, value);
            return this;
        }

        public CreateBankSettingsBuilder withDepositPercents(AbstractMap<Money, Percent> value) {
            this.depositPercents = value;
            return this;
        }

        public CreateBankSettingsBuilder withTransactionLimit(Money limit) {
            this.transactionLimitForDoubtfulClients = limit;
            return this;
        }

        public CreateBankSettingsBuilder withCommission(Money commission) {
            this.commission = commission;
            return this;
        }

        public CreateBankSettings build() {
            if (name == null)
                throw new NullPointerException("Name must not be null");

            if (fixedPercent == null)
                throw new NullPointerException("FixedPercent must not be null");

            if (depositPercents == null || depositPercents.isEmpty())
                throw new NullPointerException("Deposit percents must not be null or empty");

            if (transactionLimitForDoubtfulClients == null)
                throw new NullPointerException("Transaction limit for doubtful clients must not be null");

            if (commission == null)
                throw new NullPointerException("Commission must not be null");

            return new CreateBankSettings(name, fixedPercent, depositPercents, transactionLimitForDoubtfulClients, commission);
        }
    }
}
