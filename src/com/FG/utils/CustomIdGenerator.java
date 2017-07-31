/*Author : Diana P Lazar
Date created : 24/03/2016
Copyright@ FindGoose
 */

package com.FG.utils;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.id.IdentityGenerator;

public class CustomIdGenerator extends IdentityGenerator {


public Serializable generate(org.hibernate.engine.spi.SessionImplementor session, Object obj) throws HibernateException {

//    if ((((IndustryTypes) obj).getIndustryTypeID()) == null) {
        Serializable id = super.generate(session, obj) ;
        return id;
//    } else {
//        return ((IndustryTypes) obj).getIndustryTypeID();

    }
}
