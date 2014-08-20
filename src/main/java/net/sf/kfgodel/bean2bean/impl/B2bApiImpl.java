package net.sf.kfgodel.bean2bean.impl;

import net.sf.kfgodel.bean2bean.api.*;
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

    @Override
    public B2bManipulatorApi manipulate() {
        return Mockito.mock(this.getClass(), Mockito.RETURNS_SMART_NULLS).manipulate();
    }

    @Override
    public B2bMapperApi map() {
        return Mockito.mock(this.getClass(), Mockito.RETURNS_SMART_NULLS).map();
    }
}
