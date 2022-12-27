package com.inyongtisto.myhelper.extension

fun String?.toDoubleSafety(): Double {
    return if (this != null) {
        if (this.isEmpty()) {
            0.0
        } else {
            try {
                this.toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0.0
            }
        }
    } else {
        0.0
    }
}

fun Double?.toDoubleSafety(): Double {
    return if (this != null) {
        try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }
    } else {
        0.0
    }
}

fun Int?.toDoubleSafety(): Double {
    return if (this != null) {
        try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }
    } else {
        0.0
    }
}

fun Long?.toDoubleSafety(): Double {
    return if (this != null) {
        try {
            this.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0.0
        }
    } else {
        0.0
    }.also { logs("hasil toDoubleSafety : $it") }
}

fun Int?.toFloatSafety(): Float {
    return if (this != null) {
        try {
            this.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0f
        }
    } else {
        0f
    }
}

fun String?.toIntSafety(): Int {
    return if (this != null) {
        if (this.isEmpty()) {
            0
        } else {
            try {
                this.toInt()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                0
            }
        }
    } else {
        0
    }
}

fun Boolean?.toBoolSafety(): Boolean {
    return this ?: false
}

fun Double?.toIntSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Float?.toIntSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Long?.toIntSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Int?.toIntSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun Float?.toFloatSafety(): Float {
    return if (this != null) {
        try {
            this.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0f
        }
    } else {
        0f
    }
}

fun Byte?.toIntSafety(): Int {
    return if (this != null) {
        try {
            this.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            0
        }
    } else {
        0
    }
}

fun String?.toStringForceEmpty(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    return this.toString()
}

fun Int?.discount(discount: Int?): Int {
    val harga = this.def(0)
    return ((discount.def(0).toDouble() / 100) * harga).toInt()
}