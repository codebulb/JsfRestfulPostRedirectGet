package ch.codebulb.jsfrestfulprg.service;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import ch.codebulb.jsfrestfulprg.model.Payment;
import java.util.ArrayList;
import java.util.Objects;

@SessionScoped
public class PaymentService extends BaseService<Payment> implements Serializable{
    public List<Payment> findByCustomer(Long customerId) {
        List<Payment> ret = new ArrayList<>();
        for (Payment entity : findAll()) {
            if (Objects.equals(entity.getCustomer().getId(), customerId)) {
                ret.add(entity);
            }
        }
        return ret;
    }
}
