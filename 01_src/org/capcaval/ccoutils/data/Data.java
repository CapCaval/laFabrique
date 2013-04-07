package org.capcaval.ccoutils.data;

import org.capcaval.ccoutils.data._impl.DataImpl;
import org.capcaval.ccoutils.factory.FactoryTools;
import org.capcaval.ccoutils.factory.GenericFactory;

public interface Data<T> extends DataReadWrite<T>, DataFeeder<T> {
}
