package com.bestotc.domain;

import javax.validation.Payload;

/**
 * Created by jack on 18/7/5.
 */
public class VcbPayload {

    public interface MissingParameter extends Payload {

    }

    public interface InvalidParameter extends Payload{

    }

    public interface PrimaryKeyIsNullGroup {
    }
    public interface PrimaryKeyIsNotNullGroup {
    }
}
