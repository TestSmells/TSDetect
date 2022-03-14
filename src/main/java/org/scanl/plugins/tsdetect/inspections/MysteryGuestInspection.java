package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MysteryGuestInspection extends SmellInspection{

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitDeclarationStatement(PsiDeclarationStatement statement){
				if (hasSmell(statement)) {
					holder.registerProblem(statement, getDescription());
				}

				super.visitDeclarationStatement(statement);
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if(!shouldTestElement(element)) return false;
		if(!(element instanceof PsiDeclarationStatement)) return false;

		List<String> mysteryTypes = new ArrayList<>(
				Arrays.asList(
						"Context",
						"Cursor",
						"File",
						"FileOutputStream",
						"HttpClient",
						"HttpResponse",
						"HttpPost",
						"HttpGet",
						"SoapObject",
						"SQLiteOpenHelper",
						"SQLiteDatabase"
				));
		PsiDeclarationStatement statement = (PsiDeclarationStatement) element;
		PsiLocalVariable v = (PsiLocalVariable) statement.getDeclaredElements()[0];
		for(String variableType:mysteryTypes){
			if(v.getTypeElement().getText().contains(variableType))
				return true;
		}
		return false;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.MYSTERY_GUEST;
	}

	@Override
	public Class<? extends PsiElement> getVisitedType() { return PsiDeclarationStatement.class; }
}
