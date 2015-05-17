package ch.codebulb.jsfrestfulprg.controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import ch.codebulb.jsfrestfulprg.model.Customer;
import ch.codebulb.jsfrestfulprg.model.EmploymentStatus;
import ch.codebulb.jsfrestfulprg.service.CustomerService;
import java.io.Serializable;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CustomerController extends BaseController<Customer> implements Serializable {
    @Inject
    private CustomerService service;

    @Override
    protected CustomerService getService() {
        return service;
    }

    @Override
    protected Customer createNewEntity() {
        return new Customer();
    }
    
    public boolean isCompanyEmptyWhenUnemployed(FacesContext context, List<UIInput> components, List<Object> values) {
        Customer customer = new Customer();
        customer.setEmploymentStatus((EmploymentStatus) values.get(0));
        customer.setCompanyName((String) values.get(1));
        return customer.isCompanyEmptyWhenUnemployed();
    }
}
