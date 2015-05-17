package ch.codebulb.jsfrestfulprg.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import org.omnifaces.util.Components;

@ManagedBean
@ApplicationScoped
public class ForceRenderActionSourceListener {
    public void forceRenderActionSource(final PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES) {
            forceRenderActionSource(true);
        }
    }
    
    public void forceRenderActionSourceReset(final PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.APPLY_REQUEST_VALUES) {
            forceRenderActionSource(false);
        }
    }
    
    private void forceRenderActionSource(final boolean activate) {
        /*
         * Restore invalid values from submitted values as they are overridden
         * in preValidate (as a tradeoff for a @RequestScoped controller)
         */
        final Set<UIComponent> hiddenComponents = new HashSet<>();
        
        Components.forEachComponent().invoke(new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent target) {
                if (!target.isRendered()) {
                    if (activate) {
                        hiddenComponents.add(target);
                        target.setRendered(true);
                    }
                    else if (hiddenComponents.contains(target)) {
                        target.setRendered(false);
                    }
                }
                return VisitResult.ACCEPT;
            }
        });
    }
}
