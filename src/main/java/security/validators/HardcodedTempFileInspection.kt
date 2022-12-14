package security.validators

import com.intellij.codeInspection.LocalInspectionToolSession
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import com.jetbrains.python.inspections.PyInspection
import com.jetbrains.python.psi.PyCallExpression
import com.jetbrains.python.psi.PyStringLiteralExpression
import security.Checks
import security.helpers.SecurityVisitor
import security.helpers.calleeMatches
import security.helpers.qualifiedNameMatches
import security.helpers.skipDocstring

class HardcodedTempFileInspection : PyInspection() {
    val check = Checks.HardcodedTempFileCheck

    override fun getStaticDescription(): String? {
        return check.getStaticDescription()
    }

    override fun buildVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean,
                              session: LocalInspectionToolSession): PsiElementVisitor = Visitor(holder, session)

    private class Visitor(holder: ProblemsHolder, session: LocalInspectionToolSession) : SecurityVisitor(holder, session) {
        override fun visitPyCallExpression(node: PyCallExpression) {
            if (skipDocstring(node)) return
            val possibleTempPaths = arrayOf("/tmp", "/var/tmp", "/dev/shm")
            if (!calleeMatches(node, "open")) return
            if (!qualifiedNameMatches(node, "open", typeEvalContext)) return
            if (node.arguments.isNullOrEmpty()) return
            if (node.arguments.first() !is PyStringLiteralExpression) return
            val path = (node.arguments.first() as PyStringLiteralExpression).stringValue
            if (!listOf(*possibleTempPaths).any { path.startsWith(it)}) return
            holder.registerProblem(node, Checks.HardcodedTempFileCheck.getDescription())
        }
    }
}