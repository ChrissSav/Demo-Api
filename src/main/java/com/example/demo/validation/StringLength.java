package com.example.demo.validation;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;
import java.util.regex.Pattern;

@Documented
@Constraint(validatedBy = StringLength.Validation.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StringLength {

    String message() default "must be not empty";

    int min() default 0;

    int max() default 0;

    Class<?>[] groups() default {};

    Class<String>[] payload() default {};


    class Validation implements ConstraintValidator<StringLength, String> {
        private int min;
        private int max;
        private String message;


        @Override
        public void initialize(StringLength constraintAnnotation) {
            this.max = Math.abs(constraintAnnotation.max());
            this.min = Math.abs(constraintAnnotation.min());
        }

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            if (s == null)
                return false;
            Pattern pattern;

            if (max == 0 && min ==0) {
                pattern = Pattern.compile("^.+$", Pattern.CASE_INSENSITIVE);
                message = "must be not empty";
            }
            else if (max == 0) {
                pattern = Pattern.compile("^.{" + min + ",}$", Pattern.CASE_INSENSITIVE);
                message = "length must be greater than " +min;

            } else {
                pattern = Pattern.compile("^.{" + min + "," + max + "}$", Pattern.CASE_INSENSITIVE);
                message = "length must be less than " +max;
            }

            /** build new violation message and add it **/
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();

            return pattern.matcher(s).find();
        }
    }

}
