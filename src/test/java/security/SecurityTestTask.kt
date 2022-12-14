package security

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.jetbrains.python.PythonFileType
import com.jetbrains.python.inspections.PyInspection
import com.jetbrains.python.inspections.PyInspectionVisitor
import com.jetbrains.python.psi.*
import org.mockito.ArgumentMatchers.contains
import org.mockito.Mockito
import org.mockito.kotlin.*

open class SecurityTestTask: BasePlatformTestCase() {
    fun <inspector: PyInspection>testCodeAssignmentStatement(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
                on { registerProblem(any(), contains(check.Code), any<ProblemHighlightType>(), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyAssignmentStatement> = PsiTreeUtil.findChildrenOfType(testFile, PyAssignmentStatement::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyAssignmentStatement(e)
            }
            try {
                Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            } catch (a: AssertionError){
                Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), any<ProblemHighlightType>(), anyVararg<LocalQuickFix>())
            }
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testCodeCallExpression(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
                on { registerProblem(any(), contains(check.Code), any<ProblemHighlightType>(), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)

            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyCallExpression> = PsiTreeUtil.findChildrenOfType(testFile, PyCallExpression::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyCallExpression(e)
            }
            try {
                Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            } catch (a: AssertionError){
                Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), any<ProblemHighlightType>(), anyVararg<LocalQuickFix>())
            }
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testCodeClass(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
                on { registerProblem(any(), contains(check.Code), any<ProblemHighlightType>(), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyClass> = PsiTreeUtil.findChildrenOfType(testFile, PyClass::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyClass(e)
            }
            try {
                Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            } catch (a: AssertionError){
                Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), any<ProblemHighlightType>(), anyVararg<LocalQuickFix>())
            }
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testBinaryExpression(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyBinaryExpression> = PsiTreeUtil.findChildrenOfType(testFile, PyBinaryExpression::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyBinaryExpression(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testStringLiteralExpression(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyStringLiteralExpression> = PsiTreeUtil.findChildrenOfType(testFile, PyStringLiteralExpression::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyStringLiteralExpression(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testFormattedStringElement(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyFormattedStringElement> = PsiTreeUtil.findChildrenOfType(testFile, PyFormattedStringElement::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyFormattedStringElement(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testAssertStatement(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any<PsiElement>(), contains(check.Code), any<ProblemHighlightType>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyAssertStatement> = PsiTreeUtil.findChildrenOfType(testFile, PyAssertStatement::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyAssertStatement(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any<PsiElement>(), contains(check.Code), any<ProblemHighlightType>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testTryExceptStatement(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any<PsiElement>(), contains(check.Code), any<ProblemHighlightType>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyTryExceptStatement> = PsiTreeUtil.findChildrenOfType(testFile, PyTryExceptStatement::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyTryExceptStatement(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any<PsiElement>(), contains(check.Code), any<ProblemHighlightType>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testImportStatement(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyImportStatement> = PsiTreeUtil.findChildrenOfType(testFile, PyImportStatement::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyImportStatement(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }

    fun <inspector: PyInspection>testFromImportStatement(code: String, times: Int = 1, check: Checks.CheckType, filename: String = "test.py", instance: inspector){
        ApplicationManager.getApplication().runReadAction {
            val mockHolder = mock<ProblemsHolder> {
                on { registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>()) } doAnswer {}
            }
            val testFile = this.createLightFile(filename, PythonFileType.INSTANCE.language, code)
            val mockLocalSession = mock<LocalInspectionToolSession> {
                on { file } doReturn (testFile)
            }
            assertNotNull(testFile)
            val testVisitor = instance.buildVisitor(mockHolder, true, mockLocalSession) as PyInspectionVisitor

            val expr: MutableCollection<PyFromImportStatement> = PsiTreeUtil.findChildrenOfType(testFile, PyFromImportStatement::class.java)
            assertNotNull(expr)
            expr.forEach { e ->
                testVisitor.visitPyFromImportStatement(e)
            }
            Mockito.verify(mockHolder, Mockito.times(times)).registerProblem(any(), contains(check.Code), anyVararg<LocalQuickFix>())
            Mockito.verify(mockLocalSession, Mockito.atLeastOnce()).file
        }
    }
}