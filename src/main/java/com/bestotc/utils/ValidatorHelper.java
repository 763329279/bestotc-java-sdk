package com.bestotc.utils;

import com.bestotc.domain.VcbPayload;
import com.bestotc.exception.InvalidParameterException;
import com.bestotc.exception.MissingParameterException;
import com.bestotc.exception.VcbException;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by jack on 16/5/9.
 */
public class ValidatorHelper {

   private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validator(Object object,Class<?>... groups) throws VcbException {


        Set<ConstraintViolation<Object>> constraintViolations  = validator.validate(object,groups);
        List<VcbException> exceptionList = new ArrayList<VcbException>();

        for(ConstraintViolation constraintViolation:constraintViolations){
            Set<Class> payloads = constraintViolation.getConstraintDescriptor().getPayload();

            if(payloads.size()==0 &&
                    ReflectionUtils.findField(constraintViolation.getRootBeanClass(),constraintViolation.getPropertyPath().toString())!=null){
                for(Annotation annotation : ReflectionUtils.findField(constraintViolation.getRootBeanClass(),constraintViolation.getPropertyPath().toString()).getAnnotations()){
                    try {
                        Annotation tmp = annotation.annotationType().getAnnotation(constraintViolation.getConstraintDescriptor().getAnnotation().annotationType());
                        Method method = ReflectionUtils.findMethod(constraintViolation.getConstraintDescriptor().getAnnotation().annotationType(),"payload");
                        Class[] pays = (Class[]) ReflectionUtils.invokeMethod(method,tmp);
                        payloads = new HashSet(Arrays.asList(pays));
                    }catch (Throwable e){

                    }
                }
            }

            for(Class payload:payloads){
                if(payload.equals(VcbPayload.MissingParameter.class)){
                    exceptionList.add(0,new MissingParameterException(constraintViolation.getMessage()));
                }else if (VcbPayload.InvalidParameter.class.equals(payload)) {
                    exceptionList.add(new InvalidParameterException(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage()));
                }
            }
        }

        if(exceptionList.size()>0){
            throw exceptionList.get(0);
        }

    }

}
