package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
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

    private List<PsiStatement> issueStatements = new ArrayList<>();

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new JavaElementVisitor(){
            @Override
            public void visitMethod(PsiMethod method) {
				if (method.getBody() == null)
					return;
				if (hasSmell(method))
					holder.registerProblem(method, DESCRIPTION,
							new QuickFixRemove("INSPECTION.SMELL.EMPTY_TEST.FIX.REMOVE"),
							new QuickFixComment("INSPECTION.SMELL.EMPTY_TEST.FIX.COMMENT"));
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
        PsiMethod method = (PsiMethod) element;
        if (!PluginSettings.GetSetting(getSmellType().toString())){
            return false;
        }
        issueStatements = new ArrayList<>();
        //PsiMethod[] psiMethods = currClass.getMethods();

        PsiAnnotation[] annotations = method.getModifierList().getAnnotations();
        if(annotations.length == 0){
            return false;
        }
        for(PsiAnnotation annotation:annotations){
            //if an annotation exists with "Ignore", then return false
            if(Objects.equals(annotation.getQualifiedName(), "Ignore")){
                return true;
            }
            if(Objects.equals(annotation.getQualifiedName(), "Disable")){
                return true;
            }
        }

        return false;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.IGNORED_TEST;
    }
}
