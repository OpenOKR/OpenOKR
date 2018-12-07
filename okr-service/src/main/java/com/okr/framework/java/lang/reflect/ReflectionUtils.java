package com.okr.framework.java.lang.reflect;


import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.CodeVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    private static Map<Class<?>, Map<Method, String[]>> declaredMethodsParameterNamesCache = new HashMap();
    private static Map<Class<?>, Map<Method, String[]>> methodsParameterNamesCache = new HashMap();
    private static Map<Class<?>, List<Method>> declaredMethodsInDeclaringOrderCache = new HashMap();
    private static Map<Class<?>, List<Method>> methodsInDeclaringOrderCache = new HashMap();
    private static Map<Method, String> methodSignatureCache = new HashMap();

    public ReflectionUtils() {
    }

    private static Map<Method, String[]> doGetDeclaredMethodsParameterNames(final Class<?> clazz) {
        if(logger.isDebugEnabled()) {
            logger.debug("getDeclaredMethodsParameterNames " + clazz);
        }

        final LinkedHashMap result = new LinkedHashMap();

        ClassReader classReader;
        try {
            String classVisitor = "/" + clazz.getName().replace('.', '/') + ".class";
            InputStream classResourceInputStream = ReflectionUtils.class.getResourceAsStream(classVisitor);
            classReader = new ClassReader(classResourceInputStream);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }

        ClassVisitor classVisitor1 = new ClassVisitor() {
            private Method method;
            private String[] parameterNames;
            private int parameterIndex;

            public void visit(int version, int access, String name, String superName, String[] interfaces, String sourceFile) {
            }

            public void visitAttribute(Attribute attr) {
            }

            public void visitEnd() {
            }

            public void visitField(int access, String name, String desc, Object value, Attribute attrs) {
            }

            public void visitInnerClass(String name, String outerName, String innerName, int access) {
            }

            public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
                if(!"<init>".equals(name) && !"<clinit>".equals(name)) {
                    try {
                        Type[] ex = Type.getArgumentTypes(desc);
                        Class[] parameterTypes = new Class[ex.length];

                        for(int i = 0; i < ex.length; ++i) {
                            parameterTypes[i] = ClassUtils.forName(ex[i].getClassName(), ClassLoader.getSystemClassLoader());
                        }

                        this.method = clazz.getDeclaredMethod(name, parameterTypes);
                        this.parameterNames = new String[parameterTypes.length];
                        this.parameterIndex = 0;
                        return new CodeVisitor() {
                            public void visitAttribute(Attribute attr) {
                            }

                            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                            }

                            public void visitIincInsn(int var, int increment) {
                            }

                            public void visitInsn(int opcode) {
                            }

                            public void visitIntInsn(int opcode, int operand) {
                            }

                            public void visitJumpInsn(int opcode, Label label) {
                            }

                            public void visitLabel(Label label) {
                            }

                            public void visitLdcInsn(Object cst) {
                            }

                            public void visitLineNumber(int line, Label start) {
                            }

                            public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {
                                if(!"this".equals(name)) {
                                    if(parameterIndex < parameterNames.length) {
                                        parameterNames[parameterIndex] = name;
//                                        null.access$008(<VAR_NAMELESS_ENCLOSURE>);
                                    }

                                }
                            }

                            public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
                            }

                            public void visitMaxs(int maxStack, int maxLocals) {
                                if(!result.containsKey(method)) {
                                    for(int i = 0; i < parameterNames.length; ++i) {
                                        if(parameterNames[i] == null) {
                                            parameterNames[i] = "arg" + i;
                                        }
                                    }

                                    result.put(method, parameterNames);
                                }
                            }

                            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                            }

                            public void visitMultiANewArrayInsn(String desc, int dims) {
                            }

                            public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
                            }

                            public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
                            }

                            public void visitTypeInsn(int opcode, String desc) {
                            }

                            public void visitVarInsn(int opcode, int var) {
                            }
                        };
                    } catch (Exception var9) {
                        throw new RuntimeException(var9);
                    }
                } else {
                    return null;
                }
            }
        };
        classReader.accept(classVisitor1, false);
        return result;
    }

    public static Map<Method, String[]> getDeclaredMethodsParameterNames(Class<?> clazz) {
        if(!declaredMethodsParameterNamesCache.containsKey(clazz)) {
            Map var1 = declaredMethodsParameterNamesCache;
            synchronized(declaredMethodsParameterNamesCache) {
                if(!declaredMethodsParameterNamesCache.containsKey(clazz)) {
                    Map result = doGetDeclaredMethodsParameterNames(clazz);
                    declaredMethodsParameterNamesCache.put(clazz, result);
                    return result;
                }
            }
        }

        return (Map)declaredMethodsParameterNamesCache.get(clazz);
    }

    private static Map<Method, String[]> doGetMethodsParameterNames(Class<?> clazz) {
        if(logger.isDebugEnabled()) {
            logger.debug("getMethodsParameterNames " + clazz);
        }

        LinkedHashMap result = new LinkedHashMap();
        HashSet methodSignatures = new HashSet();

        for(Class tmpClass = clazz; tmpClass != null; tmpClass = tmpClass.getSuperclass()) {
            Map methods = getDeclaredMethodsParameterNames(tmpClass);
            Iterator i$ = methods.keySet().iterator();

            while(i$.hasNext()) {
                Method method = (Method)i$.next();
                String methodSignature = getMethodSignature(method);
                if(!methodSignatures.contains(methodSignature)) {
                    methodSignatures.add(methodSignature);
                    result.put(method, methods.get(method));
                }
            }
        }

        return result;
    }

    public static Map<Method, String[]> getMethodsParameterNames(Class<?> clazz) {
        if(!methodsParameterNamesCache.containsKey(clazz)) {
            Map var1 = methodsParameterNamesCache;
            synchronized(methodsParameterNamesCache) {
                if(!methodsParameterNamesCache.containsKey(clazz)) {
                    Map result = doGetMethodsParameterNames(clazz);
                    methodsParameterNamesCache.put(clazz, result);
                    return result;
                }
            }
        }

        return (Map)methodsParameterNamesCache.get(clazz);
    }

    private static List<Method> doGetDeclaredMethodsInDeclaringOrder(final Class<?> clazz) {
        if(logger.isDebugEnabled()) {
            logger.debug("getDeclaredMethodsInDeclaringOrder " + clazz);
        }

        final ArrayList result = new ArrayList();

        ClassReader classReader;
        try {
            String classVisitor = "/" + clazz.getName().replace('.', '/') + ".class";
            InputStream classResourceInputStream = ReflectionUtils.class.getResourceAsStream(classVisitor);
            classReader = new ClassReader(classResourceInputStream);
        } catch (Exception var5) {
            throw new RuntimeException("Error read " + clazz, var5);
        }

        ClassVisitor classVisitor1 = new ClassVisitor() {
            public void visit(int version, int access, String name, String superName, String[] interfaces, String sourceFile) {
            }

            public void visitAttribute(Attribute attr) {
            }

            public void visitEnd() {
            }

            public void visitField(int access, String name, String desc, Object value, Attribute attrs) {
            }

            public void visitInnerClass(String name, String outerName, String innerName, int access) {
            }

            public CodeVisitor visitMethod(int access, String name, String desc, String[] exceptions, Attribute attrs) {
                if(!"<init>".equals(name) && !"<clinit>".equals(name)) {
                    try {
                        Type[] ex = Type.getArgumentTypes(desc);
                        Class[] parameterTypes = new Class[ex.length];

                        for(int method = 0; method < ex.length; ++method) {
                            parameterTypes[method] = ClassUtils.forName(ex[method].getClassName(), ClassLoader.getSystemClassLoader());
                        }

                        Method var10 = clazz.getDeclaredMethod(name, parameterTypes);
                        result.add(var10);
                        return null;
                    } catch (Exception var9) {
                        throw new RuntimeException(var9);
                    }
                } else {
                    return null;
                }
            }
        };
        classReader.accept(classVisitor1, false);
        return result;
    }

    public static List<Method> getDeclaredMethodsInDeclaringOrder(Class<?> clazz) {
        if(!declaredMethodsInDeclaringOrderCache.containsKey(clazz)) {
            Map var1 = declaredMethodsInDeclaringOrderCache;
            synchronized(declaredMethodsInDeclaringOrderCache) {
                if(!declaredMethodsInDeclaringOrderCache.containsKey(clazz)) {
                    List result = doGetDeclaredMethodsInDeclaringOrder(clazz);
                    declaredMethodsInDeclaringOrderCache.put(clazz, result);
                    return result;
                }
            }
        }

        return (List)declaredMethodsInDeclaringOrderCache.get(clazz);
    }

    private static List<Method> doGetMethodsInDeclaringOrder(Class<?> clazz) {
        if(logger.isDebugEnabled()) {
            logger.debug("getMethodsInDeclaringOrder " + clazz);
        }

        ArrayList result = new ArrayList();
        HashSet methodSignatures = new HashSet();

        for(Class tmpClass = clazz; tmpClass != null; tmpClass = tmpClass.getSuperclass()) {
            List methods = getDeclaredMethodsInDeclaringOrder(tmpClass);
            ArrayList temp = new ArrayList();
            Iterator i$ = methods.iterator();

            while(i$.hasNext()) {
                Method method = (Method)i$.next();
                String methodSignature = getMethodSignature(method);
                if(!methodSignatures.contains(methodSignature)) {
                    methodSignatures.add(methodSignature);
                    temp.add(method);
                }
            }

            result.addAll(0, temp);
        }

        return result;
    }

    public static List<Method> getMethodsInDeclaringOrder(Class<?> clazz) {
        if(!methodsInDeclaringOrderCache.containsKey(clazz)) {
            Map var1 = methodsInDeclaringOrderCache;
            synchronized(methodsInDeclaringOrderCache) {
                if(!methodsInDeclaringOrderCache.containsKey(clazz)) {
                    List result = doGetMethodsInDeclaringOrder(clazz);
                    methodsInDeclaringOrderCache.put(clazz, result);
                    return result;
                }
            }
        }

        return (List)methodsInDeclaringOrderCache.get(clazz);
    }

    private static String doGetMethodSignature(Method method) {
        if(logger.isDebugEnabled()) {
            logger.debug("getMethodSignature " + method);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        sb.append("(");
        Class[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length > 0) {
            Class[] arr$ = parameterTypes;
            int len$ = parameterTypes.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Class parameterType = arr$[i$];
                sb.append(ClassUtils.getQualifiedName(parameterType));
                sb.append(", ");
            }

            sb.setLength(sb.length() - 2);
        }

        sb.append(")");
        return sb.toString();
    }

    public static String getMethodSignature(Method method) {
        if(!methodSignatureCache.containsKey(method)) {
            Map var1 = methodSignatureCache;
            synchronized(methodSignatureCache) {
                if(!methodSignatureCache.containsKey(method)) {
                    String result = doGetMethodSignature(method);
                    methodSignatureCache.put(method, result);
                    return result;
                }
            }
        }

        return (String)methodSignatureCache.get(method);
    }

    public static java.lang.reflect.Type getActualTypeArgument(Class<?> clazz, TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if(!(genericDeclaration instanceof Class)) {
            return null;
        } else {
            TypeVariable[] typeVariables = genericDeclaration.getTypeParameters();
            java.lang.reflect.Type[] actualTypeArguments = null;
            java.lang.reflect.Type[] interfaceTypes = clazz.getGenericInterfaces();
            java.lang.reflect.Type[] i = interfaceTypes;
            int len$ = interfaceTypes.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                java.lang.reflect.Type interfaceType = i[i$];
                if(interfaceType instanceof ParameterizedType) {
                    actualTypeArguments = ((ParameterizedType)interfaceType).getActualTypeArguments();
                    break;
                }
            }

            if(actualTypeArguments == null) {
                throw new RuntimeException("Actual type arguments not found");
            } else if(typeVariables.length != actualTypeArguments.length) {
                throw new RuntimeException("Wrong number of actual type arguments");
            } else {
                for(int var10 = 0; var10 < typeVariables.length; ++var10) {
                    if(typeVariables[var10] == typeVariable) {
                        return actualTypeArguments[var10];
                    }
                }

                throw new RuntimeException("Actual type argument for " + typeVariable + " not found");
            }
        }
    }

    public static Method getSetMethod(Object obj, String fieldName) throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        return pd.getWriteMethod();
    }

    public static Method getGetMethod(Object obj, String fieldName) throws IntrospectionException {
        PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
        return pd.getReadMethod();
    }

    public static void invokeSet(Object obj, String fieldName, Object value) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method writeMethod = getSetMethod(obj, fieldName);
        writeMethod.invoke(obj, new Object[]{value});
    }

    public static Object invokeGet(Object obj, String fieldName) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method readMethod = getGetMethod(obj, fieldName);
        return readMethod.invoke(obj, new Object[0]);
    }
}

