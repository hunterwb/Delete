package com.hunterwb.delete;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class DeleteProcessor extends SimpleTreeVisitor<Void, TreePath> implements Processor, TaskListener {

    private Trees trees;

    @Override public void init(ProcessingEnvironment processingEnv) {
        JavacTask.instance(processingEnv).addTaskListener(this);
        trees = Trees.instance(processingEnv);
    }

    @Override public void started(TaskEvent e) {}

    @Override public void finished(TaskEvent e) {
        if (e.getKind() == TaskEvent.Kind.ANALYZE) {
            e.getCompilationUnit().accept(this, new TreePath(e.getCompilationUnit()));
        }
    }

    @Override public Void visitCompilationUnit(CompilationUnitTree node, TreePath path) {
        for (Tree typeDecl : node.getTypeDecls()) {
            typeDecl.accept(this, new TreePath(path, typeDecl));
        }
        return null;
    }

    @Override public Void visitClass(ClassTree node, TreePath path) {
        for (Tree member : node.getMembers()) {
            member.accept(this, new TreePath(path, member));
        }
        return null;
    }

    @Override public Void visitMethod(MethodTree node, TreePath path) {
        ExecutableElement e = (ExecutableElement) trees.getElement(path);
        if (shouldDelete(node, e)) IllegalAccess.remove(e);
        return null;
    }

    @Override public Void visitVariable(VariableTree node, TreePath path) {
        VariableElement e = (VariableElement) trees.getElement(path);
        if (shouldDelete(node, e)) IllegalAccess.remove(e);
        return null;
    }

    private static boolean shouldDelete(MethodTree node, ExecutableElement e) {
        if (e.getKind() == ElementKind.CONSTRUCTOR && e.getModifiers().contains(Modifier.PRIVATE)) {
            List<? extends StatementTree> statements = node.getBody().getStatements();
            return statements.size() >= 2 &&
                    statements.get(0).getKind() == Tree.Kind.EXPRESSION_STATEMENT &&
                    statements.get(1).getKind() == Tree.Kind.THROW;
        }
        return false;
    }

    private static boolean shouldDelete(VariableTree node, VariableElement e) {
        Set<Modifier> modifiers = e.getModifiers();
        return modifiers.contains(Modifier.PRIVATE) &&
                modifiers.contains(Modifier.STATIC) &&
                modifiers.contains(Modifier.FINAL) &&
                e.getConstantValue() != null;
    }

    @Override public Set<String> getSupportedOptions() {
        return Collections.emptySet();
    }

    @Override public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton("*");
    }

    @Override public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }

    @Override public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return Collections.emptySet();
    }
}
