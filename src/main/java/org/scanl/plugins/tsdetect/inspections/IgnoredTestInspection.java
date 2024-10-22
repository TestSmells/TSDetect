package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.PsiLocation;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IgnoredTestInspection extends SmellInspection{

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new JavaElementVisitor(){
            @Override
            public void visitMethod(PsiMethod method) {
                if (!JUnitUtil.isTestMethod(new PsiLocation<>(method)))
                    return;
                if (method.getBody() == null)
					return;
				if (hasSmell(method))
					holder.registerProblem(method, getDescription(),
                            new QuickFixRemove(getResourceName("FIX.REMOVE")),
                            new QuickFixComment(getResourceName("FIX.COMMENT")));
			}
        };
    }


    /**
     * This method does the actual smell detection. For each method in the given class,
     * it checks the method's annotation, and if that annotation includes "Ignore", then
     * that means an ignored test exits.
     * @param element PsiClass to test
     * @return true if smell exists, false if it doesnt
     */
    @Override
    public boolean hasSmell(PsiElement element) {
        if (!shouldTestElement(element)) return false;
        if (!(element instanceof PsiMethod)) return false;

        PsiMethod method = (PsiMethod) element;

        PsiAnnotation[] annotations = method.getModifierList().getAnnotations();
        if (annotations.length == 0) {
            return false;
        }
        for (PsiAnnotation annotation : annotations) {
            //if an annotation exists with "Ignore", then return false
            if(annotation.getQualifiedName() != null) {
                if (annotation.getQualifiedName().contains("Ignore")) {
                    return true;
                }
                if (annotation.getQualifiedName().contains("Disabled")) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.IGNORED_TEST;
    }
}
