package ch.codebulb.jsfrestfulprg.controller;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import ch.codebulb.jsfrestfulprg.model.Identifiable;
import ch.codebulb.jsfrestfulprg.service.BaseService;
import ch.codebulb.jsfrestfulprg.util.Strings;
import java.util.logging.Logger;
import org.omnifaces.util.Components;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

public abstract class BaseController<T extends Identifiable> {    
    private List<T> entities;
    private Long currentEntityId;
    private T currentEntity;
    private NavigationOutcome currentEntityLoadedOutcome;
    
    public void initEntities() {
        this.entities = getService().findAll();
    }
    
    public void postValidate() {
        /*
         * Restore invalid values from submitted values as they are overridden
         * in preValidate (as a tradeoff for a @RequestScoped controller)
         */
        Components.forEachComponent().invoke(new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent target) {
                if (target instanceof UIInput && !((UIInput)target).isValid()) {
                    ((UIInput) target).setValue(((UIInput) target).getSubmittedValue());
                    return VisitResult.REJECT;
                }
                return VisitResult.ACCEPT;
            }
        });
    }
    
    public String initCurrentEntity() {
        if (currentEntityLoadedOutcome != null) {
            NavigationOutcome ret = currentEntityLoadedOutcome;
            currentEntityLoadedOutcome = null;
            return resolveUrl(ret).navigateTo();
        }
        
        // load param during preValidate phase (if @RequestScoped)
        String idParam = Faces.getRequestParameterMap().get("id");
        if (!Strings.isEmpty(idParam)) {
            currentEntityId = Long.parseLong(idParam);
        }
        
        // without id param: CREATE
        if (currentEntityId == null) {
            currentEntity = createNewEntity();
        }
        else {
            // with id param: READ
            currentEntity = getService().findById(currentEntityId);
            if (currentEntity == null) {
                Messages.addGlobalError("Entity with id " + currentEntityId + " not found!");
                currentEntityLoadedOutcome = NavigationOutcome.BACK;
                return resolveUrl(currentEntityLoadedOutcome).navigateTo();
            }
        }
        currentEntityLoadedOutcome = NavigationOutcome.THIS;
        return resolveUrl(currentEntityLoadedOutcome).navigateTo();
    }
    
    protected static enum NavigationOutcome {
        THIS, BACK
    }
    
    protected Url resolveUrl(NavigationOutcome outcome) {
        switch (outcome) {
            case THIS: return new Url(null);
            case BACK: return new Url("list.xhtml");
            default: throw new IllegalArgumentException("Navigation outcome not recognized: " + outcome);
        }
    }
    
    public String save(T currentEntity) {
        getService().save(currentEntity);
        return resolveUrl(NavigationOutcome.BACK).redirect().navigateTo();
    }
    
    public String delete(Long id) {
        getService().delete(id);
        return resolveUrl(NavigationOutcome.BACK).redirect().navigateTo();
    }
    
    public String delete() {
        Long id = Long.parseLong(Faces.getRequestParameter("deleteId"));
        return delete(id);
    }

    public List<T> getEntities() {
        return entities;
    }

    public Long getCurrentEntityId() {
        return currentEntityId;
    }

    public void setCurrentEntityId(Long currentEntityId) {
        this.currentEntityId = currentEntityId;
    }

    public T getCurrentEntity() {
        return currentEntity;
    }

    public void setCurrentEntity(T currentEntity) {
        this.currentEntity = currentEntity;
    }
    
    protected abstract BaseService<T> getService();
    protected abstract T createNewEntity();
}
