package cn.escort.frameworkConfig.web.exceptionHandler.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;


//参考Objects
public class ExceptionUtil{


    public static <T> T requireNonNull(T obj) {
        return requireNonNull(obj,"数据为空");
    }


    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link NullPointerException} if it is. This method
     * is designed primarily for doing parameter validation in methods and
     * constructors with multiple parameters, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Objects.requireNonNull(bar, "bar must not be null");
     *     this.baz = Objects.requireNonNull(baz, "baz must not be null");
     * }
     * </pre></blockquote>
     *
     * @param obj     the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (isNull(obj)){
            throw new BadRequestException(message);
        }
        return obj;
    }

    /**
     * Returns the first argument if it is non-{@code null} and
     * otherwise returns the non-{@code null} second argument.
     *
     * @param obj an object
     * @param defaultObj a non-{@code null} object to return if the first argument
     *                   is {@code null}
     * @param <T> the type of the reference
     * @return the first argument if it is non-{@code null} and
     *        otherwise the second argument if it is non-{@code null}
     * @throws NullPointerException if both {@code obj} is null and
     *        {@code defaultObj} is {@code null}
     * @since 9
     */
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }

    /**
     * Returns the first argument if it is non-{@code null} and otherwise
     * returns the non-{@code null} value of {@code supplier.get()}.
     *
     * @param obj an object
     * @param supplier of a non-{@code null} object to return if the first argument
     *                 is {@code null}
     * @param <T> the type of the first argument and return type
     * @return the first argument if it is non-{@code null} and otherwise
     *         the value from {@code supplier.get()} if it is non-{@code null}
     * @throws NullPointerException if both {@code obj} is null and
     *        either the {@code supplier} is {@code null} or
     *        the {@code supplier.get()} value is {@code null}
     * @since 9
     */
    public static <T> T requireNonNullElseGet(T obj, Supplier<? extends T> supplier) {
        return (isNull(obj)) ? obj
                : requireNonNull(requireNonNull(supplier, "supplier").get(), "supplier.get()");
    }


    /**
     * Checks that the specified object reference is not {@code null} and
     * throws a customized {@link NullPointerException} if it is.
     *
     * <p>Unlike the method {@link #requireNonNull(Object, String)},
     * this method allows creation of the message to be deferred until
     * after the null check is made. While this may confer a
     * performance advantage in the non-null case, when deciding to
     * call this method care should be taken that the costs of
     * creating the message supplier are less than the cost of just
     * creating the string message directly.
     *
     * @param obj     the object reference to check for nullity
     * @param messageSupplier supplier of the detail message to be
     * used in the event that a {@code NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     * @since 1.8
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        if (isNull(obj))
            throw new BadRequestException(messageSupplier == null ?
                    null : messageSupplier.get());
        return obj;
    }

    private static <T> Boolean isNull(T obj){

        if(Objects.isNull(obj)){
            return Boolean.TRUE;
        }

        if (obj instanceof CharSequence str) {
           return StringUtils.isBlank(str);
        }

        if (obj instanceof List list) {
            return list.isEmpty();
        }

        return Boolean.FALSE;
    }

    public static <T extends  Number> T requireNonPositive(T obj) {
        return  requireNonPositive(obj,"data is empty","Value must be positive");
    }

    public static <T extends  Number> T requireNonPositive(T obj, String message) {
        return  requireNonPositive(obj,"data is empty",message);
    }

    public static <T extends  Number> T requireNonPositive(T obj, String nullMessage, String positiveMessage) {
        requireNonNull(obj,nullMessage);
        double value = obj.doubleValue(); // 将参数转换为 double 类型
        if (value <= 0) {
            throw new BadRequestException(positiveMessage);
        }
        return obj;
    }

    public static <T extends  Number> T requireNonNumberRange(T number, T min, T max) {
        return requireNonNumberRange(number,min,max,"data is empty","not in range");
    }

    public static <T extends  Number> T requireNonNumberRange(T number, T min, T max, String numberRangeMessage) {
        return requireNonNumberRange(number,min,max,"data is empty",numberRangeMessage);
    }

    public static <T extends  Number> T requireNonNumberRange(T number, T min, T max, String nullMessage, String numberRangeMessage) {
        requireNonNull(number,nullMessage);
        double numValue = number.doubleValue();
        double minValue = min.doubleValue();
        double maxValue = max.doubleValue();

        if (numValue < minValue || numValue > maxValue) {
            throw new BadRequestException(numberRangeMessage);
        }

        return number;
    }


    public static <T> T requireExist(T t, Collection<T> collection) {
        return requireExist(t,collection,"data is empty","collection in range");
    }

    public static <T> T requireExist(T t, Collection<T> collection, String noNExistMessage) {
        return requireExist(t,collection,"data is empty",noNExistMessage);
    }

    public static <T> T requireExist(T t, Collection<T> collection, String nullMessage, String noNExistMessage) {
        requireNonNull(t,nullMessage);
        if(collection.contains(t)){
            throw new BadRequestException(noNExistMessage);
        }
        return t;
    }


    public static <T> T requireNoNExist(T t, Collection<T> collection) {
        return requireNoNExist(t,collection,"data is empty","collection not in range");
    }

    public static <T> T requireNoNExist(T t, Collection<T> collection, String noNExistMessage) {
        return requireNoNExist(t,collection,"data is empty",noNExistMessage);
    }

    public static <T> T requireNoNExist(T t, Collection<T> collection, String nullMessage, String noNExistMessage) {
        requireNonNull(t,nullMessage);
        if(!collection.contains(t)){
            throw new BadRequestException(noNExistMessage);
        }
        return t;
    }



}
