package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class ResourceOptimismInspection extends SmellInspection {

	private HashMap<String, PsiElement> uncheckedFields;

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if(hasSmell(method)) {
					for (PsiElement unusedField : uncheckedFields.values()) {
						holder.registerProblem(unusedField, getDescription(),
								new QuickFixRemove(getResourceName("FIX.REMOVE")),
								new QuickFixComment(getResourceName("FIX.COMMENT")));
					}
				}
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if (!shouldTestElement(element)) return false;
		if (!(element instanceof PsiMethod)) return false;
		uncheckedFields = new HashMap<>();

		PsiMethod method = (PsiMethod) element;
		PsiClass cls = method.getContainingClass();
		PsiField @NotNull [] fields = Objects.requireNonNull(cls).getFields();
		uncheckedFields = new HashMap<>();
		for (PsiField field : fields) {
			if(field.getType().getPresentableText().equals("File")) {
				uncheckedFields.put(field.getName(), field);
			}
		}

		Collection<? extends PsiLocalVariable> elements = PsiTreeUtil.collectElementsOfType(method, PsiLocalVariable.class);
		for(PsiLocalVariable e:elements){
			if(e.getType().getPresentableText().equals("File")) {
				uncheckedFields.put(e.getName(), e);
			}
		}
		Collection<? extends PsiMethodCallExpression> expressions = PsiTreeUtil.collectElementsOfType(method, PsiMethodCallExpression.class);
		for(PsiMethodCallExpression expression:expressions){
			String[] ex = expression.getText().split("\\.");
			if(ex.length>=2) {
				String call = ex[1].replace("(", "").replace(")", "");
				String name = ex[0];
				if (call.equals("exists") ||
						call.equals("isFile") ||
						call.equals("notExists")) {
					uncheckedFields.remove(name);
				}
			}
		}

		return uncheckedFields.keySet().size() > 0;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.RESOURCE_OPTIMISM;
	}
}
