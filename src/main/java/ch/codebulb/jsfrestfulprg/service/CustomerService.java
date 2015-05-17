package ch.codebulb.jsfrestfulprg.service;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import ch.codebulb.jsfrestfulprg.model.Customer;
import ch.codebulb.jsfrestfulprg.model.Payment;
import java.util.ArrayList;
import javax.inject.Inject;

@SessionScoped
public class CustomerService extends BaseService<Customer> implements Serializable {
    @Inject
    private PaymentService paymentService;
    
    @PostConstruct
    protected void initDemoValues() {
        save(new Customer("Max", "First Street", "Los Angeles")
                .add(new Payment(100, new Date()))
                .add(new Payment(200, null)));
        save(new Customer("Sarah", "Second Street", "San Francisco")
                .add(new Payment(100, new Date())));
    }

    @Override
    public Customer findById(Long id) {
        Customer entity = super.findById(id);
        if (entity != null) {
            entity.setPayments(paymentService.findByCustomer(entity.getId()));
        }
        return entity;
    }
    
    @Override
    public void save(Customer entity) {
        for (Payment payment : entity.getPayments()) {
            paymentService.save(payment);
        }
        entity.setPayments(new ArrayList<Payment>());
        super.save(entity);
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);
        for (Payment payment : customer.getPayments()) {
            paymentService.delete(payment.getId());
        }
        super.delete(id);
    }
}
