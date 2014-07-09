package net.sf.kfgodel.bean2bean.api.impl;

import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.api.B2bConfigurationApi;
import net.sf.kfgodel.bean2bean.api.B2bConverterApi;
import org.mockito.Mockito;

/**
 * This class is the implementation of the api
 * Created by kfgodel on 09/07/14.
 */
public class B2bApiImpl implements B2bApi {

    public static B2bApiImpl create() {
        B2bApiImpl b2bApi = new B2bApiImpl();
        return b2bApi;
    }

    @Override
    public B2bConfigurationApi configuration() {
        return Mockito.mock(this.getClass(), Mockito.RETURNS_SMART_NULLS).configuration();
    }

    @Override
    public B2bConverterApi convert() {
        return Mockito.mock(this.getClass(), Mockito.RETURNS_SMART_NULLS).convert();
    }
}
