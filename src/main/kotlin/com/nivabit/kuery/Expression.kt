package com.nivabit.kuery

abstract class Expression<T>()

/**
 * Equals expression
 */
class EqExpression(val leftHand: Any, val rightHand: Any?) : Expression<Boolean>() {

    override fun toString(): String {
        return if (rightHand != null) "$leftHand = $rightHand" else "$leftHand IS NULL"
    }

}

infix fun Table.Column.eq(param: Any?): EqExpression {
    return EqExpression("\"${this.table}\".\"$this\"", param)
}

infix fun Table.Column.eq(column: Table.Column): EqExpression {
    return EqExpression("\"${this.table}\".\"$this\"", "\"${column.table}\".\"$column\"")
}

infix fun Table.Column.eq(value: Boolean): EqExpression {
    return EqExpression("\"${this.table}\".\"$this\"", if (value) "1" else "0")
}

/**
 * Not equals expression
 */
class NeExpression(val leftHand: Any, val rightHand: Any?) : Expression<Boolean>() {

    override fun toString(): String {
        return if (rightHand != null) "$leftHand != $rightHand" else "$leftHand IS NOT NULL"
    }

}

infix fun Table.Column.ne(param: Any?): NeExpression {
    return NeExpression("\"${this.table}\".\"$this\"", param)
}

infix fun Table.Column.ne(column: Table.Column): NeExpression {
    return NeExpression("\"${this.table}\".\"$this\"", "\"${column.table}\".\"$column\"")
}

infix fun Table.Column.ne(value: Boolean): NeExpression {
    return NeExpression("\"${this.table}\".\"$this\"", if (value) "1" else "0")
}

/**
 * Not expression
 */
class NotExpression(val expr: Expression<Boolean>) : Expression<Boolean>() {

    override fun toString(): String {
        return "(NOT $expr)"
    }

}

fun not(expr: Expression<Boolean>): NotExpression {
    return NotExpression(expr)
}

/**
 * And expression
 */
class AndExpression(val exprs: Array<Expression<Boolean>>) : Expression<Boolean>() {

    override fun toString(): String {
        return "(${exprs.joinToString(" AND ")})"
    }

}

infix fun Expression<Boolean>.and(expr: Expression<Boolean>): AndExpression {
    return AndExpression(arrayOf(this, expr))
}

/**
 * Or expression
 */
class OrExpression(val exprs: Array<Expression<Boolean>>) : Expression<Boolean>() {

    override fun toString(): String {
        return "(${exprs.joinToString(" OR ")})"
    }

}

infix fun Expression<Boolean>.or(expr: Expression<Boolean>): OrExpression {
    return OrExpression(arrayOf(this, expr))
}

/**
 * Less than expression
 */
class LtExpression(val leftHand: Any, val rightHand: Any) : Expression<Boolean>() {

    override fun toString(): String {
        return "$leftHand < $rightHand"
    }

}

/**
 * Less than or equal expression
 */
class LteExpression(val leftHand: Any, val rightHand: Any) : Expression<Boolean>() {

    override fun toString(): String {
        return "$leftHand <= $rightHand"
    }

}

/**
 * Greater than expression
 */
class GtExpression(val leftHand: Any, val rightHand: Any) : Expression<Boolean>() {

    override fun toString(): String {
        return "$leftHand > $rightHand"
    }

}

/**
 * Greater than or equal expression
 */
class GteExpression(val leftHand: Any, val rightHand: Any) : Expression<Boolean>() {

    override fun toString(): String {
        return "$leftHand >= $rightHand"
    }

}