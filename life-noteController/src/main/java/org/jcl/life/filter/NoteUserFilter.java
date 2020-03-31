package org.jcl.life.filter;

import org.springframework.stereotype.Component;

/**
 * @author chenglei
 */
@Component
public class NoteUserFilter extends UserFilter{

    @Override
    public void filter() {
        super.filter();
        System.out.println("sub do flter");
    }
}
