package net.amcintosh.freshbooks.models;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import net.amcintosh.freshbooks.Util;
import net.amcintosh.freshbooks.models.api.ConvertibleContent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ServiceRates are hourly rates that can be applied to services to allow billing
 * of time entries tracked against that service.
 *
 * @see <a href="https://www.freshbooks.com/api/services">FreshBooks API - Services</a>
 */
public class ServiceRate extends GenericJson implements ConvertibleContent {

    @Key("service_id") Long serviceId;
    @Key("business_id") Long businessId;
    @Key String rate;

    /**
     * The unique id of the service this rate applies to.
     *
     * @return
     */
    public long getServiceId() {
        return serviceId;
    }

    /**
     * The unique id for business.
     *
     * @return
     */
    public long getBusinessId() {
        return businessId;
    }

    /**
     * The rate for the associated service.
     *
     * @return Eg. 10.60
     */
    public BigDecimal getRate() {
        if (this.rate != null) {
            return new BigDecimal(rate);
        }
        return null;
    }

    /**
     * The rate for the associated service.
     *
     * @param rate Eg. 10.60
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate.toString();
    }

    @Override
    public Map<String, Object> getContent() {
        Map<String, Object> content = new HashMap<>();
        Util.convertContent(content, "rate", rate);
        return content;
    }
}
