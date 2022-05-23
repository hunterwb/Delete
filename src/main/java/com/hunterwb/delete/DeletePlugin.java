package com.hunterwb.delete;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeInfo;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor8;
import java.util.List;
import java.util.Set;

public final class DeletePlugin extends SimpleElementVisitor8<Void, Void> implements Plugin, TaskListener {

    private CompilationUnitTree compilationUnit;

    @Override public String getName() {
        return "Delete";
    }

    @Override public void init(JavacTask task, String... args) {
        task.addTaskListener(this);
    }

    @Override public void started(TaskEvent e) {
        if (e.getKind() == TaskEvent.Kind.GENERATE) {
            TypeElement type = e.getTypeElement();
            compilationUnit = e.getCompilationUnit();
            if (type != null && compilationUnit != null) {
                visitType(type, null);
            }
        }
    }

    @Override public void finished(TaskEvent e) {}

    @Override public Void visitType(TypeElement e, Void unused) {
        for (Element n : e.getEnclosedElements()) {
            n.accept(this, null);
        }
        return null;
    }

    @Override public Void visitExecutable(ExecutableElement e, Void unused) {
        if (shouldDelete(e)) deleteMember(e);
        return null;
    }

    @Override public Void visitVariable(VariableElement e, Void unused) {
        if (shouldDelete(e)) deleteMember(e);
        return null;
    }

    private boolean shouldDelete(ExecutableElement e) {
        if (e.getKind() == ElementKind.CONSTRUCTOR && e.getModifiers().contains(Modifier.PRIVATE) && e.getParameters().isEmpty()) {
            MethodTree tree = (MethodTree) TreeInfo.declarationFor((Symbol) e, (JCTree) compilationUnit);
            List<? extends StatementTree> statements = tree.getBody().getStatements();
            return statements.size() >= 2 &&
                    statements.get(0).getKind() == Tree.Kind.EXPRESSION_STATEMENT &&
                    statements.get(1).getKind() == Tree.Kind.THROW;
        }
        return false;
    }

    private static boolean shouldDelete(VariableElement e) {
        if (e.getConstantValue() != null) {
            Set<Modifier> modifiers = e.getModifiers();
            return modifiers.contains(Modifier.PRIVATE) &&
                    modifiers.contains(Modifier.STATIC) &&
                    modifiers.contains(Modifier.FINAL);
        }
        return false;
    }

    private static void deleteMember(Element e) {
        ((Symbol.ClassSymbol) e.getEnclosingElement()).members_field.remove((Symbol) e);
    }
}
