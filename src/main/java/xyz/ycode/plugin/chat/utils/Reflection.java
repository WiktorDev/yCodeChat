package xyz.ycode.plugin.chat.utils;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Reflection {
    private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static String NMS_PREFIX;
    private static String VERSION;
    private static Pattern MATCH_VARIABLE;

    public static <T> Reflection.FieldAccessor<T> getSimpleField(Class<?> target, String name) {
        return getField(target, name);
    }

    public static <T> Reflection.FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
        return getField(target, name, fieldType, 0);
    }

    public static <T> Reflection.FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
        return getField(getClass(className), name, fieldType, 0);
    }

    public static <T> Reflection.FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
        return getField(target, (String)null, fieldType, index);
    }

    public static <T> Reflection.FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
        return getField(getClass(className), fieldType, index);
    }

    private static <T> Reflection.FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
        Field[] declaredFields;
        int length = (declaredFields = target.getDeclaredFields()).length;

        for(int i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
                field.setAccessible(true);
                return new Reflection.FieldAccessor<T>() {
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException var3) {
                            throw new RuntimeException("Cannot access reflection.", var3);
                        }
                    }

                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException var4) {
                            throw new RuntimeException("Cannot access reflection.", var4);
                        }
                    }

                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name, fieldType, index);
        } else {
            throw new IllegalArgumentException("Cannot find field with type " + fieldType);
        }
    }

    private static <T> Reflection.FieldAccessor<T> getField(Class<?> target, String name) {
        Field[] declaredFields;
        int length = (declaredFields = target.getDeclaredFields()).length;

        for(int i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if (name == null || field.getName().equals(name)) {
                field.setAccessible(true);
                return new Reflection.FieldAccessor<T>() {
                    public T get(Object target) {
                        try {
                            return (T) field.get(target);
                        } catch (IllegalAccessException var3) {
                            throw new RuntimeException("Cannot access reflection.", var3);
                        }
                    }

                    public void set(Object target, Object value) {
                        try {
                            field.set(target, value);
                        } catch (IllegalAccessException var4) {
                            throw new RuntimeException("Cannot access reflection.", var4);
                        }
                    }

                    public boolean hasField(Object target) {
                        return field.getDeclaringClass().isAssignableFrom(target.getClass());
                    }
                };
            }
        }

        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), name);
        } else {
            throw new IllegalArgumentException("Cannot find field with type");
        }
    }

    public static Reflection.MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
        return getTypedMethod(getClass(className), methodName, (Class)null, params);
    }

    public static Reflection.MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
        return getTypedMethod(clazz, methodName, (Class)null, params);
    }

    public static Reflection.MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
        Method[] declaredMethods;
        int length = (declaredMethods = clazz.getDeclaredMethods()).length;

        for(int i = 0; i < length; ++i) {
            final Method method = declaredMethods[i];
            if ((methodName == null || method.getName().equals(methodName)) && returnType == null || method.getReturnType().equals(returnType) && Arrays.equals(method.getParameterTypes(), params)) {
                method.setAccessible(true);
                return new Reflection.MethodInvoker() {
                    public Object invoke(Object target, Object... arguments) {
                        try {
                            return method.invoke(target, arguments);
                        } catch (Exception var4) {
                            throw new RuntimeException("Cannot invoke method " + method, var4);
                        }
                    }
                };
            }
        }

        if (clazz.getSuperclass() != null) {
            return getMethod(clazz.getSuperclass(), methodName, params);
        } else {
            throw new IllegalStateException(String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
        }
    }

    public static Reflection.ConstructorInvoker getConstructor(String className, Class<?>... params) {
        return getConstructor(getClass(className), params);
    }

    public static Reflection.ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
        Constructor[] declaredConstructors;
        int length = (declaredConstructors = clazz.getDeclaredConstructors()).length;

        for(int i = 0; i < length; ++i) {
            final Constructor<?> constructor = declaredConstructors[i];
            if (Arrays.equals(constructor.getParameterTypes(), params)) {
                constructor.setAccessible(true);
                return new Reflection.ConstructorInvoker() {
                    public Object invoke(Object... arguments) {
                        try {
                            return constructor.newInstance(arguments);
                        } catch (Exception var3) {
                            throw new RuntimeException("Cannot invoke constructor " + constructor, var3);
                        }
                    }
                };
            }
        }

        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(params)));
    }

    public static Class<Object> getUntypedClass(String lookupName) {
        Class<Object> clazz = (Class<Object>) getClass(lookupName);
        return clazz;
    }

    public static Class<?> getClass(String lookupName) {
        return getCanonicalClass(expandVariables(lookupName));
    }

    public static Class<?> getMinecraftClass(String name) {
        return getCanonicalClass(String.valueOf(String.valueOf(NMS_PREFIX)) + "." + name);
    }

    public static Class<?> getCraftBukkitClass(String name) {
        return getCanonicalClass(String.valueOf(String.valueOf(OBC_PREFIX)) + "." + name);
    }

    private static Class<?> getCanonicalClass(String canonicalName) {
        try {
            return Class.forName(canonicalName);
        } catch (ClassNotFoundException var2) {
            throw new IllegalArgumentException("Cannot find " + canonicalName, var2);
        }
    }

    private static String expandVariables(String name) {
        StringBuffer output = new StringBuffer();

        Matcher matcher;
        String replacement;
        for(matcher = MATCH_VARIABLE.matcher(name); matcher.find(); matcher.appendReplacement(output, Matcher.quoteReplacement(replacement))) {
            String variable = matcher.group(1);
            replacement = "";
            if ("nms".equalsIgnoreCase(variable)) {
                replacement = NMS_PREFIX;
            } else if ("obc".equalsIgnoreCase(variable)) {
                replacement = OBC_PREFIX;
            } else {
                if (!"version".equalsIgnoreCase(variable)) {
                    throw new IllegalArgumentException("Unknown variable: " + variable);
                }

                replacement = VERSION;
            }

            if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.') {
                replacement = String.valueOf(replacement) + ".";
            }
        }

        matcher.appendTail(output);
        return output.toString();
    }

    static {
        NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
        VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
        MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
    }

    public interface MethodInvoker {
        Object invoke(Object var1, Object... var2);
    }

    public interface FieldAccessor<T> {
        T get(Object var1);

        void set(Object var1, Object var2);

        boolean hasField(Object var1);
    }

    public interface ConstructorInvoker {
        Object invoke(Object... var1);
    }
}