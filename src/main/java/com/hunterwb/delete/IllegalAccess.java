package com.hunterwb.delete;

import javax.lang.model.element.Element;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class IllegalAccess {

    private static final Class<?> SYMBOL;
    private static final Method SYMBOL$MEMBERS;

    private static final Class<?> WRITEABLE_SCOPE;
    private static final Method WRITEABLE_SCOPE$REMOVE;

    static {
        try {
            SYMBOL = Class.forName("com.sun.tools.javac.code.Symbol");
            SYMBOL$MEMBERS = SYMBOL.getMethod("members");

            Class<?> writeableScope;
            try {
                writeableScope = Class.forName("com.sun.tools.javac.code.Scope$WriteableScope");
            } catch (ClassNotFoundException e) {
                writeableScope = Class.forName("com.sun.tools.javac.code.Scope");
            }
            WRITEABLE_SCOPE = writeableScope;
            WRITEABLE_SCOPE$REMOVE = WRITEABLE_SCOPE.getMethod("remove", SYMBOL);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    static void remove(Element member) {
        try {
            Object members = SYMBOL$MEMBERS.invoke(member.getEnclosingElement());
            WRITEABLE_SCOPE$REMOVE.invoke(members, member);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
