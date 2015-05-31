package ch.codebulb.jsfrestfulprg.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Customer implements Serializable, Identifiable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Pattern(regexp = "[A-Za-z ]*")
    private String name;
    private String address;
    private String city;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private EmploymentStatus employmentStatus = EmploymentStatus.Unemployed;
    private String companyName;
    
    // We don't cascade as child entities live independent of their parent
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();
    
    public Customer() {}

    @Deprecated
    // for demo purposes only
    public Customer(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
    
    public Customer add(Payment payment) {
        this.payments.add(payment);
        payment.setCustomer(this);
        return this;
    }
    
    public boolean isCompanyEmptyWhenUnemployed() {
        if (getEmploymentStatus() == EmploymentStatus.Unemployed) {
            return getCompanyName() == null;
        }
        return true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfrestfulprg.model.Customer[ id=" + id + " ]";
    }
    
}
